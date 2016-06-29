package de.hdm.editor.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.ProfileVisit;

public class ShowProfilesView extends Update {

	@Override
	protected String getHeadlineText() {
		return "";
	}

	@Override
	protected void run() {
		this.append("Here you will see your search results");

		FlexTable contentTable = new FlexTable();
		final Button markAsSeenButton;
		final Button markAsUnseenButton;
		final FlexTable profilesTable = new FlexTable();

		DecoratorPanel contentTableDecorator = new DecoratorPanel();
		contentTableDecorator.setWidth("100%");
		contentTableDecorator.setWidth("18em");

		contentTable.setWidth("100%");
		contentTable.getCellFormatter().addStyleName(0, 0, "profiles-ListContainer");
		contentTable.getCellFormatter().setWidth(0, 0, "100%");

		contentTableDecorator.add(contentTable);
		RootPanel.get("Details").add(contentTableDecorator);

		// Create the menu
		//
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setBorderWidth(0);
		hPanel.setSpacing(0);
		hPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		markAsSeenButton = new Button("Mark as seen");
		hPanel.add(markAsSeenButton);
		markAsUnseenButton = new Button("Mark as unseen");
		hPanel.add(markAsUnseenButton);
		contentTable.getCellFormatter().addStyleName(0, 0, "profiles-ListMenu");
		contentTable.setWidget(0, 0, hPanel);
		// vPanel.add(hPanel);

		ClientsideSettings.getLogger().info("Buttons werden aufgebaut");

		// Create the profiles list
		//
		profilesTable.setCellSpacing(0);
		profilesTable.setCellPadding(0);
		profilesTable.setWidth("100%");
		profilesTable.addStyleName("profiles-ListContents");
		profilesTable.getColumnFormatter().setWidth(0, "15px");
		contentTable.setWidget(1, 0, profilesTable);

		profilesTable.removeAllRows();

		ClientsideSettings.getLogger().info("Profil-Tabelle wird aufgebaut");
		ArrayList<Profile> list = ClientsideSettings.getProfilesFoundAndCompared();
		ClientsideSettings.getLogger().info("Profil-Liste gesetzt");
		ClientsideSettings.getLogger().info(list.toString());

		refreshData(profilesTable);
		
		

		profilesTable.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				int selectedRow = this.getClickedRow(event);
				
				profilesTable.getWidget(selectedRow, 1).addStyleName("label-Profile-Teaser-regular");
				
				ArrayList<ProfileVisit> pvs = new ArrayList<ProfileVisit>();

				if (selectedRow >= 0) {
					ProfileVisit pv = new ProfileVisit();
					pv.setVisitingProfileId(ClientsideSettings.getUserProfile().getId());
					pv.setVisitedProfileId(ClientsideSettings.getProfilesFoundAndCompared().get(selectedRow).getId());
					pvs.add(pv);
					ClientsideSettings.getAdministration().createProfileVisit(pvs, new AsyncCallback<Void>() {
						@Override
						public void onSuccess(Void result) {

						}

						@Override
						public void onFailure(Throwable caught) {
							ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
						}
					});
					Profile selectedProfile = ClientsideSettings.getProfilesFoundAndCompared().get(selectedRow);
					selectedProfile.setWasVisited(true);
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
			@Override
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
					@Override
					public void onSuccess(Void result) {

					}

					@Override
					public void onFailure(Throwable caught) {
						ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
					}
				});
				this.refreshData(profilesTable);
			}

			private ArrayList<Integer> getSelectedRows() {
				ArrayList<Integer> selectedRows = new ArrayList<Integer>();

				for (int i = 0; i < profilesTable.getRowCount(); ++i) {
					CheckBox checkBox = (CheckBox) profilesTable.getWidget(i, 0);
					if (checkBox.getValue()) {
						selectedRows.add(i);
					}
				}

				return selectedRows;
			}
			
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
		});

		markAsUnseenButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ArrayList<Integer> selectedRows = this.getSelectedRows();
				ArrayList<ProfileVisit> pvs = new ArrayList<ProfileVisit>();

				for (int i = 0; i < selectedRows.size(); ++i) {
					ProfileVisit pv = new ProfileVisit();
					pv.setVisitingProfileId(ClientsideSettings.getUserProfile().getId());
					pv.setVisitedProfileId(
							ClientsideSettings.getProfilesFoundAndCompared().get(selectedRows.get(i)).getId());
					ClientsideSettings.getProfilesFoundAndCompared().get(selectedRows.get(i)).setWasVisited(false);
					pvs.add(pv);
				}
				ClientsideSettings.getAdministration().deleteProfileVisit(pvs, new AsyncCallback<Void>() {
					@Override
					public void onSuccess(Void result) {

					}

					@Override
					public void onFailure(Throwable caught) {
						ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
					}
				});
				this.refreshData(profilesTable);
			}

			private ArrayList<Integer> getSelectedRows() {
				ArrayList<Integer> selectedRows = new ArrayList<Integer>();

				for (int i = 0; i < profilesTable.getRowCount(); ++i) {
					CheckBox checkBox = (CheckBox) profilesTable.getWidget(i, 0);
					if (checkBox.getValue()) {
						selectedRows.add(i);
					}
				}

				return selectedRows;
			}
			
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
		});
	}

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
}