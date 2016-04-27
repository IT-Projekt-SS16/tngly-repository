package de.hdm.wi7.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SearchByProfileView extends Update{

	  /**
	   * Jeder Showcase besitzt eine einleitende Ãœberschrift, die durch diese
	   * Methode zu erstellen ist.
	   * 
	   * @see Showcase#getHeadlineText()
	   */
	  @Override
	  protected String getHeadlineText() {
	    return "Search By Profile View";
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
    
    TextBox tbg = new TextBox();
    TextBox tbdob = new TextBox();
    TextBox tbbh = new TextBox();
    TextBox tbhc = new TextBox();
    TextBox tbs = new TextBox();
    TextBox tbc = new TextBox();
    
    //*** BEISPIEL ADDKEYHANDLER NOCH FÜR ALLE ÜBERNEHMEN***
    
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
	  
	  final Button saveProfilButton = new Button("Search");
	    saveProfilButton.setStylePrimaryName("tngly-menubutton");
	    verPanel.add(saveProfilButton);

	    saveProfilButton.addClickHandler(new ClickHandler() {
	      public void onClick(ClickEvent event) {
	        Update update = new ProfileView();
	        RootPanel.get("Details").clear();
	        RootPanel.get("Details").add(update);
	      }
	    }); 
	  
	  }
}
