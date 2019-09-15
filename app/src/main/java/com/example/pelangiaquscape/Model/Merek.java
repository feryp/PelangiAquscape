package com.example.pelangiaquscape.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Merek implements Parcelable {


    private String nama;


    public Merek(){}

    public Merek(String nama){
        this.nama = nama;
    }

    protected Merek(Parcel in) {
        nama = in.readString();
    }

    public static final Creator<Merek> CREATOR = new Creator<Merek>() {
        @Override
        public Merek createFromParcel(Parcel in) {
            return new Merek(in);
        }

        @Override
        public Merek[] newArray(int size) {
            return new Merek[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nama);
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

}