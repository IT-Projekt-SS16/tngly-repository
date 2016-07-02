package de.hdm.core.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.Wish;

/**
 * Die Mapper-Klasse WishMapper stellt eine
 * Schnittstelle zwischen Applikation und Datenbank dar. Die zu persistierenden
 * Profilwünsche werden hier auf eine relationale Ebene projiziert. Die abzurufenden
 * Profilwünsche werden aus den relationalen Tabellen zusammengestellt.
 * 
 * @author Philipp Schmitt
 */
public class WishMapper {
	/**
	 * Instanziieren des Mappers
	 */
		  private static WishMapper wishMapper = null;

		  /**
			 * Mithilfe des <code>protected</code>-Attributs im Konstruktor wird
			 * verhindert, dass von anderen Klassen eine neue Instanz der Klasse
			 * geschaffen werden kann.
			 */
	  protected WishMapper() {
	  }

	  /**
		 * Aufruf eines ProfileBan-Mappers für Klassen, die keinen Zugriff auf den Konstruktor haben.
		 * 
		 * @return Einzigartige Mapper-Instanz zur Benutzung in der Applikationsschicht
		 */
	  public static WishMapper wishMapper() {
	    if (wishMapper == null) {
	    	wishMapper = new WishMapper();
	    }

	    return wishMapper;
	  }
	  
	  /**
		 * Read-Methode - Anhand einer vorgegebenen id wird der dazu gehörige
		 * Wish in der Datenbank gesucht.
		 * 
		 * @author Philipp Schmitt
		 * @param id
		 *            Die id, zu der der Eintrag aus der DB gelesen werden soll
		 * @return Das durch die id referenzierte Wish-Objekt
		 * 
		 */
	  
