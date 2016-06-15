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
import de.hdm.core.shared.bo.ProfileBan;

public class ProfileBanMapper {


	/**
	 * Übernommen & angepasst von: @author Thies
	 */
	
	  public static ProfileBanMapper getProfileBanMapper() {
			return profileBanMapper;
		}

		public static void setProfileBanMapper(ProfileBanMapper profileBanMapper) {
			ProfileBanMapper.profileBanMapper = profileBanMapper;
		}

		  private static ProfileBanMapper profileBanMapper = null;

	  protected ProfileBanMapper() {
	  }


	  public static ProfileBanMapper profileBanMapper() {
	    if (profileBanMapper == null) {
	    	profileBanMapper = new ProfileBanMapper();
	    }

	    return profileBanMapper;
	  }
	  
	  
	  public ProfileBan findByKey(int id) {
		    // DB-Verbindung holen
		    Connection con = DBConnection.connection();

		    try {
		      // Leeres SQL-Statement (JDBC) anlegen
		      Statement stmt = con.createStatement();

		      // Statement ausfüllen und als Query an die DB schicken
		      ResultSet rs = stmt
		          .executeQuery("SELECT id, banningProfileId, bannedProfileId, timestamp FROM profileBans "
		              + "WHERE id=" + id + " ORDER BY id");

		      /*
		       * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
		       * werden. Prüfe, ob ein Ergebnis vorliegt.
		       */
		      if (rs.next()) {
		        // Ergebnis-Tupel in Objekt umwandeln
		        ProfileBan pb = new ProfileBan();
		        pb.setId(rs.getInt("id"));
		        pb.setBanningProfileId(rs.getInt("banningProfileId"));
		        pb.setBannedProfileId(rs.getInt("bannedProfileId"));
		        pb.setTimestamp(rs.getDate("timestamp"));

		        return pb;
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		      return null;
		    }

		    return null;
		  }

		  public Vector<ProfileBan> findAll() {
		    Connection con = DBConnection.connection();
		    // Ergebnisvektor vorbereiten
		    Vector<ProfileBan> result = new Vector<ProfileBan>();

		    try {
		      Statement stmt = con.createStatement();

		      ResultSet rs = stmt.executeQuery("SELECT id, banningProfileId, bannedProfileId, timestamp FROM profileBans"
		           + "ORDER BY id");

		      // Für jeden Eintrag im Suchergebnis wird nun ein Customer-Objekt
		      // erstellt.
		      while (rs.next()) {
		        ProfileBan pb = new ProfileBan();
		        pb.setId(rs.getInt("id"));
		        pb.setBanningProfileId(rs.getInt("banningProfileId"));
		        pb.setBannedProfileId(rs.getInt("bannedProfileId"));
		        pb.setTimestamp(rs.getDate("timestamp"));

		        // Hinzufügen des neuen Objekts zum Ergebnisvektor
		        
		        result.addElement(pb);
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    // Ergebnisvektor zurückgeben
		    return result;
		  }

		  public ArrayList<ProfileBan> findBannedProfiles(int banningProfileId) {
			  
			    Connection con = DBConnection.connection();
			    // Ergebnisvektor vorbereiten
			    ArrayList<ProfileBan> result = new ArrayList<ProfileBan>();

			    ProfileMapper profileMapper = null;
				  profileMapper = ProfileMapper.profileMapper();
			    
			    try {
			      Statement stmt = con.createStatement();

			      ResultSet rs = stmt.executeQuery("SELECT id, banningProfileId, bannedProfileId, timestamp FROM profileBans"
			           + "WHERE banningProfileId=" + banningProfileId + "ORDER BY timestamp");

			      // Für jeden Eintrag im Suchergebnis wird nun ein Customer-Objekt
			      // erstellt.
			      while (rs.next()) {
			        ProfileBan pb = new ProfileBan();
			        pb.setId(rs.getInt("id"));
			        pb.setBanningProfileId(rs.getInt("banningProfileId"));
			        pb.setBannedProfileId(rs.getInt("bannedProfileId"));
			        pb.setTimestamp(rs.getDate("timestamp"));
			        pb.setBanningProfile(profileMapper.findByKey(pb.getBanningProfileId()));
			        pb.setBannedProfile(profileMapper.findByKey(pb.getBanningProfileId()));
			        // Hinzufügen des neuen Objekts zum Ergebnisvektor
			        
			        result.add(pb);
			      }
			    }
			    catch (SQLException e) {
			      e.printStackTrace();
			    }

			    // Ergebnisvektor zurückgeben
			    return result;
			  }
		  
		 
		  public ProfileBan insert(ProfileBan pb) {
			  
		    Connection con = DBConnection.connection();

		    try {
		      Statement stmt = con.createStatement();

		      /*
		       * Zunächst schauen wir nach, welches der momentan höchste
		       * Primärschlüsselwert ist.
		       */
		      ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
		          + "FROM profileBans ");

		      // Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
		      if (rs.next()) {
		        /*
		         * c erhält den bisher maximalen, nun um 1 inkrementierten
		         * Primärschlüssel.
		         */
		        pb.setId(rs.getInt("maxid") + 1);

		        stmt = con.createStatement();
		        
				SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd");
				Date currentDate = new Date();
				String date = mySQLformat.format(currentDate);
		        
		        // Jetzt erst erfolgt die tatsächliche Einfügeoperation
		        stmt.executeUpdate("INSERT INTO profileBans (id, banningProfileId, bannedProfileId, timestamp) "
		            + "VALUES (" + pb.getId() + ",'" + pb.getBanningProfileId() + "','" + pb.getBannedProfileId() + "','" + date + "')");
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    return pb;
		  }


		  public ProfileBan edit(ProfileBan pb) {
			
			// Diese Methode heißt nur zwecks der Konvention "edit" - aufgrund des inhaltlichen Kontexts macht sie nicht mehr als den timestamp zu aktualisieren.
				    Connection con = DBConnection.connection();

				    try {
				      Statement stmt = con.createStatement();

						SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd");
						Date currentDate = new Date();
						String date = mySQLformat.format(currentDate);
						
						// insert Date as current timestamp yyyy-MM-dd, NICHT VERGESSEN!
				      
				      stmt.executeUpdate("UPDATE profileBans " + "SET timestamp=\""
				          + date
				          + "WHERE id=" + pb.getId());

				    }
				    catch (SQLException e) {
				      e.printStackTrace();
				    }

				    // Um Analogie zu insert(Customer c) zu wahren, geben wir c zurück
				    return pb;
				  }

		public void delete(ProfileBan pb) {
		    Connection con = DBConnection.connection();
		
		    try {
		      Statement stmt = con.createStatement();
		
		      stmt.executeUpdate("DELETE FROM profileBans " + "WHERE id=" + pb.getId());
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }
		  }

		public void delete(Profile profile) {
			
			 Connection con = DBConnection.connection();
				
			    try {
			      Statement stmt = con.createStatement();
			
			      stmt.executeUpdate("DELETE FROM profileBans " + "WHERE banningProfileId=" + profile.getId());
			    }
			    catch (SQLException e) {
			      e.printStackTrace();
			    }
			
		}
}
