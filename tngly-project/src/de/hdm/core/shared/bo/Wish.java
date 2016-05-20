package de.hdm.core.shared.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Wish implements Serializable{

	private static final long serialVersionUID = 1L;
	private static Wish wishlist = null;
	private static ArrayList<Profile> arrayWishlist;
	/**
	 * Hier wird der Konstruktor fuer die Klasse Wishlist implementiert.
	 */
	public Wish(){
	}
	/**
	 * Um sicherzustellen, dass pro Profile-Instanz nur eine Wishlist-Instanz existiert
	 * sollte eine Wishlist-Instanz nur ueber diese Methode erstellt werden.
	 * @return
	 */
	public static Wish wishlist() {
	    if (wishlist == null) {
	    	wishlist = new Wish();
	    	arrayWishlist = new ArrayList<Profile>();
	    }

	    return wishlist;
	  }
	/**
	 * Diese Methode fuegt der ArrayList ein neues Profile-Objekt hinzu.
	 */
	public void addProfileToWishlist(Profile profile){
		arrayWishlist.add(profile);
	}
	/**
	 * Diese Methode gibt die ArrayList mit allen enthaltenen Profile-Objekten zurueck.
	 * @return 
	 */
	public ArrayList<Profile> getWishlist(){
		return arrayWishlist;
	}
	/**
	 * Diese Methode prueft, ob ein bestimmtes Profile-Objekt in der Array-List
	 * enthalten ist und loescht dieses.
	 */
	public void deleteProfileFromWishlist(Profile profile){
		for(Profile p : arrayWishlist){
			if(p.getUserName() == profile.getUserName()){
				arrayWishlist.remove(p);
			}
		}
	}
	
	public void deleteWishlist(Wish wishlist){
		wishlist = null;
	}

	
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
