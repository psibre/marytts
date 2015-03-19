package marytts.modules.phonemiser

class ArpaStressAllophoneSet extends AllophoneSet {
    ArpaStressAllophoneSet(inputStream) {
        super(inputStream)
    }

    @Override
    void assignMissingStress(List syllables) {
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
