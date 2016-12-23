package br.com.opining.tasks.auth;

import android.app.Activity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;

import java.util.Arrays;

/**
 * Classe que recupera credencial de autenticação do facebook para o firebase.
 */
public class FacebookAuth implements FacebookCallback<LoginResult> {

    private CallbackManager callbackManager;
    private Authenticable authenticable;
    private LoginManager loginManager;

    public FacebookAuth(Authenticable authenticable, Activity activity) {
        this.callbackManager = CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();
        loginManager.registerCallback(callbackManager, this);
        loginManager.logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"));
        this.authenticable = authenticable;
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        AuthCredential credential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
        authenticable.onSuccessGetCredentials(credential);
    }

    @Override
    public void onCancel() {
        //TODO Criar exception para tratar cancelamento de requisição
    }

    @Override
    public void onError(FacebookException error) {
        authenticable.onFailedGetCredentials(error);
    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }
}
