package de.hdm.reportgenerator.client;

import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
//import com.google.gwt.event.dom.client.KeyPressEvent;
//import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.shared.AdministrationServiceAsync;
import de.hdm.core.shared.ReportGeneratorAsync;
import de.hdm.core.shared.bo.SearchProfile;

public class SearchByProfileViewR extends UpdateReportGenerator {
	
	private AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();
	private ReportGeneratorAsync reportgenerator = ClientsideSettings.getReportGenerator();

	/**
	 * Jeder Showcase besitzt eine einleitende Überschrift, die durch diese
	 * Methode zu erstellen ist.
	 * 
	 * @see Showcase#getHeadlineText()
	 */
	@Override
	protected String getHeadlineText() {
		return "ReportGenerator - Search By Profiles";
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

		
		

		// logger.info(ClientsideSettings.getLoginInfo().getEmailAddress());

		// if (ClientsideSettings.getUserProfile() == null) {
		// int atIndex =
		// ClientsideSettings.getLoginInfo().getEmailAddress().indexOf("@");
		// ClientsideSettings.getAdministration().findProfileByName(
		// ClientsideSettings.getLoginInfo().getEmailAddress().substring(0,
		// atIndex), new FindCallback());
		// }

		VerticalPanel verPanel = new VerticalPanel();
		verPanel.setSpacing(10);

		HorizontalPanel horPanel = new HorizontalPanel();
		horPanel.add(verPanel);

		final TextBox tbAgeRangeFrom = new TextBox();
		final TextBox tbAgeRangeTo = new TextBox();
		final TextBox tbHeightRangeFrom = new TextBox();
		final TextBox tbHeightRangeTo = new TextBox();

		RootPanel.get("Details").add(horPanel);
		logger.info("HorizontalPanel aufgebaut.");

		final Label lblWrongInputAgeRangeFrom = new Label(
				"Please only enter numbers between 16 and 99 in field 'From'");
		final Label lblSmallNumberAgeRangeFrom = new Label(
				"Please enter a lower number in field 'From' than in field 'To'");
		final Label lblWrongInputAgeRangeTo = new Label(
				"Please only enter numbers between 16 and 99 in field 'To'");
		final Label lblSmallNumberAgeRangeTo = new Label(
				"Please enter a higher number in field 'To' than in field 'From'");

		final Label lblWrongInputHeightRangeFrom = new Label(
				"Please only enter numbers in following pattern: '#.##' between 1.00 and 2.99");
		final Label lblSmallNumberHeightRangeFrom = new Label(
				"Please enter a lower number in field 'From' than in field 'To'");
		final Label lblWrongInputHeightRangeTo = new Label(
				"Please only enter numbers in following pattern: '#.##' between 1.00 and 2.99");
		final Label lblSmallNumberHeightRangeTo = new Label(
				"Please enter a higher number in field 'To' than in field 'From'");

		final CheckBox chkGenderAny = new CheckBox();
		final CheckBox chkAgeAny = new CheckBox();
		final CheckBox chkBodyHeightAny = new CheckBox();
		final CheckBox chkHairColourAny = new CheckBox();
		final CheckBox chkSmokerAny = new CheckBox();
		final CheckBox chkConfessionAny = new CheckBox();
		final CheckBox chkOnlyUnseenProfiles = new CheckBox();

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
		genderBox.addItem("Female");
		genderBox.addItem("Male");

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


		final FlexTable t = new FlexTable();
		t.setCellSpacing (5);

		t.setText(0, 0, "Gender");
		if (ClientsideSettings.getSearchProfile() == null) {
			t.setWidget(0, 1, genderBox);
		} else {
			int index;
			if (ClientsideSettings.getSearchProfile().getGender() == "Male") {
				index = 1;
			} else {
				index = 0;
			}
			genderBox.setItemSelected(index, true);
			verPanel.add(genderBox);
		}

		t.setText(0, 4, "Any");
		t.setWidget(0, 5, chkGenderAny);

		chkGenderAny.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean genderChecked = ((CheckBox) event.getSource()).getValue();
				if (genderChecked == true) {
					genderBox.setEnabled(false);
				} else {
					genderBox.setEnabled(true);
				}
			}
		});

		if (ClientsideSettings.getSearchProfile() == null) {
			t.setText(1, 0, "Age Range");
			t.setText(2, 0, "From");
			t.setWidget(2, 1, tbAgeRangeFrom);
			t.setText(2, 2, "To");
			t.setWidget(2, 3, tbAgeRangeTo);
		} else {
			tbAgeRangeFrom.setText(Integer.toString(ClientsideSettings.getSearchProfile().getAgeRangeFrom()));
			t.setWidget(2, 1, tbAgeRangeFrom);
			tbAgeRangeTo.setText(Integer.toString(ClientsideSettings.getSearchProfile().getAgeRangeTo()));
			t.setWidget(2, 3, tbAgeRangeTo);
		}
		t.setText(2, 4, "Any");
		t.setWidget(2, 5, chkAgeAny);

		chkAgeAny.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean ageChecked = ((CheckBox) event.getSource()).getValue();
				if (ageChecked == true) {
					tbAgeRangeFrom.setEnabled(false);
					tbAgeRangeTo.setEnabled(false);
				} else {
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

		if (ClientsideSettings.getSearchProfile() == null) {
			t.setText(3, 0, "Body Height");
			t.setWidget(4, 1, tbHeightRangeFrom);
			t.setText(4, 0, "From");
			t.setWidget(4, 3, tbHeightRangeTo);
			t.setText(4, 2, "To");
		} else {
			tbHeightRangeFrom.setText(Float.toString(ClientsideSettings.getSearchProfile().getBodyHeightFrom()));
			t.setWidget(4, 1, tbHeightRangeFrom);
			tbHeightRangeTo.setText(Float.toString(ClientsideSettings.getSearchProfile().getBodyHeightTo()));
			t.setWidget(4, 3, tbHeightRangeTo);
		}
		t.setText(4, 4, "Any");
		t.setWidget(4, 5, chkBodyHeightAny);
		// verPanel.add(anyCheck);
		chkBodyHeightAny.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean bodyHeightChecked = ((CheckBox) event.getSource()).getValue();
				if (bodyHeightChecked == true) {
					tbHeightRangeFrom.setEnabled(false);
					tbHeightRangeTo.setEnabled(false);
				} else {
					tbHeightRangeFrom.setEnabled(true);
					tbHeightRangeTo.setEnabled(true);
				}
			}
		});

		t.setText(5, 0, "Haircolor");

		if (ClientsideSettings.getSearchProfile() == null) {
			t.setWidget(5, 1, hairColourList);
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
		t.setText(5, 4, "Any");
		t.setWidget(5, 5, chkHairColourAny);
		// verPanel.add(anyCheck);
		chkHairColourAny.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean hairColourChecked = ((CheckBox) event.getSource()).getValue();
				if (hairColourChecked == true) {
					hairColourList.setEnabled(false);
				} else {
					hairColourList.setEnabled(true);
				}
			}
		});

		t.setText(6, 0, "Smoker");
		if (ClientsideSettings.getSearchProfile() == null) {
			t.setWidget(6, 1, isSmokingBox);
		} else {
			isSmokingBox.setItemSelected(ClientsideSettings.getSearchProfile().getIsSmoking(), true);
			t.setWidget(6, 1, isSmokingBox);
		}
		t.setText(6, 4, "Any");
		t.setWidget(6, 5, chkSmokerAny);
		// verPanel.add(anyCheck);
		chkSmokerAny.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean smokerChecked = ((CheckBox) event.getSource()).getValue();
				if (smokerChecked == true) {
					isSmokingBox.setEnabled(false);
				} else {
					isSmokingBox.setEnabled(true);
				}
			}
		});

		t.setText(7, 0, "Confession");
		if (ClientsideSettings.getSearchProfile() == null) {
			t.setWidget(7, 1, confessionBox);
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
			t.setWidget(7, 1, confessionBox);
		}
		t.setText(7, 4, "Any");
		t.setWidget(7, 5, chkConfessionAny);
		// verPanel.add(anyCheck);
		chkConfessionAny.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean confessionChecked = ((CheckBox) event.getSource()).getValue();
				if (confessionChecked == true) {
					confessionBox.setEnabled(false);
				} else {
					confessionBox.setEnabled(true);
				}
			}
		});

		
		verPanel.add(t);
		RootPanel.get("Details").add(t);

		// Label myHobbiesLabel = new Label("Other Hobbies:");
		// verPanel.add(myHobbiesLabel);
		// verPanel.add(tbmh);

		t.setText(9, 3, "Only Unseen Profiles");
		t.setWidget(9, 2, chkOnlyUnseenProfiles);

		final Button showProfilesButton = new Button("Search");
		showProfilesButton.setStyleName("tngly-button");
		t.setWidget(9, 1, showProfilesButton);

		RootPanel.get("Details").add(showProfilesButton);

		showProfilesButton.addClickHandler(new ClickHandler() {
			public void onClick (ClickEvent event) {

				boolean genderChecked = chkGenderAny.getValue();
				boolean ageChecked = chkAgeAny.getValue();
				boolean bodyHeightChecked = chkBodyHeightAny.getValue();
				boolean hairColourChecked = chkHairColourAny.getValue();
				boolean smokerChecked = chkSmokerAny.getValue();
				boolean confessionChecked = chkConfessionAny.getValue();
				boolean unseenChecked = chkOnlyUnseenProfiles.getValue();


				Logger logger = ClientsideSettings.getLogger();
				logger.info("Erfolgreich onClick ausgefuehrt.");

				logger.info("getUserProfile war null.");
				SearchProfile temp = new SearchProfile();

				logger.info("Profile+DateTimeFormat instantiiert");

				logger.info("Name CHECK");

				logger.info("lastName CHECK");

				if (genderChecked == false) {
					int selectedGenderIndex = genderBox.getSelectedIndex();
					temp.setGender(genderBox.getItemText(selectedGenderIndex));
				} else {
					temp.setGender(null);
				}

				logger.info("gender CHECK");

				logger.info("dateOfBirth CHECK");


				if (ageChecked == false) {

					if (!tbAgeRangeFrom.getText().matches("[1-9][0-9]")) {
						t.setWidget(2, 6, lblWrongInputAgeRangeFrom);
						return;
					} else if (Float.valueOf(tbAgeRangeFrom.getText()) > Float.valueOf(tbAgeRangeTo.getText())) {
						t.setWidget(2, 6, lblSmallNumberAgeRangeFrom);
						return;
					} else {
						int arf = Integer.parseInt(tbAgeRangeFrom.getText());
						temp.setAgeRangeFrom(arf);
					}

					if (!tbAgeRangeTo.getText().matches("[1-9][0-9]")) {
						t.setWidget(2, 6, lblWrongInputAgeRangeTo);
						return;
					} else if (Float.valueOf(tbAgeRangeTo.getText()) < Float.valueOf(tbAgeRangeFrom.getText())) {
						t.setWidget(2, 6, lblSmallNumberAgeRangeTo);
						return;
					} else {
						int art = Integer.parseInt(tbAgeRangeTo.getText());
						temp.setAgeRangeTo(art);
					}

				} else {
					temp.setAgeRangeFrom(0);
					temp.setAgeRangeTo(0);
				}
				
				logger.info("Age CHECK");

				if (bodyHeightChecked == false) {

					if (!tbHeightRangeFrom.getText().matches("^[1-2].[0-9]{1,2}$")) {
						t.setWidget(4, 6, lblWrongInputHeightRangeFrom);
						return;
					} else if (Float.valueOf(tbHeightRangeFrom.getText()) > Float.valueOf(tbHeightRangeTo.getText())) {
						t.setWidget(4, 6, lblSmallNumberHeightRangeFrom);
						return;
					} else {
						float bhf = Float.parseFloat(tbHeightRangeFrom.getText().trim());
						temp.setBodyHeightFrom(bhf);
					}

					if (!tbHeightRangeTo.getText().matches("^[1-2].[0-9]{1,2}$")) {
						t.setWidget(4, 6, lblWrongInputHeightRangeTo);
						return;
					} else if (Float.valueOf(tbHeightRangeTo.getText()) < Float.valueOf(tbHeightRangeFrom.getText())) {
						t.setWidget(4, 6, lblSmallNumberHeightRangeTo);
						return;
					} else {
						float bht = Float.parseFloat(tbHeightRangeTo.getText().trim());
						temp.setBodyHeightTo(bht);
					}

				} else {
					temp.setBodyHeightFrom(0f);
					temp.setBodyHeightTo(0f);
				}
				
				showProfilesButton.setEnabled(false);
				showProfilesButton.setStylePrimaryName("tngly-disabledButton");
				
				logger.info("bodyHeight CHECK");
				logger.info("bodyHeight CHECK");

				// temp.setHairColour(tbhc.getText());
				if (hairColourChecked == false) {
					int selectedHairColourIndex = hairColourList.getSelectedIndex();
					temp.setHairColour(hairColourList.getItemText(selectedHairColourIndex));
				} else {
					temp.setHairColour(null);
				}

				logger.info("HairColour CHECK");

				if (smokerChecked == false) {
					int selectedIsSmokingIndex = isSmokingBox.getSelectedIndex();
					if (isSmokingBox.getItemText(selectedIsSmokingIndex) == "Yes") {
						temp.setIsSmoking(0);
					} else {
						temp.setIsSmoking(1);
					}
				} else {
					temp.setIsSmoking(-1);
				}

				logger.info("isSmoking CHECK");

				// temp.setConfession(tbc.getText());
				if (confessionChecked == false) {
					int selectedConfessionIndex = confessionBox.getSelectedIndex();
					temp.setConfession(confessionBox.getItemText(selectedConfessionIndex));
				} else {
					temp.setConfession(null);
				}

				logger.info("Confession CHECK");

				ClientsideSettings.setSearchProfile(temp);
				logger.info(ClientsideSettings.getSearchProfile().toString());
				
				ClientsideSettings.getLogger().info("Report AllProfiles erstellen...");

				 UpdateReportGenerator update = new AllProfilesView(unseenChecked, temp);
				
				 RootPanel.get("Details").clear();
				 RootPanel.get("Details").add(update);
				
				 logger.info("Erfolgreicher Reswitch.");

			}
		});

	}
}

