package de.hdm.core.server.db;

import java.sql.Connection;
import java.sql.DriverManager;

import com.google.appengine.api.utils.SystemProperty;
/**
 * Verwalten einer Verbindung zur Datenbank.
 * 
 * <b>Vorteil:</b> Sehr einfacher Verbindungsaufbau zur Datenbank.
 * <p>
 * <b>Nachteil:</b> Durch die Singleton-Eigenschaft der Klasse kann nur auf eine
 * fest vorgegebene Datenbank zugegriffen werden.
 * <p>
 * 
 * Da die Einrichtung von mehreren Datenbank-Verbindungen den Rahmen dieses Projekts
 * sprengen bzw. die Software unnötig verkomplizieren würde, haben wir eine einzige
 * Datenbank mit einer einzigen Verbindung eingerichtet.
 * 
 * Kommentare inspiriert und abgewandelt von @author Thies
 */

public class DBConnection {

    /**
     * Die Klasse DBConnection wird nur einmal instantiiert. Man spricht hierbei
     * von einem sogenannten <b>Singleton</b>.
     * <p>
     * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
     * für sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
     * speichert die einzige Instanz dieser Klasse.
     * 
     */
	
    private static Connection con = null;

    /**
     * Die URL, mit deren Hilfe die Datenbank angesprochen wird. In einer
     * professionellen Applikation würde diese Zeichenkette aus einer
     * Konfigurationsdatei eingelesen oder über einen Parameter von außen
     * mitgegeben, um bei einer Veränderung dieser URL nicht die gesamte
     * Software neu komilieren zu müssen.
     */
    private static String googleUrl = "jdbc:google:mysql://173.194.237.74:3306/tnglyDB?user=root";

    /**
     *   Die localUrl würde normalerweise benutzt werden, um in einer komplett auf dem Rechner
     *   laufenden Entwicklungsumgebung zu testen. Wir haben von Anfang an direkt auf der Live-DB
     *   von Google Cloud SQL entwickelt, um unter möglichst realistischen Bedingungen zu arbeiten.
     */
    
    private static String localUrl = "jdbc:mysql://173.194.237.74:3306/tnglyDB?user=root";

    /**
     * Diese statische Methode kann aufgrufen werden durch
     * <code>DBConnection.connection()</code>. Sie stellt die
     * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine
     * einzige Instanz von <code>DBConnection</code> existiert.
     * <p>
     * 
     * <b>Fazit:</b> DBConnection sollte nicht mittels <code>new</code>
     * instantiiert werden, sondern stets durch Aufruf dieser statischen
     * Methode.
     * <p>
     * 
     * <b>Nachteil:</b> Bei Zusammenbruch der Verbindung zur Datenbank - dies
     * kann z.B. durch ein unbeabsichtigtes Herunterfahren der Datenbank
     * ausgelöst werden - wird keine neue Verbindung aufgebaut, so dass die in
     * einem solchen Fall die gesamte Software neu zu starten ist. In einer
     * robusten Lösung würde man hier die Klasse dahingehend modifizieren, dass
     * bei einer nicht mehr funktionsfähigen Verbindung stets versucht würde,
     * eine neue Verbindung aufzubauen. Dies würde allerdings ebenfalls den
     * Rahmen dieses Projekts sprengen.
     * 
     * @return DAS <code>DBConnection</code>-Objekt.
     * @see con
     */
    
    public static Connection connection() {
    	
        /**
         *  Wenn es bisher keine Conncetion zur DB gab, ...
         */
    	
        if (con == null) {
        	
        	/**
        	 * Je nach Entwicklungsumgebung wird url benutzt, um entweder die Verbindung zur lokalen DB
        	 * oder zur remote DB zu übergeben.
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
                    Class.forName("com.mysql.jdbc.Driver");
                    url = localUrl;
                }

                /**
                 * Hier gibt der DriverManager eine Verbindung, hergestellt durch die angegebene
                 * URL und den User-Account mit vollem Zugriff zurück
                 * 
                 * Diese Verbindung wird dann in der statischen Variable con
                 * abgespeichert und fortan verwendet.
                 */
                con = DriverManager.getConnection(url, user, password);
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
