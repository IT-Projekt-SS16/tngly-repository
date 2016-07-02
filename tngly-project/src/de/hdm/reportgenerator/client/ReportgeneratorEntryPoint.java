package de.hdm.reportgenerator.client;



import java.util.logging.Logger;

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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.client.LoginService;
import de.hdm.core.client.LoginServiceAsync;
import de.hdm.core.shared.LoginInfo;
import de.hdm.editor.client.EditProfileView;
import de.hdm.editor.client.Update;


public class ReportgeneratorEntryPoint implements EntryPoint {
	
	private static final Logger logger = ClientsideSettings.getLogger();

	private LoginInfo loginInfo = null;

	private VerticalPanel loginPanel = new VerticalPanel();

	private Label loginLabel = new Label("Please sign in to your Google Account to access the REPORTGENERATOR module.");

	private Anchor signInLink = new Anchor("Sign In");

	@Override
	public void onModuleLoad() {
		LoginServiceAsync loginService = GWT.create(LoginService.class);

		loginService.login(GWT.getHostPageBaseURL() + "Reportgenerator.html", new AsyncCallback<LoginInfo>() {

			public void onFailure(Throwable error) {

			}

			public void onSuccess(LoginInfo result) {

				loginInfo = result;

				if (loginInfo.isLoggedIn()) {
					logger.info("LoginService onSuccess wird ausgef�hrt");

					loadReportgenerator();

				} else {

					loadLogin();

				}

			}

		});

	}
	
	
	private void loadReportgenerator() {
		
		VerticalPanel verPanel = new VerticalPanel();
		
		final Label title = new Label("TNGLY");
		
		final Button signOutButton = new Button("SIGN OUT");
		final Button imprintButton = new Button("IMPRINT");
		final Button editorButton = new Button("GO TO ED");
		
		final DialogBox dialogBox = createDialogBox();
	    dialogBox.setGlassEnabled(true);
	    dialogBox.setAnimationEnabled(true);
		
		logger.info("loadReportgenerator wird ausgef�hrt");

		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			public void onUncaughtException(Throwable e) {
				
			}
		});
		
//		title.setStylePrimaryName("tngly-header");
		
		signOutButton.setStylePrimaryName("tngly-menubutton");
		imprintButton.setStylePrimaryName("tngly-submenubutton");
		editorButton.setStylePrimaryName("tngly-submenubutton");

		verPanel.setStylePrimaryName("tngly-navigation");
		
		verPanel.add(signOutButton);
		verPanel.add(editorButton);
		verPanel.add(imprintButton);

//		RootPanel.get("Header").add(title);
		RootPanel.get("Navigator").add(verPanel);
		
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
		
		editorButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.open(GWT.getHostPageBaseURL() + "Editor.html",
						"_self", "");
			}
		});
		
		UpdateReportGenerator update = new SearchByProfileViewR();
		RootPanel.get("Details").clear();
		RootPanel.get("Details").add(update);
		
		logger.info("RootPanel wurde gecleart und SearchByProfileReportView hinzugefügt");
		
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
				+ "Philipp Schmitt (27940)<br> Kevin Jaeger (27942)<br> Dominik Dach (27932)<br> Lorena Esposito (27981)<br> Marius Klepser (27989)<br> Esra Simsek (26497)<br>";

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

		RootPanel.get("Details").add(loginPanel);
	}
}
