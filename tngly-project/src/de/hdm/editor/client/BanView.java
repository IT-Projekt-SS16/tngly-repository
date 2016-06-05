package de.hdm.editor.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;

public class BanView extends Update{


	  protected String getHeadlineText() {
	    return "Ban View";
	  }
	 

	  protected void run() {
		 
		  
		  FlexTable t = new FlexTable();
		  t.setStyleName("Table");
		  t.setCellSpacing(10);
		  
		  
		  Button deleteButton = new Button("Delete");
		  deleteButton.setStyleName("tngly-button");
		  
		  Button deleteButton1 = new Button("Delete");
		  deleteButton1.setStyleName("tngly-button");
		  
		  Button deleteButton2 = new Button("Delete");
		  deleteButton2.setStyleName("tngly-button");
		  
		  Button deleteButton3 = new Button("Delete");
		  deleteButton3.setStyleName("tngly-button");
		  
		  t.setText(0, 0, "Philly Vanilli");
		  t.setWidget(0, 1, deleteButton);
		  
		  
		  t.setText(1, 0, "Kevin Jaeger");
		  t.setWidget(1, 1, deleteButton1);
		  
		  
		  t.setText(2, 0, "Hans Wurst");
		  t.setWidget(2, 1,deleteButton2 );
		  
		  
		  t.setText(3, 0, "Mr. Cheese");
		  t.setWidget(3, 1, deleteButton3);
		  
		  RootPanel.get("Details").add(t);
		 
			        
			      }
}
		    
	 
		    
	  
		    
		    
		    
  

		    
		  
		
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  



