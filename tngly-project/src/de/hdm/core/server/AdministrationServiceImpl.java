package de.hdm.core.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.core.server.db.InformationMapper;
import de.hdm.core.server.db.ProfileBanMapper;
import de.hdm.core.server.db.ProfileMapper;
import de.hdm.core.server.db.ProfileVisitMapper;
import de.hdm.core.server.db.PropertyMapper;
import de.hdm.core.server.db.WishMapper;
import de.hdm.core.shared.AdministrationService;
import de.hdm.core.shared.AdministrationServiceAsync;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.ProfileBan;
import de.hdm.core.shared.bo.ProfileVisit;
import de.hdm.core.shared.bo.SearchProfile;
import de.hdm.core.shared.bo.Wish;

/**
 * Implementierungsklasse des Interface {@link AdministationService}. Diese
 * Klasse ist <em>die</em> Klasse, die saemtliche Applikationslogik (oder engl.
 * Business Logic) aggregiert. Die Applikationslogik findet sich in den Methoden
 * dieser Klasse. Diese Klasse steht mit einer Reihe weiterer Datentypen in
 * Verbindung. Dies sind:
 * <ol>
 * <li>{@link AdministrationService}: Dies ist das <em>lokale</em> - also
 * Server-seitige - Interface, das die im System zur Verfuegung gestellten
 * Funktionen deklariert.</li>
 * <li>{@link AdministrationServiceAsync}:
 * <code>AdministartionServiceImpl</code> und <code>AdministrationService</code>
 * bilden nur die Server-seitige Sicht der Applikationslogik ab. Diese basiert
 * vollstaendig auf synchronen Funktionsaufrufen. Wir muessen jedoch in der Lage
 * sein, Client-seitige asynchrone Aufrufe zu bedienen. Dies bedingt ein
 * weiteres Interface, das in der Regel genauso benannt wird, wie das synchrone
 * Interface, jedoch mit dem zusaetzlichen Suffix "Async". Es steht nur
 * mittelbar mit dieser Klasse in Verbindung. Die Erstellung und Pflege der
 * Async Interfaces wird durch das Google Plugin semiautomatisch unterstuetzt.
 * Weitere Informationen unter {@link AdministrationServiceAsync}.</li>
 * <li>{@link RemoteServiceServlet}: Jede Server-seitig instantiierbare und
 * Client-seitig ueber GWT RPC nutzbare Klasse muss die Klasse
 * <code>RemoteServiceServlet</code> implementieren. Sie legt die funktionale
 * Basis fuer die Anbindung von <code>AdministrationServiceImpl</code> an die
 * Runtime des GWT RPC-Mechanismus.</li>
 * </ol>
 * <b>Wichtiger Hinweis:</b> Diese Klasse bedient sich sogenannter
 * Mapper-Klassen. Sie gehoeren der Datenbank-Schicht an und bilden die
 * objektorientierte Sicht der Applikationslogik auf die relationale
 * organisierte Datenbank ab. Beachten Sie, dass saemtliche Methoden, die
 * mittels GWT RPC aufgerufen werden koennen ein
 * <code>throws IllegalArgumentException</code> in der Methodendeklaration
 * aufweisen. Diese Methoden duerfen also Instanzen von
 * {@link IllegalArgumentException} auswerfen. Mit diesen Exceptions koennen
 * z.B. Probleme auf der Server-Seite in einfacher Weise auf die Client-Seite
 * transportiert und dort systematisch in einem Catch-Block abgearbeitet werden.
 * 
 * @author Kevin Jaeger, Philipp Schmitt
 */
public class AdministrationServiceImpl extends RemoteServiceServlet implements AdministrationService {

	/**
	 * Das Profil des aktuellen Benutzer. Hier hinterlegt, um schnell darauf
	 * zurueckgreifen zu koennen.
	 */
	private Profile currentUserProfile = null;

	/**
	 * Eindeutige SerialVersion ID. Wird zum Serialisieren der Klasse benoetigt.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Referenz auf den DatenbankMapper, der Profilobjekte mit der Datenbank
	 * abgleicht.
	 */
	private ProfileMapper profileMapper = null;

	/**
	 * Referenz auf den DatenbankMapper, der Wunschlistenobjekte mit der
	 * Datenbank abgleicht.
	 */
	private WishMapper wishMapper = null;

