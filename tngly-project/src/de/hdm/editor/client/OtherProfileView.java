package de.hdm.editor.client;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.eclipse.jetty.util.log.Log;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
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
import de.hdm.core.shared.bo.Description;
import de.hdm.core.shared.bo.Information;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.ProfileBan;
import de.hdm.core.shared.bo.Wish;

public class OtherProfileView extends Update {

	@Override
	protected String getHeadlineText() {
		return null;
	}
	
	public OtherProfileView(Profile selectedProfile, String originView, Profile cUP) {
		this.selectedProfile = selectedProfile;
		this.originView = originView;
		this.currentUserProfile = cUP;
		
		logger.info("OPV-45");
		logger.info("selectedProfile.getId(); " + selectedProfile.getId());
		logger.info("OPV-43");
		logger.info("currentUserProfile.getId(); " + currentUserProfile.getId());
	}
	
	public OtherProfileView(Profile selectedProfile, String originView) {
		this.selectedProfile = selectedProfile;
		this.originView = originView;
	}
	
	private static final Logger logger = ClientsideSettings.getLogger();
	private AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();
	private Profile selectedProfile;
	private Profile currentUserProfile;
	
	private boolean isWished = false;
	private boolean isBanned = false;
	
	private VerticalPanel verPanel = new VerticalPanel();
	private VerticalPanel verPanel2 = new VerticalPanel();
	
	private HorizontalPanel horPanel = new HorizontalPanel();
	private HorizontalPanel horPanelButtons = new HorizontalPanel();
	private HorizontalPanel horPanelLine = new HorizontalPanel();
	
	HTML horLine = new HTML("<hr  style=\"width:100%;\" />");
	HTML verLine = new HTML("  <table style='display:inline;border-collapse:collapse;border:0'><tr><td style='padding:0'><img src='transparent.gif' width='1' height='500' style='background:grey'></td></tr></table>"); 
	
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
	
	private String originView;
	
	private final Button atfProfilButton = new Button("Add to Favorites");
	private final Button dffProfilButton = new Button("Delete from Favorites");
	private final Button backButton = new Button("Back");
	private final Button banProfilButton = new Button("Ban Profile");
	private final Button unbanProfilButton = new Button("Unban Profile");



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
	
		atfProfilButton.setStylePrimaryName("tngly-opvbutton");
		dffProfilButton.setStylePrimaryName("tngly-opvbutton");
		backButton.setStylePrimaryName("tngly-backbutton");
		banProfilButton.setStylePrimaryName("tngly-opvbutton");
		unbanProfilButton.setStylePrimaryName("tngly-opvbutton");

		
		if (selectedProfile.getIsFavorite() == true)	{
			atfProfilButton.setStylePrimaryName("tngly-disabledButton");
			atfProfilButton.setEnabled(false);
			
			banProfilButton.setStylePrimaryName("tngly-disabledButton");
			banProfilButton.setEnabled(false);
			
			unbanProfilButton.setStylePrimaryName("tngly-disabledButton");
			unbanProfilButton.setEnabled(false);
			
			isWished = true;
		}
		
		if (selectedProfile.getIsBanned() == true)	{
			atfProfilButton.setStylePrimaryName("tngly-disabledButton");
			atfProfilButton.setEnabled(false);
			
			dffProfilButton.setStylePrimaryName("tngly-disabledButton");
			dffProfilButton.setEnabled(false);
			
			banProfilButton.setStylePrimaryName("tngly-disabledButton");
			banProfilButton.setEnabled(false);
			
			isBanned = true;
		}
		
		if (selectedProfile.getIsBanned() == false && selectedProfile.getIsFavorite() == false)	{
			
			dffProfilButton.setStylePrimaryName("tngly-disabledButton");
			dffProfilButton.setEnabled(false);
			
			unbanProfilButton.setStylePrimaryName("tngly-disabledButton");
			unbanProfilButton.setEnabled(false);
			
		}
		
		
       // adminService.checkBanAndWish(currentUserProfile, selectedProfile, chkBanAndWishCallback());
	   // adminService.isProfileWished(currentUserProfile, selectedProfile, isProfileWishedCallback());
	   // adminService.isProfileBanned(currentUserProfile, selectedProfile, isProfileBannedCallback());
		
