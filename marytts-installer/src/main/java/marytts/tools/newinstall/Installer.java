package marytts.tools.newinstall;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import marytts.tools.newinstall.enums.LogLevel;
import marytts.tools.newinstall.enums.Status;
import marytts.tools.newinstall.objects.Component;
import marytts.tools.newinstall.objects.VoiceComponent;

import org.apache.commons.io.FileUtils;
import org.apache.ivy.Ivy;
import org.apache.ivy.core.install.InstallOptions;
import org.apache.ivy.core.module.descriptor.DependencyDescriptor;
import org.apache.ivy.core.module.descriptor.ModuleDescriptor;
import org.apache.ivy.core.module.id.ArtifactRevisionId;
import org.apache.ivy.core.module.id.ModuleId;
import org.apache.ivy.core.module.id.ModuleRevisionId;
import org.apache.ivy.core.report.ArtifactDownloadReport;
import org.apache.ivy.core.report.ResolveReport;
import org.apache.ivy.core.resolve.ResolveOptions;
import org.apache.ivy.core.settings.IvySettings;
import org.apache.ivy.plugins.parser.xml.XmlModuleDescriptorParser;
import org.apache.ivy.util.DefaultMessageLogger;
import org.apache.log4j.Logger;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.gson.Gson;

/**
 * Main class of the component installer. Both holds the main method and holds data and functionality methods on the component
 * data
 * 
 * @author Jonathan, Ingmar
 * 
 */
public class Installer {

	// Ivy instance, used for installation and resolving purposes
	private Ivy ivy;

	// Java representation of the ivysettings.xml file that holds information about repository structure and location
	private IvySettings ivySettings;

	// Options passed on to the resolve/install methods of the Ivy object
	private ResolveOptions resolveOptions;
	private InstallOptions installOptions;

	// holds all currently available components
	private List<Component> resources;

	// map storing all possible component values. Used for easy access when GUI sets the filtering options in the comboBoxes
	private HashMap<String, HashSet<String>> attributeValues;

	// the instance of the command line interface which is created once the installer is started
	private InstallerCLI cli;

	// holds the directory where marytts should be (preferably: is) installed. This location will be used to put downloaded and
	// installed components in
	private String maryBasePath;

	static Logger logger = Logger.getLogger(marytts.tools.newinstall.Installer.class.getName());

	/**
	 * initializes the attribute values to be stored in Installer for later usage in GUI and CLI
	 */
	private void initAttributeValues() {
		this.attributeValues = new HashMap<String, HashSet<String>>();
		this.attributeValues.put("locale", new HashSet<String>());
		this.attributeValues.put("status", new HashSet<String>());
		this.attributeValues.put("type", new HashSet<String>());
		this.attributeValues.put("gender", new HashSet<String>());
	}

	public Installer(String[] args) {

		logger.debug("Loading installer.");
		this.resources = new ArrayList<Component>();
		this.cli = new InstallerCLI(args, this);
		initAttributeValues();

		// test if user has specified mary path on command line. If not, determine directory Installer is run from
		if (this.maryBasePath == null) {
			setMaryBase();
		}
		logger.debug("Set mary base path to: " + this.maryBasePath);

		// setup ivy

		try {
			// loads ivy settings from resources ivysettings.xml file
			loadIvySettings();

			// creating a new ivy instance as well as sets necessary options
			loadIvy();

			logger.debug("Starting ivy resource parse");
			// parses component descriptors, creates Component objects from them and stores them in this.resources
			parseIvyResources();

			// once the resources are parsed, Installer passes the workflow on to the command line interface which evaluates
			// parameters that have been passed on to the Installer
			this.cli.mainEvalCommandLine();

		} catch (IOException ioe) {
			logger.error("Could not access settings file: " + ioe.getMessage());
		} catch (ParseException pe) {
			logger.error("Could not access settings file: " + pe.getMessage());
		}
	}

	/**
	 * creates an Ivy instance and sets necessary options
	 */
	public void loadIvy() {
		logger.info("Creating new Ivy instance");
		this.ivy = Ivy.newInstance(this.ivySettings);
		setIvyLoggingLevel(LogLevel.warn);
		this.resolveOptions = new ResolveOptions().setOutputReport(false);
		this.installOptions = new InstallOptions().setOverwrite(true).setTransitive(true);
	}

