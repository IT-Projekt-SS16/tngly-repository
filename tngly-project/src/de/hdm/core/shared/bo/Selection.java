package de.hdm.core.shared.bo;

import java.io.Serializable;

public class Selection extends Property implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Items
	private int items;
	
	/*
	 * Operations
	 */
	
	// Get items
	public int getItems()	{
		return this.items;
	}
		
	// Add item
	public void addItem(int newItem)	{

	}
		
	// Delete item
	public void deleteItem(int oldItem)	{

	}
}