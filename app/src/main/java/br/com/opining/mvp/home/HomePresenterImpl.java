package br.com.opining.mvp.home;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import br.com.opining.domain.Room;

public class HomePresenterImpl implements HomePresenter, FirebaseAuth.AuthStateListener, DebateRetriever.DebateRetrieveListener {

    private HomeView mView;
    private DebateRetriever mModel;
    private FirebaseAuth firebase;

    public HomePresenterImpl(HomeView mView) {
        this.mView = mView;
        this.mModel = new HomeModel(this);
        firebase = FirebaseAuth.getInstance();
        firebase.addAuthStateListener(this);
    }

    @Override
    public void doLogout() {
        firebase.signOut();
    }

    @Override
    public void onDestroy() {
        mView = null;
        mModel.stopListening();
        firebase.removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if (firebaseAuth.getCurrentUser() == null) {
            mView.logoutSuccessful();
        }
    }

    @Override
    public void onDebateRetrieved(Room room) {
        mView.addDebatesToList(room);
    }
}
