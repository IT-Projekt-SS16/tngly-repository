package de.hdm.core.client;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.core.server.ServersideSettings;
import de.hdm.core.shared.AdministrationService;
import de.hdm.core.shared.AdministrationServiceAsync;
import de.hdm.core.shared.CommonSettings;
import de.hdm.core.shared.LoginInfo;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.SearchProfile;
import de.hdm.core.shared.bo.User;

public class ClientsideSettings extends CommonSettings {

	/**
	 * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitgen
	 * Dienst namens <code>AdministrationService</code>.
	 */

	private static AdministrationServiceAsync administration = null;
	
	private static ArrayList<Profile> profilesFoundAndCompared = null;
	
	private static ArrayList<Profile> wishlist = null;
	
	private static ArrayList<Profile> banlist = null;
	/**
	 * Instanz des applikationsweit (f�r Client und Server) eindeutigen Profil
	 * des Benutzers.
	 */
	private static Profile userProfile = null;

	/**
	 * Instanz des applikationsweit (f�r Client und Server) eindeutigen Profil
	 * f�r die Partnersuche.
	 */
	private static SearchProfile searchProfile = null;

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
		ClientsideSettings.userProfile = userProfile;
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
	public static SearchProfile getSearchProfile() {
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
	public static void setSearchProfile(SearchProfile searchProfile) {
		ClientsideSettings.searchProfile = searchProfile;
	}


	public static ArrayList<Profile> getProfilesFoundAndCompared() {
		return profilesFoundAndCompared;
	}

	public static void setProfilesFoundAndCompared(ArrayList<Profile> profilesFoundAndCompared) {
		ClientsideSettings.profilesFoundAndCompared = profilesFoundAndCompared;
	}
	
	public static ArrayList<Profile> getWishlist(){
		return wishlist;
	}

	public static void setWishlist(ArrayList<Profile> wishlist){
		ClientsideSettings.wishlist = wishlist;
	}

	public static ArrayList<Profile> getBanlist(){
		return banlist;
	}
	
	public static void setBanlist(ArrayList<Profile> banlist){
		ClientsideSettings.banlist = banlist;
	}
	
	private static LoginInfo loginInfo = null;

	public static LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public static void setLoginInfo(LoginInfo loginInfo) {
		ClientsideSettings.loginInfo = loginInfo;
	}

	private static User currentUser = null;

	/**
	 * Name des Client-seitigen Loggers.
	 */
	private static final String LOGGER_NAME = "Tngly Web Client";

	/**
	 * Instanz des Client-seitigen Loggers.
	 */
	private static final Logger log = Logger.getLogger(LOGGER_NAME);

	/**
	 * <p>
	 * Auslesen des applikationsweiten (Client-seitig!) zentralen Loggers.
	 * </p>
	 * 
	 * <h2>Anwendungsbeispiel:</h2> Zugriff auf den Logger herstellen durch:
	 * 
	 * <pre>
	 * Logger logger = ClientsideSettings.getLogger();
	 * </pre>
	 * 
	 * und dann Nachrichten schreiben etwa mittels
	 * 
	 * <pre>
	 * logger.severe(&quot;Sie sind nicht berechtigt, ...&quot;);
	 * </pre>
	 * 
	 * oder
	 * 
	 * <pre>
	 * logger.info;
	 * </pre>
	 * 
	 * <p>
	 * Bitte auf <em>angemessene Log Levels</em> achten! Severe und info sind
	 * nur Beispiele.
	 * </p>
	 * 
	 * <h2>HINWEIS:</h2>
	 * <p>
	 * Beachten Sie, dass Sie den auszugebenden Log nun nicht mehr durch
	 * bedarfsweise Einfuegen und Auskommentieren etwa von
	 * <code>System.out.println(...);</code> steuern. Sie belassen kuenftig
	 * saemtliches Logging im Code und k�nnen ohne abermaliges Kompilieren den
	 * Log Level "von aussen" durch die Datei <code>logging.properties</code>
	 * steuern. Sie finden diese Datei in Ihrem <code>war/WEB-INF</code>-Ordner.
	 * Der dort standardmaessig vorgegebene Log Level ist <code>WARN</code>.
	 * Dies wuerde bedeuten, dass Sie keine <code>INFO</code>-Meldungen wohl
	 * aber <code>WARN</code>- und <code>SEVERE</code>-Meldungen erhielten. Wenn
	 * Sie also auch Log des Levels <code>INFO</code> wollten, muessten Sie in
	 * dieser Datei <code>.level = INFO</code> setzen.
	 * </p>
	 * 
	 * Weitere Infos siehe Dokumentation zu Java Logging.
	 * 
	 * @return die Logger-Instanz f�r die Server-Seite
	 */
	public static Logger getLogger() {
		return log;
	}

	/**
	 * <p>
	 * Anlegen und Auslesen der applikationsweit eindeutigen
	 * AdministrationService. Diese Methode erstellt die AdministrationService,
	 * sofern sie noch nicht existiert. Bei wiederholtem Aufruf dieser Methode
	 * wird stets das bereits zuvor angelegte Objekt zurueckgegeben.
	 * </p>
	 * 
	 * <p>
	 * Der Aufruf dieser Methode erfolgt im Client z.B. durch
	 * <code>AdministrationServiceAsync administration = ClientsideSettings.getAdministration()</code>
	 * .
	 * </p>
	 * 
	 * @return eindeutige Instanz des Typs
	 *         <code>AdministrationServiceAsync</code>
	 * @author Peter Thies
	 * @since 28.02.2012
	 */
	public static AdministrationServiceAsync getAdministration() {
		// Gab es bislang noch keine Administration-Instanz, dann...
		if (administration == null) {
			// Zun�chst instantiieren wir Administration
			administration = GWT.create(AdministrationService.class);

			final AsyncCallback<Void> initAdministrationCallback = new AsyncCallback<Void>() {
				public void onFailure(Throwable caught) {
					ClientsideSettings.getLogger()
							.severe("Der Administration konnte nicht initialisiert werden!" + caught);
				}

				public void onSuccess(Void result) {
					ClientsideSettings.getLogger().info("Der Administration wurde initialisiert.");
				}
			};

			administration.init(initAdministrationCallback);
		}

		// So, nun brauchen wir die Administration nur noch zur�ckzugeben.

		return administration;
	}

	/**
	 * <p>
	 * R�ckgeben des applikationsweit eindeutigen aktuellen Benutzers. Bei
	 * wiederholtem Aufruf dieser Methode wird stets das bereits zuvor angelegte
	 * Objekt zur�ckgegeben.
	 * </p>
	 * 
	 * @return eindeutige Instanz des Typs <code>User</code>
	 * @author Kevin Jaeger
	 * @since 28.04.2016
	 */
	public static User getCurrentUser() {
		return currentUser;
	}

	/**
	 * <p>
	 * Setzen des applikationsweit eindeutigen aktuellen Benutzers. Bei
	 * wiederholtem Aufruf dieser Methode wird stets das bereits zuvor angelegte
	 * Objekt �berschrieben.
	 * </p>
	 * 
	 * @author Kevin Jaeger
	 * @since 28.04.2016
	 */
	public static void setCurrentUser(User currentUser) {
		ClientsideSettings.currentUser = currentUser;
	}

}
