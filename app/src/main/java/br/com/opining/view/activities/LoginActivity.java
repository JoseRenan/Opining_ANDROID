package br.com.opining.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import br.com.opining.R;
import br.com.opining.helpers.AndroidHelper;
import br.com.opining.task.FailureListener;
import br.com.opining.task.LoginListener;
import com.google.android.gms.tasks.OnCompleteListener;

import java.util.Arrays;

public class LoginActivity extends Activity implements OnFailureListener{

    private FirebaseAuth firebaseAuth;
    private Button btnRedirectRegister;
    private Button btnRedirectEnter;
    private EditText editEmail;
    private EditText editPassword;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AuthCredential credential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
                firebaseAuth.signInWithCredential(credential)
                        .addOnSuccessListener(new LoginListener(LoginActivity.this))
                        .addOnFailureListener(new FailureListener(LoginActivity.this));
            }

            @Override
            public void onCancel() {}

            @Override
            public void onError(FacebookException error) {
                AndroidHelper.showSnackbar(LoginActivity.this, error.getMessage());
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    public void doLogin(View view){
        enableForm(false);
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        if(!verifyEmail(email)){
            editEmail.setError(getString(R.string.error_email));
            enableForm(true);

        }else if(!verifyPassword(password) ){
            editPassword.setError(getString(R.string.error_password));
            enableForm(true);

        }else {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new LoginListener(this))
                    .addOnFailureListener(new FailureListener(this))
                    .addOnFailureListener(this);
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

    public void doLoginWithFacebook(View view){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        enableForm(true);
    }
}
