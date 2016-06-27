package de.hdm.editor.client;

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
import de.hdm.core.shared.bo.Profile;

public class EditProfileView extends Update {
	
	private static final Logger logger = ClientsideSettings.getLogger();

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
	
	private final CheckBox chkVolleyball = new CheckBox();
	private final CheckBox chkFootball = new CheckBox();
	private final CheckBox chkWatchPeople = new CheckBox();
	private final CheckBox chkIT = new CheckBox();
	private final CheckBox chkHandball = new CheckBox();
	private final CheckBox chkPP = new CheckBox();
	
	private final CheckBox chkStoneAge = new CheckBox();
	private final CheckBox chkAncientTimes = new CheckBox();
	private final CheckBox chkEarlyMiddleAges = new CheckBox();
	private final CheckBox chkLateMiddleAges = new CheckBox();
	private final CheckBox chkRenaissance = new CheckBox();
	private final CheckBox chkIndusrialAge = new CheckBox();
	private final CheckBox chkModernAge = new CheckBox();
	
	private final CheckBox chkBringing = new CheckBox();
	private final CheckBox chkEnjoying = new CheckBox();
	private final CheckBox chkBeing = new CheckBox();
	private final CheckBox chkSolving = new CheckBox();
	private final CheckBox chkKeeping = new CheckBox();
	
	private final Button saveProfilButton = new Button("Save");

	/**
	 * Jede View besitzt eine einleitende Überschrift, die durch diese
	 * Methode zu erstellen ist.
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
		adminService.getProfileByUserName(
				ClientsideSettings.getLoginInfo().getEmailAddress().substring(0, atIndex), getCurrentUserProfileCallback());
		
		logger.info("Erfolgreich Profile-Edit-View geswitcht.");
		logger.info(ClientsideSettings.getLoginInfo().getEmailAddress());
		
		verPanel.setSpacing(10);
		verPanel2.setSpacing(10);
		
		/**
		 * Instanziierung aller relevanten Eingabemöglichkeiten = Textboxen, Checkboxen, DatePicker usw. 
		 */

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
		hairColourList.setPixelSize(130,25);
		
		isSmokingBox.setVisibleItemCount(1);
		isSmokingBox.addItem("Yes");
		isSmokingBox.addItem("No");
		isSmokingBox.setPixelSize(130,25);
		
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
		confessionBox.setPixelSize(130,25);

		genderBox.setVisibleItemCount(1);
		genderBox.addItem("Female");
		genderBox.addItem("Male");
		genderBox.setPixelSize(130,25);

		datePicker.setYearArrowsVisible(true);
		datePicker.setYearAndMonthDropdownVisible(false);
		// Zeigt 51 years in the years dropdown. The range of years is centered on the selected date
		datePicker.setVisibleYearCount(101);
		datePicker.setYearAndMonthDropdownVisible(true);

		if (currentUserProfile != null) {
			logger.info("Result: " + currentUserProfile.getUserName());
		} else {
			logger.info("Result: NULL");
		}
		
		t.setText(0, 0, "Username");
		tbun.setEnabled(false);
		t.setWidget(0, 1, tbun);
		
		t.setText(2,0,"First Name");
			t.setWidget(2,1,tbfn);
		
		t.setText(3, 0, "Last Name");
			t.setWidget(3,1,tbn);

		t.setText(4, 0, "Gender");
			t.setWidget(4, 1, genderBox);  
		
		t.setText(5, 0, "Date of Birth");
		t.setWidget(5,1,datePicker);
		

		t.setText(6,0, "Body Height");
			t.setWidget(6,1,tbbh);

		t.setText(7, 0, "Haircolor");
			t.setWidget(7,1,hairColourList);

		t.setText(8, 0, "Smoker");
			t.setWidget(8, 1, isSmokingBox);

		t.setText(9, 0, "Confession");
		t.setWidget(9, 1, confessionBox);
		
		t.setText(10,0, "Hobbies");
		
		FlexTable t2 = new FlexTable();
		
		t2.setText(0, 0, "Volleyball");
		t2.setWidget(0, 1, chkVolleyball);
		
		t2.setText(1, 0, "Football");
		t2.setWidget(1, 1, chkFootball);
		
		t2.setText(2, 0, "Watch People");
		t2.setWidget(2, 1, chkWatchPeople);
		
		t2.setText(3, 0, "Not working at the IT-Project");
		t2.setWidget(3, 1, chkIT);
		
		t2.setText(4, 0, "Handball");
		t2.setWidget(4, 1, chkHandball);
		
		t2.setText(5, 0, "Pocket Pool");
		t2.setWidget(5, 1, chkPP);
		
		t.setWidget(10, 1, t2);
		
		ta.setCharacterWidth(50);
		ta.setVisibleLines(5);
		
