package de.hdm.core.shared.bo;

import java.io.Serializable;
import java.util.ArrayList;

public class Selection extends Property implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Items
	private ArrayList<String> items;
	
	//InformationValues from Information table
	private ArrayList<Information> informationValues;
	
	/*
	 * Operations
	 */
	
	// Get items
	public ArrayList<String> getItems()	{
		return this.items;
	}
		
	// Add item
	public void addItem(String newItem)	{
		this.items.add(newItem);
	}
		
	// Delete item
	public void deleteItem(String oldItem)	{
		this.items.remove(this.items.indexOf(oldItem));
	}

	public ArrayList<Information> getInformationValues() {
		return informationValues;
	}

	public void setInformationValues(ArrayList<Information> informationValues) {
		this.informationValues = informationValues;
	}
}