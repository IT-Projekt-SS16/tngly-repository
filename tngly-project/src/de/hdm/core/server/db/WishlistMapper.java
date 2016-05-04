package de.hdm.core.server.db;

import java.sql.*;
import java.util.Vector;
import de.hdm.core.shared.bo.Wishlist;
import de.hdm.core.shared.bo.Profile;

public class WishlistMapper {


	/**
	 * Übernommen & angepasst von: @author Thies
	 */
	
	  public static WishlistMapper getWishlistMapper() {
			return wishlistMapper;
		}

		public static void setWishlistMapper(WishlistMapper wishlistMapper) {
			WishlistMapper.wishlistMapper = wishlistMapper;
		}

		  private static WishlistMapper wishlistMapper = null;

	  protected WishlistMapper() {
	  }


	  public static WishlistMapper wishlistMapper() {
	    if (wishlistMapper == null) {
	    	wishlistMapper = new WishlistMapper();
	    }

	    return wishlistMapper;
	  }
}
