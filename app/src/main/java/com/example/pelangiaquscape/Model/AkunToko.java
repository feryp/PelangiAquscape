package com.example.pelangiaquscape.Model;

public class AkunToko {

    private String namaToko;
    private String noTelepon;
    private String emailToko;
    private String alamat;
    private String kodePos;

    public AkunToko(){}

    public AkunToko(String namaToko, String noTelepon, String emailToko, String alamat, String kodePos) {
        this.namaToko = namaToko;
        this.noTelepon = noTelepon;
        this.emailToko = emailToko;
        this.alamat = alamat;
        this.kodePos = kodePos;
    }

    public String getNamaToko() {
        return namaToko;
    }

    public void setNamaToko(String namaToko) {
        this.namaToko = namaToko;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getEmailToko(){return  emailToko;}

    public void setEmailToko(String emailToko){this.emailToko = emailToko;}

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKodePos() {
        return kodePos;
    }

    public void setKodePos(String kodePos) {
        this.kodePos = kodePos;
    }
}