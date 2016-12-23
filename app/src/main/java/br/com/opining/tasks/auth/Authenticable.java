package br.com.opining.tasks.auth;

import com.google.firebase.auth.AuthCredential;

public interface Authenticable {
    void onSuccessGetCredentials(AuthCredential credential);
    void onFailedGetCredentials(Exception ex);
}
