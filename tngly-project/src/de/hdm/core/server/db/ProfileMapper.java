package de.hdm.core.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.SearchProfile;

/**
 * Die Mapper-Klasse ProfileMapper stellt eine Schnittstelle zwischen Applikation
 * und Datenbank dar. Die zu persistierenden Profile werden hier auf eine
 * relationale Ebene projiziert. Die abzurufenden Profile werden aus den
 * relationalen Tabellen zusammengestellt.
 * 
 * @author Philipp Schmitt
 */

public class ProfileMapper {

	/**
	 * Instanziieren des Mappers
	 */
	private static ProfileMapper profileMapper = null;

	/**
	 * Mithilfe des <code>protected</code>-Attributs im Konstruktor wird
	 * verhindert, dass von anderen Klassen eine neue Instanz der Klasse
	 * geschaffen werden kann.
	 */
	protected ProfileMapper() {
	}

	/**
	 * Aufruf eines Profile-Mappers für Klassen, die keinen Zugriff auf den
	 * Konstruktor haben.
	 * 
	 * @return Einzigartige Mapper-Instanz zur Benutzung in der
	 *         Applikationsschicht
	 */
	public static ProfileMapper profileMapper() {
		if (profileMapper == null) {
			profileMapper = new ProfileMapper();
		}

		return profileMapper;
	}

	/**
	 * Read-Methode - Anhand einer vorgegebenen id wird das dazu gehoerige
	 * Profile in der Datenbank gesucht.
	 * 
	 * @author Philipp Schmitt
	 * @param id
	 *            Die id des Profils, das aus der Datenbank gelesen werden soll
	 * @return Das durch die id referenzierte Profile-Objekt
	 * 
	 */

