package com.example.pelangiaquscape.Model;

import android.content.ClipData;
import android.os.Parcel;
import android.os.Parcelable;

public class ItemKeranjang implements Parcelable {

    String kode;
    String merek;
    double hargaJual;
    double hargaBeli;
    String satuan;
    int qty;
    double totalPrice;

    public ItemKeranjang(String kode, String merek, double hargaJual, double hargaBeli, String satuan, int qty, double totalPrice) {
        this.kode = kode;
        this.merek = merek;
        this.hargaJual = hargaJual;
        this.hargaBeli = hargaBeli;
        this.satuan = satuan;
        this.qty = qty;
        this.totalPrice = totalPrice;
    }



    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getMerek() {
        return merek;
    }

    public void setMerek(String merek) {
        this.merek = merek;
    }

    public double getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(double hargaJual) {
        this.hargaJual = hargaJual;
    }

    public double getHargaBeli() {
        return hargaBeli;
    }

    public void setHargaBeli(double hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


    public ItemKeranjang(Parcel in){
//        String kode;
//        String merek;
//        double hargaJual;
//        double hargaBeli;
//        String satuan;
//        int qty;
//        double totalPrice;
        this.kode = in.readString();
        this.merek = in.readString();
        this.hargaJual = in.readDouble();
        this.hargaBeli = in.readDouble();
        this.satuan = in.readString();
        this.qty = in.readInt();
        this.totalPrice = in.readDouble();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kode);
        dest.writeString(merek);
        dest.writeDouble(hargaJual);
        dest.writeDouble(hargaBeli);
        dest.writeString(satuan);
        dest.writeInt(qty);
        dest.writeDouble(totalPrice);
    }

    public static final Creator<ItemKeranjang> CREATOR = new Creator<ItemKeranjang>() {
        @Override
        public ItemKeranjang createFromParcel(Parcel in) {
            return new ItemKeranjang(in);
        }

        @Override
        public ItemKeranjang[] newArray(int size) {
            return new ItemKeranjang[size];
        }
    };
}