	/**
	 * Referenz auf den DatenbankMapper, der Kontaktsperrenobjekte mit der
	 * Datenbank abgleicht.
	 */
	private ProfileBanMapper profileBanMapper = null;

	/**
	 * Referenz auf den DatenbankMapper, der Informationsobjekte mit der
	 * Datenbank abgleicht.
	 */
	private InformationMapper informationMapper = null;

	/**
	 * Referenz auf den DatenbankMapper, der Profilbesuchsobjekte mit der
	 * Datenbank abgleicht.
	 */
	private ProfileVisitMapper profileVisitMapper = null;

	/**
	 * Referenz auf den DatenbankMapper, der Eigenschaftsobjekte mit der
	 * Datenbank abgleicht.
	 */
	private PropertyMapper propertyMapper = null;

	/**
	 * No-Argument Konstruktor
	 * 
	 * @throws IllegalArgumentException
	 *             Benoetigt fuer RPC-Core
	 */
	public AdministrationServiceImpl() throws IllegalArgumentException {

	}

	/**
	 * Initialisiert die Implementierung des Interface AdministrationService.
	 * Diese Methode muss fuer jede Instanz von
	 * <code>AdministrationServiceImpl</code> aufgerufen werden.
	 * 
	 * @author Kevin Jaeger
	 */
	@Override
	public void init() throws IllegalArgumentException {
		/*
		 * Ganz wesentlich ist, dass die Administration einen vollstaendigen
		 * Satz von Mappern besitzt, mit deren Hilfe sie dann mit der Datenbank
		 * kommunizieren kann.
		 */
		this.profileMapper = ProfileMapper.profileMapper();
		this.wishMapper = WishMapper.wishMapper();
		this.profileBanMapper = ProfileBanMapper.profileBanMapper();
		this.informationMapper = InformationMapper.informationMapper();
		this.propertyMapper = PropertyMapper.propertyMapper();
		this.profileVisitMapper = ProfileVisitMapper.profileVisitMapper();
	}

	/**
	 * Erstellt ein neues Profil in der Datenbank. Dazu ruft sie mit dem
	 * uebergebenen Profil den ProfilMapper auf, der dieses dann ueber eine
	 * INSERT-Abfrage in die Datenbank einfuegt.
	 * 
	 * @author Kevin Jaeger
	 * @param profile
	 *            Das Profil, das in die Datenbank eingefuegt werden soll
	 */
	@Override
	public void createProfile(Profile profile) throws IllegalArgumentException {
		this.profileMapper.insert(profile);
	}

	/**
	 * Aktualisiert die Attribute und Eigenschaften eines Profils in der
	 * Datenbank. Dazu ruft sie mit dem uebergebenen Profil den ProfilMapper auf,
	 * der dieses dann ueber eine UPDATE-Abfrage in der Datenbank aktualisiert.
	 * 
	 * @author Kevin Jaeger
	 * @param profile
	 *            Das Profil, das aktualisiert werden soll
	 */
	@Override
	public void editProfile(Profile profile) throws IllegalArgumentException {
		this.profileMapper.edit(profile);
		this.informationMapper.edit(profile);
	}

	/**
	 * Loescht das uebergebene Profil endgueltig aus der Datenbank. Die Methode
	 * ruft dazu, alle notwendigen Mapper (Profil, Information, Wish,
	 * ProfileBan, ProfileVisit) mit dem uebergebenen Profil auf, um ein
	 * vollstaendiges Loeschen aller Profildaten sicherzustellen.
	 * 
	 * @author Kevin Jaeger
	 * @param profile
	 *            Das Profil, das aus der Datenbank entfernt werden soll
	 */
	@Override
	public void deleteProfile(Profile profile) throws IllegalArgumentException {
		this.profileBanMapper.delete(profile);
		this.profileVisitMapper.delete(profile);
		this.wishMapper.delete(profile);
		this.informationMapper.delete(profile);
		this.profileMapper.delete(profile);
	}

