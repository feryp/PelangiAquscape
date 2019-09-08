package com.example.pelangiaquscape.Model;

public class Pelanggan extends KartuPelanggan {


    private String namaPelanggan;
    private String noHp;
    private String alamat;
    private String catatan;


    public Pelanggan(String namaPelanggan, String noHp, String alamat, String catatan) {
        this.namaPelanggan = namaPelanggan;
        this.noHp = noHp;
        this.alamat = alamat;
        this.catatan = catatan;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }


    public Pelanggan(){}

}
