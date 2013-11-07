package marytts.tools.newinstall;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import org.apache.ivy.Ivy;
import org.apache.ivy.core.install.InstallOptions;
import org.apache.ivy.core.module.descriptor.ModuleDescriptor;
import org.apache.ivy.core.resolve.ResolveOptions;
import org.apache.ivy.core.retrieve.RetrieveOptions;
import org.apache.ivy.core.settings.IvySettings;
import org.apache.ivy.plugins.parser.xml.XmlModuleDescriptorParser;
import org.apache.ivy.util.DefaultMessageLogger;
import org.apache.ivy.util.Message;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.gson.Gson;

public class Installer {

	/**
	 * Parse list of voice descriptors from JSON array in resource. The resource is generated at compile time by the <a
	 * href="http://numberfour.github.io/file-list-maven-plugin/list-mojo.html">file-list-maven-plugin</a>.
	 * 
	 * @return List of voice descriptor resources
	 * @throws IOException
	 */
	public List<String> readVoiceDescriptorList() throws IOException {
		URL voiceListResource = Resources.getResource("voice-list.json");
		String voiceListJson = Resources.toString(voiceListResource, Charsets.UTF_8);
		String[] voiceDescriptors = new Gson().fromJson(voiceListJson, String[].class);
		return Arrays.asList(voiceDescriptors);
	}

	/**
	 * Test Installer <br>
	 * <b>Note:</b> must currently run with -Dmary.base=/path/to/marytts
	 * 
	 * @param args
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException, IOException {
		// load ivy settings
		IvySettings ivySettings = new IvySettings();
		ivySettings.load(Resources.getResource("ivysettings.xml"));

		// as a test, parse module descriptor for lang-de component
		URL resource = Resources.getResource("marytts-voice-cmu-slt-hsmm-5.0-SNAPSHOT.xml");
		ModuleDescriptor descriptor = XmlModuleDescriptorParser.getInstance().parseDescriptor(ivySettings, resource, true);

		// instantiate ivy
		Ivy ivy = Ivy.newInstance(ivySettings);
		// set ivy loglevel to DEBUG
		ivy.getLoggerEngine().pushLogger(new DefaultMessageLogger(Message.MSG_DEBUG));
		ResolveOptions resolveOptions = new ResolveOptions();
		ivy.resolve(descriptor, resolveOptions);
		// retrieve options; retrieved artifacts go into MARYBASE/lib
		RetrieveOptions retrieveOptions = new RetrieveOptions();
		retrieveOptions.setDestIvyPattern("${mary.base}/installed/[module]-[revision].xml");
		retrieveOptions.setDestArtifactPattern("${mary.base}/lib/[module]-[revision].[ext]");
		ivy.retrieve(descriptor.getModuleRevisionId(), retrieveOptions);

		InstallOptions installOptions = new InstallOptions();
		installOptions.setTransitive(true);
		installOptions.setOverwrite(true);
		String from = "remote";
		String to = "marytts-installed";
		ivy.install(descriptor.getModuleRevisionId(), from, to, installOptions);
	}
}
