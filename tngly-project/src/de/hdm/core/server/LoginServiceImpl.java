package de.hdm.core.server;

import java.util.logging.Logger;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.core.client.LoginService;
import de.hdm.core.shared.LoginInfo;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(LoginServiceImpl.class.getName());

	@Override

	public LoginInfo login(String requestUri) {

		log.warning("This is the request URI from UI" + requestUri);

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
