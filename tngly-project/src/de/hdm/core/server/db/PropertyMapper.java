package de.hdm.core.server.db;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import de.hdm.core.shared.bo.Property;
import de.hdm.core.shared.bo.InfoPropertyConnection;
import de.hdm.core.shared.bo.Profile;


public class PropertyMapper {

	/**
	 * Übernommen & angepasst von: @author Thies
	 */
	
  public static PropertyMapper getPropertyMapper() {
		return propertyMapper;
	}

	public static void setPropertyMapper(PropertyMapper propertyMapper) {
		PropertyMapper.propertyMapper = propertyMapper;
	}

  private static PropertyMapper propertyMapper = null;


  protected PropertyMapper() {
  }

 
  public static PropertyMapper propertyMapper() {
    if (propertyMapper == null) {
      propertyMapper = new PropertyMapper();
    }

    return propertyMapper;
  }


  public Property findByKey(int id) {
    // DB-Verbindung holen
    Connection con = DBConnection.connection();

    try {
      // Leeres SQL-Statement (JDBC) anlegen
      Statement stmt = con.createStatement();

      // Statement ausfüllen und als Query an die DB schicken
      ResultSet rs = stmt
          .executeQuery("SELECT id, textualDescription FROM properties"
              + "WHERE id=" + id + " ORDER BY textualDescription");

      /*
       * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
       * werden. Prüfe, ob ein Ergebnis vorliegt.
       */
      if (rs.next()) {
        // Ergebnis-Tupel in Objekt umwandeln
        Property p = new Property();
        p.setId(rs.getInt("id"));
        p.setTextualDescription(rs.getString("textualDescription"));       

        return p;
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      return null;
    }

    return null;
  }


  public Vector<Property> findAll() {
    Connection con = DBConnection.connection();
    // Ergebnisvektor vorbereiten
    Vector<Property> result = new Vector<Property>();

    try {
      Statement stmt = con.createStatement();

      ResultSet rs = stmt.executeQuery("SELECT id, textualDescription FROM properties"
           + "ORDER BY id");

      // Für jeden Eintrag im Suchergebnis wird nun ein Customer-Objekt
      // erstellt.
      while (rs.next()) {
    	Property p = new Property();
        p.setId(rs.getInt("id"));
        p.setTextualDescription(rs.getString("textualDescription"));

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

  
  public void delete(Property p) {
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      stmt.executeUpdate("DELETE FROM properties " + "WHERE id=" + p.getId());
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

public void edit(Property userProperty) {
	// TODO Auto-generated method stub
	
}

 // Verbindung zwischen Property und Information

public InfoPropertyConnection InfoPropertyConnection(InfoPropertyConnection i) {
	  
    Connection con = DBConnection.connection();

    try {
      Statement stmt = con.createStatement();

      /*
       * Zunächst schauen wir nach, welches der momentan höchste
       * Primärschlüsselwert ist.
       */
      ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
          + "FROM infoPropertyConnections ");

      // Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
      if (rs.next()) {
        /*
         * i erhält den bisher maximalen, nun um 1 inkrementierten
         * Primärschlüssel.
         */
        i.setId(rs.getInt("maxid") + 1);

        stmt = con.createStatement();

        // Jetzt erst erfolgt die tatsächliche Einfügeoperation
        stmt.executeUpdate("INSERT INTO infoPropertyConnections (id, informationId, propertyId) "
            + "VALUES (" + i.getId() + ",'" + i.getInformationId() + "','" + i.getPropertyId() + "')");
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }

    return i;
  }

public ArrayList<Profile> searchForProperties(ArrayList<Profile> profiles) {
	return profiles;
	// TODO Auto-generated method stub
	
}
 
}

