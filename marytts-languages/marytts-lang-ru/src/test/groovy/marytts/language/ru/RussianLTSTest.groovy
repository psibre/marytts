package marytts.language.ru

import marytts.language.LTSTest

import org.testng.Assert
import org.testng.annotations.*

class RussianLTSTest extends LTSTest {

    @Override
    Collection parseLexicon() {
        lexiconFile.readLines().findAll {
            it && !(it =~ /^\s*#/)
        }.collect {
            it.replace(' ', '').split(/\|/, 3).collect().withLazyDefault {}[0..2]
        }
    }

    @Test
    void testParseLexicon() {
        def expected = ["арс+ена", "ar-ssee-na", null]
        def actual = parseLexicon().first()
        Assert.assertEquals(actual, expected)
    }

}
