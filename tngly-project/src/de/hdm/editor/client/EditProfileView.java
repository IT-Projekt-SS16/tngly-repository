package de.hdm.editor.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.shared.AdministrationServiceAsync;
import de.hdm.core.shared.bo.Information;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.ProfileVisit;

public class EditProfileView extends Update {

	private static final Logger logger = ClientsideSettings.getLogger();

	/**
	 * Instanziierung aller relevanten Eingabemöglichkeiten = Textboxen,
	 * Checkboxen, DatePicker usw.
	 */
	private Boolean existsUserInDB = null;
	private AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();
	private Profile currentUserProfile = null;

	private VerticalPanel verPanel = new VerticalPanel();
	private VerticalPanel verPanel2 = new VerticalPanel();

	private HorizontalPanel horPanel = new HorizontalPanel();

	private final TextBox tbun = new TextBox();
	private final TextBox tbfn = new TextBox();
	private final TextBox tbn = new TextBox();
	private final TextBox tbbh = new TextBox();
	private final TextBox tBb = new TextBox();
	private final TextBox tBm = new TextBox();
	private final TextBox tbsp = new TextBox();

	private TextArea ta = new TextArea();

	private final ListBox hairColourList = new ListBox(false);
	private final ListBox isSmokingBox = new ListBox(false);
	private final ListBox confessionBox = new ListBox(false);
	private final ListBox genderBox = new ListBox(false);
	private final ListBox myHobbiesSelect = new ListBox(true);

	final ListBox subcultureBox = new ListBox(false);
	final ListBox eraBox = new ListBox(false);

	private final DatePicker datePicker = new DatePicker();

	private FlexTable t = new FlexTable();
	private FlexTable t2 = new FlexTable();
	private FlexTable t3 = new FlexTable();
	private FlexTable t4 = new FlexTable();
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

	/**
	 * Jede View besitzt eine einleitende Überschrift, die durch diese Methode
	 * zu erstellen ist.
	 * 
	 * @see Showcase#getHeadlineText()
	 */

	protected String getHeadlineText() {
		return "";
	}

