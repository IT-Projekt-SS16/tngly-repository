package de.hdm.core.server.db;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import de.hdm.core.shared.bo.Wish;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.Wish;

public class WishMapper {


	/**
	 * Übernommen & angepasst von: @author Thies
	 */
	
	  public static WishMapper getWishMapper() {
			return wishMapper;
		}

		public static void setWishMapper(WishMapper wishMapper) {
			WishMapper.wishMapper = wishMapper;
		}

		  private static WishMapper wishMapper = null;

	  protected WishMapper() {
	  }


	  public static WishMapper wishMapper() {
	    if (wishMapper == null) {
	    	wishMapper = new WishMapper();
	    }

	    return wishMapper;
	  }
	  
		/**
		 * FindByKey-Methode. Hierbei wird ein Wish-Objekt anhand der ID gefunden und zurückgegeben.
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
		          .executeQuery("SELECT id, wishingProfileId, wishedProfileId, timestamp FROM wishes "
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
		 * FindAll-Methode. Hierbei werden in einem Vektor alle ProfileVisit-Objekte ausgegeben.
		 */
	  
		  public Vector<Wish> findAll() {
		    Connection con = DBConnection.connection();
		    /**
		     *  Ergebnisvektor vorbereiten
		     */
		    Vector<Wish> result = new Vector<Wish>();

		    try {
		      Statement stmt = con.createStatement();

		      ResultSet rs = stmt.executeQuery("SELECT id, wishingProfileId, wishedProfileId, timestamp FROM wishes"
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
			 * FindWishedProfiles-Methode. Hierbei werden durch die ID eines Profiles alle dazu gehörigen
			 * Wish-Objekte gefunden und zurückgegeben.
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

			      ResultSet rs = stmt.executeQuery("SELECT id, wishingProfileId, wishedProfileId, timestamp FROM wishes"
			           + "WHERE wishingProfileId=" + wishingProfileId + "ORDER BY timestamp");

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
			 * Insert-Methode. Ein Wish-Objekt w wird übergeben und die zugehörigen Werte
			 * in ein SQL-Statement geschrieben, welches ausgeführt wird, um das Objekt in die Datenbank einzutragen.
			 * 
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
		          + "FROM wishes ");

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
				
				// insert Date as current timestamp yyyy-MM-dd, NICHT VERGESSEN!
		        
		        /**
		         *  Jetzt erst erfolgt die tatsächliche Einfügeoperation
		         */
		        stmt.executeUpdate("INSERT INTO wishes (id, wishingProfileId, wishedProfileId, timestamp) "
		            + "VALUES (" + w.getId() + ",'" + w.getWishingProfileId() + "','" + w.getWishedProfileId() + "','" + date + "')");
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    return w;
		  }

			/**
			 *  Edit-Methode. Diese Methode heißt nur zwecks der Konvention "edit" -
			 *  aufgrund des inhaltlichen Kontexts macht sie nicht mehr als den timestamp zu aktualisieren.
			 * @param w
			 * @return
			 */

		  public Wish edit(Wish w) {
			
				/**
				 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
				 */
			  
				    Connection con = DBConnection.connection();

				    try {
				      Statement stmt = con.createStatement();
				      
						SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd");
						Date currentDate = new Date();
						String date = mySQLformat.format(currentDate);
						
						/**
						 *  insert Date as current timestamp yyyy-MM-dd, NICHT VERGESSEN!
						 */
				      
				      stmt.executeUpdate("UPDATE wishes " + "SET timestamp=\""
				          + date
				          + "WHERE id=" + w.getId());

				    }
				    catch (SQLException e) {
				      e.printStackTrace();
				    }

				    /**
				     *  Um Analogie zu insert(Wish w) zu wahren, geben wir w zurück
				     */
				    return w;
				  }

			/** 
			 *  Delete-Methode. Ein Wish-Objekt wird übergeben und dieses aus der DB gelöscht.
			 */
		  
		public void delete(Wish w) {
		    Connection con = DBConnection.connection();
		
		    try {
		      Statement stmt = con.createStatement();
		
		      stmt.executeUpdate("DELETE FROM wishes " + "WHERE id=" + w.getId());
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }
		  }

		/**
		 * Delete-Methode für Profile. Ein Profilobjekt profile wird übergeben und die zugehörigen Werte
		 * in ein SQL-Statement geschrieben, welches ausgeführt wird, um alle Wish-Objekte zu entfernen,
		 * die mit diesem Profil verknüpft sind.
		 * 
		 */	
		
		public void delete(Profile profile) {

			 Connection con = DBConnection.connection();
				
			    try {
			      Statement stmt = con.createStatement();
			
			      stmt.executeUpdate("DELETE FROM wishes " + "WHERE wishingProfileId=" + profile.getId());
			    }
			    catch (SQLException e) {
			      e.printStackTrace();
			    }
			
		}
	  
}
