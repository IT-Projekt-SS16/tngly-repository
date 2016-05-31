package de.hdm.editor.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.shared.bo.Profile;

public class EditProfileView extends Update {

	private Boolean existsUserInDB = null;

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

		logger.info(ClientsideSettings.getLoginInfo().getEmailAddress());

		if (ClientsideSettings.getUserProfile() == null) {
			int atIndex = ClientsideSettings.getLoginInfo().getEmailAddress().indexOf("@");
			ClientsideSettings.getAdministration().findProfileByName(
					ClientsideSettings.getLoginInfo().getEmailAddress().substring(0, atIndex), new FindCallback());
		}

		VerticalPanel verPanel = new VerticalPanel();

		RootPanel.get("Details").add(verPanel);


		final TextBox tbfn = new TextBox();
		final TextBox tbn = new TextBox();
		final TextBox tbbh = new TextBox();

		final ListBox hairColourList = new ListBox(false);
		hairColourList.setVisibleItemCount(1);
		hairColourList.addItem("Black");
		hairColourList.addItem("Brown");
		hairColourList.addItem("Red");
		hairColourList.addItem("Blonde");
		hairColourList.addItem("Dark Blonde");

		final ListBox isSmokingBox = new ListBox(false);
		isSmokingBox.setVisibleItemCount(1);
		isSmokingBox.addItem("Yes");
		isSmokingBox.addItem("No");

		final ListBox confessionBox = new ListBox(false);
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

		final ListBox genderBox = new ListBox(false);
		genderBox.setVisibleItemCount(1);
		genderBox.addItem("F");
		genderBox.addItem("M");

		final ListBox myHobbiesSelect = new ListBox(true);
		myHobbiesSelect.setVisibleItemCount(11);
		myHobbiesSelect.addItem("Handicraft");
		myHobbiesSelect.addItem("Languages");
		myHobbiesSelect.addItem("Singing");
		myHobbiesSelect.addItem("Art");
		myHobbiesSelect.addItem("Dancing");
		myHobbiesSelect.addItem("Reading");
		myHobbiesSelect.addItem("Computer");
		myHobbiesSelect.addItem("Movies");
		myHobbiesSelect.addItem("Cooking");
		myHobbiesSelect.addItem("Music");
		myHobbiesSelect.addItem("Fitness");
		
		final DatePicker datePicker = new DatePicker();
		datePicker.setYearArrowsVisible(true);
		datePicker.setYearAndMonthDropdownVisible(true);
		// show 51 years in the years dropdown. The range of years is centered
		// on the selected date
		datePicker.setVisibleYearCount(101);

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
		ta.setVisibleLines(10);

		// HorizontalPanel horizonPanel = new HorizontalPanel();
		// Label firstWarning = new Label("Bitte f�llen Sie alle nachfolgenden
		// Felder vollst�ndig aus!");
		// firstWarning.setPixelSize(10, 10);
		// horizonPanel.add(firstWarning);
		// verPanel.add(horizonPanel);
		// if (ClientsideSettings.getUserProfile() == null){
		// verPanel.add(firstWarning);
		// }

		if (ClientsideSettings.getUserProfile() != null) {
			logger.info("Result: " + ClientsideSettings.getUserProfile().getUserName());
		} else {
			logger.info("Result: NULL");
		}

		Label firstName = new Label("First Name:");
		verPanel.add(firstName);
		if (ClientsideSettings.getUserProfile() == null) {
			verPanel.add(tbfn);
		} else {
			tbfn.setText(ClientsideSettings.getUserProfile().getName());
			verPanel.add(tbfn);
		}

		Label name = new Label("Last Name:");
		verPanel.add(name);
		if (ClientsideSettings.getUserProfile() == null) {
			verPanel.add(tbn);
		} else {
			tbn.setText(ClientsideSettings.getUserProfile().getLastName());
			verPanel.add(tbn);
		}

		Label gender = new Label("Gender:");
		verPanel.add(gender);
		if (ClientsideSettings.getUserProfile() == null) {
			verPanel.add(genderBox);
		} else {
			int index;
			if (ClientsideSettings.getUserProfile().getGender() == "M") {
				index = 1;
			} else {
				index = 0;
			}
			genderBox.setItemSelected(index, true);
			verPanel.add(genderBox);
		}

		Label dateOfBirth = new Label("Date of Birth:");
		verPanel.add(dateOfBirth);
		if (ClientsideSettings.getUserProfile() == null) {
			verPanel.add(datePicker);
		} else {
			datePicker.setValue(ClientsideSettings.getUserProfile().getDateOfBirth());
			verPanel.add(datePicker);
		}

		Label bodyHeight = new Label("Body Height:");
		verPanel.add(bodyHeight);
		if (ClientsideSettings.getUserProfile() == null) {
			verPanel.add(tbbh);
		} else {
			tbbh.setText(Float.toString(ClientsideSettings.getUserProfile().getBodyHeight()));
			verPanel.add(tbbh);
		}

