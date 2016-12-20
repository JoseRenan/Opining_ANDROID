package br.com.opining.mvp.login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import java.util.Arrays;

import br.com.opining.R;
import br.com.opining.helpers.FirebaseUserHelper;
import br.com.opining.domain.User;

public class LoginModel implements Authenticator, OnFailureListener, OnSuccessListener {

    private final String TAG = getClass().getName();

    private static final String FACEBOOK_PROVIDER = "facebook";
    private static final String TWITTER_PROVIDER = "twitter";
    private static final String NO_PROVIDER = "no";

    private String provider;
    private FirebaseAuth firebaseAuth;
    private CallbackManager callbackManager;
    private TwitterAuthClient twitterAuthClient;
    private Authenticator.OnLoginListener loginListener;

    public LoginModel (Authenticator.OnLoginListener loginListener) {
        this.loginListener = loginListener;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (FacebookSdk.isFacebookRequestCode(requestCode))
            callbackManager.onActivityResult(requestCode,resultCode,data);
        else{
            twitterAuthClient.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void doLoginWithFacebook(Context context) {
        this.provider = FACEBOOK_PROVIDER;
        FacebookSdk.sdkInitialize(context);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i(TAG, "usuário autenticado com facebook");
                acessLoginData("facebook", loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                //TODO Create exception to cancelled login
                loginListener.onLoginError(new Exception());
            }

            @Override
            public void onError(FacebookException error) {
                Log.w(TAG, error.getMessage());
                loginListener.onLoginError(error);
            }
        });

        LoginManager.getInstance().logInWithReadPermissions(loginListener.getActivity(), Arrays.asList("public_profile", "email"));
    }

    @Override
    public void doLoginWithTwitter() {
        this.provider = TWITTER_PROVIDER;
        twitterAuthClient = new TwitterAuthClient();
        twitterAuthClient.authorize(loginListener.getActivity(), new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                acessLoginData("twitter",
                        result.data.getAuthToken().token,
                        result.data.getAuthToken().secret,
                        String.valueOf(result.data.getUserId()));
            }

            @Override
            public void failure(TwitterException exception) {
                loginListener.onLoginError(R.string.error_twitter);
            }
        });
    }

    @Override
    public void doLoginWithEmail(String email, String password) {
        this.provider = NO_PROVIDER;
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(this)
                .addOnFailureListener(this);
    }

    private void acessLoginData(String provider, String... tokens){
        if (tokens != null && tokens.length >= 1) {

            AuthCredential credential = null;

            if (provider.equals("twitter")){
                credential = TwitterAuthProvider.getCredential(tokens[0], tokens[1]);
            }
            else if(provider.equals("facebook")){
                credential = FacebookAuthProvider.getCredential(tokens[0]);
            }

            Log.i(TAG, "credenciando usuário com firebase");
            firebaseAuth.signInWithCredential(credential)
                    .addOnSuccessListener(this)
                    .addOnFailureListener(this);
        } else {
            firebaseAuth.signOut();
        }
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        loginListener.onLoginError(e);
    }

    @Override
    public void onSuccess(Object o) {
        if (!this.provider.equals(NO_PROVIDER)) {
            updateProfileAndAddToDatabase();
        }
        loginListener.onLoginSuccessful();
    }

    private void updateProfileAndAddToDatabase() {

        final FirebaseUser fUser = FirebaseUserHelper.getCurrentFirebaseUser();
        final User user = new User(fUser.getDisplayName(), fUser.getEmail(), fUser.getPhotoUrl().toString());

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(user.getName()).build();

        FirebaseUserHelper.updateUser(fUser, profileUpdates, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
                    dbRef.child("users").child(fUser.getUid()).setValue(user);
                    Log.d(TAG, "User profile updated.");
                }
            }
        });
    }
}
