package de.hdm.wi7.shared;

import java.util.Date;

public class Profile {

	/*
	 *  Attributes
	 */
	
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
	private char gender;
	
	// The body height of the person
	private float bodyHeight;
	
	// The person´s hair colour
	private String hairColour;
	
	// The person´s religious confession (e.g. catholic, muslim..)
	private String confession;
	
	// Is the person smoking? (e.g. yes, no)
	private boolean isSmoking;
	
	/*
	 * Get-/Set-Operations
	 */
	
	
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
	public char getGender()	{
		return this.gender;
	}
	
	// Set person´s gender
	public void setGender(char gender)	{
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
	public boolean getIsSmoking()	{
		return this.isSmoking;
	}
	
	// Set person´s smoking status - Is the person smoking?
	public void setIsSmoking(boolean isSmoking)	{
		this.isSmoking = isSmoking;
	}
	
	// Return textual description of selected instance adding the real name and user name

	public String toString() {
	    return super.toString() + " " + this.name + " " + this.lastName + " aka " + this.userName;
	  }
}
