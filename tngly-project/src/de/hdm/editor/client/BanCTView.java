package de.hdm.editor.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import com.google.gwt.cell.client.Cell;
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
import de.hdm.core.shared.bo.ProfileBan;

/**
 * Diese View Klasse stellt die "blockierten" Profile des aktuellen Benutzers
 * mithilfe einer Tabelle dar. Der Benutzer hat die Wahl, ueber die Tabelle in
 * das ausgewaehlte Profil oder zurueck zur Suchansicht zu springen.
 * 
 * @author Kevin Jaeger, Philipp Schmitt
 */
public class BanCTView extends Update {

	/**
	 * Die AdministrationService ermoeglicht die asynchrone Kommunikation mit der
	 * Applikationslogik.
	 */
	private AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();

	/**
	 * Die Instanz des aktuellen Benutzers ermoeglicht den schnellen Zugriff auf
	 * dessen Profileigenschaften.
	 */
	private Profile currentUserProfile;

	/**
	 * Instanziierung des Tabellen Widgets zur Darstellung von Benutzerprofilen.
	 */
	private CellTable<Profile> cellTable = new CellTable<Profile>();

	/**
	 * Instanziierung des DataProviders, der die Profilwerte fuer das Tabellen
	 * Widget bereithaelt.
	 */
	private ListDataProvider<Profile> dataProvider = new ListDataProvider<Profile>();

	/**
	 * Instanziierung des Handlers, der die Profilwerte fuer das Tabellen Widget
	 * sortiert.
	 */
	private ListHandler<Profile> sortHandler = new ListHandler<Profile>(dataProvider.getList());

	/**
	 * Instanziierung des SelectionModel, welches die Auswahl von Profilwerten
	 * im Tabellen Widget unterstuetzt.
	 */
	private final MultiSelectionModel<Profile> selectionModel = new MultiSelectionModel<Profile>(null);

