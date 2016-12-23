package br.com.opining.mvp.login;

import android.content.Intent;

import br.com.opining.mvp.AppPresenter;

public interface LoginPresenter extends AppPresenter {
    void doLoginWithEmail(String email, String password);
    void doLoginWithFacebook();
    void doLoginWithTwitter();
    void doLoginWithGoogle();
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
