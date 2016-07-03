package de.hdm.core.shared.bo;

/**
 * Subklasse von Profil - Definition eines Suchprofil-Objekts, das
 * benutzerdefinierte Kriterien zur individuellen Suche nach anderen Profilen
 * wie zum Beispiel dem gewuenschten Alter oder der gewuenschten Haarfarbe
 * beinhaltet.
 * 
 * @author Philipp Schmitt
 */
public class SearchProfile extends Profile {

	/*
	 * Attribute
	 */

	/**
	 * Deklaration der serialVersionUID zur Serialisierung der Objekte
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Alter in Jahren, von dem an nach Profilen gesucht werden soll
	 */
	private int ageRangeFrom;
	/**
	 * Alter in Jahren, bis zu dem nach Profilen gesucht werden soll
	 */
	private int ageRangeTo;
	/**
	 * Koerpergroeße in Metern als float, von der an nach Profilen gesucht werden
	 * soll
	 */
	private float bodyHeightFrom;
	/**
	 * Koerpergroeße in Metern als float, bis zu der nach Profilen gesucht werden
	 * soll
	 */
	private float bodyHeightTo;

	/*
	 * Get-/Set-Operations
	 */

	/**
	 * Rueckgeben des Alters in Jahren, von dem an nach Profilen gesucht werden
	 * soll
	 * 
	 * @author Philipp Schmitt
	 * @return Mindestalter in Jahren
	 */
	public int getAgeRangeFrom() {
		return ageRangeFrom;
	}

	/**
	 * Setzen des Alters in Jahren, von dem an nach Profilen gesucht werden soll
	 * 
	 * @author Philipp Schmitt
	 * @param ageRangeFrom
	 *            Das zu setzende Mindestalter in Jahren
	 */
	public void setAgeRangeFrom(int ageRangeFrom) {
		this.ageRangeFrom = ageRangeFrom;
	}

	/**
	 * Rueckgeben des Alters in Jahren, bis zu dem nach Profilen gesucht werden
	 * soll
	 * 
	 * @return Maximalalter in Jahren
	 */
	public int getAgeRangeTo() {
		return ageRangeTo;
	}

	/**
	 * Setzen des Alters in Jahren, bis zu dem nach Profilen gesucht werden soll
	 * 
	 * @param ageRangeTo
	 *            Das zu setzende Maximalalter in Jahren
	 */
	public void setAgeRangeTo(int ageRangeTo) {
		this.ageRangeTo = ageRangeTo;
	}

	/**
	 * Rueckgeben der Koerpergroeße in Metern, von der an nach Profilen gesucht
	 * werden soll
	 * 
	 * @author Philipp Schmitt
	 * @return Mindestkoerpergroeße in Metern
	 */
	public float getBodyHeightFrom() {
		return bodyHeightFrom;
	}

	/**
	 * Setzen der Koerpergroeße in Metern, von der an nach Profilen gesucht werden
	 * soll
	 * 
	 * @param bodyHeightFrom
	 *            Die zu setzende Mindestkoerpergroeße in Metern
	 */
	public void setBodyHeightFrom(float bodyHeightFrom) {
		this.bodyHeightFrom = bodyHeightFrom;
	}

	/**
	 * Rueckgeben der Koerpergroeße in Metern, bis zu der nach Profilen gesucht
	 * werden soll
	 * 
	 * @return Maximalkoerpergroeße in Metern
	 */
	public float getBodyHeightTo() {
		return bodyHeightTo;
	}

	/**
	 * Setzen der Koerpergroeße in Metern, bis zu der nach Profilen gesucht werden
	 * soll
	 * 
	 * @param bodyHeightTo
	 *            Die zu setzende Maximalkoerpergroeße in Metern
	 */
	public void setBodyHeightTo(float bodyHeightTo) {
		this.bodyHeightTo = bodyHeightTo;
	}

	/**
	 * Rueckgeben des Suchprofil-Objekts als String mit ausgewaehlten
	 * Variablen-Werten
	 * 
	 * @author Philipp Schmitt
	 * @return Textuelle Beschreibung des Suchprofil-Objekts anhand ausgewaehlter
	 *         Eigenschaften
	 */
	@Override
	public String toString() {
		return super.getGender() + "," + "Age: " + ageRangeFrom + " - " + ageRangeTo + "," + "Body Height: "
				+ bodyHeightFrom + " - " + bodyHeightTo + "," + super.getHairColour() + "," + super.getIsSmoking() + ","
				+ super.getConfession();

	}

}
