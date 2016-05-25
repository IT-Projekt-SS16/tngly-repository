package de.hdm.core.shared.bo;

import java.io.Serializable;
import java.util.Date;

public class Information implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Information ID
	private int id;
	
	// Information value
	private String value;
	
	// Referenced property
	private int propertyId;
	
	// Referenced profile
	private int profileId;
	
	
	/*
	 * Get-/Set-Operations
	 */
	
	// Get the ID
	public int getId()	{
		return this.id;
	}
		
	// Set property´s ID
	public void setId(int id)	{
		this.id = id;
	}
		
	// Get value
	public String getValue()	{
		return this.value;
	}
		
	// Set value
	public void setValue(String value)	{
		this.value = value;
	}
	
	// Get referenced property ID
	public int getPropertyId()	{
		return this.propertyId;
	}
	
	// Set referenced property ID
	public void setPropertyId(int propertyId)	{
		this.propertyId = propertyId;
	}
	
	// Get referenced profile ID
	public int getProfileId()	{
		return this.profileId;
	}
	
	// Set referenced profile ID
	public void setProfileId(int profileId)	{
		this.profileId = profileId;
	}
	
	// toString mit objektspezifischer Ausgabe
	
	@Override
	public String toString() {
		return super.toString() + " information id = " + this.getId() + " hat den Wert: " + this.getValue() + " und ist verbunden mit dem Profil von "
	 + this.profileId + " bezogen auf die Eigenschaft " + this.getPropertyId();
	}
}