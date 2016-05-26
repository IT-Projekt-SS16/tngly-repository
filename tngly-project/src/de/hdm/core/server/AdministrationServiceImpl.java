package de.hdm.core.server;

import java.util.ArrayList;
import java.util.Collections;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.core.client.ClientsideSettings;
import de.hdm.core.server.db.InformationMapper;
import de.hdm.core.server.db.ProfileBanMapper;
import de.hdm.core.server.db.ProfileMapper;
import de.hdm.core.server.db.ProfileVisitMapper;
import de.hdm.core.server.db.PropertyMapper;
import de.hdm.core.server.db.WishMapper;
import de.hdm.core.shared.AdministrationService;
import de.hdm.core.shared.AdministrationServiceAsync;
import de.hdm.core.shared.bo.Description;
import de.hdm.core.shared.bo.Profile;
import de.hdm.core.shared.bo.ProfileBan;
import de.hdm.core.shared.bo.Property;
import de.hdm.core.shared.bo.SearchProfile;
import de.hdm.core.shared.bo.Selection;
import de.hdm.core.shared.bo.User;
import de.hdm.core.shared.bo.Wish;
import de.hdm.core.shared.bo.Wishlist;

/**
 * <p>
 * Implementierungsklasse des Interface <code>AdministationService</code>. Diese
 * Klasse ist <em>die</em> Klasse, die saemtliche Applikationslogik (oder engl.
 * Business Logic) aggregiert. Sie ist wie eine Spinne, die saemtliche
 * Zusammenhaenge in ihrem Netz (in unserem Fall die Daten der Applikation)
 * ueberblickt und fuer einen geordneten Ablauf und dauerhafte Konsistenz der
 * Daten und Ablaeufe sorgt.
 * </p>
 * <p>
 * Die Applikationslogik findet sich in den Methoden dieser Klasse. Jede dieser
 * Methoden kann als <em>Transaction Script</em> bezeichnet werden. Dieser Name
 * laesst schon vermuten, dass hier analog zu Datenbanktransaktion pro
 * Transaktion gleiche mehrere Teilaktionen durchgefuehrt werden, die das System
 * von einem konsistenten Zustand in einen anderen, auch wieder konsistenten
 * Zustand ueberfuehren. Wenn dies zwischenzeitig scheitern sollte, dann ist das
 * jeweilige Transaction Script dafuer verantwortlich, eine Fehlerbehandlung
 * durchzufuehren.
 * </p>
 * <p>
 * Diese Klasse steht mit einer Reihe weiterer Datentypen in Verbindung. Dies
 * sind:
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
 * </p>
 * <p>
 * <b>Wichtiger Hinweis:</b> Diese Klasse bedient sich sogenannter
 * Mapper-Klassen. Sie gehoeren der Datenbank-Schicht an und bilden die
 * objektorientierte Sicht der Applikationslogik auf die relationale
 * organisierte Datenbank ab. Zuweilen kommen "kreative" Zeitgenossen auf die
 * Idee, in diesen Mappern auch Applikationslogik zu realisieren. Siehe dazu
 * auch die Hinweise in {@link #delete(User)} Einzig nachvollziehbares Argument
 * fuer einen solchen Ansatz ist die Steigerung der Performance umfangreicher
 * Datenbankoperationen. Doch auch dieses Argument zieht nur dann, wenn wirklich
 * grosse Datenmengen zu handhaben sind. In einem solchen Fall wuerde man jedoch
 * eine entsprechend erweiterte Architektur realisieren, die wiederum saemtliche
 * Applikationslogik in der Applikationsschicht isolieren wuerde. Also, keine
 * Applikationslogik in die Mapper-Klassen "stecken" sondern dies auf die
 * Applikationsschicht konzentrieren!
 * </p>
 * <p>
 * Beachten Sie, dass saemtliche Methoden, die mittels GWT RPC aufgerufen werden
 * koennen ein <code>throws IllegalArgumentException</code> in der
 * Methodendeklaration aufweisen. Diese Methoden duerfen also Instanzen von
 * {@link IllegalArgumentException} auswerfen. Mit diesen Exceptions koennen
 * z.B. Probleme auf der Server-Seite in einfacher Weise auf die Client-Seite
 * transportiert und dort systematisch in einem Catch-Block abgearbeitet werden.
 * </p>
 */
@SuppressWarnings("serial")
public class AdministrationServiceImpl extends RemoteServiceServlet implements AdministrationService {

	/**
	 * Eindeutige SerialVersion Id. Wird zum Serialisieren der Klasse benoetigt.
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
	 */
	public AdministrationServiceImpl() throws IllegalArgumentException {

	}

