package de.hdm.core.server.db;

import java.sql.*;
import java.util.Vector;
import de.hdm.core.shared.bo.Wishlist;
import de.hdm.core.shared.bo.Profile;

public class InformationMapper {


	/**
	 * Übernommen & angepasst von: @author Thies
	 */
	
	  public static InformationMapper getInformationMapper() {
			return informationMapper;
		}

		public static void setInformationMapper(InformationMapper informationMapper) {
			InformationMapper.informationMapper = informationMapper;
		}

		  private static InformationMapper informationMapper = null;

	  protected InformationMapper() {
	  }


	  public static InformationMapper informationMapper() {
	    if (informationMapper == null) {
	    	informationMapper = new InformationMapper();
	    }

	    return informationMapper;
	  }
}
