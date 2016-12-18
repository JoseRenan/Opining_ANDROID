package br.com.opining.mvp.login;

/**
 * Created by José Renan on 18/12/2016.
 */

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView mView;

    public LoginPresenterImpl(LoginView mView) {
        this.mView = mView;
    }

    @Override
    public void doLoginWithEmail(String email, String password) {
        //TODO Validate and call model to do login
    }

    @Override
    public void doLoginWithFacebook() {
        //TODO Validate call model to do login
    }

    @Override
    public void doLoginWithTwitter() {
        //TODO Validate call model to do login
    }

    @Override
    public void onDestroy() {
        this.mView = null;
    }
}
