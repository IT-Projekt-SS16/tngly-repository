/**
 * 
 */
package de.hdm.wi7.shared;

import java.io.Serializable;

public class Property implements Serializable{

	/*
	 *  Attributes
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// The property´s ID
	private int id;
	
	// The property´s textual description
	private String textualDescription;
	
	/*
	 * Get-/Set-Operations + toString
	 */
	
	// Get property´s ID
	public int getId()	{
		return this.id;
	}
		
	// Set property´s ID
	public void setId(int id)	{
		this.id = id;
	}
		
	// Get property´s textual description
	public String getTextualDescription()	{
		return this.textualDescription;
	}
		
	// Set profile´s user name
	public void setTextualDescription(String textualDescription)	{
		this.textualDescription = textualDescription;
	}
	
	// Return textual description of selected instance adding the real name and user name	
	@Override
	public String toString() {
	    return super.toString() + " Description" + this.textualDescription;
	}
	
}
