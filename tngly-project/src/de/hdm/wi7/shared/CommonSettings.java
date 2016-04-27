package de.hdm.wi7.shared;

public class CommonSettings {
	private static Profile userProfile = null;
	
	private static Profile searchProfile = null;

	public static Profile getUserProfile() {
		return userProfile;
	}

	public static void setUserProfile(Profile userProfile) {
		CommonSettings.userProfile = userProfile;
	}

	public static Profile getSearchProfile() {
		return searchProfile;
	}

	public static void setSearchProfile(Profile searchProfile) {
		CommonSettings.searchProfile = searchProfile;
	}

}
