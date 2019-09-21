package com.example.pelangiaquscape.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class AkunToko implements Parcelable {

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

    protected AkunToko(Parcel in) {
        namaToko = in.readString();
        noTelepon = in.readString();
        emailToko = in.readString();
        alamat = in.readString();
        kodePos = in.readString();
    }

    public static final Creator<AkunToko> CREATOR = new Creator<AkunToko>() {
        @Override
        public AkunToko createFromParcel(Parcel in) {
            return new AkunToko(in);
        }

        @Override
        public AkunToko[] newArray(int size) {
            return new AkunToko[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(namaToko);
        dest.writeString(noTelepon);
        dest.writeString(emailToko);
        dest.writeString(alamat);
        dest.writeString(kodePos);
    }
}
