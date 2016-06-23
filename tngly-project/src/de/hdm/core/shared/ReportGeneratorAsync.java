package de.hdm.core.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.core.shared.bo.SearchProfile;
import de.hdm.core.shared.report.AllProfilesReport;

public interface ReportGeneratorAsync {
	
	void init(AsyncCallback<Void> callback);

	void createAllProfilesReport(Boolean unseenOrAll, SearchProfile searchProfile, AsyncCallback<AllProfilesReport> callback);

}
