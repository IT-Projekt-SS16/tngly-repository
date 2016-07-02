package de.hdm.editor.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ShowRangeEvent;
import com.google.gwt.event.logical.shared.ShowRangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.gwt.user.datepicker.client.DatePicker;

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.shared.AdministrationServiceAsync;
import de.hdm.core.shared.bo.Information;
import de.hdm.core.shared.bo.Profile;

/**
 * Diese View Klasse für den Editor Client stellt das persönliche Profil des
 * aktuell eingeloggten Benutzers zur weiteren Bearbeitung zur Verfügung. Der
 * Benutzer hat die Wahl, seine Profilwerte und -eigenschaften zu ändern oder
 * das komplette Profil zu löschen.
 * 
 * @author Kevin Jaeger, Philipp Schmitt
 */
public class EditProfileView extends Update {

	/**
	 * Die AdministrationService ermöglicht die asynchrone Kommunikation mit der
	 * Applikationslogik.
	 */
	private AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();

	/**
	 * Die Instanz des aktuellen Benutzers ermöglicht den schnellen Zugriff auf
	 * dessen Profileigenschaften.
	 */
	private Profile currentUserProfile = null;

	/**
	 * Deklaration, Definition und Initialisierung aller relevanten
	 * Eingabemöglichkeiten, wie: Textboxen, Listboxen, TextArea, Checkboxen,
	 * DatePicker sowie Widgets zur Gestaltung der View, wie: VerticalPanel,
	 * HorizontalPanel, Trennlinien Und Widgets zur Ablaufsteuerung, wie:
	 * Buttons
	 */
	private VerticalPanel verPanel = new VerticalPanel();
	private VerticalPanel verPanel2 = new VerticalPanel();
	private HorizontalPanel horPanel = new HorizontalPanel();
	private HorizontalPanel horPanel2 = new HorizontalPanel();

	private HTML horLine = new HTML("<hr  style=\"width:100%;\" />");
	private HTML horLine2 = new HTML("<hr  style=\"width:100%;\" />");
	private String horLineStr = new String("<hr  style=\"width:100%;\" />");
	private HTML verLine = new HTML(
			"  <table style='display:inline;border-collapse:collapse;border:0'><tr><td style='padding:0'><img src='transparent.gif' width='1' height='625' style='background:grey'></td></tr></table>");

	private final Label lblWrongInputFirstName = new Label(
			"Please only enter characters (a-z, A-Z) in field 'First Name'");
	private final Label lblWrongInputLastName = new Label(
			"Please only enter characters (a-z, A-Z) in field 'Last Name'");
	private final Label lblWrongInputBodyHeight = new Label(
			"Please only enter numbers in following pattern: '#.##' between 1.00 and 2.99");

	private final TextBox tbun = new TextBox();
	private final TextBox tbfn = new TextBox();
	private final TextBox tbn = new TextBox();
	private final TextBox tbbh = new TextBox();
	private final TextBox tBb = new TextBox();
	private final TextBox tBm = new TextBox();
	private TextArea ta = new TextArea();

	private final ListBox hairColourList = new ListBox(false);
	private final ListBox isSmokingBox = new ListBox(false);
	private final ListBox confessionBox = new ListBox(false);
	private final ListBox genderBox = new ListBox(false);
	private final ListBox subcultureBox = new ListBox(false);
	private final ListBox eraBox = new ListBox(false);

	private final DatePicker datePicker = new DatePicker();

	private FlexTable t = new FlexTable();
	private FlexTable t2 = new FlexTable();
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

	private final Button saveProfilButton = new Button("Save");
	private final Button deleteProfileButton = new Button("Delete Profile");

	/**
	 * Jede View besitzt eine einleitende Überschrift, die durch diese Methode
	 * erstellt wird.
	 * 
	 * @author Peter Thies
	 * @see Update#getHeadlineText()
	 */
	@Override
	protected String getHeadlineText() {
		return "Edit Profile";
	}

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

		/**
		 * Auslesen des Profils vom aktuellen Benutzer aus der Datenbank.
		 */
		int atIndex = ClientsideSettings.getLoginInfo().getEmailAddress().indexOf("@");
		adminService.getProfileByUserName(ClientsideSettings.getLoginInfo().getEmailAddress().substring(0, atIndex),
				getCurrentUserProfileCallback());

		/*
		 * Formatierung der Panels für die Ansicht.
		 */
		verPanel.setSpacing(10);
		verPanel2.setSpacing(10);
		horPanel2.setWidth("100%");
		horPanel2.setHorizontalAlignment(ALIGN_CENTER);

