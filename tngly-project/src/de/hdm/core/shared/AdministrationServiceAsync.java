package de.hdm.core.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.core.shared.bo.Description;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.ProfileBan;
import de.hdm.core.shared.bo.Property;
import de.hdm.core.shared.bo.SearchProfile;
import de.hdm.core.shared.bo.Selection;
import de.hdm.core.shared.bo.User;
import de.hdm.core.shared.bo.Wish;
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

//	/**
//	 * Login Daten werden mit der Datenbank abgeglichen
//	 */
//	public void loginUser(boolean isReportGen, AsyncCallback<User> callback) throws IllegalArgumentException;
//
//	/**
//	 * Durch den Logout wird die SessionID in der DB gespeichert und der
//	 * Benutzer wird ausgeloggt
//	 */
//	public void logoutUser(boolean isReportGen, AsyncCallback<String> callback) throws IllegalArgumentException;

	/**
	 * Interne Methode zur Anlage von Profilen bei Erstanmeldung eines Benutzers am System.
	 */
	public void createProfile(Profile profile, AsyncCallback<Void> callback) throws IllegalArgumentException;

	/**
	 * Aufruf dieser Methode durch den Benutzer, 
	 * um Informationen des Profils zu �ndern.
	 */
	public void editProfile(Profile profile, AsyncCallback<Void> callback) throws IllegalArgumentException;

	/**
	 * Aufruf dieser Methode durch den Benutzer, 
	 * um das eigene Profil endg�ltig aus dem System zu l�schen.
	 */
	public void deleteProfile(Profile profile, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void findProfileByName(String userEmail, AsyncCallback<Profile> callback) throws IllegalArgumentException;
	
	public void searchAndCompareProfiles(SearchProfile searchProfile, AsyncCallback<ArrayList<Profile>> callback) throws IllegalArgumentException;
	
	/**
	 * Aufruf dieser Methode durch den Benutzer, 
	 * um ein gesehenes Partnerprofil zu der Wunschliste des eigenen Profils hinzu zu f�gen.
	 */
	public void addProfileToWishlist(Wish wishlist, AsyncCallback<Void> callback) throws IllegalArgumentException;

	/**
	 * Interne Methode zur L�schung von Wunschlisten bei der L�schung eines Profils durch den Benutzer.
	 */
	public void deleteWishlist(Wish wishlist, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	/**
	 * Interne Methode zur Loeschung eines Profils von der Wishlist.
	 */
	public void deleteProfileFromWishlist(Wish wishlist, AsyncCallback<Void> callback) throws IllegalArgumentException;

	/**
	 * Aufruf dieser Methode durch den Benutzer, 
	 * um f�r ein gesehenes Partnerprofil eine Kontaktsperre zum eigenen Profil zu verf�gen.
	 */
	public void createProfileBan(int profileId, AsyncCallback<ProfileBan> callback) throws IllegalArgumentException;

	/**
	 * Aufruf dieser Methode durch den Benutzer, 
	 * um die Kontaktsperre f�r ein gesehenes Partnerprofil zum eigenen Profil zu entfernen.
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
