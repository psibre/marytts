package marytts.language

import marytts.fst.FSTLookup
import marytts.modules.phonemiser.AllophoneSet
import marytts.modules.phonemiser.TrainedLTS

import org.testng.FileAssert
import org.testng.annotations.*
import org.testng.asserts.SoftAssert

class LTSTest {

    def allophoneSetFile
    def lexiconFile
    def lts
    def fst

    @BeforeSuite
    @Parameters(['allophoneset', 'lexicon', 'lts', 'fst'])
    void setUp(String allophoneSetPath, String lexiconPath, String ltsResourcePath, String fstResourcePath) {
        allophoneSetFile = new File(allophoneSetPath)
        lexiconFile = new File(lexiconPath)
        def allophoneSet = AllophoneSet.getAllophoneSet(allophoneSetFile.newInputStream(), 'test')
        def ltsStream = new File(ltsResourcePath).newInputStream()
        lts = new TrainedLTS(allophoneSet, ltsStream, false);
        def fstStream = new File(fstResourcePath).newInputStream()
        fst = new FSTLookup(fstStream, null);
    }

    @Test
    @Parameters(['allophoneset', 'lexicon', 'lts', 'fst'])
    void canReadResources(String allophoneSetPath, String lexiconPath, String ltsResourcePath, String fstResourcePath) {
        FileAssert.assertReadable(allophoneSetFile)
        FileAssert.assertReadable(lexiconFile)
        FileAssert.assertReadable(new File(ltsResourcePath))
        FileAssert.assertReadable(new File(fstResourcePath))
    }

    Collection parseLexicon() {
        lexiconFile.readLines().findAll { it && !(it =~ /^\s*#/) }
    }

    @DataProvider
    Object[][] lexicon() {
        parseLexicon().collect {
            it.tokenize().withLazyDefault {}[0..2]
        }
    }

    @Test(dataProvider = 'lexicon')
    void testLTS(String lemma, String expected, String pos) {
        def softAssert = new SoftAssert()
        softAssert.assertNotNull(lemma)
        softAssert.assertNotNull(expected, 'Lexicon entry must have a transcription:')
        def predicted = lts.predictPronunciation(lemma)
        softAssert.assertEquals(predicted, predicted.replaceAll('1', ''), 'Should not find trailing ones on vowels:')
        def actual
        try {
            actual = lts.syllabify(predicted)?.replaceAll(' ', '')
            softAssert.assertEquals(actual, expected, 'Predicted transcriptions differ:')
        } catch (IllegalArgumentException e) {
            softAssert.fail("Could not syllabify: $e.message")
        }
        // lexicon lookup lemma+pos, fall back to lemma only
        actual = (fst.lookup(lemma + pos) ?: fst.lookup(lemma)).first().replace(' ', '')
        softAssert.assertEquals(actual, expected, 'Stored transcriptions differ:')
        softAssert.assertAll()
    }

}
