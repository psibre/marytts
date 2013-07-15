package marytts.tools.newinstall;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.apache.ivy.Ivy;
import org.apache.ivy.core.settings.IvySettings;
import org.apache.ivy.plugins.resolver.URLResolver;

public class Installer {

	/**
	 * @param args
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws MalformedURLException {
		String repositoryUrl = System.getProperty("repositoryUrl");
		URL repoUrl = new URL(repositoryUrl);
		URLResolver resolver = new URLResolver();
		resolver.setM2compatible(true);
		resolver.addArtifactPattern(repoUrl + "[organisation]/[module]/[revision]/[artifact](-[revision]).[ext]");

		IvySettings ivySettings = new IvySettings();
		ivySettings.addResolver(resolver);

		Ivy ivy = Ivy.newInstance(ivySettings);
		String[] listModules = ivy.listModules("marytts");
		String moduleList = StringUtils.join(listModules, "\n");
		System.out.println(moduleList);
	}

}
