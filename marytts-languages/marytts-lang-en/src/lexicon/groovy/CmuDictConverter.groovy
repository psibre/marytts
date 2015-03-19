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

    def convert(dest, lexica) {
        new File(dest).withWriter { out ->
            lexica.each { lexicon ->
                new File(lexicon).eachLine { line ->
                    if (line && !line.startsWith(';')) {
                        def (lemma, pos, arpaPhones) = line.trim().replaceAll(/[()"]/, '').split(/\s/, 3)
                        // TODO: handle stress and syllabification!
                        def sampaPhones = arpaPhones.split().collect {
                            def (arpa, stress) = it.replaceAll(/(.+?)([0-2]?)$/, '$1 $2').tokenize()
                            stress = stress ?: ''
                            def sampa = arpa2sampa[arpa]
                            (sampa ?: arpa) + stress
                        }.join()
                        try {
                            def transcription = allophoneSet.syllabify sampaPhones
                            out.println "$lemma\t$transcription${pos != 'nil' ? "\t$pos" : ''}"
                        } catch (Exception e) {
                            log.warning "Could not syllabify <$lemma> -- excluding it!"
                        }
                    }
                }
            }
        }
    }

    static void main(String[] args) {
        def (allophoneSetFile, mappingFile, destFile) = args
        def lexiconFiles = args.drop(3)
        def converter = new CmuDictConverter(allophoneSetFile, mappingFile)
        converter.convert(destFile, lexiconFiles)
    }
}
