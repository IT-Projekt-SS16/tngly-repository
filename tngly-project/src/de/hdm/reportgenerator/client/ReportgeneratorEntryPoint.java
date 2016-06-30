package de.hdm.reportgenerator.client;



import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
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
					logger.info("LoginService onSuccess wird ausgef�hrt");

					loadReportgenerator();

				} else {

					loadLogin();

				}

			}

		});

	}
	
	
	private void loadReportgenerator() {
		
		logger.info("loadReportgenerator wird ausgef�hrt");

		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			public void onUncaughtException(Throwable e) {
				
			}
		});

		HorizontalPanel horPanel = new HorizontalPanel();

		RootPanel.get("Navigator").add(horPanel);
		
		signOutLink.setHref(loginInfo.getLogoutUrl());
		
		moduleLink.setHref(GWT.getHostPageBaseURL() + "Reportgenerator.html");

		VerticalPanel vp = new VerticalPanel();

		vp.add(signOutLink);
		
		vp.add(moduleLink);

		RootPanel.get("Navigator").add(vp);
		
		
		UpdateReportGenerator update = new SearchByProfileViewR();
		RootPanel.get("Details").clear();
		RootPanel.get("Details").add(update);
		
		logger.info("RootPanel wurde gecleart und SearchByProfileReportView hinzugefügt");
		
	}

	private void loadLogin() {

		// Assemble login panel.

		signInLink.setHref(loginInfo.getLoginUrl());

		loginPanel.add(loginLabel);

		loginPanel.add(signInLink);

		RootPanel.get("Details").add(loginPanel);
	}
}
