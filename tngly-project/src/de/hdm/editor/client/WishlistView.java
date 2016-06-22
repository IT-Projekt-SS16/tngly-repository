package de.hdm.editor.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.server.AdministrationServiceImpl;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.ProfileBan;
import de.hdm.core.shared.bo.ProfileVisit;
import de.hdm.core.shared.bo.Wish;

public class WishlistView extends Update{


	  protected String getHeadlineText() {
	    return "";
	  }
	 
	  private final ArrayList<Wish> wishList = ClientsideSettings.getWishlist();

	  protected void run() {
		  this.append("Here you will see your list of wished profiles");

		  DataGrid<Wish> wishTable = new DataGrid<Wish>();
		  wishTable.setWidth("100%");
		   
		  
		  wishTable.setEmptyTableWidget(new Label("You do not have a ban"));	  
		  
		  
		// Add a selection model to handle user selection.
		    final MultiSelectionModel<Wish> selectionModel = new MultiSelectionModel<Wish>();
		    wishTable.setSelectionModel(selectionModel);
		    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
		      public void onSelectionChange(SelectionChangeEvent event) {
		        Set<Wish> selected = selectionModel.getSelectedSet();
		        if (selected != null) {
		          Window.alert("You selected: " + selected.size() + "Profiles");
		        }
		      }
		    });
		  
		  Column<Wish, Boolean> checkColumn =
			        new Column<Wish, Boolean>(new CheckboxCell(true, false)) {
			          @Override
			          public Boolean getValue(Wish wish) {
			            // Get the value from the selection model.
			            return selectionModel.isSelected(wish);
			          }
			        };
			   wishTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
			    wishTable.setColumnWidth(checkColumn, 40, Unit.PX);
			    
		    // Add a text column to show the username.
		    TextColumn<Wish> userNameColumn = new TextColumn<Wish>() {
		      @Override
		      public String getValue(Wish wish) {
		        return wish.getWishedProfile().getUserName();
		      }
		    };
		    wishTable.addColumn(userNameColumn, "Username");

		    // Add a text column to show the name.
		    TextColumn<Wish> nameColumn = new TextColumn<Wish>() {
		      @Override
		      public String getValue(Wish wish) {
		        return wish.getWishedProfile().getName();
		      }
		    };
		    wishTable.addColumn(nameColumn, "Name");
		    
		    // Add a text column to show the lastname.
		    TextColumn<Wish> lastNameColumn = new TextColumn<Wish>() {
		      @Override
		      public String getValue(Wish wish) {
		        return wish.getWishedProfile().getLastName();
		      }
		    };
		    wishTable.addColumn(lastNameColumn, "LastName");
		    
		 // Add a date column to show the birthday.
		    DateCell dateCell = new DateCell();
		    Column<Wish, Date> dateColumn = new Column<Wish, Date>(dateCell) {
		      @Override
		      public Date getValue(Wish wish) {
		        return wish.getWishedProfile().getDateOfBirth();
		      }
		    };
		    wishTable.addColumn(dateColumn, "Birthday");
		    
		    // Add a text column to show the gender.
		    TextColumn<Wish> genderColumn = new TextColumn<Wish>() {
		      @Override
		      public String getValue(Wish wish) {
		        return wish.getWishedProfile().getGender();
		      }
		    };
		    wishTable.addColumn(genderColumn, "Gender");
		    
			  
	  
	  

		    
		 // Push data into the CellList.
		    wishTable.setRowCount(wishList.size(), true);
		    wishTable.setRowData(0, wishList);

		 // Add the widgets to the root panel.
		    RootPanel.get().add(wishTable);
		  
	  
	  
	

			/**DecoratorPanel contentTableDecorator = new DecoratorPanel();
			contentTableDecorator.setWidth("100%");
			contentTableDecorator.setWidth("18em");

			contentTable.setWidth("100%");
			contentTable.getCellFormatter().addStyleName(0, 0, "profiles-ListContainer");
			contentTable.getCellFormatter().setWidth(0, 0, "100%");

			contentTableDecorator.add(contentTable);
			RootPanel.get("Details").add(contentTableDecorator);
			 **/
			// Create the menu
			//
			HorizontalPanel hPanel = new HorizontalPanel();
			hPanel.setBorderWidth(0);
			hPanel.setSpacing(0);
			hPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
			//markAsSeenButton = new Button("Mark as seen");
			//hPanel.add(markAsSeenButton);
			//markAsUnseenButton = new Button("Mark as unseen");
			//hPanel.add(markAsUnseenButton);
			//removeButton = new Button("Remove");
			//hPanel.add(removeButton);
			//contentTable.getCellFormatter().addStyleName(0, 0, "profiles-ListMenu");
			//contentTable.setWidget(0, 0, hPanel);
			// vPanel.add(hPanel);

			ClientsideSettings.getLogger().info("Buttons werden aufgebaut");

			// Create the profiles list
			//
			/**wishesTable.setCellSpacing(0);
			wishesTable.setCellPadding(0);
			wishesTable.setWidth("100%");
			wishesTable.addStyleName("profiles-ListContents");
			wishesTable.getColumnFormatter().setWidth(0, "15px");
			contentTable.setWidget(1, 0, wishesTable);
			
			wishesTable.removeAllRows();
			**/
			
			ClientsideSettings.getLogger().info("Wishlist wird aufgebaut");
			ArrayList<Wish> list = ClientsideSettings.getWishlist();
			ClientsideSettings.getLogger().info("Profil-Liste gesetzt");
			ClientsideSettings.getLogger().info(list.toString());
			}
			}

						
		   
			/**refreshData(wishesTable);
			
			

			wishesTable.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					int selectedRow = this.getClickedRow(event);
					
					wishesTable.getWidget(selectedRow, 1).addStyleName("label-Profile-Teaser-regular");
					
					ArrayList<ProfileVisit> pvs = new ArrayList<ProfileVisit>();

					if (selectedRow >= 0) {
						ProfileVisit pv = new ProfileVisit();
						pv.setVisitingProfileId(ClientsideSettings.getUserProfile().getId());
						pv.setVisitedProfileId(ClientsideSettings.getWishlist().get(selectedRow).getId());
						pvs.add(pv);
						ClientsideSettings.getAdministration().createProfileVisit(pvs, new AsyncCallback<Void>() {
							public void onSuccess(Void result) {

							}

							public void onFailure(Throwable caught) {
								ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
							}
						});
						**/
