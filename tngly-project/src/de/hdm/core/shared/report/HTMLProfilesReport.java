package de.hdm.core.shared.report;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.ui.HTML;

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.shared.bo.Information;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.SearchProfile;

/**
 * Report, der alle Konten alle Kunden darstellt. Die Klasse tr#gt keine
 * weiteren Attribute- und Methoden-Implementierungen, da alles Notwendige schon
 * in den Superklassen vorliegt. Ihre Existenz ist dennoch wichtig, um bestimmte
 * Typen von Reports deklarieren und mit ihnen objektorientiert umgehen zu
 * können.
 * 
 */
public class HTMLProfilesReport {

	private static Logger logger = ClientsideSettings.getLogger();
	
	//Aufbau der HTML Seite
		private static String generateReportHead() {
			return "<html>"
					+ "<head>"
					+ "<title>LOL</title>"
					+ "</head>"
					+ "<body>";
		}
		
		private static String generateReportEnd(String currentReport) {
			return currentReport + "</body></html>";
		}
		
		//Aufbau der Tabelle
		public static HTML generateAllProfilesReport(ArrayList<Profile> searchResult, SearchProfile searchProfile) {
			String report = generateReportHead();
			
			report += "<div>";
			
			logger.info("Zeile 45 HTMLprofilesReport ausgeführt");
			
			// Hinzuf�gen des aktuellen Datums mit Uhrzeit f�r die �berschrift des Reports.
			Date today = new Date();
		    DateTimeFormat fmt = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm:ss");
		    
		    String smokerSearchProfile;
		    if(searchProfile.getIsSmoking() == 0){
		    	smokerSearchProfile = "No";
		    } else {
		    	smokerSearchProfile = "Yes";
		    }

		    report += "<br>";
			report += "Generated at " + fmt.format(today) + "<br>";
			report += "<br>";
			report += "<br>";
			report += "Gender: " + searchProfile.getGender() + "<br>";
			report += "<br>";
			report += "Age Range: " + searchProfile.getAgeRangeFrom() + " - " + searchProfile.getAgeRangeTo() + "<br>";
			report += "<br>";
			report += "Body Height: " + searchProfile.getBodyHeightFrom() + "m" + " - " + searchProfile.getBodyHeightTo() + "m" + "<br>";
			report += "<br>";
			report += "Haircolour: " + searchProfile.getHairColour() + "<br>";
			report += "<br>";
			report += "Smoker: " + smokerSearchProfile + "<br>";
			report += "<br>";
			report += "Confession: " + searchProfile.getConfession() + "<br>";
			report += "<br>";
			report += "<div style=\"overflow-x:auto;\">";
			report += "<table id=\"reporttable\">"
					+ "<tr>"
					+ "<th id=\"profile\">Username</th>"
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
					+ "<th id=\"profile\">Strong Points</th>"
					+ "<th id=\"profile\">Associated Subculture</th>"
					+ "<th id=\"profile\">Favorite Movies</th>"
					+ "<th id=\"profile\">Favorite Era</th>"
					+ "<th id=\"profile\">Similiarity</th>"
					+ "</tr>";
			
			logger.info("Zeile 71 HTMLprofilesReport ausgeführt");
			
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
				if (!p.getDescriptionList().get(0).getInformationValues().isEmpty()){
					selfDescription = p.getDescriptionList().get(0).getInformationValues().get(0).getValue();
				} else {
					selfDescription = "";
				}
				
				String favoriteBand;
				if (!p.getDescriptionList().get(1).getInformationValues().isEmpty()){
					favoriteBand = p.getDescriptionList().get(1).getInformationValues().get(0).getValue();
				} else {
					favoriteBand = "";
				}
				
				String favoriteMovie;
				if (!p.getDescriptionList().get(2).getInformationValues().isEmpty()){
					favoriteMovie = p.getDescriptionList().get(2).getInformationValues().get(0).getValue();
				} else {
					favoriteMovie = "";
				}
				
				float bhFormatted = (float) (Math.round(p.getBodyHeight() * 100) / 100.0);
				
				logger.info("Zeile 103 HTMLprofilesReport ausgeführt");
				
				report += "<tr id=\"spalten\">"
						+ "<td id=\"zelle\">" + p.getUserName() + "</td>"
						+ "<td id=\"zelle\">" + p.getName() + "</td>"
						+ "<td id=\"zelle\">" + p.getLastName() + "</td>"
						+ "<td id=\"zelle\">" + p.getGender() + "</td>"
						+ "<td id=\"zelle\">" + String.valueOf(age) +"</td>"
						+ "<td id=\"zelle\">" + Float.toString(bhFormatted) + "m" + "</td>"
						+ "<td id=\"zelle\">" + p.getHairColour() +"</td>"
						+ "<td id=\"zelle\">" + smoker + "</td>"
						+ "<td id=\"zelle\">" + p.getConfession() +"</td>"
						+ "<td id=\"zelle\">" + resultHobbies.toString() + "</td>"
						+ "<td id=\"zelle\">" + selfDescription +"</td>"
						+ "<td id=\"zelle\">" + favoriteBand + "</td>"
						+ "<td id=\"zelle\">" + resultEra.toString() +"</td>"
						+ "<td id=\"zelle\">" + resultCulture.toString() + "</td>"
						+ "<td id=\"zelle\">" + favoriteMovie +"</td>"
						+ "<td id=\"zelle\">" + resultPoints.toString() + "</td>"
						+ "<td id=\"zelle\">" + p.getSimiliarityToReference() + "%" + "</td>"
						+ "</tr>";
			}
			
			report += "</table>";
			report += "</div>";
			report += "</div>";
			
			logger.info("Zeile 125 HTMLprofilesReport ausgeführt");
			
			report = generateReportEnd(report);
			logger.info("Zeile 134 HTMLprofilesReport ausgeführt");
			logger.info(report);
			return new HTML(report);
	}

}