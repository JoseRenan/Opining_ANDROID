package br.com.opining.mvp.login;

import br.com.opining.mvp.AppView;

/**
 * Created by José Renan on 18/12/2016.
 */

public interface LoginView extends AppView {
    public void showLoginError(String msg);
    public void showInvalidPasswordError(String msg);
    public void showInvalidEmailError(String msg);
    public void notifySuccessfulLogin();
}