		tbun.setPixelSize(120, 15);
		tbfn.setPixelSize(120, 15);
		tbn.setPixelSize(120, 15);
		tbbh.setPixelSize(120, 15);

		tbun.setStylePrimaryName("tngly-disabledTextbox");

		/*
		 * Befüllen der Listboxen mit Werten
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

		datePicker.setYearArrowsVisible(true);
		datePicker.setYearAndMonthDropdownVisible(false);
		datePicker.setVisibleYearCount(101);
		datePicker.setYearAndMonthDropdownVisible(true);

		/*
		 * Sperren der Eingabemöglichkeit im DatePicker bei zukünftigen Daten
		 */
		datePicker.addShowRangeHandlerAndFire(new ShowRangeHandler<java.util.Date>() {
			@Override
			public void onShowRange(ShowRangeEvent<Date> event) {
				Date start = event.getStart();
				Date temp = CalendarUtil.copyDate(start);
				Date end = event.getEnd();

				// long d = 817689600000L;
				Date today = new Date();

				while (temp.before(end)) {
					if (temp.after(today) && datePicker.isDateVisible(temp)) {
						datePicker.setTransientEnabledOnDates(false, temp);
					}
					CalendarUtil.addDaysToDate(temp, 1);
				}
			}
		});

		ta.setWidth("230px");
		tbun.setWidth("100%");

		/*
		 * Aufbau und Befüllung der FlexTables mit Werten und Widgets
		 */
		t.setText(0, 0, "Username");
		tbun.setEnabled(false);
		t.setWidget(0, 1, tbun);

		t.setText(2, 0, "First Name");
		t.setWidget(2, 1, tbfn);

		t.setText(3, 0, "Last Name");
		t.setWidget(3, 1, tbn);

		t.setText(4, 0, "Gender");
		t.setWidget(4, 1, genderBox);

		t.setText(5, 0, "Date of Birth");
		t.setWidget(5, 1, datePicker);

		t.setText(6, 0, "Body Height (in meter)");
		t.setWidget(6, 1, tbbh);

		t.setText(7, 0, "Haircolor");
		t.setWidget(7, 1, hairColourList);

		t.setText(8, 0, "Smoker");
		t.setWidget(8, 1, isSmokingBox);

		t.setText(9, 0, "Confession");
		t.setWidget(9, 1, confessionBox);

		t.setHTML(10, 0, horLineStr);
		t.setHTML(10, 1, horLineStr);

		t.setText(11, 0, "Hobbies");
		t2.setWidget(0, 0, chkVolleyball);
		t2.setWidget(1, 0, chkFootball);
		t2.setWidget(2, 0, chkWatchPeople);
		t2.setWidget(3, 0, chkIT);
		t2.setWidget(4, 0, chkHandball);
		t2.setWidget(5, 0, chkPP);
		t.setWidget(11, 1, t2);

		ta.setCharacterWidth(50);
		ta.setVisibleLines(2);

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

		t3.setText(4, 0, "Strong Points");
		t7.setWidget(0, 0, chkBringing);
		t7.setWidget(1, 0, chkEnjoying);
		t7.setWidget(2, 0, chkBeing);
		t7.setWidget(3, 0, chkSolving);
		t7.setWidget(4, 0, chkKeeping);
		t3.setWidget(4, 1, t7);

		t3.setHTML(5, 0, horLineStr);
		t3.setHTML(5, 1, horLineStr);

		t3.setText(6, 0, "I associate myself with this subculture");
		t6.setWidget(0, 0, chkHipHop);
		t6.setWidget(1, 0, chkMetal);
		t6.setWidget(2, 0, chkRock);
		t6.setWidget(3, 0, chkEmo);
		t6.setWidget(4, 0, chkAzzlackz);
		t3.setWidget(6, 1, t6);

		t3.setHTML(7, 0, horLineStr);
		t3.setHTML(7, 1, horLineStr);

		t3.setText(8, 0, "Favorite Era");
		t5.setWidget(0, 0, chkStoneAge);
		t5.setWidget(1, 0, chkAncientTimes);
		t5.setWidget(2, 0, chkEarlyMiddleAges);
		t5.setWidget(3, 0, chkLateMiddleAges);
		t5.setWidget(4, 0, chkRenaissance);
		t5.setWidget(5, 0, chkIndustrialAge);
		t5.setWidget(6, 0, chkModernAge);
		t3.setWidget(8, 1, t5);

