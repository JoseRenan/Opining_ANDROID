package br.com.opining.mvp.splash;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;

import br.com.opining.helpers.FirebaseUserHelper;

public class SplashPresenterImpl implements SplashPresenter, OnFailureListener, OnSuccessListener {

    private SplashView mView;

    public SplashPresenterImpl (SplashView mView) {
        this.mView = mView;
    }

    @Override
    public void checkIfIsLoggedIn() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                FirebaseUser user = FirebaseUserHelper.getCurrentFirebaseUser();
                if(user != null){
                    FirebaseUserHelper.reloadFirebaseUser(user, SplashPresenterImpl.this, SplashPresenterImpl.this);
                } else {
                    mView.goToLogin();
                }
            }
        }, 2000);
    }

    @Override
    public void onDestroy() {
        this.mView = null;
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        this.mView.goToLogin();
    }

    @Override
    public void onSuccess(Object o) {
        mView.goToHome();
    }
}