//						Profile selectedProfile = ClientsideSettings.getWishlist().get(selectedRow).getWishedProfile();
//						//selectedProfile.setWasVisited(true);
//						Update update = new OtherProfileView(selectedProfile);
//
//						RootPanel.get("Details").clear();
//						RootPanel.get("Details").add(update);
					

				

				/**private int getClickedRow(ClickEvent event) {
					int selectedRow = -1;
					HTMLTable.Cell cell = wishesTable.getCellForEvent(event);

					if (cell != null) {
						// Suppress clicks if the user is actually selecting the
						// check box
						//
						if (cell.getCellIndex() > 0) {
							selectedRow = cell.getRowIndex();
						}
					}
					return selectedRow;
				}
			});

			markAsSeenButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					ArrayList<Integer> selectedRows = this.getSelectedRows();
					ArrayList<ProfileVisit> pvs = new ArrayList<ProfileVisit>();

					for (int i = 0; i < selectedRows.size(); ++i) {
						ProfileVisit pv = new ProfileVisit();
						pv.setVisitingProfileId(ClientsideSettings.getUserProfile().getId());
						pv.setVisitedProfileId(
								ClientsideSettings.getProfilesFoundAndCompared().get(selectedRows.get(i)).getId());
						ClientsideSettings.getProfilesFoundAndCompared().get(selectedRows.get(i)).setWasVisited(true);
						pvs.add(pv);
					}
					ClientsideSettings.getAdministration().createProfileVisit(pvs, new AsyncCallback<Void>() {
						public void onSuccess(Void result) {

						}

						public void onFailure(Throwable caught) {
							ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
						}
					});
					this.refreshData(profilesTable);
				}**/

				/**
				private void refreshData(final FlexTable profilesTable) {
					for (int i = 0; i < ClientsideSettings.getProfilesFoundAndCompared().size(); ++i) {
						profilesTable.setWidget(i, 0, new CheckBox());
						Label lblProfileTeaser = new Label(ClientsideSettings.getProfilesFoundAndCompared().get(i).toString());
						if (ClientsideSettings.getProfilesFoundAndCompared().get(i).getWasVisited() == false) {
							lblProfileTeaser.addStyleName("label-Profile-Teaser-bold");
						}
						profilesTable.setWidget(i, 1, lblProfileTeaser);
					}
				}
			});**/

			//removeButton.addClickHandler(new ClickHandler() {
				//public void onClick(ClickEvent event) {
					//ArrayList<Integer> selectedRows = this.getSelectedRows();
					
					//for (int i = 0; i < selectedRows.size(); ++i) {
						
						
						//ClientsideSettings.getAdministration().deleteWishFromWishlist(ClientsideSettings.getUserProfile().getId(), ClientsideSettings.getWishlist().get(i).getId(), new DeleteCallback());
					//}
					//ClientsideSettings.getAdministration().deleteProfileVisit(pvs, new AsyncCallback<Void>() {
					//	public void onSuccess(Void result) {

					//	}

						//public void onFailure(Throwable caught) {
							//ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
						//}
					//});
					//this.refreshData(wishTable);


				/**private ArrayList<Integer> getSelectedRows() {
					ArrayList<Integer> selectedRows = new ArrayList<Integer>();

					for (int i = 0; i < wishesTable.getRowCount(); ++i) {
						CheckBox checkBox = (CheckBox) wishesTable.getWidget(i, 0);
						if (checkBox.getValue()) {
							selectedRows.add(i);
						}
					}

					return selectedRows;
				}
				
				private void refreshData(final FlexTable wishesTable) {
					for (int i = 0; i < ClientsideSettings.getWishlist().size(); ++i) {
						wishesTable.setWidget(i, 0, new CheckBox());
						Label lblProfileTeaser = new Label(ClientsideSettings.getWishlist().get(i).toString());
						if (ClientsideSettings.getProfilesFoundAndCompared().get(i).getWasVisited() == false) {
							lblProfileTeaser.addStyleName("label-Profile-Teaser-bold");
						}
						wishesTable.setWidget(i, 1, lblProfileTeaser);
					}
				}
			});
		}

		
		  final CheckBox checkBox1 = new CheckBox();
		  final CheckBox checkBox2 = new CheckBox();
		  
		  FlexTable t = new FlexTable();
		  t.setStyleName("Table-Wishlist");
		  t.setCellSpacing(10);
		  
		  FlexTable t1 = new FlexTable();
		  t1.setStyleName("Table-Wishlist");
		  t1.setCellSpacing(10);
		  
		  
		  Button deleteButton = new Button("Delete");
		  deleteButton.setStyleName("tngly-button");
		  
		  
		  t.setText(0, 0, "Mr. Tngly");
		  
		  t.setText(1, 0, "Age:");
		  t.setText(2, 0, "Gender:" );
		  t.setText(1, 1, "40");
		  t.setText(2, 1, "Male");
		  t.setText(1, 2, "Hobbies:");
		  t.setText(2, 2, "Realname:");
		  t.setText(1, 3, "Zucken");
		  t.setText(2, 3, "Max Example");
		  t.setWidget(3, 0, checkBox1);
		  
		  
		  t1.setText(0, 0, "Kevina Hunter");
		  
		  t1.setText(1, 0, "Age:");
		  t1.setText(2, 0, "Gender:" );
		  t1.setText(1, 1, "17");
		  t1.setText(2, 1, "Female");
		  t1.setText(1, 2, "Hobbies:");
		  t1.setText(2, 2, "Realname:");
		  t1.setText(1, 3, "Auch Zucken");
		  t1.setText(2, 3, "Maxine Example");
		  
		  t1.setWidget(3, 0, checkBox2);
		  
		  
		
		  
		  RootPanel.get("Details").add(t);
		  RootPanel.get("Details").add(t1);
			        
		  RootPanel.get("Details").add(deleteButton);
		  **/

		    
	 
		    
	  
		    
		    
		    
  

		    
		  
		
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  

