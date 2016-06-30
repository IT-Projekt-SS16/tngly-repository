package de.hdm.reportgenerator.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.shared.AdministrationServiceAsync;
import de.hdm.core.shared.ReportGeneratorAsync;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.SearchProfile;
import de.hdm.core.shared.report.HTMLProfilesReport;

public class AllProfilesView extends UpdateReportGenerator {
	
	private Profile searchProfile = new Profile();
	private Boolean unseenChecked = null;
	
	private ReportGeneratorAsync reportGenerator = ClientsideSettings.getReportGenerator();
	private AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();
	
	private ScrollPanel scrollPanel = new ScrollPanel();
	
	public AllProfilesView(Boolean unseenChecked, SearchProfile searchProfile){
		this.searchProfile = searchProfile;
		this.unseenChecked = unseenChecked;
		adminService.searchAndCompareProfiles(unseenChecked, searchProfile, new CompareCallback());
	}

	/**
	 * Jeder Showcase besitzt eine einleitende �berschrift, die durch diese
	 * Methode zu erstellen ist.
	 * 
	 * @see Showcase#getHeadlineText()
	 */
	// @Override
	protected String getHeadlineText() {
		return "Show Report";
	}

	/**
	 * Jeder Showcase muss die <code>run()</code>-Methode implementieren. Sie
	 * ist eine "Einschubmethode", die von einer Methode der Basisklasse
	 * <code>ShowCase</code> aufgerufen wird, wenn der Showcase aktiviert wird.
	 */
	@Override
	protected void run() {
		
//		ReportGeneratorAsync reportGenerator = ClientsideSettings.getReportGenerator();
//
//		if (ClientsideSettings.getUnseenOrAll()) {
//			reportGenerator.createAllProfilesReport("Unseen", new AllProfilesReportCallback());
//		} else {
//			reportGenerator.createAllProfilesReport("", new AllProfilesReportCallback());
//		}
//
//		this.append(this.reportText);
	}
	
	class CompareCallback implements AsyncCallback<ArrayList<Profile>> {
		@Override
		public void onFailure(Throwable caught) {
			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
		}

		@Override
		public void onSuccess(ArrayList<Profile> result) {
			scrollPanel.setSize("100%", "100%");
			RootPanel.get("Details").add(scrollPanel);
			scrollPanel.add(HTMLProfilesReport.generateAllProfilesReport(result));
		}

	}
}


//class AllProfilesReportCallback implements AsyncCallback<AllProfilesReport> {
//
//	@Override
//	public void onFailure(Throwable caught) {
//		ClientsideSettings.getLogger().severe("Fehler bei der Abfrage " + caught.getMessage());
//	}
//
//	@Override
//	public void onSuccess(AllProfilesReport report) {
//		if (report != null) {
//			HTMLReportWriter writer = new HTMLReportWriter();
//			writer.process(report);
//			ClientsideSettings.setAllProfilesReport(writer.getReportText());
//		}
//	}
//}
// BankAdministrationAsync bankVerwaltung =
// ClientsideSettings.getBankVerwaltung();

// bankVerwaltung.getCustomerById(1, new GetCustomerCallback(this));
// }
//
// /**
// * <p>
// * Wir nutzen eine Nested Class, um das zur�ckerhaltene Objekt weiter zu
// * bearbeiten.
// * </p>
// * <p>
// * <b>Amerkungen:</b> Eine Nested Class besitzt den Vorteil, die Lokalit�t des
// * Gesamtsystems zu f�rdern, da der Klassenname (hier: "UseCustomer")
// * au�erhalb von DeleteAccountDemo nicht "verbraucht" wird. Doch Vorsicht!
// * Wenn eine Klasse mehrfach, also gewisserma�en an mehreren Stellen im
// * Programm, nutzbar ist, sollte man �berlegen, ob man eine solche Klasse als
// * normale - also separate - Klasse realisiert bzw. anordnet.
// * </p>
// * <p>
// * Weitere Dokumentation siehe <code>CreateAccountDemo.UseCustomer</code>.
// * </p>
// *
// * @see CreateAccountDemo.UseCustomer
// */
// class GetCustomerCallback implements AsyncCallback<Customer> {
// private Showcase showcase = null;
//
// public GetCustomerCallback(Showcase c) {
// this.showcase = c;
// }
//
// @Override
// public void onFailure(Throwable caught) {
// this.showcase.append("Fehler bei der Abfrage " + caught.getMessage());
// }
//
// public void onSuccess(Customer customer) {
// if (customer != null) {
// ReportGeneratorAsync reportGenerator = ClientsideSettings
// .getReportGenerator();
//
// reportGenerator.createAllAccountsOfCustomerReport(customer,
// new AllAccountsOfCustomerReportCallback(this.showcase));
// }
// }
//
// /**
// * <p>
// * Diese Klasse ist eine Nested Classs innerhalb einer Nested Class! Auf
// * diese Weise k�nnen wir einen klassenbezogenen Verarbeitungskontext
// * aufbauen, also gewisserma�en einen klassenbasierter Stack.
// * </p>
// * <p>
// * <b>Erl�uterung:</b> Stellen Sie sich folgende Struktur vor (Syntax frei
// * erfunden):
// *
// * <pre>
// * (Instance of GetCustomerCallback
// *
// * Hier sind s�mtliche Infos zum Kontext nach dem ersten Call bzgl.
// * des Kunden verf�gbar, also als Ergebnis des Calls das Kundenobjekt.
// *
// * (Instance of AllAccountsOfCustomerReportCallback
// *
// * Hier sind zus�tzlich noch die Infos zum Kontext nach dem zweiten
// * Call, also der fertige Report zu dessen Weiterverarbeitung, verf�gbar.
// *
// * )
// * )
// * </pre>
// *
// * </p>
// *
// }
// }
// }
// }
//
// }
// * @author thies
// * @version 1.0
// *
// */
// class AllAccountsOfCustomerReportCallback
// implements AsyncCallback<AllAccountsOfCustomerReport> {
// private Showcase showcase = null;
//
// public AllAccountsOfCustomerReportCallback(Showcase c) {
// this.showcase = c;
// }
//
// @Override
// public void onFailure(Throwable caught) {
// this.showcase.append("Fehler bei der Abfrage " + caught.getMessage());
// }
//
// @Override
// public void onSuccess(AllAccountsOfCustomerReport report) {
// if (report != null) {
// HTMLReportWriter writer = new HTMLReportWriter();
// writer.process(report);
// this.showcase.append(writer.getReportText());