		Label hairColour = new Label("Haircolour:");
		verPanel.add(hairColour);
		if (ClientsideSettings.getUserProfile() == null) {
			verPanel.add(hairColourList);
		} else {
			int index;
			if (ClientsideSettings.getUserProfile().getHairColour() == "Black") {
				index = 0;
			} else if (ClientsideSettings.getUserProfile().getHairColour() == "Brown") {
				index = 1;
			} else if (ClientsideSettings.getUserProfile().getHairColour() == "Red") {
				index = 2;
			} else if (ClientsideSettings.getUserProfile().getHairColour() == "Blonde") {
				index = 3;
			} else {
				index = 4;
			}
			hairColourList.setItemSelected(index, true);
			verPanel.add(hairColourList);
		}

		Label isSmoking = new Label("Smoker:");
		verPanel.add(isSmoking);
		if (ClientsideSettings.getUserProfile() == null) {
			verPanel.add(isSmokingBox);
		} else {
			isSmokingBox.setItemSelected(ClientsideSettings.getUserProfile().getIsSmoking(), true);
			verPanel.add(isSmokingBox);
		}

		Label confession = new Label("Confession:");
		verPanel.add(confession);
		if (ClientsideSettings.getUserProfile() == null) {
			verPanel.add(confessionBox);
		} else {
			int index;
			if (ClientsideSettings.getUserProfile().getConfession() == "Atheistic") {
				index = 0;
			} else if (ClientsideSettings.getUserProfile().getConfession() == "Buddhistic") {
				index = 1;
			} else if (ClientsideSettings.getUserProfile().getConfession() == "Evangelic") {
				index = 2;
			} else if (ClientsideSettings.getUserProfile().getConfession() == "Catholic") {
				index = 3;
			} else if (ClientsideSettings.getUserProfile().getConfession() == "Hindu") {
				index = 4;
			} else if (ClientsideSettings.getUserProfile().getConfession() == "Muslim") {
				index = 5;
			} else if (ClientsideSettings.getUserProfile().getConfession() == "Jewish") {
				index = 6;
			} else if (ClientsideSettings.getUserProfile().getConfession() == "Orthodox") {
				index = 7;
			} else {
				index = 8;
			}
			confessionBox.setItemSelected(index, true);
			verPanel.add(confessionBox);
		}
		
		Label myHobbiesSelectLabel = new Label("My Hobbies:");
		verPanel.add(myHobbiesSelectLabel);
		if (ClientsideSettings.getUserProfile() == null) {
			verPanel.add(myHobbiesSelect);
		} else {
			int index;
			if (ClientsideSettings.getUserProfile().getHairColour() == "Soccer") {
				index = 0;
			} else if (ClientsideSettings.getUserProfile().getHairColour() == "Baseball") {
				index = 1;
			} else if (ClientsideSettings.getUserProfile().getHairColour() == "Volleyball") {
				index = 2;
			} else if (ClientsideSettings.getUserProfile().getHairColour() == "Basketball") {
				index = 3;
			} else if (ClientsideSettings.getUserProfile().getHairColour() == "Golf") {
				index = 4;
			} else {
				index = 5;
			}
			myHobbiesSelect.setItemSelected(index, true);
			verPanel.add(myHobbiesSelect);
			
					
		}
		
		Label selfDescriptionLabel = new Label("This is how I describe myself:");
		verPanel.add(selfDescriptionLabel);
		verPanel.add(ta);
		
		final Button saveProfilButton = new Button("Save");
		saveProfilButton.setStylePrimaryName("tngly-menubutton");
		verPanel.add(saveProfilButton);

		saveProfilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				Logger logger = ClientsideSettings.getLogger();
				logger.info("Erfolgreich onClick ausgefuehrt.");

				logger.info("getUserProfile war null.");
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

				logger.info("Confession CHECK");

				if (ClientsideSettings.getUserProfile() == null) {
					ClientsideSettings.setUserProfile(temp);
					 ClientsideSettings.getAdministration().createProfile(temp,
					 new CreateCallback());
					logger.info("if-getAdministration wurde aufgerufen");
				} else {
					logger.info("getUserProfile war nicht 0.");
					ClientsideSettings.setUserProfile(temp);
					 ClientsideSettings.getAdministration().createProfile(temp,
							 new CreateCallback());
							logger.info("if-getAdministration wurde aufgerufen");
					// ClientsideSettings.getAdministration().editProfile(temp,
					// new CreateCallback());
				}

				Update update = new ProfileView();

				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);

				logger.info("Erfolgreicher Reswitch.");
				
			}
		});
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

class FindCallback implements AsyncCallback<Profile> {
	@Override
	public void onFailure(Throwable caught) {
		ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
	}

	@Override
	public void onSuccess(Profile result) {
		ClientsideSettings.setUserProfile(result);
		ClientsideSettings.getLogger().info("Result: " + result.toString());
	}

}
