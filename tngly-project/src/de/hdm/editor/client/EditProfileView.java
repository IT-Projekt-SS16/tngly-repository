package de.hdm.editor.client;

import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
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
	
	private final CheckBox chkSoccer = new CheckBox();
	private final CheckBox chkBaseball = new CheckBox();
	private final CheckBox chkVolleyball = new CheckBox();
	private final CheckBox chkBasketball = new CheckBox();
	private final CheckBox chkGolf = new CheckBox();
	
	private final Button saveProfilButton = new Button("Save");

	/**
	 * Jeder Showcase besitzt eine einleitende Überschrift, die durch diese
	 * Methode zu erstellen ist.
	 * 
	 * @see Showcase#getHeadlineText()
	 */
	@Override
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
		
		eraBox.setVisibleItemCount(1);
		eraBox.addItem("Example1");
		eraBox.addItem("Example2");
		eraBox.addItem("Example3");
		eraBox.addItem("Example4");
		eraBox.addItem("Example5");
		eraBox.addItem("Example6");
		eraBox.addItem("Example7");
		eraBox.addItem("Example8");
		eraBox.addItem("Example9");
		eraBox.setPixelSize(130,25);
		
		subcultureBox.setVisibleItemCount(1);
		subcultureBox.addItem("Example1");
		subcultureBox.addItem("Example2");
		subcultureBox.addItem("Example3");
		subcultureBox.addItem("Example4");
		subcultureBox.addItem("Example5");
		subcultureBox.addItem("Example6");
		subcultureBox.addItem("Example7");
		subcultureBox.addItem("Example8");
		subcultureBox.addItem("Example9");
		subcultureBox.setPixelSize(130,25);

		datePicker.setYearArrowsVisible(true);
		datePicker.setYearAndMonthDropdownVisible(false);
		// show 51 years in the years dropdown. The range of years is centered on the selected date
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
		
		t2.setText(0, 0, "Soccer");
		t2.setWidget(0, 1, chkSoccer);
		
		t2.setText(1, 0, "Baseball");
		t2.setWidget(1, 1, chkBaseball);
		
		t2.setText(2, 0, "Volleyball");
		t2.setWidget(2, 1, chkVolleyball);
		
		t2.setText(3, 0, "Basketball");
		t2.setWidget(3, 1, chkBasketball);
		
		t2.setText(4, 0, "Golf");
		t2.setWidget(4, 1, chkGolf);
		
		t.setWidget(10, 1, t2);
		
		ta.setCharacterWidth(50);
		ta.setVisibleLines(5);
		
		t.setText(11, 0, "This is how I describe myself");
		t.setWidget(11, 1, ta);
		
		t3.setText(0,0, "Favourite Band");
		t3.setWidget(0, 1, tBb);
		
		t3.setText(1,0, "Favourite Movie");
		t3.setWidget(1, 1, tBm);
		
		
		t3.setText(2, 0, "Favorite era");
		if (ClientsideSettings.getUserProfile() == null) {
		t3.setWidget(2, 1, eraBox);
		} else {
			int index;
			if (ClientsideSettings.getUserProfile().getConfession() == "Stone Age") {
				index = 0;
			} else if (ClientsideSettings.getUserProfile().getConfession() == "Ancient Times") {
				index = 1;
			} else if (ClientsideSettings.getUserProfile().getConfession() == "Early Middle Ages") {
				index = 2;
			} else if (ClientsideSettings.getUserProfile().getConfession() == "Late Middle Ages") {
				index = 3;
			} else if (ClientsideSettings.getUserProfile().getConfession() == "Renaissance") {
				index = 4;
			} else if (ClientsideSettings.getUserProfile().getConfession() == "Industrial Age") {
				index = 5;
			} else if (ClientsideSettings.getUserProfile().getConfession() == "Modern Age") {
				index = 6;
			}
			//eraBox.setItemSelected(index, true);
			t3.setWidget(2, 1, eraBox);
		}
		
		
		t3.setText(3, 0, "I associate myself with this subculture");	
		if (ClientsideSettings.getUserProfile() == null) {
			t3.setWidget(3, 1, subcultureBox);
			} else {
				int index;
				if (ClientsideSettings.getUserProfile().getConfession() == "Example1") {
					index = 0;
				} else if (ClientsideSettings.getUserProfile().getConfession() == "Example1") {
					index = 1;
				} else if (ClientsideSettings.getUserProfile().getConfession() == "Example1") {
					index = 2;
				} else if (ClientsideSettings.getUserProfile().getConfession() == "Example1") {
					index = 3;
				} else if (ClientsideSettings.getUserProfile().getConfession() == "Example1") {
					index = 4;
				} else if (ClientsideSettings.getUserProfile().getConfession() == "Example1") {
					index = 5;
				} else if (ClientsideSettings.getUserProfile().getConfession() == "Example1") {
					index = 6;
				} else if (ClientsideSettings.getUserProfile().getConfession() == "Example1") {
					index = 7;
				}
				//subcultureBox.setItemSelected(index, true);
				t3.setWidget(3, 1, subcultureBox);
			}
		
		
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
				datePicker.setValue(result.getDateOfBirth());
				tbbh.setText(Float.toString(result.getBodyHeight()));
				
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
