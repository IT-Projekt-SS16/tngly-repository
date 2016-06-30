package de.hdm.reportgenerator.client;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.mortbay.log.Log;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

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
	
	HTML verLine = new HTML("  <table style='display:inline;border-collapse:collapse;border:0'><tr><td style='padding:0'><img src='transparent.gif' width='1' height='500' style='background:grey'></td></tr></table>"); 
	
	HTML horLine = new HTML("<hr  style=\"width:100%;\" />");

	Logger logger = ClientsideSettings.getLogger();
	
	public AllProfilesView (Boolean unseenChecked, SearchProfile searchProfile){
		
		logger.info("Zeile 33 APV ausgeführt");
		this.searchProfile = searchProfile;
		this.unseenChecked = unseenChecked;
		logger.info("Zeile 37 APV ausgeführt");
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
		
		//adminService.searchAndCompareProfiles(unseenChecked, searchProfile, comparedProfilesCallback());
		adminService.testCallback(testCallback());
		
		logger.info("Zeile 61 ausgeführt");
		scrollPanel.add(horLine);
		RootPanel.get("Details").add(scrollPanel);
		scrollPanel.setSize("100%", "100%");
		
		logger.info("Zeile 66 ausgeführt");
		
	}
	
	private AsyncCallback<ArrayList<Profile>> comparedProfilesCallback() {
		AsyncCallback<ArrayList<Profile>> asyncCallback = new AsyncCallback<ArrayList<Profile>>() {

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
			}

			@Override
			public void onSuccess(ArrayList<Profile> result) {
				
				System.out.println("Zeile 81 APV ausgeführt");
				scrollPanel.add(HTMLProfilesReport.generateAllProfilesReport(result));
				System.out.println("Zeile 83 APV ausgeführt");
				
			}
		};
		return asyncCallback;
	} 
	
	private AsyncCallback<Integer> testCallback() {
		AsyncCallback<Integer> asyncCallback = new AsyncCallback<Integer>() {

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
				System.out.println("Zeile 102 APV ausgeführt");

			}

			@Override
			public void onSuccess(Integer result) {
				
				System.out.println("Zeile 109 APV ausgeführt");
				System.out.println("Zeile 111 APV ausgeführt");
				
			}
		};
		return asyncCallback;
	} 

}
