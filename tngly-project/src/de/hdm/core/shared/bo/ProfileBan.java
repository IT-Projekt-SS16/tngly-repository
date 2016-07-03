package de.hdm.core.shared.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * Definition eines Kontaktsperre-Objekts, das das Verhaeltnis zwischen zwei
 * Profilen darstellt. Eine Kontaktsperre wird vom Nutzer-Profil ueber ein
 * anderes Objekt verhaengt, wenn es dieses in der Suche nichtmehr angezeigt
 * bekommen moechte.
 * 
 * @author Philipp Schmitt
 */

public class ProfileBan implements Serializable {

	/*
	 * Attribute
	 */
	/**
	 * Deklaration der serialVersionUID zur Serialisierung der Objekte
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Die id der Kontaktsperre - eindeutiger Primaerschluessel fuer die Datenbank
	 */
	private int id = 0;

	/**
	 * Die id des sperrenden Profils
	 */
	private int banningProfileId;

	/**
	 * Die id des gesperrten Profils
	 */
	private int bannedProfileId;

	/**
	 * Timestamp vom Zeitpunkt der Erstellung
	 */
	private Date timestamp;

	/**
	 * Das sperrende Profil-Objekt
	 */
	private Profile banningProfile;

	/**
	 * Das gesperrte Profil-Objekt
	 */
	private Profile bannedProfile;

	/*
	 * Get-/Set-Operations + toString
	 */

	/**
	 * Rueckgeben der Kontaktsperren-Id
	 * 
	 * @author Philipp Schmitt
	 * @return Die eindeutige Id des Kontaktsperren-Objekts
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Setzen der Kontaktsperren-Id
	 * 
	 * @author Philipp Schmitt
	 * @param id
	 *            Die zu setzende Id des Kontaktsperren-Objekts
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Rueckgeben der sperrenden Profils-Id
	 * 
	 * @author Philipp Schmitt
	 * @return Die eindeutige Id des sperrenden Profils
	 */
	public int getBanningProfileId() {
		return this.banningProfileId;
	}

	/**
	 * Setzen der sperrenden Profils-Id
	 * 
	 * @author Philipp Schmitt
	 * @param banningProfileId
	 *            Die zu setzende Id des sperrenden Profils
	 */
	public void setBanningProfileId(int banningProfileId) {
		this.banningProfileId = banningProfileId;
	}

	/**
	 * Rueckgeben der gesperrten Profils-Id
	 * 
	 * @author Philipp Schmitt
	 * @return Die eindeutige id des gesperrten Profils
	 */
	public int getBannedProfileId() {
		return this.bannedProfileId;
	}

	/**
	 * Setzen der gesperrten Profils-Id
	 * 
	 * @author Philipp Schmitt
	 * @param bannedProfileId
	 *            Die zu setzende Id des gesperrten Profils
	 */
	public void setBannedProfileId(int bannedProfileId) {
		this.bannedProfileId = bannedProfileId;
	}

	/**
	 * Rueckgeben des Timestamps von der Erstellung des Kontaktsperren-Objekts
	 * 
	 * @author Philipp Schmitt
	 * @return Timestamp zum Zeitpunkt der Objekt-Erstellung
	 */
	public Date getTimestamp() {
		return this.timestamp;
	}

	/**
	 * Setzen des Timestamps von der Erstellung des Kontaktsperren-Objekts
	 * 
	 * @param timestamp
	 *            Der zu setzende Timestamp zum Zeitpunkt der Objekt-Erstellung
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Rueckgeben des sperrenden Profil-Objekts
	 * 
	 * @author Philipp Schmitt
	 * @return Das sperrende Profil-Objekt
	 */
	public Profile getBanningProfile() {
		return this.banningProfile;
	}

	/**
	 * Setzen des sperrenden Profil-Objekts
	 * 
	 * @author Philipp Schmitt
	 * @param banningProfile
	 *            Das zu setzende, sperrende Profil-Objekt
	 */
	public void setBanningProfile(Profile banningProfile) {
		this.banningProfile = banningProfile;
	}

	/**
	 * Rueckgeben des gesperrten Profil-Objekts
	 * 
	 * @author Philipp Schmitt
	 * @return Das gesperrte Profil-Objekt
	 */
	public Profile getBannedProfile() {
		return this.bannedProfile;
	}

	/**
	 * Setzen des gesperrten Profil-Objekts
	 * 
	 * @author Philipp Schmitt
	 * @param bannedProfile
	 *            Das zu setzende, gesperrte Profil-Objekt
	 */
	public void setBannedProfile(Profile bannedProfile) {
		this.bannedProfile = bannedProfile;
	}

	/**
	 * Rueckgeben des Kontaktsperren-Objekts als String mit ausgewaehlten
	 * Variablen-Werten
	 * 
	 * @author Philipp Schmitt
	 * @return Textuelle Beschreibung des Kontaktsperren-Objekts anhand
	 *         ausgewaehlter Eigenschaften
	 */
	@Override
	public String toString() {
		return super.toString() + " " + this.id + " Das Profil mit der id " + this.banningProfileId
				+ " bannte das Profile mit der id " + this.bannedProfileId + " um " + this.timestamp;
	}
}
