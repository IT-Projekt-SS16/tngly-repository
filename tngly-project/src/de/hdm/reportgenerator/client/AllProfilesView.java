package de.hdm.reportgenerator.client;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.mortbay.log.Log;

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
	
	Logger logger = ClientsideSettings.getLogger();
	
	public AllProfilesView (Boolean unseenChecked, SearchProfile searchProfile){
		
		logger.info("Zeile 33 APV ausgeführt");
		
		this.searchProfile = searchProfile;
		this.unseenChecked = unseenChecked;
		logger.info("Zeile 37 APV ausgeführt");
		adminService.searchAndCompareProfiles(unseenChecked, searchProfile, new CompareCallbackR());
		logger.info("Zeile 39 APV ausgeführt");
	}

	/**
	 * Jeder Showcase besitzt eine einleitende �berschrift, die durch diese
	 * Methode zu erstellen ist.
	 * 
	 * @see Showcase#getHeadlineText()
	 */
	// @Override
	protected String getHeadlineText() {
		return "Show Report - generated";
	}

	/**
	 * Jeder Showcase muss die <code>run()</code>-Methode implementieren. Sie
	 * ist eine "Einschubmethode", die von einer Methode der Basisklasse
	 * <code>ShowCase</code> aufgerufen wird, wenn der Showcase aktiviert wird.
	 */
	@Override
	protected void run() {
		
		logger.info("Zeile 61 ausgeführt");
		
		
		
		
	}
	
	class CompareCallbackR implements AsyncCallback<ArrayList<Profile>> {
		@Override
		public void onFailure(Throwable caught) {
			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
		}

		@Override
		public void onSuccess(ArrayList<Profile> result) {
			System.out.println("Zeile 72 APV ausgeführt");
			scrollPanel.setSize("100%", "100%");
			scrollPanel.add(HTMLProfilesReport.generateAllProfilesReport(result));
			RootPanel.get("Details").add(scrollPanel);
			System.out.println("Zeile 75 APV ausgeführt");
		}

	}
}
