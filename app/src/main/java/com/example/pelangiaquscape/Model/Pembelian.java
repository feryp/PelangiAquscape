package com.example.pelangiaquscape.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Pembelian implements Parcelable {
    private String noPesanan;
    private long tanggalPesanan;
    private String namaPemasok;
    private int metodePembayaran;
    private List<ItemKeranjang> listBarang;
//    private int statusPembelian;



    private boolean proses;

    public Pembelian(){}

    public Pembelian(String noPesanan, long tanggalPesanan, String namaPemasok,
                     int metodePembayaran, List<ItemKeranjang> listBarang, boolean proses) {
        this.noPesanan = noPesanan;
        this.tanggalPesanan = tanggalPesanan;
        this.namaPemasok = namaPemasok;
        this.metodePembayaran = metodePembayaran;
        this.listBarang = listBarang;
//        this.statusPembelian = statusPembelian;
        this.proses = proses;
    }

    protected Pembelian(Parcel in) {
        noPesanan = in.readString();
        tanggalPesanan = in.readLong();
        namaPemasok = in.readString();
        metodePembayaran = in.readInt();
        listBarang = in.createTypedArrayList(ItemKeranjang.CREATOR);
//        statusPembelian = in.readInt();
        proses = in.readByte() != 0;
    }


    public String getNoPesanan() {
        return noPesanan;
    }

    public void setNoPesanan(String noPesanan) {
        this.noPesanan = noPesanan;
    }

    public long getTanggalPesanan() {
        return tanggalPesanan;
    }

    public void setTanggalPesanan(long tanggalPesanan) {
        this.tanggalPesanan = tanggalPesanan;
    }

    public String getNamaPemasok() {
        return namaPemasok;
    }

    public void setNamaPemasok(String namaPemasok) {
        this.namaPemasok = namaPemasok;
    }

    public int getMetodePembayaran() {
        return metodePembayaran;
    }

    public void setMetodePembayaran(int metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }

    public List<ItemKeranjang> getListBarang() {
        return listBarang;
    }

    public void setListBarang(List<ItemKeranjang> listBarang) {
        this.listBarang = listBarang;
    }

//    public int getStatusPembelian() {
//        return statusPembelian;
//    }
//
//    public void setStatusPembelian(int statusPembelian) {
//        this.statusPembelian = statusPembelian;
//    }

    public boolean getProses() {
        return proses;
    }

    public void setProses(boolean proses) {
        this.proses = proses;
    }

    public double getTotalHarga(){
        double total = 0;
        for(ItemKeranjang keranjang:listBarang){
            total = total + (keranjang.getHargaBeli()* keranjang.getQty());
        }

        return total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(noPesanan);
        dest.writeLong(tanggalPesanan);
        dest.writeString(namaPemasok);
        dest.writeInt(metodePembayaran);
        dest.writeTypedList(listBarang);
        dest.writeByte((byte) (proses ? 1 : 0));
    }

    public static final Creator<Pembelian> CREATOR = new Creator<Pembelian>() {
        @Override
        public Pembelian createFromParcel(Parcel in) {
            return new Pembelian(in);
        }

        @Override
        public Pembelian[] newArray(int size) {
            return new Pembelian[size];
        }
    };
}
