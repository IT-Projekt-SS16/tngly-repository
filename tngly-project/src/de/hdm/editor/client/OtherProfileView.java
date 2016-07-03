package de.hdm.editor.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.shared.AdministrationServiceAsync;
import de.hdm.core.shared.bo.Information;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.ProfileBan;
import de.hdm.core.shared.bo.SearchProfile;
import de.hdm.core.shared.bo.Wish;

/**
 * Diese View Klasse f�r den Editor Client stellt das Profil eines anderen
 * Benutzers dar. Der Benutzer hat die Wahl, dieses Profil zu seiner
 * "Wunschliste" hinzuf�gen bzw. zu l�schen oder das Profil zu "blockieren" bzw.
 * die Kontaktsperre aufzuheben.
 * 
 * @author Kevin Jaeger, Philipp Schmitt
 */
public class OtherProfileView extends Update {

	/**
	 * Jede View besitzt eine einleitende �berschrift, die durch diese Methode
	 * erstellt wird.
	 * 
	 * @author Peter Thies
	 * @see Update#getHeadlineText()
	 */
	@Override
	protected String getHeadlineText() {
		return "View " + selectedProfile.getName() + "'s Profile";
	}

	/**
	 * Parametrisierter Konstruktor der View
	 * 
	 * @author Philipp Schmitt
	 * @param selectedProfile
	 *            Das ausgew�hlte Profil aus der Tabelle
	 * @param originView
	 *            Die Ursprungsview, aus der der Benutzer in diese View
	 *            gesprungen ist
	 * @param cUP
	 *            Das Profil des aktuellen Benutzers
	 * @param searchProfile
	 * 			  Das von der vorhergehenden View ggf. übergebene Suchprofil
	 */
	public OtherProfileView(Profile selectedProfile, String originView, Profile cUP, SearchProfile searchProfile) {
		this.selectedProfile = selectedProfile;
		this.originView = originView;
		this.currentUserProfile = cUP;
		this.searchProfile = searchProfile;
	}
	
	/**
	 * Parametrisierter Konstruktor der View
	 * 
	 * @author Philipp Schmitt
	 * @param selectedProfile
	 *            Das ausgew�hlte Profil aus der Tabelle
	 * @param originView
	 *            Die Ursprungsview, aus der der Benutzer in diese View
	 *            gesprungen ist
	 * @param cUP
	 *            Das Profil des aktuellen Benutzers
	 */
	public OtherProfileView(Profile selectedProfile, String originView, Profile cUP) {
		this.selectedProfile = selectedProfile;
		this.originView = originView;
		this.currentUserProfile = cUP;
	}
	
	/**
	 * Parametrisierter Konstruktor der View
	 * 
	 * @author Philipp Schmitt
	 * @param selectedProfile
	 *            Das ausgew�hlte Profil aus der Tabelle
	 * @param originView
	 *            Die Ursprungsview, aus der der Benutzer in diese View
	 *            gesprungen ist
	 */
	public OtherProfileView(Profile selectedProfile, String originView) {
		this.selectedProfile = selectedProfile;
		this.originView = originView;
	}

	/**
	 * Die AdministrationService erm�glicht die asynchrone Kommunikation mit der
	 * Applikationslogik.
	 */
	private AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();

	/**
	 * Die Instanz des ausgew�hlten Profils aus der Tabelle erm�glicht den
	 * schnellen Zugriff auf dessen Profileigenschaften.
	 */
	private Profile selectedProfile;

	/**
	 * Die Instanz des aktuellen Benutzers erm�glicht den schnellen Zugriff auf
	 * dessen Profileigenschaften.
	 */
	private Profile currentUserProfile;

	/**
	 * Das von der vorhergehenden ShowProfilesCTView übergebene Suchprofil. Dieses kann
	 * im Falle eines back-Befehls in die vorhergehende View wieder verwendet werden.
	 */
	private SearchProfile searchProfile;
	/**
	 * Die Speicherung der urspr�nglichen View, aus der der Benutzer in diese
	 * View abgesprungen ist, erm�glicht die sp�tere R�ckkehr in die entsprechende View.
	 */
	private String originView;

