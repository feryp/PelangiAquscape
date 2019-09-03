package com.example.pelangiaquscape.Model;

import java.util.List;

public class Pembelian {
    private String noPesanan;
    private long tanggalPesanan;
    private String namaPemasok;
    private String metodePembayaran;
    private List<Barang> listBarang;
    private boolean proses;

    public Pembelian(){}

    public Pembelian(String noPesanan, long tanggalPesanan, String namaPemasok, String metodePembayaran, List<Barang> listBarang, boolean proses) {
        this.noPesanan = noPesanan;
        this.tanggalPesanan = tanggalPesanan;
        this.namaPemasok = namaPemasok;
        this.metodePembayaran = metodePembayaran;
        this.listBarang = listBarang;
        this.proses = proses;
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

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }

    public List<Barang> getListBarang() {
        return listBarang;
    }

    public void setListBarang(List<Barang> listBarang) {
        this.listBarang = listBarang;
    }

    public boolean isProses() {
        return proses;
    }

    public void setProses(boolean proses) {
        this.proses = proses;
    }
}
