package marytts.modules;

import static org.fest.assertions.Assertions.assertThat;

import java.io.InputStream;

import marytts.datatypes.MaryData;

import org.apache.commons.io.IOUtils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JTokeniserTest {

    private static JTokeniser jtok;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        jtok = new JTokeniser();
        jtok.startup();
    }

    @Before
    @After
    public void checkState() {
        assertThat(jtok.getState()).isEqualTo(JTokeniser.MODULE_RUNNING);
    }

    private void processTestResource(String resourceName) throws Exception {
        InputStream inputStream = getClass().getResourceAsStream(resourceName);
        String input = IOUtils.toString(inputStream);
        MaryData inputData = new MaryData(jtok.inputType(), jtok.getLocale());
        inputData.setData(input);
        jtok.process(inputData);
    }

    @Test
    public void canProcess() throws Exception {
        processTestResource("RAWMARYXML.xml");
    }

    @Test(expected = de.dfki.lt.tools.tokenizer.exceptions.LanguageNotSupportedException.class)
    public void cannotProcessGerman() throws Exception {
        jtok.setTokenizerLanguage("de");
        processTestResource("RAWMARYXML.de.xml");
    }

    @Test(expected = de.dfki.lt.tools.tokenizer.exceptions.LanguageNotSupportedException.class)
    public void cannotProcessFrench() throws Exception {
        jtok.setTokenizerLanguage("fr");
        processTestResource("RAWMARYXML.fr.xml");
    }

    @AfterClass
    public static void tearDownAfterClass() {
        jtok.shutdown();
        assertThat(jtok.getState()).isEqualTo(JTokeniser.MODULE_OFFLINE);
    }

}
