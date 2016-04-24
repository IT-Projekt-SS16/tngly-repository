package de.hdm.wi7.client;

import java.util.Date;

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
	   * Jeder Showcase besitzt eine einleitende Ãœberschrift, die durch diese
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
    
    TextBox tbu = new TextBox();
    final TextBox tbfn = new TextBox();
    final TextBox tbn = new TextBox();
    final TextBox tbg = new TextBox();
    final TextBox tbdob = new TextBox();
    final TextBox tbbh = new TextBox();
    final TextBox tbhc = new TextBox();
    final TextBox tbs = new TextBox();
    final TextBox tbc = new TextBox();
    
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
	    
	    ClientsideSettings.getLogger().severe("INFO: " + CommonSettings.getUserProfile().toString());

	    saveProfilButton.addClickHandler(new ClickHandler() {
	      public void onClick(ClickEvent event) {
	    	  if (CommonSettings.getUserProfile() == null){
	    		  Profile temp = new Profile();
		    	  String expectedPattern = "MM/dd/yyyy";
		    	  DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat(expectedPattern);
		    	  
		    	  temp.setName(tbfn.getText());
		    	  temp.setLastName(tbn.getText());
		    	  temp.setGender(tbg.getText());
		    	  Date date = null;
		    	  date = dateTimeFormat.parse(tbdob.getText());
		    	  temp.setDateOfBirth(date);
		    	  float f = Float.valueOf(tbbh.getText().trim()).floatValue();
		    	  temp.setBodyHeight(f);
		    	  temp.setHairColour(tbhc.getText());
		    	  if (tbs.getText() == "Yes"){
		    		  temp.setIsSmoking(true);
		    	  }
		    	  else {
		    		  temp.setIsSmoking(false);
		    	  }
		    	  temp.setConfession(tbc.getText());
		    	  ClientsideSettings.getAdministration().createProfile(temp, new CreateCallback());
	    	  }
	    	  else {
	    		  Profile temp = new Profile();
	    		  String expectedPattern = "MM/dd/yyyy";
		    	  DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat(expectedPattern);
		    	  
		    	  temp.setName(tbfn.getText());
		    	  temp.setLastName(tbn.getText());
		    	  temp.setGender(tbg.getText());
		    	  Date date = null;
		    	  date = dateTimeFormat.parse(tbdob.getText());
		    	  temp.setDateOfBirth(date);
		    	  float f = Float.valueOf(tbbh.getText().trim()).floatValue();
		    	  temp.setBodyHeight(f);
		    	  temp.setHairColour(tbhc.getText());
		    	  if (tbs.getText() == "Yes"){
		    		  temp.setIsSmoking(true);
		    	  }
		    	  else {
		    		  temp.setIsSmoking(false);
		    	  }
		    	  temp.setConfession(tbc.getText());
		    	  ClientsideSettings.getAdministration().editProfile(temp, new CreateCallback());
	    	  }
	    	 
	    	  Update update = new ProfileView();
	        RootPanel.get("Details").clear();
	        RootPanel.get("Details").add(update);
	      }
	    }); 
	  

	  }
	  class CreateCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			
		}
		  
	  }
}
