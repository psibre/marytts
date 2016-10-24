package marytts.unitselection.io

import org.testng.annotations.*

class TimelineTest {
    def timeline

    @BeforeMethod
    void setUp() {
        timeline = new Timeline()
    }

    @Test(expectedExceptions = AssertionError)
    void testCreateFrom() {
        assert false // not yet implemented
    }

    @Test(expectedExceptions = AssertionError)
    void testSaveTo() {
        assert false // not yet implemented
    }
}
