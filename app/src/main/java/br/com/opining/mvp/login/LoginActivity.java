package br.com.opining.mvp.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import br.com.opining.R;
import br.com.opining.helpers.AndroidHelper;
import br.com.opining.view.activities.HomeActivity;
import br.com.opining.view.activities.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements LoginView {

    public LoginPresenter mPresenter;
    private Button btnRedirectRegister;
    private Button btnRedirectEnter;
    private EditText editEmail;
    private EditText editPassword;
    private FrameLayout frameLoading;

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
        editPassword = (EditText) findViewById(R.id.edt_password);
    }

    public void doLogin(View view) {
        frameLoading.setVisibility(View.VISIBLE);
        mPresenter.doLoginWithEmail(editEmail.getText().toString(), editPassword.getText().toString());
    }

    public void doLoginWithFacebook(View view){
        mPresenter.doLoginWithFacebook();
    }

    public void doLoginWithTwitter(View view){
        mPresenter.doLoginWithFacebook();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void showLoginError(String msg) {
        frameLoading.setVisibility(View.GONE);
        AndroidHelper.showSnackbar(LoginActivity.this, msg);
    }

    @Override
    public void showInvalidPasswordError(String msg) {
        frameLoading.setVisibility(View.GONE);
        editPassword.setError(msg);
    }

    @Override
    public void showInvalidEmailError(String msg) {
        frameLoading.setVisibility(View.GONE);
        editEmail.setError(msg);
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
}