	public void loadIvySettings() throws ParseException, IOException {
		this.ivySettings = new IvySettings();
		this.ivySettings.setVariable("mary.base", this.maryBasePath);
		logger.debug("Loading ivysettings.xml");
		this.ivySettings.load(Resources.getResource("ivysettings.xml"));
	}

	/**
	 * Helper method used to manipulate the ivy log level
	 * 
	 * @param logLevel
	 */
	protected void setIvyLoggingLevel(LogLevel logLevel) {
		int ivyLevel;
		switch (logLevel) {
		case error:
			ivyLevel = 0;
			break;
		case warn:
			ivyLevel = 1;
			break;
		case info:
			ivyLevel = 2;
			break;
		case debug:
			ivyLevel = 3;
			break;
		case verbose_debug:
			ivyLevel = 4;
			break;
		default:
			ivyLevel = 1;
			break;
		}
		DefaultMessageLogger dml = new DefaultMessageLogger(ivyLevel);
		dml.setShowProgress(true);
		this.ivy.getLoggerEngine().setDefaultLogger(dml);
	}

	/**
	 * method to set the maryBasePath variable. Is only called if user didn't manually set a path on the command line and thus
	 * uses instead the location where the Installer.jar is run from
	 */
	private void setMaryBase() {
		logger.debug("Setting mary base directory");
		File maryBase = null;
		// fall back to location of this class/jar
		// from http://stackoverflow.com/a/320595
		URL location = Installer.class.getProtectionDomain().getCodeSource().getLocation();
		try {
			logger.debug("Trying to use directory Installer is run from.");
			maryBase = new File(location.toURI().getPath());
		} catch (URISyntaxException use) {
			// TODO Auto-generated catch block
			logger.error("Could not parse " + location + ": " + use.getMessage() + "\n");
		}
		setMaryBase(maryBase);
	}

	/**
	 * sets a new file path for the marytts base directory.
	 * 
	 * @param maryBase
	 * @return true if mary path was successfully set, false otherwise
	 */
	public boolean setMaryBase(File maryBase) {
		boolean toReturn;
		try {
			maryBase = maryBase.getCanonicalFile();
			toReturn = true;
		} catch (IOException ioe) {
			logger.error("Could not determine path to directory " + maryBase + ": " + ioe + "\n");
			toReturn = false;
		}
		// if this is running from the jar file, back off to directory containing it
		if (maryBase.isFile()) {
			logger.debug("Installer is running from jar. Creating directory for setting mary base path");
			maryBase = maryBase.getParentFile();
			toReturn = true;
		}
		// create directory (with parents, if required)
		try {
			FileUtils.forceMkdir(maryBase);
			toReturn = true;
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
			toReturn = false;
		}
		try {
			this.maryBasePath = maryBase.getCanonicalPath();
			toReturn = true;
		} catch (IOException ioe) {
			logger.error("Could not determine path to directory " + maryBase + ": " + ioe + "\n");
			toReturn = false;
		}
		return toReturn;
	}

	/**
	 * @return the maryBasePath
	 */
	public String getMaryBasePath() {
		return this.maryBasePath;
	}

	/**
	 * Installs given component using the ivy instance of this class
	 * 
	 * @param component
	 * @throws ParseException
	 * @throws IOException
	 */
	public void install(Component component) throws ParseException, IOException {

		// List<String> installedArtifacts = new ArrayList<String>();

		// first resolves component's dependencies
		logger.info("IVY: Resolving and installing component " + component.getName());
		ResolveReport resolveDependencies = this.ivy.resolve(component.getModuleDescriptor(), this.resolveOptions);

		ArtifactDownloadReport[] dependencyReports = resolveDependencies.getAllArtifactsReports();
		// if (dependencyReports[0].getDownloadStatus() == DownloadStatus.SUCCESSFUL) {
		// Artifact art = dependencyReports[0].getArtifact();
		// // String artifactName = art.getAttribute("organisation") + "-" + art.getName() + "."
		// // + art.getExt();
		// logger.info("IVY: Resolved dependency " + art.getName());
		// }

		// if there were dependencies that have been resolved, install these as well (copy from downloaded/ to lib/)
		for (int i = 0; i < dependencyReports.length; i++) {
			// install resolved dependencies
			ArtifactDownloadReport artifactDownloadReport = dependencyReports[i];
			ModuleRevisionId mrid = artifactDownloadReport.getArtifact().getModuleRevisionId();
			ResolveReport installDependencies = this.ivy.install(mrid, "downloaded", "installed", this.installOptions);
			// logging?
			// ArtifactDownloadReport[] installReports = installDependencies.getAllArtifactsReports();
		}

		// finally install the component itself
		ResolveReport install = this.ivy.install(component.getModuleDescriptor().getModuleRevisionId(), "remote", "installed",
				this.installOptions);
		logger.info("IVY: " + install.getAllArtifactsReports()[0]);
	}

