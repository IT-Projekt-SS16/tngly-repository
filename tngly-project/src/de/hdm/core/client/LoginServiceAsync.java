package de.hdm.core.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.core.shared.LoginInfo;

/**
 * Das asynchrone Gegenstueck des Interface {@link LoginService}. Es wird
 * semiautomatisch durch das Google Plugin erstellt und gepflegt. Daher erfolgt
 * hier keine weitere Dokumentation. Fuer weitere Informationen: siehe das
 * synchrone Interface {@link LoginService}
 * 
 * @author Kevin Jaeger
 */
public interface LoginServiceAsync {
	public void login(String requestUri, AsyncCallback<LoginInfo> async);
}
