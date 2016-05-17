package de.hdm.core.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.core.shared.bo.Description;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.ProfileBan;
import de.hdm.core.shared.bo.Property;
import de.hdm.core.shared.bo.Selection;
import de.hdm.core.shared.bo.User;
import de.hdm.core.shared.bo.Wishlist;

@RemoteServiceRelativePath("administration")
public interface AdministrationService extends RemoteService {

	/**
	 * Initialsierungsmethode. Siehe dazu Anmerkungen zum
	 * No-Argument-Konstruktor {@link #AdministrationServiceImpl()}. Diese
	 * Methode muss fuer jede Instanz von <code>AdministrationServiceImpl</code>
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

	/**
	 * Interne Methode zur Anlage von Profilen bei Erstanmeldung eines Benutzers am System.
	 */
	public void createProfile(Profile profile) throws IllegalArgumentException;

	/**
	 * Aufruf dieser Methode durch den Benutzer, 
	 * um Informationen des Profils zu ändern.
	 */
	public void editProfile(Profile profile) throws IllegalArgumentException;

	/**
	 * Aufruf dieser Methode durch den Benutzer, 
	 * um das eigene Profil endgültig aus dem System zu löschen.
	 */
	public void deleteProfile(Profile profile) throws IllegalArgumentException;

	public Profile findProfile(String userEmail) throws IllegalArgumentException;
	
	/**
	 * Aufruf dieser Methode durch den Benutzer, 
	 * um ein gesehenes Partnerprofil zu der Wunschliste des eigenen Profils hinzu zu fügen.
	 */
	public void editWishlist(Wishlist wishlist) throws IllegalArgumentException;

	/**
	 * Interne Methode zur Löschung von Wunschlisten bei der Löschung eines Profils durch den Benutzer.
	 */
	public void deleteWishlist(Wishlist wishlist) throws IllegalArgumentException;

	/**
	 * Aufruf dieser Methode durch den Benutzer, 
	 * um für ein gesehenes Partnerprofil eine Kontaktsperre zum eigenen Profil zu verfügen.
	 */
	public ProfileBan createProfileBan(int profileId) throws IllegalArgumentException;

	/**
	 * Aufruf dieser Methode durch den Benutzer, 
	 * um die Kontaktsperre für ein gesehenes Partnerprofil zum eigenen Profil zu entfernen.
	 */
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
