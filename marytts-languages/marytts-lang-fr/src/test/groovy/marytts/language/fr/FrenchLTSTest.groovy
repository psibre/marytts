package marytts.language.fr

import marytts.language.LTSTest

import org.testng.Assert
import org.testng.annotations.*

class FrenchLTSTest extends LTSTest {

    @Test
    void testParseLexicon() {
        def expected = ["à", "'a", "functional"]
        def actual = parseLexicon().first()
        Assert.assertEquals(actual, expected)
    }

}
