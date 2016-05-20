package de.hdm.reportgenerator.client;



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

import de.hdm.core.client.LoginService;
import de.hdm.core.client.LoginServiceAsync;
import de.hdm.core.shared.LoginInfo;
//import de.hdm.editor.client.BanView;
//import de.hdm.editor.client.ProfileView;
//import de.hdm.editor.client.SearchProfileView;
//import de.hdm.editor.client.Update;
//import de.hdm.editor.client.WishlistView;

public class ReportgeneratorEntryPoint implements EntryPoint {

	private LoginInfo loginInfo = null;

	private VerticalPanel loginPanel = new VerticalPanel();

	private Label loginLabel = new Label("Please sign in to your Google Account to access the REPORTGENERATOR module.");

	private Anchor signInLink = new Anchor("Sign In");

	private Anchor signOutLink = new Anchor("Sign Out");

	private Anchor moduleLink = new Anchor("Change to Editor module");

	@Override
	public void onModuleLoad() {
		LoginServiceAsync loginService = GWT.create(LoginService.class);

		loginService.login(GWT.getHostPageBaseURL() + "Reportgenerator.html", new AsyncCallback<LoginInfo>() {

			public void onFailure(Throwable error) {

			}

			public void onSuccess(LoginInfo result) {

				loginInfo = result;

				if (loginInfo.isLoggedIn()) {

					loadReportgenerator();

				} else {

					loadLogin();

				}

			}

		});

	}

	private void loadReportgenerator() {

		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			public void onUncaughtException(Throwable e) {
				
			}
		});

		HorizontalPanel horPanel = new HorizontalPanel();

		RootPanel.get("Navigator").add(horPanel);

		final Button allProfilesButton = new Button("All Profiles");

		allProfilesButton.setStylePrimaryName("tngly-menubutton");

		horPanel.add(allProfilesButton);

		allProfilesButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				/*
				 * Showcase instantiieren.
				 */
				UpdateReportGenerator updateR = new AllProfilesView();

				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(updateR);
			}
		});
//
//		final Button wishlistButton = new Button("Wishlist");
//		wishlistButton.setStylePrimaryName("tngly-menubutton");
//		horPanel.add(wishlistButton);
//
//		wishlistButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				Update update = new WishlistView();
//				RootPanel.get("Details").clear();
//				RootPanel.get("Details").add(update);
//			}
//		});
//
//		final Button searchProfilButton = new Button("Search Profile");
//		searchProfilButton.setStylePrimaryName("tngly-menubutton");
//		horPanel.add(searchProfilButton);
//
//		searchProfilButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				Update update = new SearchProfileView();
//				RootPanel.get("Details").clear();
//				RootPanel.get("Details").add(update);
//			}
//		});
//
//		final Button banViewButton = new Button("Bans");
//		banViewButton.setStylePrimaryName("tngly-menubutton");
//		horPanel.add(banViewButton);
//
//		banViewButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				Update update = new BanView();
//				RootPanel.get("Details").clear();
//				RootPanel.get("Details").add(update);
//			}
//
//		});
//		
		
		
		signOutLink.setHref(loginInfo.getLogoutUrl());
		
		moduleLink.setHref(GWT.getHostPageBaseURL() + "Editor.html");

		VerticalPanel vp = new VerticalPanel();

		vp.add(new Label("I'm logged in to REPORTGENERATOR module!"));

		vp.add(signOutLink);
		
		vp.add(moduleLink);

		RootPanel.get("Details").add(vp);

	}

	private void loadLogin() {

		// Assemble login panel.

		signInLink.setHref(loginInfo.getLoginUrl());

		loginPanel.add(loginLabel);

		loginPanel.add(signInLink);

		RootPanel.get("Details").add(loginPanel);

	}

}