// class CompareCallback implements AsyncCallback<ArrayList<Profile>> {
// @Override
// public void onFailure(Throwable caught) {
// ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
// }
//
// @Override
// public void onSuccess(ArrayList<Profile> result) {
// ClientsideSettings.getLogger().info("Profile-Liste Auf Client-Seite
// gesetzt");
// ClientsideSettings.setProfilesFoundAndCompared(result);
// ClientsideSettings.getLogger().info("Profiles Size:" +
// ClientsideSettings.getProfilesFoundAndCompared().size());
//
// if (ClientsideSettings.getUnseenOrAll()) {
// ClientsideSettings.getReportGenerator().createAllProfilesReport("Unseen", new
// AllProfilesReportCallback());
// } else {
// ClientsideSettings.getReportGenerator().createAllProfilesReport("", new
// AllProfilesReportCallback());
// ClientsideSettings.getLogger().info("Report AllProfiles erstellen...");
// }
//
//// UpdateReportGenerator update = new AllProfilesView();
//// RootPanel.get("Details").clear();
//// RootPanel.get("Details").add(update);
//// ClientsideSettings.getLogger().info("Erfolgreicher Reswitch.");
// }
// }

//class AllProfilesReportCallback implements AsyncCallback<AllProfilesReport> {
//
//	@Override
//	public void onFailure(Throwable caught) {
//		ClientsideSettings.getLogger().severe("Fehler bei der Abfrage " + caught.getMessage());
//	}
//
//	@Override
//	public void onSuccess(AllProfilesReport result) {
//		System.out.println("Callback Success");
//		HTMLReportWriter writer = new HTMLReportWriter();
//		if (result != null) {
//			writer.process(result);
//			ClientsideSettings.setAllProfilesReport(writer.getReportText());
//			ClientsideSettings.getLogger().info("Report in HTML umgewandelt");
//		}
//		UpdateReportGenerator update = new AllProfilesView(writer.getReportText());
//		RootPanel.get("Details").clear();
//		RootPanel.get("Details").add(update);
//		ClientsideSettings.getLogger().info("Erfolgreicher Reswitch.");
//	}
//}