		/*
		 * Zuweisung von Styles an die jeweiligen Widgets.
		 */
		saveProfilButton.setStyleName("tngly-bluebutton");
		deleteProfileButton.setStyleName("tngly-button");
		lblWrongInputFirstName.setStyleName("serverResponseLabelError");
		lblWrongInputLastName.setStyleName("serverResponseLabelError");
		lblWrongInputBodyHeight.setStyleName("serverResponseLabelError");

		/*
		 * Zuweisung des jeweiligen Child Widget zum Parent Widget.
		 */
		horPanel2.add(saveProfilButton);
		horPanel2.add(deleteProfileButton);
		verPanel.add(t);
		verPanel2.add(t3);
		horPanel.add(verPanel);
		horPanel.add(verLine);
		horPanel.add(verPanel2);
		RootPanel.get("Details").add(horLine);
		RootPanel.get("Details").add(horPanel2);
		RootPanel.get("Details").add(horLine2);
		RootPanel.get("Details").add(horPanel);

		/*
		 * Zuweisung der ClickHandler an die jeweiligen Buttons.
		 */
		saveProfilButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				/*
				 * Rücksetzung der Labels für die Meldungen zu Eingabefehlern.
				 */
				t.setWidget(2, 2, null);
				t.setWidget(3, 2, null);
				t.setWidget(6, 2, null);

				/*
				 * Überprüfung der Textboxen (Namen, Körpergröße) auf logische
				 * Falscheingaben bzw. formale Inkorrektheiten (bspw. Zahl in
				 * Textfeld).
				 */
				if (!tbfn.getText().matches("^[a-zA-Z ]{0,30}$")) {
					t.setWidget(2, 2, lblWrongInputFirstName);
					return;
				}
				if (!tbn.getText().matches("^[a-zA-Z ]{0,30}$")) {
					t.setWidget(3, 2, lblWrongInputLastName);
					return;
				}
				if (!tbbh.getText().matches("^[1-2].[0-9]{1,2}$")) {
					t.setWidget(6, 2, lblWrongInputBodyHeight);
					return;
				}

				/*
				 * Speichern der eingegebenen Werte blockieren, um
				 * Mehrfach-Klicks und daraus entstehende, unnötige Einträge in
				 * der Datenbank zu verhindern.
				 */
				saveProfilButton.setEnabled(false);
				saveProfilButton.setStylePrimaryName("tngly-disabledButton");

				/*
				 * Auslesen der eingegebenen Werte aus den Widgets in den
				 * aktuellen Benutzer.
				 */
				int atIndex = ClientsideSettings.getLoginInfo().getEmailAddress().indexOf("@");
				currentUserProfile
						.setUserName(ClientsideSettings.getLoginInfo().getEmailAddress().substring(0, atIndex));
				currentUserProfile.setName(capitalize(tbfn.getText().toUpperCase()));
				currentUserProfile.setLastName(capitalize(tbn.getText().toUpperCase()));
				int selectedGenderIndex = genderBox.getSelectedIndex();
				currentUserProfile.setGender(genderBox.getItemText(selectedGenderIndex));
				currentUserProfile.setDateOfBirth(datePicker.getValue());
				float f = Float.valueOf(tbbh.getText().trim()).floatValue();
				currentUserProfile.setBodyHeight(f);
				int selectedHairColourIndex = hairColourList.getSelectedIndex();
				currentUserProfile.setHairColour(hairColourList.getItemText(selectedHairColourIndex));
				int selectedIsSmokingIndex = isSmokingBox.getSelectedIndex();
				if (isSmokingBox.getItemText(selectedIsSmokingIndex) == "Yes") {
					currentUserProfile.setIsSmoking(1);
				} else {
					currentUserProfile.setIsSmoking(0);
				}
				int selectedConfessionIndex = confessionBox.getSelectedIndex();
				currentUserProfile.setConfession(confessionBox.getItemText(selectedConfessionIndex));

				ArrayList<Integer> selectedRowsMyHobbies = this.getSelectedRows(t2);
				ArrayList<Integer> selectedRowsFavoriteEra = this.getSelectedRows(t5);
				ArrayList<Integer> selectedRowsSubculture = this.getSelectedRows(t6);
				ArrayList<Integer> selectedRowsStrongPoints = this.getSelectedRows(t7);

				currentUserProfile.getSelectionList().get(0).getInformationValues().clear();

