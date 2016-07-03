package de.hdm.core.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.core.shared.bo.Description;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.Selection;

/**
 * Die Mapper-Klasse PropertyMapper stellt eine Schnittstelle zwischen
 * Applikation und Datenbank dar. Aufgrund der gestellten Anforderungen enthaelt
 * diese Klasse keine typischen CRUD-Methoden. Die Properties werden im Rahmen
 * dieses Projekts einmal festgelegt und der Datenbank hinzugefuegt. Diese sind
 * nicht frei erstell-, editier- und loeschbar. Die zu persistierenden
 * Eigenschaften wuerden hier auf eine relationale Ebene projiziert. Die
 * abzurufenden Properties wuerden aus den relationalen Tabellen
 * zusammengestellt.
 * 
 * @author Philipp Schmitt
 */

public class PropertyMapper {
	/**
	 * Instanziieren des Mappers
	 */
	private static PropertyMapper propertyMapper = null;

	/**
	 * Mithilfe des <code>protected</code>-Attributs im Konstruktor wird
	 * verhindert, dass von anderen Klassen eine neue Instanz der Klasse
	 * geschaffen werden kann.
	 */
	protected PropertyMapper() {
	}

	/**
	 * Aufruf eines Property-Mappers fuer Klassen, die keinen Zugriff auf den
	 * Konstruktor haben.
	 * 
	 * @return Einzigartige Mapper-Instanz zur Benutzung in der
	 *         Applikationsschicht
	 */
	public static PropertyMapper propertyMapper() {
		if (propertyMapper == null) {
			propertyMapper = new PropertyMapper();
		}

		return propertyMapper;
	}

	/**
	 * Read-Methode zur Befuellung einer beliebigen Anzahl an Profilen mit
	 * aktuell in der Datenbank verfuegbaren Eigenschaften, fuer die der Nutzer
	 * Informationswerte verwalten kann.
	 * 
	 * @author Philipp Schmitt
	 * @param profiles
	 *            Ein Array an Profilen, fuer die die moeglichen Eigenschaften
	 *            hinzugefuegt werden sollen
	 * @return Das Profil-Array mit den ergaenzten Eigenschaftswerten
	 */

	public ArrayList<Profile> searchForProperties(ArrayList<Profile> profiles) {

		/**
		 * DB-Verbindung holen
		 */
		Connection con = DBConnection.connection();

		for (Profile p : profiles) {
			try {
				/**
				 * Leeres SQL-Statement (JDBC) anlegen
				 */
				Statement stmt = con.createStatement();

				/**
				 * Statement ausfuellen und als Query an die DB schicken
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
