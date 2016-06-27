package de.hdm.core.shared.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.ui.HTML;

import de.hdm.core.shared.bo.Information;
import de.hdm.core.shared.bo.Profile;

/**
 * Report, der alle Konten alle Kunden darstellt. Die Klasse tr#gt keine
 * weiteren Attribute- und Methoden-Implementierungen, da alles Notwendige schon
 * in den Superklassen vorliegt. Ihre Existenz ist dennoch wichtig, um bestimmte
 * Typen von Reports deklarieren und mit ihnen objektorientiert umgehen zu
 * kÃ¶nnen.
 * 
 */
public class HTMLProfilesReport {

	//Aufbau der HTML Seite
		private static String generateReportHead() {
			return "<html>"
					+ "<head>"
					+ "<title></title>"
					+ "</head>"
					+ "<body>";
		}
		
		private static String generateReportEnd(String currentReport) {
			return currentReport + "</body></html>";
		}
		
		//Aufbau der Tabelle
		public static HTML generateAllProfilesReport(ArrayList<Profile> searchResult) {
			String report = generateReportHead();
			
			report += "<div>";
			
			// Hinzufügen des aktuellen Datums mit Uhrzeit für die Überschrift des Reports.
			Date today = new Date();
		    DateTimeFormat fmt = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm:ss");

		    report += "<br>";
			report += "Profile Report, generated at " + fmt.format(today) + "<br>";
			report += "<br>";
			report += "<table id=\"reporttable\">"
					+ "<tr>"
					+ "<th id=\"profile\">First Name</th>"
					+ "<th id=\"profile\">Last Name</th>"
					+ "<th id=\"profile\">Gender</th>"
					+ "<th id=\"profile\">Age</th>"
					+ "<th id=\"profile\">Body Height</th>"
					+ "<th id=\"profile\">Hair Colour</th>"
					+ "<th id=\"profile\">Smoker</th>"
					+ "<th id=\"profile\">Confession</th>"
					+ "<th id=\"profile\">Hobbies</th>"
					+ "<th id=\"profile\">Self-Description</th>"
					+ "<th id=\"profile\">Favorite Band</th>"
					+ "<th id=\"profile\">Favorite Era</th>"
					+ "<th id=\"profile\">Associated Subculture</th>"
					+ "<th id=\"profile\">Favorite Movies</th>"
					+ "<th id=\"profile\">Strong Points</th>"
					+ "</tr>";
			
			for(Profile p : searchResult) {
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
				
				report += "<tr id=\"spalten\">"
						+ "<td id=\"zelle\">" + p.getName() + " " + ""+ p.getLastName() +"</td>"
						+ "<td id=\"zelle\">" + p.getGender() + "</td>"
						+ "<td id=\"zelle\">" + String.valueOf(age) +"</td>"
						+ "<td id=\"zelle\">" + String.valueOf(p.getBodyHeight()) + "</td>"
						+ "<td id=\"zelle\">" + p.getHairColour() +"</td>"
						+ "<td id=\"zelle\">" + smoker + "</td>"
						+ "<td id=\"zelle\">" + p.getConfession() +"</td>"
						+ "<td id=\"zelle\">" + resultHobbies.toString() + "</td>"
						+ "<td id=\"zelle\">" + p.getDescriptionList().get(0).getInformationValues().get(0).getValue() +"</td>"
						+ "<td id=\"zelle\">" + p.getDescriptionList().get(1).getInformationValues().get(0).getValue() + "</td>"
						+ "<td id=\"zelle\">" + resultEra.toString() +"</td>"
						+ "<td id=\"zelle\">" + resultCulture.toString() + "</td>"
						+ "<td id=\"zelle\">" + p.getDescriptionList().get(2).getInformationValues().get(0).getValue() +"</td>"
						+ "<td id=\"zelle\">" + resultPoints.toString() + "</td>"
						+ "</tr>";
			}
			
			report += "</table>";
			
			report += "</div>";
			
			report = generateReportEnd(report);
			return new HTML(report);
	}

}