				/*
				 * Auslesen der Werte der Checkboxen für die Profileigenschaft
				 * "MyHobbies" und Schreiben dieser Werte in den aktuellen
				 * Benutzer
				 */
				for (int i : selectedRowsMyHobbies) {
					Information info = new Information();
					info.setProfileId(currentUserProfile.getId());
					info.setPropertyId(1);
					CheckBox checkBox = (CheckBox) t2.getWidget(i, 0);
					info.setValue(checkBox.getText());
					currentUserProfile.getSelectionList().get(0).getInformationValues().add(info);
				}

				/*
				 * Auslesen der Werte der Textbox für die Profileigenschaft
				 * "How I describe myself" und Schreiben dieser Werte in den
				 * aktuellen Benutzer
				 */
				if (currentUserProfile.getDescriptionList().get(0).getInformationValues().size() > 0) {
					currentUserProfile.getDescriptionList().get(0).getInformationValues().get(0)
							.setProfileId(currentUserProfile.getId());
					currentUserProfile.getDescriptionList().get(0).getInformationValues().get(0).setPropertyId(2);

					if (ta.getText().isEmpty()) {
						currentUserProfile.getDescriptionList().get(0).getInformationValues().get(0).setValue(null);
					} else {
						currentUserProfile.getDescriptionList().get(0).getInformationValues().get(0)
								.setValue(capitalize(ta.getText()));
					}
				} else {
					Information in = new Information();
					in.setProfileId(currentUserProfile.getId());
					in.setPropertyId(2);
					if (ta.getText().isEmpty()) {
						in.setValue(null);
					} else {
						in.setValue(ta.getText());
					}
					currentUserProfile.getDescriptionList().get(0).getInformationValues().add(in);
				}

				/*
				 * Auslesen der Werte der Textbox für die Profileigenschaft
				 * "Favorite Band" und Schreiben dieser Werte in den aktuellen
				 * Benutzer
				 */
				if (currentUserProfile.getDescriptionList().get(1).getInformationValues().size() > 0) {
					currentUserProfile.getDescriptionList().get(1).getInformationValues().get(0)
							.setProfileId(currentUserProfile.getId());
					currentUserProfile.getDescriptionList().get(1).getInformationValues().get(0).setPropertyId(3);

					if (tBb.getText().isEmpty()) {
						currentUserProfile.getDescriptionList().get(1).getInformationValues().get(0).setValue(null);
					} else {
						currentUserProfile.getDescriptionList().get(1).getInformationValues().get(0)
								.setValue(capitalize(tBb.getText()));
					}
				} else {
					Information in = new Information();
					in.setProfileId(currentUserProfile.getId());
					in.setPropertyId(3);
					if (tBb.getText().isEmpty()) {
						in.setValue(null);
					} else {
						in.setValue(tBb.getText());
					}
					currentUserProfile.getDescriptionList().get(1).getInformationValues().add(in);
				}

				/*
				 * Auslesen der Werte der Textbox für die Profileigenschaft
				 * "How I describe myself" und Schreiben dieser Werte in den
				 * aktuellen Benutzer
				 */
				if (currentUserProfile.getDescriptionList().get(2).getInformationValues().size() > 0) {
					currentUserProfile.getDescriptionList().get(2).getInformationValues().get(0)
							.setProfileId(currentUserProfile.getId());
					currentUserProfile.getDescriptionList().get(2).getInformationValues().get(0).setPropertyId(6);

					if (tBm.getText().isEmpty()) {
						currentUserProfile.getDescriptionList().get(2).getInformationValues().get(0).setValue(null);
					} else {
						currentUserProfile.getDescriptionList().get(2).getInformationValues().get(0)
								.setValue(capitalize(tBm.getText()));
					}
				} else {
					Information in = new Information();
					in.setProfileId(currentUserProfile.getId());
					in.setPropertyId(6);
					if (tBm.getText().isEmpty()) {
						in.setValue(null);
					} else {
						in.setValue(tBm.getText());
					}
					currentUserProfile.getDescriptionList().get(2).getInformationValues().add(in);
				}

				/*
				 * Auslesen der Werte der Checkboxen für die Profileigenschaft
				 * "My Strong Points" und Schreiben dieser Werte in den
				 * aktuellen Benutzer
				 */
				currentUserProfile.getSelectionList().get(1).getInformationValues().clear();
				for (int i : selectedRowsStrongPoints) {
					Information info = new Information();
					info.setProfileId(currentUserProfile.getId());
					info.setPropertyId(4);
					CheckBox checkBox = (CheckBox) t7.getWidget(i, 0);
					info.setValue(checkBox.getText());
					currentUserProfile.getSelectionList().get(1).getInformationValues().add(info);
				}