	  public Wish findByKey(int id) {
		    /**
		     *  DB-Verbindung holen
		     */
		    Connection con = DBConnection.connection();

		    try {
		      /**
		       *  Leeres SQL-Statement (JDBC) anlegen
		       */
		      Statement stmt = con.createStatement();

		      /**
		       *  Statement ausfüllen und als Query an die DB schicken
		       */
		      ResultSet rs = stmt
		          .executeQuery("SELECT id, wishingProfileId, wishedProfileId, timestamp FROM profileWishes "
		              + "WHERE id=" + id + " ORDER BY id");

		      /**
		       * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
		       * werden. Prüfe, ob ein Ergebnis vorliegt.
		       */
		      if (rs.next()) {
		        /**
		         *  Ergebnis-Tupel in Objekt umwandeln
		         */
		        Wish w = new Wish();
		        w.setId(rs.getInt("id"));
		        w.setWishingProfileId(rs.getInt("wishingProfileId"));
		        w.setWishedProfileId(rs.getInt("wishedProfileId"));
		        w.setTimestamp(rs.getDate("timestamp"));

		        return w;
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		      return null;
		    }

		    return null;
		  }
	  
	  /**
		 * Read-Methode - Auslesen aller Wishes in einen Vektor.
		 * 
		 * @author Philipp Schmitt
		 * @return Liste aller derzeit in der Datenbank eingetragenen Profilwünsche
		 */
	  
		  public Vector<Wish> findAll() {
		    Connection con = DBConnection.connection();
		    /**
		     *  Ergebnisvektor vorbereiten
		     */
		    Vector<Wish> result = new Vector<Wish>();

		    try {
		      Statement stmt = con.createStatement();

		      ResultSet rs = stmt.executeQuery("SELECT id, wishingProfileId, wishedProfileId, timestamp FROM profileWishes"
		           + "ORDER BY id");

		      /**
		       *  Für jeden Eintrag im Suchergebnis wird nun ein Wish-Objekt erstellt.
		       */
		      
		      while (rs.next()) {
		        Wish w = new Wish();
		        w.setId(rs.getInt("id"));
		        w.setWishingProfileId(rs.getInt("wishingProfileId"));
		        w.setWishedProfileId(rs.getInt("wishedProfileId"));
		        w.setTimestamp(rs.getDate("timestamp"));

		        /**
		         *  Hinzufügen des neuen Objekts zum Ergebnisvektor
		         */
		        
		        result.addElement(w);
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    /**
		     *  Ergebnisvektor zurückgeben
		     */
		    return result;
		  }

		  /**
			 * Read-Methode - Auslesen aller Wishes (Profilwünsche), die vom
			 * übergebenen Profil je getätigt wurden
			 * 
			 * @author Philipp Schmitt
			 * @param wishingProfileId
			 *            Id des Profils, zu dem alle Profilwünsche gesucht werden
			 *            sollen.
			 * @return Liste aller von dem zu überprüfenden Profil getätigten
			 *         Profilwünsche
			 */
		  
		  public ArrayList<Wish> findWishedProfiles(int wishingProfileId) {
			  
			    Connection con = DBConnection.connection();
			    /**
			     *  Ergebnisvektor vorbereiten
			     */
			    ArrayList<Wish> result = new ArrayList<Wish>();
			    
			    ProfileMapper profileMapper = null;
				  profileMapper = ProfileMapper.profileMapper();
				  
			    try {
			      Statement stmt = con.createStatement();

			      ResultSet rs = stmt.executeQuery("SELECT id, wishingProfileId, wishedProfileId, timestamp FROM profileWishes"
			           + " WHERE wishingProfileId=" + wishingProfileId + " ORDER BY timestamp");

			      /**
			       *  Für jeden Eintrag im Suchergebnis wird nun ein Wish-Objekt erstellt.
			       */
			      
			      while (rs.next()) {
			        Wish w = new Wish();
			        w.setId(rs.getInt("id"));
			        w.setWishingProfileId(rs.getInt("wishingProfileId"));
			        w.setWishedProfileId(rs.getInt("wishedProfileId"));
			        w.setTimestamp(rs.getDate("timestamp"));
			        w.setWishingProfile(profileMapper.findByKey(w.getWishingProfileId()));
			        w.setWishedProfile(profileMapper.findByKey(w.getWishedProfileId()));
			        
			        /**
			         *  Hinzufügen des neuen Objekts zum Ergebnisvektor
			         */
			        
			        result.add(w);
			      }
			    }
			    catch (SQLException e) {
			      e.printStackTrace();
			    }

			    /**
			     *  Ergebnisvektor zurückgeben
			     */
			    return result;
			  }
		  
		  /**
			 * Read-Methode - Abfrage, ob für das übergebene zu checkende Profil ein Profilwunsch durch den Nutzer besteht.
			 * 
			 * @param currentUserProfile
			 *            Profil, das ein anderes Profil gewünscht haben soll
			 * @param otherProfile
			 *            Profil, das gewünscht
			 * @return Boolean, ob das angemeldete Profil das zu checkende Profil
			 *         wünscht
			 */
		  
		  public boolean isProfileWished(int wishingProfileId, int checkedProfileId)	{
				 /**
				  * Datenbankverbindung aufbauen
				  */
				 Connection con = DBConnection.connection();
				 
				 /** 
				  * Vorbereiten der Ergebnisvariable
				  */
				 boolean result = false;
				 
				 try {
					 
					 /**
					  * Erstellung des SQL-Statements
					  */
				      Statement stmt = con.createStatement();

				      /**
				       * Übergabe und Ausführung der Abfrage
				       */
				      ResultSet rs = stmt.executeQuery("SELECT id, wishingProfileId, wishedProfileId, timestamp FROM profileWishes"
				           + " WHERE wishingProfileId=" + wishingProfileId + " AND wishedProfileId=" + checkedProfileId +" ORDER BY timestamp");
				      
				      /**
				       * Check, ob ein Ergebnis zurückkam. Falls ja, ist das Profil gebannt.
				       */
				      if (rs.next())	{
				    	  result = true;
				      }
				      
				 }
				 catch (SQLException e) {
				      e.printStackTrace();
				    }
				 
				 /**
				  * Rückgabe der Ergebnisvariable
				  */
				 return result; 
				 
			 }
		  
		  
		  /**
			 * Insert-Methode - Ein Wish-Objekt w wird übergeben und die
			 * zugehörigen Werte in ein SQL-Statement geschrieben, welches ausgeführt
			 * wird, um das Objekt in die Datenbank einzutragen.
			 * 
			 * @author Philipp Schmitt
			 * @param w
			 *            Profilwunsch, die in die Datenbank geschrieben werden soll
			 * @return Wish-Objekt, das in die Datenbank geschrieben wurde
			 */
		  
		  public Wish insert(Wish w) {
			  
				/**
				 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
				 */
			  
		    Connection con = DBConnection.connection();

		    try {
		      Statement stmt = con.createStatement();

		      /**
		       * Zunächst schauen wir nach, welches der momentan höchste
		       * Primärschlüsselwert ist.
		       */
		      ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
		          + "FROM profileWishes ");

		      /**
		       *  Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
		       */
		      if (rs.next()) {
		        /**
		         * w erhält den bisher maximalen, nun um 1 inkrementierten
		         * Primärschlüssel.
		         */
		        w.setId(rs.getInt("maxid") + 1);

		        stmt = con.createStatement();

				SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd");
				Date currentDate = new Date();
				String date = mySQLformat.format(currentDate);
		        
		        /**
		         *  Jetzt erst erfolgt die tatsächliche Einfügeoperation
		         */
		        stmt.executeUpdate("INSERT INTO profileWishes (id, wishingProfileId, wishedProfileId, timestamp) "
		            + "VALUES (" + w.getId() + ",'" + w.getWishingProfileId() + "','" + w.getWishedProfileId() + "','" + date + "')");
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    return w;
		  }


		  /**
			 * Delete-Methode - Ein Wish-Objekt wird übergeben und dieses aus der
			 * DB gelöscht.
			 * 
			 * @author Philipp Schmitt
			 * @param w
			 *            Profilwunsch, der aufgehoben werden soll
			 */
		  
		public void delete(Wish w) {
		    Connection con = DBConnection.connection();
		
		    try {
		      Statement stmt = con.createStatement();
		      stmt.executeUpdate("DELETE FROM profileWishes " + "WHERE wishingProfileId=" + w.getWishingProfileId() + " AND wishedProfileId=" +w.getWishedProfileId());
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }
		  }

		/**
		 * Delete-Methode zur Profillöschung. Ein Profilobjekt profile soll komplett
		 * aus der Datenbank gelöscht werden und hiermit auch die entsprechenden
		 * Referenzen in der wish-Tabelle.
		 * 
		 * @author Philipp Schmitt
		 * @param profile
		 *            Profil, das komplett aus der Datenbank gelöscht wird
		 */
		
		public void delete(Profile profile) {

			 Connection con = DBConnection.connection();
				
			    try {
			      Statement stmt = con.createStatement();
			
			      stmt.executeUpdate("DELETE FROM profileWishes " + "WHERE wishingProfileId=" + profile.getId());
			    }
			    catch (SQLException e) {
			      e.printStackTrace();
			    }
			
		}
	  
}
