package de.hdm.core.shared.bo;

import java.io.Serializable;

public class Information implements Serializable {

	/*
	 * Attribute
	 */

	/**
	 * Deklaration der serialVersionUID zur Serialisierung der Objekte
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Die id der Information - eindeutiger Primärschlüssel für die Datenbank
	 */
	private int id;

	/**
	 * Die textuelle Beschreibung des Wertes, z.B. zur Eigenschaft
	 * "Meine Hobbies" -> "Volleyball"
	 */
	private String value;

	/**
	 * Die Eigenschafts-Id, auf die sich das Informationsobjekt bezieht.
	 */
	private int propertyId;

	/**
	 * Die Profil-Id, auf die sich das Informationsobjekt bezieht
	 */
	private int profileId;

	/*
	 * Get-/Set-Operations + toString
	 */

	/**
	 * Rückgeben der Informations-Id
	 * 
	 * @author Philipp Schmitt
	 * @return Die eindeutige Id des Eigenschafts-Objekts
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Setzen der Informations-Id
	 * 
	 * @author Philipp Schmitt
	 * @param id
	 *            Die zu setzende Id des Informations-Objekts
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Rückgeben des textuellen Informations-Werts
	 * 
	 * @author Philipp Schmitt
	 * @return Der textuelle Wert der Information (z.B. "Volleyball")
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * Setzen des textuellen Informations-Werts
	 * 
	 * @author Philipp Schmitt
	 * @param value
	 *            Der zu setzende textuelle Wert der Information (z.B.
	 *            "Volleyball")
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Rückgeben der Eigenschafts-Id, auf die sich das Informationsobjekt
	 * bezieht
	 * 
	 * @author Philipp Schmitt
	 * @return Eigenschafts-Id, auf der sich das Informationsobjekt bezieht
	 */
	public int getPropertyId() {
		return this.propertyId;
	}

	/**
	 * Setzen der Eigenschafts-Id, auf die sich das Informationsobjekt bezieht
	 * 
	 * @author Philipp Schmitt
	 * @param propertyId
	 *            Die zu setzende Eigenschafts-Id, auf die sich das
	 *            Informationsobjekt bezieht
	 */
	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	/**
	 * Rückgeben der Profil-Id, auf die sich das Informationsobjekt bezieht
	 * 
	 * @author Philipp Schmitt
	 * @return Profil-Id, auf der sich das Informationsobjekt bezieht
	 */
	public int getProfileId() {
		return this.profileId;
	}

	/**
	 * Setzen der Profil-Id, auf die sich das Informationsobjekt bezieht
	 * 
	 * @author Philipp Schmitt
	 * @param profileId
	 *            Die zu setzende Profile-Id, auf die sich das
	 *            Informationsobjekt bezieht
	 */
	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}

	/**
	 * Rückgeben des Informations-Objekts als String mit ausgewählten
	 * Variablen-Werten
	 * 
	 * @author Philipp Schmitt
	 * @return Textuelle Beschreibung des Informations-Objekts anhand ausgewählter Eigenschaften
	 */
	@Override
	public String toString() {
		return super.toString() + " information id = " + this.getId() + " hat den Wert: " + this.getValue()
				+ " und ist verbunden mit dem Profil von " + this.profileId + " bezogen auf die Eigenschaft "
				+ this.getPropertyId();
	}
}