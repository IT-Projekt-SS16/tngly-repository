package de.hdm.editor.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
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
import de.hdm.core.shared.bo.ProfileVisit;
import de.hdm.core.shared.bo.SearchProfile;

/**
 * Diese View Klasse stellt die Suchergebnisse anhand eines Suchprofils mithilfe
 * einer Tabelle für den Benutzer dar. Der Benutzer hat die Wahl, über die
 * Tabelle in das ausgewählte Profil oder zurück zur Suchansicht zu springen.
 * 
 * @author Kevin Jaeger, Philipp Schmitt
 *
 */
public class ShowProfilesCTView extends Update {

	/**
	 * Die AdministrationService ermöglicht die asynchrone Kommunikation mit der
	 * Applikationslogik.
	 */
	private AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();

	/**
	 * Die Instanz des aktuellen Benutzers ermöglicht den schnellen Zugriff auf
	 * dessen Profileigenschaften.
	 */
	private Profile currentUserProfile = new Profile();

	/**
	 * Die Instanz des Suchprofils ermöglicht den schnellen Zugriff auf dessen
	 * Kriterien.
	 */
	private SearchProfile searchProfile = null;

	/**
	 * Instanziierung des Tabellen Widgets zur Darstellung von Benutzerprofilen.
	 */
	private CellTable<Profile> cellTable = new CellTable<Profile>();

	/**
	 * Instanziierung des DataProviders, der die Profilwerte für das Tabellen
	 * Widget bereithält.
	 */
	private ListDataProvider<Profile> dataProvider = new ListDataProvider<Profile>();

	/**
	 * Instanziierung des Handlers, der die Profilwerte für das Tabellen Widget
	 * sortiert.
	 */
	private ListHandler<Profile> sortHandler = new ListHandler<Profile>(dataProvider.getList());

	/**
	 * Instanziierung des SelectionModel, welches die Auswahl von Profilwerten
	 * im Tabellen Widget unterstützt.
	 */
	private final MultiSelectionModel<Profile> selectionModel = new MultiSelectionModel<Profile>(null);

