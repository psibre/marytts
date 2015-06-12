package marytts.language.it

import marytts.language.LTSTest

import org.testng.Assert
import org.testng.annotations.*

class ItalianLTSTest extends LTSTest {

    @Test
    void testParseLexicon() {
        def expected = ["a", "a", "functional"]
        def actual = parseLexicon().first()
        Assert.assertEquals(actual, expected)
    }

}
