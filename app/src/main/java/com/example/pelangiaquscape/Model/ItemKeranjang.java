package com.example.pelangiaquscape.Model;

public class ItemKeranjang {

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
}
