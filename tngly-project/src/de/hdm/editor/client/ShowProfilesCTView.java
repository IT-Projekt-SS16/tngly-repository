package de.hdm.editor.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Logger;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
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
import de.hdm.core.shared.bo.SearchProfile;

public class ShowProfilesCTView extends Update {
	
	private static final Logger logger = ClientsideSettings.getLogger();

	private AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();

	private HorizontalPanel hPanel = new HorizontalPanel();

	private Profile profile = null;
	private Profile currentUserProfile = new Profile();
	private SearchProfile searchProfile = null;

	private final Button markAsSeenButton = new Button("Mark as seen");
	private final Button markAsUnseenButton = new Button("Mark as unseen");
	private final Button backButton = new Button("Back");

	private ListDataProvider<Profile> dataProvider = new ListDataProvider<Profile>();

	private CellTable<Profile> cellTable = new CellTable<Profile>();
	private ListHandler<Profile> sortHandler = new ListHandler<Profile>(dataProvider.getList());

	// Add a selection model so we can select cells.
	private final MultiSelectionModel<Profile> selectionModel = new MultiSelectionModel<Profile>(null);

	public ShowProfilesCTView(SearchProfile searchProfile) {
		this.searchProfile = searchProfile;
	}

	@Override
	protected String getHeadlineText() {
		return "";
	}

	@Override
	protected void run() {
		
		int atIndex = ClientsideSettings.getLoginInfo().getEmailAddress().indexOf("@");
		adminService.getProfileByUserName(ClientsideSettings.getLoginInfo().getEmailAddress().substring(0, atIndex),
				getCurrentUserProfileCallback());
		adminService.searchAndCompareProfiles(false, searchProfile, getComparedProfileCallback());

		hPanel.setBorderWidth(0);
		hPanel.setSpacing(0);
		hPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		hPanel.add(backButton);

		ClientsideSettings.getLogger().info("Buttons werden aufgebaut");

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

		RootPanel.get("Details").add(hPanel);
		RootPanel.get("Details").add(cellTable);
		RootPanel.get("Details").add(pager);
		
		backButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
		        	Update update = new SearchByProfileView();
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(update);
					logger.info("Erfolgreich View geswitcht.");
			}
		});
	}

		
	
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
//		Column<Profile, Boolean> checkColumn = new Column<Profile, Boolean>(new CheckboxCell(true, false)) {
//			@Override
//			public Boolean getValue(Profile object) {
//				// Get the value from the selection model.
//				return selectionModel.isSelected(object);
//			}
//		};
//		cellTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
//		cellTable.setColumnWidth(checkColumn, 40, Unit.PX);

		Column<Profile, String> clickableTextColumn = new Column<Profile, String>(new ClickableTextCell()) {
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
				Update update = new OtherProfileView(object, "ShowProfilesCTView", currentUserProfile);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		});
		cellTable.addColumn(clickableTextColumn, "Username");
		cellTable.setColumnWidth(clickableTextColumn, 130, Unit.PX);

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
		cellTable.setColumnWidth(firstNameColumn, 60, Unit.PCT);

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
		cellTable.setColumnWidth(lastNameColumn, 60, Unit.PCT);

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
		cellTable.setColumnWidth(ageColumn, 30, Unit.PCT);

		// Similiarity To Reference.
		Column<Profile, String> similiarityColumn = new Column<Profile, String>(new TextCell()) {
			@Override
			public String getValue(Profile object) {
				return String.valueOf(object.getSimiliarityToReference()) + "%";
			}
		};
		similiarityColumn.setSortable(true);
		similiarityColumn.setDefaultSortAscending(true);
		sortHandler.setComparator(similiarityColumn, new Comparator<Profile>() {
			@Override
			public int compare(Profile o1, Profile o2) {
				int similiarityO1 = o1.getSimiliarityToReference();
				int similiarityO2 = o2.getSimiliarityToReference();
				return String.valueOf(similiarityO1).compareTo(String.valueOf(similiarityO2));
			}
		});
		cellTable.addColumn(similiarityColumn, "Similiarity");
		cellTable.setColumnWidth(similiarityColumn, 40, Unit.PCT);
		
		Column<Profile, String> favoriteColumn = new Column<Profile, String>(new ButtonCell()) {
			@Override
			public String getValue(Profile object) {
				// Get the value from the selection model.
				if (object.getIsFavorite() == true){
				return "FAVORITE";
				} else {
					return null;	
				}
			}
		};
		cellTable.addColumn(favoriteColumn, "");
		cellTable.setColumnWidth(favoriteColumn, 130, Unit.PX);
	}

	private AsyncCallback<ArrayList<Profile>> getComparedProfileCallback() {
		AsyncCallback<ArrayList<Profile>> asyncCallback = new AsyncCallback<ArrayList<Profile>>() {

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
			}

			@Override
			public void onSuccess(ArrayList<Profile> result) {
				ClientsideSettings.getLogger()
						.severe("Success GetComparedProfilesCallback: " + result.getClass().getSimpleName());
				for (Profile p : result) {
					dataProvider.getList().add(p);
				}
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
				logger.info("currentUserProfileId: " + currentUserProfile.getId());
			}
		};
		
		ClientsideSettings.getLogger().info("AsyncCallback zu Ende ausgeführt");
		return asyncCallback;
	}
}