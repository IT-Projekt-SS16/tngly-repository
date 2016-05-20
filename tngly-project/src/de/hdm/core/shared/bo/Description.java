package de.hdm.core.shared.bo;

import java.io.Serializable;

public class Description extends Property implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
