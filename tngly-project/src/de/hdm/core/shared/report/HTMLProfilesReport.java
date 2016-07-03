package de.hdm.core.shared.report;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.ui.HTML;

import de.hdm.core.shared.bo.Information;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.SearchProfile;

/**
 * In dieser Klasse wird der Report fuer die beiden Abfragen (alle Profile oder
 * nur ungesehene Profile) in HTML aufgebaut. Die Ausgabe der beiden Abfragen
 * erfolgt in einer Tabelle mit 17 Spalten (Username, First Name, Last Name,
 * Gender, Age, Body Height, HairColour, Smoker, Confession, Hobbies,
 * Self-description, Favorite Band, Strong Points, Favorite Movie, Associated
 * Subculture, Favorite Movie, Favorite Era, Similiarity), in denen alle
 * Eigenschaften eines Profils ausgegeben werden.
 * 
 * @author Kevin Jaeger
 *
 */
public class HTMLProfilesReport {

	/**
	 * Gibt den Titel des Berichtes in HTML zurueck.
	 * 
	 * @return
	 */
	private static String generateReportHead() {
		return "<html>" + "<head>" + "<title></title>" + "</head>" + "<body>";
	}

	/**
	 * Gibt das Ende des Berichtes in HTML zurueck.
	 * 
	 * @param currentReport
	 *            der aktuelle Stand des Berichtes
	 * @return
	 */
	private static String generateReportEnd(String currentReport) {
		return currentReport + "</body></html>";
	}

