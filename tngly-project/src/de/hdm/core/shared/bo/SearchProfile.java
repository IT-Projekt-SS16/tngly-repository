package de.hdm.core.shared.bo;

public class SearchProfile extends Profile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int ageRangeFrom;
	private int ageRangeTo;
	private float bodyHeightFrom;
	private float bodyHeightTo;
	
	public int getAgeRangeFrom() {
		return ageRangeFrom;
	}
	public void setAgeRangeFrom(int ageRangeFrom) {
		this.ageRangeFrom = ageRangeFrom;
	}
	public int getAgeRangeTo() {
		return ageRangeTo;
	}
	public void setAgeRangeTo(int ageRangeTo) {
		this.ageRangeTo = ageRangeTo;
	}
	public float getBodyHeightFrom() {
		return bodyHeightFrom;
	}
	public void setBodyHeightFrom(float bodyHeightFrom) {
		this.bodyHeightFrom = bodyHeightFrom;
	}
	public float getBodyHeightTo() {
		return bodyHeightTo;
	}
	public void setBodyHeightTo(float bodyHeightTo) {
		this.bodyHeightTo = bodyHeightTo;
	}

	public String toString() {
		return super.getGender() + "," + "Age: " + ageRangeFrom + " - " + ageRangeTo + "," + "Body Height: " + bodyHeightFrom +
				" - " + bodyHeightTo + "," + super.getHairColour() + "," + super.getIsSmoking() + "," + super.getConfession();
		
	}
	
}
