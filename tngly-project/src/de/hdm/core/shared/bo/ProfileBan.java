package de.hdm.core.shared.bo;

import java.io.Serializable;
import java.util.Date;

public class ProfileBan implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Default-Konstruktor
	public ProfileBan(){
	}

	
	
	// The Ban´s ID
			private int id = 0;
			
			// The banning profile´s id
			private int banningProfileId;
			
			// The banned profile´s id
			private int bannedProfileId;
			
			// The ban´s timestamp
			private Date timestamp;
			
			private Profile banningProfile;
			
			private Profile bannedProfile;
			
			/*
			 * Get-/Set-Operations + toString
			 */
			
			
			// Get Ban´s id
			public int getId()	{
				return this.id;
			}
			
			// Set profile´s ID
			public void setId(int id)	{
				this.id = id;
			}
			
			// Get banning profile´s id
			public int getBanningProfileId()	{
				return this.banningProfileId;
			}
			
			// Set banning profile´s id
			public void setBanningProfileId(int banningProfileId)	{
				this.banningProfileId = banningProfileId;
			}
			
			// Get banned profile´s id
			public int getBannedProfileId()	{
				return this.bannedProfileId;
			}
			
			// Set banned profile´s id
			public void setBannedProfileId(int bannedProfileId)	{
				this.bannedProfileId = bannedProfileId;
			}
			
			// Get timestamp
			public Date getTimestamp()	{
				return this.timestamp;
			}
			
			// Set timestamp
			public void setTimestamp(Date timestamp)	{
				this.timestamp = timestamp;
			}
			
			public Profile getBanningProfile()	{
				return this.banningProfile;
			}
			
			public void setBanningProfile(Profile banningProfile)	{
				this.banningProfile = banningProfile;
			}
			
			public Profile getBannedProfile()	{
				return this.bannedProfile;
			}
			
			public void setBannedProfile(Profile bannedProfile)	{
				this.bannedProfile = bannedProfile;
			}
			
			
			@Override
			public String toString() {
			    return super.toString() + " " + this.id + " Das Profil mit der id " + this.banningProfileId + " bannte das Profile mit der id " + this.bannedProfileId + " um " + this.timestamp;
			  }
}
