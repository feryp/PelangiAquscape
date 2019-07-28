package com.example.pelangiaquscape.Model;

public class User {

    private String id;
    private String username;
    private String telepon;
    private String email;
    private String password;
    private String ulangiPassword;
    private String kodeLogin;
    private String nama;
    private String statusJabatan;
    private String bio;
    private String imageUrl;


    public User(String id, String username, String telepon, String email, String password, String ulangiPassword, String kodeLogin, String nama, String statusJabatan, String bio, String imageUrl) {
        this.id = id;
        this.username = username;
        this.telepon = telepon;
        this.email = email;
        this.password = password;
        this.ulangiPassword = ulangiPassword;
        this.kodeLogin = kodeLogin;
        this.nama = nama;
        this.statusJabatan = statusJabatan;
        this.bio = bio;
        this.imageUrl = imageUrl;
    }

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

    public String getUlangiPassword() {
        return ulangiPassword;
    }

    public void setUlangiPassword(String ulangiPassword) {
        this.ulangiPassword = ulangiPassword;
    }

    public String getKodeLogin() {
        return kodeLogin;
    }

    public void setKodeLogin(String kodeLogin) {
        this.kodeLogin = kodeLogin;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getStatusJabatan() {
        return statusJabatan;
    }

    public void setStatusJabatan(String statusJabatan) {
        this.statusJabatan = statusJabatan;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
