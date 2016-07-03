package de.hdm.core.shared.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.view.client.ProvidesKey;

/**
 * Definition eines Profil-Objekts, das den Nutzer und die von ihm eingegebenen
 * Eigenschaften und Merkmale darstellt. Das Profil ist weiterhin zur korrekten
 * Navigation in der Applikation unabdinglich und fungiert ueber den Usernamen
 * gleichzeitig als User.
 * 
 * @author Philipp Schmitt
 */
public class Profile implements Serializable, Comparable<Profile> {

	/*
	 * Attribute
	 */

	/**
	 * Deklaration der serialVersionUID zur Serialisierung der Objekte
	 */
	private static final long serialVersionUID = 1L;

	public static final ProvidesKey<Profile> KEY_PROVIDER = null;

	private int similiarityToReference;

	/**
	 * Die id des Profils - eindeutiger Primaerschluessel fuer die Datenbank
	 */
	private int id;

	/**
	 * Der Username des Profil-Nutzers, der gleichzeitig als Login ueber die
	 * Google-Mailadresse fungiert
	 */
	private String userName;

	/**
	 * Der reale Vorname des Profil-Nutzers
	 */
	private String name;

	/**
	 * Der reale Nachname des Profil-Nutzers
	 */
	private String lastName;

	/**
	 * Der Geburtstag des Profil-Nutzers
	 */
	private Date dateOfBirth;

	/**
	 * Das Geschlecht des Profil-Nutzers
	 */
	private String gender;

	/**
	 * Die Koerpergroeße des Profil-Nutzers in Meter im Format #.##
	 */
	private float bodyHeight;

	/**
	 * Die Haarfarbe des Profil-Nutzers
	 */
	private String hairColour;

	/**
	 * Die Konfession des Profil-Nutzers
	 */
	private String confession;

	/**
	 * Die Information, ob der Nutzer des Profils raucht oder nicht - zur
	 * uebertragung auf die Datenbank direkt als int formatiert 0 = raucht nicht
	 * 1 = raucht
	 */
	private int isSmoking;

	/**
	 * Die Information, ob das Profil vom aktuellen Nutzer bereits besucht wurde
	 * (ein Eintrag in der profileVisits-Tabelle der Datenbank besteht)
	 */
	private Boolean wasVisited;

	/**
	 * Eine Liste an Wishes (Profilwuenschen), die vom Profil-Nutzer bisher
	 * erstellt wurden
	 */
	private ArrayList<Wish> wishlist;

	/**
	 * Eine Liste an profileBans (Kontaktsperren), die bisher vom Profil-Nutzer
	 * verhaengt worden sind
	 */
	private ArrayList<ProfileBan> banlist;

	/**
	 * Eine Liste an allen frei beschreibenden Eigenschaften, die aktuell in der
	 * Datenbank hinterlegt sind. Hinter diesen Eigenschaftsobjekten befinden
	 * sich wiederum die Informationsobjekte, die dem Profil zugeordnet sind und
	 * die Interessen des Profil-Nutzers weiter beschreiben.
	 */
	private ArrayList<Description> descriptionList;

	/**
	 * Eine Liste an allen auszuwaehlenden Eigenschaften, die aktuell in der
	 * Datenbank hinterlegt sind. Hinter diesen Eigenschaftsobjekten befinden
	 * sich wiederum die Informationsobjekte, die dem Profil zugeordnet sind und
	 * die Interessen des Profil-Nutzers weiter beschreiben.
	 */
	private ArrayList<Selection> selectionList;

	/**
	 * Die Information, ob das Profil vom aktuellen Nutzer gewuenscht wird (ein
	 * Eintrag in der wish-Tabelle der Datenbank besteht)
	 */
	private boolean isFavorite;

	/**
	 * Die Information, ob das Profil vom aktuellen Nutzer gebannt ist (ein
	 * Eintrag in der profileBans-Tabelle der Datenbank besteht)
	 */
	private boolean isBanned;

	/**
	 * Die Information, ob das Profil in der aktuellen Session durch einen Login
	 * mit einer bisher nicht bekannten Googlemail erstellt wurde.
	 */
	private boolean createdOnLogin;

	/**
	 * Standard-Konstruktor der Klasse, bei dem alle Listen und boole'schen
	 * Werte initialisiert werden.
	 */
	public Profile() {
		this.setWishlist(new ArrayList<Wish>());
		this.setBanlist(new ArrayList<ProfileBan>());
		this.setDescriptionList(new ArrayList<Description>());
		this.setSelectionList(new ArrayList<Selection>());
		this.setWasVisited(false);
		this.setIsFavorite(false);
		this.setIsBanned(false);
	}

	/*
	 * Get-/Set-Operations + toString
	 */

