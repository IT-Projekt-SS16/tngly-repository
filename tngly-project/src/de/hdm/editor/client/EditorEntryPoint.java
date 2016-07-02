package de.hdm.editor.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.client.LoginService;
import de.hdm.core.client.LoginServiceAsync;
import de.hdm.core.shared.LoginInfo;

/**
 * EntryPoint Klasse für den Editor Client. Initialisierung der Navigation und
 * Überprüfung des eingeloggten Users mit der Datenbank, ob der Benutzer bereits
 * in der Datenbank gespeichert ist.
 * 
 * @author Kevin Jaeger
 */
public class EditorEntryPoint implements EntryPoint {

	/**
	 * Der LoginService ermöglicht die asynchrone Kommunikation mit der
	 * Applikationslogik.
	 */
	private final LoginServiceAsync loginService = GWT.create(LoginService.class);

	/**
	 * Die Instanz von LoginInfo dient als Hilfsklasse für das Login und stellt
	 * erforderliche Variablen und Operationen bereit.
	 */
	private LoginInfo loginInfo = null;

	/**
	 * Deklaration, Definition und Initialisierung der Widgets.
	 */
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Please sign in to your Google Account to access the Editor module.");
	private Anchor signInLink = new Anchor("Sign In");

	private VerticalPanel verPanel = new VerticalPanel();
	private final Button profileButton = new Button("MY PROFILE");
	private final Button editProfileButton = new Button("EDIT PROFILE");
	private final Button wishlistButton = new Button("WISHLIST");
	private final Button banlistButton = new Button("BANLIST");
	private final Button searchProfilButton = new Button("SEARCH PARTNER");
	private final Button signOutButton = new Button("SIGN OUT");
	private final Button imprintButton = new Button("IMPRINT");
	private final DialogBox dialogBox = createDialogBox();

	/**
	 * Die Implementierung des Interface, um der Klasse zu ermöglichen, als
	 * EntryPoint des Modules zu laden.
	 */
	@Override
	public void onModuleLoad() {

		/**
		 * Der AsyncCallback für die Anmeldung des Benutzers. Die
		 * Benutzerinformationen werden mithilfe der LoginInfo ausgegeben.
		 */
		loginService.login(GWT.getHostPageBaseURL() + "Editor.html", new AsyncCallback<LoginInfo>() {

			@Override
			public void onFailure(Throwable error) {
			}

			@Override
			public void onSuccess(LoginInfo result) {

				loginInfo = result;
				ClientsideSettings.setLoginInfo(result);

				if (loginInfo.isLoggedIn()) {
					loadEditor();
				} else {
					loadLogin();
				}
			}
		});
	}

