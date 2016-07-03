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
	 * Aufruf eines Information-Mappers fuer Klassen, die keinen Zugriff auf den
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
	 * Insert-Methode - Ein Informationsobjekt wird uebergeben und die
	 * zugehoerigen Werte in ein SQL-Statement geschrieben, welches ausgefuehrt
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
			 * Suchen des aktuell hoechsten Primaerschluesselwertes
			 */

			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM information");

			if (rs.next()) {

				/**
				 * Erhoehung dieses Werts um 1
				 */

				in.setId(rs.getInt("maxid") + 1);

				/**
				 * Vorbereiten eines neuen SQL-Statements
				 */
				stmt = con.createStatement();

				/**
				 * Statement ausfuellen und als Query an die DB schicken
				 */
				stmt.executeUpdate(
						"INSERT INTO information (id, value, propertyId, profileId) " + "VALUES (" + in.getId() + ",'"
								+ in.getValue() + "','" + in.getPropertyId() + "','" + in.getProfileId() + "')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/**
		 * Rueckgeben des in die Datenbank geschriebenen Information-Objekts
		 */
		return in;
	}

	/**
	 * Read-Methode zur Befuellung einer beliebigen Anzahl an Profilen mit den
	 * korrespendierenden Werten / Eintraegen aus der information - Tabelle.
	 * 
	 * @author Philipp Schmitt
	 * @param profiles
	 *            Ein Array an Profilen, zu denen die zugehoerigen
	 *            Informationswerte ausgelesen werden sollen
	 * @return Das Profil-Array mit den ergaenzten Informationswerten.
	 */
	public ArrayList<Profile> searchForInformationValues(ArrayList<Profile> profiles) {

		/**
		 * DB-Verbindung holen
		 */
		Connection con = DBConnection.connection();

		/**
		 * Durchlaufen jedes Profil-Objekts in der uebergebenen Liste
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
					 * Anlegen eines temporaeren Zwischenspeichers fuer die
					 * ausgelesenen Informationswerte
					 */
					ArrayList<Information> informationValuesTemp = new ArrayList<Information>();

					/**
					 * Statement ausfuellen und als Query an die DB schicken
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
						 * Informationsobjekt wird zur ArrayList hinzugefuegt
						 */
						informationValuesTemp.add(i);
					}

					/**
					 * Das vollkommen ausgefuellte Set an vorhandenen
					 * Informationen wird der jeweiligen
					 * Beschreibungs-Eigenschaft des Profils als ArrayList
					 * uebergeben
					 */
					d.setInformationValues(informationValuesTemp);

				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}

				/**
				 * Durchlaufen jedes auszuwaehlenden Eigenschaft-Objekts (
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
						 * Statement ausfuellen und als Query an die DB schicken
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
							 * Informationsobjekt wird zur ArrayList hinzugefuegt
							 */
							informationValuesTemp.add(i);
						}

						/**
						 * Das vollkommen ausgefuellte Set an vorhandenen
						 * Informationen wird der jeweiligen Auswahl-Eigenschaft
						 * als ArrayList uebergeben
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
	 * Edit-Methode - Ein Profil wird uebergeben und die zugehoerigen Werte in ein
	 * SQL-Statement geschrieben, welches ausgefuehrt wird, um die
	 * Informationswerte des Profils in der Datenbank zu aktualisieren.
	 * 
	 * @author Philipp Schmitt
	 * @param profile
	 *            Das Profil, dessen Informationswerte geaendert werden soll.
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
			 * Pruefen, ob fuer die Eigenschaft ein Wert hinterlegt ist
			 */

			if (d.getInformationValues().get(0).getValue() != null) {

				/**
				 * Falls ja, soll geprueft werden, ob dieser dem Wert, der
				 * bereits in der DB hinterlegt ist, entspricht.
				 */
				try {
					Statement stmt = con.createStatement();
					/**
					 * Statement ausfuellen und als Query an die DB schicken.
					 */
					String sqlS = ("SELECT * FROM information " + "WHERE profileId=" + profile.getId()
							+ " AND propertyId=" + d.getId());
					ResultSet rs = stmt.executeQuery(sqlS);

					/**
					 * Falls die Eigenschaft bereits mit einem Informationswert
					 * in der DB hinterlegt ist, wird dieser Eintrag geupdatet.
					 * Falls bisher kein Informationswert mit der Eigenschaft
					 * verknuepft war, wird hier ein neuer Eintrag angelegt.
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
			 * Falls fuer die Eigenschaft im uebergebenen Profil kein
			 * Informationswert hinterlegt ist, sollen die bisherigen Eintraege
			 * aus der Datenbank geloescht werden.
			 */
			if (d.getInformationValues().get(0).getValue() == null) {
				try {
					Statement stmt = con.createStatement();
					/**
					 * Statement ausfuellen und als Query an die DB schicken.
					 */
					String sqlS = ("SELECT * FROM information " + "WHERE profileId=" + profile.getId()
							+ " AND propertyId=" + d.getId());
					ResultSet rs = stmt.executeQuery(sqlS);

					/**
					 * Falls bisher ein Informationswert fuer die Eigenschaft
					 * hinterlegt war, mappen dieses Objekts und Weitergabe zur
					 * Loeschung
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
		 * Durchlaufen jedes auszuwaehlenden Eigenschaft-Objekts (
		 * <code>Selection</code>) des Profils
		 */
		for (Selection s : profile.getSelectionList()) {

			/**
			 * Pauschales Loeschen aller bisherigen Eintraege mit
			 * Informationswerten zu Auswahleigenschaften, die dem Profil bisher
			 * zugeordnet waren
			 */
			try {
				Statement stmt = con.createStatement();
				/**
				 * Statement ausfuellen und als Query an die DB schicken.
				 * Loeschung erfolgt.
				 */
				String sqlS = ("DELETE FROM information " + "WHERE profileId=" + profile.getId() + " AND propertyId="
						+ s.getId());
				System.out.println("SELECT * FROM information " + "WHERE profileId=" + profile.getId()
						+ " AND propertyId=" + s.getId());
				stmt.executeUpdate(sqlS);

				/**
				 * Falls fuer die Auswahleigenschaften im uebergebenen Profil
				 * Werte hinterlegt sind, werden diese nun der Datenbank neu
				 * hinzugefuegt.
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
	 * Delete-Methode - Ein Informationsobjekt in wird uebergeben und die
	 * zugehoerigen Werte in ein SQL-Statement geschrieben, welches ausgefuehrt
	 * wird, um das Objekt aus der Datenbank zu entfernen.
	 * 
	 * @author Philipp Schmitt
	 * @param in
	 *            Das Informationsobjekt, das aus der DB geloescht werden soll
	 */

	public void delete(Information in) {

		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/**
			 * Statement ausfuellen und als Query an die DB schicken. Loeschung
			 * erfolgt.
			 */
			stmt.executeUpdate("DELETE FROM information " + "WHERE id=" + in.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Delete-Methode zur Profilloeschung. Ein Profilobjekt profile soll komplett
	 * aus der Datenbank geloescht werden und hiermit auch die entsprechenden
	 * Referenzen in der information-Tabelle.
	 * 
	 * @author Philipp Schmitt
	 * @param profile
	 *            Profil, das komplett aus der Datenbank geloescht wird
	 */

	public void delete(Profile profile) {

		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/**
			 * Statement ausfuellen und als Query an die DB schicken. Loeschung
			 * erfolgt.
			 */

			stmt.executeUpdate("DELETE FROM information " + "WHERE profileId=" + profile.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
