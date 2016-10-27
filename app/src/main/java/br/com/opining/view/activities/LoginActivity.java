package br.com.opining.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.hardware.camera2.params.Face;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

import br.com.opining.R;
import br.com.opining.helpers.AndroidHelper;
import br.com.opining.task.FailureListener;
import br.com.opining.task.LoginListener;

import java.util.Arrays;

public class LoginActivity extends Activity implements OnFailureListener{

    private FirebaseAuth firebaseAuth;
    private Button btnRedirectRegister;
    private Button btnRedirectEnter;
    private EditText editEmail;
    private EditText editPassword;
    private CallbackManager callbackManager;
    private TwitterAuthClient twitterAuthClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inicialização do Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        //Inicialização do layout
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

        if (FacebookSdk.isFacebookRequestCode(requestCode))
           callbackManager.onActivityResult(requestCode,resultCode,data);
        else{
            twitterAuthClient.onActivityResult(requestCode, resultCode, data);
        }
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
        return email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
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
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i(this.toString(), "usuário autenticado com facebook");

                acessLoginData("facebook", loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {}

            @Override
            public void onError(FacebookException error) {
                Log.w(this.toString(), error.getMessage());
                AndroidHelper.showSnackbar(LoginActivity.this, error.getMessage());
            }
        });
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
    }

    //Autoriza o usuário com o twitter
    public void doLoginWithTwitter(View view){
        twitterAuthClient = new TwitterAuthClient();
        twitterAuthClient.authorize(this, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                acessLoginData("twitter",
                        result.data.getAuthToken().token,
                        result.data.getAuthToken().secret,
                        String.valueOf(result.data.getUserId()));
            }

            @Override
            public void failure(TwitterException exception) {
                AndroidHelper.showSnackbar(LoginActivity.this, R.string.error_twitter);
            }
        });
    }

    //Recebe o token e autoriza no firebase de acordo com o provedor recebido
    public void acessLoginData(String provider, String... tokens){
        if (tokens != null
                && tokens.length >= 1){

            AuthCredential credential = null;
            if (provider.equals("twitter")){
                credential = TwitterAuthProvider.getCredential(tokens[0], tokens[1]);
            }
            else if(provider.equals("facebook")){
                credential = FacebookAuthProvider.getCredential(tokens[0]);
            }

            Log.i(this.toString(), "credenciando usuário com firebase");
            firebaseAuth.signInWithCredential(credential)
                    .addOnSuccessListener(new LoginListener(LoginActivity.this))
                    .addOnFailureListener(new FailureListener(LoginActivity.this));
        }else{
            firebaseAuth.signOut();
        }
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        enableForm(true);
    }

}
