package br.com.opining.model;

import android.net.Uri;

public class User {

    private String name;
    private String email;
    private Uri photoUri;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }
}
