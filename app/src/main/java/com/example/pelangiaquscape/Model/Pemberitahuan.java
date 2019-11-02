package com.example.pelangiaquscape.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pemberitahuan implements Parcelable{

    private String judul;
    private String pesan;
    private String namaBarang;
    private long waktu;
    private boolean baca;
    private boolean proses;

    public Pemberitahuan(String judul, String pesan, String namaBarang, long waktu, boolean baca, boolean proses) {
        this.judul = judul;
        this.pesan = pesan;
        this.namaBarang = namaBarang;
        this.waktu = waktu;
        this.baca = baca;
        this.proses = proses;
    }

    protected Pemberitahuan(Parcel in) {
        this.judul = in.readString();
        this.pesan = in.readString();
        this.namaBarang = in.readString();
        this.waktu = in.readLong();
        this.baca = in.readByte() != 0;
        this.proses = in.readByte() != 0;
    }

    public Pemberitahuan(){}


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

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
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
        dest.writeString(this.judul);
        dest.writeString(this.pesan);
        dest.writeString(this.namaBarang);
        dest.writeLong(this.waktu);
        dest.writeByte((byte) (baca ? 1 : 0));
        dest.writeByte((byte) (proses ? 1 : 0));
    }

}
