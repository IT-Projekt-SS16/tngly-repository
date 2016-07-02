package de.hdm.core.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.core.server.AdministrationServiceImpl;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.ProfileBan;
import de.hdm.core.shared.bo.ProfileVisit;
import de.hdm.core.shared.bo.SearchProfile;
import de.hdm.core.shared.bo.Wish;

/**
 * Das synchrone Gegenstueck des Interface {@link AdministrationServiceAsync}
 * für RPCs, um die Geschäftsobjekte zu verwalten. Es erfolgt hier keine weitere
 * Dokumentation. Fuer weitere Informationen: siehe die Implementierung des
 * Interface {@link AdministrationServiceImpl}
 * 
 * @author Kevin Jaeger
 */
@RemoteServiceRelativePath("administration")
public interface AdministrationService extends RemoteService {

	public void init() throws IllegalArgumentException;

	public void createProfile(Profile profile) throws IllegalArgumentException;

	public void editProfile(Profile profile) throws IllegalArgumentException;

	public void deleteProfile(Profile profile) throws IllegalArgumentException;

	public Profile getProfileByUserName(String userEmail) throws IllegalArgumentException;

	public ArrayList<Profile> searchAndCompareProfiles(Boolean unseenChecked, SearchProfile searchProfile)
			throws IllegalArgumentException;

	public void createProfileVisit(ArrayList<ProfileVisit> visitedProfiles) throws IllegalArgumentException;

	public void deleteProfileVisit(ArrayList<ProfileVisit> visitedProfiles) throws IllegalArgumentException;

	public Boolean wasProfileVisited(Profile currentUserProfile, Profile dependantProfile)
			throws IllegalArgumentException;

	public Wish addWishToWishlist(int wishedProfileId, int wishingProfileId) throws IllegalArgumentException;

	public void deleteWishFromWishlist(int wishedProfileId, int wishingProfileId) throws IllegalArgumentException;

	public ProfileBan createProfileBan(int bannedpId, int banningpId) throws IllegalArgumentException;

	public void deleteProfileBan(int bannedpId, int banningpId) throws IllegalArgumentException;

	public ArrayList<Profile> getBans() throws IllegalArgumentException;

	public void deleteProfileBan(ArrayList<ProfileBan> toUnban) throws IllegalArgumentException;

	public ArrayList<Profile> getWishes() throws IllegalArgumentException;

	public void deleteWishes(ArrayList<Wish> toUnwish) throws IllegalArgumentException;

	public Boolean isProfileWished(Profile currentUserProfile, Profile selectedProfile) throws IllegalArgumentException;

	public Boolean isProfileBanned(Profile currentUserProfile, Profile selectedProfile) throws IllegalArgumentException;
}
