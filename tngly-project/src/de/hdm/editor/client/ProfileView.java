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
		profile.setCellSpacing(10);
		
		verPanel.add(profile);

		Label userName = new Label("Username:");
		profile.setWidget(0,0,userName);
		Label userNameValue;
		if (ClientsideSettings.getUserProfile() == null){
			userNameValue = new Label("please edit");
		profile.setWidget(0, 1, userNameValue);
		} else {
			userNameValue = new Label(ClientsideSettings.getUserProfile().getUserName());
		}
		profile.setWidget(0, 1, userNameValue);

		Label firstName = new Label("First Name:");
		profile.setWidget(1, 0, firstName);
//		verPanel.add(firstName);
		if (ClientsideSettings.getUserProfile() == null){
		Label firstNameValue = new Label("please edit");
		profile.setWidget(1, 1, firstNameValue);
		}else{
		Label firstNameValue = new Label(ClientsideSettings.getUserProfile().getName());
		profile.setWidget(1, 1, firstNameValue);
//		verPanel.add(firstNameValue);
		}
		Label name = new Label("Last Name:");
		profile.setWidget(2, 0, name);
//		verPanel.add(name);
		if (ClientsideSettings.getUserProfile() == null){
		Label nameValue = new Label("please edit");
		profile.setWidget(2,1, nameValue);
		}else{
		Label nameValue = new Label(ClientsideSettings.getUserProfile().getLastName());
		profile.setWidget(2, 1, nameValue);
//		verPanel.add(nameValue);
		}

		Label gender = new Label("Gender:");
		profile.setWidget(3, 0, gender);
//		verPanel.add(gender);
		if (ClientsideSettings.getUserProfile() == null){
		Label genderValue = new Label("please edit");
		profile.setWidget(3,1, genderValue);
		}else {
		Label genderValue = new Label(ClientsideSettings.getUserProfile().getGender());
		profile.setWidget(3, 1, genderValue);
			}
//		verPanel.add(genderValue);

		Label dateOfBirth = new Label("Date of Birth:");
		profile.setWidget(4, 0, dateOfBirth);
//		verPanel.add(dateOfBirth);
		if (ClientsideSettings.getUserProfile() == null){
			Label dOBValue = new Label("please edit");
			profile.setWidget(4,1, dOBValue);
			}else {
		Label dOBValue = new Label(ClientsideSettings.getUserProfile().getDateOfBirth().toString());
		profile.setWidget(4, 1, dOBValue);
//		verPanel.add(dOBValue);
			}

		Label bodyHeight = new Label("Body Height:");
		profile.setWidget(5, 0, bodyHeight);
//		verPanel.add(bodyHeight);
		if (ClientsideSettings.getUserProfile() == null){
			Label bodyHeightValue = new Label("please edit");
			profile.setWidget(5,1, bodyHeightValue);
			}else {
		Label bodyHeightValue = new Label(Float.toString(ClientsideSettings.getUserProfile().getBodyHeight()));
		profile.setWidget(5, 1, bodyHeightValue);
//		verPanel.add(bodyHeightValue);
			}
		
		Label hairColour = new Label("Haircolour:");
		profile.setWidget(6, 0, hairColour);
//		verPanel.add(hairColour);
		if (ClientsideSettings.getUserProfile() == null){
			Label hairColourValue = new Label("please edit");
			profile.setWidget(6,1, hairColourValue);
			}else {
		Label hairColourValue = new Label(ClientsideSettings.getUserProfile().getHairColour());
		profile.setWidget(6, 1, hairColourValue);
//		verPanel.add(hairColourValue);
			}

		Label isSmoking = new Label("Smoker:");
		profile.setWidget(7, 0, isSmoking);
//		verPanel.add(isSmoking);
		if (ClientsideSettings.getUserProfile() == null){
			Label smokingValue = new Label("please edit");
			profile.setWidget(7,1, smokingValue);
			}else {
		Label smokingValue;
		if (ClientsideSettings.getUserProfile().getIsSmoking() == 0){
			smokingValue = new Label("Yes");
		} else {
			smokingValue = new Label("No");
		}
		profile.setWidget(7, 1, smokingValue);
//		verPanel.add(smokingValue);
			}
		
		Label confession = new Label("Confession:");
		profile.setWidget(8, 0, confession);
//		verPanel.add(confession);
		if (ClientsideSettings.getUserProfile() == null){
			Label confessionValue = new Label("please edit");
			profile.setWidget(8,1, confessionValue);
			}else {
		Label confessionValue = new Label(ClientsideSettings.getUserProfile().getConfession());
		profile.setWidget(8, 1, confessionValue);
//		verPanel.add(confessionValue);
			}

		final Button editProfilButton = new Button("Edit Profile");
		editProfilButton.setStylePrimaryName("tngly-button");
		profile.setWidget(9, 0, editProfilButton);

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
		profile.setWidget(10, 0, deleteProfilButton);

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
