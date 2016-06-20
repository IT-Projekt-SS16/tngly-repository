package de.hdm.editor.client;

import java.awt.Checkbox;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
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
		return "";
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
		VerticalPanel verPanel2 = new VerticalPanel();
		verPanel.setSpacing(10);
		verPanel2.setSpacing(10);
		
		
		

		final TextBox tbun = new TextBox();
		tbun.setPixelSize(120, 15);
		final TextBox tbfn = new TextBox();
		tbfn.setPixelSize(120, 15);
		final TextBox tbn = new TextBox();
		tbn.setPixelSize(120, 15);
		final TextBox tbbh = new TextBox();
		tbbh.setPixelSize(120, 15);
		

		final ListBox hairColourList = new ListBox(false);
		hairColourList.setVisibleItemCount(1);
		hairColourList.addItem("Black");
		hairColourList.addItem("Brown");
		hairColourList.addItem("Red");
		hairColourList.addItem("Blonde");
		hairColourList.addItem("Dark Blonde");
		hairColourList.setPixelSize(130,25);

		final ListBox isSmokingBox = new ListBox(false);
		isSmokingBox.setVisibleItemCount(1);
		isSmokingBox.addItem("Yes");
		isSmokingBox.addItem("No");
		isSmokingBox.setPixelSize(130,25);

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
		confessionBox.setPixelSize(130,25);

		final ListBox genderBox = new ListBox(false);
		genderBox.setVisibleItemCount(1);
		genderBox.addItem("Female");
		genderBox.addItem("Male");
		genderBox.setPixelSize(130,25);

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
		myHobbiesSelect.setPixelSize(130,130);
		
		
		
		final DatePicker datePicker = new DatePicker();
		datePicker.setYearArrowsVisible(true);
		datePicker.setYearAndMonthDropdownVisible(false);
		// show 51 years in the years dropdown. The range of years is centered
		// on the selected date
		datePicker.setVisibleYearCount(101);
		datePicker.setYearAndMonthDropdownVisible(true);

		// tbfn.addKeyPressHandler(new KeyPressHandler() {

		// public void onKeyPress(KeyPressEvent event) {
		// if (!Character.isDigit(event.getCharCode())) {
		// ((TextBox) event.getSource()).cancelKey();
		// }
		// }
		// });

		TextArea ta = new TextArea();
		ta.setCharacterWidth(50);
		ta.setVisibleLines(5);


		if (ClientsideSettings.getUserProfile() != null) {
			logger.info("Result: " + ClientsideSettings.getUserProfile().getUserName());
		} else {
			logger.info("Result: NULL");
		}

		
		
		FlexTable t = new FlexTable();
		
		
		t.setText(0, 0, "Username");
		t.setWidget(0,1,tbun);
		Label example = new Label("Example: Tngly32");
		example.setStyleName("username-Example");
		t.setWidget(1, 1, example);
		
		t.setText(2,0,"First Name");
		
		if (ClientsideSettings.getUserProfile() == null) {
			t.setWidget(2,1,tbfn);
		} else {
			tbfn.setText(ClientsideSettings.getUserProfile().getName());
			t.setWidget(2,1,tbfn);
		}
		
		t.setText(3, 0, "Last Name");
		if (ClientsideSettings.getUserProfile() == null) {
			t.setWidget(3,1,tbn);
		} else {
			tbn.setText(ClientsideSettings.getUserProfile().getLastName());
			t.setWidget(3,1,tbn);
		}

		

		t.setText(4, 0, "Gender");
		if (ClientsideSettings.getSearchProfile() == null) {
			t.setWidget(4, 1, genderBox);
		} else {
			int index;
			if (ClientsideSettings.getSearchProfile().getGender() == "Male") {
				index = 1;
			} else {
				index = 0;
			}
			genderBox.setItemSelected(index, true);
			t.setWidget(4, 1, genderBox);
		}  
		
		t.setText(5, 0, "Date of Birth");
		if (ClientsideSettings.getUserProfile() == null) {
		t.setWidget(5,1,datePicker);
		} else {
			datePicker.setValue(ClientsideSettings.getUserProfile().getDateOfBirth());
			t.setWidget(5,1,datePicker);
		}
		

		t.setText(6,0, "Body Height");
		if (ClientsideSettings.getUserProfile() == null) {
			t.setWidget(6,1,tbbh);
		} else {
			tbbh.setText(Float.toString(ClientsideSettings.getUserProfile().getBodyHeight()));
			t.setWidget(6,1,tbbh);
		}

		t.setText(7, 0, "Haircolor");;
		if (ClientsideSettings.getUserProfile() == null) {
			t.setWidget(7,1,hairColourList);
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
			t.setWidget(7,1,hairColourList);
		}

		t.setText(8, 0, "Smoker");
		if (ClientsideSettings.getUserProfile() == null) {
			t.setWidget(8, 1, isSmokingBox);
		} else {
			isSmokingBox.setItemSelected(ClientsideSettings.getUserProfile().getIsSmoking(), true);
			t.setWidget(8, 1, isSmokingBox);
		}

		t.setText(9, 0, "Confession");
		if (ClientsideSettings.getUserProfile() == null) {
		t.setWidget(9, 1, confessionBox);
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
			t.setWidget(9, 1, confessionBox);
		}
		
		t.setText(10,0, "Hobbies");
