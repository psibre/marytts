package marytts.language

import marytts.modules.phonemiser.AllophoneSet

class LexiconCreator {

    static void main(String[] args) {
        def (allophoneSetFileName, lexiconFileName, fstFileName, ltsFileName) = args
        def allophoneSet = AllophoneSet.getAllophoneSet(allophoneSetFileName);
        def lexiconCreator = new marytts.tools.newlanguage.LexiconCreator(allophoneSet, lexiconFileName, fstFileName, ltsFileName)
        lexiconCreator.prepareLexicon()
        lexiconCreator.compileFST()
        lexiconCreator.compileLTS()
    }

}
