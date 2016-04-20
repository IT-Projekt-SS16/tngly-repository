package de.hdm.wi7.client;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
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
      
      Label userName = new Label("Username:");
	  verPanel.add(userName);
	  
	  Label firstName = new Label("First Name:");
	  verPanel.add(firstName);
	  
	  Label name = new Label("Name:");
	  verPanel.add(name);
	  
	  Label gender = new Label("Gender:");
	  verPanel.add(gender);
	  
	  Label dateOfBirth = new Label("Date of Birth:");
	  verPanel.add(dateOfBirth);
		  
	  Label bodyHeight = new Label("Body Height:");
	  verPanel.add(bodyHeight);
	  
	  Label hairColour = new Label("Haircolour:");
	  verPanel.add(hairColour);
		  
	  Label isSmoking = new Label("Smoker:");
	  verPanel.add(isSmoking);
	  
	  Label confession = new Label("Confession:");
	  verPanel.add(confession);
	  
	  
	  final Button editProfilButton = new Button("Edit Profile");
	    editProfilButton.setStylePrimaryName("tngly-menubutton");
	    verPanel.add(editProfilButton);

	    editProfilButton.addClickHandler(new ClickHandler() {
	      public void onClick(ClickEvent event) {
	        Update update = new EditProfileView();
	        RootPanel.get("Details").clear();
	        RootPanel.get("Details").add(update);
	      }
	    }); 
	  
	  
	  
	  }
}