	/**
	 * Rueckgeben der Wunschliste (Eintraegen in der wish-Tabelle)
	 * 
	 * @author Philipp Schmitt
	 * @return Liste mit Wunsch-Objekten
	 */
	public ArrayList<Wish> getWishlist() {
		return wishlist;
	}

	/**
	 * Setzen der Wunschliste (Eintraegen in der wish-Tabelle)
	 * 
	 * @author Philipp Schmitt
	 * @param wishlist
	 *            Die zu setzende Liste mit Eintraegen der wish-Tabelle
	 */
	public void setWishlist(ArrayList<Wish> wishlist) {
		this.wishlist = wishlist;
	}

	/**
	 * Rueckgeben der Sperrliste (Eintraegen in der profileBans-Tabelle)
	 * 
	 * @author Philipp Schmitt
	 * @return Liste mit Kontaktsperre-Objekten
	 */
	public ArrayList<ProfileBan> getBanlist() {
		return banlist;
	}

	/**
	 * Setzen der Sperrliste (Eintraegen in der profilebans-Tabelle)
	 * 
	 * @author Philipp Schmitt
	 * @param banlist
	 *            Die zu setzende Liste mit Eintraegen der profileBans-Tabelle
	 */
	public void setBanlist(ArrayList<ProfileBan> banlist) {
		this.banlist = banlist;
	}

	/**
	 * Rueckgeben der beschreibenden Eigenschaftsliste
	 * 
	 * @author Philipp Schmitt
	 * @return Liste mit beschreibenden Eigenschaftsobjekten (
	 *         <code>Description</code>)
	 */
	public ArrayList<Description> getDescriptionList() {
		return descriptionList;
	}

	/**
	 * Setzen der beschreibenden Eigenschaftsliste
	 * 
	 * @author Philipp Schmitt
	 * @param descriptionList
	 *            Die zu setzende Liste mit beschreibenden Eigenschaftsobjekten
	 *            (<code>Description</code>)
	 */
	public void setDescriptionList(ArrayList<Description> descriptionList) {
		this.descriptionList = descriptionList;
	}

	/**
	 * Rueckgeben der auszuwaehlenden Eigenschaftsliste
	 * 
	 * @author Philipp Schmitt
	 * @return Liste mit Auswahl-Eigenschaftsobjekten (<code>Selection</code>)
	 */
	public ArrayList<Selection> getSelectionList() {
		return selectionList;
	}

	/**
	 * Setzen der auszuwaehlenden Eigenschaftsliste
	 * 
	 * @author Philipp Schmitt
	 * @param selectionList
	 *            Die zu setzende Liste mit AuswahlEigenschaftsobjekten (
	 *            <code>Selection</code>)
	 */
	public void setSelectionList(ArrayList<Selection> selectionList) {
		this.selectionList = selectionList;
	}

	/**
	 * Rueckgeben der Profil-Id
	 * 
	 * @author Philipp Schmitt
	 * @return id des Profils
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Setzen der Profil-Id
	 * 
	 * @author Philipp Schmitt
	 * @param id
	 *            Die zu setzende Id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Rueckgeben des Profil-Usernamens
	 * 
	 * @author Philipp Schmitt
	 * @return Username des Profils
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * Setzen des Profil-Usernamens
	 * 
	 * @author Philipp Schmitt
	 * @param userName
	 *            Der zu setzende Username des Profilnutzers
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Rueckgeben des Profil-Vornamens
	 * 
	 * @author Philipp Schmitt
	 * @return Vorname des Profil-Nutzers
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Setzen des Profil-Vornamens
	 * 
	 * @author Philipp Schmitt
	 * @param name
	 *            Der zu setzende Vorname des Profilnutzers
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Rueckgeben des Profil-Nachnamens
	 * 
	 * @author Philipp Schmitt
	 * @return Nachname des Profil-Nutzers
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Setzen des Profil-Nachnamens
	 * 
	 * @author Philipp Schmitt
	 * @param lastName
	 *            Der zu setzende Nachname des Profil-Nutzers
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Rueckgeben des Profil-Geburtstages
	 * 
	 * @author Philipp Schmitt
	 * @return Geburtstag des Profil-Nutzers
	 */
	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	/**
	 * Setzen des Profil-Geburtstages
	 * 
	 * @author Philipp Schmitt
	 * @param dateOfBirth
	 *            Der zu setzende Geburtstag des Profil-Nutzers
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Rueckgeben des Profil-Geschlechts
	 * 
	 * @author Philipp Schmitt
	 * @return Geschlecht des Profil-Nutzers
	 */
	public String getGender() {
		return this.gender;
	}

	/**
	 * Setzen des Profil-Geschlechts
	 * 
	 * @author Philipp Schmitt
	 * @param gender
	 *            Das zu setzende Geschlecht des Profil-Nutzers
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Rueckgeben der Profil-Koerpergroeße
	 * 
	 * @author Philipp Schmitt
	 * @return Koerpergroeße des Profil-Nutzers
	 */
	public float getBodyHeight() {
		return this.bodyHeight;
	}

