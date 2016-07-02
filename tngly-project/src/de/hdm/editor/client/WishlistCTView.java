package de.hdm.editor.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.shared.AdministrationServiceAsync;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.Wish;

public class WishlistCTView extends Update {

	private AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();

	private HorizontalPanel hPanel = new HorizontalPanel();

	private Profile currentUserProfile;

	Logger logger = ClientsideSettings.getLogger();

	private ListDataProvider<Profile> dataProvider = new ListDataProvider<Profile>();

	private CellTable<Profile> cellTable = new CellTable<Profile>();
	private ListHandler<Profile> sortHandler = new ListHandler<Profile>(dataProvider.getList());

	// Add a selection model so we can select cells.
	private final MultiSelectionModel<Profile> selectionModel = new MultiSelectionModel<Profile>(null);

	private final Button unwishProfileButton = new Button("Unwish selected profiles");

	HTML horLine = new HTML("<hr  style=\"width:100%;\" />");

	HTML horLine2 = new HTML("<hr  style=\"width:100%;\" />");

	public WishlistCTView() {
		// this.searchProfile = searchProfile;
	}

	@Override
	protected String getHeadlineText() {
		return "Your wishes";
	}

	@Override
	protected void run() {

		adminService.getWishes(getWishesCallback());

		int atIndex = ClientsideSettings.getLoginInfo().getEmailAddress().indexOf("@");
		adminService.getProfileByUserName(ClientsideSettings.getLoginInfo().getEmailAddress().substring(0, atIndex),
				getCurrentUserProfileCallback());

		hPanel.setBorderWidth(0);
		hPanel.setSpacing(0);
		hPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		ClientsideSettings.getLogger().info("Buttons werden aufgebaut");

		unwishProfileButton.setStylePrimaryName("tngly-ctvbutton");

		cellTable.setWidth("100%", true);

		// Do not refresh the headers and footers every time the data is
		// updated.
		cellTable.setAutoHeaderRefreshDisabled(true);

		// Attach a column sort handler to the ListDataProvider to sort the
		// list.
		cellTable.addColumnSortHandler(sortHandler);
		cellTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Profile>createCheckboxManager());

		// Initialize the columns.
		initTableColumns(selectionModel, sortHandler);

		// Add the CellList to the adapter in the database.
		addDataDisplay(cellTable);

		// Create a Pager to control the table.
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setDisplay(cellTable);

		RootPanel.get("Details").add(horLine);
		RootPanel.get("Details").add(unwishProfileButton);
		RootPanel.get("Details").add(horLine2);
		RootPanel.get("Details").add(hPanel);
		RootPanel.get("Details").add(cellTable);
		RootPanel.get("Details").add(pager);

		unwishProfileButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				unwishProfileButton.setEnabled(false);
				unwishProfileButton.setStylePrimaryName("tngly-disabledButton");

				ArrayList<Profile> toUnwish = new ArrayList<Profile>(selectionModel.getSelectedSet());
				ClientsideSettings.getLogger().info("Arraylist contains: " + toUnwish.get(0).getUserName());
				ArrayList<Wish> wishesToDelete = new ArrayList<Wish>();

				for (Profile p : toUnwish) {
					Wish w = new Wish();
					w.setWishedProfileId(p.getId());
					wishesToDelete.add(w);
				}