	public Profile findByKey(int id) {
		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/**
			 * Statement ausfüllen und als Query an die DB schicken
			 */
			ResultSet rs = stmt.executeQuery("SELECT id, userName, name, lastName, dateOfBirth,"
					+ " gender, bodyHeight, hairColour, confession, isSmoking FROM profiles " + "WHERE id='" + id
					+ "' ORDER BY id");

			/**
			 * Da id Primaerschlüssel ist, kann max. nur ein Tupel zurückgegeben
			 * werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {

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
				p.setIsSmoking(rs.getInt("isSmoking"));

				return p;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Read-Methode - Anhand eines Usernamen wird das dazu gehoerige Profile in
	 * der Datenbank gesucht. Diese Methode ist vor Allem für den Login
	 * relevant, der über die Google-Email-Adresse realisiert wird. Die
	 * Google-Adresse fungiert also als Username, der von Haus aus eindeutig
	 * ist.
	 * 
	 * @author Philipp Schmitt
	 * @param id
	 *            Der Username zum Profil, das aus der Datenbank gelesen werden
	 *            soll
	 * @return Das durch die id referenzierte Profile-Objekt
	 * 
	 */

	public Profile findByName(String id) {
		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/**
			 * Statement ausfüllen und als Query an die DB schicken
			 */
			ResultSet rs = stmt.executeQuery("SELECT id, userName, name, lastName, dateOfBirth,"
					+ " gender, bodyHeight, hairColour, confession, isSmoking FROM profiles " + "WHERE userName='" + id
					+ "' ORDER BY lastName");

			/**
			 * Da id Primaerschlüssel ist, kann max. nur ein Tupel zurückgegeben
			 * werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				/**
				 * Ergebnis-Tupel in Objekt umwandeln
				 */
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

				// evtl. muss hier nochmal geschaut werden, ob 0 automatisch als
				// false und * automatisch als true ausgegeben wird

				p.setIsSmoking(rs.getInt("isSmoking"));

				return p;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Read-Methode - Auslesen aller Profile in einen Vektor.
	 * 
	 * @author Philipp Schmitt
	 * @return Liste aller derzeit in der Datenbank eingetragenen Profile.
	 */

	public Vector<Profile> findAll() {
		Connection con = DBConnection.connection();
		/**
		 * Ergebnisvektor vorbereiten
		 */
		Vector<Profile> result = new Vector<Profile>();

		/**
		 * Erzeugen eines neuen SQL-Statements.
		 */

		try {
			Statement stmt = con.createStatement();

			/**
			 * Statement ausfüllen und als Query an die DB schicken.
			 */

			ResultSet rs = stmt.executeQuery("SELECT id, userName, name, lastName, dateOfBirth,"
					+ " gender, bodyHeight, hairColour, confession, isSmoking FROM profiles" + "ORDER BY id");

			/**
			 * Für jeden Eintrag im Suchergebnis wird nun ein Profile-Objekt
			 * erstellt.
			 */
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

				// evtl. muss hier nochmal geschaut werden, ob 0 automatisch als
				// false und * automatisch als true ausgegeben wird

				p.setIsSmoking(rs.getInt("isSmoking"));

				/**
				 * Hinzufügen des neuen Objekts zum Ergebnisvektor
				 */
				result.addElement(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/**
		 * Ergebnisvektor zurückgeben
		 */
		return result;
	}

	/**
	 * Insert-Methode - Ein Profile-Objekt wird übergeben und die zugehoerigen
	 * Werte in ein SQL-Statement geschrieben, welches ausgeführt wird, um das
	 * Objekt in die Datenbank einzutragen.
	 * 
	 * @author Philipp Schmitt
	 * @param pb
	 *            Profil, das in die Datenbank geschrieben werden soll
	 * @return Profile-Objekt, das in die Datenbank geschrieben wurde
	 */

	public Profile insert(Profile p) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/**
			 * Zunaechst schauen wir nach, welches der momentan hoechste
			 * Primaerschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM profiles ");

			/**
			 * Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
			 */
			if (rs.next()) {
				/**
				 * p erhaelt den bisher maximalen, nun um 1 inkrementierten
				 * Primaerschlüssel.
				 */
				p.setId(rs.getInt("maxid") + 1);

				/**
				 * Erzeugen eines neuen SQL-Statements.
				 */

				stmt = con.createStatement();

				String date = null;
				if (p.getDateOfBirth() != null) {
					SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd");
					date = mySQLformat.format(p.getDateOfBirth());
				}

				/**
				 * Jetzt erst erfolgt die tatsaechliche Einfügeoperation
				 */
				stmt.executeUpdate(
						"INSERT INTO profiles (id, userName, name, lastName, gender, dateOfBirth, bodyHeight, hairColour, confession, isSmoking) "
								+ "VALUES (" + p.getId() + ",'" + p.getUserName() + "','" + p.getName() + "','"
								+ p.getLastName() + "','" + p.getGender() + "','" + date + "','" + p.getBodyHeight()
								+ "','" + p.getHairColour() + "','" + p.getConfession() + "','" + p.getIsSmoking()
								+ "')");

				System.out.println(
						"INSERT INTO profiles (id, userName, name, lastName, gender, dateOfBirth, bodyHeight, hairColour, confession, isSmoking) "
								+ "VALUES (" + p.getId() + ",'" + p.getUserName() + "','" + p.getName() + "','"
								+ p.getLastName() + "','" + p.getGender() + "','" + date + "','" + p.getBodyHeight()
								+ "','" + p.getHairColour() + "','" + p.getConfession() + "','" + p.getIsSmoking()
								+ "')");

				return p;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;

	}

	/**
	 * Delete-Methode - Ein Profile-Objekt wird übergeben, anhand dessen der
	 * zugehoerige Eintrag in der Datenbank geloescht wird
	 * 
	 * @author Philipp Schmitt
	 * @param p
	 *            Profil, das geloescht werden soll
	 */

	public void delete(Profile p) {

		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/**
			 * Statement ausfüllen und als Query an die DB schicken
			 */

			stmt.executeUpdate("DELETE FROM profiles " + "WHERE id=" + p.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Edit-Methode - Ein Profil wird übergeben und die zugehoerigen Werte in ein
	 * SQL-Statement geschrieben, welches ausgeführt wird, um die
	 * Informationswerte des Profils in der Datenbank zu aktualisieren.
	 * 
	 * @author Philipp Schmitt
	 * @param profile
	 *            Das Profil, dessen Variablen in der DB geaendert werden soll.
	 */

	public Profile edit(Profile p) {

		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd");
			String date = mySQLformat.format(p.getDateOfBirth());

			/**
			 * Statement ausfüllen und als Query an die DB schicken
			 */

			stmt.executeUpdate("UPDATE profiles " + "SET userName=\"" + p.getUserName() + "\", " + "name=\""
					+ p.getName() + "\", " + "lastName=\"" + p.getLastName() + "\", " + "dateOfBirth=\"" + date + "\", "
					+ "gender=\"" + p.getGender() + "\", " + "bodyHeight=\"" + p.getBodyHeight() + "\", "
					+ "hairColour=\"" + p.getHairColour() + "\", " + "confession=\"" + p.getConfession() + "\", "
					+ "isSmoking=" + p.getIsSmoking() + " WHERE id=" + p.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		/**
		 * Um Analogie zu insert(Profile p) zu wahren, geben wir p zurück
		 */
		return p;
	}

	/**
	 * Read-Methode - Übergabe eines Suchprofils, anhand dessen Profile aus der
	 * Datenbank ausgelesen werden sollen, die den Kriterien (z.B.
	 * <code>AgeRange</code> ) entsprechen.
	 * 
	 * @author Philipp Schmitt
	 * @param searchProfile
	 *            Suchprofil mit Werten, nach denen die auszugebenden Profile
	 *            selektiert werden sollen
	 * @return Ausgabe aller Profile, die den Kriterien des Suchprofils
	 *         entsprechen
	 */

	public ArrayList<Profile> searchProfileByProfile(SearchProfile searchProfile) {
		/**
		 * DB-Verbindung holen und Erzeugen einer neuen ArrayList & eines
		 * SQL-Statements.
		 */
		Connection con = DBConnection.connection();

		ArrayList<Profile> profiles = new ArrayList<Profile>();

		try {
			Statement stmt = con.createStatement();

			/**
			 * Statement ausfüllen und als Query an die DB schicken
			 */
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder
					.append("SELECT id, userName, name, lastName, dateOfBirth, FLOOR((DATEDIFF(NOW(), dateOfBirth) / 365.25)) AS age, "
							+ "gender, bodyHeight, hairColour, confession, isSmoking FROM `profiles`");

			boolean and = false;

			/**
			 * Wenn irgendein WHERE-Kriterium gesetzt wurde, dann wird auch ein
			 * WHERE ins Statement gesetzt.
			 */

			if (searchProfile.getGender() != null || searchProfile.getAgeRangeFrom() != 0
					|| searchProfile.getAgeRangeTo() != 0 || searchProfile.getBodyHeightFrom() != 0f
					|| searchProfile.getBodyHeightTo() != 0f || searchProfile.getHairColour() != null
					|| searchProfile.getConfession() != null || searchProfile.getIsSmoking() != -1) {

				stringBuilder.append(" WHERE");

			}

			if (searchProfile.getGender() != null) {
				if (and == true) {
					stringBuilder.append(" AND");
				}

				else {
				}
				stringBuilder.append(" gender='" + searchProfile.getGender() + "'");
				and = true;
			} else {
				and = false;
			}

			/**
			 * Geburtsdatum
			 */
			if (searchProfile.getAgeRangeFrom() != 0 && searchProfile.getAgeRangeTo() != 0) {
				if (and == true) {
					stringBuilder.append(" AND");
				} else {
				}

				stringBuilder.append(" FLOOR((DATEDIFF(NOW(), dateOfBirth) / 365.25)) BETWEEN "
						+ searchProfile.getAgeRangeFrom() + " AND " + searchProfile.getAgeRangeTo());
				and = true;
			}

			/**
			 * Hier muss die Applikationslogik von Vornherein darauf achten,
			 * dass, wenn z.b. nur der von-Wert eingegeben wird, der bis-Wert
			 * automatisch aufgefüllt wird & vice versa
			 */

			if (searchProfile.getBodyHeightFrom() != 0f && searchProfile.getBodyHeightTo() != 0f) {
				if (and == true) {
					stringBuilder.append(" AND");
				} else {
				}

				stringBuilder.append(" bodyHeight BETWEEN " + searchProfile.getBodyHeightFrom() + " AND "
						+ searchProfile.getBodyHeightTo());
				and = true;
			} else {
				if (and == true) {
					and = true;
				} else {
					and = false;
				}
			}

			/**
			 * Haarfarbe selektieren
			 */

			if (searchProfile.getHairColour() != null) {
				if (and == true) {
					stringBuilder.append(" AND ");
				} else {
				}

				stringBuilder.append(" hairColour='" + searchProfile.getHairColour() + "'");
				and = true;
			} else {
				if (and == true) {
					and = true;
				} else {
					and = false;
				}
			}

			/**
			 * Bekenntnis selektieren
			 */

			if (searchProfile.getConfession() != null) {
				if (and == true) {
					stringBuilder.append(" AND");
				} else {
				}

				stringBuilder.append(" confession='" + searchProfile.getConfession() + "'");
				and = true;
			} else {
				if (and == true) {
					and = true;
				} else {
					and = false;
				}
			}

			if (searchProfile.getIsSmoking() != -1) {
				if (and == true) {
					stringBuilder.append(" AND");
				} else {
				}

				stringBuilder.append(" isSmoking=" + searchProfile.getIsSmoking());
			}

			stringBuilder.append(" ORDER BY id");

			and = false;

			String preparedStatement = stringBuilder.toString();

			ResultSet rs = stmt.executeQuery(preparedStatement);

			/**
			 * Da id Primaerschlüssel ist, kann max. nur ein Tupel zurückgegeben
			 * werden. Prüfe, ob ein Ergebnis vorliegt.
			 */

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

				p.setIsSmoking(rs.getInt("isSmoking"));

				/**
				 * Hinzufügen des neuen Objekts zum Ergebnisvektor
				 */
				profiles.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return profiles;
	}

}
