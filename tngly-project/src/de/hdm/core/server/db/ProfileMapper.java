package de.hdm.core.server.db;

import java.sql.*;
import java.util.Vector;

import de.hdm.core.shared.*;
import de.hdm.core.shared.bo.Profile;

/**
 * Übernommen & angepasst von: @author Thies
 */

public class ProfileMapper {

  private static ProfileMapper profileMapper = null;

  protected ProfileMapper() {
  }

  public static ProfileMapper profileMapper() {
    if (profileMapper == null) {
      profileMapper = new ProfileMapper();
    }

    return profileMapper;
  }

 
  public Profile findByKey(int id) {
    // DB-Verbindung holen
    Connection con = DBConnection.connection();

    try {
      // Leeres SQL-Statement (JDBC) anlegen
      Statement stmt = con.createStatement();

      // Statement ausfüllen und als Query an die DB schicken
      ResultSet rs = stmt
          .executeQuery("SELECT id, userName, name, lastName, dateOfBirth,"
          		+ " gender, bodyHeight, hairColour, confession, isSmoking FROM profiles "
              + "WHERE id=" + id + " ORDER BY lastName");

      /*
       * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
       * werden. Prüfe, ob ein Ergebnis vorliegt.
       */
      if (rs.next()) {
        // Ergebnis-Tupel in Objekt umwandeln
        Profile p = new Profile();
        p.setId(rs.getInt("id"));
        p.setUserName(rs.getString("userName"));
        p.setName(rs.getString("name"));
        p.setLastName(rs.getString("lastName"));
        p.setDateOfBirth(rs.getDate("dateOfBirth"));
        p.setGender(rs.getString("gender"));
        p.setBodyHeight(rs.getFloat("bodyHeight"));
        p.setHairColour(rs.getString("hairColour"));
        p.setConfession(rs.getString("confession"));
        
        // evtl. muss hier nochmal geschaut werden, ob 0 automatisch als false und * automatisch als true ausgegeben wird
        
        p.setIsSmoking(rs.getInt("isSmoking"));
       

        return p;
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      return null;
    }

    return null;
  }

  public Vector<Profile> findAll() {
    Connection con = DBConnection.connection();
    // Ergebnisvektor vorbereiten
    Vector<Profile> result = new Vector<Profile>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt.executeQuery("SELECT id, userName, name, lastName, dateOfBirth,"
        		+ " gender, bodyHeight, hairColour, confession, isSmoking FROM profiles"
           + "ORDER BY id");

      // Für jeden Eintrag im Suchergebnis wird nun ein Customer-Objekt
      // erstellt.
      while (rs.next()) {
        Profile p = new Profile();
        p.setId(rs.getInt("id"));
        p.setUserName(rs.getString("userName"));
        p.setName(rs.getString("name"));
        p.setLastName(rs.getString("lastName"));
        p.setDateOfBirth(rs.getDate("dateOfBirth"));
        p.setGender(rs.getString("gender"));
        p.setBodyHeight(rs.getFloat("bodyHeight"));
        p.setHairColour(rs.getString("hairColour"));
        p.setConfession(rs.getString("confession"));
        
        // evtl. muss hier nochmal geschaut werden, ob 0 automatisch als false und * automatisch als true ausgegeben wird
        
        p.setIsSmoking(rs.getInt("isSmoking"));

        // Hinzufügen des neuen Objekts zum Ergebnisvektor
        result.addElement(p);
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }

    // Ergebnisvektor zurückgeben
    return result;
  }

 
  public Profile insert(Profile p) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      /*
       * Zunächst schauen wir nach, welches der momentan höchste
       * Primärschlüsselwert ist.
       */
      ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
          + "FROM profiles ");

      // Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
      if (rs.next()) {
        /*
         * c erhält den bisher maximalen, nun um 1 inkrementierten
         * Primärschlüssel.
         */
        p.setId(rs.getInt("maxid") + 1);

        stmt = con.createStatement();

        // Jetzt erst erfolgt die tatsächliche Einfügeoperation
        stmt.executeUpdate("INSERT INTO profiles (id, userName, name, lastName, gender, dateOfBirth, bodyHeight, hairColour, confession, isSmoking) "
            + "VALUES (" + p.getId() + ",'" + p.getUserName() + "','" + p.getName() + "','" + p.getLastName() + "','" + p.getGender() + "','" + p.getDateOfBirth() + "','"
            + p.getBodyHeight() + "','" + p.getHairColour() + "','" + p.getConfession() + "','" + p.getIsSmoking() + "')");
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }

    return p;
  }


  public void delete(Profile p) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("DELETE FROM profiles " + "WHERE id=" + p.getId());
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

public Profile edit(Profile p) {
	
		    Connection con = DBConnection.connection();

		    try {
		      Statement stmt = con.createStatement();

		      stmt.executeUpdate("UPDATE profiles " + "SET userName=\""
		          + p.getUserName() + "\", " + "name=\"" + p.getName() + "\", " + "lastName=\"" + p.getLastName()
		          + "\", " + "lastName=\"" + p.getLastName() + "\", " + "dateOfBirth=\"" + p.getDateOfBirth() 
		          + "\", " + "gender=\"" + p.getGender() + "\", " + "bodyHeight=\"" + p.getBodyHeight() 
		          + "\", " + "hairColour=\"" + p.getHairColour() + "\", " + "confession=\"" + p.getConfession()
		          + "\", " + "isSmoking=\"" + p.getIsSmoking()
		          + "WHERE id=" + p.getId());

		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    // Um Analogie zu insert(Customer c) zu wahren, geben wir c zurück
		    return p;
		  }
	

}