				adminService.deleteWishes(wishesToDelete, deleteWishesCallback());
				refreshDisplays();
				return;
			}
		});

		////////////////////////////////////////////////////////////////////////////////////////////

	}

	//////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Add a display to the database. The current range of interest of the
	 * display will be populated with data.
	 * 
	 * @param display
	 *            a {@Link HasData}.
	 */
	public void addDataDisplay(HasData<Profile> display) {
		dataProvider.addDataDisplay(display);
	}

	/**
	 * Refresh all displays.
	 */
	public void refreshDisplays() {
		dataProvider.refresh();
	}

	/**
	 * Add the columns to the table.
	 */
	private void initTableColumns(final SelectionModel<Profile> selectionModel, ListHandler<Profile> sortHandler) {
		// Checkbox column. This table will uses a checkbox column for
		// selection.
		// Alternatively, you can call cellTable.setSelectionEnabled(true) to
		// enable
		// mouse selection.
		Column<Profile, Boolean> checkColumn = new Column<Profile, Boolean>(new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(Profile object) {
				// Get the value from the selection model.
				return selectionModel.isSelected(object);
			}
		};
		cellTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		cellTable.setColumnWidth(checkColumn, 40, Unit.PX);

		Column<Profile, String> clickableTextColumn = new Column<Profile, String>(new ClickableTextCell()) {

			@Override
			public String getCellStyleNames(Cell.Context context, Profile object) {
				return "tngly-userNameColumn";
			}

			@Override
			public String getValue(Profile object) {
				// Get the value from the selection model.
				return object.getUserName();
			}
		};
		clickableTextColumn.setFieldUpdater(new FieldUpdater<Profile, String>() {
			@Override
			public void update(int index, Profile object, String value) {
				// Called when the user changes the value.
				Update update = new OtherProfileView(object, "WishlistCTView", currentUserProfile);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		});
		cellTable.addColumn(clickableTextColumn, "Username");
		cellTable.setColumnWidth(clickableTextColumn, 140, Unit.PX);

		// First Name.
		Column<Profile, String> firstNameColumn = new Column<Profile, String>(new TextCell()) {
			@Override
			public String getValue(Profile object) {
				return object.getName();
			}
		};
		firstNameColumn.setSortable(true);
		firstNameColumn.setDefaultSortAscending(true);
		sortHandler.setComparator(firstNameColumn, new Comparator<Profile>() {
			@Override
			public int compare(Profile o1, Profile o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		cellTable.addColumn(firstNameColumn, "First Name");
		cellTable.setColumnWidth(firstNameColumn, 50, Unit.PCT);

		// Last Name.
		Column<Profile, String> lastNameColumn = new Column<Profile, String>(new TextCell()) {
			@Override
			public String getValue(Profile object) {
				return object.getLastName();
			}
		};
		lastNameColumn.setSortable(true);
		lastNameColumn.setDefaultSortAscending(true);
		sortHandler.setComparator(lastNameColumn, new Comparator<Profile>() {
			@Override
			public int compare(Profile o1, Profile o2) {
				return o1.getLastName().compareTo(o2.getLastName());
			}
		});
		cellTable.addColumn(lastNameColumn, "Last Name");
		cellTable.setColumnWidth(lastNameColumn, 50, Unit.PCT);

		// Gender.
		Column<Profile, String> genderColumn = new Column<Profile, String>(new TextCell()) {
			@Override
			public String getValue(Profile object) {
				return object.getGender();
			}
		};
		genderColumn.setSortable(true);
		genderColumn.setDefaultSortAscending(true);
		sortHandler.setComparator(genderColumn, new Comparator<Profile>() {
			@Override
			public int compare(Profile o1, Profile o2) {
				return o1.getGender().compareTo(o2.getGender());
			}
		});
		cellTable.addColumn(genderColumn, "Gender");
		cellTable.setColumnWidth(genderColumn, 40, Unit.PCT);

		// Age.
		Column<Profile, String> ageColumn = new Column<Profile, String>(new TextCell()) {
			@Override
			public String getValue(Profile object) {
				Date dateBirth = object.getDateOfBirth();
				Date dateNow = new Date();
				int age = dateNow.getYear() - dateBirth.getYear();
				return String.valueOf(age);
			}
		};
		ageColumn.setSortable(true);
		ageColumn.setDefaultSortAscending(true);
		sortHandler.setComparator(ageColumn, new Comparator<Profile>() {
			@Override
			public int compare(Profile o1, Profile o2) {
				Date dateBirthO1 = o1.getDateOfBirth();
				Date dateBirthO2 = o2.getDateOfBirth();
				Date dateNow = new Date();
				int ageO1 = dateNow.getYear() - dateBirthO1.getYear();
				int ageO2 = dateNow.getYear() - dateBirthO2.getYear();
				return String.valueOf(ageO1).compareTo(String.valueOf(ageO2));
			}
		});
		cellTable.addColumn(ageColumn, "Age");
		cellTable.setColumnWidth(ageColumn, 20, Unit.PCT);

		// Haircolour.
		Column<Profile, String> haircolorColumn = new Column<Profile, String>(new TextCell()) {
			@Override
			public String getValue(Profile object) {
				return object.getHairColour();
			}
		};
		haircolorColumn.setSortable(true);
		haircolorColumn.setDefaultSortAscending(true);
		sortHandler.setComparator(haircolorColumn, new Comparator<Profile>() {
			@Override
			public int compare(Profile o1, Profile o2) {
				return o1.getHairColour().compareTo(o2.getHairColour());
			}
		});
		cellTable.addColumn(haircolorColumn, "Haircolour");
		cellTable.setColumnWidth(haircolorColumn, 40, Unit.PCT);

		// Smoker.
		Column<Profile, String> smokerColumn = new Column<Profile, String>(new TextCell()) {
			@Override
			public String getValue(Profile object) {
				if (object.getIsSmoking() == 0) {
					return "NO";
				} else {
					return "YES";
				}
			}
		};
		smokerColumn.setSortable(true);
		smokerColumn.setDefaultSortAscending(true);
		sortHandler.setComparator(smokerColumn, new Comparator<Profile>() {
			@Override
			public int compare(Profile o1, Profile o2) {
				int smoker01 = o1.getIsSmoking();
				int smoker02 = o2.getIsSmoking();
				return String.valueOf(smoker01).compareTo(String.valueOf(smoker02));
			}
		});
		cellTable.addColumn(smokerColumn, "Smoker");
		cellTable.setColumnWidth(smokerColumn, 40, Unit.PCT);

		// Confession.
		Column<Profile, String> confessionColumn = new Column<Profile, String>(new TextCell()) {
			@Override
			public String getValue(Profile object) {
				return object.getConfession();
			}
		};
		confessionColumn.setSortable(true);
		confessionColumn.setDefaultSortAscending(true);
		sortHandler.setComparator(confessionColumn, new Comparator<Profile>() {
			@Override
			public int compare(Profile o1, Profile o2) {
				return o1.getConfession().compareTo(o2.getConfession());
			}
		});
		cellTable.addColumn(confessionColumn, "Confession");
		cellTable.setColumnWidth(confessionColumn, 40, Unit.PCT);
	}

	private AsyncCallback<ArrayList<Profile>> getWishesCallback() {
		AsyncCallback<ArrayList<Profile>> asyncCallback = new AsyncCallback<ArrayList<Profile>>() {

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
			}

			@Override
			public void onSuccess(ArrayList<Profile> result) {
				ClientsideSettings.getLogger()
						.severe("Success GetWishesCallback: " + result.getClass().getSimpleName());
				ClientsideSettings.getLogger().severe("+ id des Profiles" + result.get(0).getId());
				for (Profile p : result) {
					dataProvider.getList().add(p);
				}
			}
		};
		return asyncCallback;
	}

	private AsyncCallback<Void> deleteWishesCallback() {
		AsyncCallback<Void> asyncCallback = new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {

				Update update = new WishlistCTView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
				ClientsideSettings.getLogger().info("ProfileWish wurde entfernt");

			}
		};
		return asyncCallback;
	}

	private AsyncCallback<Profile> getCurrentUserProfileCallback() {
		AsyncCallback<Profile> asyncCallback = new AsyncCallback<Profile>() {

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Profile result) {
				ClientsideSettings.getLogger()
						.severe("Success GetCurrentUserProfileCallback: " + result.getClass().getSimpleName());
				currentUserProfile = result;
			}
		};
		return asyncCallback;
	}
}