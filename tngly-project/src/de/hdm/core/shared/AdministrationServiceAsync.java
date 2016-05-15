package de.hdm.core.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.core.shared.bo.Description;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.ProfileBan;
import de.hdm.core.shared.bo.Property;
import de.hdm.core.shared.bo.Selection;
import de.hdm.core.shared.bo.User;
import de.hdm.core.shared.bo.Wishlist;

public interface AdministrationServiceAsync {

	/**
	 * Initialsierungsmethode. Siehe dazu Anmerkungen zum
	 * No-Argument-Konstruktor {@link #AdministrationServiceImpl()}. Diese
	 * Methode muss fuer jede Instanz von <code>AdministrationServiceImpl</code>
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

	/**
	 * Interne Methode zur Anlage von Profilen bei Erstanmeldung eines Benutzers am System.
	 */
	public void createProfile(Profile profile, AsyncCallback<Void> callback) throws IllegalArgumentException;

	/**
	 * Aufruf dieser Methode durch den Benutzer, 
	 * um Informationen des Profils zu ändern.
	 */
	public void editProfile(Profile profile, AsyncCallback<Void> callback) throws IllegalArgumentException;

	/**
	 * Aufruf dieser Methode durch den Benutzer, 
	 * um das eigene Profil endgültig aus dem System zu löschen.
	 */
	public void deleteProfile(Profile profile, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void findProfile(String userEmail, AsyncCallback<Profile> callback) throws IllegalArgumentException;
	
	/**
	 * Aufruf dieser Methode durch den Benutzer, 
	 * um ein gesehenes Partnerprofil zu der Wunschliste des eigenen Profils hinzu zu fügen.
	 */
	public void editWishlist(Wishlist wishlist, AsyncCallback<Void> callback) throws IllegalArgumentException;

	/**
	 * Interne Methode zur Löschung von Wunschlisten bei der Löschung eines Profils durch den Benutzer.
	 */
	public void deleteWishlist(Wishlist wishlist, AsyncCallback<Void> callback) throws IllegalArgumentException;

	/**
	 * Aufruf dieser Methode durch den Benutzer, 
	 * um für ein gesehenes Partnerprofil eine Kontaktsperre zum eigenen Profil zu verfügen.
	 */
	public void createProfileBan(int profileId, AsyncCallback<ProfileBan> callback) throws IllegalArgumentException;

	/**
	 * Aufruf dieser Methode durch den Benutzer, 
	 * um die Kontaktsperre für ein gesehenes Partnerprofil zum eigenen Profil zu entfernen.
	 */
	public void deleteProfileBan(int profileId, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void createProperty(AsyncCallback<Property> callback) throws IllegalArgumentException;

	public void editProperty(Property property, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void deleteProperty(Property property, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void createSelection(int SelectionId, AsyncCallback<Property> callback) throws IllegalArgumentException;

	public void editSelection(Selection selection, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void deleteSelection(Selection selection, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void createDescription(int SelectionId, AsyncCallback<Description> callback) throws IllegalArgumentException;

	public void editDescription(Description description, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void deleteDescription(Description description, AsyncCallback<Void> callback)
			throws IllegalArgumentException;

	public void createInformation(int ProfileId, int PropertyId, AsyncCallback<Description> callback)
			throws IllegalArgumentException;

	public void editInformation(Description description, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void deleteInformation(Description description, AsyncCallback<Void> callback)
			throws IllegalArgumentException;

}
