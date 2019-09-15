package com.example.pelangiaquscape.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Barang implements Parcelable {

    private String kode;
    private double hargaBeli;
    private double hargaJual;
    private String satuan;
    private String merek;
    private int stok;
    private int minStok;

    public Barang(){}
    public Barang(String kode, double hargaBeli, double hargaJual, String satuan, String merek, int stok, int minStok) {
        this.kode = kode;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
        this.satuan = satuan;
        this.merek = merek;
        this.stok = stok;
        this.minStok = minStok;
    }

    public Barang(String kode, double hargaBeli, double hargaJual, String satuan, String merek) {
        this.kode = kode;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
        this.satuan = satuan;
        this.merek = merek;
    }

    protected Barang(Parcel in) {
        kode = in.readString();
        hargaBeli = in.readDouble();
        hargaJual = in.readDouble();
        satuan = in.readString();
        merek = in.readString();
        stok = in.readInt();
        minStok = in.readInt();
    }

    public static final Creator<Barang> CREATOR = new Creator<Barang>() {
        @Override
        public Barang createFromParcel(Parcel in) {
            return new Barang(in);
        }

        @Override
        public Barang[] newArray(int size) {
            return new Barang[size];
        }
    };

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public double getHargaBeli() {
        return hargaBeli;
    }

    public void setHargaBeli(double hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public double getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(double hargaJual) {
        this.hargaJual = hargaJual;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getMerek() {
        return merek;
    }

    public void setMerek(String merek) {
        this.merek = merek;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public int getMinStok() {
        return minStok;
    }

    public void setMinStok(int minStok) {
        this.minStok = minStok;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kode);
        dest.writeDouble(hargaBeli);
        dest.writeDouble(hargaJual);
        dest.writeString(satuan);
        dest.writeString(merek);
        dest.writeInt(stok);
        dest.writeInt(minStok);
    }
}
