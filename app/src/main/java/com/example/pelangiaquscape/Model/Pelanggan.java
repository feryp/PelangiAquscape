package com.example.pelangiaquscape.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pelanggan extends KartuPelanggan implements Parcelable{


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

    protected Pelanggan(Parcel in) {
        namaPelanggan = in.readString();
        noHp = in.readString();
        alamat = in.readString();
        catatan = in.readString();
    }

    public static final Creator<Pelanggan> CREATOR = new Creator<Pelanggan>() {
        @Override
        public Pelanggan createFromParcel(Parcel in) {
            return new Pelanggan(in);
        }

        @Override
        public Pelanggan[] newArray(int size) {
            return new Pelanggan[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(namaPelanggan);
        dest.writeString(noHp);
        dest.writeString(alamat);
        dest.writeString(catatan);
    }
}
