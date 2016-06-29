package de.hdm.editor.client;

import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.shared.bo.Description;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.ProfileBan;
import de.hdm.core.shared.bo.Wish;

public class OtherProfileView extends Update {

	private Profile selectedProfile;
	
	private VerticalPanel verPanel = new VerticalPanel();
	private VerticalPanel verPanel2 = new VerticalPanel();
	
	private HorizontalPanel horPanel = new HorizontalPanel();
	private HorizontalPanel horPanelButtons = new HorizontalPanel();
	private HorizontalPanel horPanelLine = new HorizontalPanel();
	
	HTML horLine = new HTML("<hr  style=\"width:100%;\" />");
	
	private final TextBox tbun = new TextBox();
	private final TextBox tbfn = new TextBox();
	private final TextBox tbn = new TextBox();
	private final TextBox tdob = new TextBox();
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

	public OtherProfileView(Profile selectedProfile) {
		this.selectedProfile = selectedProfile;
	}

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

	@Override
	protected void run() {

		verPanel.setSpacing(10);
		verPanel2.setSpacing(10);
		
		horPanelButtons.setHorizontalAlignment(ALIGN_CENTER);
		horPanelButtons.setVerticalAlignment(ALIGN_TOP);
		horPanelButtons.setStylePrimaryName("tngly-buttonPanel");
		horPanelButtons.setWidth("100%");
		
		horPanelLine.setStylePrimaryName("tngly-linePanel");
		horPanelLine.setHeight("5px");
		horPanelLine.setWidth("100%");

		tbun.setPixelSize(120, 15);
		tbfn.setPixelSize(120, 15);
		tbn.setPixelSize(120, 15);
		tbbh.setPixelSize(120, 15);
		tdob.setPixelSize(120, 15);
		
		
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

		// Label userName = new Label("Username:");
		// verPanel.add(userName);
		// Label userNameValue;
		// if (ClientsideSettings.getUserProfile().getUserName() == null){
		// userNameValue = new Label("");
		// } else {
		// userNameValue = new
		// Label(ClientsideSettings.getUserProfile().getUserName());
		// }
		// verPanel.add(userNameValue);
		
		t.setText(0, 0, "Username");
		tbun.setEnabled(false);
		tbun.setText(selectedProfile.getUserName());
			t.setWidget(0, 1, tbun);
		
		t.setText(2,0,"First Name");
		tbfn.setEnabled(false);
		tbfn.setText(selectedProfile.getName());
			t.setWidget(2,1,tbfn);
		
		t.setText(3, 0, "Last Name");
		tbn.setEnabled(false);
		tbn.setText(selectedProfile.getLastName());
			t.setWidget(3,1,tbn);
		
			int index;
			if (selectedProfile.getGender() == "Male") {
				index = 1;} else {index = 0;}
			genderBox.setItemSelected(index, true);

		t.setText(4, 0, "Gender");
		genderBox.setEnabled(false);
		genderBox.setItemSelected(index, true);
			t.setWidget(4, 1, genderBox);  
		
		t.setText(5, 0, "Date of Birth");
		tdob.setEnabled(false);
		tdob.setText(selectedProfile.getDateOfBirth().toString());
			t.setWidget(5,1,tdob);
		
			float bh = selectedProfile.getBodyHeight();
			float bhFormatted = (float) (Math.round(bh*100) / 100.0);

		t.setText(6,0, "Body Height");
		tbbh.setEnabled(false);
		tbbh.setText(Float.toString(bhFormatted));
			t.setWidget(6,1,tbbh);

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
			t.setWidget(7,1,hairColourList);

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
		
		t.setText(10,0, "Hobbies");
		
		FlexTable t2 = new FlexTable();
		
		t2.setText(0, 0, "Volleyball");
		chkVolleyball.setEnabled(false);
		t2.setWidget(0, 1, chkVolleyball);
		
		t2.setText(1, 0, "Football");
		chkFootball.setEnabled(false);
		t2.setWidget(1, 1, chkFootball);
		
		t2.setText(2, 0, "Watch People");
		chkWatchPeople.setEnabled(false);
		t2.setWidget(2, 1, chkWatchPeople);
		
		t2.setText(3, 0, "Not working at the IT-Project");
		chkIT.setEnabled(false);
		t2.setWidget(3, 1, chkIT);
		
		t2.setText(4, 0, "Handball");
		chkHandball.setEnabled(false);
		t2.setWidget(4, 1, chkHandball);
		
		t2.setText(5, 0, "Pocket Pool");
		chkPP.setEnabled(false);
		t2.setWidget(5, 1, chkPP);
		
		t.setWidget(10, 1, t2);
		
		ta.setCharacterWidth(50);
		ta.setVisibleLines(5);
		
		t.setText(11, 0, "This is how I describe myself");
		ta.setEnabled(false);
		t.setWidget(11, 1, ta);
		
		for (Description d : selectedProfile.getDescriptionList())	{
			System.out.println("Description: " + d.getTextualDescription());
			if (d.getTextualDescription() == "My hobbies")	{
				System.out.println("Value: " + d.getInformationValues().get(0).getValue());
				// ta.setText(d.getInformationValues().get(0).getValue());
			} else {}
		} 
		
		
		t3.setText(0,0, "Favorite Band");
		tBb.setEnabled(false);
		t3.setWidget(0, 1, tBb);
		
		/**for (Description d : selectedProfile.getDescriptionList())	{
			if (d.getTextualDescription() == "Favorite band")	{
				ta.setText(d.getInformationValues().get(0).getValue());
			} else {}
		}*/
		
		t3.setText(1,0, "Favorite Movie");
		tBm.setEnabled(false);
		t3.setWidget(1, 1, tBm);
		
		/**for (Description d : selectedProfile.getDescriptionList())	{
			if (d.getTextualDescription() == "Favorite movies")	{
				ta.setText(d.getInformationValues().get(0).getValue());
			} else {}
		}
		*/
		
		FlexTable t5 = new FlexTable();
		
		t3.setText(4,0, "Favorite Era");
		
		t5.setText(0, 0, "Stone Age");
		chkStoneAge.setEnabled(false);
		t5.setWidget(0, 1, chkStoneAge);
		
		t5.setText(1, 0, "Ancient Times");
		chkAncientTimes.setEnabled(false);
		t5.setWidget(1, 1, chkAncientTimes);
		
		t5.setText(2, 0, "Early Middle Ages");
		chkEarlyMiddleAges.setEnabled(false);
		t5.setWidget(2, 1, chkEarlyMiddleAges);
		
		t5.setText(3, 0, "Late Middle Ages");
		chkLateMiddleAges.setEnabled(false);
		t5.setWidget(3, 1, chkLateMiddleAges);
		
		t5.setText(4, 0, "Renaissance");
		chkRenaissance.setEnabled(false);
		t5.setWidget(4, 1, chkRenaissance);
		
		t5.setText(5, 0, "IndustrialAge");
		chkIndusrialAge.setEnabled(false);
		t5.setWidget(5, 1, chkIndusrialAge);
		
		t5.setText(5, 0, "ModernAge");
		chkModernAge.setEnabled(false);
		t5.setWidget(5, 1, chkModernAge);
		
		t3.setWidget(4, 1, t5);
		
		FlexTable t7 = new FlexTable();
		
		t3.setText(3, 0, "I associate myself with this subculture");	
		
		t7.setText(0, 0, "bringing creativity into a relationship");
		chkBringing.setEnabled(false);
		t7.setWidget(0, 1, chkBringing);
		
		t7.setText(1, 0, "enjoying the simple things");
		chkEnjoying.setEnabled(false);
		t7.setWidget(1, 1, chkEnjoying);
		
		t7.setText(2, 0, "being romantic");
		chkBeing.setEnabled(false);
		t7.setWidget(2, 1, chkBeing);
		
		t7.setText(3, 0, "solving conflicts quickly");
		chkSolving.setEnabled(false);
		t7.setWidget(3, 1, chkSolving);
		
		t7.setText(4, 0, "keeping calm in chaotic situations");
		chkKeeping.setEnabled(false);
		t7.setWidget(4, 1, chkKeeping);
			
		t3.setWidget(3, 1, t7);
		
		t3.setText(2, 0, "strong points");
		tbsp.setEnabled(false);
		t3.setWidget(2, 1, tbsp);
			
		verPanel.add(t);
		verPanel2.add(t3);
		
		horPanel.add(verPanel);
		horPanel.add(verPanel2);
		
		RootPanel.get("Details").add(horPanelButtons);
		RootPanel.get("Details").add(horLine);
		RootPanel.get("Details").add(horPanel);

		final Button atfProfilButton = new Button("Add to Favorites");
		atfProfilButton.setStylePrimaryName("tngly-opvbutton");
		horPanelButtons.add(atfProfilButton);

		atfProfilButton.addClickHandler(new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
		ClientsideSettings.getAdministration().addWishToWishlist(selectedProfile.getId(), ClientsideSettings.getUserProfile().getId(), new CreateWishCallback());
		Update update = new EditProfileView();
		
		RootPanel.get("Details").clear();
		RootPanel.get("Details").add(update);
		
		Logger logger = ClientsideSettings.getLogger();
		logger.info("Erfolgreich View geswitcht.");
		}
		});

		final Button dffProfilButton = new Button("Delete from Favorites");
		dffProfilButton.setStylePrimaryName("tngly-opvbutton");
		horPanelButtons.add(dffProfilButton);

		atfProfilButton.addClickHandler(new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
		ClientsideSettings.getAdministration().deleteWishFromWishlist(selectedProfile.getId(), ClientsideSettings.getUserProfile().getId(), new DeleteCallback());
		Update update = new EditProfileView();
		
		final Button backButton = new Button("Back");
		backButton.setStylePrimaryName("tngly-menubutton");
		horPanelButtons.add(backButton);
	
		
		
		RootPanel.get("Details").clear();
		RootPanel.get("Details").add(update);
		
		Logger logger = ClientsideSettings.getLogger();
		logger.info("Erfolgreich View geswitcht.");
		}
		});

		final Button banProfilButton = new Button("Ban Profile");
		banProfilButton.setStylePrimaryName("tngly-opvbutton");
		horPanelButtons.add(banProfilButton);

		banProfilButton.addClickHandler(new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
		ClientsideSettings.getAdministration().createProfileBan(selectedProfile.getId(), ClientsideSettings.getUserProfile().getId(), new CreateProfileBanCallback());
		
		Window.open(ClientsideSettings.getLoginInfo().getLogoutUrl(),
		"_self", "");
		
		Logger logger = ClientsideSettings.getLogger();
		logger.info("Erfolgreich Profil gel�scht.");
		}
		});

		final Button unbanProfilButton = new Button("Unban Profile");
		unbanProfilButton.setStylePrimaryName("tngly-opvbutton");
		horPanelButtons.add(unbanProfilButton);

		banProfilButton.addClickHandler(new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
		ClientsideSettings.getAdministration().deleteProfileBan(selectedProfile.getId(), ClientsideSettings.getUserProfile().getId(),
		new DeleteCallback());
		ClientsideSettings.setUserProfile(null);
		Window.open(ClientsideSettings.getLoginInfo().getLogoutUrl(),
		"_self", "");
		
		Logger logger = ClientsideSettings.getLogger();
		logger.info("Erfolgreich ProfilBan erstellt.");
		}
		});

	}
}
    class DeleteCallback implements AsyncCallback<Void> {
	@Override
	public void onFailure(Throwable caught) {
		ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
	}

	@Override
	public void onSuccess(Void result) {
		// TODO Auto-generated method stub

	}

}
class CreateProfileBanCallback implements AsyncCallback<ProfileBan> {
	@Override
	public void onFailure(Throwable caught) {
		ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
	}

	@Override
	public void onSuccess(ProfileBan pb) {
		// TODO Auto-generated method stub

	}

}

class CreateWishCallback implements AsyncCallback<Wish> {
	@Override
	public void onFailure(Throwable caught) {
		ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
	}

	@Override
	public void onSuccess(Wish wish) {
		// TODO Auto-generated method stub

	}

}