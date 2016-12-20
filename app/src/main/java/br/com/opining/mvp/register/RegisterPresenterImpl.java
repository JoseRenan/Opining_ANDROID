package br.com.opining.mvp.register;

import android.support.annotation.NonNull;

import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import br.com.opining.R;
import br.com.opining.helpers.ValidatorHelper;
import br.com.opining.domain.User;

public class RegisterPresenterImpl implements RegisterPresenter, UserRegister.OnRegisterListener {

    private RegisterView mView;
    private UserRegister mModel;

    public RegisterPresenterImpl(RegisterView mView) {
        this.mView = mView;
        this.mModel = new RegisterModel(this);
    }

    @Override
    public void doRegister(String name, String email, String password, String repeatPassword) {
        if (validateInformations(name, email, password, repeatPassword)) {
            User user = new User(name, email, password, null);
            mModel.doRegister(user);
        }
    }

    @Override
    public void onDestroy() {
        this.mView = null;
    }

    @Override
    public void onRegisterError(@NonNull Exception e) {
        if (e instanceof FirebaseNetworkException){
            mView.notifyRegisterError(mView.get().getString(R.string.error_time_out));
        } else if(e instanceof FirebaseAuthUserCollisionException){
            mView.notifyRegisterError(mView.get().getString(R.string.error_duplicate));
        } else {
            mView.notifyRegisterError(mView.get().getString(R.string.error));
        }
    }

    @Override
    public void onRegisterSuccessful() {
        mView.notifyRegisterSuccessful();
    }

    private boolean validateInformations(String name, String email, String password, String repeatPassword) {

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
        } else if (!ValidatorHelper.validatePassword(password)) {
            isValid = false;
            mView.showInvalidPasswordError(mView.get().getString(R.string.error_password));
        }

        if (!ValidatorHelper.validateName(name)) {
            isValid = false;
            mView.showInvalidNameError(mView.get().getString(R.string.error_name));
        }

        if (!password.equals(repeatPassword)) {
            isValid = false;
            mView.showInvalidPasswordMatchError();
        }

        return isValid;
    }
}
