package marytts.language.fr

import org.testng.Assert
import org.testng.FileAssert
import org.testng.annotations.*

class LTSTest {

    @Test
    @Parameters(['allophoneset', 'lexicon'])
    void testParameters(String allophoneSetPath, String lexiconPath) {
        Assert.assertTrue(allophoneSetPath.endsWith('allophones.fr.xml'))
        Assert.assertTrue(lexiconPath.endsWith('fr.txt'))
    }

    @Test
    @Parameters(['allophoneset', 'lexicon'])
    void canReadResources(String allophoneSetPath, String lexiconPath) {
        def allophoneSetFile = new File(allophoneSetPath)
        FileAssert.assertReadable(allophoneSetFile)
        def lexiconFile = new File(lexiconPath)
        FileAssert.assertReadable(lexiconFile)
    }

}
