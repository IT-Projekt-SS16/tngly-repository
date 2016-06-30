package de.hdm.core.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.core.shared.bo.SearchProfile;
import de.hdm.core.shared.report.AllProfilesReport;

@RemoteServiceRelativePath("reportgenerator")
public interface ReportGenerator extends RemoteService{
	
	/**
	   * Initialisierung des Objekts. Diese Methode ist vor dem Hintergrund von GWT
	   * RPC zus�tzlich zum No Argument Constructor der implementierenden Klasse
	   *AdministrationServiceImpl} notwendig. Bitte diese Methode direkt nach der
	   * Instantiierung aufrufen.
	   * 
	   * @throws IllegalArgumentException
	   */
	  public void init() throws IllegalArgumentException;
	  
	  /**
	   * Erstellen eines <code>AllProfilesReport</code>-Reports. Dieser
	   * Report-Typ stellt s�mtliche Profile f�r den Benutzer dar.
	   * 
	   * @param c eine Referenz auf das Kundenobjekt bzgl. dessen der Report
	   *          erstellt werden soll.
	   * @return das fertige Reportobjekt
	   * @throws IllegalArgumentException
	   * @see AllAccountsOfCustomerReport
	   */
	  public abstract AllProfilesReport createAllProfilesReport(Boolean unseenOrAll, SearchProfile searchProfile) throws IllegalArgumentException;

	Integer testCallback() throws IllegalArgumentException;
}
