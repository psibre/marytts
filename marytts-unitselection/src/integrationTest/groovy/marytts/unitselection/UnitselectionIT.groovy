package marytts.unitselection

import marytts.server.MaryProperties

import org.testng.annotations.*

class UnitselectionIT {

    @Test
    void testProperty() {
        def synthesizers = MaryProperties.getList("synthesizers.classes.list")
        assert synthesizers
        assert synthesizers.contains("marytts.unitselection.UnitSelectionSynthesizer")
    }

}