	/**
	 * Initialsierungsmethode. Siehe dazu Anmerkungen zum
	 * No-Argument-Konstruktor {@link #AdministrationServiceImpl()}. Diese
	 * Methode muss fuer jede Instanz von <code>AdministrationServiceImpl</code>
	 * aufgerufen werden.
	 */
	public void init() throws IllegalArgumentException {
		/*
		 * Ganz wesentlich ist, dass die Administration einen vollstaendigen
		 * Satz von Mappern besitzt, mit deren Hilfe sie dann mit der Datenbank
		 * kommunizieren kann.
		 */
		this.profileMapper = ProfileMapper.profileMapper();
		this.wishMapper = WishMapper.getWishMapper();
		this.profileBanMapper = ProfileBanMapper.getProfileBanMapper();
		this.informationMapper = InformationMapper.getInformationMapper();
		this.propertyMapper = PropertyMapper.getPropertyMapper();
		this.profileVisitMapper = ProfileVisitMapper.getProfileVisitMapper();
	}

//	/**
//	 * Login Daten werden mit der Datenbank abgeglichen
//	 * 
//	 */
//	@Override
//	public User loginUser(boolean isReportGen) throws IllegalArgumentException {
//		com.google.appengine.api.users.UserService userService = com.google.appengine.api.users.UserServiceFactory
//				.getUserService();
//
//		if (userService.isUserLoggedIn()) {
//			com.google.appengine.api.users.User user = userService.getCurrentUser();
//
//			User u = new User();
//			u.setEmail(user.getEmail());
//			u.setUserName(user.getNickname());
//			u.setUserId(user.getUserId());
//			u.setIsLoggedIn(true);
//			return u;
//		} else {
//			User u = new User();
//			u.setIsLoggedIn(false);
//			if (isReportGen) {
//				u.setLoginUrl(userService.createLoginURL(ServersideSettings.PAGE_URL_REPORT));
//			} else {
//				u.setLoginUrl(userService.createLoginURL(ServersideSettings.PAGE_URL_EDITOR));
//			}
//			return u;
//		}
//	}
//
//	/**
//	 * Durch den Logout wird die SessionID in der DB gespeichert und der
//	 * Benutzer wird ausgeloggt
//	 */
//	@Override
//	public String logoutUser(boolean isReportGen) throws IllegalArgumentException {
//		com.google.appengine.api.users.UserService userService = com.google.appengine.api.users.UserServiceFactory
//				.getUserService();
//
//		if (userService.isUserLoggedIn()) {
//			if (isReportGen) {
//				return userService.createLogoutURL(ServersideSettings.PAGE_URL_REPORT);
//			}
//			return userService.createLogoutURL(ServersideSettings.PAGE_URL_EDITOR);
//		}
//		return "http://www.google.de";
//	}

	/**
	 * Interne Methode zur Anlage von Profilen bei Erstanmeldung eines Benutzers
	 * am System.
	 */
	@Override
	public Profile createProfile(Profile profile) throws IllegalArgumentException {
		Profile profileCreated = this.profileMapper.insert(profile);
		// Setzen des applikationaweit eindeutigen, zugreifbaren Profil des
		// Benutzers
		ServersideSettings.setUserProfile(profileCreated);
		return profileCreated;
	}

	/**
	 * Aufruf dieser Methode durch den Benutzer, um Informationen des Profils zu
	 * �ndern.
	 */
	@Override
	public void editProfile(Profile profile) throws IllegalArgumentException {
		// Setzen des applikationaweit eindeutigen, zugreifbaren Profil des
		// Benutzers
		ServersideSettings.setUserProfile(profile);
		// �bergabe des Benutzerprofils an den ProfilMapper zur weiteren
		// Verarbeitung (Update in DB)
		this.profileMapper.edit(profile);
		this.informationMapper.edit(profile);
	}

	/**
	 * Aufruf dieser Methode durch den Benutzer, 
	 * um das eigene Profil endg�ltig aus dem System zu l�schen.
	 */
	@Override
	public void deleteProfile(Profile profile) throws IllegalArgumentException {
		// �bergabe des applikationsweiten Benutzerprofils an den ProfilMapper zur weiteren Verarbeitung (L�schen in DB)
		this.profileBanMapper.delete(profile);
		this.profileVisitMapper.delete(profile);
		this.wishMapper.delete(profile);
		this.informationMapper.delete(profile);
		this.profileMapper.delete(profile);
		// L�schen des applikationsweiten Benutzerprofils (durch NULL-Setzung)
		ServersideSettings.setUserProfile(null);
	}
	
	public Profile findProfileByName(String userEmail) throws IllegalArgumentException {
		return this.profileMapper.findByName(userEmail);
	}
	
	@Override
	public ArrayList<Profile> searchAndCompareProfiles(SearchProfile searchProfile) throws IllegalArgumentException {
		ServersideSettings.setSearchProfile(searchProfile);
		ArrayList<Profile> profiles = this.profileMapper.searchProfileByProfile(searchProfile);
		// profiles = this.propertyMapper.searchForProperties(profiles);
		// profiles = this.informationMapper.searchForInformationValues(profiles);
		Profile reference = ServersideSettings.getUserProfile();
		for (Profile p : profiles){
			p.equals(reference);
		}
		Collections.sort(profiles, Collections.reverseOrder());
		ServersideSettings.setProfilesFoundAndCompared(profiles);
		ClientsideSettings.setProfilesFoundAndCompared(profiles);
		System.out.println("Clientside-Settings, ProfilesFoundAndCompared wird gesetzt");
		return profiles;
	}

	@Override
	public void addWishToWishlist(Wish wish) throws IllegalArgumentException {
		this.wishMapper.insert(wish);

	}

	@Override
	public void deleteWishFromWishlist(Wish wish) throws IllegalArgumentException {
		this.wishMapper.delete(wish);

	}
	

	@Override
	public ProfileBan createProfileBan(Profile bannedp, Profile banningp) throws IllegalArgumentException {
			ProfileBan pb = new ProfileBan();
			pb.setBannedProfileId(bannedp.getId());
			pb.setBanningProfileId(banningp.getId());
			pb.setId(1);
			return this.profileBanMapper.insert(pb);
	}

	@Override
	public void deleteProfileBan(ProfileBan pb) throws IllegalArgumentException {
		this.profileBanMapper.delete(pb);

	}

	@Override
	public Property createProperty() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void editProperty(Property property) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteProperty(Property property) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public Property createSelection(int SelectionId) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void editSelection(Selection selection) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSelection(Selection selection) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public Description createDescription(int SelectionId) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void editDescription(Description description) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteDescription(Description description) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public Description createInformation(int ProfileId, int PropertyId) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void editInformation(Description description) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteInformation(Description description) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}
}
