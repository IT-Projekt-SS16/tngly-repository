package de.hdm.core.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;

import com.google.appengine.api.utils.SystemProperty;
/**
 * Verwalten einer Verbindung zur Datenbank.
 * 
 * @author Philipp Schmitt
 */

public class DBConnection {

    /**
     * Instantiieren der Connection und Festlegung der Einzigartigkeit durch <code>static</code>
     */
	
    private static Connection con = null;

    /**
     * Die URL für die Google Cloud-SQL Datenbank - angesprochen übern die Projekt-, sowie Instanz-ID.
     * In deploytem Status soll die Datenbank von der Application nur über einen root-Zugang und den entsprechenden Google-Treibern angesprochen werden.
     */
    private static String googleUrl = "jdbc:google:mysql://our-lacing-132223:tngly/tnglyDB?user=root";

    /**
     * Aktivieren des Loggers zum Debugging.
     */
    
    private static Logger logger = Logger.getLogger("Tngly Web Client");
    
    /**
     *   Die URL für die Google Cloud-SQL Datenbank zum Ansteuern der Datenbank von einer
     *   lokalen Entwicklungsumgebung (nicht <code>deployed</code>). Hier muss die Datenbank über die
     *   bereitgestellte IPv4-Adresse sowie einen eingerichteten Zugang angesprochen werden.
     */
    
    private static String localUrl = "jdbc:mysql://173.194.237.74:3306/tnglyDB";

    /**
     * Statische Methode zum Aufruf der <code>DBConnection.connection()</code>.
     * Weitere Sicherstellung der Einzigartigkeit einer DBConnection-Instanz.
     * 
     * @author Philipp Schmitt
     * @return DAS <code>DBConnection</code>-Objekt.
     * @see con
     */
    
    public static Connection connection() {
    	
        /**
         *  Prüfen, ob es bisher eine Connection aufgebaut ist.
         */
    	
        if (con == null) {
        	
        	/**
        	 * Je nach Entwicklungsumgebung wird url benutzt, um entweder die Verbindung von der lokalen Entwicklungsumgebung
        	 * oder der deployten Applikation aufzubauen.
        	 */
        	
            String url = null;
            
            /**
             * Zugangsdaten für einen Nicht-root-Account zur Cloud SQL, der vollen Zugriff auf die DB hat.
             */
    
             String user = "phil";
             String password = "goat";    
            
            try {
                if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
                    /** Load the class that provides the new
                     *	"jdbc:google:mysql://" prefix.
                     */
                    Class.forName("com.mysql.jdbc.GoogleDriver");
                    url = googleUrl;

                } else {
                    /**
                     *  Local MySQL instance to use during development.
                     */
                    Class.forName("com.mysql.jdbc.GoogleDriver");
                    url = googleUrl;
                }

                // Für Local development wieder url, user, password, driver und url anpassen
                /**
                 * Hier gibt der DriverManager eine Verbindung, hergestellt durch die angegebene
                 * URL und den User-Account mit vollem Zugriff, zurück
                 * 
                 * Diese Verbindung wird dann in der statischen Variable con
                 * abgespeichert und fortan verwendet.
                 */
              // Für Deployment hier nur (url) übergeben und in if/else jeweils GoogleDriver und local
                con = DriverManager.getConnection(url);

            } 
            
            /**
             * Falls die Verbindung fehlschlägt, soll die dazugehörige Exception ausgegeben werden.
             */
           
            catch (Exception e) {
                con = null;
                e.printStackTrace();
            }
        }

        /**
         *  Zurückgegeben der Verbindung
         */
        return con;
    }

}
