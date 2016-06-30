package de.hdm.core.shared.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.view.client.ProvidesKey;

public class Profile implements Serializable, Comparable<Profile> {

	/*
	 * Attributes
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final ProvidesKey<Profile> KEY_PROVIDER = null;

	private int similiarityToReference;

	// The profile´s ID
	private int id;

	// The profile´s username
	private String userName;

	// The real (pre)name of the person
	private String name;

	// The real last name of the person
	private String lastName;

	// The persons date of birth
	private Date dateOfBirth;

	// The person´s gender
	private String gender;

	// The body height of the person
	private float bodyHeight;

	// The person´s hair colour
	private String hairColour;

	// The person´s religious confession (e.g. catholic, muslim..)
	private String confession;

	// Is the person smoking? (e.g. yes, no)
	private int isSmoking;

	// The person´s movie
	private String movie;

	private String band;

	private Boolean wasVisited;

	private ArrayList<Wish> wishlist;

	private ArrayList<ProfileBan> banlist;

	private ArrayList<Description> descriptionList;
	private ArrayList<Selection> selectionList;

	private boolean isFavorite;

	private boolean isBanned;

	public Profile() {
		this.setWishlist(new ArrayList<Wish>());
		this.setBanlist(new ArrayList<ProfileBan>());
		this.setDescriptionList(new ArrayList<Description>());
		this.setSelectionList(new ArrayList<Selection>());
		this.setWasVisited(false);
		this.setIsFavorite(false);
	}

	/*
	 * Get-/Set-Operations + toString
	 */

	public ArrayList<Wish> getWishlist() {
		return wishlist;
	}

	public void setWishlist(ArrayList<Wish> wishlist) {
		this.wishlist = wishlist;
	}

	public ArrayList<ProfileBan> getBanlist() {
		return banlist;
	}

	public void setBanlist(ArrayList<ProfileBan> banlist) {
		this.banlist = banlist;
	}

	public ArrayList<Description> getDescriptionList() {
		return descriptionList;
	}

	public void setDescriptionList(ArrayList<Description> descriptionList) {
		this.descriptionList = descriptionList;
	}

	public ArrayList<Selection> getSelectionList() {
		return selectionList;
	}

	public void setSelectionList(ArrayList<Selection> selectionList) {
		this.selectionList = selectionList;
	}

	// Get profile´s ID
	public int getId() {
		return this.id;
	}

	// Set profile´s ID
	public void setId(int id) {
		this.id = id;
	}

	// Get profile´s user name
	public String getUserName() {
		return this.userName;
	}

	// Set profile´s user name
	public void setUserName(String userName) {
		this.userName = userName;
	}

	// Get person´s real (pre)name
	public String getName() {
		return this.name;
	}

	// Set person´s real (pre)name
	public void setName(String name) {
		this.name = name;
	}

	// Get person´s real last name
	public String getLastName() {
		return this.lastName;
	}

	// Set person´s real last name
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	// Get person´s date of birth
	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	// Set person´s date of birth
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	// Get person´s gender
	public String getGender() {
		return this.gender;
	}

	// Set person´s gender
	public void setGender(String gender) {
		this.gender = gender;
	}

	// Get person´s body height
	public float getBodyHeight() {
		return this.bodyHeight;
	}

	// Set person´s body height
	public void setBodyHeight(float bodyHeight) {
		this.bodyHeight = bodyHeight;
	}

	// Get person´s hair colour
	public String getHairColour() {
		return this.hairColour;
	}

	// Set person´s hair colour
	public void setHairColour(String hairColour) {
		this.hairColour = hairColour;
	}

	// Get person´s confession
	public String getConfession() {
		return this.confession;
	}

	// Set person´s confession
	public void setConfession(String confession) {
		this.confession = confession;
	}

	// Get person´s smoking status - Is the person smoking?
	public int getIsSmoking() {
		return this.isSmoking;
	}

	// Set person´s smoking status - Is the person smoking?
	public void setIsSmoking(int isSmoking) {
		this.isSmoking = isSmoking;
	}

