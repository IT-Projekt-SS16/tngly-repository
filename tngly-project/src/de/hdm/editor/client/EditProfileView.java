package de.hdm.editor.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;


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

public class EditProfileView extends Update {

	private static final Logger logger = ClientsideSettings.getLogger();

	/**
	 * Instanziierung aller relevanten Eingabem�glichkeiten = Textboxen,
	 * Checkboxen, DatePicker usw.
	 */
	private AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();
	private Profile currentUserProfile = null;

	private VerticalPanel verPanel = new VerticalPanel();
	private VerticalPanel verPanel2 = new VerticalPanel();

	private HorizontalPanel horPanel = new HorizontalPanel();
	private HorizontalPanel horPanel2 = new HorizontalPanel();
	
	HTML horLine = new HTML("<hr  style=\"width:100%;\" />");
	HTML horLine2 = new HTML("<hr  style=\"width:100%;\" />");

	HTML verLine = new HTML("  <table style='display:inline;border-collapse:collapse;border:0'><tr><td style='padding:0'><img src='transparent.gif' width='1' height='625' style='background:grey'></td></tr></table>"); 
	
	private final Label lblWrongInputFirstName = new Label("Please only enter characters (a-z, A-Z) in field 'First Name'");
	private final Label lblWrongInputLastName = new Label("Please only enter characters (a-z, A-Z) in field 'Last Name'");
	private final Label lblWrongInputBodyHeight = new Label("Please only enter numbers in following pattern: '#.##' between 1.00 and 2.99");

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
	private final Button deleteProfileButton = new Button("Delete Profile");

	/**
	 * Jede View besitzt eine einleitende �berschrift, die durch diese Methode
	 * zu erstellen ist.
	 * 
	 * @see Showcase#getHeadlineText()
	 */

