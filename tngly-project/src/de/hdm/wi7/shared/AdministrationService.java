package de.hdm.wi7.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("administration")
public interface AdministrationService extends RemoteService{
	
	/**
	 * Initialsierungsmethode. Siehe dazu Anmerkungen zum
	 * No-Argument-Konstruktor {@link #AdministrationCommonImpl()}. Diese
	 * Methode muss fuer jede Instanz von <code>AdministrationCommonImpl</code>
	 * aufgerufen werden.
	 *
	 * @throws IllegalArgumentException
	 */
	public void init() throws IllegalArgumentException;

	/**
	 * Login Daten werden mit der Datenbank abgeglichen
	 */
	public User loginUser(boolean isReportGen) throws IllegalArgumentException;

	/**
	 * Durch den Logout wird die SessionID in der DB gespeichert und der
	 * Benutzer wird ausgeloggt
	 */
	public String logoutUser(boolean isReportGen) throws IllegalArgumentException;
	
	public void createProfile(Profile profile) throws IllegalArgumentException;
	
	public void editProfile(Profile profile) throws IllegalArgumentException;
	
	public void deleteProfile(Profile profile) throws IllegalArgumentException;
	
	public Wishlist createWishlist(int profileId) throws IllegalArgumentException;
	
	public void editWishlist(Wishlist wishlist) throws IllegalArgumentException;
	
	public void deleteWishlist(Wishlist wishlist) throws IllegalArgumentException;
	
	public ProfileBan createProfileBan(int profileId) throws IllegalArgumentException;
	
	public void deleteProfileBan(int profileId) throws IllegalArgumentException;
	
	public Property createProperty() throws IllegalArgumentException;
	
	public void editProperty(Property property) throws IllegalArgumentException;
	
	public void deleteProperty(Property property) throws IllegalArgumentException;
	
	public Property createSelection(int SelectionId) throws IllegalArgumentException;
	
	public void editSelection(Selection selection) throws IllegalArgumentException;
	
	public void deleteSelection(Selection selection) throws IllegalArgumentException;
	
	public Description createDescription(int SelectionId) throws IllegalArgumentException;
	
	public void editDescription(Description description) throws IllegalArgumentException;
	
	public void deleteDescription(Description description) throws IllegalArgumentException;
	
	public Description createInformation(int ProfileId, int PropertyId) throws IllegalArgumentException;
	
	public void editInformation(Description description) throws IllegalArgumentException;
	
	public void deleteInformation(Description description) throws IllegalArgumentException;

}
