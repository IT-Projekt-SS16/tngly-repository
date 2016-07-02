package de.hdm.reportgenerator.client;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.shared.AdministrationServiceAsync;
import de.hdm.core.shared.ReportGeneratorAsync;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.SearchProfile;
import de.hdm.core.shared.report.HTMLProfilesReport;
import de.hdm.editor.client.SearchByProfileView;
import de.hdm.editor.client.Update;

public class AllProfilesView extends UpdateReportGenerator {
	
	private SearchProfile searchProfile = new SearchProfile();
	private Boolean unseenChecked = false;
	
	private ReportGeneratorAsync reportGenerator = ClientsideSettings.getReportGenerator();
	private AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();
	
	private ScrollPanel scrollPanel = new ScrollPanel();
	
	private VerticalPanel verPanel = new VerticalPanel();
	
	private HorizontalPanel horPanel = new HorizontalPanel();
	
	HTML verLine = new HTML("  <table style='display:inline;border-collapse:collapse;border:0'><tr><td style='padding:0'><img src='transparent.gif' width='1' height='500' style='background:grey'></td></tr></table>"); 
	
	HTML horLine = new HTML("<hr  style=\"width:100%;\" />");
	HTML horLine2 = new HTML("<hr  style=\"width:100%;\" />");
	HTML horLine3 = new HTML("<hr  style=\"width:100%;\" />");

	Logger logger = ClientsideSettings.getLogger();
	
	private final Button backButton = new Button("Back");
	
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
		return "Your search results";
	}

	/**
	 * Jeder Showcase muss die <code>run()</code>-Methode implementieren. Sie
	 * ist eine "Einschubmethode", die von einer Methode der Basisklasse
	 * <code>ShowCase</code> aufgerufen wird, wenn der Showcase aktiviert wird.
	 */
	@Override
	protected void run() {
		
		RootPanel.get("Details").setWidth("85%");
		RootPanel.get("Navigator").setStylePrimaryName("rootpanel-totheleft");
		RootPanel.get("Details").setStylePrimaryName("rootpanel-totheleft");
		
		horPanel.setBorderWidth(0);
		horPanel.setSpacing(0);
		horPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		backButton.setStylePrimaryName("tngly-backbutton");
		horPanel.add(backButton);
		
		RootPanel.get("Details").add(horLine);
		RootPanel.get("Details").add(horPanel);
		RootPanel.get("Details").add(horLine2);
		
		backButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
					backButton.setEnabled(false);
					backButton.setStylePrimaryName("tngly-disabledButton");
					RootPanel.get("Details").setWidth("65%");
					RootPanel.get("Navigator").setStylePrimaryName("rootpanel-totheright");
					RootPanel.get("Details").setStylePrimaryName("rootpanel-totheright");
		        	UpdateReportGenerator update = new SearchByProfileViewR(searchProfile, unseenChecked);
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(update);
					logger.info("Erfolgreich View geswitcht.");
			}
		});
		
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
