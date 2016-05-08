package de.hdm.core.shared.bo;

import java.io.Serializable;
import java.util.Date;

public class ProfileVisit implements Serializable{

	public ProfileVisit()	{
		
	}

	private static final long serialVersionUID = 1L;

	/*
	 *  Attributes
	 */


	// The ProfileVisit´s ID
	private int id;
	
	// The visiting profile
	private int visitingProfileId;
	
	// The visited profile
	private int visitedProfileId;
	
	// The visit´s timestamp
	private Date timestamp;
	
	/*
	 * Get-/Set-Operations + toString
	 */
	
	
	// Get ProfileVisits´s id
	public int getId()	{
		return this.id;
	}
	
	// Set profile´s ID
	public void setId(int id)	{
		this.id = id;
	}
	
	// Get ProfileVisit´s visiting profile id
	public int getVisitingProfileId()	{
		return this.visitingProfileId;
	}
	
	// Set ProfileVisit´s visiting profile id
	public void setVisitingProfileId(int visitingProfileId)	{
		this.visitingProfileId = visitingProfileId;
	}
	
	// Get ProfileVisit´s visited profile id
	public int getVisitedProfileId()	{
		return this.visitedProfileId;
	}
	
	// Get ProfileVisit´s visited profile id
	public void setVisitedProfileId(int visitedProfileId)	{
		this.visitedProfileId = visitedProfileId;
	}
	
	// Get timestamp
	public Date getTimestamp()	{
		return this.timestamp;
	}
	
	// Set timestamp
	public void setTimestamp(Date timestamp)	{
		this.timestamp = timestamp;
	}
	
	@Override
	public String toString() {
	    return super.toString() + " " + this.id + " Das Profil mit der id " + this.visitingProfileId + " besuchte das Profil mit der id " + this.visitedProfileId + " um " + this.timestamp;
	  }
}
