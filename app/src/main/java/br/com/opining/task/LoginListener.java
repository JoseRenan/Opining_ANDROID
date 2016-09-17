package br.com.opining.task;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import br.com.opining.activities.HomeActivity;

/**
 * Created by juan on 16/09/16.
 */
public class LoginListener implements OnSuccessListener {

    private Activity activity;

    public LoginListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onSuccess(Object o) {
        Intent intent = new Intent(activity, HomeActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
