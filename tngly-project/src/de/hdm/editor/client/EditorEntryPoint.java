package de.hdm.editor.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
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
import de.hdm.core.shared.AdministrationServiceAsync;
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

	private final AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();
	
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

	@Override
	public void onModuleLoad() {

		loginService.login(GWT.getHostPageBaseURL() + "Editor.html", new AsyncCallback<LoginInfo>() {

			@Override
			public void onFailure(Throwable error) {

			}

			@Override
			public void onSuccess(LoginInfo result) {

				loginInfo = result;

				ClientsideSettings.setLoginInfo(result);

				if (loginInfo.isLoggedIn()) {

					
//					adminService.checkUserProfile(checkUserProfileCallback());
					loadEditor();

				} else {

					loadLogin();

				}

			}

		});

	}

	private AsyncCallback<Void> checkUserProfileCallback() {
		AsyncCallback<Void> asyncCallback = new AsyncCallback<Void>(){

			@Override
			public void onFailure(Throwable caught) {
				ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				ClientsideSettings.getLogger().severe("Success CheckUserProfileCallback: " + result.getClass().getSimpleName());
				
			}
		};
		return asyncCallback;
	}

	private void loadEditor() {

		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			@Override
			public void onUncaughtException(Throwable e) {
				logger.log(Level.ALL, "Ex caught!", e);
			}
		});

		VerticalPanel verPanel = new VerticalPanel();
		HorizontalPanel logoutPanel = new HorizontalPanel();
		
		
		RootPanel.get("Navigator").add(logoutPanel);
		
		RootPanel.get("Navigator").add(verPanel);
		

		final Button profileButton = new Button("MY PROFILE");
		verPanel.add(profileButton);
		
		final Button editProfileButton = new Button("EDIT PROFILE");
		editProfileButton.setStylePrimaryName("tngly-submenubutton");
		verPanel.add(editProfileButton);

		final Button profileListsButton = new Button("PROFILELISTS");
		profileListsButton.setStylePrimaryName("tngly-menubutton");
		verPanel.add(profileListsButton);

		
		final Button wishlistButton = new Button("WISHLIST");
		wishlistButton.setStylePrimaryName("tngly-submenubuttonblack");
		verPanel.add(wishlistButton);
		
		final Button banlistButton = new Button("BANLIST");
		banlistButton.setStylePrimaryName("tngly-submenubuttonblack");
		verPanel.add(banlistButton);
		
		final Button searchProfilButton = new Button("SEARCH PARTNER");
		searchProfilButton.setStylePrimaryName("tngly-menubutton");
		verPanel.add(searchProfilButton);


		profileButton.setStylePrimaryName("tngly-EditProfileButton");

		
		profileButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				profileButton.setStylePrimaryName("tngly-Profilebutton");
				wishlistButton.setStylePrimaryName("tngly-submenubuttonblack");
				banlistButton.setStylePrimaryName("tngly-submenubuttonblack");
				searchProfilButton.setStylePrimaryName("tngly-menubutton");
				editProfileButton.setStylePrimaryName("tngly-submenubuttonblack");
				profileListsButton.setStylePrimaryName("tngly-menubutton");
				Update update = new ProfileView();

				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		});

		profileListsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				profileButton.setStylePrimaryName("tngly-menubutton");
				wishlistButton.setStylePrimaryName("tngly-submenubutton");
				banlistButton.setStylePrimaryName("tngly-submenubuttonblack");
				searchProfilButton.setStylePrimaryName("tngly-menubutton");
				editProfileButton.setStylePrimaryName("tngly-submenubuttonblack");
				profileListsButton.setStylePrimaryName("tngly-Profilebutton");
				Update update = new WishlistCTView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		});

		
		wishlistButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				profileButton.setStylePrimaryName("tngly-menubutton");
				wishlistButton.setStylePrimaryName("tngly-submenubutton");
				banlistButton.setStylePrimaryName("tngly-submenubuttonblack");
				searchProfilButton.setStylePrimaryName("tngly-menubutton");
				editProfileButton.setStylePrimaryName("tngly-submenubuttonblack");
				profileListsButton.setStylePrimaryName("tngly-Profilebutton");
				Update update = new WishlistCTView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		});

	

		searchProfilButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				profileButton.setStylePrimaryName("tngly-menubutton");
				wishlistButton.setStylePrimaryName("tngly-submenubuttonblack");
				banlistButton.setStylePrimaryName("tngly-submenubuttonblack");
				searchProfilButton.setStylePrimaryName("tngly-Profilebutton");
				editProfileButton.setStylePrimaryName("tngly-submenubuttonblack");
				profileListsButton.setStylePrimaryName("tngly-menubutton");
				Update update = new SearchByProfileView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		});
		
		banlistButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				profileButton.setStylePrimaryName("tngly-menubutton");
				wishlistButton.setStylePrimaryName("tngly-submenubuttonblack");
				banlistButton.setStylePrimaryName("tngly-submenubutton");
				searchProfilButton.setStylePrimaryName("tngly-menubutton");
				editProfileButton.setStylePrimaryName("tngly-submenubuttonblack");
				profileListsButton.setStylePrimaryName("tngly-Profilebutton");
				Update update = new BanCTView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		});
		
		
		editProfileButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				profileButton.setStylePrimaryName("tngly-Profilebutton");
				wishlistButton.setStylePrimaryName("tngly-submenubuttonblack");
				banlistButton.setStylePrimaryName("tngly-submenubuttonblack");
				searchProfilButton.setStylePrimaryName("tngly-menubutton");
				editProfileButton.setStylePrimaryName("tngly-submenubutton");
				profileListsButton.setStylePrimaryName("tngly-menubutton");
				Update update = new EditProfileView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		});
		
	
	
//		final Button signOutButton = new Button("SIGN OUT");
//		signOutButton.setStylePrimaryName("tngly-signoutButton");
//		logoutPanel.add(signOutButton);

//		signOutButton.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				Window.open(ClientsideSettings.getLoginInfo().getLogoutUrl(),
//						"_self", "");
//			}
//		});

	

		signOutLink.setHref(loginInfo.getLogoutUrl());


		verPanel.add(signOutLink);

		
		Update update = new EditProfileView();
		RootPanel.get("Details").clear();
		RootPanel.get("Details").add(update);

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
