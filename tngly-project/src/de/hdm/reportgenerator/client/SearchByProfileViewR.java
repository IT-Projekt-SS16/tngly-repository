package de.hdm.reportgenerator.client;

import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
//import com.google.gwt.event.dom.client.KeyPressEvent;
//import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
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
import de.hdm.editor.client.Update;

public class SearchByProfileViewR extends UpdateReportGenerator {

	private static final Logger logger = ClientsideSettings.getLogger();
	
	private SearchProfile searchProfile;
	
	private Boolean unseenChecked;

	private VerticalPanel verPanel = new VerticalPanel();

	private final Label lblWrongInputAgeRangeFrom = new Label(
			"Please only enter numbers between 0 and 99 in field 'From'");
	private final Label lblSmallNumberAgeRangeFrom = new Label(
			"Please enter a lower number in field 'From' than in field 'To'");
	private final Label lblWrongInputAgeRangeTo = new Label(
			"Please only enter numbers between 0 and 99 in field 'To'");
	private final Label lblSmallNumberAgeRangeTo = new Label(
			"Please enter a higher number in field 'To' than in field 'From'");

	private final Label lblWrongInputHeightRangeFrom = new Label(
			"Please only enter numbers in following pattern: '#.##' between 1.00 and 2.99");
	private final Label lblSmallNumberHeightRangeFrom = new Label(
			"Please enter a lower number in field 'From' than in field 'To'");
	private final Label lblWrongInputHeightRangeTo = new Label(
			"Please only enter numbers in following pattern: '#.##' between 1.00 and 2.99");
	private final Label lblSmallNumberHeightRangeTo = new Label(
			"Please enter a higher number in field 'To' than in field 'From'");

	private final TextBox tbAgeRangeFrom = new TextBox();
	private final TextBox tbAgeRangeTo = new TextBox();
	private final TextBox tbHeightRangeFrom = new TextBox();
	private final TextBox tbHeightRangeTo = new TextBox();

	private final CheckBox chkGenderAny = new CheckBox("Any");
	private final CheckBox chkAgeAny = new CheckBox("Any");
	private final CheckBox chkBodyHeightAny = new CheckBox("Any");
	private final CheckBox chkHairColourAny = new CheckBox("Any");
	private final CheckBox chkSmokerAny = new CheckBox("Any");
	private final CheckBox chkConfessionAny = new CheckBox("Any");
	private final CheckBox chkOnlyUnseenProfiles = new CheckBox("");

	private final ListBox hairColourList = new ListBox(false);
	private final ListBox isSmokingBox = new ListBox(false);
	private final ListBox confessionBox = new ListBox(false);
	private final ListBox genderBox = new ListBox(false);

	HTML horLine = new HTML("<hr  style=\"width:100%;\" />");

	private FlexTable t = new FlexTable();

	private final Button showProfilesButton = new Button("Search");

	/**
	 * Jeder Showcase besitzt eine einleitende Überschrift, die durch diese
	 * Methode zu erstellen ist.
	 * 
	 * @see Showcase#getHeadlineText()
	 */
	@Override
	protected String getHeadlineText() {
		return "Reportgenerator - search profile";
	}
	
	public SearchByProfileViewR()	{
		
	}
	
	public SearchByProfileViewR(SearchProfile searchProfile, Boolean unseenChecked)	{
		if (ClientsideSettings.getSearchProfile() != null)	{
			this.searchProfile = searchProfile;
			this.unseenChecked = unseenChecked;
			logger.info("90 searchProfile: " + searchProfile.toString());
		}
		
	}