				/*
				 * Auslesen der Werte der Checkboxen für die Profileigenschaft
				 * "I associate myself with this subculture" und Schreiben
				 * dieser Werte in den aktuellen Benutzer
				 */
				currentUserProfile.getSelectionList().get(2).getInformationValues().clear();
				for (int i : selectedRowsSubculture) {
					Information info = new Information();
					info.setProfileId(currentUserProfile.getId());
					info.setPropertyId(5);
					CheckBox checkBox = (CheckBox) t6.getWidget(i, 0);
					info.setValue(checkBox.getText());
					currentUserProfile.getSelectionList().get(2).getInformationValues().add(info);
				}

				/*
				 * Auslesen der Werte der Checkboxen für die Profileigenschaft
				 * "Favorite Era" und Schreiben dieser Werte in den aktuellen
				 * Benutzer
				 */
				currentUserProfile.getSelectionList().get(3).getInformationValues().clear();
				for (int i : selectedRowsFavoriteEra) {
					Information info = new Information();
					info.setProfileId(currentUserProfile.getId());
					info.setPropertyId(7);
					CheckBox checkBox = (CheckBox) t5.getWidget(i, 0);
					info.setValue(checkBox.getText());
					currentUserProfile.getSelectionList().get(3).getInformationValues().add(info);
				}
				adminService.editProfile(currentUserProfile, updateUserProfileCallback());
			}

			/**
			 * Gibt alle selektierten Zeilen der übergebenen FlexTable zurück.
			 * 
			 * @author Kevin Jaeger
			 * @return Liste mit den Nummern der selektierten Zeilen der
			 *         FlexTable
			 */
			private ArrayList<Integer> getSelectedRows(FlexTable table) {
				ArrayList<Integer> selectedRows = new ArrayList<Integer>();

				for (int i = 0; i < table.getRowCount(); ++i) {
					CheckBox checkBox = (CheckBox) table.getWidget(i, 0);
					if (checkBox.getValue()) {
						selectedRows.add(i);
					}
				}
				return selectedRows;
			}
		});

		/*
		 * Zuweisung der ClickHandler an die jeweiligen Buttons.
		 */
		deleteProfileButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				deleteProfileButton.setEnabled(false);
				deleteProfileButton.setStylePrimaryName("tngly-disabledButton");
				adminService.deleteProfile(currentUserProfile, deleteUserProfileCallback());
			}
		});
	}

	/**
	 * AsyncCallback für das Auslesen vom Profil des aktuellen Benutzers aus der
	 * Datenbank.
	 * 
	 * @return Profil des aktuellen Benutzers
	 */
	private AsyncCallback<Profile> getCurrentUserProfileCallback() {
		AsyncCallback<Profile> asyncCallback = new AsyncCallback<Profile>() {

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Profile result) {
				ClientsideSettings.getLogger()
						.severe("Success GetCurrentUserProfileCallback: " + result.getClass().getSimpleName());
				currentUserProfile = result;

				/*
				 * Zuweisung von Profilwerten an die jeweiligen Widgets.
				 */
				tbun.setText(result.getUserName());
				tbfn.setText(result.getName());
				tbn.setText(result.getLastName());

				int index = 0;
				switch (result.getGender()) {
				case "Male":
					index = 1;
					break;
				case "Female":
					index = 0;
					break;
				default:
				}
				genderBox.setItemSelected(index, true);

				Date dob = result.getDateOfBirth();
				datePicker.setValue(dob);
				datePicker.setCurrentMonth(dob);

				float bh = result.getBodyHeight();
				float bhFormatted = (float) (Math.round(bh * 100) / 100.0);
				tbbh.setText(Float.toString(bhFormatted));

				switch (result.getHairColour()) {
				case "Black":
					index = 0;
					break;
				case "Brown":
					index = 1;
					break;
				case "Red":
					index = 2;
					break;
				case "Blonde":
					index = 3;
					break;
				case "Dark Blonde":
					index = 4;
					break;
				default:
				}
				hairColourList.setItemSelected(index, true);

				isSmokingBox.setItemSelected(result.getIsSmoking(), true);

				switch (result.getConfession()) {
				case "Atheistic":
					index = 0;
					break;
				case "Buddhistic":
					index = 1;
					break;
				case "Evangelic":
					index = 2;
					break;
				case "Catholic":
					index = 3;
					break;
				case "Hindu":
					index = 4;
					break;
				case "Muslim":
					index = 5;
					break;
				case "Jewish":
					index = 6;
					break;
				case "Orthodox":
					index = 7;
					break;
				case "Other":
					index = 8;
					break;
				default:
				}
				confessionBox.setItemSelected(index, true);

				/*
				 * Markierung der Checkboxen für die Profileigenschaft
				 * "My Hobbies"
				 */
				for (int x = 0; x < t2.getRowCount(); x++) {
					ArrayList<String> values = new ArrayList<String>();
					String tempValue;
					CheckBox cb = (CheckBox) t2.getWidget(x, 0);
					for (Information i : result.getSelectionList().get(0).getInformationValues()) {
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
				if (result.getDescriptionList().get(0).getInformationValues().size() > 0) {
					ta.setText(result.getDescriptionList().get(0).getInformationValues().get(0).getValue());
				} else {
				}
				if (result.getDescriptionList().get(1).getInformationValues().size() > 0) {
					tBb.setText(result.getDescriptionList().get(1).getInformationValues().get(0).getValue());
				} else {
				}
				if (result.getDescriptionList().get(2).getInformationValues().size() > 0) {
					tBm.setText(result.getDescriptionList().get(2).getInformationValues().get(0).getValue());
				} else {
				}

				/*
				 * Markierung der Checkboxen für die Profileigenschaft
				 * "My Strong Points"
				 */
				for (int x = 0; x < t7.getRowCount(); x++) {
					ArrayList<String> values = new ArrayList<String>();
					String tempValue;
					CheckBox cb = (CheckBox) t7.getWidget(x, 0);
					for (Information i : result.getSelectionList().get(1).getInformationValues()) {
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
				 * Markierung der Checkboxen für die Profileigenschaft
				 * "I associate myself with this subculture"
				 */
				for (int x = 0; x < t6.getRowCount(); x++) {
					ArrayList<String> values = new ArrayList<String>();
					String tempValue;
					CheckBox cb = (CheckBox) t6.getWidget(x, 0);
					for (Information i : result.getSelectionList().get(2).getInformationValues()) {
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
				 * Markierung der Checkboxen für die Profileigenschaft
				 * "Favorite Era"
				 */
				for (int x = 0; x < t5.getRowCount(); x++) {
					ArrayList<String> values = new ArrayList<String>();
					String tempValue;
					CheckBox cb = (CheckBox) t5.getWidget(x, 0);

					for (Information i : result.getSelectionList().get(3).getInformationValues()) {
						tempValue = i.getValue();
						values.add(tempValue);
					}
					tempValue = cb.getText();
					if (values.contains(tempValue)) {
						cb.setValue(true);
						cb.addStyleName("tngly-checkbox-marked");
					}
				}
				currentUserProfile = result;
			}
		};
		return asyncCallback;
	}

	/**
	 * AsyncCallback für das Aktualisieren vom Profil des aktuellen Benutzers in der
	 * Datenbank.
	 * 
	 * @return
	 */
	private AsyncCallback<Void> updateUserProfileCallback() {
		AsyncCallback<Void> asyncCallback = new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				ClientsideSettings.getLogger().info("Zeile 688 ausgeführt, onSuccess firing");
				Update update = new ProfileView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		};
		return asyncCallback;
	}

	/**
	 * AsyncCallback für das Löschen vom Profil des aktuellen Benutzers in der
	 * Datenbank.
	 * 
	 * @return
	 */
	private AsyncCallback<Void> deleteUserProfileCallback() {
		AsyncCallback<Void> asyncCallback = new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				ClientsideSettings.getLogger().info("Success: DeleteUserProfileCallback");
				Window.open(ClientsideSettings.getLoginInfo().getLogoutUrl(), "_self", "");
			}
		};
		return asyncCallback;
	}

	/**
	 * Bearbeitet die Vor- und Nachnamen des aktuellen Benutzers für eine bessere Darstellung.
	 * 
	 * @author Philipp Schmitt
	 * @return Der bearbeitete Text
	 */
	public static String capitalize(String givenString) {
		String Separateur = " ,.-;";
		StringBuffer sb = new StringBuffer();
		boolean ToCap = true;
		for (int i = 0; i < givenString.length(); i++) {
			if (ToCap)
				sb.append(Character.toUpperCase(givenString.charAt(i)));
			else
				sb.append(Character.toLowerCase(givenString.charAt(i)));

			if (Separateur.indexOf(givenString.charAt(i)) >= 0)
				ToCap = true;
			else
				ToCap = false;
		}
		return sb.toString().trim();
	}
}
