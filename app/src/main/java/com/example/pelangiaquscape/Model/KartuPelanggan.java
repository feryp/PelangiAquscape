package com.example.pelangiaquscape.Model;

public class KartuPelanggan {

    private String namaPelanggan;
    private String noHpPelanggan;
    private String alamatPelanggan;
    private String namaToko;
    private String noHpToko;
    private String emailToko;
    private String alamatToko;

    public KartuPelanggan(){}

    public KartuPelanggan(String namaPelanggan, String noHpPelanggan, String alamatPelanggan,
                          String namaToko, String noHpToko, String emailToko, String alamatToko) {
        this.namaPelanggan = namaPelanggan;
        this.noHpPelanggan = noHpPelanggan;
        this.alamatPelanggan = alamatPelanggan;
        this.namaToko = namaToko;
        this.noHpToko = noHpToko;
        this.emailToko = emailToko;
        this.alamatToko = alamatToko;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getNoHpPelanggan() {
        return noHpPelanggan;
    }

    public void setNoHpPelanggan(String noHpPelanggan) {
        this.noHpPelanggan = noHpPelanggan;
    }

    public String getAlamatPelanggan() {
        return alamatPelanggan;
    }

    public void setAlamatPelanggan(String alamatPelanggan) {
        this.alamatPelanggan = alamatPelanggan;
    }

    public String getNamaToko() {
        return namaToko;
    }

    public void setNamaToko(String namaToko) {
        this.namaToko = namaToko;
    }

    public String getNoHpToko() {
        return noHpToko;
    }

    public void setNoHpToko(String noHpToko) {
        this.noHpToko = noHpToko;
    }

    public String getEmailToko() {
        return emailToko;
    }

    public void setEmailToko(String emailToko) {
        this.emailToko = emailToko;
    }

    public String getAlamatToko() {
        return alamatToko;
    }

    public void setAlamatToko(String alamatToko) {
        this.alamatToko = alamatToko;
    }
}
