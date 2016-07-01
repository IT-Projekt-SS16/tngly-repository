package de.hdm.editor.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
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

public class ProfileView extends Update {
	
	private static final Logger logger = ClientsideSettings.getLogger();

	/**
	 * Instanziierung aller relevanten Eingabem�glichkeiten = Textboxen, Checkboxen, DatePicker usw. 
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
	private final TextBox tDob = new TextBox();

	private TextArea ta = new TextArea();

	private final ListBox hairColourList = new ListBox(false);
	private final ListBox isSmokingBox = new ListBox(false);
	private final ListBox confessionBox = new ListBox(false);
	private final ListBox genderBox = new ListBox(false);
	private final ListBox myHobbiesSelect = new ListBox(true);

	final ListBox subcultureBox = new ListBox(false);
	final ListBox eraBox = new ListBox(false);

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

	/**
	 * Jede View besitzt eine einleitende �berschrift, die durch diese
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


		t.setText(0, 0, "Username");
		tbun.setEnabled(false);
		t.setWidget(0, 1, tbun);

		t.setText(2, 0, "First Name");
		tbfn.setEnabled(false);
		t.setWidget(2, 1, tbfn);

		t.setText(3, 0, "Last Name");
		tbn.setEnabled(false);
		t.setWidget(3, 1, tbn);

		t.setText(4, 0, "Gender");
		genderBox.setEnabled(false);
		t.setWidget(4, 1, genderBox);

		t.setText(5, 0, "Date of Birth");
		tDob.setEnabled(false);
		t.setWidget(5, 1, tDob);

		t.setText(6, 0, "Body Height");
		tbbh.setEnabled(false);
		t.setWidget(6, 1, tbbh);

		t.setText(7, 0, "Haircolor");
		hairColourList.setEnabled(false);
		t.setWidget(7, 1, hairColourList);

		t.setText(8, 0, "Smoker");
		isSmokingBox.setEnabled(false);
		t.setWidget(8, 1, isSmokingBox);

		t.setText(9, 0, "Confession");
		confessionBox.setEnabled(false);
		t.setWidget(9, 1, confessionBox);

		t.setText(10, 0, "Hobbies");
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

		t3.setText(2, 0, "My Strong Points");
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
		t3.setWidget(2, 1, t7);

		t3.setText(3, 0, "I associate myself with this subculture");
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
		t3.setWidget(3, 1, t6);
		
		
		verPanel.add(t);
		verPanel2.add(t3);
		
		horPanel.add(verPanel);
		horPanel.add(verPanel2);
		
		RootPanel.get("Details").add(horPanel);
		
		logger.info(currentUserProfile.toString());
				return;
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
//				if (result.getGender() == "Male") {
//					index = 1;
//				} else {
//					index = 0;
//				}
				switch(result.getGender()){
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

				tDob.setText(dob.toString());

				float bh = result.getBodyHeight();

				float bhFormatted = (float) (Math.round(bh * 100) / 100.0);

				tbbh.setText(Float.toString(bhFormatted));

//				if (result.getHairColour() == "Black") {
//					index = 0;
//				} else if (result.getHairColour() == "Brown") {
//					index = 1;
//				} else if (result.getHairColour() == "Red") {
//					index = 2;
//				} else if (result.getHairColour() == "Blonde") {
//					index = 3;
//				} else {
//					index = 4;
//				}
				switch(result.getHairColour()){
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
		        default:
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
				

				if (result.getDescriptionList().get(0).getInformationValues().size() > 0)	{
					logger.info("Profile-Description 0: " + result.getDescriptionList().get(0).getInformationValues().get(0).getValue());

				ta.setText(result.getDescriptionList().get(0).getInformationValues().get(0).getValue());
				} else{				logger.info("Profile-Description 0: null");
}
				
				
				if (result.getDescriptionList().get(1).getInformationValues().size() > 0)	{
					
					logger.info("Profile-Description 1: " + result.getDescriptionList().get(1).getInformationValues().get(0).getValue());
				
				tBb.setText(result.getDescriptionList().get(1).getInformationValues().get(0).getValue());
				} else{logger.info("Profile-Description 1: null" );
				}
				

				if (result.getDescriptionList().get(2).getInformationValues().size() > 0)	{
					logger.info("Profile-Description 2: " + result.getDescriptionList().get(2).getInformationValues().get(0).getValue());

				tBm.setText(result.getDescriptionList().get(2).getInformationValues().get(0).getValue());
				} else{logger.info("Profile-Description 2: null");}
				

				
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
				 * Markierung der Checkboxen f�r die Eigenschaft "Favorite Era"
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
	

}

