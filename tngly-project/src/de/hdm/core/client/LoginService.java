package de.hdm.core.client;

import com.google.gwt.user.client.rpc.RemoteService;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.core.shared.LoginInfo;

@RemoteServiceRelativePath("login")

public interface LoginService extends RemoteService {

	public LoginInfo login(String requestUri);

}
