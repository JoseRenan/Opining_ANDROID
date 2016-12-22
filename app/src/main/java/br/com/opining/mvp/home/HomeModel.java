package br.com.opining.mvp.home;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.opining.domain.Room;
import br.com.opining.domain.User;

public class HomeModel implements DebateRetriever, ChildEventListener {

    private final String TAG = getClass().getName();
    private DebateRetrieveListener debateListener;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    public HomeModel(DebateRetrieveListener debateListener) {
        this.debateListener = debateListener;
        mDatabase = FirebaseDatabase.getInstance();
        this.mReference = this.mDatabase.getReference("posts");
        mReference.addChildEventListener(this);
    }

    @Override
    public void stopListening() {
        mReference.removeEventListener(this);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Room room = dataSnapshot.getValue(Room.class);
        room.setRoomId(dataSnapshot.getKey());
        getUser(room);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        debateListener.onDebateUpdated(dataSnapshot.getKey(), dataSnapshot.getValue(Room.class));
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        debateListener.onDebateRemoved(dataSnapshot.getKey());
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        Log.i(TAG, dataSnapshot.getKey() + " Foi movido no firebase");
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        debateListener.onCancelled(databaseError.toException());
    }

    private void getUser(final Room room) {
        DatabaseReference mUserReference = this.mDatabase.getReference("users").child(room.getAuthorId());
        ValueEventListener userListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Room newRoom = new Room(room.getRoomId(), room.getAuthorId(),
                        room.getContent(), room.getTimestamp());
                User user = dataSnapshot.getValue(User.class);
                newRoom.setAuthorId(user.getName());
                debateListener.onDebateReceived(newRoom);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                debateListener.onCancelled(databaseError.toException());
            }
        };
        mUserReference.addListenerForSingleValueEvent(userListener);
    }
}
