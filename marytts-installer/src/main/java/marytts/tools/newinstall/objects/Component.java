/**
 * 
 */
package marytts.tools.newinstall.objects;

import java.util.Locale;

import marytts.tools.newinstall.Status;

import org.apache.ivy.core.module.descriptor.ModuleDescriptor;

import com.google.common.collect.ComparisonChain;

/**
 * Java object representing marytts voices (components)
 * 
 * @author Jonathan
 * 
 */
public class Component implements Comparable<Component> {

	private String name;
	private Locale locale;
	private String gender;
	private String type;
	private String version;
	private String licenseName;
	private String licenseShortName;
	private String description;
	private Status availabilityState;
	private long size;

	public Component(ModuleDescriptor descriptor) {

		setGender(descriptor.getExtraAttribute("gender"));
		setDescription(descriptor.getDescription());
		setLicenseName(descriptor.getLicenses()[0].getName());
		setLicenseShortName(descriptor.getExtraAttribute("license"));
		setLocale(new Locale(descriptor.getExtraAttribute("locale")));
		setType(descriptor.getExtraAttribute("type"));
		setVersion(descriptor.getAttribute("revision"));
		setName(descriptor.getExtraAttribute("name"));
		
		long parsedLong;
		try {
			parsedLong = Long.parseLong(descriptor.getAllArtifacts()[0].getExtraAttribute("size"));
		} catch (NumberFormatException nfe) {
			System.err.println(descriptor.getAllArtifacts()[0].getExtraAttribute("size") + " could not be parsed.");
			parsedLong = 0L;
		}
		setSize(parsedLong);
		// TODO ONLY FOR TESTING, THIS STATE SHOULD BE DETERMINED BY SOME METHOD
		setAvailabilityState(Status.AVAILABLE);

	}

	/**
	 * @param locale
	 *            the locale to set
	 */
	private void setLocale(Locale locale) {

		if (locale.toString().equalsIgnoreCase("en-us")) {
			locale = Locale.US;
		} else if (locale.toString().equalsIgnoreCase("en-gb")) {
			locale = Locale.UK;
		}
		this.locale = locale;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	private void setGender(String gender) {

		this.gender = gender;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	private void setType(String type) {

		this.type = type;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	private void setVersion(String version) {

		this.version = version;
	}

	/**
	 * @param licenseName
	 *            the licenseName to set
	 */
	private void setLicenseName(String licenseName) {

		this.licenseName = licenseName;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	private void setDescription(String description) {

		this.description = description;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	private void setName(String name) {

		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {

		return this.name;
	}

	/**
	 * @return the locale
	 */
	public Locale getLocale() {

		return this.locale;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {

		return this.gender;
	}

	/**
	 * @return the type
	 */
	public String getType() {

		return this.type;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {

		return this.version;
	}

	/**
	 * @return the licenseName
	 */
	public String getLicenseName() {

		return this.licenseName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {

		return this.description;
	}

	public Status getStatus() {

		return this.availabilityState;
	}

	/**
	 * @param availabilityState
	 *            the availabilityState to set
	 */
	public void setAvailabilityState(Status availabilityState) {
		this.availabilityState = availabilityState;
	}

	/**
	 * @return the licenseShortName
	 */
	public String getLicenseShortName() {
		return this.licenseShortName;
	}

	/**
	 * @param licenseShortName
	 *            the licenseShortName to set
	 */
	private void setLicenseShortName(String licenseShortName) {
		this.licenseShortName = licenseShortName;
	}

	/**
	 * @return the size
	 */
	public long getSize() {
		return this.size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	private void setSize(long size) {
		this.size = size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return "Component [locale=" + this.locale + ", gender=" + this.gender + ", type=" + this.type + ", version="
				+ this.version + ", licenseName=" + this.licenseName + ", description=" + this.description + "]";
	}

	@Override
	public int compareTo(Component o) {

		return ComparisonChain.start().compare(this.locale.toString(), o.getLocale().toString())
				.compare(this.gender, o.getGender()).compare(this.name, o.getName()).result();
	}

	// TODO getter/setter?
	// TODO compareTo/equals...?

}
