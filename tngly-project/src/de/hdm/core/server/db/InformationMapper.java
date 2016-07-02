package de.hdm.core.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.core.shared.bo.Description;
import de.hdm.core.shared.bo.Information;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.Selection;

/**
 * Die Mapper-Klasse InformationMapper stellt eine Schnittstelle zwischen
 * Applikation und Datenbank dar. Die zu persistierenden Informationswerte werden hier auf
 * eine relationale Ebene projiziert. Die abzurufenden Informationswerte werden aus den
 * relationalen Tabellen zusammengestellt.
 * 
 * @author Philipp Schmitt
 */
public class InformationMapper {

	/**
	 * Mithilfe des <code>protected</code>-Attributs im Konstruktor wird
	 * verhindert, dass von anderen Klassen eine neue Instanz der Klasse
	 * geschaffen werden kann.
	 */

	protected InformationMapper() {
	}

	/**
	 * Aufruf eines Information-Mappers für Klassen, die keinen Zugriff auf den
	 * Konstruktor haben.
	 * 
	 * @return Einzigartige Mapper-Instanz zur Benutzung in der
	 *         Applikationsschicht
	 */

	public static InformationMapper informationMapper() {
		if (informationMapper == null) {
			informationMapper = new InformationMapper();
		}

		return informationMapper;
	}

	/**
	 * Instanziieren des Mappers
	 */

	private static InformationMapper informationMapper = null;

	/**
	 * Insert-Methode - Ein Informationsobjekt wird übergeben und die
	 * zugehörigen Werte in ein SQL-Statement geschrieben, welches ausgeführt
	 * wird, um das Objekt in die Datenbank einzutragen.
	 * 
	 * @author Philipp Schmitt
	 * @param in
	 *            In die DB zu schreibendes Information-Objekt
	 * @return In die DB geschriebenes Information-Objekt
	 */