	/**
	 * Setzen der Profil-Koerpergroeße
	 * 
	 * @author Philipp Schmitt
	 * @param bodyHeight
	 *            Die zu setzende Koerpergroeße des Profil-Nutzers
	 */
	public void setBodyHeight(float bodyHeight) {
		this.bodyHeight = bodyHeight;
	}

	/**
	 * Rueckgeben der Profil-Haarfarbe
	 * 
	 * @author Philipp Schmitt
	 * @return Haarfarbe des Profil-Nutzers
	 */
	public String getHairColour() {
		return this.hairColour;
	}

	/**
	 * Setzen der Profil-Haarfarbe
	 * 
	 * @author Philipp Schmitt
	 * @param hairColour
	 *            Die zu setzende Haarfarbe des Profil-Nutzers
	 */
	public void setHairColour(String hairColour) {
		this.hairColour = hairColour;
	}

	/**
	 * Rueckgeben der Profil-Konfession
	 * 
	 * @author Philipp Schmitt
	 * @return Konfession des Profil-Nutzers
	 */
	public String getConfession() {
		return this.confession;
	}

	/**
	 * Setzen der Profil-Konfession
	 * 
	 * @author Philipp Schmitt
	 * @param confession
	 *            Die zu setzende Konfession des Profil-Nutzers
	 */
	public void setConfession(String confession) {
		this.confession = confession;
	}

	/**
	 * Rueckgeben des Raucher-Status
	 * 
	 * @author Philipp Schmitt
	 * @return Raucher-Status des Profil-Nutzers
	 */
	public int getIsSmoking() {
		return this.isSmoking;
	}

	/**
	 * Setzen des Raucher-Status
	 * 
	 * @author Philipp Schmitt
	 * @param isSmoking
	 *            Der zu setzende Raucher-Status des Profil-Nutzers
	 */
	public void setIsSmoking(int isSmoking) {
		this.isSmoking = isSmoking;
	}

	/**
	 * Rueckgeben des aehnlichkeitswerts
	 * 
	 * @author Philipp Schmitt
	 * @return aehnlichkeitswert zum aktuell eingeloggten Nutzer-Profil
	 */
	public int getSimiliarityToReference() {
		return similiarityToReference;
	}

	/**
	 * Setzen des aehnlichkeitswerts
	 * 
	 * @author Philipp Schmitt
	 * @param similiarityToReference
	 *            Der zu setzende aehnlichkeitswert zum aktuell eingeloggten
	 *            Nutzer-Profil
	 */
	public void setSimiliarityToReference(int similiarityToReference) {
		this.similiarityToReference = similiarityToReference;
	}

	/**
	 * Rueckgeben der Information, ob das Profil vom aktuellen Nutzer-Profil
	 * besucht wurde (ein Eintrag in der profileVisits-Tabelle besteht)
	 * 
	 * @author Philipp Schmitt
	 * @return Information, ob das Profil schon vom aktuellen Nutzer-Profil
	 *         besucht wurde
	 */
	public Boolean getWasVisited() {
		return wasVisited;
	}

	/**
	 * Setzen der Information, ob das Profil vom aktuellen Nutzer-Profil besucht
	 * wurde ( ein Eintrag in der profileVisits-Tabelle besteht)
	 * 
	 * @author Philipp Schmitt
	 * @param wasVisited
	 *            Die zu setzende Information, ob das Profil schon vom aktuellen
	 *            Nutzer-Profil besucht wurde
	 */
	public void setWasVisited(Boolean wasVisited) {
		this.wasVisited = wasVisited;
	}

	/**
	 * Setzen der Information, ob das Profil vom aktuellen Nutzer-Profil
	 * gewuenscht ist ( ein Eintrag in der wishes-Tabelle besteht )
	 * 
	 * @author Philipp Schmitt
	 * @param profileWished
	 *            Die zu setzende Information, ob das Profil vom aktuellen
	 *            Nutzer-Profil gewuenscht ist
	 */
	public void setIsFavorite(boolean profileWished) {
		this.isFavorite = profileWished;
	}

	/**
	 * Rueckgeben der Information, ob das Profil vom aktuellen Nutzer-Profil
	 * gewuenscht ist (ein Eintrag in der wishes-Tabelle besteht)
	 * 
	 * @author Philipp Schmitt
	 * @return Information, ob das Profil schon vom aktuellen Nutzer-Profil
	 *         gewuenscht ist
	 */
	public Boolean getIsFavorite() {
		return isFavorite;
	}