	/**
	 * Gibt das Benutzerprofil mit allen dazugehoerigen Informationen anhand des
	 * Username zurueck.
	 * 
	 * @author Philipp Schmitt, Kevin Jaeger
	 * @param userEmail
	 *            Der Username, zu dem das Profil zurueckgegeben werden soll
	 * @return Das gefundene Benutzerprofil
	 */
	@Override
	public Profile getProfileByUserName(String userEmail) throws IllegalArgumentException {
		ArrayList<Profile> profiles = new ArrayList<Profile>();

		/*
		 * Wenn aktuell kein Profil in der Datenbank unter dem uebergebenen
		 * Username vorhanden ist, dann wird ein neues Profil mit Username &
		 * Geburtsdatum in der Datenbank angelegt.
		 */
		if (this.profileMapper.findByName(userEmail) == null) {

			Profile toCreate = new Profile();
			toCreate.setUserName(userEmail);
			Date currentDate = new Date();
			toCreate.setDateOfBirth(currentDate);
			this.createProfile(toCreate);

			Profile profile2 = this.profileMapper.findByName(userEmail);
			profiles.add(profile2);
		}

		/*
		 * Wenn aktuell ein Profil in der Datenbank unter dem uebergebenen
		 * Username vorhanden ist, dann wird dieses von den entsprechenden
		 * Mappern (Property & Information) befuellt und zurueckgegeben.
		 */
		if (this.profileMapper.findByName(userEmail) != null) {
			Profile profile = this.profileMapper.findByName(userEmail);
			profiles.add(profile);
			profiles = this.propertyMapper.searchForProperties(profiles);
			profiles = this.informationMapper.searchForInformationValues(profiles);
		}
		return profiles.get(0);
	}

	/**
	 * Sucht Profile nach einem uebergebenen Suchprofil und vergleicht diese auf
	 * uehnlichkeit ihrer Eigenschaften.
	 * 
	 * @author Kevin Jaeger
	 * @param unseenChecked
	 *            Zeigt, ob vom Benutzer gesehene Profile aus dem Suchergebnis
	 *            entfernt werden sollen
	 * @param searchProfile
	 *            Die vom Benutzer eingegebenen Kriterien fuer die Profilsuche
	 * @return Eine Liste mit nach uehnlichkeit verglichenen, sortierten Profilen
	 */
	@Override
	public ArrayList<Profile> searchAndCompareProfiles(Boolean unseenChecked, SearchProfile searchProfile)
			throws IllegalArgumentException {
		/*
		 * Suche nach Profilen, die mit dem Suchprofil uebereinstimmen.
		 */
		ArrayList<Profile> profiles = this.profileMapper.searchProfileByProfile(searchProfile);

		/*
		 * Auslesen des aktuellen Benutzernamen aus der Google Accounts API, um
		 * das Profil des aktuellen Benutzers aus der Datenbank zu lesen.
		 */
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		int atIndex = user.getEmail().indexOf("@");
		String userName = user.getEmail().substring(0, atIndex);
		currentUserProfile = this.getProfileByUserName(userName);

		/*
		 * Wenn nur ungesehene Profile vom Benutzer gesucht werden, werden alle
		 * schon besuchten Profile aus dem Suchergebnis entfernt.
		 */
		if (unseenChecked) {
			for (int x = 0; x < profiles.size(); x++) {
				Profile p = profiles.get(x);
				if (p.getWasVisited()) {
					profiles.remove(x);
				}
			}
		}

		/*
		 * Da dem Suchenden keine Profile angezeigt werden duerfen, die diesen
		 * "blockiert" haben, muessen diese Profile aus dem Suchergebnis entfernt
		 * werden.
		 */
		for (int x = 0; x < profiles.size(); x++) {
			Profile p = profiles.get(x);
			if (this.profileBanMapper.isProfileBanned(p.getId(), currentUserProfile.getId())) {
				profiles.remove(x);
			}
		}

		/*
		 * Da dem Suchenden auch keine Profile angezeigt werden sollen, die
		 * dieser "blockiert" hat, muessen diese Profile aus dem Suchergebnis
		 * entfernt werden.
		 */
		for (int x = 0; x < profiles.size(); x++) {
			Profile p = profiles.get(x);
			if (this.profileBanMapper.isProfileBanned(currentUserProfile.getId(), p.getId())) {
				profiles.remove(x);
			}
		}

		/*
		 * Ebenso soll dem Suchenden nicht sein eigenes Benutzerprofil angezeigt
		 * werden, deshalb wird dieses aus dem Suchergebnis entfernt.
		 */
		for (int x = 0; x < profiles.size(); x++) {
			Profile p = profiles.get(x);
			if (currentUserProfile.getUserName() == p.getUserName()) {
				profiles.remove(x);
			}
		}

		/*
		 * Da nun alle Profile entfernt wurden, die zu entfernen waren, wird fuer
		 * die restlichen Profile ueberprueft, ob diese schon besucht wurden bzw.
		 * einen Wunsch/Favorite des Benutzers darstellen.
		 */
		for (int x = 0; x < profiles.size(); x++) {
			Profile p = profiles.get(x);
			p.setWasVisited(this.profileVisitMapper.wasProfileVisited(currentUserProfile, p));
			p.setIsFavorite(this.wishMapper.isProfileWished(currentUserProfile.getId(), p.getId()));
		}

		/*
		 * Auslesen aller Profileigenschaften sowie deren Informationen
		 */
		profiles = this.propertyMapper.searchForProperties(profiles);
		profiles = this.informationMapper.searchForInformationValues(profiles);

		/*
		 * Vergleich aller gefundenen Profile mit dem eigenen Benutzerprofil auf
		 * uehnlichkeit bei Eigenschaften
		 */
		for (int x = 0; x < profiles.size(); x++) {
			Profile p = profiles.get(x);
			p.equals(currentUserProfile);
		}

		/*
		 * Sortierung aller verglichenen Profile anhand des uehnlichkeitswertes
		 */
		Collections.sort(profiles, Collections.reverseOrder());
		return profiles;
	}

