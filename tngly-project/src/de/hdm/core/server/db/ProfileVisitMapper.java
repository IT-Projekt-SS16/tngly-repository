package de.hdm.core.server.db;

import java.sql.*;
import java.util.Vector;
import de.hdm.core.shared.bo.Wishlist;
import de.hdm.core.shared.bo.Profile;

public class ProfileVisitMapper {


	/**
	 * Übernommen & angepasst von: @author Thies
	 */
	
	  public static ProfileVisitMapper getProfileVisitMapper() {
			return profileVisitMapper;
		}

		public static void setProfileVisitMapper(ProfileVisitMapper profileVisitMapper) {
			ProfileVisitMapper.profileVisitMapper = profileVisitMapper;
		}

		  private static ProfileVisitMapper profileVisitMapper = null;

	  protected ProfileVisitMapper() {
	  }


	  public static ProfileVisitMapper profileVisitMapper() {
	    if (profileVisitMapper == null) {
	    	profileVisitMapper = new ProfileVisitMapper();
	    }

	    return profileVisitMapper;
	  }
}