//		if (ClientsideSettings.getUserProfile() == null) {
//		t.setWidget(10, 1, myHobbiesSelect);
//		} else {
//			int index;
//			if (ClientsideSettings.getUserProfile().getHairColour() == "Soccer") {
//				index = 0;
//			} else if (ClientsideSettings.getUserProfile().getHairColour() == "Baseball") {
//				index = 1;
//			} else if (ClientsideSettings.getUserProfile().getHairColour() == "Volleyball") {
//				index = 2;
//			} else if (ClientsideSettings.getUserProfile().getHairColour() == "Basketball") {
//				index = 3;
//			} else if (ClientsideSettings.getUserProfile().getHairColour() == "Golf") {
//				index = 4;
//			} else {
//				index = 5;
//			}
//			myHobbiesSelect.setItemSelected(index, true);
//			t.setWidget(10, 1, myHobbiesSelect);
//			
//					
//		}
//		
		
		final CheckBox chkSoccer = new CheckBox();
		final CheckBox chkBaseball = new CheckBox();
		final CheckBox chkVolleyball = new CheckBox();
		final CheckBox chkBasketball = new CheckBox();
		final CheckBox chkGolf = new CheckBox();
		
		
		FlexTable t2 = new FlexTable();
		FlexTable t4 = new FlexTable();
		
		t2.setText(0, 0, "Soccer");
		t2.setWidget(0, 1, chkSoccer);
		
		t2.setText(1, 0, "Baseball");
		t2.setWidget(1, 1, chkBaseball);
		
		t2.setText(2, 0, "Volleyball");
		t2.setWidget(2, 1, chkVolleyball);
		
		t2.setText(3, 0, "Basketball");
		t2.setWidget(3, 1, chkBasketball);
		
		t2.setText(4, 0, "Golf");
		t2.setWidget(4, 1, chkGolf);
		
		t.setWidget(10, 1, t2);
		
		FlexTable t3 = new FlexTable();
		
		t.setText(11, 0, "This is how I describe myself");
		t.setWidget(11, 1, ta);
		
		final TextBox tBb = new TextBox();
        final TextBox tBm = new TextBox();
		
		
		t3.setText(0,0, "Favourite Band(s)");
		t3.setWidget(0, 1, tBb);
		
		
		
		t3.setText(1,0, "Favourite Movie(s)");
		t3.setWidget(1, 1, tBm);
		
		verPanel.add(t);
		
		verPanel2.add(t3);
		
		HorizontalPanel horPanel = new HorizontalPanel();
		horPanel.add(verPanel);
		horPanel.add(verPanel2);
		
		RootPanel.get("Details").add(horPanel);
		
		final Button saveProfilButton = new Button("Save");
		saveProfilButton.setStyleName("tngly-button");
		t.setWidget(12, 1, saveProfilButton);
			

		saveProfilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
//				final String symbol = tbfn.getText().toUpperCase().trim();
//				if (!symbol.matches("^[0-9A-Z\\.]{1,10}$")) {
//				Window.alert("'" + symbol + "' is not a valid symbol.");
//				tbfn.selectAll();
//				return;
//				}
//				
//				final String symbol1 = tbn.getText().toUpperCase().trim();
//				if (!symbol1.matches("^[0-9A-Z\\.]{1,10}$")) {
//				Window.alert("'" + symbol1 + "' is not a valid symbol.");
//				tbn.selectAll();
//				return;
//				}
//				
//				final String symbol2 = tbbh.getText().toUpperCase().trim();
//				if (!symbol2.matches("^[0-9A-Z\\.]{1,10}$")) {
//				Window.alert("'" + symbol2 + "' is not a valid symbol.");
//				tbbh.selectAll();
//				return;
//				}
				
//				 if (ClientsideSettings.getUserProfile() == null){
//				 Window.alert("Bitte f�llen Sie alle Felder aus");
//				 }

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

				temp.setMovie(tBm.getText());
				
				temp.setBand(tBb.getText());
				
				
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
