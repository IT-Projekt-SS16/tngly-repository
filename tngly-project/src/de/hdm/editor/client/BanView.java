package de.hdm.editor.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;

public class BanView extends Update{


	  protected String getHeadlineText() {
	    return "";
	  }
	 

	  protected void run() {
		 
		  
		  final CheckBox checkBox1 = new CheckBox();
		  final CheckBox checkBox2 = new CheckBox();
		  
		  FlexTable t = new FlexTable();
		  t.setStyleName("Table-Wishlist");
		  t.setCellSpacing(10);
		  
		  FlexTable t1 = new FlexTable();
		  t1.setStyleName("Table-Wishlist");
		  t1.setCellSpacing(10);
		  
		  
		  Button deleteButton = new Button("Delete");
		  deleteButton.setStyleName("tngly-button");
		  
		  
		  t.setText(0, 0, "Mr. Tngly");
		  
		  t.setText(1, 0, "Age:");
		  t.setText(2, 0, "Gender:" );
		  t.setText(1, 1, "40");
		  t.setText(2, 1, "Male");
		  t.setText(1, 2, "Hobbies:");
		  t.setText(2, 2, "Realname:");
		  t.setText(1, 3, "Zucken");
		  t.setText(2, 3, "Max Example");
		  t.setWidget(3, 0, checkBox1);
		  
		  
		  t1.setText(0, 0, "Kevina Hunter");
		  
		  t1.setText(1, 0, "Age:");
		  t1.setText(2, 0, "Gender:" );
		  t1.setText(1, 1, "17");
		  t1.setText(2, 1, "Female");
		  t1.setText(1, 2, "Hobbies:");
		  t1.setText(2, 2, "Realname:");
		  t1.setText(1, 3, "Auch Zucken");
		  t1.setText(2, 3, "Maxine Example");
		  
		  t1.setWidget(3, 0, checkBox2);
		  
		  
		
		  
		  RootPanel.get("Details").add(t);
		  RootPanel.get("Details").add(t1);
			        
		  RootPanel.get("Details").add(deleteButton);
			        
			      }
}
		    
	 
		    
	  
		    
		    
		    
  

		    
		  
		
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  



