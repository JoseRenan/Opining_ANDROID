package br.com.opining.mvp.login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

public interface Authenticator {

    interface OnLoginListener {
        void onLoginError(int msg);
        void onLoginError(@NonNull Exception ex);
        void onLoginSuccessful();
        AppCompatActivity getActivity();
    }

    void onActivityResult(int requestCode, int resultCode, Intent data);
    void doLoginWithFacebook(Context context);
    void doLoginWithTwitter();
    void doLoginWithGoogle();
    void doLoginWithEmail(String email, String password);
}