	/**
	 * Jeder Showcase muss die <code>run()</code>-Methode implementieren. Sie
	 * ist eine "Einschubmethode", die von einer Methode der Basisklasse
	 * <code>ShowCase</code> aufgerufen wird, wenn der Showcase aktiviert wird.
	 */
	@Override
	protected void run() {

		logger.info("Erfolgreich Search-By-Profile-View geswitcht.");
		// logger.info(this.searchProfile.toString());

		hairColourList.setVisibleItemCount(1);
		hairColourList.addItem("Black");
		hairColourList.addItem("Brown");
		hairColourList.addItem("Red");
		hairColourList.addItem("Blonde");
		hairColourList.addItem("Dark Blonde");

		isSmokingBox.setVisibleItemCount(1);
		isSmokingBox.addItem("Yes");
		isSmokingBox.addItem("No");

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

		genderBox.setVisibleItemCount(1);
		genderBox.addItem("Female");
		genderBox.addItem("Male");

		t.setText(0, 0, "Gender");
		t.setWidget(0, 1, genderBox);

		if (this.searchProfile != null) {
			int index = 0;
			logger.info("Zeile 137 ausgeführt");
			
			if (this.searchProfile.getGender() != null )	{
			
				if (this.searchProfile.getGender() == "Male") {
					index = 1; }
				else if (this.searchProfile.getGender() == "Female") {
					index = 0; }
				genderBox.setItemSelected(index, true);
			}
			else { chkGenderAny.setValue(true);
				   genderBox.setEnabled(false);}
			
			logger.info("Zeile 144 ausgeführt");
		}

		t.setWidget(0, 4, chkGenderAny);

		t.setText(1, 0, "Age Range");
		t.setText(2, 0, "From");
		t.setWidget(2, 1, tbAgeRangeFrom);
		t.setText(2, 2, "To");
		t.setWidget(2, 3, tbAgeRangeTo);

		if (this.searchProfile != null) {
			if (searchProfile.getAgeRangeFrom() == 0 && searchProfile.getAgeRangeTo() == 0)	{
				
				chkAgeAny.setValue(true);
				tbAgeRangeFrom.setEnabled(false);
				tbAgeRangeTo.setEnabled(false);
			}
			else	{
			tbAgeRangeFrom.setText(Integer.toString(this.searchProfile.getAgeRangeFrom()));
			tbAgeRangeTo.setText(Integer.toString(this.searchProfile.getAgeRangeTo()));
			}
		}

		t.setWidget(2, 4, chkAgeAny);

		t.setText(3, 0, "Body Height (in Meter)");
		t.setWidget(4, 1, tbHeightRangeFrom);
		t.setText(4, 0, "From");
		t.setWidget(4, 3, tbHeightRangeTo);
		t.setText(4, 2, "To");

		if (this.searchProfile != null) {
			if (searchProfile.getBodyHeightFrom() == 0 && searchProfile.getBodyHeightTo() == 0 )	{
				
				chkBodyHeightAny.setValue(true);
				tbHeightRangeFrom.setEnabled(false);
				tbHeightRangeTo.setEnabled(false);
			}
			else {
			tbHeightRangeFrom.setText(Float.toString(this.searchProfile.getBodyHeightFrom()));
			tbHeightRangeTo.setText(Float.toString(this.searchProfile.getBodyHeightTo()));
			}
		}

		t.setWidget(4, 4, chkBodyHeightAny);

		t.setText(5, 0, "Haircolor");
		t.setWidget(5, 1, hairColourList);

		if (this.searchProfile != null) {
			if (this.searchProfile.getHairColour() != null) {
				int index;
				if (this.searchProfile.getHairColour() == "Black") {
					index = 0;
				} else if (this.searchProfile.getHairColour() == "Brown") {
					index = 1;
				} else if (this.searchProfile.getHairColour() == "Red") {
					index = 2;
				} else if (this.searchProfile.getHairColour() == "Blonde") {
					index = 3;
				} else {
					index = 4;
				}
				hairColourList.setItemSelected(index, true);
			}
			else {
				chkHairColourAny.setValue(true);
				hairColourList.setEnabled(false);
			}
		}

		t.setWidget(5, 4, chkHairColourAny);

		t.setText(6, 0, "Smoker");
		t.setWidget(6, 1, isSmokingBox);

		if (this.searchProfile != null) {
			if (this.searchProfile.getIsSmoking() != -1)	{
			isSmokingBox.setItemSelected(this.searchProfile.getIsSmoking(), true);
			}
			else {
				chkSmokerAny.setValue(true);
				isSmokingBox.setEnabled(false);
			}
	}

		t.setWidget(6, 4, chkSmokerAny);

		t.setText(7, 0, "Confession");
		t.setWidget(7, 1, confessionBox);

		if (this.searchProfile != null) {
			if (this.searchProfile.getConfession() != null) {

				int index;
				if (this.searchProfile.getConfession() == "Atheistic") {
					index = 0;
				} else if (this.searchProfile.getConfession() == "Buddhistic") {
					index = 1;
				} else if (this.searchProfile.getConfession() == "Evangelic") {
					index = 2;
				} else if (this.searchProfile.getConfession() == "Catholic") {
					index = 3;
				} else if (this.searchProfile.getConfession() == "Hindu") {
					index = 4;
				} else if (this.searchProfile.getConfession() == "Muslim") {
					index = 5;
				} else if (this.searchProfile.getConfession() == "Jewish") {
					index = 6;
				} else if (this.searchProfile.getConfession() == "Orthodox") {
					index = 7;
				} else {
					index = 8;
				}
				confessionBox.setItemSelected(index, true);
			}
			else {
				chkConfessionAny.setValue(true);
				confessionBox.setEnabled(false);
			}
			
		}
		
		if (unseenChecked != null)	{
			if (unseenChecked == true)	{
				chkOnlyUnseenProfiles.setValue(true);
			} else { chkOnlyUnseenProfiles.setValue(false);}
		}

		t.setWidget(7, 4, chkConfessionAny);

		lblWrongInputAgeRangeFrom.setStyleName("serverResponseLabelError");
		lblSmallNumberAgeRangeFrom.setStyleName("serverResponseLabelError");
		lblWrongInputAgeRangeTo.setStyleName("serverResponseLabelError");
		lblSmallNumberAgeRangeTo.setStyleName("serverResponseLabelError");
		lblWrongInputHeightRangeFrom.setStyleName("serverResponseLabelError");
		lblSmallNumberHeightRangeFrom.setStyleName("serverResponseLabelError");
		lblWrongInputHeightRangeTo.setStyleName("serverResponseLabelError");
		lblSmallNumberHeightRangeTo.setStyleName("serverResponseLabelError");

		t.setWidget(11, 1, showProfilesButton);
		showProfilesButton.setStyleName("tngly-bluebutton");

		t.setCellSpacing(5);
		verPanel.setSpacing(10);

		verPanel.add(t);
		RootPanel.get("Details").add(horLine);
		RootPanel.get("Details").add(verPanel);

		// Label myHobbiesLabel = new Label("Other Hobbies:");
		// verPanel.add(myHobbiesLabel);
		// verPanel.add(tbmh);

		t.setText(9, 3, "Only Unseen Profiles");
		t.setWidget(9, 2, chkOnlyUnseenProfiles);

		RootPanel.get("Details").add(showProfilesButton);

		showProfilesButton.addClickHandler(new ClickHandler() {
			public void onClick (ClickEvent event) {

				boolean genderChecked = chkGenderAny.getValue();
				boolean ageChecked = chkAgeAny.getValue();
				boolean bodyHeightChecked = chkBodyHeightAny.getValue();
				boolean hairColourChecked = chkHairColourAny.getValue();
				boolean smokerChecked = chkSmokerAny.getValue();
				boolean confessionChecked = chkConfessionAny.getValue();
				unseenChecked = chkOnlyUnseenProfiles.getValue();


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

					if (!tbAgeRangeFrom.getText().matches("[0-9]|[1-9][0-9]|[1-9]")) {
						t.setWidget(2, 6, lblWrongInputAgeRangeFrom);
						return;
					} else if (Float.valueOf(tbAgeRangeFrom.getText()) > Float.valueOf(tbAgeRangeTo.getText())) {
						t.setWidget(2, 6, lblSmallNumberAgeRangeFrom);
						return;
					} else {
						int arf = Integer.parseInt(tbAgeRangeFrom.getText());
						temp.setAgeRangeFrom(arf);
					}

					if (!tbAgeRangeTo.getText().matches("[0-9]|[1-9][0-9]|[1-9]")) {
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
