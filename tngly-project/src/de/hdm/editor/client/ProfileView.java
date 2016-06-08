package de.hdm.editor.client;

import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.core.client.ClientsideSettings;

public class ProfileView extends Update {

	/**
	 * Jeder Showcase besitzt eine einleitende Ãœberschrift, die durch diese
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

		VerticalPanel verPanel = new VerticalPanel();

		RootPanel.get("Details").add(verPanel);
		
		FlexTable profile = new FlexTable();
		
		verPanel.add(profile);

//		Label userName = new Label("Username:");
//		verPanel.add(userName);
//		Label userNameValue;
//		if (ClientsideSettings.getUserProfile().getUserName() == null){
//			userNameValue = new Label("");
//		} else {
//			userNameValue = new Label(ClientsideSettings.getUserProfile().getUserName());
//		}
//		verPanel.add(userNameValue);

		Label firstName = new Label("First Name:");
		profile.setWidget(0, 0, firstName);
//		verPanel.add(firstName);
		Label firstNameValue = new Label(ClientsideSettings.getUserProfile().getName());
		profile.setWidget(0, 1, firstNameValue);
//		verPanel.add(firstNameValue);

		Label name = new Label("Last Name:");
		profile.setWidget(1, 0, name);
//		verPanel.add(name);
		Label nameValue = new Label(ClientsideSettings.getUserProfile().getLastName());
		profile.setWidget(1, 1, nameValue);
//		verPanel.add(nameValue);

		Label gender = new Label("Gender:");
		profile.setWidget(2, 0, gender);
//		verPanel.add(gender);
		Label genderValue = new Label(ClientsideSettings.getUserProfile().getGender());
		profile.setWidget(2, 1, genderValue);
//		verPanel.add(genderValue);

		Label dateOfBirth = new Label("Date of Birth:");
		profile.setWidget(3, 0, dateOfBirth);
//		verPanel.add(dateOfBirth);
		Label dOBValue = new Label(ClientsideSettings.getUserProfile().getDateOfBirth().toString());
		profile.setWidget(3, 1, dOBValue);
//		verPanel.add(dOBValue);

		Label bodyHeight = new Label("Body Height:");
		profile.setWidget(4, 0, bodyHeight);
//		verPanel.add(bodyHeight);
		Label bodyHeightValue = new Label(Float.toString(ClientsideSettings.getUserProfile().getBodyHeight()));
		profile.setWidget(4, 1, bodyHeightValue);
//		verPanel.add(bodyHeightValue);

		Label hairColour = new Label("Haircolour:");
		profile.setWidget(5, 0, hairColour);
//		verPanel.add(hairColour);
		Label hairColourValue = new Label(ClientsideSettings.getUserProfile().getHairColour());
		profile.setWidget(5, 1, hairColourValue);
//		verPanel.add(hairColourValue);

		Label isSmoking = new Label("Smoker:");
		profile.setWidget(6, 0, isSmoking);
//		verPanel.add(isSmoking);
		Label smokingValue;
		if (ClientsideSettings.getUserProfile().getIsSmoking() == 0){
			smokingValue = new Label("Yes");
		} else {
			smokingValue = new Label("No");
		}
		profile.setWidget(6, 1, smokingValue);
//		verPanel.add(smokingValue);

		Label confession = new Label("Confession:");
		profile.setWidget(7, 0, confession);
//		verPanel.add(confession);
		Label confessionValue = new Label(ClientsideSettings.getUserProfile().getConfession());
		profile.setWidget(7, 1, confessionValue);
//		verPanel.add(confessionValue);

		final Button editProfilButton = new Button("Edit Profile");
		editProfilButton.setStylePrimaryName("tngly-button");
		verPanel.add(editProfilButton);

		editProfilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Update update = new EditProfileView();

				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);

				Logger logger = ClientsideSettings.getLogger();
				logger.info("Erfolgreich View geswitcht.");
			}
		});
		
		final Button deleteProfilButton = new Button("Delete Profile");
		deleteProfilButton.setStylePrimaryName("tngly-button");
		verPanel.add(deleteProfilButton);

		deleteProfilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ClientsideSettings.getAdministration().deleteProfile(ClientsideSettings.getUserProfile(), new DeleteCallback());
				ClientsideSettings.setUserProfile(null);
				Window.open(ClientsideSettings.getLoginInfo().getLogoutUrl(), "_self", "");

				Logger logger = ClientsideSettings.getLogger();
				logger.info("Erfolgreich Profil gelöscht.");
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
