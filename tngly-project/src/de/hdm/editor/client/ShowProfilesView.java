package de.hdm.editor.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.shared.bo.Profile;

public class ShowProfilesView extends Update {

	protected String getHeadlineText() {
		return "Search Result";
	}

	protected void run() {
		this.append("Here you will see your search results");

		FlexTable contentTable = new FlexTable();
		final Button markAsReadButton;
		final Button markAsUnreadButton;
		final FlexTable profilesTable;

		// t.setText(0, 0, "Philly Vanilli");
		// t.setWidget(0, 1, new Button("remove"));
		//
		// t.setText(1, 0, "Kevin Jaeger");
		// t.setWidget(1, 1, new Button("remove"));
		//
		// t.setText(2, 0, "Hans Wurst");
		// t.setWidget(2, 1, new Button("remove"));
		//
		// t.setText(3, 0, "Mr. Cheese");
		// t.setWidget(3, 1, new Button("remove"));

		DecoratorPanel contentTableDecorator = new DecoratorPanel();
		contentTableDecorator.setWidth("100%");
		contentTableDecorator.setWidth("18em");

		contentTable.setWidth("100%");
		contentTable.getCellFormatter().addStyleName(0, 0, "profiles-ListContainer");
		contentTable.getCellFormatter().setWidth(0, 0, "100%");

		// Create the menu
		//
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setBorderWidth(0);
		hPanel.setSpacing(0);
		hPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		markAsReadButton = new Button("Mark as read");
		hPanel.add(markAsReadButton);
		markAsUnreadButton = new Button("Mark as unread");
		hPanel.add(markAsUnreadButton);
		contentTable.getCellFormatter().addStyleName(0, 0, "profiles-ListMenu");
		contentTable.setWidget(0, 0, hPanel);
		
		// Create the profiles list
	    //
		profilesTable = new FlexTable();
		profilesTable.setCellSpacing(0);
		profilesTable.setCellPadding(0);
		profilesTable.setWidth("100%");
		profilesTable.addStyleName("profiles-ListContents");
		profilesTable.getColumnFormatter().setWidth(0, "15px");
	    contentTable.setWidget(1, 0, profilesTable);
	    
	    contentTableDecorator.add(contentTable);
	    
	    profilesTable.removeAllRows();
	    
	    for (int i = 0; i < ClientsideSettings.getProfilesFoundAndCompared().size(); ++i) {
	      profilesTable.setWidget(i, 0, new CheckBox());
	      profilesTable.setText(i, 1, ClientsideSettings.getProfilesFoundAndCompared().get(i).toString());
	    }
	    
	    profilesTable.addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	          int selectedRow = this.getClickedRow(event);
	          
	          if (selectedRow >= 0) {
	            Profile selectedProfile = ClientsideSettings.getProfilesFoundAndCompared().get(selectedRow);
	            Update update = new OtherProfileView(selectedProfile);

				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
	          }
	        }

			private int getClickedRow(ClickEvent event) {
				int selectedRow = -1;
			    HTMLTable.Cell cell = profilesTable.getCellForEvent(event);
			    
			    if (cell != null) {
			      // Suppress clicks if the user is actually selecting the 
			      //  check box
			      //
			      if (cell.getCellIndex() > 0) {
			        selectedRow = cell.getRowIndex();
			      }
			    }
			    return selectedRow;
			}
	      });

		RootPanel.get("Details").add(contentTableDecorator);

	}
}