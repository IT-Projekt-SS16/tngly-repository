package de.hdm.core.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.ibm.icu.util.Calendar;

import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.SearchProfile;

/**
 * Übernommen & angepasst von: @author Thies
 */

public class ProfileMapper {

	private static ProfileMapper profileMapper = null;

	protected ProfileMapper() {
	}

	public static ProfileMapper profileMapper() {
		if (profileMapper == null) {
			profileMapper = new ProfileMapper();
		}

		return profileMapper;
	}

	public Profile findByName(String id) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT id, userName, name, lastName, dateOfBirth,"
					+ " gender, bodyHeight, hairColour, confession, isSmoking FROM profiles " + "WHERE userName LIKE "
					+ "'" + id + "%'" + " ORDER BY lastName");

			/*
			 * Da id Primärschlüssel ist, kann max. nur ein Tupel
			 * zurückgegeben werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
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

	public Vector<Profile> findAll() {
		Connection con = DBConnection.connection();
		// Ergebnisvektor vorbereiten
		Vector<Profile> result = new Vector<Profile>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, userName, name, lastName, dateOfBirth,"
					+ " gender, bodyHeight, hairColour, confession, isSmoking FROM profiles" + "ORDER BY id");

			// Für jeden Eintrag im Suchergebnis wird nun ein Customer-Objekt
			// erstellt.
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

				// Hinzufügen des neuen Objekts zum Ergebnisvektor
				result.addElement(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Ergebnisvektor zurückgeben
		return result;
	}

	public Profile insert(Profile p) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/*
			 * Zunächst schauen wir nach, welches der momentan höchste
			 * Primärschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM profiles ");

			// Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
			if (rs.next()) {
				/*
				 * c erhält den bisher maximalen, nun um 1 inkrementierten
				 * Primärschlüssel.
				 */
				p.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();
				
				SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd");
				String date = mySQLformat.format(p.getDateOfBirth());
				
				// Jetzt erst erfolgt die tatsächliche Einfügeoperation
				stmt.executeUpdate(
						"INSERT INTO profiles (id, userName, name, lastName, gender, dateOfBirth, bodyHeight, hairColour, confession, isSmoking) "
								+ "VALUES (" + p.getId() + ",'" + p.getUserName() + "','" + p.getName() + "','"
								+ p.getLastName() + "','" + p.getGender() + "','" + date + "','"
								+ p.getBodyHeight() + "','" + p.getHairColour() + "','" + p.getConfession() + "','"
								+ p.getIsSmoking() + "')");
				
				System.out.println("INSERT INTO profiles (id, userName, name, lastName, gender, dateOfBirth, bodyHeight, hairColour, confession, isSmoking) "
						+ "VALUES (" + p.getId() + ",'" + p.getUserName() + "','" + p.getName() + "','"
						+ p.getLastName() + "','" + p.getGender() + "','" + date + "','"
						+ p.getBodyHeight() + "','" + p.getHairColour() + "','" + p.getConfession() + "','"
						+ p.getIsSmoking() + "')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return p;
	}

	public void delete(Profile p) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM profiles " + "WHERE id=" + p.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Profile edit(Profile p) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			
			SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd");
			String date = mySQLformat.format(p.getDateOfBirth());
			
			stmt.executeUpdate("UPDATE profiles " + "SET userName=\"" + p.getUserName() + "\", " + "name=\""
					+ p.getName() + "\", " + "lastName=\"" + p.getLastName() + "\", " + "lastName=\"" + p.getLastName()
					+ "\", " + "dateOfBirth=\"" + date + "\", " + "gender=\"" + p.getGender() + "\", "
					+ "bodyHeight=\"" + p.getBodyHeight() + "\", " + "hairColour=\"" + p.getHairColour() + "\", "
					+ "confession=\"" + p.getConfession() + "\", " + "isSmoking=\"" + p.getIsSmoking() + "WHERE id="
					+ p.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Um Analogie zu insert(Customer c) zu wahren, geben wir c zurück
		return p;
	}

	/*
	 * TODO: Umschreiben der nachfolgenden Methode zur Ausgabe von Profile nach
	 * Vorgabe des "Suchprofils"
	 */
	public ArrayList<Profile> searchProfileByProfile(SearchProfile searchProfile) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		ArrayList<Profile> profiles = new ArrayList<Profile>();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder
					.append("SELECT id, userName, name, lastName, dateOfBirth, FLOOR((DATEDIFF(NOW(), dateOfBirth) / 365.25)) AS age, "
							+ "gender, bodyHeight, hairColour, confession, isSmoking FROM `profiles`");

			boolean and = false;

			if (searchProfile.getGender() != null) {
				if (and == true) {
					stringBuilder.append(" AND ");
				}

				else {
				}
				stringBuilder.append(" WHERE gender='" + searchProfile.getGender() + "'");
				and = true;
			} else {
				and = false;
			}

			// Geburtsdatum
			if (searchProfile.getAgeRangeFrom() != 0 && searchProfile.getAgeRangeTo() != 0) {
				if (and == true) {
					stringBuilder.append(" AND ");
				} else {
				}

				stringBuilder.append("FLOOR((DATEDIFF(NOW(), dateOfBirth) / 365.25)) BETWEEN "
						+ searchProfile.getAgeRangeFrom() + " AND " + searchProfile.getAgeRangeTo());
			}

			// Hier muss die Applikationslogik von Vornherein darauf achten,
			// dass, wenn z.b. nur der von-Wert eingegeben wird, der bis-Wert
			// automatisch aufgefüllt wird & vice versa
			if (searchProfile.getBodyHeightFrom() != 0f && searchProfile.getBodyHeightTo() != 0f) {
				if (and == true) {
					stringBuilder.append(" AND ");
				} else {
				}

				stringBuilder.append("bodyHeight BETWEEN " + searchProfile.getBodyHeightFrom() + " AND "
						+ searchProfile.getBodyHeightTo());
				and = true;
			} else {
				if (and == true) {
					and = true;
				} else {
					and = false;
				}
			}

			// Haarfarbe selektieren

			if (searchProfile.getHairColour() != null) {
				if (and == true) {
					stringBuilder.append(" AND ");
				} else {
				}

				stringBuilder.append("hairColour='" + searchProfile.getHairColour() + "'");
				and = true;
			} else {
				if (and == true) {
					and = true;
				} else {
					and = false;
				}
			}

			// Bekenntnis selektieren

			if (searchProfile.getConfession() != null) {
				if (and == true) {
					stringBuilder.append(" AND ");
				} else {
				}

				stringBuilder.append("confession='" + searchProfile.getConfession() + "'");
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
					stringBuilder.append(" AND ");
				} else {
				}

				stringBuilder.append("isSmoking=" + searchProfile.getIsSmoking());
			}

			stringBuilder.append(" ORDER BY id");

			and = false;

			String preparedStatement = stringBuilder.toString();
			System.out.println(preparedStatement);

			ResultSet rs = stmt.executeQuery(preparedStatement);

			// + "WHERE birthYear BETWEEN " + yearFrom + " AND " + yearTo
			// + " AND " + "gender='" + searchProfile.getGender() + "'" + " AND
			// " + "bodyHeight BETWEEN "
			// + searchProfile.getBodyHeightFrom() + " AND " +
			// searchProfile.getBodyHeightTo()
			// + " AND " + "hairColour='" + searchProfile.getHairColour() + "'"
			// + " AND "
			// + "confession='" + searchProfile.getConfession() + "'" + " AND "
			// + "isSmoking=" + searchProfile.getIsSmoking() + " ORDER BY
			// lastName");

			/*
			 * Da id Primärschlüssel ist, kann max. nur ein Tupel
			 * zurückgegeben werden. Prüfe, ob ein Ergebnis vorliegt.
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
				System.out.println(p.getName() + " " + p.getLastName());
				System.out.println(p.getDateOfBirth());

				// Hinzufügen des neuen Objekts zum Ergebnisvektor
				profiles.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return profiles;

	}

}
