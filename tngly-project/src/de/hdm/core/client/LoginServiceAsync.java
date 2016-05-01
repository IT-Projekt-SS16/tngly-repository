package de.hdm.core.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.core.shared.LoginInfo;

public interface LoginServiceAsync {

	public void login(String requestUri, AsyncCallback<LoginInfo> async);

}