		tbun.setPixelSize(120, 15);
		tbfn.setPixelSize(120, 15);
		tbn.setPixelSize(120, 15);
		tbbh.setPixelSize(120, 15);
		tDob.setPixelSize(120, 15);
		ta.setWidth("230px");
		
		
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
		
		logger.info("Zeile 204 ausgeführt");
		
		RootPanel.get("Details").add(verPanel);
		
		backButton.addClickHandler(new ClickHandler() {
			@Override

			public void onClick(ClickEvent event) {
				switch(originView){
		        case "ShowProfilesCTView":
		        	Update updateProfilesCTView = new ShowProfilesCTView(ClientsideSettings.getSearchProfile());
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(updateProfilesCTView);
					logger.info("Erfolgreich View geswitcht.");
		            break;
		        case "BanCTView":
		        	Update updateBanCTView = new BanCTView();
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(updateBanCTView);
					logger.info("Erfolgreich View geswitcht.");
		            break;
		        case "WishlistCTView":
		        	Update updateWishlistCTView = new WishlistCTView();
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(updateWishlistCTView);
					logger.info("Erfolgreich View geswitcht.");
		            break;
		        default:
		           
		        } 
			}
		});
		
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
		tDob.setEnabled(false);
		tDob.setText(selectedProfile.getDateOfBirth().toString());
			t.setWidget(5,1,tDob);
		
			
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

		
		logger.info("Zeile 416 ausgeführt");

		/*
		 * Markierung der Checkboxen f�r die Eigenschaft "My Hobbies"
		 */
		for (int x = 0; x < t2.getRowCount(); x++) {
			ArrayList<String> values = new ArrayList<String>();
			String tempValue;
			logger.info("Zeile 424 ausgeführt");

			CheckBox cb = (CheckBox) t2.getWidget(x, 0);
			for (Information i : selectedProfile.getSelectionList().get(0).getInformationValues()) {
				tempValue = i.getValue();
				values.add(tempValue);
				logger.info("Zeile 428 ausgeführt");

			}
			logger.info("Zeile 433 ausgeführt");
			tempValue = cb.getText();
			logger.info("Zeile 435 ausgeführt");

			if (values.contains(tempValue)) {
				logger.info("cbIsEnabled " + cb.isEnabled());
				cb.setValue(true);
			}
		}
		
		logger.info("Zeile 435 ausgeführt");

		
		if (selectedProfile.getDescriptionList().get(0).getInformationValues().size() > 0)	{
			logger.info("Profile-Description 0: " + selectedProfile.getDescriptionList().get(0).getInformationValues().get(0).getValue());

		ta.setText(selectedProfile.getDescriptionList().get(0).getInformationValues().get(0).getValue());
		} else{				logger.info("Profile-Description 0: null");
}
		
		
		if (selectedProfile.getDescriptionList().get(1).getInformationValues().size() > 0)	{
			
			logger.info("Profile-Description 1: " + selectedProfile.getDescriptionList().get(1).getInformationValues().get(0).getValue());
		
		tBb.setText(selectedProfile.getDescriptionList().get(1).getInformationValues().get(0).getValue());
		} else{logger.info("Profile-Description 1: null" );
		}
		

