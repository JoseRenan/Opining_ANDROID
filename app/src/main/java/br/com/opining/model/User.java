package br.com.opining.model;

public class User {

    private String name;
    private String email;
    private String password;
    private String photoUri;

    public User (){}

    public User(String name, String email, String password, String photoUri) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String photoUri) {
        this.name = name;
        this.email = email;
        this.photoUri = photoUri;
    }

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

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
