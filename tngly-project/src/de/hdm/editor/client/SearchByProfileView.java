package de.hdm.editor.client;

import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
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

public class SearchByProfileView extends Update {

	private static final Logger logger = ClientsideSettings.getLogger();

	private VerticalPanel verPanel = new VerticalPanel();

	private final Label lblWrongInputAgeRangeFrom = new Label(
			"Please only enter numbers between 16 and 99 in field 'From'");
	private final Label lblSmallNumberAgeRangeFrom = new Label(
			"Please enter a lower number in field 'From' than in field 'To'");
	private final Label lblWrongInputAgeRangeTo = new Label(
			"Please only enter numbers between 16 and 99 in field 'To'");
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
		return "Select search profile";
	}

	/**
	 * Jeder Showcase muss die <code>run()</code>-Methode implementieren. Sie
	 * ist eine "Einschubmethode", die von einer Methode der Basisklasse
	 * <code>ShowCase</code> aufgerufen wird, wenn der Showcase aktiviert wird.
	 */
	@Override
	protected void run() {

		logger.info("Erfolgreich Search-By-Profile-View geswitcht.");
		// logger.info(ClientsideSettings.getSearchProfile().toString());

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

		// if (ClientsideSettings.getSearchProfile() != null) {
		// int index;
		// if (ClientsideSettings.getSearchProfile().getGender() == "Male") {
		// index = 1;
		// } else {
		// index = 0;
		// }
		// genderBox.setItemSelected(index, true);
		// }

		t.setWidget(0, 4, chkGenderAny);

		t.setText(1, 0, "Age Range");
		t.setText(2, 0, "From");
		t.setWidget(2, 1, tbAgeRangeFrom);
		t.setText(2, 2, "To");
		t.setWidget(2, 3, tbAgeRangeTo);

		// if (ClientsideSettings.getSearchProfile() != null) {
		// tbAgeRangeFrom.setText(Integer.toString(ClientsideSettings.getSearchProfile().getAgeRangeFrom()));
		// tbAgeRangeTo.setText(Integer.toString(ClientsideSettings.getSearchProfile().getAgeRangeTo()));
		// }

		t.setWidget(2, 4, chkAgeAny);

		t.setText(3, 0, "Body Height");
		t.setWidget(4, 1, tbHeightRangeFrom);
		t.setText(4, 0, "From");
		t.setWidget(4, 3, tbHeightRangeTo);
		t.setText(4, 2, "To");

		// if (ClientsideSettings.getSearchProfile() != null) {
		// tbHeightRangeFrom.setText(Float.toString(ClientsideSettings.getSearchProfile().getBodyHeightFrom()));
		// tbHeightRangeTo.setText(Float.toString(ClientsideSettings.getSearchProfile().getBodyHeightTo()));
		// }

		t.setWidget(4, 4, chkBodyHeightAny);

		t.setText(5, 0, "Haircolor");
		t.setWidget(5, 1, hairColourList);

		// if (ClientsideSettings.getSearchProfile() != null) {
		// int index;
		// if (ClientsideSettings.getSearchProfile().getHairColour() == "Black")
		// {
		// index = 0;
		// } else if (ClientsideSettings.getSearchProfile().getHairColour() ==
		// "Brown") {
		// index = 1;
		// } else if (ClientsideSettings.getSearchProfile().getHairColour() ==
		// "Red") {
		// index = 2;
		// } else if (ClientsideSettings.getSearchProfile().getHairColour() ==
		// "Blonde") {
		// index = 3;
		// } else {
		// index = 4;
		// }
		// hairColourList.setItemSelected(index, true);
		// }

		t.setWidget(5, 4, chkHairColourAny);

		t.setText(6, 0, "Smoker");
		t.setWidget(6, 1, isSmokingBox);

		// if (ClientsideSettings.getSearchProfile() != null) {
		// isSmokingBox.setItemSelected(ClientsideSettings.getSearchProfile().getIsSmoking(),
		// true);
		// }

		t.setWidget(6, 4, chkSmokerAny);

		t.setText(7, 0, "Confession");
		t.setWidget(7, 1, confessionBox);

		// if (ClientsideSettings.getSearchProfile() != null) {
		// int index;
		// if (ClientsideSettings.getSearchProfile().getConfession() ==
		// "Atheistic") {
		// index = 0;
		// } else if (ClientsideSettings.getSearchProfile().getConfession() ==
		// "Buddhistic") {
		// index = 1;
		// } else if (ClientsideSettings.getSearchProfile().getConfession() ==
		// "Evangelic") {
		// index = 2;
		// } else if (ClientsideSettings.getSearchProfile().getConfession() ==
		// "Catholic") {
		// index = 3;
		// } else if (ClientsideSettings.getSearchProfile().getConfession() ==
		// "Hindu") {
		// index = 4;
		// } else if (ClientsideSettings.getSearchProfile().getConfession() ==
		// "Muslim") {
		// index = 5;
		// } else if (ClientsideSettings.getSearchProfile().getConfession() ==
		// "Jewish") {
		// index = 6;
		// } else if (ClientsideSettings.getSearchProfile().getConfession() ==
		// "Orthodox") {
		// index = 7;
		// } else {
		// index = 8;
		// }
		// confessionBox.setItemSelected(index, true);
		// }

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

		///////////////////////////////////////////////////////////////////////////////////
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

		////////////////////////////////////////////////////////////////////////////////

		showProfilesButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				t.setWidget(4, 5, null);
				t.setWidget(2, 5, null);

				SearchProfile temp = new SearchProfile();

				boolean genderChecked = chkGenderAny.getValue();
				boolean ageChecked = chkAgeAny.getValue();
				boolean bodyHeightChecked = chkBodyHeightAny.getValue();
				boolean hairColourChecked = chkHairColourAny.getValue();
				boolean smokerChecked = chkSmokerAny.getValue();
				boolean confessionChecked = chkConfessionAny.getValue();

				if (ageChecked == false) {

					if (!tbAgeRangeFrom.getText().matches("[1-9][0-9]")) {
						t.setWidget(2, 5, lblWrongInputAgeRangeFrom);
						return;
					} else if (Float.valueOf(tbAgeRangeFrom.getText()) > Float.valueOf(tbAgeRangeTo.getText())) {
						t.setWidget(2, 5, lblSmallNumberAgeRangeFrom);
						return;
					} else {
						int arf = Integer.parseInt(tbAgeRangeFrom.getText());
						temp.setAgeRangeFrom(arf);
					}

					if (!tbAgeRangeTo.getText().matches("[1-9][0-9]")) {
						t.setWidget(2, 5, lblWrongInputAgeRangeTo);
						return;
					} else if (Float.valueOf(tbAgeRangeTo.getText()) < Float.valueOf(tbAgeRangeFrom.getText())) {
						t.setWidget(2, 5, lblSmallNumberAgeRangeTo);
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
						t.setWidget(4, 5, lblWrongInputHeightRangeFrom);
						return;
					} else if (Float.valueOf(tbHeightRangeFrom.getText()) > Float.valueOf(tbHeightRangeTo.getText())) {
						t.setWidget(4, 5, lblSmallNumberHeightRangeFrom);
						return;
					} else {
						float bhf = Float.parseFloat(tbHeightRangeFrom.getText().trim());
						temp.setBodyHeightFrom(bhf);
					}

					if (!tbHeightRangeTo.getText().matches("^[1-2].[0-9]{1,2}$")) {
						t.setWidget(4, 5, lblWrongInputHeightRangeTo);
						return;
					} else if (Float.valueOf(tbHeightRangeTo.getText()) < Float.valueOf(tbHeightRangeFrom.getText())) {
						t.setWidget(4, 5, lblSmallNumberHeightRangeTo);
						return;
					} else {
						float bht = Float.parseFloat(tbHeightRangeTo.getText().trim());
						temp.setBodyHeightTo(bht);
					}

				} else {
					temp.setBodyHeightFrom(0f);
					temp.setBodyHeightTo(0f);
				}
				
				logger.info("bodyHeight CHECK");

				showProfilesButton.setEnabled(false);
				showProfilesButton.setStylePrimaryName("tngly-disabledButton");

				if (genderChecked == false) {
					int selectedGenderIndex = genderBox.getSelectedIndex();
					temp.setGender(genderBox.getItemText(selectedGenderIndex));
				} else {
					temp.setGender(null);
				}

				logger.info("gender CHECK");

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

				if (confessionChecked == false) {
					int selectedConfessionIndex = confessionBox.getSelectedIndex();
					temp.setConfession(confessionBox.getItemText(selectedConfessionIndex));
				} else {
					temp.setConfession(null);
				}

				logger.info("Confession CHECK");

				ClientsideSettings.setSearchProfile(temp);
				logger.info(ClientsideSettings.getSearchProfile().toString());

				Update update = new ShowProfilesCTView(temp);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
				logger.info("Erfolgreicher Reswitch.");
			}
		});

	}
}
