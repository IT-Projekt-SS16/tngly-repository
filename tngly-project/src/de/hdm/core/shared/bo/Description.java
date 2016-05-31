package de.hdm.core.shared.bo;

import java.io.Serializable;
import java.util.ArrayList;

public class Description extends Property implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String description;

	//InformationValues from Information table
		private ArrayList<Information> informationValues;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public ArrayList<Information> getInformationValues() {
		return informationValues;
	}

	public void setInformationValues(ArrayList<Information> informationValues) {
		this.informationValues = informationValues;
	}
}
