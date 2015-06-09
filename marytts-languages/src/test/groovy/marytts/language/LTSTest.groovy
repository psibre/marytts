package marytts.language

import marytts.modules.phonemiser.AllophoneSet
import marytts.modules.phonemiser.TrainedLTS

import org.testng.FileAssert
import org.testng.annotations.*
import org.testng.asserts.SoftAssert

class LTSTest {

    def allophoneSetFile
    def lexiconFile
    def lts

    @BeforeSuite
    @Parameters(['allophoneset', 'lexicon', 'lts'])
    void setUp(String allophoneSetPath, String lexiconPath, String ltsResourcePath) {
        allophoneSetFile = new File(allophoneSetPath)
        lexiconFile = new File(lexiconPath)
        def allophoneSet = AllophoneSet.getAllophoneSet(allophoneSetFile.newInputStream(), 'test')
        def ltsStream = getClass().getResourceAsStream(ltsResourcePath)
        lts = new TrainedLTS(allophoneSet, ltsStream, false);
    }

    @Test
    @Parameters(['allophoneset', 'lexicon'])
    void canReadResources(String allophoneSetPath, String lexiconPath) {
        FileAssert.assertReadable(allophoneSetFile)
        FileAssert.assertReadable(lexiconFile)
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
        softAssert.assertNotNull(expected, 'lexicon entry must have a transcription:')
        def predicted = lts.predictPronunciation(lemma)
        softAssert.assertEquals(predicted, predicted.replaceAll('1', ''), 'Should not find trailing ones on vowels:')
        def actual
        try {
            actual = lts.syllabify(predicted)?.replaceAll(' ', '')
        } catch (IllegalArgumentException e) {
            softAssert.fail("Could not syllabify: $e.message")
        }
        softAssert.assertEquals(actual, expected, 'transcriptions differ:')
        softAssert.assertAll()
    }

}