		t.setText(11, 0, "This is how I describe myself");
		t.setWidget(11, 1, ta);
		
		t3.setText(0,0, "Favourite Band");
		t3.setWidget(0, 1, tBb);
		
		t3.setText(1,0, "Favourite Movie");
		t3.setWidget(1, 1, tBm);
		
		FlexTable t5 = new FlexTable();
		
		t3.setText(2,0, "Favorite Era");
		
		t5.setText(0, 0, "Stone Age");
		t5.setWidget(0, 1, chkStoneAge);
		
		t5.setText(1, 0, "Ancient Times");
		t5.setWidget(1, 1, chkAncientTimes);
		
		t5.setText(2, 0, "Early Middle Ages");
		t5.setWidget(2, 1, chkEarlyMiddleAges);
		
		t5.setText(3, 0, "Late Middle Ages");
		t5.setWidget(3, 1, chkLateMiddleAges);
		
		t5.setText(4, 0, "Renaissance");
		t5.setWidget(4, 1, chkRenaissance);
		
		t5.setText(5, 0, "IndusrialAge");
		t5.setWidget(5, 1, chkIndusrialAge);
		
		t5.setText(5, 0, "ModernAge");
		t5.setWidget(5, 1, chkModernAge);
		
		t3.setWidget(2, 1, t5);
		
		FlexTable t7 = new FlexTable();
		
		t3.setText(3, 0, "I associate myself with this subculture");	
		
		t7.setText(0, 0, "bringing creativity into a relationship");
		t7.setWidget(0, 1, chkBringing);
		
		t7.setText(1, 0, "enjoying the simple things");
		t7.setWidget(1, 1, chkEnjoying);
		
		t7.setText(2, 0, "being romantic");
		t7.setWidget(2, 1, chkBeing);
		
		t7.setText(3, 0, "solving conflicts quickly");
		t7.setWidget(3, 1, chkSolving);
		
		t7.setText(4, 0, "keeping calm in chaotic situations");
		t7.setWidget(4, 1, chkKeeping);
			
		t3.setWidget(3, 1, t7);
		
		verPanel.add(t);
		verPanel2.add(t3);
		
		horPanel.add(verPanel);
		horPanel.add(verPanel2);
		
		RootPanel.get("Details").add(horPanel);
		
		logger.info(currentUserProfile.toString());
		
		saveProfilButton.setStyleName("tngly-button");
		t.setWidget(12, 1, saveProfilButton);
		saveProfilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				final String symbol = tbfn.getText().toUpperCase().trim();
				 if (ClientsideSettings.getUserProfile() == null){
				 Window.alert("Please insert a First Name");
				 }
				 else{
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
//				final String symbol2 = tbbh.getText().toUpperCase().trim();
//				if (!symbol2.matches("^[0-9A-Z\\.]{1,10}$")) {
//				Window.alert("'" + symbol2 + "' is not a valid symbol.");
//				tbbh.selectAll();
//				return;
//				}
				


				Logger logger = ClientsideSettings.getLogger();
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

				temp.setMovie(tBm.getText());
				
				temp.setBand(tBb.getText());
				
				
				logger.info("Confession CHECK");

				if (currentUserProfile == null) {
					 adminService.createProfile(temp, new CreateCallback());
					logger.info("if-getAdministration wurde aufgerufen");
				} else {
					 adminService.editProfile(temp, updateUserProfileCallback());
							logger.info("if-getAdministration wurde aufgerufen");
				}

			
				Update update = new ProfileView();

				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);

				logger.info("Erfolgreicher Reswitch.");
				
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
				ClientsideSettings.getLogger().severe("Success GetCurrentUserProfileCallback: " + result.getClass().getSimpleName());
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
				
				float bhFormatted = (float) (Math.round(bh*100) / 100.0);
					
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
				
//				for (int x = 0; x<=t2.getRowCount(); x++){
//					for (Information i : result.getSelectionList().get(0).getInformationValues()){
//						if (t2.getText(x, x-x) == i.getValue()){
//							CheckBox cb = new CheckBox();
//							cb.setValue(true);
//							t2.setWidget(x, x-2, cb);
//						}
//					}
//				}
				
				ta.setText(result.getDescriptionList().get(0).getInformationValues().get(0).getValue());
				logger.info(result.getDescriptionList().toString());
				tBb.setText(result.getDescriptionList().get(1).getInformationValues().get(0).getValue());
				tBm.setText(result.getDescriptionList().get(2).getInformationValues().get(0).getValue());
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
			}
		};
		return asyncCallback;
	}

}

class CreateCallback implements AsyncCallback<Profile> {
	@Override
	public void onFailure(Throwable caught) {
		ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
	}

	@Override
	public void onSuccess(Profile result) {
		ClientsideSettings.setUserProfile(result);

	}

}
