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

/**
 * Die Mapper-Klasse ProfileBanMapper stellt eine Schnittstelle zwischen
 * Applikation und Datenbank dar. Die zu persistierenden Kontaktsperren werden hier auf
 * eine relationale Ebene projiziert. Die abzurufenden Kontaktsperren werden aus den
 * relationalen Tabellen zusammengestellt.
 * 
 * @author Philipp Schmitt
 */
public class ProfileBanMapper {

	/**
	 * Instanziieren des Mappers
	 */
	private static ProfileBanMapper profileBanMapper = null;

	/**
	 * Mithilfe des <code>protected</code>-Attributs im Konstruktor wird
	 * verhindert, dass von anderen Klassen eine neue Instanz der Klasse
	 * geschaffen werden kann.
	 */
	protected ProfileBanMapper() {
	}

	/**
	 * Aufruf eines ProfileBan-Mappers für Klassen, die keinen Zugriff auf den
	 * Konstruktor haben.
	 * 
	 * @return Einzigartige Mapper-Instanz zur Benutzung in der
	 *         Applikationsschicht
	 */

	public static ProfileBanMapper profileBanMapper() {
		if (profileBanMapper == null) {
			profileBanMapper = new ProfileBanMapper();
		}

		return profileBanMapper;
	}

	/**
	 * Read-Methode - Anhand einer vorgegebenen id wird der dazu gehörige
	 * ProfileBan in der Datenbank gesucht.
	 * 
	 * @author Philipp Schmitt
	 * @param id
	 *            Die id, zu der der Eintrag aus der DB gelesen werden soll
	 * @return Das durch die id referenzierte ProfileBan-Objekt
	 * 
	 */

