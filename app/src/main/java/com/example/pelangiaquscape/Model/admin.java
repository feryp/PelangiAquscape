package com.example.pelangiaquscape.Model;

public class admin {

    private String id;
    private String username;
    private String telepon;
    private String email;
    private String password;
    private String ulangiPassword;

    public String getId() {
        return id;
    }

    public admin(String id, String username, String telepon, String email, String password, String ulangiPassword) {
        this.id = id;
        this.username = username;
        this.telepon = telepon;
        this.email = email;
        this.password = password;
        this.ulangiPassword = ulangiPassword;
    }

    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getTelepon() { return telepon; }

    public void setTelepon(String telepon) { this.telepon = telepon; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getUlangiPassword() { return ulangiPassword; }

    public void setUlangiPassword(String ulangiPassword) { this.ulangiPassword = ulangiPassword; }



    public admin(){

    }


}
