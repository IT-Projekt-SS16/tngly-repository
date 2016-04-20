package de.hdm.wi7.client;

// Kommentar zum Test als Commit

import de.hdm.wi7.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Tngly_project implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
	
	HorizontalPanel horPanel = new HorizontalPanel();
	
	RootPanel.get("Navigator").add(horPanel);

	  
	    final Button profileButton = new Button("Profile");

	   profileButton.setStylePrimaryName("tngly-menubutton");

	    horPanel.add(profileButton);
	    
	    profileButton.addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event)
	        {
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
	    
	   
	}
}