	/*
	 * Aufbau der HTML-Tabelle (plus Datum & Ausgabe des Suchprofils)
	 */
	/**
	 * Gibt einen Teil des Berichtes (bestehend aus Datum, Suchprofil und
	 * Abfrageergebnis der beiden Abfragen) in HTML zurueck.
	 * 
	 * @param searchResult
	 *            gefundene und verglichene Profile (sortiert nach �hnlichkeit)
	 * @param searchProfile
	 *            eingegebene Kritierien zur Suche nach Profilen (Geschlecht,
	 *            Alter, K�rpergr��e, Haarfarbe, Raucher, Konfession)
	 * @return
	 */
	public static HTML generateAllProfilesReport(ArrayList<Profile> searchResult, SearchProfile searchProfile) {
		String report = generateReportHead();

		report += "<div>";

		/*
		 * Korrekte Formatierung des Datums mit Uhrzeit f�r die Darstellung im Bericht 
		 */
		Date today = new Date();
		DateTimeFormat fmt = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm:ss");

		/*
		 * Auslesen des Rauchverhaltens aus dem Suchprofil zur Darstellung im Bericht
		 */
		String smokerSearchProfile;
		if (searchProfile.getIsSmoking() == 0) {
			smokerSearchProfile = "No";
		} else {
			smokerSearchProfile = "Yes";
		}

		/*
		 * Einf�gen des Datums in den Bericht
		 */
		report += "<br>";
		report += "Generated at " + fmt.format(today) + "<br>";
		report += "<br>";
		/*
		 * Einf�gen des Suchprofils in den Bericht
		 */
		report += "Search Profile ||   Gender: " + searchProfile.getGender() + "  |  Age Range: " + searchProfile.getAgeRangeFrom() + " - " + searchProfile.getAgeRangeTo() + "  |  BodyHeight: " + searchProfile.getBodyHeightFrom() + "m - " + searchProfile.getBodyHeightTo() + "m";
		report += "  |  Haircolour: " + searchProfile.getHairColour() + "  |  Smoker: " + smokerSearchProfile + "  |  Confession: " + searchProfile.getConfession();

		// report += "Confession: " + searchProfile.getConfession() + "<br>";
		report += "<br>";
		/*
		 * Einf�gen der �berschriften der Tabelle in den Bericht
		 */
		report += "<div style=\"overflow-x:auto;\">";
		report += "<table id=\"reporttable\">" + "<tr>" + "<th id=\"profile\">Username</th>"
				+ "<th id=\"profile\">First Name</th>" + "<th id=\"profile\">Last Name</th>"
				+ "<th id=\"profile\">Gender</th>" + "<th id=\"profile\">Age</th>"
				+ "<th id=\"profile\">Body Height</th>" + "<th id=\"profile\">Hair Colour</th>"
				+ "<th id=\"profile\">Smoker</th>" + "<th id=\"profile\">Confession</th>"
				+ "<th id=\"profile\">Hobbies</th>" + "<th id=\"profile\">Self-Description</th>"
				+ "<th id=\"profile\">Favorite Band</th>" + "<th id=\"profile\">Strong Points</th>"
				+ "<th id=\"profile\">Associated Subculture</th>" + "<th id=\"profile\">Favorite Movie</th>"
				+ "<th id=\"profile\">Favorite Era</th>" + "<th id=\"profile\">Similiarity</th>" + "</tr>";

		/*
		 * Auslesen & Formatieren der Profileigenschaften f�r eine korrekte Anzeige in der HTML-Tabelle
		 */
		for (Profile p : searchResult) {
			Date dateBirth = p.getDateOfBirth();
			Date dateNow = new Date();
			int age = dateNow.getYear() - dateBirth.getYear();

			String smoker;
			if (p.getIsSmoking() == 0) {
				smoker = "YES";
			} else {
				smoker = "NO";
			}

			StringBuffer resultHobbies = new StringBuffer();
			for (Information i : p.getSelectionList().get(0).getInformationValues()) {
				resultHobbies.append(i.getValue() + "\n");
			}

			StringBuffer resultEra = new StringBuffer();
			for (Information i : p.getSelectionList().get(1).getInformationValues()) {
				resultEra.append(i.getValue() + "\n");
			}

			StringBuffer resultCulture = new StringBuffer();
			for (Information i : p.getSelectionList().get(2).getInformationValues()) {
				resultCulture.append(i.getValue() + "\n");
			}

			StringBuffer resultPoints = new StringBuffer();
			for (Information i : p.getSelectionList().get(3).getInformationValues()) {
				resultPoints.append(i.getValue() + "\n");
			}

			String selfDescription;
			if (!p.getDescriptionList().get(0).getInformationValues().isEmpty()) {
				selfDescription = p.getDescriptionList().get(0).getInformationValues().get(0).getValue();
			} else {
				selfDescription = "";
			}

			String favoriteBand;
			if (!p.getDescriptionList().get(1).getInformationValues().isEmpty()) {
				favoriteBand = p.getDescriptionList().get(1).getInformationValues().get(0).getValue();
			} else {
				favoriteBand = "";
			}

			String favoriteMovie;
			if (!p.getDescriptionList().get(2).getInformationValues().isEmpty()) {
				favoriteMovie = p.getDescriptionList().get(2).getInformationValues().get(0).getValue();
			} else {
				favoriteMovie = "";
			}

			float bhFormatted = (float) (Math.round(p.getBodyHeight() * 100) / 100.0);

			/*
			 * Einf�gen der Eigenschaften eines Profils (ein Profil = eine Zeile) in die HTML-Tabelle
			 */
			report += "<tr id=\"spalten\">" + "<td id=\"zelle\">" + p.getUserName() + "</td>" + "<td id=\"zelle\">"
					+ p.getName() + "</td>" + "<td id=\"zelle\">" + p.getLastName() + "</td>" + "<td id=\"zelle\">"
					+ p.getGender() + "</td>" + "<td id=\"zelle\">" + String.valueOf(age) + "</td>"
					+ "<td id=\"zelle\">" + Float.toString(bhFormatted) + "m" + "</td>" + "<td id=\"zelle\">"
					+ p.getHairColour() + "</td>" + "<td id=\"zelle\">" + smoker + "</td>" + "<td id=\"zelle\">"
					+ p.getConfession() + "</td>" + "<td id=\"zelle\">" + resultHobbies.toString() + "</td>"
					+ "<td id=\"zelle\">" + selfDescription + "</td>" + "<td id=\"zelle\">" + favoriteBand + "</td>"
					+ "<td id=\"zelle\">" + resultEra.toString() + "</td>" + "<td id=\"zelle\">"
					+ resultCulture.toString() + "</td>" + "<td id=\"zelle\">" + favoriteMovie + "</td>"
					+ "<td id=\"zelle\">" + resultPoints.toString() + "</td>" + "<td id=\"zelle\">"
					+ p.getSimiliarityToReference() + "%" + "</td>" + "</tr>";
		}

		/*
		 * Abschliessen der HTML-Tabelle durch Setzen der Tags
		 */
		report += "</table>";
		report += "</div>";
		report += "</div>";

		report = generateReportEnd(report);
		return new HTML(report);
	}
}