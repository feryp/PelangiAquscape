package com.example.pelangiaquscape.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pemasok implements Parcelable {

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

    protected Pemasok(Parcel in) {
        jenisPerusahaan = in.readString();
        namaPemasok = in.readString();
        klasifikasiPerusahaan = in.readString();
        kualifikasiPerusahaan = in.readString();
        telepon = in.readString();
        emailPemasok = in.readString();
        noHpPemasok = in.readString();
        alamatPemasok = in.readString();
    }

    public static final Creator<Pemasok> CREATOR = new Creator<Pemasok>() {
        @Override
        public Pemasok createFromParcel(Parcel in) {
            return new Pemasok(in);
        }

        @Override
        public Pemasok[] newArray(int size) {
            return new Pemasok[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(jenisPerusahaan);
        dest.writeString(namaPemasok);
        dest.writeString(klasifikasiPerusahaan);
        dest.writeString(kualifikasiPerusahaan);
        dest.writeString(telepon);
        dest.writeString(emailPemasok);
        dest.writeString(noHpPemasok);
        dest.writeString(alamatPemasok);
    }
}
