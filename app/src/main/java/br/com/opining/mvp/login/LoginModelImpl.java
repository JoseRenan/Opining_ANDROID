package br.com.opining.mvp.login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.opining.helpers.FirebaseUserHelper;
import br.com.opining.domain.User;
import br.com.opining.tasks.auth.Authenticable;
import br.com.opining.tasks.auth.FacebookAuth;
import br.com.opining.tasks.auth.GoogleAuth;
import br.com.opining.tasks.auth.TwitterAuth;

public class LoginModelImpl implements LoginModel, OnFailureListener, OnSuccessListener, Authenticable {

    private static final int RC_SIGN_IN = 9001;
    private final String TAG = getClass().getName();

    private static final String FACEBOOK_PROVIDER = "facebook";
    private static final String TWITTER_PROVIDER = "twitter";
    private static final String GOOGLE_PROVIDER = "google";
    private static final String NO_PROVIDER = "no";

    private FacebookAuth facebookAuth;
    private TwitterAuth twitterAuth;
    private GoogleAuth googleAuth;

    private String provider;
    private FirebaseAuth firebaseAuth;
    private LoginModel.OnLoginListener loginListener;

    public LoginModelImpl(LoginModel.OnLoginListener loginListener) {
        this.loginListener = loginListener;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (FacebookSdk.isFacebookRequestCode(requestCode))
            facebookAuth.getCallbackManager().onActivityResult(requestCode,resultCode,data);
        else if (requestCode == RC_SIGN_IN) {
            googleAuth.onActivityResult(requestCode, resultCode, data);
        } else {
            twitterAuth.getTwitterAuthClient().onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void doLoginWithFacebook(Context context) {
        this.provider = FACEBOOK_PROVIDER;
        facebookAuth = new FacebookAuth(this, loginListener.getActivity());
    }

    @Override
    public void doLoginWithTwitter() {
        this.provider = TWITTER_PROVIDER;
        twitterAuth = new TwitterAuth(this, loginListener.getActivity());
    }

    @Override
    public void doLoginWithGoogle() {
        this.provider = GOOGLE_PROVIDER;
        googleAuth = new GoogleAuth(this, loginListener.getActivity(), RC_SIGN_IN);
    }

    @Override
    public void doLoginWithEmail(String email, String password) {
        this.provider = NO_PROVIDER;
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(this)
                .addOnFailureListener(this);
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        loginListener.onLoginError(e);
    }

    @Override
    public void onSuccess(Object o) {
        if (!this.provider.equals(NO_PROVIDER))
            updateProfileAndAddToDatabase();
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

    @Override
    public void onSuccessGetCredentials(AuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(this)
                .addOnFailureListener(this);
    }

    @Override
    public void onFailedGetCredentials(Exception ex) {
        loginListener.onLoginError(ex);
    }
}
