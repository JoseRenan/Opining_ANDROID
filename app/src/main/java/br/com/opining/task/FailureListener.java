package br.com.opining.task;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import br.com.opining.R;

/**
 * Created by juan on 16/09/16.
 */
public class FailureListener implements OnFailureListener {

    private Activity activity;

    public FailureListener(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        if (e instanceof FirebaseNetworkException){
            Snackbar.make(activity.findViewById(android.R.id.content), R.string.error_time_out, Snackbar.LENGTH_LONG).show();
        }else if(e instanceof FirebaseAuthInvalidCredentialsException){
            Snackbar.make(activity.findViewById(android.R.id.content), R.string.error_credential, Snackbar.LENGTH_LONG).show();
        }else if(e instanceof FirebaseAuthInvalidUserException){
            Snackbar.make(activity.findViewById(android.R.id.content), R.string.error_inexist, Snackbar.LENGTH_LONG).show();
        }
    }
}
