package de.hdm.core.shared.bo;

import java.io.Serializable;
import java.util.ArrayList;
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
	private static Wishlist wishlist = null;
	private static ArrayList<Wish> arrayWishlist;
	private static int wishlistId = 0;
	/**
	 * Um sicherzustellen, dass pro Profile-Instanz nur eine Wishlist-Instanz existiert
	 * sollte eine Wishlist-Instanz nur ueber diese Methode erstellt werden.
	 * @return
	 */
	public static Wishlist wishlist() {
	    if (wishlist == null) {
	    	wishlist = new Wishlist();
	    	wishlist.setId(wishlistId);
	    	arrayWishlist = new ArrayList<Wish>();
	    }

	    return wishlist;
	  }
	/**
	 * Diese Methode fuegt der ArrayList ein neues Profile-Objekt hinzu.
	 */
	public void addWishToWishlist(Wish wish){
		arrayWishlist.add(wish);
	}
	/**
	 * Diese Methode gibt die ArrayList mit allen enthaltenen Profile-Objekten zurueck.
	 * @return 
	 */
	public ArrayList<Wish> getWishlist(){
		return arrayWishlist;
	}
	/**
	 * Diese Methode prueft, ob ein bestimmtes Profile-Objekt in der Array-List
	 * enthalten ist und loescht dieses.
	 */
	public void deleteWishFromWishlist(Wish wish){
		for(Wish w : arrayWishlist){
			if(w.getId() == wish.getId()){
				arrayWishlist.remove(w);
			}
		}
	}
	
	public void deleteWishlist(Wishlist wishlist){
		wishlist = null;
	}

}
