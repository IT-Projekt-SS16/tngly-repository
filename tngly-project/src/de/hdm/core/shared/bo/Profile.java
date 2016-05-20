package de.hdm.core.shared.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Profile implements Serializable{

	/*
	 *  Attributes
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	
	private ArrayList<Wish> wishlist;
	
	private ArrayList<ProfileBan> banlist;
	
	private ArrayList<Property> propertylist;
	
	public Profile(){
		this.setWishlist(new ArrayList<Wish>());
		this.setBanlist(new ArrayList<ProfileBan>());
		this.setPropertylist(new ArrayList<Property>());
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

	public ArrayList<Property> getPropertylist() {
		return propertylist;
	}

	public void setPropertylist(ArrayList<Property> propertylist) {
		this.propertylist = propertylist;
	}

	// Get profile´s ID
	public int getId()	{
		return this.id;
	}
	
	// Set profile´s ID
	public void setId(int id)	{
		this.id = id;
	}
	
	// Get profile´s user name
	public String getUserName()	{
		return this.userName;
	}
	
	// Set profile´s user name
	public void setUserName(String userName)	{
		this.userName = userName;
	}
	
	// Get person´s real (pre)name
	public String getName()	{
		return this.name;
	}
	
	// Set person´s real (pre)name
	public void setName(String name)	{
		this.name = name;
	}
	
	// Get person´s real last name
	public String getLastName()	{
		return this.lastName;
	}
	
	// Set person´s real last name
	public void setLastName(String lastName)	{
		this.lastName = lastName;
	}
	
	// Get person´s date of birth
	public Date getDateOfBirth()	{
		return this.dateOfBirth;
	}
	
	// Set person´s date of birth 
	public void setDateOfBirth(Date dateOfBirth)	{
		this.dateOfBirth = dateOfBirth;
	}
	
	// Get person´s gender
	public String getGender()	{
		return this.gender;
	}
	
	// Set person´s gender
	public void setGender(String gender)	{
		this.gender = gender;
	}
	
	
	// Get person´s body height
	public float getBodyHeight()	{
		return this.bodyHeight;
	}
	
	// Set person´s body height
	public void setBodyHeight(float bodyHeight)	{
		this.bodyHeight = bodyHeight;
	}
	
	// Get person´s hair colour
	public String getHairColour()	{
		return this.hairColour;
	}
	
	// Set person´s hair colour
	public void setHairColour(String hairColour)	{
		this.hairColour = hairColour;
	}
	
	// Get person´s confession
	public String getConfession()	{
		return this.confession;
	}
	
	// Set person´s confession
	public void setConfession(String confession)	{
		this.confession = confession;
	}
	
	// Get person´s smoking status - Is the person smoking?
	public int getIsSmoking()	{
		return this.isSmoking;
	}
	
	// Set person´s smoking status - Is the person smoking?
	public void setIsSmoking(int isSmoking)	{
		this.isSmoking = isSmoking;
	}
	
	// Return textual description of selected instance adding the real name and user name
	
	public int getSimiliarityToReference() {
		return similiarityToReference;
	}

	public void setSimiliarityToReference(int similiarityToReference) {
		this.similiarityToReference = similiarityToReference;
	}

	@Override
	public String toString() {
	    return super.toString() + " " + this.name + " " + this.lastName + " aka " + this.userName;
	  }
}
