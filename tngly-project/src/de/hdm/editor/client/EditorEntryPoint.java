package de.hdm.editor.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
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

					
					//adminService.checkUserProfile(checkUserProfileCallback());
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
				loadEditor();
			}
		};
		return asyncCallback;
	}

	private void loadEditor() {
		
		VerticalPanel verPanel = new VerticalPanel();
		
		final Button profileButton = new Button("MY PROFILE");
		final Button editProfileButton = new Button("EDIT PROFILE");
		final Button wishlistButton = new Button("WISHLIST");
		final Button banlistButton = new Button("BANLIST");
		final Button searchProfilButton = new Button("SEARCH PARTNER");
		final Button signOutButton = new Button("SIGN OUT");
		final Button imprintButton = new Button("IMPRINT");
		final Button reportGenButton = new Button("GO TO RG");
		
		final DialogBox dialogBox = createDialogBox();
	    dialogBox.setGlassEnabled(true);
	    dialogBox.setAnimationEnabled(true);

		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			@Override
			public void onUncaughtException(Throwable e) {
				logger.log(Level.ALL, "Ex caught!", e);
			}
		});
		
		editProfileButton.setStylePrimaryName("tngly-submenubutton");
		wishlistButton.setStylePrimaryName("tngly-submenubutton");
		banlistButton.setStylePrimaryName("tngly-submenubutton");
		searchProfilButton.setStylePrimaryName("tngly-menubutton");
		signOutButton.setStylePrimaryName("tngly-menubutton-signout");
		profileButton.setStylePrimaryName("tngly-menubutton");
		imprintButton.setStylePrimaryName("tngly-submenubutton");
		reportGenButton.setStylePrimaryName("tngly-submenubutton");
		
		verPanel.setStylePrimaryName("tngly-navigation");
		
		verPanel.add(profileButton);
		verPanel.add(editProfileButton);
		verPanel.add(wishlistButton);
		verPanel.add(banlistButton);
		verPanel.add(searchProfilButton);
		verPanel.add(signOutButton);
		verPanel.add(reportGenButton);
		verPanel.add(imprintButton);
		
		RootPanel.get("Navigator").add(verPanel);
		
		
		profileButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				profileButton.setStylePrimaryName("tngly-menubutton-active");
				
				editProfileButton.setStylePrimaryName("tngly-submenubutton");
				wishlistButton.setStylePrimaryName("tngly-submenubutton");
				banlistButton.setStylePrimaryName("tngly-submenubutton");
				searchProfilButton.setStylePrimaryName("tngly-menubutton");
				signOutButton.setStylePrimaryName("tngly-menubutton-signout");
				imprintButton.setStylePrimaryName("tngly-submenubutton");
				
				Update update = new ProfileView();

				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		});
		
		wishlistButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				wishlistButton.setStylePrimaryName("tngly-submenubutton-active");
				
				editProfileButton.setStylePrimaryName("tngly-submenubutton");
				banlistButton.setStylePrimaryName("tngly-submenubutton");
				searchProfilButton.setStylePrimaryName("tngly-menubutton");
				signOutButton.setStylePrimaryName("tngly-menubutton-signout");
				profileButton.setStylePrimaryName("tngly-menubutton");
				imprintButton.setStylePrimaryName("tngly-submenubutton");
				
				Update update = new WishlistCTView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		});

		searchProfilButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				searchProfilButton.setStylePrimaryName("tngly-menubutton-active");
				
				editProfileButton.setStylePrimaryName("tngly-submenubutton");
				wishlistButton.setStylePrimaryName("tngly-submenubutton");
				banlistButton.setStylePrimaryName("tngly-submenubutton");
				signOutButton.setStylePrimaryName("tngly-menubutton-signout");
				profileButton.setStylePrimaryName("tngly-menubutton");
				imprintButton.setStylePrimaryName("tngly-submenubutton");
				
				Update update = new SearchByProfileView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		});
		
		banlistButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				banlistButton.setStylePrimaryName("tngly-submenubutton-active");
				
				editProfileButton.setStylePrimaryName("tngly-submenubutton");
				wishlistButton.setStylePrimaryName("tngly-submenubutton");
				searchProfilButton.setStylePrimaryName("tngly-menubutton");
				signOutButton.setStylePrimaryName("tngly-menubutton-signout");
				profileButton.setStylePrimaryName("tngly-menubutton");
				imprintButton.setStylePrimaryName("tngly-submenubutton");
				
				Update update = new BanCTView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		});
		
		
		editProfileButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				editProfileButton.setStylePrimaryName("tngly-submenubutton-active");
				
				wishlistButton.setStylePrimaryName("tngly-submenubutton");
				banlistButton.setStylePrimaryName("tngly-submenubutton");
				searchProfilButton.setStylePrimaryName("tngly-menubutton");
				signOutButton.setStylePrimaryName("tngly-menubutton-signout");
				profileButton.setStylePrimaryName("tngly-menubutton");
				imprintButton.setStylePrimaryName("tngly-submenubutton");
				
				Update update = new EditProfileView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
		});

		signOutButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.open(ClientsideSettings.getLoginInfo().getLogoutUrl(),
						"_self", "");
			}
		});
		
		imprintButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialogBox.center();
	            dialogBox.show();
			}
		});
		
		reportGenButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.open(GWT.getHostPageBaseURL() + "Reportgenerator.html",
						"_self", "");
			}
		});

		Update update = new EditProfileView();
		RootPanel.get("Details").clear();
		RootPanel.get("Details").add(update);
	}

	private DialogBox createDialogBox() {
		// Create a dialog box and set the caption text
	    final DialogBox dialogBox = new DialogBox();
	    dialogBox.ensureDebugId("cwDialogBox");
	    dialogBox.setText("Imprint");

	    // Create a table to layout the content
	    VerticalPanel dialogContents = new VerticalPanel();
	    dialogContents.setSpacing(4);
	    dialogBox.setWidget(dialogContents);
	    
	    HTML aboutHTML = new HTML();
	    
	    String about = "IT-Projekt SS 2016<br>"
				+ "Tingly Partnerboerse<br>"
				+ "Hochschule der Medien Stuttgart<br>"
				+ "Team 10<br>"
				+ "Philipp Schmitt ()<br> Kevin Jaeger ()<br> Dominik Dach ()<br> Lorena Esposito ()<br> Marius Klepser ()<br> Esra Simsek ()<br>";

	    aboutHTML.setHTML(about);
	    dialogContents.add(aboutHTML);
	    dialogContents.setCellHorizontalAlignment(
	        aboutHTML, HasHorizontalAlignment.ALIGN_CENTER);
	    
	 // Add a close button at the bottom of the dialog
	    Button closeButton = new Button(
	        "Close", new ClickHandler() {
	          public void onClick(ClickEvent event) {
	            dialogBox.hide();
	          }
	        });
	    
	    dialogContents.add(closeButton);
	    dialogContents.setCellHorizontalAlignment(
	        closeButton, HasHorizontalAlignment.ALIGN_CENTER);
	    
	 // Return the dialog box
	    return dialogBox;
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