	/**
	 * Instanziierung des Pagers, der die Kontrolle über das Tabellen Widget
	 * unterstützt.
	 */
	SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);

	/**
	 * Deklaration, Definition und Initialisierung aller relevanten
	 * Eingabemöglichkeiten, wie: Widgets zur Gestaltung der View, wie:
	 * HorizontalPanel, Trennlinien und Widgets zur Ablaufsteuerung, wie:
	 * Buttons
	 */
	private HorizontalPanel hPanel = new HorizontalPanel();

	HTML horLine = new HTML("<hr  style=\"width:100%;\" />");
	HTML horLine2 = new HTML("<hr  style=\"width:100%;\" />");

	private final Button backButton = new Button("Back");

	/**
	 * Parametrisierter Konstruktor der View
	 * 
	 * @author Philipp Schmitt
	 * @param searchProfile
	 *            das Suchprofil, das vom Benutzer eingegeben wurde
	 */
	public ShowProfilesCTView(SearchProfile searchProfile) {
		this.searchProfile = searchProfile;
	}

	/**
	 * Jede View besitzt eine einleitende Überschrift, die durch diese Methode
	 * erstellt wird.
	 * 
	 * @author Peter Thies
	 * @see Update#getHeadlineText()
	 */
	@Override
	protected String getHeadlineText() {
		return "Your search results";
	}

	/**
	 * Jede View muss die <code>run()</code>-Methode implementieren. Sie ist
	 * eine "Einschubmethode", die von einer Methode der Basisklasse
	 * <code>Update</code> aufgerufen wird, wenn die View aktiviert wird.
	 * 
	 * @author Kevin Jaeger
	 * @return
	 */
	@Override
	protected void run() {

		/**
		 * Abfragen von Profilen anhand des Suchprofils vom Benutzer aus der
		 * Datenbank.
		 */
		int atIndex = ClientsideSettings.getLoginInfo().getEmailAddress().indexOf("@");
		adminService.searchAndCompareProfiles(false, searchProfile, getComparedProfileCallback());

		/**
		 * Auslesen des Profils vom aktuellen Benutzer aus der Datenbank.
		 */
		adminService.getProfileByUserName(ClientsideSettings.getLoginInfo().getEmailAddress().substring(0, atIndex),
				getCurrentUserProfileCallback());

		/*
		 * Formatierung der Panels und Widgets für die Ansicht.
		 */
		hPanel.setBorderWidth(0);
		hPanel.setSpacing(0);
		hPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		backButton.setStylePrimaryName("tngly-backbutton");
		cellTable.setWidth("100%", true);

		/*
		 * Keine erneute Aktualisierung der Header und Footer bei einer
		 * Wertänderung.
		 */
		cellTable.setAutoHeaderRefreshDisabled(true);

		/*
		 * Anhängen eines Handlers zur Spaltensortierung an den DataProvider, um
		 * die Tabelle sortieren zu können.
		 */
		cellTable.addColumnSortHandler(sortHandler);
		cellTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Profile>createCheckboxManager());

		/*
		 * Initialisierung der Tabellenspalten
		 */
		initTableColumns(selectionModel, sortHandler);

		/*
		 * Hinzufügen eines DatenAdapters zur Tabelle.
		 */
		addDataDisplay(cellTable);

		pager.setDisplay(cellTable);

		/*
		 * Zuweisung des jeweiligen Child Widget zum Parent Widget.
		 */
		hPanel.add(backButton);
		RootPanel.get("Details").add(horLine);
		RootPanel.get("Details").add(hPanel);
		RootPanel.get("Details").add(horLine2);
		RootPanel.get("Details").add(cellTable);
		RootPanel.get("Details").add(pager);

		/*
		 * Zuweisung der ClickHandler an die jeweiligen Buttons.
		 */
		backButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				backButton.setEnabled(false);
				backButton.setStylePrimaryName("tngly-disabledButton");
				Update update = new SearchByProfileView(searchProfile);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		});
	}

	/**
	 * Fügt einen Datenadapter hinzu. Die aktuelle Anzeige wird mit Werten
	 * befüllt.
	 * 
	 * @param display
	 *            a {@Link HasData}.
	 */
	public void addDataDisplay(HasData<Profile> display) {
		dataProvider.addDataDisplay(display);
	}

	/**
	 * Aktualisiert alle Datenadapter.
	 * 
	 * @return
	 */
	public void refreshDisplays() {
		dataProvider.refresh();
	}

	/**
	 * Fügt die Spalten in die Tabelle.
	 * 
	 * @param selectionModel
	 *            SelectionModel, welches die Auswahl von Profilwerten im
	 *            Tabellen Widget unterstützt
	 * @param sortHandler
	 *            Handler, der die Profilwerte für das Tabellen Widget sortiert.
	 */
	private void initTableColumns(final SelectionModel<Profile> selectionModel, ListHandler<Profile> sortHandler) {

		ButtonCell profileVisitedButton = new ButtonCell() {
			@Override
			public void render(final Context context, final SafeHtml data, final SafeHtmlBuilder sb) {
				sb.appendHtmlConstant("<button type=\"button\" class=\"profile-Visited-Button\" tabindex=\"-1\">");
				if (data != null) {
					sb.append(data);
				}
				sb.appendHtmlConstant("</button>");
			}
		};

		ButtonCell favoriteButton = new ButtonCell() {
			@Override
			public void render(final Context context, final SafeHtml data, final SafeHtmlBuilder sb) {
				sb.appendHtmlConstant("<button type=\"button\" class=\"favorite-Button\" tabindex=\"-1\">");
				if (data != null) {
					sb.append(data);
				}
				sb.appendHtmlConstant("</button>");
			}
		};

		// Profilbesuch.
		Column<Profile, String> profileVisitedColumn = new Column<Profile, String>(profileVisitedButton) {
			@Override
			public String getValue(Profile object) {
				// Get the value from the selection model.
				if (object.getWasVisited() == true) {
					return null;
				} else {
					return "â™¦";
				}
			}
		};
		cellTable.addColumn(profileVisitedColumn, "");
		cellTable.setColumnWidth(profileVisitedColumn, 35, Unit.PX);

		// Username.
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
				if (!object.getWasVisited()) {
					ArrayList<ProfileVisit> pvs = new ArrayList<ProfileVisit>();
					ProfileVisit pv = new ProfileVisit();
					pv.setVisitingProfileId(currentUserProfile.getId());
					pv.setVisitedProfileId(object.getId());
					pvs.add(pv);
					adminService.createProfileVisit(pvs, createProfileVisitCallback());
				}
				Update update = new OtherProfileView(object, "ShowProfilesCTView", currentUserProfile);
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		});
		cellTable.addColumn(clickableTextColumn, "Username");
		cellTable.setColumnWidth(clickableTextColumn, 150, Unit.PX);

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
		cellTable.addColumn(haircolorColumn, "Hair colour");
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

		// Ähnlichkeitswert.
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

		// Favorite
		Column<Profile, String> favoriteColumn = new Column<Profile, String>(favoriteButton) {
			@Override
			public String getValue(Profile object) {
				// Get the value from the selection model.
				if (object.getIsFavorite() == true) {
					return "FAVORITE";
				} else {
					return null;
				}
			}
		};
		cellTable.addColumn(favoriteColumn, "");
		cellTable.setColumnWidth(favoriteColumn, 120, Unit.PX);
	}

	/**
	 * AsyncCallback für das Abfragen von Profilen anhand eines Suchprofils aus der
	 * Datenbank.
	 * 
	 * @return Liste mit gefundenen und verglichenen Profilen
	 */
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

	/**
	 * AsyncCallback für das Auslesen vom Profil des aktuellen Benutzers aus der
	 * Datenbank.
	 * 
	 * @return Profil des aktuellen Benutzers
	 */
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

	/**
	 * AsyncCallback für das Speichern eines Profilbesuchs in die
	 * Datenbank.
	 * 
	 * @return
	 */
	private AsyncCallback<Void> createProfileVisitCallback() {
		AsyncCallback<Void> asyncCallback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				ClientsideSettings.getLogger()
						.severe("Success CreateProfileVisitCallback: " + result.getClass().getSimpleName());
			}
		};
		return asyncCallback;
	}
}