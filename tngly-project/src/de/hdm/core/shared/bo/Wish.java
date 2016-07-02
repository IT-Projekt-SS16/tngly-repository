package de.hdm.core.shared.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * Definition eines Profilwunsch-Objekts, das das Verhältnis zwischen zwei
 * Profilen darstellt. Ein Profilwunsch wird vom Nutzer-Profil für ein anderes
 * Profil erstellt, wenn sich der Nutzer dieses Profil zur späteren, weiteren
 * Begutachtung "merken" möchte.
 * 
 * @author Philipp Schmitt
 */

public class Wish implements Serializable {

	/*
	 * Attribute
	 */
	/**
	 * Deklaration der serialVersionUID zur Serialisierung der Objekte
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Die id des Profilwunschs - eindeutiger Primärschlüssel für die Datenbank
	 */
	private int id = 0;

	/**
	 * Die id des wünschenden Profils
	 */
	private int wishingProfileId = 0;

	/**
	 * Die id des gewünschten Profils
	 */
	private int wishedProfileId = 0;

	/**
	 * Timestamp vom Zeitpunkt der Erstellung
	 */
	private Date timestamp;

	/**
	 * Das wünschende Profil-Objekt
	 */
	private Profile wishingProfile;

	/**
	 * Das gewünschte Profil-Objekt
	 */
	private Profile wishedProfile;

	/*
	 * Get-/Set-Operations + toString
	 */

	/**
	 * Rückgeben der Profilwunsch-Id
	 * 
	 * @author Philipp Schmitt
	 * @return Die eindeutige Id des Profilwunsch-Objekts
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Setzen der Profilwunsch-Id
	 * 
	 * @author Philipp Schmitt
	 * @param id
	 *            Die zu setzende Id des Profilwunsch-Objekts
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Rückgeben der wünschenden Profils-Id
	 * 
	 * @author Philipp Schmitt
	 * @return Die eindeutige Id des wünschenden Profils
	 */
	public int getWishingProfileId() {
		return this.wishingProfileId;
	}

	/**
	 * Setzen der wünschenden Profils-Id
	 * 
	 * @author Philipp Schmitt
	 * @param wishingProfileId
	 *            Die zu setzende Id des wünschenden Profils
	 */
	public void setWishingProfileId(int wishingProfileId) {
		this.wishingProfileId = wishingProfileId;
	}

	/**
	 * Rückgeben der gewünschten Profils-Id
	 * 
	 * @author Philipp Schmitt
	 * @return Die eindeutige id des gewünschten Profils
	 */
	public int getWishedProfileId() {
		return this.wishedProfileId;
	}

	/**
	 * Setzen der gewünschten Profils-Id
	 * 
	 * @author Philipp Schmitt
	 * @param wishedProfileId
	 *            Die zu setzende Id des gewünschten Profils
	 */
	public void setWishedProfileId(int wishedProfileId) {
		this.wishedProfileId = wishedProfileId;
	}

	/**
	 * Rückgeben des Timestamps von der Erstellung des Profilwunsch-Objekts
	 * 
	 * @author Philipp Schmitt
	 * @return Timestamp zum Zeitpunkt der Objekt-Erstellung
	 */
	public Date getTimestamp() {
		return this.timestamp;
	}

	/**
	 * Setzen des Timestamps von der Erstellung des Profilwunsch-Objekts
	 * 
	 * @param timestamp
	 *            Der zu setzende Timestamp zum Zeitpunkt der Objekt-Erstellung
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Rückgeben des wünschenden Profil-Objekts
	 * 
	 * @author Philipp Schmitt
	 * @return Das wünschende Profil-Objekt
	 */
	public Profile getWishingProfile() {
		return this.wishingProfile;
	}

	/**
	 * Setzen des wünschenden Profil-Objekts
	 * 
	 * @author Philipp Schmitt
	 * @param wishingProfile
	 *            Das zu setzende, wünschende Profil-Objekt
	 */
	public void setWishingProfile(Profile wishingProfile) {
		this.wishingProfile = wishingProfile;
	}

	/**
	 * Rückgeben des gewünschten Profil-Objekts
	 * 
	 * @author Philipp Schmitt
	 * @return Das gewünschte Profil-Objekt
	 */
	public Profile getWishedProfile() {
		return this.wishedProfile;
	}

	/**
	 * Setzen des gewünschten Profil-Objekts
	 * 
	 * @author Philipp Schmitt
	 * @param wishedProfile
	 *            Das zu setzende, gewünschte Profil-Objekt
	 */
	public void setWishedProfile(Profile wishedProfile) {
		this.wishedProfile = wishedProfile;
	}

	/**
	 * Rückgeben des Profilwunsch-Objekts als String mit ausgewählten
	 * Variablen-Werten
	 * 
	 * @author Philipp Schmitt
	 * @return Textuelle Beschreibung des Profilwunsch-Objekts anhand
	 *         ausgewählter Eigenschaften
	 */
	@Override
	public String toString() {
		return super.toString() + " " + this.id + " Das Profil mit der id " + this.wishingProfileId
				+ " wünscht sich das Profile mit der id " + this.wishedProfileId + " um " + this.timestamp;
	}

}
