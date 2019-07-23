package com.example.pelangiaquscape.Model;

public class Barang {

    private String kode;
    private double hargaBeli;
    private double hargaJual;
    private String satuan;
    private String merek;

    public Barang(){}
    public Barang(String kode, double hargaBeli, double hargaJual, String satuan, String merek) {
        this.kode = kode;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
        this.satuan = satuan;
        this.merek = merek;
    }

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
        merek = merek;
    }
}