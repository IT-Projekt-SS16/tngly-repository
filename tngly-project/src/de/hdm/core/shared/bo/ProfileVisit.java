package de.hdm.core.shared.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * Definition eines Profilbesuchs-Objekts, das das Verhaeltnis zwischen zwei
 * Profilen darstellt. Eine Profilbesuch-Objekt wird dann erstellt, wenn sich
 * das aktuelle Nutzer-Profil ein anderes Profil in der Detailansicht anzeigen
 * laesst.
 * 
 * @author Philipp Schmitt
 */
public class ProfileVisit implements Serializable {

	/*
	 * Attribute
	 */

	/**
	 * Deklaration der serialVersionUID zur Serialisierung der Objekte
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Die id des Profilbesuchs - eindeutiger Primaerschluessel fuer die Datenbank
	 */
	private int id;

	/**
	 * Die id des besuchenden Profils
	 */
	private int visitingProfileId;

	/**
	 * Die id des besuchten Profils
	 */
	private int visitedProfileId;

	/**
	 * Timestamp vom Zeitpunkt der Erstellung
	 */
	private Date timestamp;

	/*
	 * Get-/Set-Operations + toString
	 */

	/**
	 * Rueckgeben der Profilbesuchs-Id
	 * 
	 * @author Philipp Schmitt
	 * @return Die eindeutige Id des Profilbesuchs-Objekts
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Setzen der Profilbesuchs-Id
	 * 
	 * @author Philipp Schmitt
	 * @param id
	 *            Die zu setzende Id des Profilbesuchs-Objekts
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Rueckgeben der besuchenden Profils-Id
	 * 
	 * @author Philipp Schmitt
	 * @return Die eindeutige Id des besuchenden Profils
	 */
	public int getVisitingProfileId() {
		return this.visitingProfileId;
	}

	/**
	 * Setzen der besuchenden Profils-Id
	 * 
	 * @author Philipp Schmitt
	 * @param visitingProfileId
	 *            Die zu setzende Id des besuchenden Profils
	 */
	public void setVisitingProfileId(int visitingProfileId) {
		this.visitingProfileId = visitingProfileId;
	}

	/**
	 * Rueckgeben der besuchten Profils-Id
	 * 
	 * @author Philipp Schmitt
	 * @return Die eindeutige id des besuchten Profils
	 */
	public int getVisitedProfileId() {
		return this.visitedProfileId;
	}

	/**
	 * Setzen der besuchten Profils-Id
	 * 
	 * @author Philipp Schmitt
	 * @param visitedProfileId
	 *            Die zu setzende Id des besuchten Profils
	 */
	public void setVisitedProfileId(int visitedProfileId) {
		this.visitedProfileId = visitedProfileId;
	}

	/**
	 * Rueckgeben des Timestamps von der Erstellung des Profilbesuch-Objekts
	 * 
	 * @author Philipp Schmitt
	 * @return Timestamp zum Zeitpunkt der Objekt-Erstellung
	 */
	public Date getTimestamp() {
		return this.timestamp;
	}

	/**
	 * Setzen des Timestamps von der Erstellung des Profilbesuch-Objekts
	 * 
	 * @param timestamp
	 *            Der zu setzende Timestamp zum Zeitpunkt der Objekt-Erstellung
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Rueckgeben des Profilbesuchs-Objekts als String mit ausgewaehlten
	 * Variablen-Werten
	 * 
	 * @author Philipp Schmitt
	 * @return Textuelle Beschreibung des Profilbesuchs-Objekts anhand
	 *         ausgewaehlter Eigenschaften
	 */
	@Override
	public String toString() {
		return super.toString() + " " + this.id + " Das Profil mit der id " + this.visitingProfileId
				+ " besuchte das Profil mit der id " + this.visitedProfileId + " um " + this.timestamp;
	}
}
