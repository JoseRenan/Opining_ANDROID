package br.com.opining.mvp.login;

import br.com.opining.mvp.AppPresenter;

/**
 * Created by José Renan on 18/12/2016.
 */

public interface LoginPresenter extends AppPresenter {
    public void doLoginWithEmail(String email, String password);
    public void doLoginWithFacebook();
    public void doLoginWithTwitter();
}
