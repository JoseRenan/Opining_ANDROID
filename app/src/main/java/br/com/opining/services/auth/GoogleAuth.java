package br.com.opining.services.auth;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.GoogleAuthProvider;

import br.com.opining.R;

/**
 * Classe que recupera credencial de autenticação do twitter para o firebase.
 */
public class GoogleAuth implements GoogleApiClient.OnConnectionFailedListener {

    private Authenticable authenticable;
    private GoogleApiClient mGoogleApiClient;

    public GoogleAuth(Authenticable authenticable, AppCompatActivity activity, int requestCode) {
        this.authenticable = authenticable;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .enableAutoManage(activity, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        activity.startActivityForResult(signInIntent, requestCode);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        authenticable.onFailedGetCredentials(new Exception());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            authenticable.onSuccessGetCredentials(GoogleAuthProvider.getCredential(account.getIdToken(), null));
        } else {
            //TODO Create exception to throw here
            authenticable.onFailedGetCredentials(new Exception());
        }
    }
}
