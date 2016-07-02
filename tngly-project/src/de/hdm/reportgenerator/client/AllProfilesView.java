package de.hdm.reportgenerator.client;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.shared.AdministrationServiceAsync;
import de.hdm.core.shared.ReportGeneratorAsync;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.SearchProfile;
import de.hdm.core.shared.report.HTMLProfilesReport;

public class AllProfilesView extends UpdateReportGenerator {
	
	private SearchProfile searchProfile = new SearchProfile();
	private Boolean unseenChecked = null;
	
	private ReportGeneratorAsync reportGenerator = ClientsideSettings.getReportGenerator();
	private AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();
	
	private ScrollPanel scrollPanel = new ScrollPanel();
	
	private VerticalPanel verPanel = new VerticalPanel();
	
	HTML verLine = new HTML("  <table style='display:inline;border-collapse:collapse;border:0'><tr><td style='padding:0'><img src='transparent.gif' width='1' height='500' style='background:grey'></td></tr></table>"); 
	
	HTML horLine = new HTML("<hr  style=\"width:100%;\" />");

	Logger logger = ClientsideSettings.getLogger();
	
	public AllProfilesView (Boolean unseenChecked, SearchProfile searchProfile){
		
		logger.info("Zeile 33 APV ausgeführt");
		this.searchProfile = searchProfile;
		this.unseenChecked = unseenChecked;
		logger.info("Zeile 37 APV ausgeführt");
	}

	/**
	 * Jeder Showcase besitzt eine einleitende �berschrift, die durch diese
	 * Methode zu erstellen ist.
	 * 
	 * @see Showcase#getHeadlineText()
	 */
	// @Override
	protected String getHeadlineText() {
		return "";
	}

	/**
	 * Jeder Showcase muss die <code>run()</code>-Methode implementieren. Sie
	 * ist eine "Einschubmethode", die von einer Methode der Basisklasse
	 * <code>ShowCase</code> aufgerufen wird, wenn der Showcase aktiviert wird.
	 */
	@Override
	protected void run() {
		
		adminService.searchAndCompareProfiles(unseenChecked, searchProfile, comparedProfilesCallback());
		reportGenerator.testCallback(testCallback());
		
	}
	
	private AsyncCallback<ArrayList<Profile>> comparedProfilesCallback() {
		AsyncCallback<ArrayList<Profile>> asyncCallback = new AsyncCallback<ArrayList<Profile>>() {

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
			}

			@Override
			public void onSuccess(ArrayList<Profile> result) {
				
				logger.info("Zeile 82 APV ausgeführt");
				logger.info("Zeile 84 APV ausgeführt");
				scrollPanel.add(HTMLProfilesReport.generateAllProfilesReport(result));
				logger.info("Zeile 86 APV ausgeführt");
				RootPanel.get("Details").add(scrollPanel);
				logger.info("Line 87 APV executed");
				
			}
		};
		return asyncCallback;
	} 
	
	private AsyncCallback<Integer> testCallback() {
		AsyncCallback<Integer> asyncCallback = new AsyncCallback<Integer>() {

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
				logger.info("Zeile 100 APV ausgeführt");

			}

			@Override
			public void onSuccess(Integer result) {
				
				logger.info("Line 107 APV executed");
				
			}
		};
		return asyncCallback;
	} 

}
