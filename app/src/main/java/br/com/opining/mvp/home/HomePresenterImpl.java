package br.com.opining.mvp.home;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseException;

import java.util.ArrayList;
import java.util.List;

import br.com.opining.domain.Room;

public class HomePresenterImpl implements HomePresenter, FirebaseAuth.AuthStateListener, DebateRetriever.DebateRetrieveListener {

    private final String TAG = getClass().getName();
    private HomeView mView;
    private DebateRetriever mModel;
    private FirebaseAuth firebase;
    private ArrayList<Room> rooms;

    public HomePresenterImpl(HomeView mView) {
        this.mView = mView;
        rooms = new ArrayList<>();
        this.mModel = new HomeModel(this);
        firebase = FirebaseAuth.getInstance();
    }

    @Override
    public void doLogout() {
        firebase.signOut();
    }

    @Override
    public List<Room> getRooms() {
        return this.rooms;
    }

    @Override
    public void startListening() {
        firebase.addAuthStateListener(this);
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
    public void onDebateReceived(Room room) {
        rooms.add(0, room);
        mView.updateDebatesDataset();
    }

    @Override
    public void onDebateUpdated(String roomId, Room room) {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getRoomId().equals(roomId))
                rooms.get(i).setContent(room.getContent());
        }
        mView.updateDebatesDataset();
    }

    @Override
    public void onDebateRemoved(String roomId) {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getRoomId().equals(roomId))
                rooms.remove(i);
        }
        mView.updateDebatesDataset();
    }

    @Override
    public void onCancelled(Exception ex) {
        if (ex instanceof DatabaseException) {
            Log.e(TAG, ex.getLocalizedMessage());
        }
    }
}
