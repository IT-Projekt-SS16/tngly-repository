package de.hdm.core.shared.bo;

import java.io.Serializable;
import java.util.Vector;

public class Wishlist implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private int profileId;
	
	
	public Wishlist() {}		
		
	public int getId()	{
		return id;
	}
	
	public void setId(int id)	{
		this.id = id;
	}
	
	public int profileId()	{
		return profileId;
	}
	
	public void setProfileId(int profileId)	{
		this.profileId = profileId;
	}

	/*
	 *  To be done
	 */
}