	/**
	 * Erstellt einen Eintrag fuer einen Profilbesuch in der Datenbank.
	 * 
	 * @author Kevin Jaeger
	 * @param visitedProfiles
	 *            Eine Liste mit Profilbesuch-Objekten, die vom Benutzer besucht
	 *            werden
	 * @return
	 */
	@Override
	public void createProfileVisit(ArrayList<ProfileVisit> visitedProfiles) throws IllegalArgumentException {
		this.profileVisitMapper.insert(visitedProfiles);
	}

	/**
	 * Luescht einen Eintrag fuer einen Profilbesuch in der Datenbank.
	 * 
	 * @author Kevin Jaeger
	 * @param visitedProfiles
	 *            Eine Liste mit Profilbesuch-Objekten, die vom Benutzer besucht
	 *            werden
	 * @return
	 */
	@Override
	public void deleteProfileVisit(ArrayList<ProfileVisit> visitedProfiles) throws IllegalArgumentException {
		this.profileVisitMapper.delete(visitedProfiles);
	}

	/**
	 * Gibt einen booleschen Wert zurueck, der anzeigt, ob ein Profil vom
	 * Benutzer schon besucht oder nicht.
	 * 
	 * @author Kevin Jaeger
	 * @param currentUserProfile
	 *            Das Profil des aktuellen Benutzers
	 * @param dependantProfile
	 *            Das Profil, bei dem geprueft wird, ob es besucht wurde
	 * @return True (wenn Profil besucht wurde), False (wenn Profil nicht
	 *         besucht wurde)
	 */
	@Override
	public Boolean wasProfileVisited(Profile currentUserProfile, Profile dependantProfile)
			throws IllegalArgumentException {
		return this.profileVisitMapper.wasProfileVisited(currentUserProfile, dependantProfile);
	}

	/**
	 * Gibt einen booleschen Wert zurueck, der anzeigt, ob ein Profil einen
	 * "Wunsch" des Benutzers darstellt.
	 * 
	 * @author Philipp Schmitt
	 * @param currentUserProfile
	 *            Das Profil des aktuellen Benutzers
	 * @param selectedProfile
	 *            Das Profil, bei dem geprueft wird, ob es einen
	 *            Favorite/"Wunsch" darstellt
	 * @return True (wenn Profil Favorite ist), False (wenn Profil kein Favorite
	 *         ist)
	 */
	@Override
	public Boolean isProfileWished(Profile currentUserProfile, Profile selectedProfile)
			throws IllegalArgumentException {
		return this.wishMapper.isProfileWished(currentUserProfile.getId(), selectedProfile.getId());
	}

