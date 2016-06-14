package de.hdm.core.server;

import java.util.ArrayList;
import java.util.logging.Logger;

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.shared.CommonSettings;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.ProfileVisit;
import de.hdm.core.shared.bo.SearchProfile;

public class ServersideSettings extends CommonSettings {

	private static final String LOGGER_NAME = "Server";
	private static final Logger log = Logger.getLogger(LOGGER_NAME);
	public static final String PAGE_URL_EDITOR = "http://OUR-APPENGINE-APPNAME.appspot.com";
	public static final String PAGE_URL_REPORT = "http://OUR-APPENGINE-APPNAME.appspot.com/ReportGen.html";

	private static ArrayList<Profile> profilesFoundAndCompared = null;
	
	private static ArrayList<ProfileVisit> profilesVisited = null;
	
	
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
	
	private static ArrayList<Profile> wishlist = null;
	
	private static ArrayList<Profile> banlist = null;
	
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
		ServersideSettings.userProfile = userProfile;
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
		ServersideSettings.searchProfile = searchProfile;
	}
	
	public static ArrayList<Profile> getProfilesFoundAndCompared() {
		return profilesFoundAndCompared;
	}

	public static void setProfilesFoundAndCompared(ArrayList<Profile> profilesFoundAndCompared) {
		ServersideSettings.profilesFoundAndCompared = profilesFoundAndCompared;
	}
	
	public static ArrayList<Profile> getWishlist(){
		return wishlist;
	}

	public static void setWishlist(ArrayList<Profile> wishlist){
		ServersideSettings.wishlist = wishlist;
	}
	
	public static ArrayList<Profile> getBanlist(){
		return banlist;
	}
	
	public static void setBanlist(ArrayList<Profile> banlist){
		ServersideSettings.banlist = banlist;
	}


	public static ArrayList<ProfileVisit> getProfilesVisited() {
		return profilesVisited;
	}

	public static void setProfilesVisited(ArrayList<ProfileVisit> profilesVisited) {
		ServersideSettings.profilesVisited = profilesVisited;
	}

	/**
	 * <p>
	 * Auslesen des applikationsweiten (Server-seitig!) zentralen Loggers.
	 * </p>
	 * 
	 * <h2>Anwendungsbeispiel:</h2> Zugriff auf den Logger herstellen durch:
	 * 
	 * <pre>
	 * Logger logger = ServersideSettings.getLogger();
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
	 * Bitte auf <em>angemessene Log Levels</em> achten! <em>severe</em> und
	 * <em>info</em> sind nur Beispiele.
	 * </p>
	 * 
	 * <h2>HINWEIS:</h2>
	 * <p>
	 * Beachten Sie, dass Sie den auszugebenden Log nun nicht mehr durch
	 * bedarfsweise Einfuegen und Auskommentieren etwa von
	 * <code>System.out.println(...);</code> steuern. Sie belassen kuenftig
	 * saemtliches Logging im Code und koennen ohne abermaliges Kompilieren den
	 * Log Level "von aussen" durch die Datei <code>logging.properties</code>
	 * steuern. Sie finden diese Datei in dem <code>war/WEB-INF</code>-Ordner
	 * Ihres Projekts. Der dort standardmaessig vorgegebene Log Level ist
	 * <code>WARN</code>. Dies wuerde bedeuten, dass Sie keine <code>INFO</code>
	 * -Meldungen wohl aber <code>WARN</code>- und <code>SEVERE</code>-Meldungen
	 * erhielten. Wenn Sie also auch Log des Levels <code>INFO</code> wollten,
	 * m�ssten Sie in dieser Datei <code>.level = INFO</code> setzen.
	 * </p>
	 * 
	 * Weitere Infos siehe Dokumentation zu Java Logging.
	 * 
	 * @return die Logger-Instanz f�r die Server-Seite
	 */
	public static Logger getLogger() {
		return log;
	}

}
