package marytts.language.fr

import org.testng.Assert
import org.testng.FileAssert
import org.testng.annotations.*

class LTSTest {

    def allophoneSetFile
    def lexiconFile

    @BeforeSuite
    @Parameters(['allophoneset', 'lexicon'])
    void setUp(String allophoneSetPath, String lexiconPath) {
        allophoneSetFile = new File(allophoneSetPath)
        lexiconFile = new File(lexiconPath)
    }

    @Test
    @Parameters(['allophoneset', 'lexicon'])
    void testParameters(String allophoneSetPath, String lexiconPath) {
        Assert.assertTrue(allophoneSetPath.endsWith('allophones.fr.xml'))
        Assert.assertTrue(lexiconPath.endsWith('fr.txt'))
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
    void test(String lemma, String transcription, String pos) {
        Assert.assertNotNull(lemma)
        Assert.assertNotNull(transcription)
    }

}