	public ProfileBan findByKey(int id) {

		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/**
			 * Statement ausfüllen und als Query an die DB schicken
			 */

			ResultSet rs = stmt.executeQuery("SELECT id, banningProfileId, bannedProfileId, timestamp FROM profileBans "
					+ "WHERE id=" + id + " ORDER BY id");

			/**
			 * Mapping des ggf. ausgelesen Eintrags in ein Java-Objekt
			 */

			if (rs.next()) {

				ProfileBan pb = new ProfileBan();
				pb.setId(rs.getInt("id"));
				pb.setBanningProfileId(rs.getInt("banningProfileId"));
				pb.setBannedProfileId(rs.getInt("bannedProfileId"));
				pb.setTimestamp(rs.getDate("timestamp"));

				return pb;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Read-Methode - Auslesen aller ProfileBans in einen Vektor.
	 * 
	 * @author Philipp Schmitt
	 * @return Liste aller derzeit in der Datenbank eingetragenen
	 *         Kontaktsperren.
	 */

	public Vector<ProfileBan> findAll() {

		/**
		 * DB-Verbindung holen
		 */

		Connection con = DBConnection.connection();

		/**
		 * Ergebnisvektor vorbereiten
		 */

		Vector<ProfileBan> result = new Vector<ProfileBan>();

		try {
			/**
			 * Erzeugen eines neuen SQL-Statements.
			 */
			Statement stmt = con.createStatement();

			/**
			 * Statement ausfüllen und als Query an die DB schicken
			 */

			ResultSet rs = stmt.executeQuery(
					"SELECT id, banningProfileId, bannedProfileId, timestamp FROM profileBans" + "ORDER BY id");

			/**
			 * Für jeden Eintrag im Suchergebnis wird nun ein ProfileBan-Objekt
			 * erstellt. *
			 */

			while (rs.next()) {
				ProfileBan pb = new ProfileBan();
				pb.setId(rs.getInt("id"));
				pb.setBanningProfileId(rs.getInt("banningProfileId"));
				pb.setBannedProfileId(rs.getInt("bannedProfileId"));
				pb.setTimestamp(rs.getDate("timestamp"));

				/**
				 * Hinzufügen des neuen Objekts zum Ergebnisvektor
				 */

				result.addElement(pb);
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
	 * Read-Methode - Auslesen aller ProfileBans (Kontaktsperren), die vom
	 * übergebenen Profil je erstellt wurden.
	 * 
	 * @author Philipp Schmitt
	 * @param banningProfileId
	 *            Id des Profils, zu dem alle Kontaktsperren gesucht werden
	 *            sollen.
	 * @return Liste aller von dem zu überprüfenden Profil erstellten
	 *         Kontaktsperren.
	 */
	public ArrayList<ProfileBan> findBannedProfiles(int banningProfileId) {

		Connection con = DBConnection.connection();
		// Ergebnisvektor vorbereiten
		ArrayList<ProfileBan> result = new ArrayList<ProfileBan>();

		ProfileMapper profileMapper = null;
		profileMapper = ProfileMapper.profileMapper();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, banningProfileId, bannedProfileId, timestamp FROM profileBans"
					+ " WHERE banningProfileId=" + banningProfileId + " ORDER BY timestamp");

			System.out.println("SELECT id, banningProfileId, bannedProfileId, timestamp FROM profileBans"
					+ "WHERE banningProfileId=" + banningProfileId + " ORDER BY timestamp");

			/**
			 * Für jeden Eintrag im Suchergebnis wird nun ein Customer-Objekt
			 * erstellt.
			 */

			while (rs.next()) {
				ProfileBan pb = new ProfileBan();
				pb.setId(rs.getInt("id"));
				pb.setBanningProfileId(rs.getInt("banningProfileId"));
				pb.setBannedProfileId(rs.getInt("bannedProfileId"));
				pb.setTimestamp(rs.getDate("timestamp"));
				pb.setBanningProfile(profileMapper.findByKey(pb.getBanningProfileId()));
				pb.setBannedProfile(profileMapper.findByKey(pb.getBanningProfileId()));
				/**
				 * Hinzufügen des neuen Objekts zum Ergebnisvektor
				 */

				System.out.println("ProfileBan Id: " + pb.getId() + "Banned Profile Id " + pb.getBannedProfileId());

				result.add(pb);
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
	 * Read-Methode - Abfrage, ob zwischen zwei Profilen eine Kontaktsperre
	 * besteht. (einseitig)
	 * 
	 * @param banningProfileId
	 *            Eindeutige id des Profils, das die Kontaktsperre erstellt
	 *            haben soll.
	 * @param checkedProfileId
	 *            Eindeutige id des Profils, für das die Kontaktsperre erstellt
	 *            worden sein soll.
	 * @return Boolean, ob eine Kontaktsperre vom bannenden Profil für ein zu
	 *         checkendes Profil erstellt wurde.
	 */
	public boolean isProfileBanned(int banningProfileId, int checkedProfileId) {
		/**
		 * Datenbankverbindung aufbauen
		 */
		Connection con = DBConnection.connection();

		/**
		 * Vorbereiten der Ergebnisvariable
		 */
		boolean result = false;

		try {

			/**
			 * Erstellung des SQL-Statements
			 */
			Statement stmt = con.createStatement();

			/**
			 * Übergabe und Ausführung der Abfrage
			 */
			ResultSet rs = stmt.executeQuery("SELECT id, banningProfileId, bannedProfileId, timestamp FROM profileBans"
					+ " WHERE banningProfileId=" + banningProfileId + " AND bannedProfileId=" + checkedProfileId
					+ " ORDER BY timestamp");

			/**
			 * Check, ob ein Ergebnis zurückkam. Falls ja, ist das Profil
			 * gebannt.
			 */
			if (rs.next()) {
				result = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		/**
		 * Rückgabe der Ergebnisvariable
		 */
		return result;

	}

	/**
	 * Insert-Methode - Ein ProfileBan-Objekt pb wird übergeben und die
	 * zugehörigen Werte in ein SQL-Statement geschrieben, welches ausgeführt
	 * wird, um das Objekt in die Datenbank einzutragen.
	 * 
	 * @author Philipp Schmitt
	 * @param pb
	 *            Kontaktsperre, die in die Datenbank geschrieben werden soll
	 * @return ProfileBan-Objekt, das in die Datenbank geschrieben wurde
	 */

	public ProfileBan insert(ProfileBan pb) {

		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/**
			 * Zunächst schauen wir nach, welches der momentan höchste
			 * Primärschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM profileBans ");

			/**
			 * Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
			 */
			if (rs.next()) {

				/**
				 * pb erhält den bisher maximalen, nun um 1 inkrementierten
				 * Primärschlüssel.
				 */

				pb.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd");
				Date currentDate = new Date();
				String date = mySQLformat.format(currentDate);

				/**
				 * Jetzt erst erfolgt die tatsächliche Einfügeoperation
				 */

				stmt.executeUpdate("INSERT INTO profileBans (id, banningProfileId, bannedProfileId, timestamp) "
						+ "VALUES (" + pb.getId() + ",'" + pb.getBanningProfileId() + "','" + pb.getBannedProfileId()
						+ "','" + date + "')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pb;
	}

	/**
	 * Delete-Methode - Ein ProfileBan-Objekt wird übergeben und dieses aus der
	 * DB gelöscht.
	 * 
	 * @author Philipp Schmitt
	 * @param pb
	 *            Kontaktsperre, die aufgehoben werden soll
	 */

	public void delete(ProfileBan pb) {

		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/**
			 * Statement ausfüllen und als Query an die DB schicken. Löschung
			 * erfolgt.
			 */

			stmt.executeUpdate("DELETE FROM profileBans " + "WHERE banningProfileId=" + pb.getBanningProfileId()
					+ " AND bannedProfileId=" + pb.getBannedProfileId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Delete-Methode zur Profillöschung. Ein Profilobjekt profile soll komplett
	 * aus der Datenbank gelöscht werden und hiermit auch die entsprechenden
	 * Referenzen in der profileBan-Tabelle.
	 * 
	 * @author Philipp Schmitt
	 * @param profile
	 *            Profil, das komplett aus der Datenbank gelöscht wird
	 */

	public void delete(Profile profile) {

		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/**
			 * Statement ausfüllen und als Query an die DB schicken. Löschung
			 * erfolgt.
			 */

			stmt.executeUpdate("DELETE FROM profileBans " + "WHERE banningProfileId=" + profile.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
