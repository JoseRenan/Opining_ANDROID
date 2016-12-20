package br.com.opining.mvp.login;

import br.com.opining.mvp.AppView;

public interface LoginView extends AppView {
    void showInvalidPasswordError(String msg);
    void showInvalidEmailError(String msg);
    void notifySuccessfulLogin();
    void notifyLoginError(String msg);
}
