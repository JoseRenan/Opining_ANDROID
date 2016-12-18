package br.com.opining.helpers;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseUserHelper {


    public static FirebaseUser getCurrentFirebaseUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static void reloadFirebaseUser(FirebaseUser user,
                                          OnSuccessListener onSuccessListener,
                                          OnFailureListener onFailureListener) {
        if (user == null)
            throw new NullPointerException("FirebaseUser cannot be null");
        user.reload().addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }
}
