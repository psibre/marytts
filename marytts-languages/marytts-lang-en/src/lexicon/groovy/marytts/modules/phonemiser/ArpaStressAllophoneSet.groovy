package marytts.modules.phonemiser

class ArpaStressAllophoneSet extends AllophoneSet {
    ArpaStressAllophoneSet(inputStream) {
        super(inputStream)
    }

    @Override
    void assignMissingStress(List syllables) {
        // correct stress markers separated from the corresponding syllable nucleus
        def prevSyllable
        syllables.each { syllable ->
            if (prevSyllable && syllable.firstAllophone.name() ==~ /[0-2]/) {
                prevSyllable.appendAllophone syllable.firstAllophone
                syllable.allophones = syllable.allophones.drop(1)
            }
            prevSyllable = syllable
        }
        // strip ARPAbet stress marks and assign corresponding Stress to the Syllable
        syllables.each { syllable ->
            syllable.allophones.removeAll {
                switch (it.name()) {
                    case '0':
                        syllable.stress = Stress.NONE
                        return true
                    case '1':
                        syllable.stress = Stress.PRIMARY
                        return true
                    case '2':
                        syllable.stress = Stress.SECONDARY
                        return true
                }
            }
        }
    }
}
