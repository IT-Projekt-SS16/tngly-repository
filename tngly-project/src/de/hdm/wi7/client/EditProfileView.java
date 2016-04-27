package de.hdm.wi7.client;

import java.util.Date;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.wi7.shared.CommonSettings;
import de.hdm.wi7.shared.Profile;

public class EditProfileView extends Update{

	  /**
	   * Jeder Showcase besitzt eine einleitende Überschrift, die durch diese
	   * Methode zu erstellen ist.
	   * 
	   * @see Showcase#getHeadlineText()
	   */
	  @Override
	  protected String getHeadlineText() {
	    return "EDIT VIEW";
	  }

	  /**
	   * Jede View muss die <code>run()</code>-Methode implementieren. Sie ist
	   * eine "Einschubmethode", die von einer Methode der Basisklasse
	   * <code>Update</code> aufgerufen wird, wenn der View aktiviert wird.
	   */
	  @Override
	  protected void run() {
		  
		  Logger logger = ClientsideSettings.getLogger();	    		 
 		 logger.info("Erfolgreich Profile-Edit-View geswitcht.");
 		 
    VerticalPanel verPanel = new VerticalPanel();
		  
    RootPanel.get("Details").add(verPanel);
    
    TextBox tbu = new TextBox();
    final TextBox tbfn = new TextBox();
    final TextBox tbn = new TextBox();
    final TextBox tbg = new TextBox();
    final TextBox tbdob = new TextBox();
    final TextBox tbbh = new TextBox();
    final TextBox tbhc = new TextBox();
    final TextBox tbs = new TextBox();
    final TextBox tbc = new TextBox();
    
    //*** BEISPIEL ADDKEYHANDLER NOCH F�R ALLE �BERNEHMEN***
    
  //  tbfn.addKeyPressHandler(new KeyPressHandler() {

     //   public void onKeyPress(KeyPressEvent event) {
    //      if (!Character.isDigit(event.getCharCode())) {
    //        ((TextBox) event.getSource()).cancelKey();
    //      }
    //    }
   //   });
    



    TextArea ta = new TextArea();
    ta.setCharacterWidth(80);
    ta.setVisibleLines(50);
    
      Label userName = new Label("Username:");
	  verPanel.add(userName);
	  verPanel.add(tbu);
    
	  Label firstName = new Label("First Name:");
	  verPanel.add(firstName);
	  verPanel.add(tbfn);
	  
	  Label name = new Label("Name:");
	  verPanel.add(name);
	  verPanel.add(tbn);
	  
	  Label gender = new Label("Gender:");
	  verPanel.add(gender);
	  verPanel.add(tbg);
	  
	  Label dateOfBirth = new Label("Date of Birth:");
	  verPanel.add(dateOfBirth);
	  verPanel.add(tbdob);
	  
	  Label bodyHeight = new Label("Body Height:");
	  verPanel.add(bodyHeight);
	  verPanel.add(tbbh);
	  
	  Label hairColour = new Label("Haircolour:");
	  verPanel.add(hairColour);
	  verPanel.add(tbhc);
	  
	  Label isSmoking = new Label("Smoker:");
	  verPanel.add(isSmoking);
	  verPanel.add(tbs);
	  
	  Label confession = new Label("Confession:");
	  verPanel.add(confession);
	  verPanel.add(tbc);
	  
	  final Button saveProfilButton = new Button("Save");
	    saveProfilButton.setStylePrimaryName("tngly-menubutton");
	    verPanel.add(saveProfilButton);
	    
	    //ClientsideSettings.getLogger().severe("INFO: " + CommonSettings.getUserProfile().toString());

	    saveProfilButton.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  Update update = new ProfileView();
		    	  
		    	  
		    	  RootPanel.get("Details").clear();
		    	  RootPanel.get("Details").add(update);
		    
		      }
	    }); 
	  }
}
	      
	    
