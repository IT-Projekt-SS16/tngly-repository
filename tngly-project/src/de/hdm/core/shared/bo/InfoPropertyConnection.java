package de.hdm.core.shared.bo;

public class InfoPropertyConnection {

		private static final long serialVersionUID = 1L;
		
			// The connection id
			private int id;
			
			// The information id
			private int informationId;
			
			// The property id
			private int propertyId;
			
			/*
			 * Get-/Set-Operations + toString
			 */
			
			
			// Get connection id
			public int getId()	{
				return this.id;
			}
			
			// Set connection ID
			public void setId(int id)	{
				this.id = id;
			}
			
			// Get informationId
			public int getInformationId()	{
				return this.informationId;
			}
			
			// Set informationId
			public void setInformationId(int informationId)	{
				this.informationId = informationId;
			}
			
			// Get propertyId
			public int getPropertyId()	{
				return this.propertyId;
			}
			
			// Get propertyId
			public void setPropertyId(int propertyId)	{
				this.propertyId = propertyId;
			}

			
			@Override
			public String toString() {
			    return super.toString() + " " + this.id + " Die Eigenschaft mit der ID " + this.propertyId + " ist verknüpft mit der Information mit der ID " + this.informationId;
			  }
	}
