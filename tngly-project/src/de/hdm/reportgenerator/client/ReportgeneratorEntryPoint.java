package de.hdm.reportgenerator.client;

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
 * EntryPoint Klasse fuer den ReportGenerator Client. Initialisierung der
 * Navigation und Ueberpruefung des eingeloggten Users mit der Datenbank, ob der
 * Benutzer bereits in der Datenbank gespeichert ist.
 * 
 * @author Kevin Jaeger
 */
public class ReportgeneratorEntryPoint implements EntryPoint {

	/**
	 * Der LoginService ermoeglicht die asynchrone Kommunikation mit der
	 * Applikationslogik.
	 */
	LoginServiceAsync loginService = GWT.create(LoginService.class);

	/**
	 * Die Instanz von LoginInfo dient als Hilfsklasse fuer das Login und stellt
	 * erforderliche Variablen und Operationen bereit.
	 */
	private LoginInfo loginInfo = null;

	/**
	 * Deklaration, Definition und Initialisierung der Widgets.
	 */
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Please sign in to your Google Account to access the REPORTGENERATOR module.");
	private Anchor signInLink = new Anchor("Sign In");

	private VerticalPanel verPanel = new VerticalPanel();
	private final Button signOutButton = new Button("SIGN OUT");
	private final Button imprintButton = new Button("IMPRINT");
	private final DialogBox dialogBox = createDialogBox();

	@Override
	public void onModuleLoad() {

		loginService.login(GWT.getHostPageBaseURL() + "Reportgenerator.html", new AsyncCallback<LoginInfo>() {

			public void onFailure(Throwable error) {
			}

			public void onSuccess(LoginInfo result) {
				loginInfo = result;
				ClientsideSettings.setLoginInfo(result);

				if (loginInfo.isLoggedIn()) {
					loadReportgenerator();
				} else {
					loadLogin();
				}
			}
		});
	}

	/**
	 * Baut den Reportgenerator mit entsprechender Navigation auf.
	 */
	private void loadReportgenerator() {

		/*
		 * Zuweisung von Styles an die jeweiligen Widgets.
		 */
		dialogBox.setGlassEnabled(true);
		dialogBox.setAnimationEnabled(true);
		signOutButton.setStylePrimaryName("tngly-menubutton-signout");
		imprintButton.setStylePrimaryName("tngly-submenubutton");
		verPanel.setStylePrimaryName("tngly-navigation");

		/*
		 * Zuweisung des jeweiligen Child Widget zum Parent Widget.
		 */
		verPanel.add(signOutButton);
		verPanel.add(imprintButton);
		RootPanel.get("Navigator").add(verPanel);

		/*
		 * Zuweisung der neuen Ansicht zum Parent Widget.
		 */
		UpdateReportGenerator update = new SearchByProfileViewR();
		RootPanel.get("Details").clear();
		RootPanel.get("Details").add(update);

		/*
		 * Zuweisung der ClickHandler an die jeweiligen Buttons.
		 */
		signOutButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
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
	 * Erstellt eine DialogBox als Impressum und gibt diese zurueck.
	 * 
	 * @return Eine DialogBox als Impressum aufbereitet
	 */
	private DialogBox createDialogBox() {
		/*
		 * Instanziierung einer DialogBox und Setzung der Ueberschrift dieser
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
		 * Hinzufuegen einer Schliessen-Schaltflaeche am Ende der DialogBox.
		 */
		dialogContents.add(closeButton);
		dialogContents.setCellHorizontalAlignment(closeButton, HasHorizontalAlignment.ALIGN_CENTER);

		return dialogBox;
	}

	/**
	 * Baut das Login-Panel zur Anmeldung fuer den Benutzer auf.
	 */
	private void loadLogin() {
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("Details").clear();
		RootPanel.get("Details").add(loginPanel);
	}
}
