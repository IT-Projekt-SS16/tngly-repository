
package de.hdm.core.shared.bo;

import java.io.Serializable;

/**
 * Definition eines Property-Objekts, das dem Nutzer die Möglichkeit bietet,
 * seine Interessen zu bestimmten Eigenschaftsfeldern zu bekunden. Es gibt zwei
 * verschiedene Arten von Eigenschaften:
 * 
 * - Zu beschreibende Eigenschaften (<code>Description</code>) - Auszuwählende
 * Eigenschaften (<code>Selection</code>)
 * 
 * Während der User bei Description eine freie Eingabe tätigen kann, muss er
 * sich bei Selections einen der präsentierten Werte entscheiden.
 * 
 * @author Philipp Schmitt
 */

public class Property implements Serializable {

	/*
	 * Attribute
	 */

	/**
	 * Deklaration der serialVersionUID zur Serialisierung der Objekte
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Die id der Eigenschaft - eindeutiger Primärschlüssel für die Datenbank
	 */
	private int id;

	/**
	 * Die textuelle Beschreibung der Eigenschaft, z.B. "Meine Hobbies"
	 */
	private String textualDescription;

	/*
	 * Get-/Set-Operations + toString
	 */

	/**
	 * Rückgeben der Eigenschafts-Id
	 * 
	 * @author Philipp Schmitt
	 * @return Die eindeutige Id des Eigenschafts-Objekts
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Setzen der Eigenschafts-Id
	 * 
	 * @author Philipp Schmitt
	 * @param id
	 *            Die zu setzende Id des Eigenschafts-Objekts
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Rückgeben der textuellen Beschreibung der Eigenschaft
	 * 
	 * @author Philipp Schmitt
	 * @return Die textuelle Beschreibung des Eigenschafts-Objekts
	 */
	public String getTextualDescription() {
		return this.textualDescription;
	}

	/**
	 * Setzen der textuellen Beschreibung der Eigenschaft
	 * 
	 * @author Philipp Schmitt
	 * @param textualDescription
	 *            Die zu setzende textuelle Beschreibung der Eigenschaft
	 */
	public void setTextualDescription(String textualDescription) {
		this.textualDescription = textualDescription;
	}

	/**
	 * Rückgeben des Eigenschafts-Objekts als String mit ausgewählten
	 * Variablen-Werten
	 * 
	 * @author Philipp Schmitt
	 * @return Textuelle Beschreibung des Profilbesuchs-Objekts anhand ausgewählter Eigenschaften
	 */
	@Override
	public String toString() {
		return super.toString() + " Description" + this.textualDescription;
	}

}
