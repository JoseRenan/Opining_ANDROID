package br.com.opining.mvp.home.create.room;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.opining.helpers.FirebaseUserHelper;
import br.com.opining.domain.Room;

public class CreateRoomPresenterImpl implements CreateRoomPresenter {

    private CreateRoomView mView;

    public CreateRoomPresenterImpl(CreateRoomView mView) {
        this.mView = mView;
    }

    @Override
    public void createRoom(String post) {
        FirebaseUser user = FirebaseUserHelper.getCurrentFirebaseUser();
        Room room = new Room(user.getUid(), post);
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("posts").push().setValue(room);
        mView.notifyRoomCreated();
    }

    @Override
    public void onDestroy() {
        this.mView = null;
    }
}
