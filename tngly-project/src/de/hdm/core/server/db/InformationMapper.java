package de.hdm.core.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdm.core.shared.bo.Description;
import de.hdm.core.shared.bo.Information;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.Selection;

public class InformationMapper {

	/**
	 * Übernommen & angepasst von: @author Thies
	 */

	public static InformationMapper getInformationMapper() {
		return informationMapper;
	}

	public static void setInformationMapper(InformationMapper informationMapper) {
		InformationMapper.informationMapper = informationMapper;
	}

	private static InformationMapper informationMapper = null;
	private ArrayList<Information> informationValuesTemp = new ArrayList<Information>();

	protected InformationMapper() {
	}

	public static InformationMapper informationMapper() {
		if (informationMapper == null) {
			informationMapper = new InformationMapper();
		}

		return informationMapper;
	}

	/**
	 * Insert-Methode. Ein Informationsobjekt in wird übergeben und die
	 * zugehörigen Werte in ein SQL-Statement geschrieben, welches ausgeführt
	 * wird, um das Objekt in die Datenbank einzutragen.
	 * 
	 */

	public Information insert(Information in) {

		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/**
			 * Der aktuell höchste Primärschlüsselwert wird gesucht...
			 */

			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM information");

			if (rs.next()) {

				/**
				 * und um 1 erhöht.
				 */

				in.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd");
				Date currentDate = new Date();
				String date = mySQLformat.format(currentDate);

				// insert Date as current timestamp yyyy-MM-dd, NICHT VERGESSEN!

				/**
				 * Statement ausfüllen und als Query an die DB schicken
				 */
				System.out
						.println("INSERT INTO information (id, value, propertyId, profileId) " + "VALUES (" + in.getId()
								+ ",'" + in.getValue() + "','" + in.getPropertyId() + "','" + in.getProfileId() + "')");
				stmt.executeUpdate(
						"INSERT INTO information (id, value, propertyId, profileId) " + "VALUES (" + in.getId() + ",'"
								+ in.getValue() + "','" + in.getPropertyId() + "','" + in.getProfileId() + "')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return in;
	}

	/**
	 * Edit-Methode. Ein Informationsobjekt in wird übergeben und die
	 * zugehörigen Werte in ein SQL-Statement geschrieben, welches ausgeführt
	 * wird, um die Werte des Objekts in der Datenbank zu aktualisieren.
	 * 
	 */

	public Information edit(Information in) {

		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd");
			Date currentDate = new Date();
			String date = mySQLformat.format(currentDate);

			/**
			 * Statement ausfüllen und als Query an die DB schicken
			 */

			stmt.executeUpdate("UPDATE information " + "SET value=\"" + in.getValue() + "\", " + "timestamp=\"" + date
					+ " WHERE id=" + in.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		/**
		 * Um Analogie zu insert(Information in) zu wahren, geben wir in zurück.
		 */
		return in;

	}

	public ArrayList<Profile> searchForInformationValues(ArrayList<Profile> profiles) {

		/**
		 * DB-Verbindung holen
		 */
		Connection con = DBConnection.connection();

		for (Profile p : profiles) {
			for (Description d : p.getDescriptionList()) {

				try {
					/**
					 * Leeres SQL-Statement (JDBC) anlegen
					 */
					Statement stmt = con.createStatement();
					ArrayList<Information> informationValuesTemp = new ArrayList<Information>();

					/**
					 * Statement ausfüllen und als Query an die DB schicken
					 */
					System.out.println(
							"INFORMATIONMAPPER ABFRAGE::: SELECT id, value, profileId, propertyId FROM information WHERE profileId="
									+ p.getId() + " AND propertyId=" + d.getId());
					String sql0 = "SELECT id, value, profileId, propertyId FROM information WHERE profileId="
							+ p.getId() + " AND propertyId=" + d.getId();
					ResultSet rs = stmt.executeQuery(sql0);

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
					 * Informationen wird der jeweiligen Eigenschaft als
					 * ArrayList übergeben
					 */
					d.setInformationValues(informationValuesTemp);

				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}

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
						 * Informationen wird der jeweiligen Eigenschaft als
						 * ArrayList übergeben
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
	 * Delete-Methode. Ein Informationsobjekt in wird übergeben und die
	 * zugehörigen Werte in ein SQL-Statement geschrieben, welches ausgeführt
	 * wird, um das Objekt aus der Datenbank zu entfernen.
	 * 
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
			System.out.println("DELETE FROM information " + "WHERE id=" + in.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void edit(Profile profile) {
		/**
		 * DB-Verbindung holen & Erzeugen eines neuen SQL-Statements.
		 */
		Connection con = DBConnection.connection();

		for (Description d : profile.getDescriptionList()) {

			System.out.println(" .. " + d.getInformationValues().get(0).getValue());

			if (d.getInformationValues().get(0).getValue() != null) {

				try {
					Statement stmt = con.createStatement();
					/**
					 * Statement ausfüllen und als Query an die DB schicken.
					 * Löschung erfolgt.
					 */
					String sqlS = ("SELECT * FROM information " + "WHERE profileId=" + profile.getId()
							+ " AND propertyId=" + d.getId());
					System.out.println("SELECT * FROM information " + "WHERE profileId=" + profile.getId()
							+ " AND propertyId=" + d.getId());
					ResultSet rs = stmt.executeQuery(sqlS);

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

			if (d.getInformationValues().get(0).getValue() == null) {
				try {
					Statement stmt = con.createStatement();
					/**
					 * Statement ausfüllen und als Query an die DB schicken.
					 * Löschung erfolgt.
					 */
					String sqlS = ("SELECT * FROM information " + "WHERE profileId=" + profile.getId()
							+ " AND propertyId=" + d.getId());
					ResultSet rs = stmt.executeQuery(sqlS);

					if (rs.next()) {
						System.out.println("Zeile 293 ausgeführt");
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

		for (Selection s : profile.getSelectionList()) {

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

				if (!s.getInformationValues().isEmpty()) {
					for (Information i : s.getInformationValues()) {

						insert(i);
					}
				} else {
					System.out.println("s.getInformationValues ist empty");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Delete-Methode für Profile. Ein Profilobjekt profile wird übergeben und
	 * die zugehörigen Werte in ein SQL-Statement geschrieben, welches
	 * ausgeführt wird, um alle Informationsobjekte zu entfernen, die mit diesem
	 * Profil verknüpft sind.
	 * 
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
