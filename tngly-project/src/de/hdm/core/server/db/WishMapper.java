package de.hdm.core.server.db;

import java.sql.*;
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
	  
	  
	  public Wish findByKey(int id) {
		    // DB-Verbindung holen
		    Connection con = DBConnection.connection();

		    try {
		      // Leeres SQL-Statement (JDBC) anlegen
		      Statement stmt = con.createStatement();

		      // Statement ausfüllen und als Query an die DB schicken
		      ResultSet rs = stmt
		          .executeQuery("SELECT id, wishingProfileId, wishedProfileId, timestamp FROM wishes "
		              + "WHERE id=" + id + " ORDER BY id");

		      /*
		       * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
		       * werden. Prüfe, ob ein Ergebnis vorliegt.
		       */
		      if (rs.next()) {
		        // Ergebnis-Tupel in Objekt umwandeln
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

		  public Vector<Wish> findAll() {
		    Connection con = DBConnection.connection();
		    // Ergebnisvektor vorbereiten
		    Vector<Wish> result = new Vector<Wish>();

		    try {
		      Statement stmt = con.createStatement();

		      ResultSet rs = stmt.executeQuery("SELECT id, wishingProfileId, wishedProfileId, timestamp FROM wishes"
		           + "ORDER BY id");

		      // Für jeden Eintrag im Suchergebnis wird nun ein Customer-Objekt
		      // erstellt.
		      while (rs.next()) {
		        Wish w = new Wish();
		        w.setId(rs.getInt("id"));
		        w.setWishingProfileId(rs.getInt("wishingProfileId"));
		        w.setWishedProfileId(rs.getInt("wishedProfileId"));
		        w.setTimestamp(rs.getDate("timestamp"));

		        // Hinzufügen des neuen Objekts zum Ergebnisvektor
		        
		        result.addElement(w);
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    // Ergebnisvektor zurückgeben
		    return result;
		  }

		  public Vector<Wish> findWishedProfiles(int wishingProfileId) {
			  
			    Connection con = DBConnection.connection();
			    // Ergebnisvektor vorbereiten
			    Vector<Wish> result = new Vector<Wish>();

			    try {
			      Statement stmt = con.createStatement();

			      ResultSet rs = stmt.executeQuery("SELECT id, wishingProfileId, wishedProfileId, timestamp FROM wishes"
			           + "WHERE wishingProfileId=" + wishingProfileId + "ORDER BY timestamp");

			      // Für jeden Eintrag im Suchergebnis wird nun ein Customer-Objekt
			      // erstellt.
			      while (rs.next()) {
			        Wish w = new Wish();
			        w.setId(rs.getInt("id"));
			        w.setWishingProfileId(rs.getInt("wishingProfileId"));
			        w.setWishedProfileId(rs.getInt("wishedProfileId"));
			        w.setTimestamp(rs.getDate("timestamp"));

			        // Hinzufügen des neuen Objekts zum Ergebnisvektor
			        
			        result.addElement(w);
			      }
			    }
			    catch (SQLException e) {
			      e.printStackTrace();
			    }

			    // Ergebnisvektor zurückgeben
			    return result;
			  }
		  
		 
		  public Wish insert(Wish w) {
			  
		    Connection con = DBConnection.connection();

		    try {
		      Statement stmt = con.createStatement();

		      /*
		       * Zunächst schauen wir nach, welches der momentan höchste
		       * Primärschlüsselwert ist.
		       */
		      ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
		          + "FROM wishes ");

		      // Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
		      if (rs.next()) {
		        /*
		         * c erhält den bisher maximalen, nun um 1 inkrementierten
		         * Primärschlüssel.
		         */
		        w.setId(rs.getInt("maxid") + 1);

		        stmt = con.createStatement();

		        // Jetzt erst erfolgt die tatsächliche Einfügeoperation
		        stmt.executeUpdate("INSERT INTO wishes (id, wishingProfileId, wishedProfileId, timestamp) "
		            + "VALUES (" + w.getId() + ",'" + w.getWishingProfileId() + "','" + w.getWishedProfileId() + "','" + w.getTimestamp() + "')");
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    return w;
		  }


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

		public Wish edit(Wish w) {
			
			// Diese Methode heißt nur zwecks der Konvention "edit" - aufgrund des inhaltlichen Kontexts macht sie nicht mehr als den timestamp zu aktualisieren.
				    Connection con = DBConnection.connection();

				    try {
				      Statement stmt = con.createStatement();

				      stmt.executeUpdate("UPDATE wishes " + "SET timestamp=\""
				          + w.getTimestamp() 
				          + "WHERE id=" + w.getId());

				    }
				    catch (SQLException e) {
				      e.printStackTrace();
				    }

				    // Um Analogie zu insert(Customer c) zu wahren, geben wir c zurück
				    return w;
				  }

		public void delete(Profile profile) {
			// TODO Auto-generated method stub
			
		}
	  
}
