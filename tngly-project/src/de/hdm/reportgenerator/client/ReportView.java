package de.hdm.reportgenerator.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.shared.AdministrationServiceAsync;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.SearchProfile;
import de.hdm.core.shared.report.HTMLProfilesReport;

/**
 * In dieser Klasse wird der Report fuer die beiden Abfragen (alle Profile oder
 * nur ungesehene Profile) in HTML angefordert und als Tabelle in einem
 * ScrollPanel ausgegeben. Die Tabelle gibt 17 Spalten (Username, First Name,
 * Last Name, Gender, Age, Body Height, HairColour, Smoker, Confession, Hobbies,
 * Self-description, Favorite Band, Strong Points, Favorite Movie, Associated
 * Subculture, Favorite Movie, Favorite Era, Similiarity) aus, in denen alle
 * Eigenschaften eines Profils enthalten sind.
 * 
 * @author Kevin Jaeger
 *
 */
public class ReportView extends UpdateReportGenerator {

	/**
	 * Der AdminService ermöglicht die asynchrone Kommunikation mit der
	 * Applikationslogik.
	 */
	private AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();

	/**
	 * Die Speicherung des Suchprofils ermöglicht den schnellen Zugriff auf die
	 * durch den Benutzer eingegebenen Kriterien.
	 */
	private SearchProfile searchProfile = new SearchProfile();

	/**
	 * Die Speicherung des booleschen Wertes, ob der Benutzer nur ungesehene
	 * Profile ausgeben möchte oder nicht.
	 */
	private Boolean unseenChecked = false;

	/**
	 * Deklaration, Definition und Initialisierung aller relevanten Widgets zur
	 * Gestaltung der View, wie: VerticalPanel Und Widgets zur Ablaufsteuerung,
	 * wie: Button
	 */
	private ScrollPanel scrollPanel = new ScrollPanel();
	private HorizontalPanel horPanel = new HorizontalPanel();

	HTML verLine = new HTML(
			"  <table style='display:inline;border-collapse:collapse;border:0'><tr><td style='padding:0'><img src='transparent.gif' width='1' height='500' style='background:grey'></td></tr></table>");
	HTML horLine = new HTML("<hr  style=\"width:100%;\" />");
	HTML horLine2 = new HTML("<hr  style=\"width:100%;\" />");
	HTML horLine3 = new HTML("<hr  style=\"width:100%;\" />");

	private final Button backButton = new Button("Back");

	/**
	 * Parametrisierter Konstruktor der View
	 * 
	 * @author Philipp Schmitt
	 * @param unseenChecked
	 *            True, wenn Benutzer nur ungesehene Profile ausgeben möchte
	 *            (False, wenn nicht)
	 * @param searchProfile
	 *            das Suchprofil, das vom Benutzer eingegeben wurde
	 */
	public ReportView(Boolean unseenChecked, SearchProfile searchProfile) {
		this.searchProfile = searchProfile;
		this.unseenChecked = unseenChecked;
	}

	/**
	 * Jede View besitzt eine einleitende Überschrift, die durch diese Methode
	 * erstellt wird.
	 * 
	 * @author Peter Thies
	 * @see UpdateReportGenerator#getHeadlineText()
	 */
	protected String getHeadlineText() {
		return "Your search results";
	}

	/**
	 * Jede View muss die <code>run()</code>-Methode implementieren. Sie ist
	 * eine "Einschubmethode", die von einer Methode der Basisklasse
	 * <code>Update</code> aufgerufen wird, wenn die View aktiviert wird.
	 */
	@Override
	protected void run() {

		/*
		 * Formatierung der Widgets für die Ansicht.
		 */
		RootPanel.get("Details").setWidth("85%");
		RootPanel.get("Navigator").setStylePrimaryName("rootpanel-totheleft");
		RootPanel.get("Details").setStylePrimaryName("rootpanel-totheleft");
		horPanel.setBorderWidth(0);
		horPanel.setSpacing(0);
		horPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		backButton.setStylePrimaryName("tngly-backbutton");

		/*
		 * Zuweisung des jeweiligen Child Widget zum Parent Widget.
		 */
		horPanel.add(backButton);
		RootPanel.get("Details").add(horLine);
		RootPanel.get("Details").add(horPanel);
		RootPanel.get("Details").add(horLine2);

		/*
		 * Zuweisung der ClickHandler an die jeweiligen Buttons.
		 */
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
			}
		});
		adminService.searchAndCompareProfiles(unseenChecked, searchProfile, comparedProfilesCallback());
	}

	/**
	 * AsyncCallback für das Abfragen von Profilen anhand eines Suchprofils aus der
	 * Datenbank.
	 * 
	 * @return Liste mit gefundenen und verglichenen Profilen
	 */
	private AsyncCallback<ArrayList<Profile>> comparedProfilesCallback() {
		AsyncCallback<ArrayList<Profile>> asyncCallback = new AsyncCallback<ArrayList<Profile>>() {
			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
			}

			@Override
			public void onSuccess(ArrayList<Profile> result) {
				scrollPanel.add(HTMLProfilesReport.generateAllProfilesReport(result, searchProfile));
				RootPanel.get("Details").add(scrollPanel);
			}
		};
		return asyncCallback;
	}
}
