package de.hdm.core.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.ProfileBan;
import de.hdm.core.shared.bo.ProfileVisit;
import de.hdm.core.shared.bo.SearchProfile;
import de.hdm.core.shared.bo.Wish;

/**
 * Das asynchrone Gegenstueck des Interface {@link AdministrationService} für
 * RPCs, um die Geschäftsobjekte zu verwalten. Es wird semiautomatisch durch das
 * Google Plugin erstellt und gepflegt. Daher erfolgt hier keine weitere
 * Dokumentation. Fuer weitere Informationen: siehe das synchrone Interface
 * {@link AdministrationService}
 * 
 * @author Kevin Jaeger
 */
public interface AdministrationServiceAsync {

	public void init(AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void createProfile(Profile profile, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void editProfile(Profile profile, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void deleteProfile(Profile profile, AsyncCallback<Void> callback) throws IllegalArgumentException;

	public void getProfileByUserName(String userEmail, AsyncCallback<Profile> callback) throws IllegalArgumentException;

	void searchAndCompareProfiles(Boolean unseenChecked, SearchProfile searchProfile,
			AsyncCallback<ArrayList<Profile>> callback);

	public void createProfileVisit(ArrayList<ProfileVisit> visitedProfiles, AsyncCallback<Void> callback)
			throws IllegalArgumentException;

	public void deleteProfileVisit(ArrayList<ProfileVisit> visitedProfiles, AsyncCallback<Void> callback)
			throws IllegalArgumentException;

	public void wasProfileVisited(Profile currentUserProfile, Profile dependantProfile, AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;

	public void addWishToWishlist(int wishedProfileId, int wishingProfileId, AsyncCallback<Wish> callback)
			throws IllegalArgumentException;

	public void deleteWishFromWishlist(int wishedProfileId, int wishingProfileId, AsyncCallback<Void> callback)
			throws IllegalArgumentException;

	public void createProfileBan(int bannedpId, int banningpId, AsyncCallback<ProfileBan> callback)
			throws IllegalArgumentException;

	public void deleteProfileBan(int bannedpId, int banningpId, AsyncCallback<Void> callback)
			throws IllegalArgumentException;

	public void getBans(AsyncCallback<ArrayList<Profile>> callback);

	public void deleteProfileBan(ArrayList<ProfileBan> toUnban, AsyncCallback<Void> callback);

	public void getWishes(AsyncCallback<ArrayList<Profile>> callback);

	public void deleteWishes(ArrayList<Wish> toUnwish, AsyncCallback<Void> callback);

	public void isProfileWished(Profile currentUserProfile, Profile selectedProfile, AsyncCallback<Boolean> callback);

	public void isProfileBanned(Profile currentUserProfile, Profile selectedProfile, AsyncCallback<Boolean> callback);
}