		if (selectedProfile.getDescriptionList().get(2).getInformationValues().size() > 0)	{
			logger.info("Profile-Description 2: " + selectedProfile.getDescriptionList().get(2).getInformationValues().get(0).getValue());

		tBm.setText(selectedProfile.getDescriptionList().get(2).getInformationValues().get(0).getValue());
		} else{logger.info("Profile-Description 2: null");}
		

		
		/*
		 * Markierung der Checkboxen f�r die Eigenschaft
		 * "My Strong Points"
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
			}
		}

		logger.info("Zeile 481 ausgeführt");

		
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
			}
		}
		
		logger.info("Zeile 520 ausgeführt");

		
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

		logger.info("Zeile 528 ausgeführt");

		
		RootPanel.get("Details").add(horPanelButtons);
		RootPanel.get("Details").add(horLine);
		RootPanel.get("Details").add(horPanel);
		
		logger.info("Zeile 535 ausgeführt");


		backButton.addClickHandler(new ClickHandler() {
			@Override

			public void onClick(ClickEvent event) {
				switch(originView){
		        case "ShowProfilesCellTableView":
		        	Update updateProfilesCTView = new ShowProfilesCTView(ClientsideSettings.getSearchProfile());
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(updateProfilesCTView);
					logger.info("Erfolgreich View geswitcht.");
		            break;
		        case "BanCTView":
		        	Update updateBanCTView = new BanCTView();
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(updateBanCTView);
					logger.info("Erfolgreich View geswitcht.");
		            break;
		        case "WishlistCTView":
		        	Update updateWishlistCTView = new WishlistCTView();
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(updateWishlistCTView);
					logger.info("Erfolgreich View geswitcht.");
		            break;
		        default:
		           
		        } 
			}
		});
		
		atfProfilButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				logger.info("onClick ausgeführt!");
				adminService.addWishToWishlist(selectedProfile.getId(),
						currentUserProfile.getId(), new CreateWishCallback());
			}
		});

		dffProfilButton.addClickHandler(new ClickHandler() {
			@Override

			public void onClick(ClickEvent event) {
				adminService.deleteWishFromWishlist(selectedProfile.getId(),
						currentUserProfile.getId(), new DeleteWishCallback());
			}
		});

		banProfilButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				adminService.createProfileBan(selectedProfile.getId(),
						currentUserProfile.getId(), new CreateProfileBanCallback());	
			}
		});

		unbanProfilButton.addClickHandler(new ClickHandler() {
			@Override

			public void onClick(ClickEvent event) {
				adminService.deleteProfileBan(currentUserProfile.getId(),
						selectedProfile.getId(), new DeleteBanCallback());
			}
		});
	}

	class DeleteWishCallback implements AsyncCallback<Void> {
	@Override
	public void onFailure(Throwable caught) {
		ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
	}

	@Override
	public void onSuccess(Void result) {
		selectedProfile.setIsFavorite(false);
		Update update = new OtherProfileView(selectedProfile, originView, currentUserProfile);
		RootPanel.get("Details").clear();
		RootPanel.get("Details").add(update);
		logger.info("Erfolgreich View geswitcht.");
	}

}
    
    class DeleteBanCallback implements AsyncCallback<Void> {
    	@Override
    	public void onFailure(Throwable caught) {
    		ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
    	}

    	@Override
    	public void onSuccess(Void result) {
    		selectedProfile.setIsBanned(false);
    		Update update = new OtherProfileView(selectedProfile, originView, currentUserProfile);
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(update);
			logger.info("Erfolgreich View geswitcht.");
    	}

    }
    
class CreateProfileBanCallback implements AsyncCallback<ProfileBan> {
	@Override
	public void onFailure(Throwable caught) {
		ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
	}

	@Override
	public void onSuccess(ProfileBan pb) {
		selectedProfile.setIsBanned(true);
		Update update = new OtherProfileView(selectedProfile, originView, currentUserProfile);
		RootPanel.get("Details").clear();
		RootPanel.get("Details").add(update);
		logger.info("Erfolgreich View geswitcht.");
	}

}

class CreateWishCallback implements AsyncCallback<Wish> {
	@Override
	public void onFailure(Throwable caught) {
		ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
	}

	@Override
	public void onSuccess(Wish wish) {
		selectedProfile.setIsFavorite(true);
		Update update = new OtherProfileView(selectedProfile, originView, currentUserProfile);
		RootPanel.get("Details").clear();
		RootPanel.get("Details").add(update);
		logger.info("Erfolgreich View geswitcht.");

	}
}

	
	
	
}


