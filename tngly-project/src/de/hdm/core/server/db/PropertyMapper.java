package de.hdm.core.server.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import de.hdm.core.shared.bo.Property;
import de.hdm.core.shared.bo.InfoPropertyConnection;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.Description;
import de.hdm.core.shared.bo.Selection;

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

	/**
	 * FindByKey-Methode. Hierbei wird ein Property-Objekt anhand der ID gefunden und zurückgegeben.
	 */
	
	public Property findByKey(int id) {
		
		/**
		 *  DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */
		
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/**
			 *  Statement ausfüllen und als Query an die DB schicken
			 */
			ResultSet rs = stmt.executeQuery("SELECT id, textualDescription FROM properties" + "WHERE id=" + id
					+ " ORDER BY textualDescription");

			/**
			 * Da id Primärschlüssel ist, kann max. nur ein Tupel
			 * zurückgegeben werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				/**
				 *  Ergebnis-Tupel in Objekt umwandeln
				 */
				Property p = new Property();
				p.setId(rs.getInt("id"));
				p.setTextualDescription(rs.getString("textualDescription"));

				return p;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * FindAll-Methode. Hierbei werden in einem Vektor alle Profiles ausgegeben.
	 */
	
	public Vector<Property> findAll() {
		
		/**
		 * DB-Verbindung holen
		 */
		
		Connection con = DBConnection.connection();
		
		 /** 
		  * Ergebnisvektor vorbereiten
		  */
		
		Vector<Property> result = new Vector<Property>();

		try {
			/**
			 * Erzeugen eines neuen SQL-Statements.
			 */
			Statement stmt = con.createStatement();

			/**
			 *  Statement ausfüllen und als Query an die DB schicken.
			 */
			
			ResultSet rs = stmt.executeQuery("SELECT id, textualDescription FROM properties" + "ORDER BY id");

			/**
			 *  Für jeden Eintrag im Suchergebnis wird nun ein Property-Objekt erstellt.
			 */
			while (rs.next()) {
				Property p = new Property();
				p.setId(rs.getInt("id"));
				p.setTextualDescription(rs.getString("textualDescription"));

				/**
				 *  Hinzufügen des neuen Objekts zum Ergebnisvektor
				 */
				result.addElement(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/**
		 *  Ergebnisvektor zurückgeben
		 */
		return result;
	}

	/** 
	 *  Delete-Methode. Ein Profile-Objekt wird übergeben und dieses aus der DB gelöscht.
	 */
	
	public void delete(Property p) {
		
		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */
		
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/**
			 *  Statement ausfüllen und als Query an die DB schicken
			 */
			
			stmt.executeUpdate("DELETE FROM properties " + "WHERE id=" + p.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void edit(Property userProperty) {
		// TODO Auto-generated method stub

	}

	/**
	 *  Verbindung zwischen Property und Information
	 * @param i
	 * @return
	 */

	public InfoPropertyConnection InfoPropertyConnection(InfoPropertyConnection i) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/**
			 * Zunächst wird geprüft, welches der momentan höchste
			 * Primärschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM infoPropertyConnections ");

			// Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
			if (rs.next()) {
				/**
				 * i erhält den bisher maximalen, nun um 1 inkrementierten
				 * Primärschlüssel.
				 */
				i.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// Jetzt erst erfolgt die tatsächliche Einfügeoperation
				stmt.executeUpdate("INSERT INTO infoPropertyConnections (id, informationId, propertyId) " + "VALUES ("
						+ i.getId() + ",'" + i.getInformationId() + "','" + i.getPropertyId() + "')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return i;
	}

	/**
	 * SearchForProperties-Methode. Eine ArrayList mit Profil-Objekten wird übergeben, in denen nach allen Properties gesucht wird.
	 * @param profiles
	 * @return
	 */
	
	public ArrayList<Profile> searchForProperties(ArrayList<Profile> profiles) {

		/**
		 *  DB-Verbindung holen
		 */
		Connection con = DBConnection.connection();

		for (Profile p : profiles) {
			try {
				/**
				 *  Leeres SQL-Statement (JDBC) anlegen
				 */
				Statement stmt = con.createStatement();

				/**
				 * Statement ausfüllen und als Query an die DB schicken
				 */
				String sql0 = "SELECT id, textualDescription FROM properties WHERE type ='description'";
				ResultSet rs = stmt.executeQuery(sql0);

				while (rs.next()) {
					Description de = new Description();
					de.setId(rs.getInt("id"));
					de.setTextualDescription(rs.getString("textualDescription"));
					p.getDescriptionList().add(de);
				}

				String sql1 = "SELECT id, textualDescription FROM properties WHERE type ='selection'";
				ResultSet rs2 = stmt.executeQuery(sql1);

				while (rs2.next()) {
					Selection se = new Selection();
					se.setId(rs2.getInt("id"));
					se.setTextualDescription(rs2.getString("textualDescription"));
					p.getSelectionList().add(se);
				}

			}

			catch (SQLException e) {
				e.printStackTrace();
				return null;
			}

		}
		return profiles;
	}
}
