package marytts.unitselection

import groovy.util.logging.Log4j

import marytts.server.MaryProperties
import marytts.util.MaryRuntimeUtils

import org.testng.annotations.*

@Log4j
class UnitselectionIT {

    @BeforeClass
    void setUp() {
        MaryRuntimeUtils.ensureMaryStarted()
    }

    @Test
    void testProperty() {
        def synthesizers = MaryProperties.getList("synthesizers.classes.list")
        assert synthesizers?.contains("marytts.unitselection.UnitSelectionSynthesizer")
    }

}
