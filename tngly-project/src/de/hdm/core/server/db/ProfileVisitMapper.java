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

/**
 * Die Mapper-Klasse ProfileVisitMapper stellt eine Schnittstelle zwischen
 * Applikation und Datenbank dar. Die zu persistierenden Profilbesuche werden
 * hier auf eine relationale Ebene projiziert. Die abzurufenden Profilbesuche
 * werden aus den relationalen Tabellen zusammengestellt.
 * 
 * @author Philipp Schmitt
 */

public class ProfileVisitMapper {
	/**
	 * Instanziieren des Mappers
	 */
	private static ProfileVisitMapper profileVisitMapper = null;

	/**
	 * Mithilfe des <code>protected</code>-Attributs im Konstruktor wird
	 * verhindert, dass von anderen Klassen eine neue Instanz der Klasse
	 * geschaffen werden kann.
	 */
	protected ProfileVisitMapper() {
	}

	/**
	 * Aufruf eines ProfileVisit-Mappers für Klassen, die keinen Zugriff auf den
	 * Konstruktor haben.
	 * 
	 * @return Einzigartige Mapper-Instanz zur Benutzung in der
	 *         Applikationsschicht
	 */
	public static ProfileVisitMapper profileVisitMapper() {
		if (profileVisitMapper == null) {
			profileVisitMapper = new ProfileVisitMapper();
		}

		return profileVisitMapper;
	}

	/**
	 * Read-Methode - Anhand einer vorgegebenen id wird der dazu gehörige
	 * ProfileVisit in der Datenbank gesucht.
	 * 
	 * @author Philipp Schmitt
	 * @param id
	 *            Die id, zu der der Eintrag aus der DB gelesen werden soll
	 * @return Das durch die id referenzierte ProfileVisit-Objekt
	 * 
	 */

