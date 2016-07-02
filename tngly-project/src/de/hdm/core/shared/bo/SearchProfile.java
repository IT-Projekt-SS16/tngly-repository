package de.hdm.core.shared.bo;

/**
 * Subklasse von Profil - Definition eines Suchprofil-Objekts, das benutzerdefinierte Kriterien zur individuellen Suche nach anderen Profilen wie zum Beispiel dem gewünschten Alter oder der gewünschten Haarfarbe beinhaltet.
 * 
 * @author Philipp Schmitt
 */
public class SearchProfile extends Profile {

	/*
	 *  Attribute
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
	 * Körpergröße in Metern als float, von der an nach Profilen gesucht werden soll
	 */
	private float bodyHeightFrom;
	/**
	 * Körpergröße in Metern als float, bis zu der nach Profilen gesucht werden soll
	 */
	private float bodyHeightTo;
	
	/*
	 * Get-/Set-Operations
	 */
	
	/**
	 * Rückgeben des Alters in Jahren, von dem an nach Profilen gesucht werden soll
	 * @author Philipp Schmitt
	 * @return Mindestalter in Jahren
	 */
	public int getAgeRangeFrom() {
		return ageRangeFrom;
	}
	/**
	 * Setzen des Alters in Jahren, von dem an nach Profilen gesucht werden soll
	 * @author Philipp Schmitt
	 * @param ageRangeFrom Das zu setzende Mindestalter in Jahren
	 */
	public void setAgeRangeFrom(int ageRangeFrom) {
		this.ageRangeFrom = ageRangeFrom;
	}
	/**
	 * Rückgeben des Alters in Jahren, bis zu dem nach Profilen gesucht werden soll
	 * @return Maximalalter in Jahren
	 */
	public int getAgeRangeTo() {
		return ageRangeTo;
	}
	
/**
 * Setzen des Alters in Jahren, bis zu dem nach Profilen gesucht werden soll
 * @param ageRangeTo Das zu setzende Maximalalter in Jahren
 */
	public void setAgeRangeTo(int ageRangeTo) {
		this.ageRangeTo = ageRangeTo;
	}
	/**
	 * Rückgeben der Körpergröße in Metern, von der an nach Profilen gesucht werden soll
	 * @author Philipp Schmitt
	 * @return Mindestkörpergröße in Metern
	 */
	public float getBodyHeightFrom() {
		return bodyHeightFrom;
	}
	/**
	 * Setzen der Körpergröße in Metern, von der an nach Profilen gesucht werden soll
	 * @param bodyHeightFrom Die zu setzende Mindestkörpergröße in Metern
	 */
	public void setBodyHeightFrom(float bodyHeightFrom) {
		this.bodyHeightFrom = bodyHeightFrom;
	}
	
	/**
	 * Rückgeben der Körpergröße in Metern, bis zu der nach Profilen gesucht werden soll
	 * @return Maximalkörpergröße in Metern
	 */
	public float getBodyHeightTo() {
		return bodyHeightTo;
	}
	/**
	 * Setzen der Körpergröße in Metern, bis zu der nach Profilen gesucht werden soll
	 * @param bodyHeightTo Die zu setzende Maximalkörpergröße in Metern
	 */
	public void setBodyHeightTo(float bodyHeightTo) {
		this.bodyHeightTo = bodyHeightTo;
	}
	
	/**
	 * Rückgeben des Suchprofil-Objekts als String mit ausgewählten Variablen-Werten
	 * 
	 * @author Philipp Schmitt
	 * @return Textuelle Beschreibung des Suchprofil-Objekts anhand ausgewählter Eigenschaften
	 */
@Override
	public String toString() {
		return super.getGender() + "," + "Age: " + ageRangeFrom + " - " + ageRangeTo + "," + "Body Height: " + bodyHeightFrom +
				" - " + bodyHeightTo + "," + super.getHairColour() + "," + super.getIsSmoking() + "," + super.getConfession();
		
	}
	
}