	/**
	 * Gibt einen booleschen Wert zurueck, der anzeigt, ob ein Profil einen
	 * "Wunsch" des Benutzers darstellt.
	 * 
	 * @author Philipp Schmitt
	 * @return Liste mit "Wuenschen"/Favoriten (vollstuendige Profile mit allen
	 *         Informationen)
	 */
	@Override
	public ArrayList<Profile> getWishes() throws IllegalArgumentException {

		/*
		 * Auslesen des aktuellen Benutzernamen aus der Google Accounts API, um
		 * das Profil des aktuellen Benutzers aus der Datenbank zu lesen.
		 */
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		int atIndex = user.getEmail().indexOf("@");
		String userName = user.getEmail().substring(0, atIndex);
		Profile temp = this.profileMapper.findByName(userName);

		/*
		 * Auslesen der "Wuensche"/Favoriten des aktuellen Benutzers.
		 */
		ArrayList<Wish> ws = new ArrayList<Wish>();
		ws = this.wishMapper.findWishedProfiles(temp.getId());

		/*
		 * Zusammenstellen der Profile der gefundenen "Wuensche"/Favoriten.
		 */
		ArrayList<Profile> rs = new ArrayList<Profile>();
		for (Wish w : ws) {
			rs.add(this.profileMapper.findByKey(w.getWishedProfileId()));
		}

		/*
		 * Markieren der zusammengestellten "Wuensche"/Favoriten als
		 * "Favorite/Wunsch"
		 */
		for (Profile p : rs) {
			p.setIsFavorite(true);
		}

		/*
		 * Auslesen aller Profileigenschaften sowie deren Informationen
		 */
		rs = this.propertyMapper.searchForProperties(rs);
		rs = this.informationMapper.searchForInformationValues(rs);

		return rs;
	}

	/**
	 * Luescht ungewollte "Wuensche"/Favoriten aus der Datenbank.
	 * 
	 * @author Philipp Schmitt
	 * @param toUnwish
	 *            Liste mit "Wunsch"-Objekten (enthalten nur Profil-IDs)
	 * @return
	 */
	@Override
	public void deleteWishes(ArrayList<Wish> toUnwish) throws IllegalArgumentException {

		/*
		 * Auslesen des aktuellen Benutzernamen aus der Google Accounts API, um
		 * das Profil des aktuellen Benutzers aus der Datenbank zu lesen.
		 */
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		int atIndex = user.getEmail().indexOf("@");
		String userName = user.getEmail().substring(0, atIndex);
		Profile temp = this.profileMapper.findByName(userName);

		/*
		 * uebergebene "Wuensche" werden aus der Datenbank entfernt.
		 */
		for (Wish w : toUnwish) {
			w.setWishingProfileId(temp.getId());
			this.wishMapper.delete(w);
		}
	}

	/**
	 * Fuegt ein ausgewuehltes Profil zu der "Wunschliste" des Benutzers hinzu.
	 * 
	 * @author Philipp Schmitt
	 * @param wishedProfileId
	 *            Das "gewuenschte" Profil
	 * @param wishingProfileId
	 *            Das "wuenschende" Profil
	 * @return Der angelegte "Wunsch"
	 */
	@Override
	public Wish addWishToWishlist(int wishedProfileId, int wishingProfileId) throws IllegalArgumentException {
		Wish wish = new Wish();
		wish.setWishedProfileId(wishedProfileId);
		wish.setWishingProfileId(wishingProfileId);
		return this.wishMapper.insert(wish);
	}

	/**
	 * Luescht ein ausgewuehltes Profil von der "Wunschliste" des Benutzers.
	 * 
	 * @author Philipp Schmitt
	 * @param wishedProfileId
	 *            Das "gewuenschte" Profil
	 * @param wishingProfileId
	 *            Das "wuenschende" Profil
	 * @return
	 */
	@Override
	public void deleteWishFromWishlist(int wishedProfileId, int wishingProfileId) throws IllegalArgumentException {
		Wish wish = new Wish();
		wish.setWishedProfileId(wishedProfileId);
		wish.setWishingProfileId(wishingProfileId);
		this.wishMapper.delete(wish);
	}

	/**
	 * Gibt einen booleschen Wert zurueck, der anzeigt, ob das ausgewuehlte Profil
	 * vom Benutzer "blockiert" wird.
	 * 
	 * @author Philipp Schmitt
	 * @param currentUserProfile
	 *            Das Profil des aktuellen Benutzers
	 * @param selectedProfile
	 *            Das zu ueberpruefende Profil
	 * @return True (wenn Profil "blockiert" ist), False (wenn Profil nicht
	 *         "blockiert" ist)
	 */
	@Override
	public Boolean isProfileBanned(Profile currentUserProfile, Profile selectedProfile)
			throws IllegalArgumentException {
		return this.profileBanMapper.isProfileBanned(currentUserProfile.getId(), selectedProfile.getId());
	}

