package de.hdm.core.server;

import java.util.logging.Logger;

import de.hdm.core.shared.CommonSettings;

public class ServersideSettings extends CommonSettings {

	private static final String LOGGER_NAME = "Server";
	private static final Logger log = Logger.getLogger(LOGGER_NAME);

	/**
	 * Gibt den applikationsweiten (Server-seitig!) zentralen Logger zurueck.
	 * Weitere Infos siehe Dokumentation zu Java Logging.
	 * 
	 * @author Kevin Jaeger
	 * @return die Logger-Instanz fuer die Server-Seite
	 */
	public static Logger getLogger() {
		return log;
	}

}
