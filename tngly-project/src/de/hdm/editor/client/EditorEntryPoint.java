package de.hdm.editor.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.client.LoginService;
import de.hdm.core.client.LoginServiceAsync;
import de.hdm.core.shared.LoginInfo;

public class EditorEntryPoint implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final LoginServiceAsync loginService = GWT.create(LoginService.class);

	/**
	 * This is the entry point method.
	 */

	private Logger logger = Logger.getLogger("");

	private LoginInfo loginInfo = null;

	private VerticalPanel loginPanel = new VerticalPanel();

	private Label loginLabel = new Label("Please sign in to your Google Account to access the Editor module.");

	private Anchor signInLink = new Anchor("Sign In");

	private Anchor signOutLink = new Anchor("Sign Out");

	private Anchor moduleLink = new Anchor("Change to REPORT module");

	public void onModuleLoad() {

		loginService.login(GWT.getHostPageBaseURL() + "Editor.html", new AsyncCallback<LoginInfo>() {

			public void onFailure(Throwable error) {

			}

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

	private void loadEditor() {

		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			public void onUncaughtException(Throwable e) {
				logger.log(Level.ALL, "Ex caught!", e);
			}
		});

		HorizontalPanel horPanel = new HorizontalPanel();

		RootPanel.get("Navigator").add(horPanel);

		final Button profileButton = new Button("Profile");

		profileButton.setStylePrimaryName("tngly-menubutton");

		horPanel.add(profileButton);

		profileButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				/*
				 * Showcase instantiieren.
				 */
				Update update = new ProfileView();

				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		});

		final Button wishlistButton = new Button("Wishlist");
		wishlistButton.setStylePrimaryName("tngly-menubutton");
		horPanel.add(wishlistButton);

		wishlistButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Update update = new WishlistView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		});

		final Button searchProfilButton = new Button("Search Profile");
		searchProfilButton.setStylePrimaryName("tngly-menubutton");
		horPanel.add(searchProfilButton);

		searchProfilButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Update update = new SearchProfileView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		});

		final Button banViewButton = new Button("Bans");
		banViewButton.setStylePrimaryName("tngly-menubutton");
		horPanel.add(banViewButton);

		banViewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Update update = new BanView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}

		});

		signOutLink.setHref(loginInfo.getLogoutUrl());

		moduleLink.setHref(GWT.getHostPageBaseURL() + "Reportgenerator.html");

		horPanel.add(signOutLink);

		horPanel.add(moduleLink);

	}

	private void loadLogin() {

		// Assemble login panel.

		signInLink.setHref(loginInfo.getLoginUrl());

		loginPanel.add(loginLabel);

		loginPanel.add(signInLink);

		RootPanel.get("Details").clear();
		RootPanel.get("Details").add(loginPanel);

	}
}
