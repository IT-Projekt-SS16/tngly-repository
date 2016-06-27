package de.hdm.core.server;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.core.shared.AdministrationService;
import de.hdm.core.shared.ReportGenerator;
import de.hdm.core.shared.bo.Information;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.SearchProfile;
import de.hdm.core.shared.report.AllProfilesReport;
import de.hdm.core.shared.report.Column;
import de.hdm.core.shared.report.CompositeParagraph;
import de.hdm.core.shared.report.Row;
import de.hdm.core.shared.report.SimpleParagraph;

@SuppressWarnings("serial")
public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGenerator {

	/**
	 * Ein ReportGenerator benötigt Zugriff auf die BankAdministration, da diese
	 * die essentiellen Methoden für die Koexistenz von Datenobjekten (vgl.
	 * bo-Package) bietet.
	 */
	private AdministrationService administrationService = null;

	/**
	 * <p>
	 * Ein <code>RemoteServiceServlet</code> wird unter GWT mittels
	 * <code>GWT.create(Klassenname.class)</code> Client-seitig erzeugt. Hierzu
	 * ist ein solcher No-Argument-Konstruktor anzulegen. Ein Aufruf eines
	 * anderen Konstruktors ist durch die Client-seitige Instantiierung durch
	 * <code>GWT.create(Klassenname.class)</code> nach derzeitigem Stand nicht
	 * möglich.
	 * </p>
	 * <p>
	 * Es bietet sich also an, eine separate Instanzenmethode zu erstellen, die
	 * Client-seitig direkt nach <code>GWT.create(Klassenname.class)</code>
	 * aufgerufen wird, um eine Initialisierung der Instanz vorzunehmen.
	 * </p>
	 */
	public ReportGeneratorImpl() throws IllegalArgumentException {
	}

	/**
	 * Initialsierungsmethode. Siehe dazu Anmerkungen zum
	 * No-Argument-Konstruktor.
	 * 
	 * @see #ReportGeneratorImpl()
	 */
	public void init() throws IllegalArgumentException {
		/*
		 * Ein ReportGeneratorImpl-Objekt instantiiert für seinen Eigenbedarf
		 * eine BankVerwaltungImpl-Instanz.
		 */
		AdministrationServiceImpl a = new AdministrationServiceImpl();
		a.init();
		this.administrationService = a;
	}

	/**
	 * Auslesen der zugehörigen BankAdministration (interner Gebrauch).
	 * 
	 * @return das BankVerwaltungsobjekt
	 */
	protected AdministrationService getAdministrationService() {
		return this.administrationService;
	}

	@Override
	public AllProfilesReport createAllProfilesReport(Boolean unseenOrAll, SearchProfile searchProfile) throws IllegalArgumentException {
		System.out.println("Start createAllProfilesReport");
		
		/*
		 * Zunächst legen wir uns einen leeren Report an.
		 */
		AllProfilesReport result = new AllProfilesReport();

		/*
		 * Nun werden sämtliche Konten des Kunden ausgelesen und deren Kto.-Nr.
		 * und Kontostand sukzessive in die Tabelle eingetragen.
		 */
//		ArrayList<Profile> profiles = this.getAdministrationService().searchAndCompareProfiles(searchProfile);

		/*
		 * Datum der Erstellung hinzufügen. new Date() erzeugt autom. einen
		 * "Timestamp" des Zeitpunkts der Instantiierung des Date-Objekts.
		 */
		result.setCreated(new Date());

		/*
		 * Ab hier erfolgt die Zusammenstellung der Kopfdaten (die Dinge, die
		 * oben auf dem Report stehen) des Reports. Die Kopfdaten sind
		 * mehrzeilig, daher die Verwendung von CompositeParagraph.
		 */
		CompositeParagraph header = new CompositeParagraph();

		// Name und Vorname des Kunden aufnehmen
		header.addSubParagraph(new SimpleParagraph("The search profile you entered:"));

		// Name und Vorname des Kunden aufnehmen
		header.addSubParagraph(new SimpleParagraph("Gender: " + searchProfile.getGender()));

		// Kundennummer aufnehmen
		header.addSubParagraph(
				new SimpleParagraph("Age Range: " + searchProfile.getAgeRangeFrom() + " To "
						+ searchProfile.getAgeRangeTo()));

		// Name und Vorname des Kunden aufnehmen
		header.addSubParagraph(
				new SimpleParagraph("Body Height Range: " + searchProfile.getBodyHeightFrom()
						+ " To " + searchProfile.getBodyHeightTo()));

		// Name und Vorname des Kunden aufnehmen
		header.addSubParagraph(
				new SimpleParagraph("Hair Colour: " + searchProfile.getHairColour()));

		// Name und Vorname des Kunden aufnehmen
		if (searchProfile.getIsSmoking() == 0) {
			header.addSubParagraph(new SimpleParagraph("Smoker: YES"));
		} else {
			header.addSubParagraph(new SimpleParagraph("Smoker: NO"));
		}

		// Name und Vorname des Kunden aufnehmen
		header.addSubParagraph(
				new SimpleParagraph("Confession: " + searchProfile.getConfession()));

		// Hinzufügen der zusammengestellten Kopfdaten zu dem Report
		result.setHeaderData(header);

		/*
		 * Zunächst legen wir eine Kopfzeile für die Profil-Tabelle an.
		 */
		Row headline = new Row();

		/*
		 * Wir wollen Zeilen mit 2 Spalten in der Tabelle erzeugen. In die erste
		 * Spalte schreiben wir die jeweilige Kontonummer und in die zweite den
		 * aktuellen Kontostand. In der Kopfzeile legen wir also entsprechende
		 * Überschriften ab.
		 */
		headline.addColumn(new Column("First Name"));
		headline.addColumn(new Column("Last Name"));
		headline.addColumn(new Column("Gender"));
		headline.addColumn(new Column("Age"));
		headline.addColumn(new Column("Body Height"));
		headline.addColumn(new Column("Hair Colour"));
		headline.addColumn(new Column("Smoker"));
		headline.addColumn(new Column("Confession"));
		headline.addColumn(new Column("Hobbies"));
		headline.addColumn(new Column("Self-Description"));
		headline.addColumn(new Column("Favorite Band"));
		headline.addColumn(new Column("Favorite Era"));
		headline.addColumn(new Column("Associated Subculture"));
		headline.addColumn(new Column("Favorite Movies"));
		headline.addColumn(new Column("Strong Points"));

		// Hinzufügen der Kopfzeile
		result.addRow(headline);

		if (unseenOrAll) {
			// Jeder Report hat einen Titel (Bezeichnung / Überschrift).
			result.setTitle("Search Results - All Unseen Profiles");

			/*
			 * Ab hier erfolgt ein zeilenweises Hinzufügen von
			 * Konto-Informationen.
			 */

//			foo: for (Profile p : profiles) {
//				if (this.administrationService.wasProfileVisited(p)) {
//					break foo;
//				}
//
//				Date dateBirth = p.getDateOfBirth();
//				Date dateNow = new Date();
//				int age = dateNow.getYear() - dateBirth.getYear();
//
//				// Eine leere Zeile anlegen.
//				Row profileRow = new Row();
//
//				// Erste Spalte: Kontonummer hinzufügen
//				profileRow.addColumn(new Column(p.getName()));
//
//				// Zweite Spalte: Kontostand hinzufügen
//				profileRow.addColumn(new Column(p.getLastName()));
//
//				// Zweite Spalte: Kontostand hinzufügen
//				profileRow.addColumn(new Column(p.getGender()));
//
//				// Zweite Spalte: Kontostand hinzufügen
//				profileRow.addColumn(new Column(String.valueOf(age)));
//
//				// Zweite Spalte: Kontostand hinzufügen
//				profileRow.addColumn(new Column(String.valueOf(p.getBodyHeight())));
//
//				// Zweite Spalte: Kontostand hinzufügen
//				profileRow.addColumn(new Column(p.getHairColour()));
//
//				// Zweite Spalte: Kontostand hinzufügen
//				if (p.getIsSmoking() == 0) {
//					profileRow.addColumn(new Column("YES"));
//				} else {
//					profileRow.addColumn(new Column("NO"));
//				}
//
//				// Zweite Spalte: Kontostand hinzufügen
//				profileRow.addColumn(new Column(p.getConfession()));
//
//				// Zweite Spalte: Kontostand hinzufügen
//				StringBuffer resultHobbies = new StringBuffer();
//				for (Information i : p.getSelectionList().get(1).getInformationValues()) {
//					resultHobbies.append(i.getValue() + "\n");
//				}
//				profileRow.addColumn(new Column(resultHobbies.toString()));
//
//				// Zweite Spalte: Kontostand hinzufügen
//				profileRow
//						.addColumn(new Column(p.getDescriptionList().get(1).getInformationValues().get(1).getValue()));
//
//				// Zweite Spalte: Kontostand hinzufügen
//				profileRow
//						.addColumn(new Column(p.getDescriptionList().get(2).getInformationValues().get(1).getValue()));
//
//				// Zweite Spalte: Kontostand hinzufügen
//				StringBuffer resultEra = new StringBuffer();
//				for (Information i : p.getSelectionList().get(2).getInformationValues()) {
//					resultEra.append(i.getValue() + "\n");
//				}
//				profileRow.addColumn(new Column(resultEra.toString()));
//
//				// Zweite Spalte: Kontostand hinzufügen
//				StringBuffer resultCulture = new StringBuffer();
//				for (Information i : p.getSelectionList().get(3).getInformationValues()) {
//					resultCulture.append(i.getValue() + "\n");
//				}
//				profileRow.addColumn(new Column(resultCulture.toString()));
//
//				// Zweite Spalte: Kontostand hinzufügen
//				profileRow
//						.addColumn(new Column(p.getDescriptionList().get(3).getInformationValues().get(1).getValue()));
//
//				// Zweite Spalte: Kontostand hinzufügen
//				StringBuffer resultPoints = new StringBuffer();
//				for (Information i : p.getSelectionList().get(4).getInformationValues()) {
//					resultPoints.append(i.getValue() + "\n");
//				}
//				profileRow.addColumn(new Column(resultPoints.toString()));
//
//				// und schließlich die Zeile dem Report hinzufügen.
//				result.addRow(profileRow);
//			}
//		} else {
//			// Jeder Report hat einen Titel (Bezeichnung / Überschrift).
//			result.setTitle("Search Results - All Profiles");
//
//			/*
//			 * Ab hier erfolgt ein zeilenweises Hinzufügen von
//			 * Konto-Informationen.
//			 */
//
//			for (Profile p : profiles) {
//
//				Date dateBirth = p.getDateOfBirth();
//				Date dateNow = new Date();
//				int age = dateNow.getYear() - dateBirth.getYear();
//
//				// Eine leere Zeile anlegen.
//				Row profileRow = new Row();
//
//				// Erste Spalte: Kontonummer hinzufügen
//				profileRow.addColumn(new Column(p.getName()));
//
//				// Zweite Spalte: Kontostand hinzufügen
//				profileRow.addColumn(new Column(p.getLastName()));
//
//				// Zweite Spalte: Kontostand hinzufügen
//				profileRow.addColumn(new Column(p.getGender()));
//
//				// Zweite Spalte: Kontostand hinzufügen
//				profileRow.addColumn(new Column(String.valueOf(age)));
//
//				// Zweite Spalte: Kontostand hinzufügen
//				profileRow.addColumn(new Column(String.valueOf(p.getBodyHeight())));
//
//				// Zweite Spalte: Kontostand hinzufügen
//				profileRow.addColumn(new Column(p.getHairColour()));
//
//				// Zweite Spalte: Kontostand hinzufügen
//				if (p.getIsSmoking() == 0) {
//					profileRow.addColumn(new Column("YES"));
//				} else {
//					profileRow.addColumn(new Column("NO"));
//				}
//
//				// Zweite Spalte: Kontostand hinzufügen
//				profileRow.addColumn(new Column(p.getConfession()));
//
//				// Zweite Spalte: Kontostand hinzufügen
//				StringBuffer resultHobbies = new StringBuffer();
//				for (Information i : p.getSelectionList().get(0).getInformationValues()) {
//					resultHobbies.append(i.getValue() + "\n");
//				}
//				profileRow.addColumn(new Column(resultHobbies.toString()));
//
//				// Zweite Spalte: Kontostand hinzufügen
//				profileRow
//						.addColumn(new Column(p.getDescriptionList().get(0).getInformationValues().get(0).getValue()));
//
//				// Zweite Spalte: Kontostand hinzufügen
//				profileRow
//						.addColumn(new Column(p.getDescriptionList().get(1).getInformationValues().get(1).getValue()));
//
//				// Zweite Spalte: Kontostand hinzufügen
//				StringBuffer resultEra = new StringBuffer();
//				for (Information i : p.getSelectionList().get(1).getInformationValues()) {
//					resultEra.append(i.getValue() + "\n");
//				}
//				profileRow.addColumn(new Column(resultEra.toString()));
//
//				// Zweite Spalte: Kontostand hinzufügen
//				StringBuffer resultCulture = new StringBuffer();
//				for (Information i : p.getSelectionList().get(2).getInformationValues()) {
//					resultCulture.append(i.getValue() + "\n");
//				}
//				profileRow.addColumn(new Column(resultCulture.toString()));
//
//				// Zweite Spalte: Kontostand hinzufügen
//				profileRow
//						.addColumn(new Column(p.getDescriptionList().get(2).getInformationValues().get(2).getValue()));
//
//				// Zweite Spalte: Kontostand hinzufügen
//				StringBuffer resultPoints = new StringBuffer();
//				for (Information i : p.getSelectionList().get(3).getInformationValues()) {
//					resultPoints.append(i.getValue() + "\n");
//				}
//				profileRow.addColumn(new Column(resultPoints.toString()));
//
//				// und schließlich die Zeile dem Report hinzufügen.
//				result.addRow(profileRow);
//			}
		}

		/*
		 * Zum Schluss müssen wir noch den fertigen Report zurückgeben.
		 */
		System.out.println(result.getTitle());
		return result;
	}
}
