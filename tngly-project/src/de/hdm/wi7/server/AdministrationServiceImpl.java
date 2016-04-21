package de.hdm.wi7.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.wi7.shared.AdministrationService;
import de.hdm.wi7.shared.Description;
import de.hdm.wi7.shared.Profile;
import de.hdm.wi7.shared.ProfileBan;
import de.hdm.wi7.shared.Property;
import de.hdm.wi7.shared.Selection;
import de.hdm.wi7.shared.User;
import de.hdm.wi7.shared.Wishlist;

/**
 * <p>
 * Implementierungsklasse des Interface <code>AdministationCommon</code>. Diese
 * Klasse ist <em>die</em> Klasse, die sï¿½mtliche Applikationslogik (oder engl.
 * Business Logic) aggregiert. Sie ist wie eine Spinne, die sï¿½mtliche
 * Zusammenhï¿½nge in ihrem Netz (in unserem Fall die Daten der Applikation)
 * ï¿½berblickt und fï¿½r einen geordneten Ablauf und dauerhafte Konsistenz der
 * Daten und Ablï¿½ufe sorgt.
 * </p>
 * <p>
 * Die Applikationslogik findet sich in den Methoden dieser Klasse. Jede dieser
 * Methoden kann als <em>Transaction Script</em> bezeichnet werden. Dieser Name
 * lï¿½sst schon vermuten, dass hier analog zu Datenbanktransaktion pro
 * Transaktion gleiche mehrere Teilaktionen durchgefï¿½hrt werden, die das
 * System von einem konsistenten Zustand in einen anderen, auch wieder
 * konsistenten Zustand ï¿½berfï¿½hren. Wenn dies zwischenzeitig scheitern
 * sollte, dann ist das jeweilige Transaction Script dafï¿½r verwantwortlich,
 * eine Fehlerbehandlung durchzufï¿½hren.
 * </p>
 * <p>
 * Diese Klasse steht mit einer Reihe weiterer Datentypen in Verbindung. Dies
 * sind:
 * <ol>
 * <li>{@link AdministrationCommon}: Dies ist das <em>lokale</em> - also
 * Server-seitige - Interface, das die im System zur Verfï¿½gung gestellten
 * Funktionen deklariert.</li>
 * <li>{@link AdministrationCommonAsync}: <code>AdministartionCommonImpl</code>
 * und <code>AdministrationCommon</code> bilden nur die Server-seitige Sicht der
 * Applikationslogik ab. Diese basiert vollstï¿½ndig auf synchronen
 * Funktionsaufrufen. Wir mï¿½ssen jedoch in der Lage sein, Client-seitige
 * asynchrone Aufrufe zu bedienen. Dies bedingt ein weiteres Interface, das in
 * der Regel genauso benannt wird, wie das synchrone Interface, jedoch mit dem
 * zusï¿½tzlichen Suffix "Async". Es steht nur mittelbar mit dieser Klasse in
 * Verbindung. Die Erstellung und Pflege der Async Interfaces wird durch das
 * Google Plugin semiautomatisch unterstï¿½tzt. Weitere Informationen unter
 * {@link AdministrationCommonAsync}.</li>
 * <li> {@link RemoteServiceServlet}: Jede Server-seitig instantiierbare und
 * Client-seitig ï¿½ber GWT RPC nutzbare Klasse muss die Klasse
 * <code>RemoteServiceServlet</code> implementieren. Sie legt die funktionale
 * Basis fï¿½r die Anbindung von <code>AdministrationCommonImpl</code> an die
 * Runtime des GWT RPC-Mechanismus.</li>
 * </ol>
 * </p>
 * <p>
 * <b>Wichtiger Hinweis:</b> Diese Klasse bedient sich sogenannter
 * Mapper-Klassen. Sie gehï¿½ren der Datenbank-Schicht an und bilden die
 * objektorientierte Sicht der Applikationslogik auf die relationale
 * organisierte Datenbank ab. Zuweilen kommen "kreative" Zeitgenossen auf die
 * Idee, in diesen Mappern auch Applikationslogik zu realisieren. Siehe dazu
 * auch die Hinweise in {@link #delete(User)} Einzig nachvollziehbares Argument
 * fï¿½r einen solchen Ansatz ist die Steigerung der Performance umfangreicher
 * Datenbankoperationen. Doch auch dieses Argument zieht nur dann, wenn wirklich
 * groï¿½e Datenmengen zu handhaben sind. In einem solchen Fall wï¿½rde man
 * jedoch eine entsprechend erweiterte Architektur realisieren, die wiederum
 * sï¿½mtliche Applikationslogik in der Applikationsschicht isolieren wï¿½rde.
 * Also, keine Applikationslogik in die Mapper-Klassen "stecken" sondern dies
 * auf die Applikationsschicht konzentrieren!
 * </p>
 * <p>
 * Beachten Sie, dass sämtliche Methoden, die mittels GWT RPC aufgerufen werden
 * können ein <code>throws IllegalArgumentException</code> in der
 * Methodendeklaration aufweisen. Diese Methoden dï¿½rfen also Instanzen von
 * {@link IllegalArgumentException} auswerfen. Mit diesen Exceptions können z.B.
 * Probleme auf der Server-Seite in einfacher Weise auf die Client-Seite
 * transportiert und dort systematisch in einem Catch-Block abgearbeitet werden.
 * </p>
 */
