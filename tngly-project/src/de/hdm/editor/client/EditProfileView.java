package de.hdm.editor.client;

import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.shared.CommonSettings;
import de.hdm.core.shared.bo.Profile;

public class EditProfileView extends Update {

	/**
	 * Jeder Showcase besitzt eine einleitende Überschrift, die durch diese
	 * Methode zu erstellen ist.
	 * 
	 * @see Showcase#getHeadlineText()
	 */
	@Override
	protected String getHeadlineText() {
		return "EDIT VIEW";
	}

	/**
	 * Jede View muss die <code>run()</code>-Methode implementieren. Sie ist
	 * eine "Einschubmethode", die von einer Methode der Basisklasse
	 * <code>Update</code> aufgerufen wird, wenn der View aktiviert wird.
	 */
	@Override
	protected void run() {

		Logger logger = ClientsideSettings.getLogger();
		logger.info("Erfolgreich Profile-Edit-View geswitcht.");

		VerticalPanel verPanel = new VerticalPanel();

		RootPanel.get("Details").add(verPanel);

		TextBox tbu = new TextBox();
		final TextBox tbfn = new TextBox();
		final TextBox tbn = new TextBox();
		final TextBox tbbh = new TextBox();
		final TextBox tbhc = new TextBox();
		final TextBox tbc = new TextBox();

		final ListBox isSmokingBox = new ListBox(false);
		isSmokingBox.setVisibleItemCount(2);
		isSmokingBox.addItem("yes");
		isSmokingBox.addItem("No");

		final ListBox genderBox = new ListBox(false);
		genderBox.setVisibleItemCount(2);
		genderBox.addItem("w");
		genderBox.addItem("m");

		final DatePicker datePicker = new DatePicker();

		// *** BEISPIEL ADDKEYHANDLER NOCH F�R ALLE �BERNEHMEN***

		// tbfn.addKeyPressHandler(new KeyPressHandler() {

		// public void onKeyPress(KeyPressEvent event) {
		// if (!Character.isDigit(event.getCharCode())) {
		// ((TextBox) event.getSource()).cancelKey();
		// }
		// }
		// });

		TextArea ta = new TextArea();
		ta.setCharacterWidth(80);
		ta.setVisibleLines(50);

		Label userName = new Label("Username:");
		verPanel.add(userName);
		if (CommonSettings.getUserProfile() == null) {
			verPanel.add(tbu);
		} else {
			tbu.setText(CommonSettings.getUserProfile().getUserName());
			verPanel.add(tbu);
		}
		

		Label firstName = new Label("First Name:");
		verPanel.add(firstName);
		if (CommonSettings.getUserProfile() == null) {
			verPanel.add(tbfn);
		} else {
			tbfn.setText(CommonSettings.getUserProfile().getName());
			verPanel.add(tbfn);
		}
		

		Label name = new Label("Last Name:");
		verPanel.add(name);
		if (CommonSettings.getUserProfile() == null) {
			verPanel.add(tbn);
		} else {
			tbn.setText(CommonSettings.getUserProfile().getLastName());
			verPanel.add(tbn);
		}
		

		Label gender = new Label("Gender:");
		verPanel.add(gender);
		if (CommonSettings.getUserProfile() == null) {
			verPanel.add(genderBox);
		} else {
			int index;
			if (CommonSettings.getUserProfile().getGender() == "m"){
				index = 1;
			} else {
				index = 0;
			}
			genderBox.setItemSelected(index, true);
			verPanel.add(genderBox);
		}
		

		Label dateOfBirth = new Label("Date of Birth:");
		verPanel.add(dateOfBirth);
		if (CommonSettings.getUserProfile() == null) {
			verPanel.add(datePicker);
		} else {
			datePicker.setValue(CommonSettings.getUserProfile().getDateOfBirth());
			verPanel.add(datePicker);
		}
		

		Label bodyHeight = new Label("Body Height:");
		verPanel.add(bodyHeight);
		if (CommonSettings.getUserProfile() == null) {
			verPanel.add(tbbh);
		} else {
			tbbh.setText(Float.toString(CommonSettings.getUserProfile().getBodyHeight()));
			verPanel.add(tbbh);
		}
		

		Label hairColour = new Label("Haircolour:");
		verPanel.add(hairColour);
		if (CommonSettings.getUserProfile() == null) {
			verPanel.add(tbhc);
		} else {
			tbhc.setText(CommonSettings.getUserProfile().getHairColour());
			verPanel.add(tbhc);
		}
		

		Label isSmoking = new Label("Smoker:");
		verPanel.add(isSmoking);
		if (CommonSettings.getUserProfile() == null) {
			verPanel.add(isSmokingBox);
		} else {
			isSmokingBox.setItemSelected(CommonSettings.getUserProfile().getIsSmoking(), true);
			verPanel.add(isSmokingBox);
		}
		

		Label confession = new Label("Confession:");
		verPanel.add(confession);
		if (CommonSettings.getUserProfile() == null) {
			verPanel.add(tbc);
		} else {
			tbc.setText(CommonSettings.getUserProfile().getConfession());
			verPanel.add(tbc);
		}
		

		final Button saveProfilButton = new Button("Save");
		saveProfilButton.setStylePrimaryName("tngly-menubutton");
		verPanel.add(saveProfilButton);

		saveProfilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				Logger logger = ClientsideSettings.getLogger();
				logger.info("Erfolgreich onClick ausgefuehrt.");

				logger.info("getUserProfile war null.");
				Profile temp = new Profile();

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

				temp.setHairColour(tbhc.getText());

				logger.info("HairColour CHECK");

				int selectedIsSmokingIndex = isSmokingBox.getSelectedIndex();
				if (isSmokingBox.getItemText(selectedIsSmokingIndex) == "Yes") {
					temp.setIsSmoking(0);
				} else {
					temp.setIsSmoking(1);
				}

				logger.info("isSmoking CHECK");

				temp.setConfession(tbc.getText());

				logger.info("Confession CHECK");

				if (CommonSettings.getUserProfile() == null) {
					ClientsideSettings.getAdministration().createProfile(temp, new CreateCallback());
//					CommonSettings.setUserProfile(temp);
					logger.info("if-getAdministration wurde aufgerufen");
				} else {
					logger.info("getUserProfile war nicht 0.");
//					CommonSettings.setUserProfile(temp);
					ClientsideSettings.getAdministration().editProfile(temp, new CreateCallback());
				}

				Update update = new ProfileView();

				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);

				logger.info("Erfolgreicher Reswitch.");

			}
		});
	}

}

class CreateCallback implements AsyncCallback<Void> {
	@Override
	public void onFailure(Throwable caught) {
		ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
	}

	@Override
	public void onSuccess(Void result) {
		// TODO Auto-generated method stub

	}

}
