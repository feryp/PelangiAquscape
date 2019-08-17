package com.example.pelangiaquscape.Model;

import java.util.List;

public class Penjualan {
    private String noPenjualan;
    private String jenisPembayaran;
    private long tanggalPenjualan;
    private String namaPenjual;
    private List<ItemKeranjang> listItemKeranjang;
    private String namaKustomer;
    private String noTelpKustomer;
    private double totalPenjualan;

    public Penjualan() {
    }

    public Penjualan(String noPenjualan, String jenisPembayaran, long tanggalPenjualan, String namaPenjual, List<ItemKeranjang> listItemKeranjang, String namaKustomer, String noTelpKustomer, double totalPenjualan) {
        this.noPenjualan = noPenjualan;
        this.jenisPembayaran = jenisPembayaran;
        this.tanggalPenjualan = tanggalPenjualan;
        this.namaPenjual = namaPenjual;
        this.listItemKeranjang = listItemKeranjang;
        this.namaKustomer = namaKustomer;
        this.noTelpKustomer = noTelpKustomer;
        this.totalPenjualan = totalPenjualan;
    }

    public String getNoPenjualan() {
        return noPenjualan;
    }

    public void setNoPenjualan(String noPenjualan) {
        this.noPenjualan = noPenjualan;
    }

    public String getJenisPembayaran() {
        return jenisPembayaran;
    }

    public void setJenisPembayaran(String jenisPembayaran) {
        this.jenisPembayaran = jenisPembayaran;
    }

    public long getTanggalPenjualan() {
        return tanggalPenjualan;
    }

    public void setTanggalPenjualan(long tanggalPenjualan) {
        this.tanggalPenjualan = tanggalPenjualan;
    }

    public String getNamaPenjual() {
        return namaPenjual;
    }

    public void setNamaPenjual(String namaPenjual) {
        this.namaPenjual = namaPenjual;
    }

    public List<ItemKeranjang> getListItemKeranjang() {
        return listItemKeranjang;
    }

    public void setListItemKeranjang(List<ItemKeranjang> listItemKeranjang) {
        this.listItemKeranjang = listItemKeranjang;
    }

    public String getNamaKustomer() {
        return namaKustomer;
    }

    public void setNamaKustomer(String namaKustomer) {
        this.namaKustomer = namaKustomer;
    }

    public String getNoTelpKustomer() {
        return noTelpKustomer;
    }

    public void setNoTelpKustomer(String noTelpKustomer) {
        this.noTelpKustomer = noTelpKustomer;
    }

    public double getTotalPenjualan() {
        return totalPenjualan;
    }

    public void setTotalPenjualan(double totalPenjualan) {
        this.totalPenjualan = totalPenjualan;
    }
}
