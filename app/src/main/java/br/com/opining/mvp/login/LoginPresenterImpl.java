package br.com.opining.mvp.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.facebook.FacebookException;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import br.com.opining.R;
import br.com.opining.helpers.ValidatorHelper;

public class LoginPresenterImpl implements LoginPresenter, Authenticator.OnLoginListener {

    private LoginView mView;
    private Authenticator mModel;

    public LoginPresenterImpl(LoginView mView) {
        this.mView = mView;
        this.mModel = new LoginModel(this);
    }

    @Override
    public void doLoginWithEmail(String email, String password) {
        if (validateCredentials(email, password)) {
            mModel.doLoginWithEmail(email, password);
        }
    }

    @Override
    public void doLoginWithFacebook() {
        mModel.doLoginWithFacebook(mView.getContext());
    }

    @Override
    public void doLoginWithTwitter() {
        mModel.doLoginWithTwitter();
    }

    @Override
    public void doLoginWithGoogle() {
        mModel.doLoginWithGoogle();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mModel.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        this.mView = null;
    }

    @Override
    public void onLoginError(int msg) {
        mView.notifyLoginError(mView.get().getString(msg));
    }

    @Override
    public void onLoginError(@NonNull Exception e) {
        if (e instanceof FirebaseNetworkException){
            mView.notifyLoginError(mView.get().getString(R.string.error_time_out));
        }else if(e instanceof FirebaseAuthInvalidCredentialsException){
            mView.notifyLoginError(mView.get().getString(R.string.error_credential));
        }else if(e instanceof FirebaseAuthInvalidUserException){
            mView.notifyLoginError(mView.get().getString(R.string.error_inexist));
        } else if (e instanceof FacebookException) {
            mView.notifyLoginError(e.getLocalizedMessage());
        }  else if(e instanceof FirebaseAuthUserCollisionException){
            mView.notifyLoginError(mView.get().getString(R.string.error_duplicate));
        } else {
            mView.notifyLoginError(mView.get().getString(R.string.error));
        }
    }

    @Override
    public void onLoginSuccessful() {
        mView.notifySuccessfulLogin();
    }

    @Override
    public AppCompatActivity getActivity() {
        return mView.get();
    }

    private boolean validateCredentials(String email, String password) {

        boolean isValid = true;

        if (email.isEmpty()) {
            isValid = false;
            mView.showInvalidEmailError(mView.get().getString(R.string.error_email_empty));
        } else if (!ValidatorHelper.validateEmail(email)) {
            isValid = false;
            mView.showInvalidEmailError(mView.get().getString(R.string.error_email));
        }

        if (password.isEmpty()) {
            isValid = false;
            mView.showInvalidPasswordError(mView.get().getString(R.string.error_password_empty));
        }

        return isValid;
    }
}
