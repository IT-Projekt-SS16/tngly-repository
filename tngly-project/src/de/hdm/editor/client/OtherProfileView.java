package de.hdm.editor.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.shared.bo.Profile;

public class OtherProfileView extends Update {

	private Profile selectedProfile;

	public OtherProfileView(Profile selectedProfile) {
		this.selectedProfile = selectedProfile;
	}

	/**
	 * Jeder Showcase besitzt eine einleitende Ãœberschrift, die durch diese
	 * Methode zu erstellen ist.
	 * 
	 * @see Showcase#getHeadlineText()
	 */
	@Override
	protected String getHeadlineText() {
		return "Other Profile View";
	}

	@Override
	protected void run() {

		VerticalPanel verPanel = new VerticalPanel();

		RootPanel.get("Details").add(verPanel);

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

		Label firstName = new Label("First Name:");
		verPanel.add(firstName);
		Label firstNameValue = new Label(this.selectedProfile.getName());
		verPanel.add(firstNameValue);

		Label name = new Label("Last Name:");
		verPanel.add(name);
		Label nameValue = new Label(this.selectedProfile.getLastName());
		verPanel.add(nameValue);

		Label gender = new Label("Gender:");
		verPanel.add(gender);
		Label genderValue = new Label(this.selectedProfile.getGender());
		verPanel.add(genderValue);

		Label dateOfBirth = new Label("Date of Birth:");
		verPanel.add(dateOfBirth);
		Label dOBValue = new Label(this.selectedProfile.getDateOfBirth().toString());
		verPanel.add(dOBValue);

		Label bodyHeight = new Label("Body Height:");
		verPanel.add(bodyHeight);
		Label bodyHeightValue = new Label(Float.toString(this.selectedProfile.getBodyHeight()));
		verPanel.add(bodyHeightValue);

		Label hairColour = new Label("Haircolour:");
		verPanel.add(hairColour);
		Label hairColourValue = new Label(this.selectedProfile.getHairColour());
		verPanel.add(hairColourValue);

		Label isSmoking = new Label("Smoker:");
		verPanel.add(isSmoking);
		Label smokingValue;
		if (this.selectedProfile.getIsSmoking() == 0) {
			smokingValue = new Label("Yes");
		} else {
			smokingValue = new Label("No");
		}
		verPanel.add(smokingValue);

		Label confession = new Label("Confession:");
		verPanel.add(confession);
		Label confessionValue = new Label(this.selectedProfile.getConfession());
		verPanel.add(confessionValue);

		final Button atfProfilButton = new Button("Add to Favorites");
		atfProfilButton.setStylePrimaryName("tngly-menubutton");
		verPanel.add(atfProfilButton);

		// atfProfilButton.addClickHandler(new ClickHandler() {
		// public void onClick(ClickEvent event) {
		// Update update = new EditProfileView();
		//
		// RootPanel.get("Details").clear();
		// RootPanel.get("Details").add(update);
		//
		// Logger logger = ClientsideSettings.getLogger();
		// logger.info("Erfolgreich View geswitcht.");
		// }
		// });

		final Button dffProfilButton = new Button("Delete from Favorites");
		dffProfilButton.setStylePrimaryName("tngly-menubutton");
		verPanel.add(dffProfilButton);

		// atfProfilButton.addClickHandler(new ClickHandler() {
		// public void onClick(ClickEvent event) {
		// Update update = new EditProfileView();
		//
		// RootPanel.get("Details").clear();
		// RootPanel.get("Details").add(update);
		//
		// Logger logger = ClientsideSettings.getLogger();
		// logger.info("Erfolgreich View geswitcht.");
		// }
		// });

		final Button banProfilButton = new Button("Ban Profile");
		banProfilButton.setStylePrimaryName("tngly-menubutton");
		verPanel.add(banProfilButton);

		// banProfilButton.addClickHandler(new ClickHandler() {
		// public void onClick(ClickEvent event) {
		// ClientsideSettings.getAdministration().deleteProfile(ClientsideSettings.getUserProfile(),
		// new DeleteCallback());
		// ClientsideSettings.setUserProfile(null);
		// Window.open(ClientsideSettings.getLoginInfo().getLogoutUrl(),
		// "_self", "");
		//
		// Logger logger = ClientsideSettings.getLogger();
		// logger.info("Erfolgreich Profil gelöscht.");
		// }
		// });

		final Button unbanProfilButton = new Button("Unban Profile");
		unbanProfilButton.setStylePrimaryName("tngly-menubutton");
		verPanel.add(unbanProfilButton);

		// banProfilButton.addClickHandler(new ClickHandler() {
		// public void onClick(ClickEvent event) {
		// ClientsideSettings.getAdministration().deleteProfile(ClientsideSettings.getUserProfile(),
		// new DeleteCallback());
		// ClientsideSettings.setUserProfile(null);
		// Window.open(ClientsideSettings.getLoginInfo().getLogoutUrl(),
		// "_self", "");
		//
		// Logger logger = ClientsideSettings.getLogger();
		// logger.info("Erfolgreich Profil gelöscht.");
		// }
		// });

	}
}
