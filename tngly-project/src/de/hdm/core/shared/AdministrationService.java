package de.hdm.core.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.core.shared.bo.Description;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.ProfileBan;
import de.hdm.core.shared.bo.ProfileVisit;
import de.hdm.core.shared.bo.Property;
import de.hdm.core.shared.bo.SearchProfile;
import de.hdm.core.shared.bo.Selection;
import de.hdm.core.shared.bo.Wish;

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
	 * Interne Methode zur Anlage von Profilen bei Erstanmeldung eines Benutzers am System.
	 */
	public void createProfile(Profile profile) throws IllegalArgumentException;

	/**
	 * Aufruf dieser Methode durch den Benutzer, 
	 * um Informationen des Profils zu �ndern.
	 */
	public void editProfile(Profile profile) throws IllegalArgumentException;

	/**
	 * Aufruf dieser Methode durch den Benutzer, 
	 * um das eigene Profil endg�ltig aus dem System zu l�schen.
	 */
	public void deleteProfile(Profile profile) throws IllegalArgumentException;

	public Profile getProfileByUserName(String userEmail) throws IllegalArgumentException;
	
	public ArrayList<Profile> searchAndCompareProfiles(Boolean unseenChecked, SearchProfile searchProfile) throws IllegalArgumentException;
	
	public void createProfileVisit(ArrayList<ProfileVisit> visitedProfiles) throws IllegalArgumentException;
	
	public void deleteProfileVisit(ArrayList<ProfileVisit> visitedProfiles) throws IllegalArgumentException;
	
	public Boolean wasProfileVisited(Profile currentUserProfile, Profile dependantProfile) throws IllegalArgumentException;
	
	public void checkUserProfile() throws IllegalArgumentException;
	
	/**
	 * Aufruf dieser Methode durch den Benutzer, 
	 * um ein gesehenes Partnerprofil zu der Wunschliste des eigenen Profils hinzu zu f�gen.
	 */
	public Wish addWishToWishlist(int wishedProfileId,int wishingProfileId) throws IllegalArgumentException;


	/**
	 * Interne Methode zur Loeschung eines Profils von der Wishlist.
	 */
	public void deleteWishFromWishlist(int wishedProfileId,int wishingProfileId) throws IllegalArgumentException;

	/**
	 * Aufruf dieser Methode durch den Benutzer, 
	 * um f�r ein gesehenes Partnerprofil eine Kontaktsperre zum eigenen Profil zu verf�gen.
	 */
	public ProfileBan createProfileBan(int bannedpId, int banningpId) throws IllegalArgumentException;

	/**
	 * Aufruf dieser Methode durch den Benutzer, 
	 * um die Kontaktsperre f�r ein gesehenes Partnerprofil zum eigenen Profil zu entfernen.
	 */
	public void deleteProfileBan(int bannedpId, int banningpId) throws IllegalArgumentException;

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

	public ArrayList<Profile> getBans() throws IllegalArgumentException;

	public void deleteProfileBan(ArrayList<ProfileBan> toUnban) throws IllegalArgumentException;

	public ArrayList<Profile> getWishes() throws IllegalArgumentException;

	public void deleteWishes(ArrayList<Wish> toUnwish) throws IllegalArgumentException;

	public Boolean isProfileWished(Profile currentUserProfile, Profile selectedProfile) throws IllegalArgumentException;

	public Boolean isProfileBanned(Profile currentUserProfile, Profile selectedProfile) throws IllegalArgumentException;

	int testCallback() throws IllegalArgumentException;

}
