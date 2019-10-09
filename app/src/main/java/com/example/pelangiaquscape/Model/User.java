package com.example.pelangiaquscape.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String id;
    private String username;
    private String telepon;
    private String email;
    private String password;

    /**
     * 0 = SuperAdmin
     * 1 = Admin
     */
    private String kodeLogin;
    private String fotoProfile;
    private String bio;


    public User() {
    }

    public User(String id, String username, String telepon, String email, String password,  String kodeLogin, String fotoProfile, String bio) {
        this.id = id;
        this.username = username;
        this.telepon = telepon;
        this.email = email;
        this.password = password;

        this.kodeLogin = kodeLogin;
        this.fotoProfile = fotoProfile;
        this.bio = bio;
    }

    protected User(Parcel in) {
        id = in.readString();
        username = in.readString();
        telepon = in.readString();
        email = in.readString();
        password = in.readString();

        kodeLogin = in.readString();
        fotoProfile = in.readString();
        bio = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getKodeLogin() {
        return kodeLogin;
    }

    public void setKodeLogin(String kodeLogin) {
        this.kodeLogin = kodeLogin;
    }

    public String getFotoProfile() {
        return fotoProfile;
    }

    public void setFotoProfile(String fotoProfile) {
        this.fotoProfile = fotoProfile;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(username);
        dest.writeString(telepon);
        dest.writeString(email);
        dest.writeString(password);

        dest.writeString(kodeLogin);
        dest.writeString(fotoProfile);
        dest.writeString(bio);
    }
}
