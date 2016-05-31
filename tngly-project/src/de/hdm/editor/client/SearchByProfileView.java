package de.hdm.editor.client;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
//import com.google.gwt.event.dom.client.KeyPressEvent;
//import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.SearchProfile;

public class SearchByProfileView extends Update {

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
	 * Jeder Showcase muss die <code>run()</code>-Methode implementieren. Sie
	 * ist eine "Einschubmethode", die von einer Methode der Basisklasse
	 * <code>ShowCase</code> aufgerufen wird, wenn der Showcase aktiviert wird.
	 */
	@Override
	protected void run() {

		Logger logger = ClientsideSettings.getLogger();
		logger.info("Erfolgreich Search-By-Profile-View geswitcht.");

		logger.info(ClientsideSettings.getLoginInfo().getEmailAddress());

		if (ClientsideSettings.getUserProfile() == null) {
			int atIndex = ClientsideSettings.getLoginInfo().getEmailAddress().indexOf("@");
			ClientsideSettings.getAdministration().findProfileByName(
					ClientsideSettings.getLoginInfo().getEmailAddress().substring(0, atIndex), new FindCallback());
		}

		VerticalPanel verPanel = new VerticalPanel();
		VerticalPanel verPanel2 = new VerticalPanel();
		verPanel.setSpacing(10);
		verPanel2.setSpacing(10);
		
		
		HorizontalPanel horPanel = new HorizontalPanel();
		horPanel.add(verPanel);
		horPanel.add(verPanel2);

		
		RootPanel.get("Details").add(horPanel);
		
		final TextBox tbfn = new TextBox();
		final TextBox tbn = new TextBox();
		final TextBox tbbh = new TextBox();
		final TextBox tbAgeRangeFrom = new TextBox();
		final TextBox tbAgeRangeTo = new TextBox();
		final TextBox tbHeightRangeFrom = new TextBox();
		final TextBox tbHeightRangeTo = new TextBox();
		// final TextBox tbmh = new TextBox();
		
		final CheckBox chkGenderAny = new CheckBox();
		final CheckBox chkAgeAny = new CheckBox();
		final CheckBox chkBodyHeightAny = new CheckBox();
		final CheckBox chkHairColourAny = new CheckBox();
		final CheckBox chkSmokerAny = new CheckBox();
		final CheckBox chkConfessionAny = new CheckBox();

		final ListBox hairColourList = new ListBox(false);
		hairColourList.setVisibleItemCount(1);
		hairColourList.addItem("Black");
		hairColourList.addItem("Brown");
		hairColourList.addItem("Red");
		hairColourList.addItem("Blonde");
		hairColourList.addItem("Dark Blonde");

		final ListBox isSmokingBox = new ListBox(false);
		isSmokingBox.setVisibleItemCount(1);
		isSmokingBox.addItem("yes");
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

		// final DatePicker datePicker = new DatePicker();
		// datePicker.setYearArrowsVisible(true);
		// datePicker.setYearAndMonthDropdownVisible(true);
		// // show 51 years in the years dropdown. The range of years is
		// centered
		// // on the selected date
		// datePicker.setVisibleYearCount(101);

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

		if (ClientsideSettings.getSearchProfile() != null) {
			logger.info("Result: " + ClientsideSettings.getUserProfile().getUserName());
		} else {
			logger.info("Result: SearchProfile NULL");
		}

		// Label firstName = new Label("First Name:");
		// verPanel.add(firstName);
		// if (ClientsideSettings.getUserProfile() == null) {
		// verPanel.add(tbfn);
		// } else {
		// tbfn.setText(ClientsideSettings.getUserProfile().getName());
		// verPanel.add(tbfn);
		// }
		//
		// Label name = new Label("Last Name:");
		// verPanel.add(name);
		// if (ClientsideSettings.getUserProfile() == null) {
		// verPanel.add(tbn);
		// } else {
		// tbn.setText(ClientsideSettings.getUserProfile().getLastName());
		// verPanel.add(tbn);
		// }

		Label gender = new Label("Gender:");
		verPanel.add(gender);
		if (ClientsideSettings.getSearchProfile() == null) {
			verPanel.add(genderBox);
		} else {
			int index;
			if (ClientsideSettings.getSearchProfile().getGender() == "M") {
				index = 1;
			} else {
				index = 0;
			}
			genderBox.setItemSelected(index, true);
			verPanel.add(genderBox);
		}
		
		verPanel.add(chkGenderAny);
		Label anyCheck = new Label("Any");
		verPanel.add(anyCheck);
		chkGenderAny.addClickHandler(new ClickHandler() {
		      @Override
		      public void onClick(ClickEvent event) {
		        boolean genderChecked = ((CheckBox) event.getSource()).getValue();
		        if (genderChecked == true){
		        	genderBox.setEnabled(false);
		        } else{
		        	genderBox.setEnabled(true);
		        }
		      }
		    });

		// Label dateOfBirth = new Label("Date of Birth:");
		// verPanel.add(dateOfBirth);
		// if (ClientsideSettings.getUserProfile() == null) {
		// verPanel.add(datePicker);
		// } else {
		// datePicker.setValue(ClientsideSettings.getUserProfile().getDateOfBirth());
		// verPanel.add(datePicker);
		// }

		Label lblAgeRange = new Label("Age Range:");
		Label lblAgeRangeFrom = new Label("From ");
		Label lblAgeRangeTo = new Label("To ");
		verPanel.add(lblAgeRange);
		if (ClientsideSettings.getSearchProfile() == null) {
			verPanel.add(lblAgeRangeFrom);
			verPanel.add(tbAgeRangeFrom);
			verPanel.add(lblAgeRangeTo);
			verPanel.add(tbAgeRangeTo);
		} else {
			tbAgeRangeFrom.setText(Integer.toString(ClientsideSettings.getSearchProfile().getAgeRangeFrom()));
			verPanel.add(tbAgeRangeFrom);
			tbAgeRangeTo.setText(Integer.toString(ClientsideSettings.getSearchProfile().getAgeRangeTo()));
			verPanel.add(tbAgeRangeTo);
		}
		verPanel.add(chkAgeAny);
		verPanel.add(anyCheck);
		chkAgeAny.addClickHandler(new ClickHandler() {
		      @Override
		      public void onClick(ClickEvent event) {
		        boolean ageChecked = ((CheckBox) event.getSource()).getValue();
		        if (ageChecked == true){
		        	tbAgeRangeFrom.setEnabled(false);
		        	tbAgeRangeTo.setEnabled(false);
		        } else{
		        	tbAgeRangeFrom.setEnabled(true);
		        	tbAgeRangeTo.setEnabled(true);
		        }
		      }
		    });

		// Label bodyHeight = new Label("Body Height:");
		// verPanel.add(bodyHeight);
		// if (ClientsideSettings.getUserProfile() == null) {
		// verPanel.add(tbbh);
		// } else {
		// tbbh.setText(Float.toString(ClientsideSettings.getUserProfile().getBodyHeight()));
		// verPanel.add(tbbh);
		// }

		Label bodyHeight = new Label("Body Height Range:");
		Label bodyHeightFrom = new Label("From ");
		Label bodyHeightTo = new Label("To ");
		verPanel.add(bodyHeight);
		if (ClientsideSettings.getSearchProfile() == null) {
			verPanel.add(bodyHeightFrom);
			verPanel.add(tbHeightRangeFrom);
			verPanel.add(bodyHeightTo);
			verPanel.add(tbHeightRangeTo);
		} else {
			tbHeightRangeFrom.setText(Float.toString(ClientsideSettings.getSearchProfile().getBodyHeightFrom()));
			verPanel.add(tbHeightRangeFrom);
			tbHeightRangeTo.setText(Float.toString(ClientsideSettings.getSearchProfile().getBodyHeightTo()));
			verPanel.add(tbHeightRangeTo);
		}
		verPanel.add(chkBodyHeightAny);
		verPanel.add(anyCheck);
		chkBodyHeightAny.addClickHandler(new ClickHandler() {
		      @Override
		      public void onClick(ClickEvent event) {
		        boolean bodyHeightChecked = ((CheckBox) event.getSource()).getValue();
		        if (bodyHeightChecked == true){
		        	tbHeightRangeFrom.setEnabled(false);
		        	tbHeightRangeTo.setEnabled(false);
		        } else{
		        	tbHeightRangeFrom.setEnabled(true);
		        	tbHeightRangeTo.setEnabled(true);
		        }
		      }
		    });

		Label hairColour = new Label("Haircolour:");
		verPanel.add(hairColour);
		if (ClientsideSettings.getSearchProfile() == null) {
			verPanel.add(hairColourList);
		} else {
			int index;
			if (ClientsideSettings.getSearchProfile().getHairColour() == "Black") {
				index = 0;
			} else if (ClientsideSettings.getSearchProfile().getHairColour() == "Brown") {
				index = 1;
			} else if (ClientsideSettings.getSearchProfile().getHairColour() == "Red") {
				index = 2;
			} else if (ClientsideSettings.getSearchProfile().getHairColour() == "Blonde") {
				index = 3;
			} else {
				index = 4;
			}
			hairColourList.setItemSelected(index, true);
			verPanel.add(hairColourList);
		}
		verPanel.add(chkHairColourAny);
		verPanel.add(anyCheck);
		chkHairColourAny.addClickHandler(new ClickHandler() {
		      @Override
		      public void onClick(ClickEvent event) {
		        boolean hairColourChecked = ((CheckBox) event.getSource()).getValue();
		        if (hairColourChecked == true){
		        	hairColourList.setEnabled(false);
		        } else{
		        	hairColourList.setEnabled(true);
		        }
		      }
		    });

		Label isSmoking = new Label("Smoker:");
		verPanel2.add(isSmoking);
		if (ClientsideSettings.getSearchProfile() == null) {
			verPanel2.add(isSmokingBox);
		} else {
			isSmokingBox.setItemSelected(ClientsideSettings.getSearchProfile().getIsSmoking(), true);
			verPanel2.add(isSmokingBox);
		}
		verPanel2.add(chkSmokerAny);
		verPanel2.add(anyCheck);
		chkSmokerAny.addClickHandler(new ClickHandler() {
		      @Override
		      public void onClick(ClickEvent event) {
		        boolean smokerChecked = ((CheckBox) event.getSource()).getValue();
		        if (smokerChecked == true){
		        	isSmokingBox.setEnabled(false);
		        }else{
		        	isSmokingBox.setEnabled(true);
		        }
		      }
		    });

		Label confession = new Label("Confession:");
		verPanel2.add(confession);
		if (ClientsideSettings.getSearchProfile() == null) {
			verPanel2.add(confessionBox);
		} else {
			int index;
			if (ClientsideSettings.getSearchProfile().getConfession() == "Atheistic") {
				index = 0;
			} else if (ClientsideSettings.getSearchProfile().getConfession() == "Buddhistic") {
				index = 1;
			} else if (ClientsideSettings.getSearchProfile().getConfession() == "Evangelic") {
				index = 2;
			} else if (ClientsideSettings.getSearchProfile().getConfession() == "Catholic") {
				index = 3;
			} else if (ClientsideSettings.getSearchProfile().getConfession() == "Hindu") {
				index = 4;
			} else if (ClientsideSettings.getSearchProfile().getConfession() == "Muslim") {
				index = 5;
			} else if (ClientsideSettings.getSearchProfile().getConfession() == "Jewish") {
				index = 6;
			} else if (ClientsideSettings.getSearchProfile().getConfession() == "Orthodox") {
				index = 7;
			} else {
				index = 8;
			}
			confessionBox.setItemSelected(index, true);
			verPanel.add(confessionBox);
		}
		verPanel2.add(chkConfessionAny);
		verPanel2.add(anyCheck);
		chkConfessionAny.addClickHandler(new ClickHandler() {
		      @Override
		      public void onClick(ClickEvent event) {
		        boolean confessionChecked = ((CheckBox) event.getSource()).getValue();
		        if (confessionChecked == true){
		        	confessionBox.setEnabled(false);
		        } else{
		        	confessionBox.setEnabled(true);
		        }
		      }
		    });

		Label myHobbiesSelectLabel = new Label("My Hobbies:");
		verPanel2.add(myHobbiesSelectLabel);
		if (ClientsideSettings.getUserProfile() == null) {
			verPanel2.add(myHobbiesSelect);
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
			verPanel2.add(myHobbiesSelect);

		}

		// Label myHobbiesLabel = new Label("Other Hobbies:");
		// verPanel.add(myHobbiesLabel);
		// verPanel.add(tbmh);

		final Button showProfilesButton = new Button("Search");
		showProfilesButton.setStylePrimaryName("tngly-menubutton");
		verPanel2.add(showProfilesButton);

		showProfilesButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				boolean genderChecked = chkGenderAny.getValue();
				boolean ageChecked = chkAgeAny.getValue();
				boolean bodyHeightChecked = chkBodyHeightAny.getValue();
				boolean hairColourChecked = chkHairColourAny.getValue();
				boolean smokerChecked = chkSmokerAny.getValue();
				boolean confessionChecked = chkConfessionAny.getValue();

				Logger logger = ClientsideSettings.getLogger();
				logger.info("Erfolgreich onClick ausgefuehrt.");

				logger.info("getUserProfile war null.");
				SearchProfile temp = new SearchProfile();

				int atIndex = ClientsideSettings.getLoginInfo().getEmailAddress().indexOf("@");

				temp.setUserName(ClientsideSettings.getLoginInfo().getEmailAddress().substring(0, atIndex));

				logger.info("Profile+DateTimeFormat instantiiert");

				temp.setName(tbfn.getText());

				logger.info("Name CHECK");

				temp.setLastName(tbn.getText());

				logger.info("lastName CHECK");

				if (genderChecked == false){
				int selectedGenderIndex = genderBox.getSelectedIndex();
				temp.setGender(genderBox.getItemText(selectedGenderIndex));
				} else {
					temp.setGender(null);
				}

				logger.info("gender CHECK");

				logger.info("dateOfBirth CHECK");

				// float f = Float.valueOf(tbbh.getText().trim()).floatValue();
				// temp.setBodyHeight(f);

				if (ageChecked == false){
				temp.setAgeRangeFrom(Integer.parseInt(tbAgeRangeFrom.getText()));
				temp.setAgeRangeTo(Integer.parseInt(tbAgeRangeTo.getText()));
				} else{
					temp.setAgeRangeFrom(0);
					temp.setAgeRangeTo(0);
				}

				if (bodyHeightChecked == false){
				temp.setBodyHeightFrom(Float.parseFloat(tbHeightRangeFrom.getText()));
				temp.setBodyHeightTo(Float.parseFloat(tbHeightRangeTo.getText()));
				} else{
					temp.setBodyHeightFrom(0f);
					temp.setBodyHeightTo(0f);
				}

				logger.info("bodyHeight CHECK");

				// temp.setHairColour(tbhc.getText());
				if (hairColourChecked == false){
				int selectedHairColourIndex = hairColourList.getSelectedIndex();
				temp.setHairColour(hairColourList.getItemText(selectedHairColourIndex));
				} else{
					temp.setHairColour(null);
				}

				logger.info("HairColour CHECK");

				if (smokerChecked == false){
				int selectedIsSmokingIndex = isSmokingBox.getSelectedIndex();
				if (isSmokingBox.getItemText(selectedIsSmokingIndex) == "Yes") {
					temp.setIsSmoking(0);
				} else {
					temp.setIsSmoking(1);
				}
				} else{
					temp.setIsSmoking(-1);
				}

				logger.info("isSmoking CHECK");

				// temp.setConfession(tbc.getText());
				if (confessionChecked == false){
				int selectedConfessionIndex = confessionBox.getSelectedIndex();
				temp.setConfession(confessionBox.getItemText(selectedConfessionIndex));
				} else {
					temp.setConfession(null);
				}

				logger.info("Confession CHECK");

				ClientsideSettings.setSearchProfile(temp);
				logger.info(ClientsideSettings.getSearchProfile().toString());

				ClientsideSettings.getAdministration().searchAndCompareProfiles(temp, new CompareCallback());

//				Update update = new ShowProfilesView();
//
//				RootPanel.get("Details").clear();
//				RootPanel.get("Details").add(update);
//
//				logger.info("Erfolgreicher Reswitch.");

			}
		});
	}
}

class CompareCallback implements AsyncCallback<ArrayList<Profile>> {
	@Override
	public void onFailure(Throwable caught) {
		ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
	}

	@Override
	public void onSuccess(ArrayList<Profile> result) {
		ClientsideSettings.getLogger().info("Profile-Liste Auf Client-Seite gesetzt");
		ClientsideSettings.setProfilesFoundAndCompared(result);
		ClientsideSettings.getLogger().info("Profiles Size:" + ClientsideSettings.getProfilesFoundAndCompared().size());
		Update update = new ShowProfilesView();
		RootPanel.get("Details").clear();
		RootPanel.get("Details").add(update);
		ClientsideSettings.getLogger().info("Erfolgreicher Reswitch.");
	}

}
