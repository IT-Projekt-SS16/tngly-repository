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
		  this.append("Here you will see your Favourits");
		  
		  FlexTable t = new FlexTable();
		  
		  
		  t.setText(0, 0, "Philly Vanilli");
		  t.setWidget(0, 1, new Button("remove"));
		  
		  
		  
		  t.setText(1, 0, "Kevin Jaeger");
		  t.setWidget(1, 1, new Button("remove"));
		  
		  
		  t.setText(2, 0, "Hans Wurst");
		  t.setWidget(2, 1, new Button("remove"));
		  
		  
		  t.setText(3, 0, "Mr. Cheese");
		  t.setWidget(3, 1, new Button("remove"));
		  
		  RootPanel.get("Details").add(t);
		 
			        
			      }
}
		    
	 
		    
	  
		    
		    
		    
  

		    
		  
		
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  



