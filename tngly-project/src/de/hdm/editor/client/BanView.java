package de.hdm.editor.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.TextInputCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Text;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.ProfileBan;

public class BanView extends Update{


	  protected String getHeadlineText() {
	    return "";
	  }
	 
	  private final ArrayList<ProfileBan> banList = ClientsideSettings.getBanlist();

	  protected void run() {
		  this.append("Here you will see your list of banned profiles");
		  final Button removeButton;
			  
			    
		
		  DataGrid<ProfileBan> bansTable = new DataGrid<ProfileBan>();
		  bansTable.setWidth("100%");
		   
		  
		  bansTable.setEmptyTableWidget(new Label("You do not have a ban"));	  
		  
		  CellTable<ProfileBan> banTable = new CellTable<ProfileBan>();
		    banTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		  
		// Add a selection model to handle user selection.
		    final MultiSelectionModel<ProfileBan> selectionModel = new MultiSelectionModel<ProfileBan>();
		    banTable.setSelectionModel(selectionModel);
		    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
		      public void onSelectionChange(SelectionChangeEvent event) {
		        Set<ProfileBan> selected = selectionModel.getSelectedSet();
		        if (selected != null) {
		          Window.alert("You selected: " + selected.size() + "Profiles");
		        }
		      }
		    });
		  
		  Column<ProfileBan, Boolean> checkColumn =
			        new Column<ProfileBan, Boolean>(new CheckboxCell(true, false)) {
			          @Override
			          public Boolean getValue(ProfileBan pb) {
			            // Get the value from the selection model.
			            return selectionModel.isSelected(pb);
			          }
			        };
			   bansTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
			    bansTable.setColumnWidth(checkColumn, 40, Unit.PX);
			    
		    // Add a text column to show the username.
		    TextColumn<ProfileBan> userNameColumn = new TextColumn<ProfileBan>() {
		      @Override
		      public String getValue(ProfileBan pb) {
		        return pb.getBannedProfile().getUserName();
		      }
		    };
		    banTable.addColumn(userNameColumn, "Username");

		    // Add a text column to show the name.
		    TextColumn<ProfileBan> nameColumn = new TextColumn<ProfileBan>() {
		      @Override
		      public String getValue(ProfileBan pb) {
		        return pb.getBannedProfile().getName();
		      }
		    };
		    banTable.addColumn(nameColumn, "Name");
		    
		    // Add a text column to show the lastname.
		    TextColumn<ProfileBan> lastNameColumn = new TextColumn<ProfileBan>() {
		      @Override
		      public String getValue(ProfileBan pb) {
		        return pb.getBannedProfile().getLastName();
		      }
		    };
		    banTable.addColumn(lastNameColumn, "LastName");
		    
		 // Add a date column to show the birthday.
		    DateCell dateCell = new DateCell();
		    Column<ProfileBan, Date> dateColumn = new Column<ProfileBan, Date>(dateCell) {
		      @Override
		      public Date getValue(ProfileBan pb) {
		        return pb.getBannedProfile().getDateOfBirth();
		      }
		    };
		    banTable.addColumn(dateColumn, "Birthday");
		    
		    // Add a text column to show the gender.
		    TextColumn<ProfileBan> genderColumn = new TextColumn<ProfileBan>() {
		      @Override
		      public String getValue(ProfileBan pb) {
		        return pb.getBannedProfile().getGender();
		      }
		    };
		    banTable.addColumn(genderColumn, "Gender");
		    
		}	  
	  
	  

		    
		 // Push data into the CellList.
		    bansTable.setRowCount(banList.size(), true);
		    bansTable.setRowData(0, banList);

		 // Add the widgets to the root panel.
		    RootPanel.get().add(bansTable);
		  
	  
	  }

//		  final CheckBox checkBox1 = new CheckBox();
//		  final CheckBox checkBox2 = new CheckBox();
//		  
//		  FlexTable t = new FlexTable();
//		  t.setStyleName("Table-Wishlist");
//		  t.setCellSpacing(10);
//		  
//		  FlexTable t1 = new FlexTable();
//		  t1.setStyleName("Table-Wishlist");
//		  t1.setCellSpacing(10);
//		  
//		  
//		  Button deleteButton = new Button("Delete");
//		  deleteButton.setStyleName("tngly-button");
//		  
//		  
//		  t.setText(0, 0, "Mr. Tngly");
//		  
//		  t.setText(1, 0, "Age:");
//		  t.setText(2, 0, "Gender:" );
//		  t.setText(1, 1, "40");
//		  t.setText(2, 1, "Male");
//		  t.setText(1, 2, "Hobbies:");
//		  t.setText(2, 2, "Realname:");
//		  t.setText(1, 3, "Zucken");
//		  t.setText(2, 3, "Max Example");
//		  t.setWidget(3, 0, checkBox1);
//		  
//		  
//		  t1.setText(0, 0, "Kevina Hunter");
//		  
//		  t1.setText(1, 0, "Age:");
//		  t1.setText(2, 0, "Gender:" );
//		  t1.setText(1, 1, "17");
//		  t1.setText(2, 1, "Female");
//		  t1.setText(1, 2, "Hobbies:");
//		  t1.setText(2, 2, "Realname:");
//		  t1.setText(1, 3, "Auch Zucken");
//		  t1.setText(2, 3, "Maxine Example");
//		  
//		  t1.setWidget(3, 0, checkBox2);
//		  
//		  
//		
//		  
//		  RootPanel.get("Details").add(t);
//		  RootPanel.get("Details").add(t1);
//			        
//		  RootPanel.get("Details").add(deleteButton);
			        
			      

		    
	 
		    
	  
		    
		    
		    
  

		    
		  
		
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  



