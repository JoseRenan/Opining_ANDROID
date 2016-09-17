package br.com.opining.task;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import br.com.opining.activities.LoginActivity;

/**
 * Created by juan on 17/09/16.
 */
public class LogoutListener implements FirebaseAuth.AuthStateListener {

    private Activity activity;

    public LogoutListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
