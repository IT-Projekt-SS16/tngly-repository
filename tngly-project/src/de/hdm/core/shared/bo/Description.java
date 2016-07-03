package de.hdm.core.shared.bo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Subklasse von Property - Definition eines Description-Objekts, das dem Nutzer
 * die Moeglichkeit bietet, seine Interessen zu bestimmten Eigenschaftsfeldern
 * durch freie Eingabe zu bekunden.
 * 
 * @author Philipp Schmitt
 */

public class Description extends Property implements Serializable {

	/*
	 * Attribute
	 */
	
	/**
	 * Deklaration der serialVersionUID zur Serialisierung der Objekte
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Liste an Informationsobjekten, die das Profil mit dieser Eigenschaft
	 * verknuepft hat
	 */
	private ArrayList<Information> informationValues;

	/*
	 * get-/set-Operations
	 */

	/**
	 * Rueckgeben der Liste an Informationsobjekten, die das Profil mit dieser
	 * Eigenschaft verknuepft hat
	 * 
	 * @author Philipp Schmitt
	 * @return Liste an Informationsobjekten des Profils zu dieser Eigenschaft
	 */
	public ArrayList<Information> getInformationValues() {
		return informationValues;
	}

	/**
	 * Setzen der Liste an Informationsobjekten, die das Profil mit dieser
	 * Eigenschaft verknuepft hat
	 * 
	 * @author Philipp Schmitt
	 * @param informationValues
	 *            Die zu setzende Liste an Informationsobjekten des Profils zu
	 *            dieser Eigenschaft
	 */
	public void setInformationValues(ArrayList<Information> informationValues) {
		this.informationValues = informationValues;
	}
}
