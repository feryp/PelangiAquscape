package com.example.pelangiaquscape.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pemberitahuan implements Parcelable{

    private String judul;
    private String pesan;
    private long waktu;
    private boolean baca;
    private boolean proses;


    public Pemberitahuan(){}

    public Pemberitahuan(String judul, String pesan, long waktu, Boolean baca, Boolean proses) {
        this.judul = judul;
        this.pesan = pesan;
        this.waktu = waktu;
        this.baca = baca;
        this.proses = proses;
    }


    protected Pemberitahuan(Parcel in) {
        judul = in.readString();
        pesan = in.readString();
        waktu = in.readLong();
        baca = in.readByte() != 0;
        proses = in.readByte() != 0;
    }

    public static final Creator<Pemberitahuan> CREATOR = new Creator<Pemberitahuan>() {
        @Override
        public Pemberitahuan createFromParcel(Parcel in) {
            return new Pemberitahuan(in);
        }

        @Override
        public Pemberitahuan[] newArray(int size) {
            return new Pemberitahuan[size];
        }
    };

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public long getWaktu() {
        return waktu;
    }

    public void setWaktu(long waktu) {
        this.waktu = waktu;
    }

    public boolean isBaca() {
        return baca;
    }

    public void setBaca(boolean baca) {
        this.baca = baca;
    }

    public boolean isProses() {
        return proses;
    }

    public void setProses(boolean proses) {
        this.proses = proses;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(judul);
        dest.writeString(pesan);
        dest.writeLong(waktu);
        dest.writeByte((byte) (baca ? 1 : 0));
        dest.writeByte((byte) (proses ? 1 : 0));
    }
}
