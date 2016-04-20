package de.hdm.wi7.client;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class EditProfileView extends Update{

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
    
    TextBox tbfn = new TextBox();
    TextBox tbn = new TextBox();
    TextBox tbg = new TextBox();
    TextBox tbdob = new TextBox();
    
    tbfn.addKeyPressHandler(new KeyPressHandler() {

        public void onKeyPress(KeyPressEvent event) {
          if (!Character.isDigit(event.getCharCode())) {
            ((TextBox) event.getSource()).cancelKey();
          }
        }
      });
    
    tbn.addKeyPressHandler(new KeyPressHandler() {

        public void onKeyPress(KeyPressEvent event) {
          if (!Character.isDigit(event.getCharCode())) {
            ((TextBox) event.getSource()).cancelKey();
          }
        }
      });
    
    tbg.addKeyPressHandler(new KeyPressHandler() {

        public void onKeyPress(KeyPressEvent event) {
          if (!Character.isDigit(event.getCharCode())) {
            ((TextBox) event.getSource()).cancelKey();
          }
        }
      });
    
    tbdob.addKeyPressHandler(new KeyPressHandler() {

        public void onKeyPress(KeyPressEvent event) {
          if (!Character.isDigit(event.getCharCode())) {
            ((TextBox) event.getSource()).cancelKey();
          }
        }
      });

    TextArea ta = new TextArea();
    ta.setCharacterWidth(80);
    ta.setVisibleLines(50);
    
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
	  }
}
