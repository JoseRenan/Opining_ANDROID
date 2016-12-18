package br.com.opining.mvp.register;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.opining.helpers.FirebaseUserHelper;
import br.com.opining.model.User;

public class RegisterModel implements UserRegister, OnSuccessListener, OnFailureListener {

    private final String TAG = getClass().getName();

    private UserRegister.OnRegisterListener resgitesrListener;
    private User user;

    public RegisterModel(OnRegisterListener resgitesrListener) {
        this.resgitesrListener = resgitesrListener;
    }

    @Override
    public void doRegister(User user) {
        this.user = user;
        Log.i(TAG, user.getEmail());
        Log.i(TAG, user.getPassword());
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnSuccessListener(this)
                .addOnFailureListener(this);
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        resgitesrListener.onRegisterError(e);
    }

    @Override
    public void onSuccess(Object o) {
        updateProfileAndAddToDatabase();
        resgitesrListener.onRegisterSuccessful();
    }

    private void updateProfileAndAddToDatabase() {

        final FirebaseUser fUser = FirebaseUserHelper.getCurrentFirebaseUser();
        this.user.setPassword(null);

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(user.getName()).build();

        FirebaseUserHelper.updateUser(fUser, profileUpdates, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
                    dbRef.child("users").child(fUser.getUid()).setValue(user);
                    Log.d(TAG, "User profile updated.");
                }
            }
        });
    }
}
