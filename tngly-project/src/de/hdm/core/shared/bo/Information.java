package de.hdm.core.shared.bo;

import java.io.Serializable;

public class Information implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Information ID
	private int id;
	
	// Information value
	private String value;
	
	/*
	 * Get-/Set-Operations + toString
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
}