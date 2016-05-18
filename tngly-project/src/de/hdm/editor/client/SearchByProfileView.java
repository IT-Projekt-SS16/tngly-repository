package de.hdm.editor.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
//import com.google.gwt.event.dom.client.KeyPressEvent;
//import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.shared.bo.Profile;

public class SearchByProfileView extends Update{

	  /**
	   * Jeder Showcase besitzt eine einleitende Ãœberschrift, die durch diese
	   * Methode zu erstellen ist.
	   * 
	   * @see Showcase#getHeadlineText()
	   */
	  @Override
	  protected String getHeadlineText() {
	    return "Search By Profile View";
	  }

	  /**
	   * Jeder Showcase muss die <code>run()</code>-Methode implementieren. Sie ist
	   * eine "Einschubmethode", die von einer Methode der Basisklasse
	   * <code>ShowCase</code> aufgerufen wird, wenn der Showcase aktiviert wird.
	   */
	  @Override
	  protected void run() {
		  
		  Logger logger = ClientsideSettings.getLogger();
			logger.info("Erfolgreich Profile-Edit-View geswitcht.");

			logger.info(ClientsideSettings.getLoginInfo().getEmailAddress());

			if (ClientsideSettings.getUserProfile() == null) {
				int atIndex = ClientsideSettings.getLoginInfo().getEmailAddress().indexOf("@");
				ClientsideSettings.getAdministration().findProfile(
						ClientsideSettings.getLoginInfo().getEmailAddress().substring(0, atIndex), new FindCallback());
			}

			VerticalPanel verPanel = new VerticalPanel();

			RootPanel.get("Details").add(verPanel);

			TextBox tbu = new TextBox();
			final TextBox tbfn = new TextBox();
			final TextBox tbn = new TextBox();
			final TextBox tbbh = new TextBox();
			final TextBox tbhc = new TextBox();
			final TextBox tbc = new TextBox();

			final ListBox hairColourList = new ListBox(false);
			hairColourList.setVisibleItemCount(5);
			hairColourList.addItem("Black");
			hairColourList.addItem("Brown");
			hairColourList.addItem("Red");
			hairColourList.addItem("Blonde");
			hairColourList.addItem("Dark Blonde");

			final ListBox isSmokingBox = new ListBox(false);
			isSmokingBox.setVisibleItemCount(2);
			isSmokingBox.addItem("yes");
			isSmokingBox.addItem("No");

			final ListBox confessionBox = new ListBox(false);
			confessionBox.setVisibleItemCount(9);
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
			genderBox.setVisibleItemCount(2);
			genderBox.addItem("w");
			genderBox.addItem("m");

			

			// *** BEISPIEL ADDKEYHANDLER NOCH Fï¿½R ALLE ï¿½BERNEHMEN***

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

			// HorizontalPanel horizonPanel = new HorizontalPanel();
			// Label firstWarning = new Label("Bitte füllen Sie alle nachfolgenden
			// Felder vollständig aus!");
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


			Label gender = new Label("Gender:");
			verPanel.add(gender);
			if (ClientsideSettings.getUserProfile() == null) {
				verPanel.add(genderBox);
			} else {
				int index;
				if (ClientsideSettings.getUserProfile().getGender() == "m") {
					index = 1;
				} else {
					index = 0;
				}
				genderBox.setItemSelected(index, true);
				verPanel.add(genderBox);
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

			final Button showProfilesButton = new Button("Search");
			showProfilesButton.setStylePrimaryName("tngly-menubutton");
			verPanel.add(showProfilesButton);

			showProfilesButton.addClickHandler(new ClickHandler() {
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

					

					logger.info("dateOfBirth CHECK");

					float f = Float.valueOf(tbbh.getText().trim()).floatValue();
					temp.setBodyHeight(f);

					logger.info("bodyHeight CHECK");

					// temp.setHairColour(tbhc.getText());
					int selectedHairColourIndex = hairColourList.getSelectedIndex();
					temp.setGender(hairColourList.getItemText(selectedHairColourIndex));

					logger.info("HairColour CHECK");

					int selectedIsSmokingIndex = isSmokingBox.getSelectedIndex();
					if (isSmokingBox.getItemText(selectedIsSmokingIndex) == "Yes") {
						temp.setIsSmoking(0);
					} else {
						temp.setIsSmoking(1);
					}

					logger.info("isSmoking CHECK");

					// temp.setConfession(tbc.getText());
					int selectedConfessionIndex = confessionBox.getSelectedIndex();
					temp.setGender(confessionBox.getItemText(selectedConfessionIndex));

					logger.info("Confession CHECK");

					if (ClientsideSettings.getUserProfile() == null) {
						ClientsideSettings.setUserProfile(temp);
						// ClientsideSettings.getAdministration().createProfile(temp,
						// new CreateCallback());
						logger.info("if-getAdministration wurde aufgerufen");
					} else {
						logger.info("getUserProfile war nicht 0.");
						ClientsideSettings.setUserProfile(temp);
						// ClientsideSettings.getAdministration().editProfile(temp,
						// new CreateCallback());
					}

					Update update = new ShowProfilesView();

					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(update);

					logger.info("Erfolgreicher Reswitch.");
					
				}
			});
		}

	}

	