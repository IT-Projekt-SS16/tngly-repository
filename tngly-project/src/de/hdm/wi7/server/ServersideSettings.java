package de.hdm.wi7.server;

import java.util.logging.Logger;

import de.hdm.wi7.shared.CommonSettings;

public class ServersideSettings extends CommonSettings {

	private static final String LOGGER_NAME = "Server";
	private static final Logger log = Logger.getLogger(LOGGER_NAME);
	public static final String PAGE_URL_EDITOR = "http://OUR-APPENGINE-APPNAME.appspot.com";
	public static final String PAGE_URL_REPORT = "http://OUR-APPENGINE-APPNAME.appspot.com/ReportGen.html";

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
