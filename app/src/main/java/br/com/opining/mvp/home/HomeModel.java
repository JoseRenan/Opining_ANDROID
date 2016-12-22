package br.com.opining.mvp.home;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.opining.domain.Room;
import br.com.opining.domain.User;

public class HomeModel implements DebateRetriever, ChildEventListener {

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
        getUser(dataSnapshot.getValue(Room.class));
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        //TODO Definir o que fazer quando um post for editado
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        //TODO Definir o que fazer quando um post é removido
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
       //TODO Definir o que fazer quando um post muda de ordem
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {}

    private void getUser(final Room room) {
        DatabaseReference mUserReference = this.mDatabase.getReference("users").child(room.getAuthorId());
        ValueEventListener userListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Room newRoom = new Room(room.getAuthorId(), room.getContent(), room.getTimestamp());
                User user = dataSnapshot.getValue(User.class);
                newRoom.setAuthorId(user.getName());
                debateListener.onDebateRetrieved(newRoom);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        mUserReference.addListenerForSingleValueEvent(userListener);
    }
}
