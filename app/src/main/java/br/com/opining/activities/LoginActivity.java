package br.com.opining.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.opining.R;
import br.com.opining.helpers.AndroidHelper;

public class LoginActivity extends Activity {

    private FirebaseAuth firebaseAuth;
    private Button btnRedirectRegister;
    private Button btnRedirectEnter;
    private EditText editEmail;
    private EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
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

    public void doLogin(View view){
        enableForm(false);
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        if(!verifyEmail(email)){
            editEmail.setError(getString(R.string.error_email));
            enableForm(true);

        }else if(!verifyPassword(password) ){
            editPassword.setText(getString(R.string.error_password));
            enableForm(true);

        }else {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        AndroidHelper.showSnackbar(LoginActivity.this, R.string.error);
                    }
                }
            });
        }
    }

    private boolean verifyEmail(String email){
        return email.contains("@");
    }

    private boolean verifyPassword(String password){
        return password.length() > 5;
    }

    private void enableForm(boolean value){
        editPassword.setEnabled(value);
        editEmail.setEnabled(value);
        btnRedirectEnter.setEnabled(value);
        btnRedirectRegister.setEnabled(value);
    }
}