	/**
	 * Gibt eine Liste mit Profilen zurueck, die vom Benutzer "blockiert" werden.
	 * 
	 * @author Philipp Schmitt
	 * @return Liste mit "blockierten" Profilen
	 */
	@Override
	public ArrayList<Profile> getBans() throws IllegalArgumentException {

		/*
		 * Auslesen des aktuellen Benutzernamen aus der Google Accounts API, um
		 * das Profil des aktuellen Benutzers aus der Datenbank zu lesen.
		 */
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		int atIndex = user.getEmail().indexOf("@");
		String userName = user.getEmail().substring(0, atIndex);
		Profile temp = this.profileMapper.findByName(userName);

		/*
		 * Auslesen der "blockierten" Profile-IDs des aktuellen Benutzers.
		 */
		ArrayList<ProfileBan> pbs = new ArrayList<ProfileBan>();
		pbs = this.profileBanMapper.findBannedProfiles(temp.getId());

		/*
		 * Zusammenstellen der "blockierten" Profile der gefundenen Profile-IDs.
		 */
		ArrayList<Profile> rs = new ArrayList<Profile>();
		for (ProfileBan pb : pbs) {
			rs.add(this.profileMapper.findByKey(pb.getBannedProfileId()));
		}

		/*
		 * Markieren der zusammengestellten "blockierten" Profile als
		 * "blockiert"
		 */
		for (Profile p : rs) {
			p.setIsBanned(true);
		}

		/*
		 * Auslesen aller Profileigenschaften sowie deren Informationen
		 */
		rs = this.propertyMapper.searchForProperties(rs);
		rs = this.informationMapper.searchForInformationValues(rs);

		return rs;
	}

	/**
	 * Erstellt einen Eintrag in der Datenbank, der anzeigt, dass das
	 * ausgewuehlte Profil "blockiert" wird.
	 * 
	 * @author Philipp Schmitt
	 * @param bannedpId
	 *            Das zu blockierende Profil
	 * @param banningpId
	 *            Das blockierende Profil
	 * @return ProfileBan-Objekt
	 */
	@Override
	public ProfileBan createProfileBan(int bannedpId, int banningpId) throws IllegalArgumentException {
		ProfileBan pb = new ProfileBan();
		pb.setBannedProfileId(bannedpId);
		pb.setBanningProfileId(banningpId);
		return this.profileBanMapper.insert(pb);
	}

	/**
	 * Luescht den Eintrag in der Datenbank, der anzeigt, dass das ausgewuehlte
	 * Profil "blockiert" wird.
	 * 
	 * @author Philipp Schmitt
	 * @param toUnban
	 *            Liste mit ProfileBan-Objekten (enthalten nur Profil-IDs)
	 * @return
	 */
	@Override
	public void deleteProfileBan(ArrayList<ProfileBan> toUnban) throws IllegalArgumentException {

		/*
		 * Auslesen des aktuellen Benutzernamen aus der Google Accounts API, um
		 * das Profil des aktuellen Benutzers aus der Datenbank zu lesen.
		 */
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		int atIndex = user.getEmail().indexOf("@");
		String userName = user.getEmail().substring(0, atIndex);
		Profile temp = this.profileMapper.findByName(userName);

		/*
		 * Entfernen der "Block"-Eintruege in der Datenbank
		 */
		for (ProfileBan pb : toUnban) {
			pb.setBanningProfileId(temp.getId());
			this.profileBanMapper.delete(pb);
		}

	}

	/**
	 * Loescht den Eintrag in der Datenbank, der anzeigt, dass das ausgewaehlte
	 * Profil "blockiert" wird.
	 * 
	 * @author Philipp Schmitt
	 * @param banningProfileId
	 *            Das blockierende Profil
	 * @param bannedProfileId
	 *            Das zu blockierende Profil
	 * @return
	 */
	@Override
	public void deleteProfileBan(int banningProfileId, int bannedProfileId) throws IllegalArgumentException {
		ProfileBan pb = new ProfileBan();
		pb.setBannedProfileId(bannedProfileId);
		pb.setBanningProfileId(banningProfileId);
		this.profileBanMapper.delete(pb);
	}
}
