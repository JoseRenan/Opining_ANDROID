package br.com.opining.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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
    private EditText edt_name;
    private EditText edt_email;
    private EditText edt_password;
    private EditText edt_confirm;
    private Button btn;

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

        btn = (Button) findViewById(R.id.btn_register);
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_email = (EditText) findViewById(R.id.edt_email);

        initializePassword();

    }

    private void initializePassword(){
        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_confirm = (EditText) findViewById(R.id.edt_repeat_password);

        edt_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edt_confirm.getText().toString() != edt_password.getText().toString()){
                    edt_confirm.setError(getString(R.string.error_password));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
        enableForm(false);

        String email = edt_email.getText().toString();
        String password = edt_password.getText().toString();
        final String nome = edt_name.getText().toString();

        if(!verifyEmail(email)){
            edt_email.setError(getString(R.string.error_email));
            enableForm(true);
        }else if(!verifyPassword(password)){
            edt_password.setError(getString(R.string.error_password));
            enableForm(true);
        }else if(password.equals(edt_confirm.getText().toString())){
            edt_confirm.setText(R.string.error_password);
            enableForm(true);
        }else if(!verifyName(nome)){
            edt_name.setError(getString(R.string.error_name));
            enableForm(true);
        }else {
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
        return email.contains("@");
    }

    private boolean verifyPassword(String password){
        return password.length() > 5;
    }

    private void enableForm(boolean value){
        edt_email.setEnabled(value);
        edt_password.setEnabled(value);
        edt_name.setEnabled(value);
        edt_confirm.setEnabled(value);
        btn.setEnabled(value);
    }

    @Override
    public void onComplete(@NonNull Task task) {
        enableForm(true);
    }
}
