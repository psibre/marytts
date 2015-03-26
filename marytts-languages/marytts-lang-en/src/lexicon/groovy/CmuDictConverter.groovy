import groovy.util.logging.*
import groovy.xml.XmlUtil
import marytts.modules.phonemiser.ArpaStressAllophoneSet

@Log
class CmuDictConverter {

    def arpa2sampa = [:]
    def allophoneSet

    CmuDictConverter(allophones, mapping) {
        new File(mapping).readLines().collect { it.trim() }.findAll { !it.startsWith('#') }.each { line ->
            def (sampa, arpa) = line.split('<->')
            arpa2sampa[arpa] = sampa
        }
        loadAllophoneSet(allophones)
    }

    def loadAllophoneSet(allophoneSetFile) {
        def allophones = new XmlSlurper().parse(allophoneSetFile)
        (0..2).each { stress ->
            allophones.appendNode {
                consonant ph: stress
            }
        }
        def inputStream = new ByteArrayInputStream(XmlUtil.serialize(allophones).bytes)
        allophoneSet = new ArpaStressAllophoneSet(inputStream)
    }

    def convert(dest, lexicon) {
        new File(dest).withWriter { out ->
            new File(lexicon).eachLine { line ->
                if (line) {
                    def m = line =~ /^([A-Z]+(\d+)?('S)?)(\([1-3]\))?\s+.*/
                    if (m) {
                        if (!m.group(2) && !m.group(4)) {
                            def (lemma, arpaPhones) = line.toLowerCase().trim().split(/\s+/, 2)
                            def sampaPhones = arpaPhones.split().collect {
                                def (arpa, stress) = it.replaceAll(/(.+?)([0-2]?)$/, '$1 $2').tokenize()
                                stress = stress ?: ''
                                def sampa = arpa2sampa[arpa]
                                (sampa ?: arpa) + stress
                            }.join()
                            try {
                                def transcription = allophoneSet.syllabify(sampaPhones).replaceAll(' ', '')
                                out.println "$lemma\t$transcription"
                            } catch (Exception e) {
                                log.warning "Could not syllabify <$lemma> -- excluding it!"
                            }
                        }
                    }
                }
            }
        }
    }

    static void main(String[] args) {
        def (allophoneSetFile, mappingFile, destFile, lexiconFile) = args
        def converter = new CmuDictConverter(allophoneSetFile, mappingFile)
        converter.convert(destFile, lexiconFile)
    }
}
