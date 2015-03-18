class CmuDictConverter {

    def arpa2sampa = [:]

    CmuDictConverter(mapping) {
        new File(mapping).readLines().collect { it.trim() }.findAll { !it.startsWith('#') }.each { line ->
            def (sampa, arpa) = line.split('<->')
            arpa2sampa[arpa] = sampa
        }
    }

    def convert(dest, lexica) {
        new File(dest).withWriter { out ->
            lexica.each { lexicon ->
                new File(lexicon).eachLine { line ->
                    if (line && !line.startsWith(';')) {
                        def (lemma, pos, arpaPhones) = line.trim().replaceAll(/[()"]/, '').split(/\s/, 3)
                        // TODO: handle stress and syllabification!
                        def sampaPhones = arpaPhones.split().collect {
                            def arpa = it.replaceAll(/[0-2]$/, '')
                            def sampa = arpa2sampa[arpa]
                            sampa ?: arpa
                        }.join()
                        out.println "$lemma\t$sampaPhones${pos != 'nil' ? "\t$pos" : ''}"
                    }
                }
            }
        }
    }

    static void main(String[] args) {
        def (mapping, dest, lexica) = args.with { it[0..1] + [it[2..-1]] }
        def converter = new CmuDictConverter(mapping)
        converter.convert(dest, lexica)
    }
}
