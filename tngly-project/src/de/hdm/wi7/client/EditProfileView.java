package de.hdm.wi7.client;

import java.text.ParseException;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;

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
    final TextBox tbbh = new TextBox();
    final TextBox tbhc = new TextBox();
    final TextBox tbc = new TextBox();
    
    final ListBox isSmokingBox = new ListBox(false);
    isSmokingBox.setVisibleItemCount(2);
    isSmokingBox.addItem("yes");
    isSmokingBox.addItem("No");
    
    final ListBox genderBox = new ListBox(false);
    genderBox.setVisibleItemCount(2);
    genderBox.addItem("w");
    genderBox.addItem("m");
       
    final  DatePicker datePicker = new DatePicker();
       
        
 
    
  
    
    
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
	  verPanel.add(genderBox);
	  
	  Label dateOfBirth = new Label("Date of Birth:");
	  verPanel.add(dateOfBirth);
	  verPanel.add(datePicker);
	  
	  Label bodyHeight = new Label("Body Height:");
	  verPanel.add(bodyHeight);
	  verPanel.add(tbbh);
	  
	  Label hairColour = new Label("Haircolour:");
	  verPanel.add(hairColour);
	  verPanel.add(tbhc);
	  
	  Label isSmoking = new Label("Smoker:");
	  verPanel.add(isSmoking);
	  verPanel.add(isSmokingBox);
	  
	  Label confession = new Label("Confession:");
	  verPanel.add(confession);
	  verPanel.add(tbc);
	  
	  final Button saveProfilButton = new Button("Save");
	    saveProfilButton.setStylePrimaryName("tngly-menubutton");
	    verPanel.add(saveProfilButton);
	    
	    saveProfilButton.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  
		    	  Logger logger = ClientsideSettings.getLogger();	
			  		 logger.info("Erfolgreich onClick ausgeführt.");
			  		 
			  		 if (CommonSettings.getUserProfile() == null){	
		    	 		 logger.info("getUserProfile war null.");
		    		  Profile temp = new Profile();
			    	  
			    	  logger.info("Profile+DateTimeFormat instantiiert");
			    	  
			    	  temp.setName(tbfn.getText());
			    	  
			    	  logger.info("Name CHECK");
			    	  
			    	  temp.setLastName(tbn.getText());
			    	  
			    	  logger.info("lastName CHECK");
			    	  
			    	  int selectedGenderIndex = genderBox.getSelectedIndex();
			    	  temp.setGender(genderBox.getItemText(selectedGenderIndex));
			    	  
			    	  logger.info("gender CHECK");
			    	  
			    	  temp.setDateOfBirth(datePicker.getValue());
			    	  
			    	  logger.info("dateOfBirth CHECK");
			    	  
			    	  float f = Float.valueOf(tbbh.getText().trim()).floatValue();
			    	  temp.setBodyHeight(f);
			    	  
			    	  logger.info("bodyHeight CHECK");
			    	  
			    	  temp.setHairColour(tbhc.getText());
			    	  
			    	  logger.info("HairColour CHECK");
			    	  
			    	  int selectedIsSmokingIndex = isSmokingBox.getSelectedIndex();
			    	  temp.setGender(genderBox.getItemText(selectedGenderIndex));
			    	  if (isSmokingBox.getItemText(selectedIsSmokingIndex) == "Yes"){
			    		  temp.setIsSmoking(1);
			    	  }
			    	  else {
			    		  temp.setIsSmoking(0);
			    	  }
			    	  
			    	  logger.info("isSmoking CHECK");
			    	  
			    	  temp.setConfession(tbc.getText());
			    	  
			    	  logger.info("Confession CHECK");

			    	  
			    	  ClientsideSettings.getAdministration().createProfile(temp, new CreateCallback());
			  		 	logger.info("if-getAdministration wurde aufgerufen");
			  		 	
			  		 	
		    	  }
		    	  else {
		    	 		 logger.info("getUserProfile war nicht 0.");
		    		  Profile temp = new Profile();
			    	  
			    	  temp.setName(tbfn.getText());
			    	  temp.setLastName(tbn.getText());
			    	  int selectedGenderIndex = genderBox.getSelectedIndex();
			    	  temp.setGender(genderBox.getItemText(selectedGenderIndex));
			    	  
			    	  temp.setDateOfBirth(datePicker.getValue());
			    	  
			    	  float f = Float.valueOf(tbbh.getText().trim()).floatValue();
			    	  temp.setBodyHeight(f);
			    	  temp.setHairColour(tbhc.getText());
			    	  
			    	  int selectedIsSmokingIndex = isSmokingBox.getSelectedIndex();
			    	  temp.setGender(genderBox.getItemText(selectedGenderIndex));
			    	  if (isSmokingBox.getItemText(selectedIsSmokingIndex) == "Yes"){
			    		  temp.setIsSmoking(1);
			    	  }
			    	  else {
			    		  temp.setIsSmoking(0);
			    	  }
			    	  
			    	  temp.setConfession(tbc.getText());
			    	  ClientsideSettings.getAdministration().editProfile(temp, new CreateCallback());
		    	  }
			  		 
			  		 
			  		 
			  		 
		    	  Update update = new ProfileView();		    	  
		    	  	    	  
		    	  RootPanel.get("Details").clear();
		    	  RootPanel.get("Details").add(update);
		    	  
		    	  logger.info("Erfolgreicher Reswitch.");
		    
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

    
	    