	/**
	 * Jede View muss die <code>run()</code>-Methode implementieren. Sie ist
	 * eine "Einschubmethode", die von einer Methode der Basisklasse
	 * <code>Update</code> aufgerufen wird, wenn der View aktiviert wird.
	 */
	@Override
	protected void run() {

		int atIndex = ClientsideSettings.getLoginInfo().getEmailAddress().indexOf("@");
		adminService.getProfileByUserName(ClientsideSettings.getLoginInfo().getEmailAddress().substring(0, atIndex),
				getCurrentUserProfileCallback());

		logger.info("Erfolgreich Profile-Edit-View geswitcht.");
		logger.info(ClientsideSettings.getLoginInfo().getEmailAddress());

		verPanel.setSpacing(10);
		verPanel2.setSpacing(10);

		tbun.setPixelSize(120, 15);
		tbfn.setPixelSize(120, 15);
		tbn.setPixelSize(120, 15);
		tbbh.setPixelSize(120, 15);

		hairColourList.setVisibleItemCount(1);
		hairColourList.addItem("Black");
		hairColourList.addItem("Brown");
		hairColourList.addItem("Red");
		hairColourList.addItem("Blonde");
		hairColourList.addItem("Dark Blonde");
		hairColourList.setPixelSize(130, 25);

		isSmokingBox.setVisibleItemCount(1);
		isSmokingBox.addItem("Yes");
		isSmokingBox.addItem("No");
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
		// Zeigt 51 years in the years dropdown. The range of years is centered
		// on the selected date
		datePicker.setVisibleYearCount(101);
		datePicker.setYearAndMonthDropdownVisible(true);

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

		t.setText(6, 0, "Body Height");
		t.setWidget(6, 1, tbbh);

		t.setText(7, 0, "Haircolor");
		t.setWidget(7, 1, hairColourList);

		t.setText(8, 0, "Smoker");
		t.setWidget(8, 1, isSmokingBox);

		t.setText(9, 0, "Confession");
		t.setWidget(9, 1, confessionBox);

		t.setText(10, 0, "Hobbies");
		t2.setWidget(0, 0, chkVolleyball);
		t2.setWidget(1, 0, chkFootball);
		t2.setWidget(2, 0, chkWatchPeople);
		t2.setWidget(3, 0, chkIT);
		t2.setWidget(4, 0, chkHandball);
		t2.setWidget(5, 0, chkPP);

		t.setWidget(10, 1, t2);

		ta.setCharacterWidth(50);
		ta.setVisibleLines(5);

		t.setText(11, 0, "This is how I describe myself");
		t.setWidget(11, 1, ta);

		t3.setText(0, 0, "Favorite Band");
		t3.setWidget(0, 1, tBb);

		t3.setText(1, 0, "Favorite Movie");
		t3.setWidget(1, 1, tBm);

		t3.setText(4, 0, "Favorite Era");
		t5.setWidget(0, 0, chkStoneAge);
		t5.setWidget(1, 0, chkAncientTimes);
		t5.setWidget(2, 0, chkEarlyMiddleAges);
		t5.setWidget(3, 0, chkLateMiddleAges);
		t5.setWidget(4, 0, chkRenaissance);
		t5.setWidget(5, 0, chkIndustrialAge);
		t5.setWidget(6, 0, chkModernAge);
		t3.setWidget(4, 1, t5);

		t3.setText(2, 0, "My Strong Points");
		t7.setWidget(0, 0, chkBringing);
		t7.setWidget(1, 0, chkEnjoying);
		t7.setWidget(2, 0, chkBeing);
		t7.setWidget(3, 0, chkSolving);
		t7.setWidget(4, 0, chkKeeping);
		t3.setWidget(2, 1, t7);

		t3.setText(3, 0, "I associate myself with this subculture");
		t6.setWidget(0, 0, chkHipHop);
		t6.setWidget(1, 0, chkMetal);
		t6.setWidget(2, 0, chkRock);
		t6.setWidget(3, 0, chkEmo);
		t6.setWidget(4, 0, chkAzzlackz);
		t3.setWidget(3, 1, t6);

		t.setWidget(12, 1, saveProfilButton);
		saveProfilButton.setStyleName("tngly-button");

		verPanel.add(t);
		verPanel2.add(t3);

		horPanel.add(verPanel);
		horPanel.add(verPanel2);

		RootPanel.get("Details").add(horPanel);

		saveProfilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				final String symbol = tbfn.getText().toUpperCase().trim();
				if (ClientsideSettings.getUserProfile() == null) {
					Window.alert("Please insert a First Name");
				} else {
					if (!symbol.matches("^[0-9A-Z\\.]{1,10}$")) {
						Window.alert("'" + symbol + "' is not a valid symbol.");

						Update update = new EditProfileView();
						RootPanel.get("Details").clear();
						RootPanel.get("Details").add(update);
						return;
					}
				}

				final String symbol1 = tbn.getText().toUpperCase().trim();
				if (!symbol1.matches("^[0-9A-Z\\.]{1,10}$")) {
					Window.alert("'" + symbol1 + "' is not a valid symbol.");
					Update update = new EditProfileView();
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(update);
					return;
				}
				//
				// final String symbol2 = tbbh.getText().toUpperCase().trim();
				// if (!symbol2.matches("^[0-9A-Z\\.]{1,10}$")) {
				// Window.alert("'" + symbol2 + "' is not a valid symbol.");
				// tbbh.selectAll();
				// return;
				// }

				logger.info("Erfolgreich onClick ausgefuehrt.");
				Profile temp = new Profile();

				int atIndex = ClientsideSettings.getLoginInfo().getEmailAddress().indexOf("@");

				temp.setUserName(ClientsideSettings.getLoginInfo().getEmailAddress().substring(0, atIndex));

				logger.info("Profile+DateTimeFormat instantiiert");

				temp.setName(tbfn.getText());

				logger.info("Name CHECK");

				temp.setLastName(tbn.getText());

				logger.info("lastName CHECK");

				int selectedGenderIndex = genderBox.getSelectedIndex();
				temp.setGender(genderBox.getItemText(selectedGenderIndex));

				logger.info("gender CHECK");

				temp.setDateOfBirth(datePicker.getValue());

				logger.info("dateOfBirth CHECK");

				float f = Float.valueOf(tbbh.getText().trim()).floatValue();
				temp.setBodyHeight(f);

				logger.info("bodyHeight CHECK");

				// temp.setHairColour(tbhc.getText());
				int selectedHairColourIndex = hairColourList.getSelectedIndex();
				temp.setHairColour(hairColourList.getItemText(selectedHairColourIndex));

				logger.info("HairColour CHECK");

				int selectedIsSmokingIndex = isSmokingBox.getSelectedIndex();
				if (isSmokingBox.getItemText(selectedIsSmokingIndex) == "Yes") {
					temp.setIsSmoking(1);
				} else {
					temp.setIsSmoking(0);
				}

				logger.info("isSmoking CHECK");

				// temp.setConfession(tbc.getText());
				int selectedConfessionIndex = confessionBox.getSelectedIndex();
				temp.setConfession(confessionBox.getItemText(selectedConfessionIndex));

				logger.info("Confession CHECK");

				ArrayList<Integer> selectedRowsMyHobbies = this.getSelectedRows(t2);
				ArrayList<Integer> selectedRowsFavoriteEra = this.getSelectedRows(t5);
				ArrayList<Integer> selectedRowsSubculture = this.getSelectedRows(t6);
				ArrayList<Integer> selectedRowsStrongPoints = this.getSelectedRows(t7);

				/*
				 * Auslesen der Checkbox-Werte für die Eigenschaft "My Hobbies"
				 */
				for (int i = 0; i < selectedRowsMyHobbies.size(); ++i) {
					Information info = new Information();
					info.setProfileId(currentUserProfile.getId());
					info.setPropertyId(1);
					CheckBox checkBox = (CheckBox) t2.getWidget(i, 0);
					info.setValue(checkBox.getText());
					temp.getSelectionList().get(0).getInformationValues().add(info);
				}
				
				/*
				 * Auslesen & Setzen der Eigenschaft "How I describe myself"
				 */
				temp.getDescriptionList().get(0).getInformationValues().get(0).setProfileId(currentUserProfile.getId());
				temp.getDescriptionList().get(0).getInformationValues().get(0).setPropertyId(2);
				temp.getDescriptionList().get(0).getInformationValues().get(0).setValue(ta.getText());
				
				/*
				 * Auslesen & Setzen der Eigenschaft "Favorite Band"
				 */
				temp.getDescriptionList().get(1).getInformationValues().get(0).setProfileId(currentUserProfile.getId());
				temp.getDescriptionList().get(1).getInformationValues().get(0).setPropertyId(3);
				temp.getDescriptionList().get(1).getInformationValues().get(0).setValue(tBb.getText());
				
				/*
				 * Auslesen & Setzen der Eigenschaft "Favorite Movie"
				 */
				temp.getDescriptionList().get(2).getInformationValues().get(0).setProfileId(currentUserProfile.getId());
				temp.getDescriptionList().get(2).getInformationValues().get(0).setPropertyId(6);
				temp.getDescriptionList().get(2).getInformationValues().get(0).setValue(tBm.getText());
				
				/*
				 * Auslesen der Checkbox-Werte für die Eigenschaft "My Strong Points"
				 */
				for (int i = 0; i < selectedRowsStrongPoints.size(); ++i) {
					Information info = new Information();
					info.setProfileId(currentUserProfile.getId());
					info.setPropertyId(7);
					CheckBox checkBox = (CheckBox) t7.getWidget(i, 0);
					info.setValue(checkBox.getText());
					temp.getSelectionList().get(3).getInformationValues().add(info);
				}
				
				/*
				 * Auslesen der Checkbox-Werte für die Eigenschaft "I associate myself with this subculture"
				 */
				for (int i = 0; i < selectedRowsSubculture.size(); ++i) {
					Information info = new Information();
					info.setProfileId(currentUserProfile.getId());
					info.setPropertyId(5);
					CheckBox checkBox = (CheckBox) t6.getWidget(i, 0);
					info.setValue(checkBox.getText());
					temp.getSelectionList().get(2).getInformationValues().add(info);
				}
				
				/*
				 * Auslesen der Checkbox-Werte für die Eigenschaft "Favorite Era"
				 */
				for (int i = 0; i < selectedRowsFavoriteEra.size(); ++i) {
					Information info = new Information();
					info.setProfileId(currentUserProfile.getId());
					info.setPropertyId(4);
					CheckBox checkBox = (CheckBox) t5.getWidget(i, 0);
					info.setValue(checkBox.getText());
					temp.getSelectionList().get(1).getInformationValues().add(info);
				}

				adminService.editProfile(temp, updateUserProfileCallback());
			}

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
	}

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

				tbun.setText(result.getUserName());
				tbfn.setText(result.getName());
				tbn.setText(result.getLastName());

				int index;
				if (result.getGender() == "Male") {
					index = 1;
				} else {
					index = 0;
				}
				genderBox.setItemSelected(index, true);

				Date dob = result.getDateOfBirth();

				datePicker.setValue(dob);

				datePicker.setCurrentMonth(dob);

				float bh = result.getBodyHeight();

				float bhFormatted = (float) (Math.round(bh * 100) / 100.0);

				tbbh.setText(Float.toString(bhFormatted));

				if (result.getHairColour() == "Black") {
					index = 0;
				} else if (result.getHairColour() == "Brown") {
					index = 1;
				} else if (result.getHairColour() == "Red") {
					index = 2;
				} else if (result.getHairColour() == "Blonde") {
					index = 3;
				} else {
					index = 4;
				}
				hairColourList.setItemSelected(index, true);

				isSmokingBox.setItemSelected(result.getIsSmoking(), true);

				if (result.getConfession() == "Atheistic") {
					index = 0;
				} else if (result.getConfession() == "Buddhistic") {
					index = 1;
				} else if (result.getConfession() == "Evangelic") {
					index = 2;
				} else if (result.getConfession() == "Catholic") {
					index = 3;
				} else if (result.getConfession() == "Hindu") {
					index = 4;
				} else if (result.getConfession() == "Muslim") {
					index = 5;
				} else if (result.getConfession() == "Jewish") {
					index = 6;
				} else if (result.getConfession() == "Orthodox") {
					index = 7;
				} else {
					index = 8;
				}
				confessionBox.setItemSelected(index, true);

				/*
				 * Markierung der Checkboxen für die Eigenschaft "My Hobbies"
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
					}
				}

				ta.setText(result.getDescriptionList().get(0).getInformationValues().get(0).getValue());
				tBb.setText(result.getDescriptionList().get(1).getInformationValues().get(0).getValue());
				tBm.setText(result.getDescriptionList().get(2).getInformationValues().get(0).getValue());

				/*
				 * Markierung der Checkboxen für die Eigenschaft
				 * "My Strong Points"
				 */
				for (int x = 0; x < t7.getRowCount(); x++) {
					ArrayList<String> values = new ArrayList<String>();
					String tempValue;
					CheckBox cb = (CheckBox) t7.getWidget(x, 0);
					for (Information i : result.getSelectionList().get(0).getInformationValues()) {
						tempValue = i.getValue();
						values.add(tempValue);
					}
					tempValue = cb.getText();
					if (values.contains(tempValue)) {
						cb.setValue(true);
					}
				}

				/*
				 * Markierung der Checkboxen für die Eigenschaft
				 * "I associate myself with this subculture"
				 */
				for (int x = 0; x < t6.getRowCount(); x++) {
					ArrayList<String> values = new ArrayList<String>();
					String tempValue;
					CheckBox cb = (CheckBox) t6.getWidget(x, 0);
					for (Information i : result.getSelectionList().get(0).getInformationValues()) {
						tempValue = i.getValue();
						values.add(tempValue);
					}
					tempValue = cb.getText();
					if (values.contains(tempValue)) {
						cb.setValue(true);
					}
				}

				/*
				 * Markierung der Checkboxen für die Eigenschaft "Favorite Era"
				 */
				for (int x = 0; x < t5.getRowCount(); x++) {
					ArrayList<String> values = new ArrayList<String>();
					String tempValue;
					CheckBox cb = (CheckBox) t5.getWidget(x, 0);
					for (Information i : result.getSelectionList().get(0).getInformationValues()) {
						tempValue = i.getValue();
						values.add(tempValue);
					}
					tempValue = cb.getText();
					if (values.contains(tempValue)) {
						cb.setValue(true);
					}
				}
			}
		};
		return asyncCallback;
	}

	private AsyncCallback<Void> updateUserProfileCallback() {
		AsyncCallback<Void> asyncCallback = new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				ClientsideSettings.getLogger().severe("Success: " + result.getClass().getSimpleName());
				Update update = new ProfileView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
				logger.info("Erfolgreicher Reswitch.");
			}
		};
		return asyncCallback;
	}

}
