package marytts.language.fr

import marytts.modules.phonemiser.AllophoneSet
import marytts.modules.phonemiser.TrainedLTS

import org.testng.Assert
import org.testng.FileAssert
import org.testng.annotations.*

class LTSTest {

    def allophoneSetFile
    def lexiconFile
    def lts

    @BeforeSuite
    @Parameters(['allophoneset', 'lexicon'])
    void setUp(String allophoneSetPath, String lexiconPath) {
        allophoneSetFile = new File(allophoneSetPath)
        lexiconFile = new File(lexiconPath)
        def allophoneSet = AllophoneSet.getAllophoneSet(allophoneSetFile.newInputStream(), 'test')
        def ltsStream = getClass().getResourceAsStream('/marytts/language/fr/lexicon/fr.lts')
        lts = new TrainedLTS(allophoneSet, ltsStream, false);
    }

    @Test
    @Parameters(['allophoneset', 'lexicon'])
    void canReadResources(String allophoneSetPath, String lexiconPath) {
        FileAssert.assertReadable(allophoneSetFile)
        FileAssert.assertReadable(lexiconFile)
    }

    @DataProvider
    Object[][] lexicon() {
        lexiconFile.readLines().findAll { !(it =~ /^\s*#/) }.collect {
            it.tokenize().withLazyDefault {}[0..2]
        }
    }

    @Test(dataProvider = 'lexicon')
    void testLTS(String lemma, String expected, String pos) {
        Assert.assertNotNull(lemma)
        Assert.assertNotNull(expected)
        def predicted = lts.predictPronunciation(lemma)
        Assert.assertEquals(predicted, predicted.replaceAll('1', ''), 'Should not find trailing ones on vowels:')
        def actual = lts.syllabify(predicted)?.replaceAll(' ', '')
        Assert.assertEquals(actual, expected)
    }

}
