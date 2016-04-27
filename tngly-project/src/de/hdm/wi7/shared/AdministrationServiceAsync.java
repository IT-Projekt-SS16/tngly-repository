package de.hdm.wi7.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AdministrationServiceAsync {
	
	/**
	 * Initialsierungsmethode. Siehe dazu Anmerkungen zum
	 * No-Argument-Konstruktor {@link #AdministrationCommonImpl()}. Diese
	 * Methode muss fuer jede Instanz von <code>AdministrationCommonImpl</code>
	 * aufgerufen werden.
	 *
	 * @throws IllegalArgumentException
	 */
	public void init(AsyncCallback<Void> callback) throws IllegalArgumentException;

	/**
	 * Login Daten werden mit der Datenbank abgeglichen
	 */
	public void loginUser(boolean isReportGen, AsyncCallback<User> callback) throws IllegalArgumentException;

	/**
	 * Durch den Logout wird die SessionID in der DB gespeichert und der
	 * Benutzer wird ausgeloggt
	 */
	public void logoutUser(boolean isReportGen, AsyncCallback<String> callback) throws IllegalArgumentException;
	
	public void createProfile(Profile profile, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void editProfile(Profile profile, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void deleteProfile(Profile profile, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void editWishlist(Wishlist wishlist, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void deleteWishlist(Wishlist wishlist, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void createProfileBan(int profileId, AsyncCallback<ProfileBan> callback) throws IllegalArgumentException;
	
	public void deleteProfileBan(int profileId, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void createProperty(AsyncCallback<Property> callback) throws IllegalArgumentException;
	
	public void editProperty(Property property, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void deleteProperty(Property property, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void createSelection(int SelectionId, AsyncCallback<Property> callback) throws IllegalArgumentException;
	
	public void editSelection(Selection selection, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void deleteSelection(Selection selection, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void createDescription(int SelectionId, AsyncCallback<Description> callback) throws IllegalArgumentException;
	
	public void editDescription(Description description, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void deleteDescription(Description description, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void createInformation(int ProfileId, int PropertyId, AsyncCallback<Description> callback) throws IllegalArgumentException;
	
	public void editInformation(Description description, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void deleteInformation(Description description, AsyncCallback<Void> callback) throws IllegalArgumentException;

}
