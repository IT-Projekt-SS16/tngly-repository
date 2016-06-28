package de.hdm.editor.client;

import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.shared.AdministrationServiceAsync;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.ProfileBan;
import de.hdm.core.shared.bo.Wish;

public class OtherProfileView extends Update {

	private static final Logger logger = ClientsideSettings.getLogger();
	private AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();
	private Profile selectedProfile;
	private String originView;

	private final VerticalPanel verPanel = new VerticalPanel();

	private Label userName = new Label("Username");
	private Label userNameValue = new Label();
	private Label firstName = new Label("First Name");
	private Label firstNameValue = new Label();
	private Label name = new Label("Last Name");
	private Label nameValue = new Label();
	private Label gender = new Label("Gender");
	private Label genderValue = new Label();
	private Label dateOfBirth = new Label("Date of Birth");
	private Label dOBValue = new Label();
	private Label bodyHeight = new Label("Body Height");
	private Label bodyHeightValue = new Label();
	private Label hairColour = new Label("Haircolour");
	private Label hairColourValue = new Label();
	private Label isSmoking = new Label("Smoker");
	private Label smokingValue = new Label();
	private Label confession = new Label("Confession");
	private Label confessionValue = new Label();
	
	private Label hobbies = new Label("Hobbies");
	private Label selfDescription = new Label("This is how I describe myself");
	private Label favoriteBand = new Label("Favorite Band");
	private Label favoriteMovie = new Label("Favorite Movie");
	private Label strongPoints = new Label("My Strong Points");
	private Label subCulture = new Label("I associate myself with this subculture");
	private Label favoriteEra = new Label("Favorite Era");
	
	private FlexTable t2 = new FlexTable();
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
	
	private final Button atfProfilButton = new Button("Add to Favorites");
	private final Button dffProfilButton = new Button("Delete from Favorites");
	private final Button backButton = new Button("Back");
	private final Button banProfilButton = new Button("Ban Profile");
	private final Button unbanProfilButton = new Button("Unban Profile");

	public OtherProfileView(Profile selectedProfile, String originView) {
		this.selectedProfile = selectedProfile;
		this.originView = originView;
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
		
		userNameValue.setText(this.selectedProfile.getUserName());
		firstNameValue.setText(this.selectedProfile.getName());
		nameValue.setText(this.selectedProfile.getLastName());
		genderValue.setText(this.selectedProfile.getGender());
		dOBValue.setText(this.selectedProfile.getDateOfBirth().toString());
		bodyHeightValue.setText(Float.toString(this.selectedProfile.getBodyHeight()));
		hairColourValue.setText(this.selectedProfile.getHairColour());
		
		if (this.selectedProfile.getIsSmoking() == 0) {
			smokingValue.setText("Yes");
		} else {
			smokingValue.setText("No");
		}
		
		confessionValue.setText(this.selectedProfile.getConfession());
		
		atfProfilButton.setStylePrimaryName("tngly-menubutton");
		dffProfilButton.setStylePrimaryName("tngly-menubutton");
		backButton.setStylePrimaryName("tngly-menubutton");
		banProfilButton.setStylePrimaryName("tngly-menubutton");
		unbanProfilButton.setStylePrimaryName("tngly-menubutton");
		
		verPanel.add(backButton);
		verPanel.add(userName);
		verPanel.add(userNameValue);
		verPanel.add(firstName);
		verPanel.add(firstNameValue);
		verPanel.add(name);
		verPanel.add(nameValue);
		verPanel.add(gender);
		verPanel.add(genderValue);
		verPanel.add(dateOfBirth);
		verPanel.add(dOBValue);
		verPanel.add(bodyHeight);
		verPanel.add(bodyHeightValue);
		verPanel.add(hairColour);
		verPanel.add(hairColourValue);
		verPanel.add(isSmoking);
		verPanel.add(smokingValue);
		verPanel.add(confession);
		verPanel.add(confessionValue);
		verPanel.add(atfProfilButton);
		verPanel.add(dffProfilButton);
		verPanel.add(banProfilButton);
		verPanel.add(unbanProfilButton);
		
		RootPanel.get("Details").add(verPanel);

		backButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				switch(originView){
		        case "ShowProfilesCellTableView":
		        	Update updateProfilesCTView = new ShowProfilesCellTableView(ClientsideSettings.getSearchProfile());
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
			public void onClick(ClickEvent event) {
				ClientsideSettings.getAdministration().addWishToWishlist(selectedProfile.getId(),
						ClientsideSettings.getUserProfile().getId(), new CreateWishCallback());
				Update update = new EditProfileView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
				logger.info("Erfolgreich View geswitcht.");
			}
		});

		dffProfilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ClientsideSettings.getAdministration().deleteWishFromWishlist(selectedProfile.getId(),
						ClientsideSettings.getUserProfile().getId(), new DeleteCallback());
				Update update = new EditProfileView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
				logger.info("Erfolgreich View geswitcht.");
			}
		});

		banProfilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ClientsideSettings.getAdministration().createProfileBan(selectedProfile.getId(),
						ClientsideSettings.getUserProfile().getId(), new CreateProfileBanCallback());
				Window.open(ClientsideSettings.getLoginInfo().getLogoutUrl(), "_self", "");
				logger.info("Erfolgreich Profil gel�scht.");
			}
		});

		unbanProfilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ClientsideSettings.getAdministration().deleteProfileBan(selectedProfile.getId(),
						ClientsideSettings.getUserProfile().getId(), new DeleteCallback());
				Window.open(ClientsideSettings.getLoginInfo().getLogoutUrl(), "_self", "");
				logger.info("Erfolgreich Profil gel�scht.");
			}
		});

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