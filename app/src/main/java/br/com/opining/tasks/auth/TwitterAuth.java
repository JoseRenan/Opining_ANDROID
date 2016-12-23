package br.com.opining.tasks.auth;

import android.app.Activity;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

/**
 * Classe que recupera credencial de autenticação do twitter para o firebase.
 */
public class TwitterAuth extends Callback<TwitterSession> {

    private TwitterAuthClient twitterAuthClient;
    private Authenticable authenticable;

    public TwitterAuth(Authenticable authenticable, Activity activity) {
        this.authenticable = authenticable;
        twitterAuthClient = new TwitterAuthClient();
        twitterAuthClient.authorize(activity, this);
    }

    @Override
    public void success(Result<TwitterSession> result) {
        AuthCredential credential = TwitterAuthProvider.getCredential(result.data.getAuthToken().token,
                result.data.getAuthToken().secret);
        authenticable.onSuccessGetCredentials(credential);
    }

    @Override
    public void failure(TwitterException exception) {
        authenticable.onFailedGetCredentials(exception);
    }

    public TwitterAuthClient getTwitterAuthClient() {
        return twitterAuthClient;
    }
}
