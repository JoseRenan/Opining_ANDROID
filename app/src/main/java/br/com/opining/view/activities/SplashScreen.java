package br.com.opining.view.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.opining.R;
import br.com.opining.task.RetrieveListener;

public class SplashScreen extends AppCompatActivity{

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onResume(){
        super.onResume();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){
                    user.reload().addOnSuccessListener(new RetrieveListener(SplashScreen.this))
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    doLogin();
                                }
                            });
                }
                else{
                    doLogin();
                }
            }
        }, 2000);
    }

    public void doLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
