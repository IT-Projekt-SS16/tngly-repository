package de.hdm.reportgenerator.client;

import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.shared.bo.SearchProfile;

/**
 * Diese View Klasse für den ReportGenerator Client stellt Eingabemöglichkeiten
 * für den Benutzer zur Verfügung, um eine Suche nach Profilen und deren Ausgabe
 * anhand folgender Kriterien zu ermöglichen: Geschlecht, Alter, Körpergrösse,
 * Haarfarbe, Raucher, Konfession
 * 
 * @author Kevin Jaeger, Philipp Schmitt
 */
public class SearchByProfileViewR extends UpdateReportGenerator {

	/**
	 * Die Speicherung des Suchprofils ermöglicht den schnellen Zugriff auf die
	 * durch den Benutzer eingegebenen Kriterien.
	 */
	private SearchProfile searchProfile;

	private Boolean unseenChecked;

	/**
	 * Deklaration, Definition und Initialisierung aller relevanten
	 * Eingabemöglichkeiten, wie: Textboxen, Listboxen, Checkboxen, sowie
	 * Widgets zur Gestaltung der View, wie: VerticalPanel Und Widgets zur
	 * Ablaufsteuerung, wie: Button
	 */
	private VerticalPanel verPanel = new VerticalPanel();

	private final Label lblWrongInputAgeRangeFrom = new Label(
			"Please only enter numbers between 0 and 99 in field 'From'");
	private final Label lblSmallNumberAgeRangeFrom = new Label(
			"Please enter a lower number in field 'From' than in field 'To'");
	private final Label lblWrongInputAgeRangeTo = new Label("Please only enter numbers between 0 and 99 in field 'To'");
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
	private final CheckBox chkOnlyUnseenProfiles = new CheckBox("Only Unseen Profiles");

	private final ListBox hairColourList = new ListBox(false);
	private final ListBox isSmokingBox = new ListBox(false);
	private final ListBox confessionBox = new ListBox(false);
	private final ListBox genderBox = new ListBox(false);

	HTML horLine = new HTML("<hr  style=\"width:100%;\" />");

	private FlexTable t = new FlexTable();

	private final Button showProfilesButton = new Button("Search");

	/**
	 * Jede View besitzt eine einleitende Überschrift, die durch diese Methode
	 * erstellt wird.
	 * 
	 * @author Peter Thies
	 * @see Showcase#getHeadlineText()
	 */
	@Override
	protected String getHeadlineText() {
		return "Reportgenerator - search profile";
	}

	/**
	 * No-Argument Konstruktor
	 */
	public SearchByProfileViewR() {
	}

	/**
	 * Parametrisierter Konstruktor der View
	 * 
	 * @author Philipp Schmitt
	 * @param searchProfile
	 *            das Suchprofil, das vom Benutzer eingegeben wurde
	 * @param unseenChecked
	 *            True, wenn durch Benutzer Checkbox gesetzt (False, wenn nicht gesetzt)
	 * 
	 */
	public SearchByProfileViewR(SearchProfile searchProfile, Boolean unseenChecked) {
		this.searchProfile = searchProfile;
		this.unseenChecked = unseenChecked;
	}