	@Override
	protected String getHeadlineText() {
		return "Edit Profile";
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
		
		horPanel2.setWidth("100%");
		horPanel2.setHorizontalAlignment(ALIGN_CENTER);

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
		
		datePicker.addShowRangeHandlerAndFire(new ShowRangeHandler<java.util.Date>()
	    {
			@Override
	        public void onShowRange(ShowRangeEvent<Date> event) 
	        {
	            Date start = event.getStart();
	            Date temp = CalendarUtil.copyDate(start);
	            Date end = event.getEnd();
	            
	            // long d = 817689600000L;
	            Date today = new Date();
				
	            while(temp.before(end))
	            {
	                if(temp.after(today) && datePicker.isDateVisible(temp))
	                {
	                    datePicker.setTransientEnabledOnDates(false,temp);
	                }
	                CalendarUtil.addDaysToDate(temp, 1);
	            }
	        }
	    });
		
		datePicker.addShowRangeHandlerAndFire(new ShowRangeHandler<java.util.Date>()
	    {
			@Override
	        public void onShowRange(ShowRangeEvent<Date> event) 
	        {
	            Date start = event.getStart();
	            Date temp = CalendarUtil.copyDate(start);
	            Date end = event.getEnd();
	            
	            // long d = 817689600000L;
	            Date today = new Date(-2209075200000L);
				
	            while(temp.before(end))
	            {
	                if(temp.before(today) && datePicker.isDateVisible(temp))
	                {
	                    datePicker.setTransientEnabledOnDates(false,temp);
	                }
	                CalendarUtil.addDaysToDate(temp, 1);
	            }
	        }
	    });

		ta.setWidth("230px");
		
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
		ta.setVisibleLines(2);

		t.setText(11, 0, "This is how I describe myself");
		t.setWidget(11, 1, ta);

		t3.setText(0, 0, "Favorite Band");
		t3.setWidget(0, 1, tBb);

		t3.setText(1, 0, "Favorite Movie");
		t3.setWidget(1, 1, tBm);

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
		
		t3.setText(4, 0, "Favorite Era");
		t5.setWidget(0, 0, chkStoneAge);
		t5.setWidget(1, 0, chkAncientTimes);
		t5.setWidget(2, 0, chkEarlyMiddleAges);
		t5.setWidget(3, 0, chkLateMiddleAges);
		t5.setWidget(4, 0, chkRenaissance);
		t5.setWidget(5, 0, chkIndustrialAge);
		t5.setWidget(6, 0, chkModernAge);
		t3.setWidget(4, 1, t5);

		saveProfilButton.setStyleName("tngly-bluebutton");
		deleteProfileButton.setStyleName("tngly-button");
		
		horPanel2.add(saveProfilButton);
		horPanel2.add(deleteProfileButton);
		
	//	t.setWidget(12, 1, horPanel2);
		
		lblWrongInputFirstName.setStyleName("serverResponseLabelError");
		lblWrongInputLastName.setStyleName("serverResponseLabelError");
		lblWrongInputBodyHeight.setStyleName("serverResponseLabelError");

		verPanel.add(t);
		verPanel2.add(t3);

		horPanel.add(verPanel);
		horPanel.add(verLine);
		horPanel.add(verPanel2);

		RootPanel.get("Details").add(horLine);
		RootPanel.get("Details").add(horPanel2);
		RootPanel.get("Details").add(horLine2);
		RootPanel.get("Details").add(horPanel);

		///////////////////////////////////////////////////////////////////////

		saveProfilButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				
				
				t.setWidget(2, 2, null);
				t.setWidget(3, 2, null);
				t.setWidget(6, 2, null);
				
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
				
				saveProfilButton.setEnabled(false);
				saveProfilButton.setStylePrimaryName("tngly-disabledButton");

				logger.info("Erfolgreich onClick ausgefuehrt.");
				Profile temp = new Profile();

				int atIndex = ClientsideSettings.getLoginInfo().getEmailAddress().indexOf("@");

				currentUserProfile
						.setUserName(ClientsideSettings.getLoginInfo().getEmailAddress().substring(0, atIndex));

				logger.info("UserName CHECK " + currentUserProfile.getUserName());

				currentUserProfile.setName(capitalize(tbfn.getText().toUpperCase()));

				logger.info("Name CHECK + " + currentUserProfile.getName());

				currentUserProfile.setLastName(capitalize(tbn.getText().toUpperCase()));

				logger.info("lastName CHECK + " + currentUserProfile.getLastName());

				int selectedGenderIndex = genderBox.getSelectedIndex();
				currentUserProfile.setGender(genderBox.getItemText(selectedGenderIndex));

				logger.info("gender CHECK");

				currentUserProfile.setDateOfBirth(datePicker.getValue());
				
				logger.info("dateOfBirth CHECK");

				float f = Float.valueOf(tbbh.getText().trim()).floatValue();
				currentUserProfile.setBodyHeight(f);

				logger.info("bodyHeight CHECK " + currentUserProfile.getBodyHeight());

				// temp.setHairColour(tbhc.getText());
				int selectedHairColourIndex = hairColourList.getSelectedIndex();
				currentUserProfile.setHairColour(hairColourList.getItemText(selectedHairColourIndex));

				logger.info("HairColour CHECK");

				int selectedIsSmokingIndex = isSmokingBox.getSelectedIndex();
				if (isSmokingBox.getItemText(selectedIsSmokingIndex) == "Yes") {
					currentUserProfile.setIsSmoking(1);
				} else {
					currentUserProfile.setIsSmoking(0);
				}

				logger.info("isSmoking CHECK");

				// temp.setConfession(tbc.getText());
				int selectedConfessionIndex = confessionBox.getSelectedIndex();
				currentUserProfile.setConfession(confessionBox.getItemText(selectedConfessionIndex));

				logger.info("Confession CHECK");

				ArrayList<Integer> selectedRowsMyHobbies = this.getSelectedRows(t2);

				for (int i : selectedRowsMyHobbies) {
					logger.info("Selektiert ist Zeile: " + i);
				}

				ArrayList<Integer> selectedRowsFavoriteEra = this.getSelectedRows(t5);
				ArrayList<Integer> selectedRowsSubculture = this.getSelectedRows(t6);
				ArrayList<Integer> selectedRowsStrongPoints = this.getSelectedRows(t7);

				logger.info("Zeile 333 ausgeführt");
				currentUserProfile.getSelectionList().get(0).getInformationValues().clear();

				/**
				 * Auslesen der Checkbox-Werte f�r die Eigenschaft "My
				 * Hobbies"
				 */
				logger.info("Zeile 381 ausgeführt");
				for (int i : selectedRowsMyHobbies) {
					Information info = new Information();
					info.setProfileId(currentUserProfile.getId());
					info.setPropertyId(1);
					CheckBox checkBox = (CheckBox) t2.getWidget(i, 0);
					logger.info("Zeile 344 ausgeführt");

					info.setValue(checkBox.getText());
					logger.info("Info-Values: " + info.getValue());
					currentUserProfile.getSelectionList().get(0).getInformationValues().add(info);
				}

				logger.info("Zeile 350 ausgeführt");

				/**
				 * Auslesen & Setzen der Eigenschaft "How I describe myself"
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
				}

				else {
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

				logger.info("Zeile 364 ausgeführt");

				/**
				 * Auslesen & Setzen der Eigenschaft "Favorite Band"
				 */
				if (currentUserProfile.getDescriptionList().get(1).getInformationValues().size() > 0) {
					currentUserProfile.getDescriptionList().get(1).getInformationValues().get(0)
							.setProfileId(currentUserProfile.getId());
					currentUserProfile.getDescriptionList().get(1).getInformationValues().get(0).setPropertyId(3);

					if (tBb.getText().isEmpty()) {
						currentUserProfile.getDescriptionList().get(1).getInformationValues().get(0).setValue(null);
					} else {
						currentUserProfile.getDescriptionList().get(1).getInformationValues().get(0)
								.setValue(tBb.getText());
					}
				}

				else {
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

				/**
				 * Auslesen & Setzen der Eigenschaft "Favorite Movie"
				 */

				if (currentUserProfile.getDescriptionList().get(2).getInformationValues().size() > 0) {
					currentUserProfile.getDescriptionList().get(2).getInformationValues().get(0)
							.setProfileId(currentUserProfile.getId());
					currentUserProfile.getDescriptionList().get(2).getInformationValues().get(0).setPropertyId(6);

					if (tBm.getText().isEmpty()) {
						currentUserProfile.getDescriptionList().get(2).getInformationValues().get(0).setValue(null);
					} else {
						currentUserProfile.getDescriptionList().get(2).getInformationValues().get(0)
								.setValue(tBm.getText());
					}
				}

				else {
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

				logger.info("Zeile 369 ausgeführt");

				/**
				 * Auslesen der Checkbox-Werte f�r die Eigenschaft "My Strong
				 * Points"
				 */
				currentUserProfile.getSelectionList().get(1).getInformationValues().clear();
				for (int i : selectedRowsStrongPoints) {
					Information info = new Information();
					info.setProfileId(currentUserProfile.getId());
					info.setPropertyId(4);
					CheckBox checkBox = (CheckBox) t7.getWidget(i, 0);
					logger.info("Zeile 429 ausgeführt");

					info.setValue(checkBox.getText());
					logger.info("Info-Values: " + info.getValue());
					currentUserProfile.getSelectionList().get(1).getInformationValues().add(info);
				}

				/**
				 * Auslesen der Checkbox-Werte f�r die Eigenschaft "I
				 * associate myself with this subculture"
				 */
				currentUserProfile.getSelectionList().get(2).getInformationValues().clear();
				for (int i : selectedRowsSubculture) {
					Information info = new Information();
					info.setProfileId(currentUserProfile.getId());
					info.setPropertyId(5);
					CheckBox checkBox = (CheckBox) t6.getWidget(i, 0);
					logger.info("Zeile 446 ausgeführt");

					info.setValue(checkBox.getText());
					logger.info("Info-Values: " + info.getValue());
					currentUserProfile.getSelectionList().get(2).getInformationValues().add(info);
				}

				/*
				 * Auslesen der Checkbox-Werte f�r die Eigenschaft
				 * "Favorite Era"
				 */

				currentUserProfile.getSelectionList().get(3).getInformationValues().clear();
				for (int i : selectedRowsFavoriteEra) {
					Information info = new Information();
					info.setProfileId(currentUserProfile.getId());
					info.setPropertyId(7);
					CheckBox checkBox = (CheckBox) t5.getWidget(i, 0);
					logger.info("Zeile 446 ausgeführt");

					info.setValue(checkBox.getText());
					logger.info("Info-Values: " + info.getValue());
					currentUserProfile.getSelectionList().get(3).getInformationValues().add(info);
				}

				logger.info("Zeile 470 ausgeführt");
				adminService.editProfile(currentUserProfile, updateUserProfileCallback());
				logger.info("editProfile vom AdminService ausgeführt");

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
		
		deleteProfileButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				
				deleteProfileButton.setEnabled(false);
				deleteProfileButton.setStylePrimaryName("tngly-disabledButton");
				
				logger.info("DeleteProfilButton onClick aufgerufen");
				adminService.deleteProfile(currentUserProfile, deleteUserProfileCallback());
				logger.info("deleteProfile vom AdminService ausgefuehrt");
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
				 * Markierung der Checkboxen f�r die Eigenschaft "My Hobbies"
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

				if (result.getDescriptionList().get(0).getInformationValues().size() > 0) {
					logger.info("Profile-Description 0: "
							+ result.getDescriptionList().get(0).getInformationValues().get(0).getValue());

					ta.setText(result.getDescriptionList().get(0).getInformationValues().get(0).getValue());
				} else {
					logger.info("Profile-Description 0: null");
				}

				if (result.getDescriptionList().get(1).getInformationValues().size() > 0) {

					logger.info("Profile-Description 1: "
							+ result.getDescriptionList().get(1).getInformationValues().get(0).getValue());

					tBb.setText(result.getDescriptionList().get(1).getInformationValues().get(0).getValue());
				} else {
					logger.info("Profile-Description 1: null");
				}

				if (result.getDescriptionList().get(2).getInformationValues().size() > 0) {
					logger.info("Profile-Description 2: "
							+ result.getDescriptionList().get(2).getInformationValues().get(0).getValue());

					tBm.setText(result.getDescriptionList().get(2).getInformationValues().get(0).getValue());
				} else {
					logger.info("Profile-Description 2: null");
				}

				/*
				 * Markierung der Checkboxen f�r die Eigenschaft
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
					for (Information i : result.getSelectionList().get(2).getInformationValues()) {
						tempValue = i.getValue();
						values.add(tempValue);
					}
					tempValue = cb.getText();
					if (values.contains(tempValue)) {
						cb.setValue(true);
					}
				}

				/*
				 * Markierung der Checkboxen f�r die Eigenschaft
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
					}
				}

				currentUserProfile = result;
			}
		};
		logger.info("AsyncCallback zu Ende ausgeführt");
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
				ClientsideSettings.getLogger().info("Zeile 688 ausgeführt, onSuccess firing");
				Update update = new ProfileView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
				logger.info("Erfolgreicher Reswitch.");
			}
		};
		return asyncCallback;
	}

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
	
	public static String capitalize (String givenString) {
	    String Separateur = " ,.-;";
	    StringBuffer sb = new StringBuffer(); 
	    boolean ToCap = true;
	    for (int i = 0; i < givenString.length(); i++) {
	        if (ToCap)              
	            sb.append(Character.toUpperCase(givenString.charAt(i)));
	        else
	            sb.append(Character.toLowerCase(givenString.charAt(i)));

	        if (Separateur.indexOf(givenString.charAt(i)) >=0) 
	            ToCap = true;
	        else
	            ToCap = false;
	    }          
	  return sb.toString().trim();
	}  
}
