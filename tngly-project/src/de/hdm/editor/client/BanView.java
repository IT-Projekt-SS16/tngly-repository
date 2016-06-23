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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.shared.AdministrationServiceAsync;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.ProfileBan;

public class BanView extends Update{


	  protected String getHeadlineText() {
	    return "";
	  }
	  
	  final Button removeButton = new Button("Remove");
	  private AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();
	  private final ArrayList<ProfileBan> banList = ClientsideSettings.getBanlist();
	  private ListDataProvider<ProfileBan> dataProvider = new ListDataProvider<ProfileBan>();

	  protected void run() {
		  this.append("Here you will see your list of banned profiles");
		  adminService.getBanlist(getBanlistCallback());
			  
			    
		
		  DataGrid<ProfileBan> bansTable = new DataGrid<ProfileBan>();
		  bansTable.setWidth("100%");
		   
		  
		  bansTable.setEmptyTableWidget(new Label("You do not have a ban"));	  
		  
		  
		  
		// Add a selection model to handle user selection.
		    final MultiSelectionModel<ProfileBan> selectionModel = new MultiSelectionModel<ProfileBan>();
		    bansTable.setSelectionModel(selectionModel);
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
		    bansTable.addColumn(userNameColumn, "Username");

		    // Add a text column to show the name.
		    TextColumn<ProfileBan> nameColumn = new TextColumn<ProfileBan>() {
		      @Override
		      public String getValue(ProfileBan pb) {
		        return pb.getBannedProfile().getName();
		      }
		    };
		    bansTable.addColumn(nameColumn, "Name");
		    
		    // Add a text column to show the lastname.
		    TextColumn<ProfileBan> lastNameColumn = new TextColumn<ProfileBan>() {
		      @Override
		      public String getValue(ProfileBan pb) {
		        return pb.getBannedProfile().getLastName();
		      }
		    };
		    bansTable.addColumn(lastNameColumn, "LastName");
		    
		 // Add a date column to show the birthday.
		    DateCell dateCell = new DateCell();
		    Column<ProfileBan, Date> dateColumn = new Column<ProfileBan, Date>(dateCell) {
		      @Override
		      public Date getValue(ProfileBan pb) {
		        return pb.getBannedProfile().getDateOfBirth();
		      }
		    };
		    bansTable.addColumn(dateColumn, "Birthday");
		    
		    // Add a text column to show the gender.
		    TextColumn<ProfileBan> genderColumn = new TextColumn<ProfileBan>() {
		      @Override
		      public String getValue(ProfileBan pb) {
		        return pb.getBannedProfile().getGender();
		      }
		    };
		    bansTable.addColumn(genderColumn, "Gender");
		    
		    dataProvider.addDataDisplay(bansTable);
	  

		    
		 // Push data into the CellList.
		    bansTable.setRowCount(banList.size(), true);
		    bansTable.setRowData(0, banList);

		 // Add the widgets to the root panel.
		    RootPanel.get("Details").add(bansTable);
		  
	  
	  }
	  
	  private AsyncCallback<ArrayList<ProfileBan>> getBanlistCallback() {
			AsyncCallback<ArrayList<ProfileBan>> asyncCallback = new AsyncCallback<ArrayList<ProfileBan>>() {

				@Override
				public void onFailure(Throwable caught) {
					ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
				}

				@Override 
				public void onSuccess(ArrayList<ProfileBan> result) {
					ClientsideSettings.getLogger().severe("Success: " + result.getClass().getSimpleName());
					dataProvider.setList(result);
				}
			};
			return asyncCallback;
		}
}

