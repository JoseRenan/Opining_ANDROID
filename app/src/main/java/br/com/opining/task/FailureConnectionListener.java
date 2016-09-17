package br.com.opining.task;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.FirebaseNetworkException;

import br.com.opining.R;

/**
 * Created by juan on 16/09/16.
 */
public class FailureConnectionListener implements OnFailureListener {

    private View view;

    public FailureConnectionListener(View view){
        this.view = view;
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        if (e instanceof FirebaseNetworkException){
            Snackbar.make(view, R.string.error_time_out,Snackbar.LENGTH_LONG).show();
        }
    }
}
