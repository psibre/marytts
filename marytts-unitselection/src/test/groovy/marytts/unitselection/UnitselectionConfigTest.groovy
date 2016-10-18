package marytts.unitselection

import org.testng.annotations.*

class UnitselectionConfigTest {

    @Test
    void isNotMainConfig() {
        def m = new UnitselectionConfig()
        assert m.isMainConfig() == false
        assert m.isSynthesisConfig()
    }

}
