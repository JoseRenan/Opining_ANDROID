package br.com.opining.mvp.settings.profile;

import android.net.Uri;
import android.support.annotation.NonNull;

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
        FirebaseUser fUser = FirebaseUserHelper.getCurrentFirebaseUser();
        Uri uri = fUser.getPhotoUrl();
        final User user = new User(newName, fUser.getEmail(), uri == null? null : uri.toString());

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(newName)
                .build();

        FirebaseUserHelper.updateUser(fUser, profileUpdates, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
                    dbRef.child("users").child(FirebaseUserHelper.getCurrentFirebaseUser().getUid()).setValue(user);
                    editorListener.onUpdadeSuccessful();
                }
            }
        });
    }

    @Override
    public void loadActualNameAndEmail() {
        FirebaseUser user = FirebaseUserHelper.getCurrentFirebaseUser();
        editorListener.onLoadActualNameAndEmail(user.getDisplayName(), user.getEmail());
    }
}
