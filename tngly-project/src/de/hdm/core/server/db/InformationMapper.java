package de.hdm.core.server.db;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import de.hdm.core.shared.bo.Wishlist;
import de.hdm.core.shared.bo.Profile;
import java.util.Date;

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

	public void delete(Profile profile) {
		// TODO Auto-generated method stub
		
	}

	public void edit(Profile profile) {
		
		SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		String date = mySQLformat.format(currentDate);
		
		// insert Date as current timestamp yyyy-MM-dd, NICHT VERGESSEN!
		
	}

	public ArrayList<Profile> searchForInformationValues(ArrayList<Profile> profiles) {
		// TODO Auto-generated method stub
		return null;
	}
}
