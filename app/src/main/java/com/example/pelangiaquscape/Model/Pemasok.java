package com.example.pelangiaquscape.Model;

public class Pemasok {

    private String jenisPerusahaan;
    private String namaPemasok;
    private String klasifikasiPerusahaan;
    private String kualifikasiPerusahaan;
    private String telepon;
    private String emailPemasok;
    private String noHpPemasok;
    private String alamatPemasok;

    public Pemasok(String jenisPerusahaan, String namaPemasok, String klasifikasiPerusahaan, String kualifikasiPerusahaan, String telepon, String emailPemasok, String noHpPemasok, String alamatPemasok) {
        this.jenisPerusahaan = jenisPerusahaan;
        this.namaPemasok = namaPemasok;
        this.klasifikasiPerusahaan = klasifikasiPerusahaan;
        this.kualifikasiPerusahaan = kualifikasiPerusahaan;
        this.telepon = telepon;
        this.emailPemasok = emailPemasok;
        this.noHpPemasok = noHpPemasok;
        this.alamatPemasok = alamatPemasok;
    }

    public String getJenisPerusahaan() {
        return jenisPerusahaan;
    }

    public void setJenisPerusahaan(String jenisPerusahaan) {
        this.jenisPerusahaan = jenisPerusahaan;
    }

    public String getNamaPemasok() {
        return namaPemasok;
    }

    public void setNamaPemasok(String namaPemasok) {
        this.namaPemasok = namaPemasok;
    }

    public String getKlasifikasiPerusahaan() {
        return klasifikasiPerusahaan;
    }

    public void setKlasifikasiPerusahaan(String klasifikasiPerusahaan) {
        this.klasifikasiPerusahaan = klasifikasiPerusahaan;
    }

    public String getKualifikasiPerusahaan() {
        return kualifikasiPerusahaan;
    }

    public void setKualifikasiPerusahaan(String kualifikasiPerusahaan) {
        this.kualifikasiPerusahaan = kualifikasiPerusahaan;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getEmailPemasok() {
        return emailPemasok;
    }

    public void setEmailPemasok(String emailPemasok) {
        this.emailPemasok = emailPemasok;
    }

    public String getNoHpPemasok() {
        return noHpPemasok;
    }

    public void setNoHpPemasok(String noHpPemasok) {
        this.noHpPemasok = noHpPemasok;
    }

    public String getAlamatPemasok() {
        return alamatPemasok;
    }

    public void setAlamatPemasok(String alamatPemasok) {
        this.alamatPemasok = alamatPemasok;
    }

    public Pemasok(){}
}
