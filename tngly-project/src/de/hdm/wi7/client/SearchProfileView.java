package de.hdm.wi7.client;



import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class SearchProfileView extends Update{

		protected String getHeadlineText() {
	    return "Search View";
	  }

	  protected void run() {
		  
		  HorizontalPanel horPanel = new HorizontalPanel();
		  
		  RootPanel.get("Details").add(horPanel);
		  this.append("Here you will see your searchoptions");
		  
		  final Button searchByProfileButton = new Button("Search By Profile");
		    searchByProfileButton.setStylePrimaryName("searchByProfileButton");
		    horPanel.add(searchByProfileButton);

		    searchByProfileButton.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		        //Update update = new SearchByProfileView();
		        RootPanel.get("Details").clear();
		        //RootPanel.get("Details").add(update);
		        
		      }
		    });
		    
		    final Button searchBySimilarityButton = new Button("Search By Similarity");
		    searchBySimilarityButton.setStylePrimaryName("searchBySimilarityButton");
		    horPanel.add(searchBySimilarityButton);

		    searchBySimilarityButton.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		        //Update update = new SearchByProfileView();
		        RootPanel.get("Details").clear();
		        //RootPanel.get("Details").add(update);
		        
		      }
		    });
		    
		    
		  
	  }
}