	/**
	 * Instanziierung des Pagers, der die Kontrolle ueber das Tabellen Widget
	 * unterstuetzt.
	 */
	SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);

	/**
	 * Deklaration, Definition und Initialisierung aller relevanten
	 * Eingabemoeglichkeiten, wie: Widgets zur Gestaltung der View, wie:
	 * HorizontalPanel, Trennlinien und Widgets zur Ablaufsteuerung, wie:
	 * Buttons
	 */
	private HorizontalPanel hPanel = new HorizontalPanel();
	private final Button unbanProfileButton = new Button("Unban selected profiles");

	HTML horLine = new HTML("<hr  style=\"width:100%;\" />");
	HTML horLine2 = new HTML("<hr  style=\"width:100%;\" />");

	/**
	 * No-Argument Konstruktor
	 */
	public BanCTView() {
	}

	/**
	 * Jede View besitzt eine einleitende Ueberschrift, die durch diese Methode
	 * erstellt wird.
	 * 
	 * @author Peter Thies
	 * @see Update#getHeadlineText()
	 */
	@Override
	protected String getHeadlineText() {
		return "Your bans";
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
		 * Abfragen von Kontaktsperren des aktuellen Benutzers aus der
		 * Datenbank.
		 */
		adminService.getBans(getBansCallback());

		/**
		 * Auslesen des Profils vom aktuellen Benutzer aus der Datenbank.
		 */
		int atIndex = ClientsideSettings.getLoginInfo().getEmailAddress().indexOf("@");
		adminService.getProfileByUserName(ClientsideSettings.getLoginInfo().getEmailAddress().substring(0, atIndex),
				getCurrentUserProfileCallback());

		/*
		 * Formatierung der Panels und Widgets fuer die Ansicht.
		 */
		hPanel.setBorderWidth(0);
		hPanel.setSpacing(0);
		hPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		cellTable.setWidth("100%", true);
		unbanProfileButton.setStylePrimaryName("tngly-ctvbutton");

		/*
		 * Keine erneute Aktualisierung der Header und Footer bei einer
		 * Wertaenderung.
		 */
		cellTable.setAutoHeaderRefreshDisabled(true);

		/*
		 * Anhaengen eines Handlers zur Spaltensortierung an den DataProvider, um
		 * die Tabelle sortieren zu koennen.
		 */
		cellTable.addColumnSortHandler(sortHandler);
		cellTable.setSelectionModel(selectionModel, DefaultSelectionEventManager.<Profile>createCheckboxManager());

		/*
		 * Initialisierung der Tabellenspalten
		 */
		initTableColumns(selectionModel, sortHandler);

		/*
		 * Hinzufuegen eines DatenAdapters zur Tabelle.
		 */
		addDataDisplay(cellTable);

		pager.setDisplay(cellTable);

		/*
		 * Zuweisung des jeweiligen Child Widget zum Parent Widget.
		 */
		RootPanel.get("Details").add(horLine);
		RootPanel.get("Details").add(unbanProfileButton);
		RootPanel.get("Details").add(horLine2);
		RootPanel.get("Details").add(hPanel);
		RootPanel.get("Details").add(cellTable);
		RootPanel.get("Details").add(pager);

		/*
		 * Zuweisung der ClickHandler an die jeweiligen Buttons.
		 */
		unbanProfileButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				unbanProfileButton.setEnabled(false);
				unbanProfileButton.setStylePrimaryName("tngly-disabledButton");
				ArrayList<Profile> toUnban = new ArrayList<Profile>(selectionModel.getSelectedSet());
				ClientsideSettings.getLogger().info("Arraylist contains: " + toUnban.get(0).getUserName());
				ArrayList<ProfileBan> bansToDelete = new ArrayList<ProfileBan>();
				for (Profile p : toUnban) {
					ProfileBan pb = new ProfileBan();
					pb.setBannedProfileId(p.getId());
					bansToDelete.add(pb);
				}
				adminService.deleteProfileBan(bansToDelete, deleteBansCallback());
				refreshDisplays();
				return;
			}
		});
	}

	/**
	 * Fuegt einen Datenadapter hinzu. Die aktuelle Anzeige wird mit Werten
	 * befuellt.
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
	 * Fuegt die Spalten in die Tabelle.
	 * 
	 * @param selectionModel
	 *            SelectionModel, welches die Auswahl von Profilwerten im
	 *            Tabellen Widget unterstuetzt
	 * @param sortHandler
	 *            Handler, der die Profilwerte fuer das Tabellen Widget sortiert.
	 */
	private void initTableColumns(final SelectionModel<Profile> selectionModel, ListHandler<Profile> sortHandler) {
		// CheckBox.
		Column<Profile, Boolean> checkColumn = new Column<Profile, Boolean>(new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(Profile object) {
				return selectionModel.isSelected(object);
			}
		};
		cellTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		cellTable.setColumnWidth(checkColumn, 40, Unit.PX);

		// Username.
		Column<Profile, String> clickableTextColumn = new Column<Profile, String>(new ClickableTextCell()) {
			@Override
			public String getCellStyleNames(Cell.Context context, Profile object) {
				return "tngly-userNameColumn";
			}

			@Override
			public String getValue(Profile object) {
				return object.getUserName();
			}
		};

		clickableTextColumn.setFieldUpdater(new FieldUpdater<Profile, String>() {
			@Override
			public void update(int index, Profile object, String value) {
				Update update = new OtherProfileView(object, "BanCTView", currentUserProfile);
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
	}

	/**
	 * AsyncCallback fuer das Abfragen von Kontaktsperren aus der
	 * Datenbank.
	 * 
	 * @return Liste mit gewuenschten Profilen
	 */
	private AsyncCallback<ArrayList<Profile>> getBansCallback() {
		AsyncCallback<ArrayList<Profile>> asyncCallback = new AsyncCallback<ArrayList<Profile>>() {
			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
			}

			@Override
			public void onSuccess(ArrayList<Profile> result) {
				ClientsideSettings.getLogger().severe("Success GetBansCallback: " + result.getClass().getSimpleName());
				ClientsideSettings.getLogger().severe("+ id des Profiles" + result.get(0).getId());
				for (Profile p : result) {
					dataProvider.getList().add(p);
				}
			}
		};
		return asyncCallback;
	}

	/**
	 * AsyncCallback fuer das Loeschen von Kontaktsperren aus der
	 * Datenbank.
	 * 
	 * @return Liste mit gewuenschten Profilen
	 */
	private AsyncCallback<Void> deleteBansCallback() {
		AsyncCallback<Void> asyncCallback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				Update update = new BanCTView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		};
		return asyncCallback;
	}

	/**
	 * AsyncCallback fuer das Auslesen vom Profil des aktuellen Benutzers aus der
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
}