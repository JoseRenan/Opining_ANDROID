package br.com.opining.mvp.settings.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.opining.R;
import br.com.opining.helpers.AndroidHelper;

public class EditProfileActivity extends AppCompatActivity implements EditProfileView {

    private Toolbar tbMain;
    private EditText edtName;
    private EditText edtEmail;
    private TextInputLayout tlName;
    private TextInputLayout tlEmail;
    private EditProfilePresenter mPresenter;
    private Button btnUpdateProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        tbMain = (Toolbar) findViewById(R.id.tb_main);
        TextView lbTitleToolbar = (TextView) findViewById(R.id.lb_title);
        lbTitleToolbar.setText(R.string.profile);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtName = (EditText) findViewById(R.id.edt_name);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        tlName = (TextInputLayout) findViewById(R.id.tl_name);
        tlEmail = (TextInputLayout) findViewById(R.id.tl_email);
        btnUpdateProfile = (Button) findViewById(R.id.btn_update);

        tlEmail.setErrorEnabled(true);
        tlName.setErrorEnabled(true);

        mPresenter = new EditProfilePresenterImpl(this);
    }

    public void updateProfile(View view) {
        tlEmail.setError(null);
        tlName.setError(null);
        tlEmail.setErrorEnabled(false);
        tlName.setErrorEnabled(false);
        mPresenter.doUpdate(edtEmail.getText().toString().trim(), edtName.getText().toString().trim());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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
    public void notifySuccessfulEdit() {
        AndroidHelper.showSnackbar(this, "Perfil atualizado com sucesso");
    }

    @Override
    public void notifyErrorEdit(String msg) {
        AndroidHelper.showSnackbar(this, msg);
    }

    @Override
    public void showInvalidNameError(String msg) {
        tlName.setError(msg);
    }

    @Override
    public void showInvalidEmailError(String msg) {
        tlEmail.setError(msg);
    }

    @Override
    public void setActualNameAndEmail(String name, String email) {
        edtEmail.setText(email);
        edtName.setText(name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}