package de.hdm.core.server.db;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.Description;
import de.hdm.core.shared.bo.Information;
import de.hdm.core.shared.bo.Property;
import de.hdm.core.shared.bo.Selection;

import java.util.Date;

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

	protected InformationMapper() {
	}

	public static InformationMapper informationMapper() {
		if (informationMapper == null) {
			informationMapper = new InformationMapper();
		}

		return informationMapper;
	}

	public Information insert(Information in) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/*
			 * Zunächst schauen wir nach, welches der momentan höchste
			 * Primärschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM information");

			// Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
			if (rs.next()) {
				/*
				 * c erhält den bisher maximalen, nun um 1 inkrementierten
				 * Primärschlüssel.
				 */
				in.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd");
				Date currentDate = new Date();
				String date = mySQLformat.format(currentDate);

				// insert Date as current timestamp yyyy-MM-dd, NICHT VERGESSEN!

				// Jetzt erst erfolgt die tatsächliche Einfügeoperation
				stmt.executeUpdate("INSERT INTO information (id, value, propertyId, profileId, timestamp) " + "VALUES ("
						+ in.getId() + ",'" + in.getValue() + "','" + in.getPropertyId() + "','" + in.getProfileId()
						+ "','" + date + "')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return in;
	}

	public Information edit(Information in) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd");
			Date currentDate = new Date();
			String date = mySQLformat.format(currentDate);

			stmt.executeUpdate("UPDATE information " + "SET value=\"" + in.getValue() + "\", " + "timestamp=\"" + date
					+ " WHERE id=" + in.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Um Analogie zu insert(Customer c) zu wahren, geben wir c zurück
		return in;

	}

	public ArrayList<Profile> searchForInformationValues(ArrayList<Profile> profiles) {

		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		for (Profile p : profiles) {
			for (Description d : p.getDescriptionList()) {

				try {
					// Leeres SQL-Statement (JDBC) anlegen
					Statement stmt = con.createStatement();

					// Statement ausfüllen und als Query an die DB schicken
					String sql0 = "SELECT id, value, profileId, propertyId, timestamp FROM information WHERE profileId="
							+ p.getId() + " AND propertyId=" + d.getId();
					ResultSet rs = stmt.executeQuery(sql0);

					while (rs.next()) {
						Information i = new Information();
						i.setId(rs.getInt("id"));
						i.setValue(rs.getString("value"));
						i.setProfileId(rs.getInt("profileId"));
						i.setPropertyId(rs.getInt("propertyId"));
						System.out.println(d.getInformationValues().toString());
						d.getInformationValues().add(i);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}

				for (Selection s : p.getSelectionList()) {

					try {
						// Leeres SQL-Statement (JDBC) anlegen
						Statement stmt = con.createStatement();

						// Statement ausfüllen und als Query an die DB schicken
						String sql1 = "SELECT id, value, profileId, propertyId, timestamp FROM information WHERE profileId="
								+ p.getId() + " AND propertyId=" + s.getId();
						ResultSet rs = stmt.executeQuery(sql1);

						while (rs.next()) {
							Information i = new Information();
							i.setId(rs.getInt("id"));
							i.setValue(rs.getString("value"));
							i.setProfileId(rs.getInt("profileId"));
							i.setPropertyId(rs.getInt("propertyId"));
							s.getInformationValues().add(i);
						}
					} catch (SQLException e) {
						e.printStackTrace();
						return null;
					}

				}
			}
		}
		return profiles;
	}

	public void delete(Information in) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM information " + "WHERE id=" + in.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void edit(Profile profile) {
		// TODO Auto-generated method stub

	}

	public void delete(Profile profile) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM information " + "WHERE profileId=" + profile.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
