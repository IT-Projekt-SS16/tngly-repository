package de.hdm.core.shared.bo;

import java.util.Date;

public class Wish {

	private static final long serialVersionUID = 1L;
	
		// The Wish´s ID
		private int id;
		
		// The wishing profile´s id
		private int wishingProfileId;
		
		// The wished profile´s id
		private int wishedProfileId;
		
		// The wish´s timestamp
		private Date timestamp;
		
		/*
		 * Get-/Set-Operations + toString
		 */
		
		
		// Get Wish´s id
		public int getId()	{
			return this.id;
		}
		
		// Set profile´s ID
		public void setId(int id)	{
			this.id = id;
		}
		
		// Get wishing profile´s id
		public int getWishingProfileId()	{
			return this.wishingProfileId;
		}
		
		// Set wishing profile´s id
		public void setWishingProfileId(int wishingProfileId)	{
			this.wishingProfileId = wishingProfileId;
		}
		
		// Get wished profile´s id
		public int getWishedProfileId()	{
			return this.wishedProfileId;
		}
		
		// Get wished profile´s id
		public void setWishedProfileId(int wishedProfileId)	{
			this.wishedProfileId = wishedProfileId;
		}
		
		// Get timestamp
		public Date getTimestamp()	{
			return this.timestamp;
		}
		
		// Set timestamp
		public void setTimestamp(Date timestamp)	{
			this.timestamp = timestamp;
		}
		
		@Override
		public String toString() {
		    return super.toString() + " " + this.id + " Das Profil mit der id " + this.wishingProfileId + " wünscht sich das Profile mit der id " + this.wishedProfileId + " um " + this.timestamp;
		  }
}
