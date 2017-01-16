package br.com.opining.mvp.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import br.com.opining.R;
import br.com.opining.helpers.AndroidHelper;
import br.com.opining.mvp.home.HomeActivity;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    private Toolbar tbMain;
    private EditText edtName;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private Button btnRegister;
    private FrameLayout frameLoading;
    private RegisterPresenter mPresenter;
    private boolean isLoading;
    private TextInputLayout layoutName;
    private TextInputLayout layoutEmail;
    private TextInputLayout layoutPassword;
    private TextInputLayout layoutConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tbMain = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tbMain);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        isLoading = false;
        mPresenter = new RegisterPresenterImpl(this);
        frameLoading = (FrameLayout) findViewById(R.id.lt_frame_loading);
        btnRegister = (Button) findViewById(R.id.btn_register);
        edtName = (EditText) findViewById(R.id.edt_name);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        edtConfirmPassword = (EditText) findViewById(R.id.edt_repeat_password);

        layoutName = (TextInputLayout) findViewById(R.id.input_name);
        layoutEmail = (TextInputLayout) findViewById(R.id.input_email);
        layoutPassword = (TextInputLayout) findViewById(R.id.input_password);
        layoutConfirmPassword = (TextInputLayout) findViewById(R.id.input_confirm_password);

        edtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    layoutName.setErrorEnabled(false);
                }
            }
        });
        edtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    layoutEmail.setErrorEnabled(false);
                }
            }
        });
        edtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    layoutPassword.setErrorEnabled(false);
                }
            }
        });
        edtConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    layoutConfirmPassword.setErrorEnabled(false);
                }
            }
        });
    }

    public void doRegister(View v) {
        startLoading(true);
        mPresenter.doRegister(edtName.getText().toString().trim(), edtEmail.getText().toString().trim(),
                edtPassword.getText().toString(), edtConfirmPassword.getText().toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!isLoading)
                    NavUtils.navigateUpFromSameTask(this);
                break;
        }
        return true;
    }

    @Override
    public void notifyRegisterError(String msg) {
        startLoading(false);
        AndroidHelper.showErrorSnackbar(this, msg);
    }

    @Override
    public void notifyRegisterSuccessful() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
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
    public void showInvalidPasswordMatchError() {
        startLoading(false);
        layoutConfirmPassword.setErrorEnabled(true);
        layoutConfirmPassword.setError(getString(R.string.error_confirm_password));
    }

    @Override
    public void showInvalidNameError(String msg) {
        startLoading(false);
        layoutName.setErrorEnabled(true);
        layoutName.setError(msg);
    }

    private void startLoading(boolean value){
        isLoading = value;
        frameLoading.setVisibility(!value? View.GONE : View.VISIBLE);
        edtEmail.setEnabled(!value);
        edtPassword.setEnabled(!value);
        edtName.setEnabled(!value);
        edtConfirmPassword.setEnabled(!value);
        btnRegister.setEnabled(!value);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}