package de.hdm.editor.client;



import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SearchProfileView extends Update{

		protected String getHeadlineText() {
	    return "";
	  }

	  protected void run() {
		  
		  VerticalPanel verPanel = new VerticalPanel();
		  verPanel.setSpacing(10);
		  
		  RootPanel.get("Details").add(verPanel);
		  
		  final Button searchByProfileButton = new Button("Search By Profile");
		    searchByProfileButton.setStylePrimaryName("tngly-button-searchview");
		    verPanel.add(searchByProfileButton);

		    searchByProfileButton.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		        Update update = new SearchByProfileView();
		        RootPanel.get("Details").clear();
		        RootPanel.get("Details").add(update);
		        
		      }
		    });
		    
		    final Button searchBySimilarityButton = new Button("Search By Similarity");
		    searchBySimilarityButton.setStylePrimaryName("tngly-button-searchview");
		    verPanel.add(searchBySimilarityButton);

		    searchBySimilarityButton.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		        //Update update = new SearchByProfileView();
		        RootPanel.get("Details").clear();
		        //RootPanel.get("Details").add(update);
		        
		      }
		    });
		    
		    
		  
	  }
}