	private boolean isWished = false;
	private boolean isBanned = false;

	/**
	 * Deklaration, Definition und Initialisierung aller relevanten
	 * Eingabem�glichkeiten, wie: Textboxen, Listboxen, TextArea, Checkboxen,
	 * DatePicker sowie Widgets zur Gestaltung der View, wie: VerticalPanel,
	 * HorizontalPanel, Trennlinien Und Widgets zur Ablaufsteuerung, wie:
	 * Buttons
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	private VerticalPanel verPanel2 = new VerticalPanel();

	private HorizontalPanel horPanel = new HorizontalPanel();
	private HorizontalPanel horPanelButtons = new HorizontalPanel();
	private HorizontalPanel horPanelLine = new HorizontalPanel();
	String horLineStr = new String("<hr  style=\"width:100%;\" />");

	HTML horLine = new HTML("<hr  style=\"width:100%;\" />");
	HTML horLine2 = new HTML("<hr  style=\"width:100%;\" />");
	HTML verLine = new HTML(
			"  <table style='display:inline;border-collapse:collapse;border:0'><tr><td style='padding:0'><img src='transparent.gif' width='1' height='500' style='background:grey'></td></tr></table>");

	private final TextBox tbun = new TextBox();
	private final TextBox tbfn = new TextBox();
	private final TextBox tbn = new TextBox();
	private final TextBox tbbh = new TextBox();
	private final TextBox tBb = new TextBox();
	private final TextBox tBm = new TextBox();
	private final TextBox tDob = new TextBox();

	private TextArea ta = new TextArea();

	private final ListBox hairColourList = new ListBox(false);
	private final ListBox isSmokingBox = new ListBox(false);
	private final ListBox confessionBox = new ListBox(false);
	private final ListBox genderBox = new ListBox(false);

	final ListBox subcultureBox = new ListBox(false);
	final ListBox eraBox = new ListBox(false);

	private FlexTable t = new FlexTable();
	private FlexTable t3 = new FlexTable();
	private FlexTable t5 = new FlexTable();
	private FlexTable t6 = new FlexTable();
	private FlexTable t7 = new FlexTable();

	private final CheckBox chkVolleyball = new CheckBox("Volleyball");
	private final CheckBox chkFootball = new CheckBox("Football");
	private final CheckBox chkWatchPeople = new CheckBox("Watch people");
	private final CheckBox chkIT = new CheckBox("Not working at the IT-project");
	private final CheckBox chkHandball = new CheckBox("Handball");
	private final CheckBox chkPP = new CheckBox("Pocket pool");

	private final CheckBox chkStoneAge = new CheckBox("Stone Age");
	private final CheckBox chkAncientTimes = new CheckBox("Ancient Times");
	private final CheckBox chkEarlyMiddleAges = new CheckBox("Early Middle Ages");
	private final CheckBox chkLateMiddleAges = new CheckBox("Late Middle Ages");
	private final CheckBox chkRenaissance = new CheckBox("Renaissance");
	private final CheckBox chkIndustrialAge = new CheckBox("Industrial Age");
	private final CheckBox chkModernAge = new CheckBox("Modern Age");

	private final CheckBox chkBringing = new CheckBox("bringing creativity into a relationship");
	private final CheckBox chkEnjoying = new CheckBox("enjoying the simple things");
	private final CheckBox chkBeing = new CheckBox("being romantic");
	private final CheckBox chkSolving = new CheckBox("solving conflicts quickly");
	private final CheckBox chkKeeping = new CheckBox("keeping calm in chaotic situations");

	private final CheckBox chkHipHop = new CheckBox("Hip Hop");
	private final CheckBox chkMetal = new CheckBox("Metal");
	private final CheckBox chkRock = new CheckBox("Rock");
	private final CheckBox chkEmo = new CheckBox("Emo");
	private final CheckBox chkAzzlackz = new CheckBox("Azzlackz");

	private final Button atfProfilButton = new Button("Add Favorite");
	private final Button dffProfilButton = new Button("Delete Favorite");
	private final Button backButton = new Button("Back");
	private final Button banProfilButton = new Button("Ban Profile");
	private final Button unbanProfilButton = new Button("Unban Profile");

	/**
	 * Jede View muss die <code>run()</code>-Methode implementieren. Sie ist
	 * eine "Einschubmethode", die von einer Methode der Basisklasse
	 * <code>Update</code> aufgerufen wird, wenn die View aktiviert wird.
	 * 
	 * @author Kevin Jaeger
	 * @return
	 */
	@Override
	protected void run() {

		/*
		 * Formatierung der Widgets f�r die Ansicht.
		 */
		verPanel.setSpacing(10);
		verPanel2.setSpacing(10);

		tbun.setStylePrimaryName("tngly-disabledTextbox");
		tbfn.setStylePrimaryName("tngly-disabledTextbox");
		tbn.setStylePrimaryName("tngly-disabledTextbox");
		tbbh.setStylePrimaryName("tngly-disabledTextbox");
		tBb.setStylePrimaryName("tngly-disabledTextbox");
		tBm.setStylePrimaryName("tngly-disabledTextbox");
		tDob.setStylePrimaryName("tngly-disabledTextbox");
		ta.setStylePrimaryName("tngly-disabledTextbox");

		horPanelButtons.setHorizontalAlignment(ALIGN_CENTER);
		horPanelButtons.setVerticalAlignment(ALIGN_TOP);
		horPanelButtons.setStylePrimaryName("tngly-buttonPanel");
		horPanelButtons.setWidth("100%");

		horPanelLine.setStylePrimaryName("tngly-linePanel");
		horPanelLine.setHeight("5px");
		horPanelLine.setWidth("100%");

		atfProfilButton.setStylePrimaryName("tngly-opvbutton");
		dffProfilButton.setStylePrimaryName("tngly-opvbutton");
		backButton.setStylePrimaryName("tngly-backbutton");
		banProfilButton.setStylePrimaryName("tngly-opvbutton");
		unbanProfilButton.setStylePrimaryName("tngly-opvbutton");

		if (selectedProfile.getIsFavorite() == true) {
			atfProfilButton.setStylePrimaryName("tngly-disabledButton");
			atfProfilButton.setEnabled(false);

			banProfilButton.setStylePrimaryName("tngly-disabledButton");
			banProfilButton.setEnabled(false);

			unbanProfilButton.setStylePrimaryName("tngly-disabledButton");
			unbanProfilButton.setEnabled(false);

			isWished = true;
		}

		if (selectedProfile.getIsBanned() == true) {
			atfProfilButton.setStylePrimaryName("tngly-disabledButton");
			atfProfilButton.setEnabled(false);

			dffProfilButton.setStylePrimaryName("tngly-disabledButton");
			dffProfilButton.setEnabled(false);

			banProfilButton.setStylePrimaryName("tngly-disabledButton");
			banProfilButton.setEnabled(false);

			isBanned = true;
		}

		if (selectedProfile.getIsBanned() == false && selectedProfile.getIsFavorite() == false) {

			dffProfilButton.setStylePrimaryName("tngly-disabledButton");
			dffProfilButton.setEnabled(false);

			unbanProfilButton.setStylePrimaryName("tngly-disabledButton");
			unbanProfilButton.setEnabled(false);

		}

		tbun.setPixelSize(120, 15);
		tbfn.setPixelSize(120, 15);
		tbn.setPixelSize(120, 15);
		tbbh.setPixelSize(120, 15);
		tDob.setPixelSize(120, 15);
		ta.setWidth("230px");
		tbun.setWidth("100%");

		/*
		 * Bef�llen der Listboxen mit Werten
		 */
		hairColourList.setVisibleItemCount(1);
		hairColourList.addItem("Black");
		hairColourList.addItem("Brown");
		hairColourList.addItem("Red");
		hairColourList.addItem("Blonde");
		hairColourList.addItem("Dark Blonde");
		hairColourList.setPixelSize(130, 25);

		isSmokingBox.setVisibleItemCount(1);
		isSmokingBox.addItem("No");
		isSmokingBox.addItem("Yes");
		isSmokingBox.setPixelSize(130, 25);

		confessionBox.setVisibleItemCount(1);
		confessionBox.addItem("Atheistic");
		confessionBox.addItem("Buddhistic");
		confessionBox.addItem("Evangelic");
		confessionBox.addItem("Catholic");
		confessionBox.addItem("Hindu");
		confessionBox.addItem("Muslim");
		confessionBox.addItem("Jewish");
		confessionBox.addItem("Orthodox");
		confessionBox.addItem("Other");
		confessionBox.setPixelSize(130, 25);

		genderBox.setVisibleItemCount(1);
		genderBox.addItem("Female");
		genderBox.addItem("Male");
		genderBox.setPixelSize(130, 25);

		/*
		 * Zuweisung des jeweiligen Child Widget zum Parent Widget.
		 */
		RootPanel.get("Details").add(verPanel);

		/*
		 * Zuweisung der ClickHandler an die jeweiligen Buttons.
		 */
		backButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				switch (originView) {
				case "ShowProfilesCTView":
					Update updateProfilesCTView = new ShowProfilesCTView(searchProfile);
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(updateProfilesCTView);
					break;
				case "BanCTView":
					Update updateBanCTView = new BanCTView();
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(updateBanCTView);
					break;
				case "WishlistCTView":
					Update updateWishlistCTView = new WishlistCTView();
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(updateWishlistCTView);
					break;
				default:
				}
			}
		});

		/*
		 * Aufbau und Bef�llung der FlexTables mit Werten und Widgets
		 */
		t.setText(0, 0, "Username");
		tbun.setEnabled(false);
		tbun.setText(selectedProfile.getUserName());
		t.setWidget(0, 1, tbun);

		t.setText(2, 0, "First Name");
		tbfn.setEnabled(false);
		tbfn.setText(selectedProfile.getName());
		t.setWidget(2, 1, tbfn);

		t.setText(3, 0, "Last Name");
		tbn.setEnabled(false);
		tbn.setText(selectedProfile.getLastName());
		t.setWidget(3, 1, tbn);

		int index;
		if (selectedProfile.getGender() == "Male") {
			index = 1;
		} else {
			index = 0;
		}
		genderBox.setItemSelected(index, true);

		t.setText(4, 0, "Gender");
		genderBox.setEnabled(false);
		genderBox.setItemSelected(index, true);
		t.setWidget(4, 1, genderBox);

		t.setText(5, 0, "Date of Birth");
		tDob.setEnabled(false);
		tDob.setText(selectedProfile.getDateOfBirth().toString());
		t.setWidget(5, 1, tDob);

		float bh = selectedProfile.getBodyHeight();
		float bhFormatted = (float) (Math.round(bh * 100) / 100.0);

		t.setText(6, 0, "Body Height (in meter");
		tbbh.setEnabled(false);
		tbbh.setText(Float.toString(bhFormatted));
		t.setWidget(6, 1, tbbh);

		if (selectedProfile.getHairColour() == "Black") {
			index = 0;
		} else if (selectedProfile.getHairColour() == "Brown") {
			index = 1;
		} else if (selectedProfile.getHairColour() == "Red") {
			index = 2;
		} else if (selectedProfile.getHairColour() == "Blonde") {
			index = 3;
		} else {
			index = 4;
		}

		t.setText(7, 0, "Haircolor");
		hairColourList.setEnabled(false);
		hairColourList.setItemSelected(index, true);
		t.setWidget(7, 1, hairColourList);

		if (selectedProfile.getIsSmoking() == 0) {
			index = 0;
		}
		if (selectedProfile.getIsSmoking() == 1) {
			index = 1;
		}

		t.setText(8, 0, "Smoker");
		isSmokingBox.setEnabled(false);
		isSmokingBox.setItemSelected(selectedProfile.getIsSmoking(), true);
		t.setWidget(8, 1, isSmokingBox);

		if (selectedProfile.getConfession() == "Atheistic") {
			index = 0;
		} else if (selectedProfile.getConfession() == "Buddhistic") {
			index = 1;
		} else if (selectedProfile.getConfession() == "Evangelic") {
			index = 2;
		} else if (selectedProfile.getConfession() == "Catholic") {
			index = 3;
		} else if (selectedProfile.getConfession() == "Hindu") {
			index = 4;
		} else if (selectedProfile.getConfession() == "Muslim") {
			index = 5;
		} else if (selectedProfile.getConfession() == "Jewish") {
			index = 6;
		} else if (selectedProfile.getConfession() == "Orthodox") {
			index = 7;
		} else {
			index = 8;
		}

		t.setText(9, 0, "Confession");
		confessionBox.setEnabled(false);
		confessionBox.setItemSelected(index, true);
		t.setWidget(9, 1, confessionBox);

		t.setHTML(10, 0, horLineStr);
		t.setHTML(10, 1, horLineStr);

		FlexTable t2 = new FlexTable();

		t.setText(11, 0, "Hobbies");
		t2.setWidget(0, 0, chkVolleyball);
		chkVolleyball.setEnabled(false);
		t2.setWidget(1, 0, chkFootball);
		chkFootball.setEnabled(false);
		t2.setWidget(2, 0, chkWatchPeople);
		chkWatchPeople.setEnabled(false);
		t2.setWidget(3, 0, chkIT);
		chkIT.setEnabled(false);
		t2.setWidget(4, 0, chkHandball);
		chkHandball.setEnabled(false);
		t2.setWidget(5, 0, chkPP);
		chkPP.setEnabled(false);

		t.setWidget(11, 1, t2);

		t.setHTML(12, 0, horLineStr);
		t.setHTML(12, 1, horLineStr);

		t.setText(13, 0, "This is how I describe myself");
		t.setWidget(13, 1, ta);

		t3.setText(0, 0, "Favorite Band");
		t3.setWidget(0, 1, tBb);

		t3.setHTML(1, 0, horLineStr);
		t3.setHTML(1, 1, horLineStr);

		t3.setText(2, 0, "Favorite Movie");
		t3.setWidget(2, 1, tBm);

		t3.setHTML(3, 0, horLineStr);
		t3.setHTML(3, 1, horLineStr);

		t3.setText(4, 0, "Favorite Era");
		t5.setWidget(0, 0, chkStoneAge);
		chkStoneAge.setEnabled(false);
		t5.setWidget(1, 0, chkAncientTimes);
		chkAncientTimes.setEnabled(false);
		t5.setWidget(2, 0, chkEarlyMiddleAges);
		chkEarlyMiddleAges.setEnabled(false);
		t5.setWidget(3, 0, chkLateMiddleAges);
		chkLateMiddleAges.setEnabled(false);
		t5.setWidget(4, 0, chkRenaissance);
		chkRenaissance.setEnabled(false);
		t5.setWidget(5, 0, chkIndustrialAge);
		chkIndustrialAge.setEnabled(false);
		t5.setWidget(6, 0, chkModernAge);
		chkModernAge.setEnabled(false);
		t3.setWidget(4, 1, t5);

		t3.setHTML(5, 0, horLineStr);
		t3.setHTML(5, 1, horLineStr);

		t3.setText(6, 0, "Strong Points");
		t7.setWidget(0, 0, chkBringing);
		chkBringing.setEnabled(false);
		t7.setWidget(1, 0, chkEnjoying);
		chkEnjoying.setEnabled(false);
		t7.setWidget(2, 0, chkBeing);
		chkBeing.setEnabled(false);
		t7.setWidget(3, 0, chkSolving);
		chkSolving.setEnabled(false);
		t7.setWidget(4, 0, chkKeeping);
		chkKeeping.setEnabled(false);
		t3.setWidget(6, 1, t7);

		t3.setHTML(7, 0, horLineStr);
		t3.setHTML(7, 1, horLineStr);

		t3.setText(8, 0, "I associate myself with this subculture");
		t6.setWidget(0, 0, chkHipHop);
		chkHipHop.setEnabled(false);
		t6.setWidget(1, 0, chkMetal);
		chkMetal.setEnabled(false);
		t6.setWidget(2, 0, chkRock);
		chkRock.setEnabled(false);
		t6.setWidget(3, 0, chkEmo);
		chkEmo.setEnabled(false);
		t6.setWidget(4, 0, chkAzzlackz);
		chkAzzlackz.setEnabled(false);
		t3.setWidget(8, 1, t6);

		/*
		 * Markierung der Checkboxen f�r die Profileigenschaft "My Hobbies"
		 */
		for (int x = 0; x < t2.getRowCount(); x++) {
			ArrayList<String> values = new ArrayList<String>();
			String tempValue;
			CheckBox cb = (CheckBox) t2.getWidget(x, 0);
			for (Information i : selectedProfile.getSelectionList().get(0).getInformationValues()) {
				tempValue = i.getValue();
				values.add(tempValue);
			}
			tempValue = cb.getText();
			if (values.contains(tempValue)) {
				cb.setValue(true);
				cb.addStyleName("tngly-checkbox-marked");
			}
		}

		/*
		 * Zuweisung von Werten der Beschreibungseigenschaften an die
		 * jeweiligen Widgets.
		 */
		if (selectedProfile.getDescriptionList().get(0).getInformationValues().size() > 0) {
			ta.setText(selectedProfile.getDescriptionList().get(0).getInformationValues().get(0).getValue());
		} else {
		}
		if (selectedProfile.getDescriptionList().get(1).getInformationValues().size() > 0) {
			tBb.setText(selectedProfile.getDescriptionList().get(1).getInformationValues().get(0).getValue());
		} else {
		}
		if (selectedProfile.getDescriptionList().get(2).getInformationValues().size() > 0) {
			tBm.setText(selectedProfile.getDescriptionList().get(2).getInformationValues().get(0).getValue());
		} else {
		}

		/*
		 * Markierung der Checkboxen f�r die Eigenschaft "My Strong Points"
		 */
		for (int x = 0; x < t7.getRowCount(); x++) {
			ArrayList<String> values = new ArrayList<String>();
			String tempValue;
			CheckBox cb = (CheckBox) t7.getWidget(x, 0);
			for (Information i : selectedProfile.getSelectionList().get(1).getInformationValues()) {
				tempValue = i.getValue();
				values.add(tempValue);
			}
			tempValue = cb.getText();
			if (values.contains(tempValue)) {
				cb.setValue(true);
				cb.addStyleName("tngly-checkbox-marked");
			}
		}

		/*
		 * Markierung der Checkboxen f�r die Eigenschaft
		 * "I associate myself with this subculture"
		 */
		for (int x = 0; x < t6.getRowCount(); x++) {
			ArrayList<String> values = new ArrayList<String>();
			String tempValue;
			CheckBox cb = (CheckBox) t6.getWidget(x, 0);
			for (Information i : selectedProfile.getSelectionList().get(2).getInformationValues()) {
				tempValue = i.getValue();
				values.add(tempValue);
			}
			tempValue = cb.getText();
			if (values.contains(tempValue)) {
				cb.setValue(true);
				cb.addStyleName("tngly-checkbox-marked");
			}
		}

		/*
		 * Markierung der Checkboxen f�r die Eigenschaft "Favorite Era"
		 */
		for (int x = 0; x < t5.getRowCount(); x++) {
			ArrayList<String> values = new ArrayList<String>();
			String tempValue;
			CheckBox cb = (CheckBox) t5.getWidget(x, 0);

			for (Information i : selectedProfile.getSelectionList().get(3).getInformationValues()) {
				tempValue = i.getValue();
				values.add(tempValue);
			}
			tempValue = cb.getText();
			if (values.contains(tempValue)) {
				cb.setValue(true);
				cb.addStyleName("tngly-checkbox-marked");
			}
		}

		/*
		 * Zuweisung des jeweiligen Child Widget zum Parent Widget.
		 */
		verPanel.add(t);
		verPanel2.add(t3);
		horPanel.add(verPanel);
		horPanel.add(verLine);
		horPanel.add(verPanel2);
		horPanelButtons.add(backButton);
		horPanelButtons.add(atfProfilButton);
		horPanelButtons.add(dffProfilButton);
		horPanelButtons.add(banProfilButton);
		horPanelButtons.add(unbanProfilButton);
		RootPanel.get("Details").add(horLine);
		RootPanel.get("Details").add(horPanelButtons);
		RootPanel.get("Details").add(horLine2);
		RootPanel.get("Details").add(horPanel);

		/*
		 * Zuweisung der ClickHandler an die jeweiligen Buttons.
		 */
		backButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				backButton.setEnabled(false);
				backButton.setStylePrimaryName("tngly-disabledButton");

				switch (originView) {
				case "ShowProfilesCellTableView":
					 Update updateProfilesCTView = new ShowProfilesCTView(searchProfile);
					 RootPanel.get("Details").clear();
					 RootPanel.get("Details").add(updateProfilesCTView);
					break;
				case "BanCTView":
					Update updateBanCTView = new BanCTView();
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(updateBanCTView);
					break;
				case "WishlistCTView":
					Update updateWishlistCTView = new WishlistCTView();
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(updateWishlistCTView);
					break;
				default:
				}
			}
		});

		atfProfilButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				atfProfilButton.setEnabled(false);
				atfProfilButton.setStylePrimaryName("tngly-disabledButton");
				banProfilButton.setEnabled(false);
				banProfilButton.setStylePrimaryName("tngly-disabledButton");
				adminService.addWishToWishlist(selectedProfile.getId(), currentUserProfile.getId(),
						new CreateWishCallback());
			}
		});

		dffProfilButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dffProfilButton.setEnabled(false);
				dffProfilButton.setStylePrimaryName("tngly-disabledButton");
				adminService.deleteWishFromWishlist(selectedProfile.getId(), currentUserProfile.getId(),
						new DeleteWishCallback());
			}
		});

		banProfilButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				banProfilButton.setEnabled(false);
				banProfilButton.setStylePrimaryName("tngly-disabledButton");
				atfProfilButton.setEnabled(false);
				atfProfilButton.setStylePrimaryName("tngly-disabledButton");
				adminService.createProfileBan(selectedProfile.getId(), currentUserProfile.getId(),
						new CreateProfileBanCallback());
			}
		});

		unbanProfilButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				unbanProfilButton.setEnabled(false);
				unbanProfilButton.setStylePrimaryName("tngly-disabledButton");
				adminService.deleteProfileBan(currentUserProfile.getId(), selectedProfile.getId(),
						new DeleteBanCallback());
			}
		});
	}

	/**
	 * AsyncCallback f�r das L�schen eines Profil-"Wunsches" in der
	 * Datenbank.
	 * 
	 * @return
	 */
	class DeleteWishCallback implements AsyncCallback<Void> {
		@Override
		public void onFailure(Throwable caught) {
			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Void result) {
			selectedProfile.setIsFavorite(false);
			Update update = new OtherProfileView(selectedProfile, originView, currentUserProfile, searchProfile);
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(update);
		}
	}

	/**
	 * AsyncCallback f�r das L�schen einer Kontaktsperre in der
	 * Datenbank.
	 * 
	 * @return
	 */
	class DeleteBanCallback implements AsyncCallback<Void> {
		@Override
		public void onFailure(Throwable caught) {
			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Void result) {
			selectedProfile.setIsBanned(false);
			Update update = new OtherProfileView(selectedProfile, originView, currentUserProfile, searchProfile);
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(update);
		}
	}

	/**
	 * AsyncCallback f�r das Erstellen einer Kontaktsperre in der
	 * Datenbank.
	 * 
	 * @return
	 */
	class CreateProfileBanCallback implements AsyncCallback<ProfileBan> {
		@Override
		public void onFailure(Throwable caught) {
			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
		}

		@Override
		public void onSuccess(ProfileBan pb) {
			selectedProfile.setIsBanned(true);
			Update update = new OtherProfileView(selectedProfile, originView, currentUserProfile, searchProfile);
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(update);
		}
	}

	/**
	 * AsyncCallback f�r das Erstellen eines Profil-"Wunsches" in der
	 * Datenbank.
	 * 
	 * @return
	 */
	class CreateWishCallback implements AsyncCallback<Wish> {
		@Override
		public void onFailure(Throwable caught) {
			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Wish wish) {
			selectedProfile.setIsFavorite(true);
			Update update = new OtherProfileView(selectedProfile, originView, currentUserProfile, searchProfile);
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(update);
		}
	}
}
