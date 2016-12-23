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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
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

    private static final int RC_SIGN_IN = 9001;
    private final String TAG = getClass().getName();

    private static final String FACEBOOK_PROVIDER = "facebook";
    private static final String TWITTER_PROVIDER = "twitter";
    private static final String GOOGLE_PROVIDER = "google";
    private static final String NO_PROVIDER = "no";

    private String provider;
    private FirebaseAuth firebaseAuth;
    private CallbackManager callbackManager;
    private TwitterAuthClient twitterAuthClient;
    private Authenticator.OnLoginListener loginListener;
    private GoogleApiClient mGoogleApiClient;

    public LoginModel (final Authenticator.OnLoginListener loginListener) {
        this.loginListener = loginListener;
        firebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(loginListener.getActivity().getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(loginListener.getActivity())
                .enableAutoManage(loginListener.getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        loginListener.onLoginError(new Exception());
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (FacebookSdk.isFacebookRequestCode(requestCode))
            callbackManager.onActivityResult(requestCode,resultCode,data);
        else if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                acessLoginData(GOOGLE_PROVIDER, account.getIdToken());
            } else {
                //TODO
            }
        } else {
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
                acessLoginData(FACEBOOK_PROVIDER, loginResult.getAccessToken().getToken());
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
                acessLoginData(TWITTER_PROVIDER,
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
    public void doLoginWithGoogle() {
        this.provider = GOOGLE_PROVIDER;
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        loginListener.getActivity().startActivityForResult(signInIntent, RC_SIGN_IN);
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

            if (provider.equals(TWITTER_PROVIDER)){
                credential = TwitterAuthProvider.getCredential(tokens[0], tokens[1]);
            } else if(provider.equals(FACEBOOK_PROVIDER)){
                credential = FacebookAuthProvider.getCredential(tokens[0]);
            } else if (provider.equals(GOOGLE_PROVIDER)) {
                credential = GoogleAuthProvider.getCredential(tokens[0], null);
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