	/**
	 * helper method to get information about dependencies of component prior to its resolution
	 * 
	 * @param component
	 * @return
	 */
	public List<String> retrieveDependencies(Component component) {

		List<String> toReturn = new ArrayList<String>();
		DependencyDescriptor[] dependencies = component.getModuleDescriptor().getDependencies();
		for (DependencyDescriptor oneDep : dependencies) {
			ModuleId moduleId = oneDep.getDependencyId();
			toReturn.add(moduleId.getName());
		}
		return toReturn;
	}

	/**
	 * @param componentName
	 * @return
	 */
	public long getSizeOfComponentByName(String componentName) {

		for (Component oneComponent : this.resources) {
			if (componentName.equalsIgnoreCase(oneComponent.getName())) {
				return oneComponent.getSize();
			}
		}
		return 0L;
	}

	/**
	 * Parse list of voice descriptors from JSON array in resource. The resource is generated at compile time by the <a
	 * href="http://numberfour.github.io/file-list-maven-plugin/list-mojo.html">file-list-maven-plugin</a>.
	 * 
	 * @return List of voice descriptor resources
	 * @throws IOException
	 */
	public List<String> readComponentDescriptorList() throws IOException {
		URL componentListResource = Resources.getResource("component-list.json");
		logger.debug("reading component descriptor list component-list.json from resources");
		String componentListJson = Resources.toString(componentListResource, Charsets.UTF_8);
		String[] componentDescriptors = new Gson().fromJson(componentListJson, String[].class);
		return Arrays.asList(componentDescriptors);
	}

	/**
	 * retrieves the voice component names from the {@link #readComponentDescriptorList()} and creates {@link Component} objects.<br>
	 * Those Components then are added to the list holding all Components and the {{@link #storeAttributeValues(Component)} method
	 * takes care of storing possible attribute values in a HashMap<br>
	 * TODO remove repeated code
	 * 
	 * @throws ParseException
	 * @throws IOException
	 */
	public void parseIvyResources() {

		try {
			List<String> resourcesList = readComponentDescriptorList();
			
			// as this method can be used to reparse the components, clear the existing ones first
			this.resources.clear();
			
			initAttributeValues();
			for (String oneFileName : resourcesList) {
				logger.debug("Parsing " + oneFileName);
				if (oneFileName.startsWith("marytts-voice")) {

					URL oneResource = Resources.getResource(oneFileName);
					ModuleDescriptor descriptor = XmlModuleDescriptorParser.getInstance().parseDescriptor(this.ivySettings,
							oneResource, true);
					VoiceComponent oneComponent = new VoiceComponent(descriptor);

					ArtifactRevisionId artifactRevisionId = descriptor.getAllArtifacts()[0].getId();
					String artifactName = /* artifactRevisionId.getAttribute("organisation") + "-" + */artifactRevisionId.getName()
							+ "-" + artifactRevisionId.getRevision() + "." + artifactRevisionId.getExt();
					oneComponent.setStatus(getResourceStatus(artifactName));
					this.resources.add(oneComponent);
					storeAttributeValues(oneComponent);
					logger.debug((oneComponent.getClass().getSimpleName().equals("VoiceComponent") ? "VoiceComponent "
							: "Component ") + oneComponent.getName() + " added to resource list.");
				} else if (oneFileName.startsWith("marytts-lang")) {

					URL oneResource = Resources.getResource(oneFileName);
					ModuleDescriptor descriptor = XmlModuleDescriptorParser.getInstance().parseDescriptor(this.ivySettings,
							oneResource, true);
					Component oneComponent = new Component(descriptor);
					ArtifactRevisionId artifactRevisionId = descriptor.getAllArtifacts()[0].getId();
					String artifactName = /* artifactRevisionId.getAttribute("organisation") + "-" + */artifactRevisionId.getName()
							+ "-" + artifactRevisionId.getRevision() + "." + artifactRevisionId.getExt();
					oneComponent.setStatus(getResourceStatus(artifactName));
					this.resources.add(oneComponent);
					storeAttributeValues(oneComponent);
					logger.debug((oneComponent.getClass().getSimpleName().equals("VoiceComponent") ? "VoiceComponent "
							: "Component ") + oneComponent.getName() + " added to resource list.");
				}
			}
		} catch (IOException ioe) {
			logger.error("Problem reading in file: " + ioe.getMessage());
		} catch (ParseException pe) {
			logger.error("Problem parsing component file: " + pe.getMessage());
		}
	}

