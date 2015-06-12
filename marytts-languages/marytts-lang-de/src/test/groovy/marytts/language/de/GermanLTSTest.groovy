package marytts.language.de

import marytts.language.LTSTest

import org.testng.Assert
import org.testng.annotations.*

class GermanLTSTest extends LTSTest {

    @Test
    void testParseLexicon() {
        def expected = ["α", "'?al-fa:", null]
        def actual = parseLexicon().first()
        Assert.assertEquals(actual, expected)
    }

}
