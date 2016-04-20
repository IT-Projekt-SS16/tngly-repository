package de.hdm.wi7.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ProfileView extends Update{
	
	  /**
	   * Jeder Showcase besitzt eine einleitende Überschrift, die durch diese
	   * Methode zu erstellen ist.
	   * 
	   * @see Showcase#getHeadlineText()
	   */
	  @Override
	  protected String getHeadlineText() {
	    return "Profile View";
	  }

	  /**
	   * Jeder Showcase muss die <code>run()</code>-Methode implementieren. Sie ist
	   * eine "Einschubmethode", die von einer Methode der Basisklasse
	   * <code>ShowCase</code> aufgerufen wird, wenn der Showcase aktiviert wird.
	   */
	  @Override
	  protected void run() {
		  
      VerticalPanel verPanel = new VerticalPanel();
		  
      RootPanel.get("Details").add(verPanel);
      
	  Label firstName = new Label("First Name:");
	  verPanel.add(firstName);
	  
	  
	  Label name = new Label("Name:");
	  verPanel.add(name);
	  
	  Label gender = new Label("Gender:");
	  verPanel.add(gender);
	  
	  Label dateOfBirth = new Label("Date of Birth:");
	  verPanel.add(dateOfBirth);
		  
	  
	  
	  
	  
	  
	  
	  
	  
	  }
}



