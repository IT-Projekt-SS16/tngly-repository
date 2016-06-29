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
	  
		/**
		 * FindByKey-Methode. Hierbei wird ein ProfileVisit-Objekt anhand der ID gefunden und zurückgegeben.
		 */
	  
	  public ProfileVisit findByKey(int id) {
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
		       * Statement ausfüllen und als Query an die DB schicken
		       */
		      ResultSet rs = stmt
		          .executeQuery("SELECT id, visitingProfileId, visitedProfileId, timestamp FROM profileVisits "
		              + "WHERE id=" + id + " ORDER BY id");

		      /**
		       * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
		       * werden. Prüfe, ob ein Ergebnis vorliegt.
		       */
		      if (rs.next()) {
		        /**
		         *  Ergebnis-Tupel in Objekt umwandeln
		         */
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

		/**
		 * FindAll-Methode. Hierbei werden in einem Vektor alle ProfileVisit-Objekte ausgegeben.
		 */
	  
		  public Vector<ProfileVisit> findAll() {
		    Connection con = DBConnection.connection();
		    /**
		     *  Ergebnisvektor vorbereiten
		     */
		    Vector<ProfileVisit> result = new Vector<ProfileVisit>();

		    try {
		    	
				/**
				 * Erzeugen eines neuen SQL-Statements.
				 */
				
		      Statement stmt = con.createStatement();

				/**
				 *  Statement ausfüllen und als Query an die DB schicken.
				 */
		      
		      ResultSet rs = stmt.executeQuery("SELECT id, visitingProfileId, visitedProfileId, timestamp FROM profileVisits"
		           + "ORDER BY id");

		      /**
		       * Für jeden Eintrag im Suchergebnis wird nun ein ProfileVisit-Objekt erstellt.
		       */
		      
		      while (rs.next()) {
		        ProfileVisit pv = new ProfileVisit();
		        pv.setId(rs.getInt("id"));
		        pv.setVisitingProfileId(rs.getInt("visitingProfileId"));
		        pv.setVisitedProfileId(rs.getInt("visitedProfileId"));
		        pv.setTimestamp(rs.getDate("timestamp"));

		        /**
		         *  Hinzufügen des neuen Objekts zum Ergebnisvektor
		         */
		        
		        result.addElement(pv);
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
			 * FindVisitedProfiles-Methode. Hierbei wereden durch die ID eines Profiles alle dazu gehörigen
			 * Profilbesuche gefunden und zurückgegeben.
			 */
		  
		  public ArrayList<ProfileVisit> findVisitedProfiles(int visitingProfileId) {
			  
				/**
				 *  DB-Verbindung holen.
				 */
			  
			    Connection con = DBConnection.connection();
			    /**
			     *  Ergebnisvektor vorbereiten
			     */
			    ArrayList<ProfileVisit> result = new ArrayList<ProfileVisit>();

			    try {
			      Statement stmt = con.createStatement();

			      /**
			       * Erzeugen eines neuen SQL-Statements.
			       */
			      
			      ResultSet rs = stmt.executeQuery("SELECT id, visitingProfileId, visitedProfileId, timestamp FROM profileVisits"
			           + "WHERE visitingProfileId=" + visitingProfileId + "ORDER BY timestamp");

			      /**
			       *  Für jeden Eintrag im Suchergebnis wird nun ein ProfileVisit-Objekt erstellt.
			       */
			      
			      while (rs.next()) {
			        ProfileVisit pv = new ProfileVisit();
			        pv.setId(rs.getInt("id"));
			        pv.setVisitingProfileId(rs.getInt("visitingProfileId"));
			        pv.setVisitedProfileId(rs.getInt("visitedProfileId"));
			        pv.setTimestamp(rs.getDate("timestamp"));

			        /**
			         *  Hinzufügen des neuen Objekts zum Ergebnisvektor
			         */
			        
			        result.add(pv);
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
			 * Insert-Methode. Eine ArrayList mit ProfileVisit-Objekten visitedProfiles wird übergeben und die zugehörigen Werte
			 * in ein SQL-Statement geschrieben, welches ausgeführt wird, um die Liste die Datenbank einzutragen.
			 * 
			 */
		 
		  public void insert(ArrayList<ProfileVisit> visitedProfiles) {
			  
				/**
				 * DB-Verbindung holen 
				 */
			  
		    Connection con = DBConnection.connection();

		      for (ProfileVisit p: visitedProfiles){
		    
		    try {
		    	/**
		    	 * Erzeugen eines neuen SQL-Statements.
		    	 */
			      Statement stmt = con.createStatement();

		      /**
		       * Zunächst schauen wir nach, welches der momentan höchste
		       * Primärschlüsselwert ist.
		       */
		      ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
		          + "FROM profileVisits ");

		      /** 
		       * Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein		       * 
		       */
		      if (rs.next()) {
		        /**
		         * p erhält den bisher maximalen, nun um 1 inkrementierten
		         * Primärschlüssel.
		         */
		        p.setId(rs.getInt("maxid") + 1);

		        stmt = con.createStatement();

				SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd");
				Date currentDate = new Date();
				String date = mySQLformat.format(currentDate);
				
				// insert Date as current timestamp yyyy-MM-dd, NICHT VERGESSEN!
		        
		        /**
		         *  Jetzt erst erfolgt die tatsächliche Einfügeoperation
		         */
		        stmt.executeUpdate("INSERT INTO profileVisits (id, visitingProfileId, visitedProfileId, timestamp) "
		            + "VALUES (" + p.getId() + ",'" + p.getVisitingProfileId() + "','" + p.getVisitedProfileId() + "','" + date + "')");
		      }
		    }
		    
		    catch (SQLException e) {
		      e.printStackTrace();
		    
		  }
		      }
		  }
		  
			/**
			 *  Edit-Methode. Diese Methode heißt nur zwecks der Konvention "edit" -
			 *  aufgrund des inhaltlichen Kontexts macht sie nicht mehr als den timestamp zu aktualisieren.
			 */

		  public ProfileVisit edit(ProfileVisit pv) {
			
				/**
				 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
				 */
			  
				    Connection con = DBConnection.connection();

				    try {
				      Statement stmt = con.createStatement();

						SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd");
						Date currentDate = new Date();
						String date = mySQLformat.format(currentDate);
										      
				      stmt.executeUpdate("UPDATE profileVisits " + "SET timestamp=\""
				          + date
				          + "WHERE id=" + pv.getId());

				    }
				    catch (SQLException e) {
				      e.printStackTrace();
				    }

				    /**
				     *  Um Analogie zu insert(ProfileVisit pv) zu wahren, geben wir pv zurück
				     */
				    return pv;
				  }

			/**
			 * Delete-Methode für ProfileVisits. Eine ArrayList visitedProfiles wird übergeben und die zugehörigen Werte
			 * in ein SQL-Statement geschrieben, welches ausgeführt wird, um alle enthaltenen ProfileVisits zu entfernen.
			 * 
			 */	
		  
		public void delete(ArrayList<ProfileVisit> visitedProfiles) {
		    Connection con = DBConnection.connection();
		
		    for (ProfileVisit p: visitedProfiles){
		    
		    try {
		      Statement stmt = con.createStatement();
		
		      stmt.executeUpdate("DELETE FROM profileVisits " + "WHERE id=" + p.getId());
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }
		  }
		}
		
		/**
		 * Delete-Methode für Profile. Ein Profilobjekt profile wird übergeben und die zugehörigen Werte
		 * in ein SQL-Statement geschrieben, welches ausgeführt wird, um alle ProfileVisit-Objekte zu entfernen,
		 * die mit diesem Profil verknüpft sind.
		 * 
		 */	
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

		public Boolean wasProfileVisited(Profile currentUserProfile, Profile otherProfile) {
			
			  /**
			   *  DB-Verbindung holen
			   */
		    Connection con = DBConnection.connection();
		    Vector<ProfileVisit> result = new Vector<ProfileVisit>();
		    
		    try {
		      /**
		       * Leeres SQL-Statement (JDBC) anlegen
		       */
		      Statement stmt = con.createStatement();

		      /**
		       *  Statement ausfüllen und als Query an die DB schicken
		       */
		      ResultSet rs = stmt.executeQuery("SELECT id, visitingProfileId, visitedProfileId, timestamp FROM profileVisits "
		              + "WHERE visitedProfileId=" + otherProfile.getId() + " AND " + "visitingProfileId=" + currentUserProfile.getId());
		      
		      while (rs.next()) {
			        ProfileVisit pv = new ProfileVisit();
			        pv.setId(rs.getInt("id"));
			        pv.setVisitingProfileId(rs.getInt("visitingProfileId"));
			        pv.setVisitedProfileId(rs.getInt("visitedProfileId"));
//			        pv.setTimestamp(rs.getDate("timestamp"));

			        /**
			         *  Hinzufügen des neuen Objekts zum Ergebnisvektor
			         */
			        
			        result.addElement(pv);
			      }
		    }
		      
			    catch (SQLException e) {
				      e.printStackTrace();
				      return null;
				    }
		    
		    if (result.isEmpty())	{
		    	return false;
		    }
		    
		    else {return true;}
		    
		    
		}
}     