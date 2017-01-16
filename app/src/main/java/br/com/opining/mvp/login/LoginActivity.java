package br.com.opining.mvp.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import br.com.opining.R;
import br.com.opining.helpers.AndroidHelper;
import br.com.opining.mvp.register.RegisterActivity;
import br.com.opining.mvp.home.HomeActivity;

public class LoginActivity extends AppCompatActivity implements LoginView {

    public LoginPresenter mPresenter;
    private Button btnRedirectRegister;
    private Button btnRedirectEnter;
    private EditText editEmail;
    private EditText editPassword;
    private FrameLayout frameLoading;
    private TextInputLayout layoutEmail;
    private TextInputLayout layoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPresenter = new LoginPresenterImpl(this);

        //Inicialização do layout
        frameLoading = (FrameLayout) findViewById(R.id.lt_frame_loading);
        btnRedirectEnter = (Button) findViewById(R.id.btn_enter);
        btnRedirectRegister = (Button) findViewById(R.id.btn_register_redirect);
        btnRedirectRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });

        editEmail = (EditText) findViewById(R.id.edt_login);
        layoutEmail = (TextInputLayout) findViewById(R.id.input_login);
        editEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    layoutEmail.setErrorEnabled(false);
                }
            }
        });

        editPassword = (EditText) findViewById(R.id.edt_password);
        layoutPassword = (TextInputLayout) findViewById(R.id.input_password);
        editPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    layoutPassword.setErrorEnabled(false);
                }
            }
        });

    }

    public void doLogin(View view) {
        startLoading(true);
        mPresenter.doLoginWithEmail(editEmail.getText().toString(), editPassword.getText().toString());
    }

    public void doLoginWithFacebook(View view){
        startLoading(true);
        mPresenter.doLoginWithFacebook();
    }

    public void doLoginWithTwitter(View view){
        startLoading(true);
        mPresenter.doLoginWithTwitter();
    }

    public void doLoginWithGoogle(View view){
        startLoading(true);
        mPresenter.doLoginWithGoogle();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void notifyLoginError(String msg) {
        startLoading(false);
        AndroidHelper.showErrorSnackbar(LoginActivity.this, msg);
    }

    @Override
    public void showInvalidPasswordError(String msg) {
        startLoading(false);
        layoutPassword.setErrorEnabled(true);
        layoutPassword.setError(msg);
    }

    @Override
    public void showInvalidEmailError(String msg) {
        startLoading(false);
        layoutEmail.setErrorEnabled(true);
        layoutEmail.setError(msg);
    }

    @Override
    public void notifySuccessfulLogin() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }

    @Override
    public AppCompatActivity get() {
        return this;
    }

    private void startLoading(boolean value) {
        frameLoading.setVisibility(!value? View.GONE : View.VISIBLE);
        editPassword.setEnabled(!value);
        editEmail.setEnabled(!value);
        btnRedirectEnter.setEnabled(!value);
        btnRedirectRegister.setEnabled(!value);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }
}
