package marytts.tools.newinstall;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.ivy.Ivy;
import org.apache.ivy.core.settings.IvySettings;
import org.apache.ivy.plugins.repository.url.URLResource;
import org.apache.ivy.plugins.resolver.URLResolver;

public class Installer {

	/**
	 * @param args
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws MalformedURLException {
		URL repoUrl = new URL("http://coli.uni-saarland.de/~steiner/.m2");
		URLResolver resolver = new URLResolver();
		resolver.setM2compatible(true);
		resolver.setName("central");
		resolver.addArtifactPattern("http://repo1.maven.org/maven2/"
				+ "[organisation]/[module]/[revision]/[artifact](-[revision]).[ext]");

		// resolver.addArtifactPattern(repoUrl.toString() + "[organisation]/[module]/[revision]/[artifact](-[revision]).[ext]");
		// resolver.addArtifactPattern(pattern);

		IvySettings ivySettings = new IvySettings();
		ivySettings.addResolver(resolver);

		Ivy ivy = Ivy.newInstance(ivySettings);
		String[] listModules = ivy.listModules("org.apache.maven");
	}

}