@SuppressWarnings("serial")
public class AdministrationServiceImpl extends RemoteServiceServlet implements AdministrationService{

	/**
	 * Eindeutige SerialVersion Id. Wird zum Serialisieren der Klasse
	 * benï¿½tigt.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Referenz auf den DatenbankMapper, der Bauteilobjekte mit der Datenbank
	 * abgleicht.
	 */
	private ProfileMapper profileMapper = null;

	/**
	 * Referenz auf den DatenbankMapper, der Baugruppenobjekte mit der Datenbank
	 * abgleicht.
	 */
	private WishlistMapper wishlistMapper = null;

	/**
	 * Referenz auf den DatenbankMapper, der Endproduktobjekte mit der Datenbank
	 * abgleicht.
	 */
	private ProfileBanMapper profileBanMapper = null;
	
	/**
	 * Referenz auf den DatenbankMapper, der Endproduktobjekte mit der Datenbank
	 * abgleicht.
	 */
	private InformationMapper informationMapper = null;
	
	/**
	 * Referenz auf den DatenbankMapper, der Endproduktobjekte mit der Datenbank
	 * abgleicht.
	 */
	private ProfileVisitMapper profileVisitMapper = null;
	
	/**
	 * Referenz auf den DatenbankMapper, der Endproduktobjekte mit der Datenbank
	 * abgleicht.
	 */
	private PropertyMapper propertyMapper = null;
	
	/**
	 * No-Argument Konstruktor
	 */
	public AdministrationServiceImpl() throws IllegalArgumentException {

	}

	/**
	 * Initialsierungsmethode. Siehe dazu Anmerkungen zum
	 * No-Argument-Konstruktor {@link #AdministrationCommonImpl()}. Diese
	 * Methode muss fï¿½r jede Instanz von <code>AdministrationCommonImpl</code>
	 * aufgerufen werden.
	 */
	public void init() throws IllegalArgumentException {
		/*
		 * Ganz wesentlich ist, dass die Administration einen vollstï¿½ndigen
		 * Satz von Mappern besitzt, mit deren Hilfe sie dann mit der Datenbank
		 * kommunizieren kann.
		 */
		this.profileMapper = ProfileMapper.getProfileMapper();
		this.wishlistMapper = WishlistMapper.getWishlistMapper();
		this.profileBanMapper = ProfileBanMapper.getProfileBanMapper();
		this.informationMapper = InformationMapper.getInformationMapper();
		this.propertyMapper = PropertyMapper.getPropertyMapper();
		this.profileVisitMapper = ProfileVisitMapper.getProfileVisitMapper();
	}
	
	/**
	 * Login Daten werden mit der Datenbank abgeglichen
	 * 
	 */
	@Override
	public User loginUser(boolean isReportGen) throws IllegalArgumentException {
		com.google.appengine.api.users.UserService userService = com.google.appengine.api.users.UserServiceFactory
				.getUserService();

		if (userService.isUserLoggedIn()) {
			com.google.appengine.api.users.User user = userService
					.getCurrentUser();

			User u = new User();
			u.setEmail(user.getEmail());
			u.setUserName(user.getNickname());
			u.setUserId(user.getUserId());
			u.setIsLoggedIn(true);
			return u;
		} else {
			User u = new User();
			u.setIsLoggedIn(false);
			if (isReportGen) {
				u.setLoginUrl(userService
						.createLoginURL(ServersideSettings.PAGE_URL_REPORT));
			} else {
				u.setLoginUrl(userService
						.createLoginURL(ServersideSettings.PAGE_URL_EDITOR));
			}
			return u;
		}
	}

	/**
	 * Durch den Logout wird die SessionID in der DB gespeichert und der
	 * Benutzer wird ausgeloggt
	 */
	@Override
	public String logoutUser(boolean isReportGen)
			throws IllegalArgumentException {
		com.google.appengine.api.users.UserService userService = com.google.appengine.api.users.UserServiceFactory
				.getUserService();

		if (userService.isUserLoggedIn()) {
			if (isReportGen) {
				return userService
						.createLogoutURL(ServersideSettings.PAGE_URL_REPORT);
			}
			return userService.createLogoutURL(ServersideSettings.PAGE_URL_EDITOR);
		}
		return "http://www.google.de";
	}
	

	@Override
	public void createProfile(Profile profile) throws IllegalArgumentException {
		this.profileMapper.createProfile(profile);
		return;
	}

	@Override
	public void editProfile(Profile profile) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteProfile(Profile profile) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Wishlist createWishlist(int profileId) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void editWishlist(Wishlist wishlist) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteWishlist(Wishlist wishlist) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProfileBan createProfileBan(int profileId) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProfileBan(int profileId) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
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