	// Set person´s real (pre)name
	public void setMovie(String m) {
		this.movie = m;
	}

	public void setBand(String b) {
		this.band = b;
	}

	// Get person´s movie
	public String getMovie() {
		return this.movie;
	}

	public String getBand() {
		return this.band;
	}
	// Return textual description of selected instance adding the real name and
	// user name

	public int getSimiliarityToReference() {
		return similiarityToReference;
	}

	public void setSimiliarityToReference(int similiarityToReference) {
		this.similiarityToReference = similiarityToReference;
	}

	public Boolean getWasVisited() {
		return wasVisited;
	}

	public void setWasVisited(Boolean wasVisited) {
		this.wasVisited = wasVisited;
	}

	public void equals(Profile p) {
		int percentage = 0;
		int addedPercentage = 100 / 9;
		if (!(p instanceof Profile)) {
			return;
		}

		System.out.println("Aktuelles Profil: " + this.getName() + ", " + this.getLastName());
		System.out.println("Referenzprofil: " + p.getName() + ", " + p.getLastName());

		// Custom equality check here, so here you need to check only those
		// fields which in your
		// opinion will be unique in your objects
		if (this.getConfession() == p.getConfession()) {
			percentage = percentage + addedPercentage;
			System.out.println("Confession verglichen -> identisch: " + percentage + "%");
		} else if (this.getIsSmoking() == p.getIsSmoking()) {
			percentage = percentage + addedPercentage;
			System.out.println("Smoking verglichen -> identisch: " + percentage + "%");
		}

		ArrayList<String> informationValuesCompare = new ArrayList<String>();
		for (int j = 0; j < this.selectionList.size(); j++) {
			for (Information i : this.selectionList.get(j).getInformationValues()){
				informationValuesCompare.add(i.getValue());
			}
		}
		ArrayList<String> informationValuesReference = new ArrayList<String>();
		for (int j = 0; j < p.getSelectionList().size(); j++) {
			for (Information i : p.getSelectionList().get(j).getInformationValues()){
				informationValuesReference.add(i.getValue());
			}
		}
		for (String s : informationValuesCompare){
			if (informationValuesReference.contains(s)){
				percentage = percentage + addedPercentage;
				System.out.println("Info-Objekt verglichen -> identisch: " + percentage + "%");
			}
		}
		
		informationValuesCompare.clear();
		informationValuesReference.clear();

		for (int j = 0; j < this.descriptionList.size(); j++) {
			for (Information i : this.descriptionList.get(j).getInformationValues()){
				informationValuesCompare.add(i.getValue());
			}
		}

		for (int j = 0; j < p.getDescriptionList().size(); j++) {
			for (Information i : p.getDescriptionList().get(j).getInformationValues()){
				informationValuesReference.add(i.getValue());
			}
		}
		for (String s : informationValuesCompare){
			if (informationValuesReference.contains(s)){
				percentage = percentage + addedPercentage;
				System.out.println("Info-Objekt verglichen -> identisch: " + percentage + "%");
			}
		}

		this.similiarityToReference = percentage;
		System.out.println("�hnlichkeitswert: " + this.similiarityToReference);
	}

	@Override
	public String toString() {
		Date dateBirth = this.dateOfBirth;
		Date dateNow = new Date();
		int age = dateNow.getYear() - dateBirth.getYear();

		return this.userName + "\n" + this.name + " " + this.lastName + ", " + this.gender + ", " + age + " ("
				+ this.similiarityToReference + "%)";
		// return super.toString() + " " + this.name + " " + this.lastName + "
		// aka " + this.userName;
	}

	@Override
	public int compareTo(Profile p1) {
		return Integer.compare(this.similiarityToReference, p1.similiarityToReference);
	}

	public void setIsFavorite(boolean profileWished) {
		this.isFavorite = profileWished;
	}

	public Boolean getIsFavorite() {
		return isFavorite;
	}

	public void setIsBanned(boolean profileBanned) {
		this.isBanned = profileBanned;
	}

	public Boolean getIsBanned() {
		return isBanned;
	}
}
