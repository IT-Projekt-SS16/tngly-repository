package de.hdm.core.server.db;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import de.hdm.core.shared.bo.Wishlist;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.ProfileVisit;

public class ProfileVisitMapper {


	/**
	 * Übernommen & angepasst von: @author Thies
	 */
	
	  public static ProfileVisitMapper getProfileVisitMapper() {
			return profileVisitMapper;
		}

		public static void setProfileVisitMapper(ProfileVisitMapper profileVisitMapper) {
			ProfileVisitMapper.profileVisitMapper = profileVisitMapper;
		}

		  private static ProfileVisitMapper profileVisitMapper = null;

	  protected ProfileVisitMapper() {
	  }


	  public static ProfileVisitMapper profileVisitMapper() {
	    if (profileVisitMapper == null) {
	    	profileVisitMapper = new ProfileVisitMapper();
	    }

	    return profileVisitMapper;
	  }
	  
	  
	  public ProfileVisit findByKey(int id) {
		    // DB-Verbindung holen
		    Connection con = DBConnection.connection();

		    try {
		      // Leeres SQL-Statement (JDBC) anlegen
		      Statement stmt = con.createStatement();

		      // Statement ausfüllen und als Query an die DB schicken
		      ResultSet rs = stmt
		          .executeQuery("SELECT id, visitingProfileId, visitedProfileId, timestamp FROM profileVisits "
		              + "WHERE id=" + id + " ORDER BY id");

		      /*
		       * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
		       * werden. Prüfe, ob ein Ergebnis vorliegt.
		       */
		      if (rs.next()) {
		        // Ergebnis-Tupel in Objekt umwandeln
		        ProfileVisit pv = new ProfileVisit();
		        pv.setId(rs.getInt("id"));
		        pv.setVisitingProfileId(rs.getInt("visitingProfileId"));
		        pv.setVisitedProfileId(rs.getInt("visitedProfileId"));
		        pv.setTimestamp(rs.getDate("timestamp"));

		        return pv;
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		      return null;
		    }

		    return null;
		  }

		  public Vector<ProfileVisit> findAll() {
		    Connection con = DBConnection.connection();
		    // Ergebnisvektor vorbereiten
		    Vector<ProfileVisit> result = new Vector<ProfileVisit>();

		    try {
		      Statement stmt = con.createStatement();

		      ResultSet rs = stmt.executeQuery("SELECT id, visitingProfileId, visitedProfileId, timestamp FROM profileVisits"
		           + "ORDER BY id");

		      // Für jeden Eintrag im Suchergebnis wird nun ein Customer-Objekt
		      // erstellt.
		      while (rs.next()) {
		        ProfileVisit pv = new ProfileVisit();
		        pv.setId(rs.getInt("id"));
		        pv.setVisitingProfileId(rs.getInt("visitingProfileId"));
		        pv.setVisitedProfileId(rs.getInt("visitedProfileId"));
		        pv.setTimestamp(rs.getDate("timestamp"));

		        // Hinzufügen des neuen Objekts zum Ergebnisvektor
		        
		        result.addElement(pv);
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    // Ergebnisvektor zurückgeben
		    return result;
		  }

		  public Vector<ProfileVisit> findVisitedProfiles(int visitingProfileId) {
			  
			    Connection con = DBConnection.connection();
			    // Ergebnisvektor vorbereiten
			    Vector<ProfileVisit> result = new Vector<ProfileVisit>();

			    try {
			      Statement stmt = con.createStatement();

			      ResultSet rs = stmt.executeQuery("SELECT id, visitingProfileId, visitedProfileId, timestamp FROM profileVisits"
			           + "WHERE visitingProfileId=" + visitingProfileId + "ORDER BY timestamp");

			      // Für jeden Eintrag im Suchergebnis wird nun ein Customer-Objekt
			      // erstellt.
			      while (rs.next()) {
			        ProfileVisit pv = new ProfileVisit();
			        pv.setId(rs.getInt("id"));
			        pv.setVisitingProfileId(rs.getInt("visitingProfileId"));
			        pv.setVisitedProfileId(rs.getInt("visitedProfileId"));
			        pv.setTimestamp(rs.getDate("timestamp"));

			        // Hinzufügen des neuen Objekts zum Ergebnisvektor
			        
			        result.addElement(pv);
			      }
			    }
			    catch (SQLException e) {
			      e.printStackTrace();
			    }

			    // Ergebnisvektor zurückgeben
			    return result;
			  }
		  
		 
		  public ProfileVisit insert(ArrayList<ProfileVisit> visitedProfiles) {
			  
		    Connection con = DBConnection.connection();

		    try {
		      Statement stmt = con.createStatement();

		      /*
		       * Zunächst schauen wir nach, welches der momentan höchste
		       * Primärschlüsselwert ist.
		       */
		      ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
		          + "FROM profileVisits ");

		      // Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
		      if (rs.next()) {
		        /*
		         * c erhält den bisher maximalen, nun um 1 inkrementierten
		         * Primärschlüssel.
		         */
		        visitedProfiles.setId(rs.getInt("maxid") + 1);

		        stmt = con.createStatement();

				SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd");
				Date currentDate = new Date();
				String date = mySQLformat.format(currentDate);
				
				// insert Date as current timestamp yyyy-MM-dd, NICHT VERGESSEN!
		        
		        // Jetzt erst erfolgt die tatsächliche Einfügeoperation
		        stmt.executeUpdate("INSERT INTO profileVisits (id, visitingProfileId, visitedProfileId, timestamp) "
		            + "VALUES (" + visitedProfiles.getId() + ",'" + visitedProfiles.getVisitingProfileId() + "','" + visitedProfiles.getVisitedProfileId() + "','" + date + "')");
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    return visitedProfiles;
		  }


		  public ProfileVisit edit(ProfileVisit pv) {
			
			// Diese Methode heißt nur zwecks der Konvention "edit" - aufgrund des inhaltlichen Kontexts macht sie nicht mehr als den timestamp zu aktualisieren.
				    Connection con = DBConnection.connection();

				    try {
				      Statement stmt = con.createStatement();

						SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd");
						Date currentDate = new Date();
						String date = mySQLformat.format(currentDate);
						
						// insert Date as current timestamp yyyy-MM-dd, NICHT VERGESSEN!
				      
				      stmt.executeUpdate("UPDATE profileVisits " + "SET timestamp=\""
				          + date
				          + "WHERE id=" + pv.getId());

				    }
				    catch (SQLException e) {
				      e.printStackTrace();
				    }

				    // Um Analogie zu insert(Customer c) zu wahren, geben wir c zurück
				    return pv;
				  }

		public void delete(ArrayList<ProfileVisit> visitedProfiles) {
		    Connection con = DBConnection.connection();
		
		    try {
		      Statement stmt = con.createStatement();
		
		      stmt.executeUpdate("DELETE FROM profileVisits " + "WHERE id=" + visitedProfiles.getId());
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }
		  }

		public void delete(Profile profile) {

			 Connection con = DBConnection.connection();
				
			    try {
			      Statement stmt = con.createStatement();
			
			      stmt.executeUpdate("DELETE FROM profileVisits " + "WHERE visitingProfileId=" + profile.getId());
			    }
			    catch (SQLException e) {
			      e.printStackTrace();
			    }
			
		}

		public Boolean wasProfileVisited(Profile p) {
			// TODO �bergebene Profil mit ID auf Spalte "VisitedProfileID" in DB pr�fen und falls in Spalte vorhanden: "true" zur�ckgeben
			return null;
		}
}
