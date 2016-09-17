package br.com.opining.activities;

import android.content.Intent;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.opining.R;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar tbMain;
    private EditText edt_name;
    private EditText edt_email;
    private EditText edt_password;
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
        edt_password = (EditText) findViewById(R.id.edt_password);
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

        }else if(!verifyName(nome)){
            edt_name.setError(getString(R.string.error_name));
            enableForm(true);
        }else {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
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
        btn.setEnabled(value);
    }
}
