package de.hdm.core.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.core.shared.bo.Description;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.ProfileBan;
import de.hdm.core.shared.bo.ProfileVisit;
import de.hdm.core.shared.bo.Property;
import de.hdm.core.shared.bo.SearchProfile;
import de.hdm.core.shared.bo.Selection;
import de.hdm.core.shared.bo.Wish;

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

	public void getProfileByUserName(String userEmail, AsyncCallback<Profile> callback) throws IllegalArgumentException;
	
	public void searchAndCompareProfiles(Boolean unseenChecked, SearchProfile searchProfile, AsyncCallback<ArrayList<Profile>> callback) throws IllegalArgumentException;
	
	public void createProfileVisit(ArrayList<ProfileVisit> visitedProfiles, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void deleteProfileVisit(ArrayList<ProfileVisit> visitedProfiles, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void wasProfileVisited(Profile currentUserProfile, Profile dependantProfile, AsyncCallback<Boolean> callback) throws IllegalArgumentException;
	
	public void checkUserProfile(AsyncCallback<Void> callback);
	
	/**
	 * Aufruf dieser Methode durch den Benutzer, 
	 * um ein gesehenes Partnerprofil zu der Wunschliste des eigenen Profils hinzu zu f�gen.
	 */
	public void addWishToWishlist(int wishedProfileId,int wishingProfileId, AsyncCallback<Wish> callback) throws IllegalArgumentException;

	/**
	 * Interne Methode zur L�schung von Wunschlisten bei der L�schung eines Profils durch den Benutzer.
	 */
	
	/**
	 * Interne Methode zur Loeschung eines Profils von der Wishlist.
	 */
	public void deleteWishFromWishlist(int wishedProfileId,int wishingProfileId, AsyncCallback<Void> callback) throws IllegalArgumentException;

	/**
	 * Aufruf dieser Methode durch den Benutzer, 
	 * um f�r ein gesehenes Partnerprofil eine Kontaktsperre zum eigenen Profil zu verf�gen.
	 */
	public void createProfileBan(int bannedpId, int banningpId, AsyncCallback<ProfileBan> callback) throws IllegalArgumentException;

	/**
	 * Aufruf dieser Methode durch den Benutzer, 
	 * um die Kontaktsperre f�r ein gesehenes Partnerprofil zum eigenen Profil zu entfernen.
	 */
	public void deleteProfileBan(int bannedpId, int banningpId, AsyncCallback<Void> callback) throws IllegalArgumentException;

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

	public void getBans(AsyncCallback<ArrayList<Profile>> callback);

	public void deleteProfileBan(ArrayList<ProfileBan> toUnban, AsyncCallback<Void> callback);

	public void getWishes(AsyncCallback<ArrayList<Profile>> callback);

	public void deleteWishes(ArrayList<Wish> toUnwish, AsyncCallback<Void> callback);
}
