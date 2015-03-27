package marytts.language.en

import org.testng.annotations.*
import org.testng.Assert

import marytts.LocalMaryInterface
import marytts.exceptions.MaryConfigurationException

import groovy.xml.*

public class EnglishLTSTest {

    private mary
    private xmlParser

    @BeforeClass
    public void setUp() throws MaryConfigurationException {
        mary = new LocalMaryInterface()
        mary.outputType = "PHONEMES"
        xmlParser = new XmlParser()
    }

    @DataProvider
    public Object[][] lexicon() {
        return this.getClass().getClassLoader().getResource("en.txt").readLines().collect {
            it.split('\t').take(2)
        }
    }

    @Test
    public void testLexDataProvider() {
        Assert.assertNotNull(lexicon())
    }

    @Test(dataProvider = "lexicon")
    public void testTranscriptions(String lemma, String expectedTranscription) {
        def maryXmlDoc = mary.generateXML(lemma)
        def maryXml = XmlUtil.serialize(maryXmlDoc.documentElement)
        def xml = new XmlSlurper().parseText(maryXml)
        def ph = xml.depthFirst().findAll { it.name() == 't' }.collect { it.@ph }.join()
        def phonemes = ph.tokenize()
        def actualTranscription = phonemes.join()
        Assert.assertEquals(actualTranscription, expectedTranscription)
    }
}