	/**
	 * Laden der Ansicht des Clients "Editor".
	 */
	private void loadEditor() {

		/*
		 * Zuweisung von Styles an die jeweiligen Widgets.
		 */
		dialogBox.setGlassEnabled(true);
		dialogBox.setAnimationEnabled(true);
		editProfileButton.setStylePrimaryName("tngly-submenubutton");
		wishlistButton.setStylePrimaryName("tngly-submenubutton");
		banlistButton.setStylePrimaryName("tngly-submenubutton");
		searchProfilButton.setStylePrimaryName("tngly-menubutton");
		signOutButton.setStylePrimaryName("tngly-menubutton-signout");
		profileButton.setStylePrimaryName("tngly-menubutton");
		imprintButton.setStylePrimaryName("tngly-submenubutton");
		verPanel.setStylePrimaryName("tngly-navigation");

		/*
		 * Zuweisung des jeweiligen Child Widget zum Parent Widget.
		 */
		verPanel.add(profileButton);
		verPanel.add(editProfileButton);
		verPanel.add(wishlistButton);
		verPanel.add(banlistButton);
		verPanel.add(searchProfilButton);
		verPanel.add(signOutButton);
		verPanel.add(imprintButton);
		RootPanel.get("Navigator").add(verPanel);

		/*
		 * Zuweisung der neuen Ansicht zum Parent Widget.
		 */
		Update update = new EditProfileView();
		RootPanel.get("Details").clear();
		RootPanel.get("Details").add(update);

		/*
		 * Zuweisung der ClickHandler an die jeweiligen Buttons.
		 */
		profileButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				/*
				 * Zuweisung von Styles an die jeweiligen Widgets.
				 */
				profileButton.setStylePrimaryName("tngly-menubutton-active");
				editProfileButton.setStylePrimaryName("tngly-submenubutton");
				wishlistButton.setStylePrimaryName("tngly-submenubutton");
				banlistButton.setStylePrimaryName("tngly-submenubutton");
				searchProfilButton.setStylePrimaryName("tngly-menubutton");
				signOutButton.setStylePrimaryName("tngly-menubutton-signout");
				imprintButton.setStylePrimaryName("tngly-submenubutton");

				/*
				 * Zuweisung der neuen Ansicht zum Parent Widget.
				 */
				Update update = new ProfileView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		});

		wishlistButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				/*
				 * Zuweisung von Styles an die jeweiligen Widgets.
				 */
				wishlistButton.setStylePrimaryName("tngly-submenubutton-active");
				editProfileButton.setStylePrimaryName("tngly-submenubutton");
				banlistButton.setStylePrimaryName("tngly-submenubutton");
				searchProfilButton.setStylePrimaryName("tngly-menubutton");
				signOutButton.setStylePrimaryName("tngly-menubutton-signout");
				profileButton.setStylePrimaryName("tngly-menubutton");
				imprintButton.setStylePrimaryName("tngly-submenubutton");

				/*
				 * Zuweisung der neuen Ansicht zum Parent Widget.
				 */
				Update update = new WishlistCTView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		});

		searchProfilButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				/*
				 * Zuweisung von Styles an die jeweiligen Widgets.
				 */
				searchProfilButton.setStylePrimaryName("tngly-menubutton-active");
				editProfileButton.setStylePrimaryName("tngly-submenubutton");
				wishlistButton.setStylePrimaryName("tngly-submenubutton");
				banlistButton.setStylePrimaryName("tngly-submenubutton");
				signOutButton.setStylePrimaryName("tngly-menubutton-signout");
				profileButton.setStylePrimaryName("tngly-menubutton");
				imprintButton.setStylePrimaryName("tngly-submenubutton");

				/*
				 * Zuweisung der neuen Ansicht zum Parent Widget.
				 */
				Update update = new SearchByProfileView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		});

		banlistButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				/*
				 * Zuweisung von Styles an die jeweiligen Widgets.
				 */
				banlistButton.setStylePrimaryName("tngly-submenubutton-active");
				editProfileButton.setStylePrimaryName("tngly-submenubutton");
				wishlistButton.setStylePrimaryName("tngly-submenubutton");
				searchProfilButton.setStylePrimaryName("tngly-menubutton");
				signOutButton.setStylePrimaryName("tngly-menubutton-signout");
				profileButton.setStylePrimaryName("tngly-menubutton");
				imprintButton.setStylePrimaryName("tngly-submenubutton");

				/*
				 * Zuweisung der neuen Ansicht zum Parent Widget.
				 */
				Update update = new BanCTView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		});

		editProfileButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				/*
				 * Zuweisung von Styles an die jeweiligen Widgets.
				 */
				editProfileButton.setStylePrimaryName("tngly-submenubutton-active");
				wishlistButton.setStylePrimaryName("tngly-submenubutton");
				banlistButton.setStylePrimaryName("tngly-submenubutton");
				searchProfilButton.setStylePrimaryName("tngly-menubutton");
				signOutButton.setStylePrimaryName("tngly-menubutton-signout");
				profileButton.setStylePrimaryName("tngly-menubutton");
				imprintButton.setStylePrimaryName("tngly-submenubutton");

				/*
				 * Zuweisung der neuen Ansicht zum Parent Widget.
				 */
				Update update = new EditProfileView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		});

		signOutButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				/*
				 * Laden der Logout-URL der Google Accounts API und Anzeige des
				 * LoginPanel.
				 */
				Window.open(ClientsideSettings.getLoginInfo().getLogoutUrl(), "_self", "");
			}
		});

		imprintButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialogBox.center();
				dialogBox.show();
			}
		});
	}

	/**
	 * Erstellt eine DialogBox als Impressum und gibt diese zurück.
	 * 
	 * @return Eine DialogBox als Impressum aufbereitet
	 */
	private DialogBox createDialogBox() {
		/*
		 * Instanziierung einer DialogBox und Setzung der Überschrift dieser
		 * DialogBox.
		 */
		final DialogBox dialogBox = new DialogBox();
		dialogBox.ensureDebugId("cwDialogBox");
		dialogBox.setText("Imprint");

		/*
		 * Instanziierung eines vertikalen Panel, um den Inhalt der DialogBox zu
		 * formatieren.
		 */
		VerticalPanel dialogContents = new VerticalPanel();
		dialogContents.setSpacing(4);
		dialogBox.setWidget(dialogContents);

		/*
		 * Setzung des Impressumstext als HTML in die DialogBox.
		 */
		HTML aboutHTML = new HTML();
		String about = "IT-Projekt SS 2016<br>" + "Tingly Partnerboerse<br>" + "Hochschule der Medien Stuttgart<br>"
				+ "Team 10<br>"
				+ "Philipp Schmitt (27940)<br> Kevin Jaeger (27942)<br> Dominik Dach (27932)<br> Lorena Esposito (27981)<br> Marius Klepser (27989)<br> Esra Simsek (26497)<br>";
		aboutHTML.setHTML(about);
		dialogContents.add(aboutHTML);
		dialogContents.setCellHorizontalAlignment(aboutHTML, HasHorizontalAlignment.ALIGN_CENTER);

		Button closeButton = new Button("Close", new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}
		});

		/*
		 * Hinzufügen einer Schliessen-Schaltfläche am Ende der DialogBox.
		 */
		dialogContents.add(closeButton);
		dialogContents.setCellHorizontalAlignment(closeButton, HasHorizontalAlignment.ALIGN_CENTER);

		return dialogBox;
	}

	/**
	 * Laden des LoginPanel für die Anmeldung des Benutzers.
	 */
	private void loadLogin() {

		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("Details").clear();
		RootPanel.get("Details").add(loginPanel);
	}
}
