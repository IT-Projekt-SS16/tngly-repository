package de.hdm.core.shared.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * Definition eines Profilwunsch-Objekts, das das Verhaeltnis zwischen zwei
 * Profilen darstellt. Ein Profilwunsch wird vom Nutzer-Profil fuer ein anderes
 * Profil erstellt, wenn sich der Nutzer dieses Profil zur spaeteren, weiteren
 * Begutachtung "merken" moechte.
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
	 * Die id des Profilwunschs - eindeutiger Primaerschluessel fuer die Datenbank
	 */
	private int id = 0;

	/**
	 * Die id des wuenschenden Profils
	 */
	private int wishingProfileId = 0;

	/**
	 * Die id des gewuenschten Profils
	 */
	private int wishedProfileId = 0;

	/**
	 * Timestamp vom Zeitpunkt der Erstellung
	 */
	private Date timestamp;

	/**
	 * Das wuenschende Profil-Objekt
	 */
	private Profile wishingProfile;

	/**
	 * Das gewuenschte Profil-Objekt
	 */
	private Profile wishedProfile;

	/*
	 * Get-/Set-Operations + toString
	 */

	/**
	 * Rueckgeben der Profilwunsch-Id
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
	 * Rueckgeben der wuenschenden Profils-Id
	 * 
	 * @author Philipp Schmitt
	 * @return Die eindeutige Id des wuenschenden Profils
	 */
	public int getWishingProfileId() {
		return this.wishingProfileId;
	}

	/**
	 * Setzen der wuenschenden Profils-Id
	 * 
	 * @author Philipp Schmitt
	 * @param wishingProfileId
	 *            Die zu setzende Id des wuenschenden Profils
	 */
	public void setWishingProfileId(int wishingProfileId) {
		this.wishingProfileId = wishingProfileId;
	}

	/**
	 * Rueckgeben der gewuenschten Profils-Id
	 * 
	 * @author Philipp Schmitt
	 * @return Die eindeutige id des gewuenschten Profils
	 */
	public int getWishedProfileId() {
		return this.wishedProfileId;
	}

	/**
	 * Setzen der gewuenschten Profils-Id
	 * 
	 * @author Philipp Schmitt
	 * @param wishedProfileId
	 *            Die zu setzende Id des gewuenschten Profils
	 */
	public void setWishedProfileId(int wishedProfileId) {
		this.wishedProfileId = wishedProfileId;
	}

	/**
	 * Rueckgeben des Timestamps von der Erstellung des Profilwunsch-Objekts
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
	 * Rueckgeben des wuenschenden Profil-Objekts
	 * 
	 * @author Philipp Schmitt
	 * @return Das wuenschende Profil-Objekt
	 */
	public Profile getWishingProfile() {
		return this.wishingProfile;
	}

	/**
	 * Setzen des wuenschenden Profil-Objekts
	 * 
	 * @author Philipp Schmitt
	 * @param wishingProfile
	 *            Das zu setzende, wuenschende Profil-Objekt
	 */
	public void setWishingProfile(Profile wishingProfile) {
		this.wishingProfile = wishingProfile;
	}

	/**
	 * Rueckgeben des gewuenschten Profil-Objekts
	 * 
	 * @author Philipp Schmitt
	 * @return Das gewuenschte Profil-Objekt
	 */
	public Profile getWishedProfile() {
		return this.wishedProfile;
	}

	/**
	 * Setzen des gewuenschten Profil-Objekts
	 * 
	 * @author Philipp Schmitt
	 * @param wishedProfile
	 *            Das zu setzende, gewuenschte Profil-Objekt
	 */
	public void setWishedProfile(Profile wishedProfile) {
		this.wishedProfile = wishedProfile;
	}

	/**
	 * Rueckgeben des Profilwunsch-Objekts als String mit ausgewaehlten
	 * Variablen-Werten
	 * 
	 * @author Philipp Schmitt
	 * @return Textuelle Beschreibung des Profilwunsch-Objekts anhand
	 *         ausgewaehlter Eigenschaften
	 */
	@Override
	public String toString() {
		return super.toString() + " " + this.id + " Das Profil mit der id " + this.wishingProfileId
				+ " wuenscht sich das Profile mit der id " + this.wishedProfileId + " um " + this.timestamp;
	}

}