	public Information insert(Information in) {

		/**
		 * Aufbauen der DB-Verbindung & Erzeugen eines neuen SQL-Statements.
		 */
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/**
			 * Suchen des aktuell höchsten Primärschlüsselwertes
			 */

			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM information");

			if (rs.next()) {

				/**
				 * Erhöhung dieses Werts um 1
				 */

				in.setId(rs.getInt("maxid") + 1);

				/**
				 * Vorbereiten eines neuen SQL-Statements
				 */
				stmt = con.createStatement();

				/**
				 * Statement ausfüllen und als Query an die DB schicken
				 */
				stmt.executeUpdate(
						"INSERT INTO information (id, value, propertyId, profileId) " + "VALUES (" + in.getId() + ",'"
								+ in.getValue() + "','" + in.getPropertyId() + "','" + in.getProfileId() + "')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/**
		 * Rückgeben des in die Datenbank geschriebenen Information-Objekts
		 */
		return in;
	}

	/**
	 * Read-Methode zur Befüllung einer beliebigen Anzahl an Profilen mit den
	 * korrespendierenden Werten / Einträgen aus der information - Tabelle.
	 * 
	 * @author Philipp Schmitt
	 * @param profiles
	 *            Ein Array an Profilen, zu denen die zugehörigen
	 *            Informationswerte ausgelesen werden sollen
	 * @return Das Profil-Array mit den ergänzten Informationswerten.
	 */
	public ArrayList<Profile> searchForInformationValues(ArrayList<Profile> profiles) {

		/**
		 * DB-Verbindung holen
		 */
		Connection con = DBConnection.connection();

		/**
		 * Durchlaufen jedes Profil-Objekts in der übergebenen Liste
		 */
		for (Profile p : profiles) {

			/**
			 * Durchlaufen jedes frei zu beschreibenden Eigenschaft-Objekts (
			 * <code>Description</code>) des Profils
			 */
			for (Description d : p.getDescriptionList()) {

				try {
					/**
					 * Leeres SQL-Statement (<code>JDBC</code>) anlegen
					 */
					Statement stmt = con.createStatement();

					/**
					 * Anlegen eines temporären Zwischenspeichers für die
					 * ausgelesenen Informationswerte
					 */
					ArrayList<Information> informationValuesTemp = new ArrayList<Information>();

					/**
					 * Statement ausfüllen und als Query an die DB schicken
					 */
					String sql0 = "SELECT id, value, profileId, propertyId FROM information WHERE profileId="
							+ p.getId() + " AND propertyId=" + d.getId();
					ResultSet rs = stmt.executeQuery(sql0);

					/**
					 * Mapping jedes aus der DB gelesenen Datensatzes in
					 * Java-Objekte
					 */
					while (rs.next()) {
						Information i = new Information();
						i.setId(rs.getInt("id"));
						i.setValue(rs.getString("value"));
						i.setProfileId(rs.getInt("profileId"));
						i.setPropertyId(rs.getInt("propertyId"));

						/**
						 * Informationsobjekt wird zur ArrayList hinzugefügt
						 */
						informationValuesTemp.add(i);
					}

					/**
					 * Das vollkommen ausgefüllte Set an vorhandenen
					 * Informationen wird der jeweiligen
					 * Beschreibungs-Eigenschaft des Profils als ArrayList
					 * übergeben
					 */
					d.setInformationValues(informationValuesTemp);

				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}

				/**
				 * Durchlaufen jedes auszuwählenden Eigenschaft-Objekts (
				 * <code>Selection</code>) des Profils
				 */
				for (Selection s : p.getSelectionList()) {

					try {
						/**
						 * Leeres SQL-Statement (JDBC) anlegen
						 */
						Statement stmt = con.createStatement();
						ArrayList<Information> informationValuesTemp = new ArrayList<Information>();

						/**
						 * Statement ausfüllen und als Query an die DB schicken
						 */
						String sql1 = "SELECT id, value, profileId, propertyId FROM information WHERE profileId="
								+ p.getId() + " AND propertyId=" + s.getId();
						ResultSet rs = stmt.executeQuery(sql1);

						/**
						 * Mapping jedes aus der DB gelesenen Datensatzes in
						 * Java-Objekte
						 */
						while (rs.next()) {
							Information i = new Information();
							i.setId(rs.getInt("id"));
							i.setValue(rs.getString("value"));
							i.setProfileId(rs.getInt("profileId"));
							i.setPropertyId(rs.getInt("propertyId"));

							/**
							 * Informationsobjekt wird zur ArrayList hinzugefügt
							 */
							informationValuesTemp.add(i);
						}

						/**
						 * Das vollkommen ausgefüllte Set an vorhandenen
						 * Informationen wird der jeweiligen Auswahl-Eigenschaft
						 * als ArrayList übergeben
						 */
						s.setInformationValues(informationValuesTemp);

					} catch (SQLException e) {
						e.printStackTrace();
						return null;
					}

				}
			}
		}
		return profiles;
	}

	/**
	 * Edit-Methode - Ein Profil wird übergeben und die zugehörigen Werte in ein
	 * SQL-Statement geschrieben, welches ausgeführt wird, um die
	 * Informationswerte des Profils in der Datenbank zu aktualisieren.
	 * 
	 * @author Philipp Schmitt
	 * @param profile
	 *            Das Profil, dessen Informationswerte geändert werden soll.
	 */

	public void edit(Profile profile) {
		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */
		Connection con = DBConnection.connection();

		/**
		 * Durchlaufen jedes frei zu beschreibenden Eigenschaft-Objekts (
		 * <code>Description</code>) des Profils
		 */
		for (Description d : profile.getDescriptionList()) {

			/**
			 * Prüfen, ob für die Eigenschaft ein Wert hinterlegt ist
			 */

			if (d.getInformationValues().get(0).getValue() != null) {

				/**
				 * Falls ja, soll geprüft werden, ob dieser dem Wert, der
				 * bereits in der DB hinterlegt ist, entspricht.
				 */
				try {
					Statement stmt = con.createStatement();
					/**
					 * Statement ausfüllen und als Query an die DB schicken.
					 */
					String sqlS = ("SELECT * FROM information " + "WHERE profileId=" + profile.getId()
							+ " AND propertyId=" + d.getId());
					ResultSet rs = stmt.executeQuery(sqlS);

					/**
					 * Falls die Eigenschaft bereits mit einem Informationswert
					 * in der DB hinterlegt ist, wird dieser Eintrag geupdatet.
					 * Falls bisher kein Informationswert mit der Eigenschaft
					 * verknüpft war, wird hier ein neuer Eintrag angelegt.
					 */
					if (rs.next() && rs.getString("value") != d.getInformationValues().get(0).getValue()) {
						System.out.println("UPDATE information " + "SET value=\""
								+ d.getInformationValues().get(0).getValue() + "\" WHERE id=" + rs.getInt("id"));
						stmt.executeUpdate("UPDATE information " + "SET value=\""
								+ d.getInformationValues().get(0).getValue() + "\" WHERE id=" + rs.getInt("id"));
					} else {
						this.insert(d.getInformationValues().get(0));
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			/**
			 * Falls für die Eigenschaft im übergebenen Profil kein
			 * Informationswert hinterlegt ist, sollen die bisherigen Einträge
			 * aus der Datenbank gelöscht werden.
			 */
			if (d.getInformationValues().get(0).getValue() == null) {
				try {
					Statement stmt = con.createStatement();
					/**
					 * Statement ausfüllen und als Query an die DB schicken.
					 */
					String sqlS = ("SELECT * FROM information " + "WHERE profileId=" + profile.getId()
							+ " AND propertyId=" + d.getId());
					ResultSet rs = stmt.executeQuery(sqlS);

					/**
					 * Falls bisher ein Informationswert für die Eigenschaft
					 * hinterlegt war, mappen dieses Objekts und Weitergabe zur
					 * Löschung
					 */
					if (rs.next()) {
						Information in = new Information();
						in.setId(rs.getInt("id"));
						this.delete(in);
					} else {
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * Durchlaufen jedes auszuwählenden Eigenschaft-Objekts (
		 * <code>Selection</code>) des Profils
		 */
		for (Selection s : profile.getSelectionList()) {

			/**
			 * Pauschales Löschen aller bisherigen Einträge mit
			 * Informationswerten zu Auswahleigenschaften, die dem Profil bisher
			 * zugeordnet waren
			 */
			try {
				Statement stmt = con.createStatement();
				/**
				 * Statement ausfüllen und als Query an die DB schicken.
				 * Löschung erfolgt.
				 */
				String sqlS = ("DELETE FROM information " + "WHERE profileId=" + profile.getId() + " AND propertyId="
						+ s.getId());
				System.out.println("SELECT * FROM information " + "WHERE profileId=" + profile.getId()
						+ " AND propertyId=" + s.getId());
				stmt.executeUpdate(sqlS);

				/**
				 * Falls für die Auswahleigenschaften im übergebenen Profil
				 * Werte hinterlegt sind, werden diese nun der Datenbank neu
				 * hinzugefügt.
				 */
				if (!s.getInformationValues().isEmpty()) {
					for (Information i : s.getInformationValues()) {

						insert(i);
					}
				} else {
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Delete-Methode - Ein Informationsobjekt in wird übergeben und die
	 * zugehörigen Werte in ein SQL-Statement geschrieben, welches ausgeführt
	 * wird, um das Objekt aus der Datenbank zu entfernen.
	 * 
	 * @author Philipp Schmitt
	 * @param in
	 *            Das Informationsobjekt, das aus der DB gelöscht werden soll
	 */

	public void delete(Information in) {

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
			stmt.executeUpdate("DELETE FROM information " + "WHERE id=" + in.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Delete-Methode zur Profillöschung. Ein Profilobjekt profile soll komplett
	 * aus der Datenbank gelöscht werden und hiermit auch die entsprechenden
	 * Referenzen in der information-Tabelle.
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

			stmt.executeUpdate("DELETE FROM information " + "WHERE profileId=" + profile.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
