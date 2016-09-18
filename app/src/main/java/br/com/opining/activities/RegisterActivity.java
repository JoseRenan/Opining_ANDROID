package br.com.opining.activities;

import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import br.com.opining.R;
import br.com.opining.task.FailureListener;
import br.com.opining.task.RegisterListener;

public class RegisterActivity extends AppCompatActivity implements OnCompleteListener{

    private Toolbar tbMain;
    private EditText edtName;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private Button btnRegister;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tbMain = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tbMain);

        firebaseAuth = FirebaseAuth.getInstance();

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnRegister = (Button) findViewById(R.id.btn_register);
        edtName = (EditText) findViewById(R.id.edt_name);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        edtConfirmPassword = (EditText) findViewById(R.id.edt_repeat_password);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    public void doRegister(View v){

        Boolean enabled = false;
        enableForm(enabled);

        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String confirmPassword = edtConfirmPassword.getText().toString();
        final String nome = edtName.getText().toString();

        if (!verifyEmail(email)) {
            edtEmail.setError(getString(R.string.error_email));
            enabled = true;
        }

        if (!verifyPassword(password)) {
            edtPassword.setError(getString(R.string.error_password));
            enabled = true;
        }

        if (!password.equals(confirmPassword)) {
            edtConfirmPassword.setError(getString(R.string.error_confirm_password));
            enabled = true;
        }

        if (!verifyName(nome)) {
            edtName.setError(getString(R.string.error_name));
            enabled = true;
        }

        enableForm(enabled);

        if (!enabled) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new RegisterListener(this))
                    .addOnFailureListener(new FailureListener(this))
                    .addOnCompleteListener(this);
        }
    }

    private boolean verifyName(String name){
        return name.length() > 0;
    }

    private boolean verifyEmail(String email){
        return email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    }

    private boolean verifyPassword(String password){
        return password.length() > 5;
    }

    private void enableForm(boolean value){
        edtEmail.setEnabled(value);
        edtPassword.setEnabled(value);
        edtName.setEnabled(value);
        edtConfirmPassword.setEnabled(value);
        btnRegister.setEnabled(value);
    }

    @Override
    public void onComplete(@NonNull Task task) {
        enableForm(true);
    }
}