	/**
	 * Setzen der Information, ob das Profil vom aktuellen Nutzer-Profil gebannt
	 * ist ( ein Eintrag in der profileBan-Tabelle besteht )
	 * 
	 * @author Philipp Schmitt
	 * @param profileBanned
	 *            Die zu setzende Information, ob das Profil vom aktuellen
	 *            Nutzer-Profil gewuenscht ist
	 */
	public void setIsBanned(boolean profileBanned) {
		this.isBanned = profileBanned;
	}

	/**
	 * Rueckgeben der Information, ob das Profil vom aktuellen Nutzer-Profil
	 * gebannt ist (ein Eintrag in der profileBan-Tabelle besteht)
	 * 
	 * @author Philipp Schmitt
	 * @return Information, ob das Profil schon vom aktuellen Nutzer-Profil
	 *         gebannt ist
	 */
	public Boolean getIsBanned() {
		return isBanned;
	}

	/**
	 * Setzen der Information, ob das Profil in der aktuellen Session durch
	 * einen Login erstellt wurde
	 * 
	 * @author Philipp Schmitt
	 * @param profileBanned
	 *            Die zu setzende Information, ob das Profil in der aktuellen
	 *            Session durch den Login erstellt wurde
	 */
	public void setCreatedOnLogin(boolean createdOnLogin) {
		this.createdOnLogin = createdOnLogin;
	}

	/**
	 * Rueckgeben der Information, ob das Profil in der aktuellen Session durch
	 * einen Login erstellt wurde
	 * 
	 * @author Philipp Schmitt
	 * @return Information, ob das Profil in der aktuellen Session durch einen
	 *         Login erstellt wurde
	 */
	public Boolean getCreatedOnLogin() {
		return createdOnLogin;
	}

	/**
	 * Vergleich eines Profils mit einem anderen, um einen in Prozent
	 * ausgedrueckten aehnlichkeitswert anhand ausgewaehlter eingetragener
	 * Eigenschaften zu ermitteln
	 * 
	 * @author Philipp Schmitt
	 * @param p
	 *            Das mit diesem Profil zu vergleichende Profil
	 */
	public void equals(Profile p) {
		int percentage = 0;
		int addedPercentage = 100 / 9;
		if (!(p instanceof Profile)) {
			return;
		}

		if (this.getConfession() == p.getConfession()) {
			percentage = percentage + addedPercentage;
		}
		if (this.getIsSmoking() == p.getIsSmoking()) {
			percentage = percentage + addedPercentage;
		}

		ArrayList<String> informationValuesCompare = new ArrayList<String>();
		for (int j = 0; j < this.selectionList.size(); j++) {
			for (Information i : this.selectionList.get(j).getInformationValues()) {
				informationValuesCompare.add(i.getValue());
			}
		}
		ArrayList<String> informationValuesReference = new ArrayList<String>();
		for (int j = 0; j < p.getSelectionList().size(); j++) {
			for (Information i : p.getSelectionList().get(j).getInformationValues()) {
				informationValuesReference.add(i.getValue());
			}
		}
		for (String s : informationValuesCompare) {
			if (informationValuesReference.contains(s)) {
				percentage = percentage + addedPercentage;
			}
		}

		informationValuesCompare.clear();
		informationValuesReference.clear();

		for (int j = 0; j < this.descriptionList.size(); j++) {
			for (Information i : this.descriptionList.get(j).getInformationValues()) {
				informationValuesCompare.add(i.getValue());
			}
		}

		for (int j = 0; j < p.getDescriptionList().size(); j++) {
			for (Information i : p.getDescriptionList().get(j).getInformationValues()) {
				informationValuesReference.add(i.getValue());
			}
		}
		for (String s : informationValuesCompare) {
			if (informationValuesReference.contains(s)) {
				percentage = percentage + addedPercentage;
			}
		}

		this.similiarityToReference = percentage;
	}

	/**
	 * Rueckgeben des Profil-Objekts als String mit ausgewaehlten Variablen-Werten
	 * 
	 * @author Philipp Schmitt
	 * @return Textuelle Beschreibung des Profil-Objekts anhand ausgewaehlter Eigenschaften
	 */
	@Override
	public String toString() {
		Date dateBirth = this.dateOfBirth;
		Date dateNow = new Date();
		int age = dateNow.getYear() - dateBirth.getYear();

		return this.userName + "\n" + this.name + " " + this.lastName + ", " + this.gender + ", " + age + " ("
				+ this.similiarityToReference + "%)";
	}

	/**
	 * Abgleichen der aehnlichkeitswerte zweier Profile
	 * 
	 * @author Philipp Schmitt
	 * @return Negative Zahl fuer <, 0 fuer =, positive Zahl fuer >
	 */
	@Override
	public int compareTo(Profile p1) {
		return Integer.compare(this.similiarityToReference, p1.similiarityToReference);
	}

}
