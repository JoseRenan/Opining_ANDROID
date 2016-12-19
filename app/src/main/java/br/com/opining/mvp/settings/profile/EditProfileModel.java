package br.com.opining.mvp.settings.profile;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.opining.helpers.FirebaseUserHelper;
import br.com.opining.model.User;

public class EditProfileModel implements ProfileEditor {

    private final String TAG = getClass().getName();

    private ProfileEditorListener editorListener;

    public EditProfileModel(ProfileEditorListener editorListener) {
        this.editorListener = editorListener;
    }

    @Override
    public void doUpdate(String newEmail, String newName) {
        //TODO update the email
        final FirebaseUser fUser = FirebaseUserHelper.getCurrentFirebaseUser();
        Uri uri = fUser.getPhotoUrl();
        final User user = new User(newName, fUser.getEmail(), uri == null? null : uri.toString());

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(newName)
                .build();

        fUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
                            dbRef.child("users").child(fUser.getUid()).setValue(user);
                            editorListener.onUpdadeSuccessful();
                            Log.d(TAG, "User profile updated.");
                        } else {
                            //TODO change to onFailure and onSuccessful to receive firebase exceptions
                            editorListener.onUpdateError(new Exception());
                        }
                    }
                });
    }
}