	public ProfileVisit findByKey(int id) {
		/**
		 * DB-Verbindung holen
		 */
		Connection con = DBConnection.connection();

		try {
			/**
			 * Leeres SQL-Statement (JDBC) anlegen
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
				 * Ergebnis-Tupel in Objekt umwandeln
				 */
				ProfileVisit pv = new ProfileVisit();
				pv.setId(rs.getInt("id"));
				pv.setVisitingProfileId(rs.getInt("visitingProfileId"));
				pv.setVisitedProfileId(rs.getInt("visitedProfileId"));
				pv.setTimestamp(rs.getDate("timestamp"));

				return pv;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Read-Methode - Auslesen aller ProfileVisits in einen Vektor.
	 * 
	 * @author Philipp Schmitt
	 * @return Liste aller derzeit in der Datenbank eingetragenen Profilbesuche
	 */

	public Vector<ProfileVisit> findAll() {
		Connection con = DBConnection.connection();
		/**
		 * Ergebnisvektor vorbereiten
		 */
		Vector<ProfileVisit> result = new Vector<ProfileVisit>();

		try {

			/**
			 * Erzeugen eines neuen SQL-Statements.
			 */

			Statement stmt = con.createStatement();

			/**
			 * Statement ausfüllen und als Query an die DB schicken.
			 */

			ResultSet rs = stmt.executeQuery(
					"SELECT id, visitingProfileId, visitedProfileId, timestamp FROM profileVisits" + "ORDER BY id");

			/**
			 * Für jeden Eintrag im Suchergebnis wird nun ein
			 * ProfileVisit-Objekt erstellt.
			 */

			while (rs.next()) {
				ProfileVisit pv = new ProfileVisit();
				pv.setId(rs.getInt("id"));
				pv.setVisitingProfileId(rs.getInt("visitingProfileId"));
				pv.setVisitedProfileId(rs.getInt("visitedProfileId"));
				pv.setTimestamp(rs.getDate("timestamp"));

				/**
				 * Hinzufügen des neuen Objekts zum Ergebnisvektor
				 */

				result.addElement(pv);
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
	 * Read-Methode - Auslesen aller ProfileVisits (Profilbesuche), die vom
	 * übergebenen Profil je getätigt wurden
	 * 
	 * @author Philipp Schmitt
	 * @param visitingProfileId
	 *            Id des Profils, zu dem alle Profilbesuche gesucht werden
	 *            sollen.
	 * @return Liste aller von dem zu überprüfenden Profil getätigten
	 *         Profilbesuche
	 */

	public ArrayList<ProfileVisit> findVisitedProfiles(int visitingProfileId) {

		/**
		 * DB-Verbindung holen.
		 */

		Connection con = DBConnection.connection();
		/**
		 * Ergebnisvektor vorbereiten
		 */
		ArrayList<ProfileVisit> result = new ArrayList<ProfileVisit>();

		try {
			Statement stmt = con.createStatement();

			/**
			 * Erzeugen eines neuen SQL-Statements.
			 */

			ResultSet rs = stmt
					.executeQuery("SELECT id, visitingProfileId, visitedProfileId, timestamp FROM profileVisits"
							+ "WHERE visitingProfileId=" + visitingProfileId + "ORDER BY timestamp");

			/**
			 * Für jeden Eintrag im Suchergebnis wird nun ein
			 * ProfileVisit-Objekt erstellt.
			 */

			while (rs.next()) {
				ProfileVisit pv = new ProfileVisit();
				pv.setId(rs.getInt("id"));
				pv.setVisitingProfileId(rs.getInt("visitingProfileId"));
				pv.setVisitedProfileId(rs.getInt("visitedProfileId"));
				pv.setTimestamp(rs.getDate("timestamp"));

				/**
				 * Hinzufügen des neuen Objekts zum Ergebnisvektor
				 */

				result.add(pv);
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
	 * Insert-Methode - Ein ProfileVisit-Array visitedProfiles wird übergeben
	 * und die zugehörigen Werte in ein SQL-Statement geschrieben, welches
	 * ausgeführt wird, um die Objekt in die Datenbank einzutragen.
	 * 
	 * @author Philipp Schmitt
	 * @param visitedProfiles
	 *            Liste mit Profilbesuchen, die in die Datenbank geschrieben
	 *            werden sollen
	 * 
	 */

	public void insert(ArrayList<ProfileVisit> visitedProfiles) {

		/**
		 * DB-Verbindung holen
		 */

		Connection con = DBConnection.connection();

		for (ProfileVisit p : visitedProfiles) {

			try {
				/**
				 * Erzeugen eines neuen SQL-Statements.
				 */
				Statement stmt = con.createStatement();

				/**
				 * Zunächst schauen wir nach, welches der momentan höchste
				 * Primärschlüsselwert ist.
				 */
				ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM profileVisits ");

				/**
				 * Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein *
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

					// insert Date as current timestamp yyyy-MM-dd, NICHT
					// VERGESSEN!

					/**
					 * Jetzt erst erfolgt die tatsächliche Einfügeoperation
					 */
					stmt.executeUpdate("INSERT INTO profileVisits (id, visitingProfileId, visitedProfileId, timestamp) "
							+ "VALUES (" + p.getId() + ",'" + p.getVisitingProfileId() + "','" + p.getVisitedProfileId()
							+ "','" + date + "')");
				}
			}

			catch (SQLException e) {
				e.printStackTrace();

			}
		}
	}

	/**
	 * Delete-Methode - Ein ProfileVisit-Array wird übergeben und die einzelnen
	 * Profilbesuche aus der DB gelöscht.
	 * 
	 * @author Philipp Schmitt
	 * @param visitedProfiles
	 *            Liste mit Profilbesuchen, die aus der Datenbank gelöscht
	 *            werden sollen
	 */

	public void delete(ArrayList<ProfileVisit> visitedProfiles) {
		Connection con = DBConnection.connection();

		for (ProfileVisit p : visitedProfiles) {

			try {
				Statement stmt = con.createStatement();

				stmt.executeUpdate("DELETE FROM profileVisits " + "WHERE id=" + p.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Delete-Methode zur Profillöschung. Ein Profilobjekt profile soll komplett
	 * aus der Datenbank gelöscht werden und hiermit auch die entsprechenden
	 * Referenzen in der profileVisit-Tabelle.
	 * 
	 * @author Philipp Schmitt
	 * @param profile
	 *            Profil, das komplett aus der Datenbank gelöscht wird
	 */
	public void delete(Profile profile) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM profileVisits " + "WHERE visitingProfileId=" + profile.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Read-Methode - Abfrage, ob zwischen ein Profil bereits besucht wurde
	 * 
	 * @param currentUserProfile
	 *            Profil, das ein anderes Profil besucht haben soll
	 * @param otherProfile
	 *            Profil, das besucht werden soll
	 * @return Boolean, ob das angemeldete Profil das zu checkende Profil
	 *         bereits besucht hat.
	 */
	public Boolean wasProfileVisited(Profile currentUserProfile, Profile otherProfile) {

		/**
		 * DB-Verbindung holen
		 */
		Connection con = DBConnection.connection();
		Vector<ProfileVisit> result = new Vector<ProfileVisit>();

		try {
			/**
			 * Leeres SQL-Statement (JDBC) anlegen
			 */
			Statement stmt = con.createStatement();

			/**
			 * Statement ausfüllen und als Query an die DB schicken
			 */
			ResultSet rs = stmt
					.executeQuery("SELECT id, visitingProfileId, visitedProfileId, timestamp FROM profileVisits "
							+ "WHERE visitedProfileId=" + otherProfile.getId() + " AND " + "visitingProfileId="
							+ currentUserProfile.getId());

			while (rs.next()) {
				ProfileVisit pv = new ProfileVisit();
				pv.setId(rs.getInt("id"));
				pv.setVisitingProfileId(rs.getInt("visitingProfileId"));
				pv.setVisitedProfileId(rs.getInt("visitedProfileId"));

				/**
				 * Hinzufügen des neuen Objekts zum Ergebnisvektor
				 */

				result.addElement(pv);
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		if (result.isEmpty()) {
			return false;
		}

		else {
			return true;
		}

	}
}