	public Status getResourceStatus(String componentName) {

		// TODO problem ist, dass der deployte name von lang comp mit marytts beginnt, der voice comps aber ohne -> beheben!!
		if (new File(this.maryBasePath + "/lib/" + componentName).exists()) {
			return Status.INSTALLED;
		}
		if (new File(this.maryBasePath + "/download/" + componentName).exists()) {
			return Status.DOWNLOADED;
		}
		return Status.AVAILABLE;
	}

	public void updateResourceStatuses() {

		for (Component oneComponent : this.resources) {
			ArtifactRevisionId ari = oneComponent.getModuleDescriptor().getAllArtifacts()[0].getId();
			String artifactName = /* ari.getAttribute("organisation") + "-" + */ari.getName() + "-" + ari.getRevision() + "."
					+ ari.getExt();
			oneComponent.setStatus(getResourceStatus(artifactName));
		}
	}

	/**
	 * When a {@link Component} or {@link VoiceComponent} is parsed, it's attribute values are extracted and added to a hashMap in
	 * order for the GUI and the CLI to access these values easily later.
	 * 
	 * @param oneComponent
	 */
	private void storeAttributeValues(Component oneComponent) {

		logger.debug("Adding component's attribute values to attributeList");
		this.attributeValues.get("locale").add(oneComponent.getLocale().toString());
		this.attributeValues.get("status").add(oneComponent.getStatus().toString());
		if (oneComponent instanceof VoiceComponent) {
			VoiceComponent oneVoiceComponent = (VoiceComponent) oneComponent;
			this.attributeValues.get("type").add(oneVoiceComponent.getType());
			this.attributeValues.get("gender").add(oneVoiceComponent.getGender());
		}
	}

