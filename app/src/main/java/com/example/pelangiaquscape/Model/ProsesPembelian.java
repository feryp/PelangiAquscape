package com.example.pelangiaquscape.Model;

import java.util.List;

public class ProsesPembelian {
    private String noPesanan;
    private long tanggalPesanan;
    private String namaPemasok;
    private String metodePembayaran;
    private List<Barang> listBarang;

    public ProsesPembelian(){}

    public ProsesPembelian(String noPesanan, long tanggalPesanan, String namaPemasok, String metodePembayaran, List<Barang> listBarang) {
        this.noPesanan = noPesanan;
        this.tanggalPesanan = tanggalPesanan;
        this.namaPemasok = namaPemasok;
        this.metodePembayaran = metodePembayaran;
        this.listBarang = listBarang;
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
}
