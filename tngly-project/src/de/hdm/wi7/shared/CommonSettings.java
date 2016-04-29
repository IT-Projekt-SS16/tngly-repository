package de.hdm.wi7.shared;

public class CommonSettings {
	/**
	 * Instanz des applikationsweit (f�r Client und Server) eindeutigen Profil
	 * des Benutzers.
	 */
	private static Profile userProfile = null;

	/**
	 * Instanz des applikationsweit (f�r Client und Server) eindeutigen Profil
	 * f�r die Partnersuche.
	 */
	private static Profile searchProfile = null;

	/**
	 * <p>
	 * R�ckgeben des applikationsweit (f�r Client und Server) eindeutigen Profil
	 * des Benutzers. Bei wiederholtem Aufruf dieser Methode wird stets das
	 * bereits zuvor angelegte Objekt zurueckgegeben.
	 * </p>
	 * 
	 * @return eindeutige Instanz des Typs <code>Profile</code>
	 * @author Kevin Jaeger
	 * @since 28.04.2016
	 */
	public static Profile getUserProfile() {
		return userProfile;
	}

	/**
	 * <p>
	 * Setzen des applikationsweit (f�r Client und Server) eindeutigen Profil
	 * des Benutzers. Bei wiederholtem Aufruf dieser Methode wird stets das
	 * bereits zuvor angelegte Objekt ueberschrieben.
	 * </p>
	 * 
	 * @author Kevin Jaeger
	 * @since 28.04.2016
	 */
	public static void setUserProfile(Profile userProfile) {
		CommonSettings.userProfile = userProfile;
	}

	/**
	 * <p>
	 * R�ckgeben des applikationsweit (f�r Client und Server) eindeutigen Profil
	 * f�r die Partnersuche. Bei wiederholtem Aufruf dieser Methode wird stets
	 * das bereits zuvor angelegte Objekt zurueckgegeben.
	 * </p>
	 * 
	 * @return eindeutige Instanz des Typs <code>Profile</code>
	 * @author Kevin Jaeger
	 * @since 28.04.2016
	 */
	public static Profile getSearchProfile() {
		return searchProfile;
	}

	/**
	 * <p>
	 * Setzen des applikationsweit (f�r Client und Server) eindeutigen Profil
	 * f�r die Partnersuche. Bei wiederholtem Aufruf dieser Methode wird stets
	 * das bereits zuvor angelegte Objekt ueberschrieben.
	 * </p>
	 * 
	 * @author Kevin Jaeger
	 * @since 28.04.2016
	 */
	public static void setSearchProfile(Profile searchProfile) {
		CommonSettings.searchProfile = searchProfile;
	}

}