	/**
	 * filters the available components by one or many attribute-value pairs. Iterates over list of components and removes those
	 * that match the given attribute-value pair. if all attributeValues are null, the method simply returns all components.
	 * 
	 * @param locale
	 * @param type
	 * @param gender
	 * @param status
	 * @param name
	 * @return component list
	 */
	public List<Component> getAvailableComponents(String locale, String type, String gender, String status, String name,
			boolean voiceOnly) {

//		/* @formatter:off */
//		logger.info("Filtering resources by " + ((locale == null) ? "" : "locale=" + locale + ";")
//										      + ((type == null) ? "" : "locale=" + type + ";") 
//										      + ((gender== null) ? "" : "locale=" + gender + ";")
//										      + ((status == null) ? "" : "locale=" + status + ";")
//										      + ((name == null) ? "" : "locale=" + name + ";"));
//		/* @formatter:on */

		List<Component> resourcesToBeFiltered = new ArrayList<Component>(this.resources);

		// stores the size of the voice component list before filtering.
		int sizeBefore = resourcesToBeFiltered.size();
		logger.debug("Resource list size before filtering: " + sizeBefore);

		// in order to modify the list while iterating over it, an iterator is needed to call the Iterator.remove() method.
		Iterator<Component> it;

		if (resourcesToBeFiltered.isEmpty()) {
			logger.warn("List is empty!");
			return resourcesToBeFiltered;
		}

		// int sizeAfter = resourcesToBeFiltered.size();
		if (locale != null && !locale.equals("all")) {
			logger.debug("filtering by " + "locale=" + locale);
			for (it = resourcesToBeFiltered.iterator(); it.hasNext();) {
				Component oneComponent = it.next();
				if (!oneComponent.getLocale().toString().equalsIgnoreCase(locale)) {
					// logger.debug("Removed " + oneComponent + " as its locale=" + locale);
					it.remove();
				}
			}
			// sizeAfter = resourcesToBeFiltered.size();
			// logger.debug("Resource list size after filtering: " + sizeAfter);
			// if (sizeBefore == sizeAfter) {
			// logger.info("Locale didn't affect filtering");
			// }
			// sizeBefore = sizeAfter;
		}
		if (type != null && !type.equals("all")) {
			logger.debug("filtering by " + "type=" + type);
			for (it = resourcesToBeFiltered.iterator(); it.hasNext();) {
				Component oneComponent = it.next();
				if (!(oneComponent instanceof VoiceComponent)) {
					logger.debug("Removed " + oneComponent + " as it is not a VoiceComponent");
					it.remove();
					continue;
				}
				VoiceComponent oneVoiceComponent = (VoiceComponent) oneComponent;
				if (!oneVoiceComponent.getType().equalsIgnoreCase(type)) {
					it.remove();
				}
			}
		}
		if (gender != null && !gender.equals("all")) {
			logger.debug("filtering by " + "gender=" + gender);
			for (it = resourcesToBeFiltered.iterator(); it.hasNext();) {
				Component oneComponent = it.next();
				if (!(oneComponent instanceof VoiceComponent)) {
					logger.debug("Removed " + oneComponent + " as it is not a VoiceComponent");
					it.remove();
					continue;
				}
				VoiceComponent oneVoiceComponent = (VoiceComponent) oneComponent;
				if (!oneVoiceComponent.getGender().equalsIgnoreCase(gender)) {
					it.remove();
				}
			}
		}
		if (status != null && !status.equals("all")) {
			logger.debug("filtering by " + "status=" + status);
			for (it = resourcesToBeFiltered.iterator(); it.hasNext();) {
				Component oneComponent = it.next();
				if (!oneComponent.getStatus().toString().equalsIgnoreCase(status)) {
					it.remove();
				}
			}
		}
		if (name != null && !name.equals("all")) {
			logger.debug("filtering by " + "name=" + name);
			for (it = resourcesToBeFiltered.iterator(); it.hasNext();) {
				Component oneComponent = it.next();
				if (!oneComponent.getName().equalsIgnoreCase(name)) {
					it.remove();
				}
			}
		}
		if (voiceOnly) {
			logger.debug("filtering by component type=" + (voiceOnly ? "voice " : " ") + "component");
			for (it = resourcesToBeFiltered.iterator(); it.hasNext();) {
				Component oneComponent = it.next();
				if (!(oneComponent instanceof VoiceComponent)) {
					it.remove();
				}
			}
		}

		return resourcesToBeFiltered;
	}

	/**
	 * @return the attributeValues
	 */
	public HashMap<String, HashSet<String>> getAttributeValues() {
		return this.attributeValues;
	}

	/**
	 * checks if component list contains a {@link Component} with the name equal to the one passed along to this method.
	 * 
	 * @param nameValue
	 *            the value of the name to be searched for
	 * @return true if nameValue was found, false otherwise
	 */
	public boolean isNamePresent(String nameValue) {

		for (Component oneComponent : this.resources) {
			if (oneComponent.getName().equalsIgnoreCase(nameValue)) {
				return true;
			}
		}
		return false;
	}

	// /**
	// * @return
	// */
	// public List<Component> getAvailableComponents() {
	// return getAvailableComponents(null, null, null, null, null, false);
	// }

	/**
	 * 
	 * Installer Main Method<br>
	 * <b>Note:</b> must currently run with -Dmary.base=/path/to/marytts
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Installer installer = new Installer(args);
	}

}
