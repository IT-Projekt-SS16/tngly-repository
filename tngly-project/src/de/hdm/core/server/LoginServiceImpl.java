package de.hdm.core.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.core.client.LoginService;
import de.hdm.core.shared.LoginInfo;

/**
 * Dies ist die Implementierungsklasse des Interface {@link LoginService}
 * 
 * @author Kevin Jaeger
 */
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

	private static final long serialVersionUID = 1L;

	/**
	 * Fuehrt den Login aus und ruft die Informationen des angemeldeten Benutzers
	 * von der Google Accounts API ab. Im Falle einer inkorrekten Anmeldung wird
	 * eine Login-URL zur Anzeige eines Anmeldebildschirms fuer den Benutzer
	 * erstellt.
	 * 
	 * @author Kevin Jaeger
	 * @param requestUri
	 * @return die Logininformationen, die vom Benutzerservice (Google)
	 *         ausgelesen wurden
	 */
	@Override
	public LoginInfo login(String requestUri) {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		LoginInfo loginInfo = new LoginInfo();

		if (user != null) {
			loginInfo.setLoggedIn(true);
			loginInfo.setEmailAddress(user.getEmail());
			loginInfo.setNickname(user.getNickname());
			loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
		} else {
			loginInfo.setLoggedIn(false);
			loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
		}
		return loginInfo;
	}
}