	/**
	 * Jede View muss die <code>run()</code>-Methode implementieren. Sie ist
	 * eine "Einschubmethode", die von einer Methode der Basisklasse
	 * <code>Update</code> aufgerufen wird, wenn die View aktiviert wird.
	 */
	@Override
	protected void run() {
		
		/*
		 * Befüllen der Listboxen mit Werten
		 */
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

		/*
		 * Aufbau und Befüllung der FlexTables mit Werten und Widgets
		 */
		t.setText(0, 0, "Gender");
		t.setWidget(0, 1, genderBox);

		if (this.searchProfile != null) {
			int index = 0;
			if (this.searchProfile.getGender() != null) {
				if (this.searchProfile.getGender() == "Male") {
					index = 1;
				} else if (this.searchProfile.getGender() == "Female") {
					index = 0;
				}
				genderBox.setItemSelected(index, true);
			} else {
				chkGenderAny.setValue(true);
				genderBox.setEnabled(false);
			}
		}

		t.setWidget(0, 4, chkGenderAny);
		t.setText(1, 0, "Age Range");
		t.setText(2, 0, "From");
		t.setWidget(2, 1, tbAgeRangeFrom);
		t.setText(2, 2, "To");
		t.setWidget(2, 3, tbAgeRangeTo);

		if (this.searchProfile != null) {
			if (searchProfile.getAgeRangeFrom() == 0 && searchProfile.getAgeRangeTo() == 0) {

				chkAgeAny.setValue(true);
				tbAgeRangeFrom.setEnabled(false);
				tbAgeRangeTo.setEnabled(false);
			} else {
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
			if (searchProfile.getBodyHeightFrom() == 0 && searchProfile.getBodyHeightTo() == 0) {

				chkBodyHeightAny.setValue(true);
				tbHeightRangeFrom.setEnabled(false);
				tbHeightRangeTo.setEnabled(false);
			} else {
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
			} else {
				chkHairColourAny.setValue(true);
				hairColourList.setEnabled(false);
			}
		}

		t.setWidget(5, 4, chkHairColourAny);
		t.setText(6, 0, "Smoker");
		t.setWidget(6, 1, isSmokingBox);

		if (this.searchProfile != null) {
			if (this.searchProfile.getIsSmoking() != -1) {
				isSmokingBox.setItemSelected(this.searchProfile.getIsSmoking(), true);
			} else {
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
			} else {
				chkConfessionAny.setValue(true);
				confessionBox.setEnabled(false);
			}
		}

		if (unseenChecked != null) {
			if (unseenChecked == true) {
				chkOnlyUnseenProfiles.setValue(true);
			} else {
				chkOnlyUnseenProfiles.setValue(false);
			}
		}
		t.setWidget(7, 4, chkConfessionAny);
		t.setWidget(11, 1, showProfilesButton);
		t.setWidget(11, 0, chkOnlyUnseenProfiles);

		/*
		 * Zuweisung von Styles an die jeweiligen Widgets.
		 */
		lblWrongInputAgeRangeFrom.setStyleName("serverResponseLabelError");
		lblSmallNumberAgeRangeFrom.setStyleName("serverResponseLabelError");
		lblWrongInputAgeRangeTo.setStyleName("serverResponseLabelError");
		lblSmallNumberAgeRangeTo.setStyleName("serverResponseLabelError");
		lblWrongInputHeightRangeFrom.setStyleName("serverResponseLabelError");
		lblSmallNumberHeightRangeFrom.setStyleName("serverResponseLabelError");
		lblWrongInputHeightRangeTo.setStyleName("serverResponseLabelError");
		lblSmallNumberHeightRangeTo.setStyleName("serverResponseLabelError");
		showProfilesButton.setStyleName("tngly-bluebutton");
		
		/*
		 * Formatierung der Widgets für die Ansicht.
		 */
		t.setCellSpacing(5);
		verPanel.setSpacing(10);

		/*
		 * Zuweisung des jeweiligen Child Widget zum Parent Widget.
		 */
		verPanel.add(t);
		RootPanel.get("Details").add(horLine);
		RootPanel.get("Details").add(verPanel);
		RootPanel.get("Details").add(showProfilesButton);

		/*
		 * Zuweisung der ClickHandler an die jeweiligen Buttons.
		 */
		showProfilesButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				/*
				 * Auslesen von Werten der Checkboxen für die Suchkriterien
				 * "Any"
				 */
				boolean genderChecked = chkGenderAny.getValue();
				boolean ageChecked = chkAgeAny.getValue();
				boolean bodyHeightChecked = chkBodyHeightAny.getValue();
				boolean hairColourChecked = chkHairColourAny.getValue();
				boolean smokerChecked = chkSmokerAny.getValue();
				boolean confessionChecked = chkConfessionAny.getValue();
				boolean unseenChecked = chkOnlyUnseenProfiles.getValue();

				/*
				 * Instanziierung eines temporären Objekts vom Typ Suchprofil,
				 * um die eingegebenen Werte vom Benutzer an den Server zu
				 * schicken.
				 */
				SearchProfile temp = new SearchProfile();

				/*
				 * Überprüfung der Textboxen (Alter, Körpergröße) auf logische
				 * Falscheingaben bzw. formale Inkorrektheiten (bspw. Zahl in
				 * Textfeld).
				 */
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

				/*
				 * Suchausführung mit eingegebenen Werte blockieren, um
				 * Mehrfach-Klicks zu verhindern.
				 */
				showProfilesButton.setEnabled(false);
				showProfilesButton.setStylePrimaryName("tngly-disabledButton");

				/*
				 * Auslesen der eingegebenen Werte aus den Widgets in das
				 * temporäre Suchprofil.
				 */
				if (genderChecked == false) {
					int selectedGenderIndex = genderBox.getSelectedIndex();
					temp.setGender(genderBox.getItemText(selectedGenderIndex));
				} else {
					temp.setGender(null);
				}
				
				if (hairColourChecked == false) {
					int selectedHairColourIndex = hairColourList.getSelectedIndex();
					temp.setHairColour(hairColourList.getItemText(selectedHairColourIndex));
				} else {
					temp.setHairColour(null);
				}

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

				if (confessionChecked == false) {
					int selectedConfessionIndex = confessionBox.getSelectedIndex();
					temp.setConfession(confessionBox.getItemText(selectedConfessionIndex));
				} else {
					temp.setConfession(null);
				}

				/*
				 * Zuweisung der neuen Ansicht zum Parent Widget.
				 */
				UpdateReportGenerator update = new ReportView(unseenChecked, temp);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		});
	}
}