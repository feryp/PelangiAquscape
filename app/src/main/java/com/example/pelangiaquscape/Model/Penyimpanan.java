package com.example.pelangiaquscape.Model;


//penyimpanan
public class Penyimpanan {
    private String id;
    private long timeInMilis;
    private String keyBarang;
    private String kodeBarang;
    private int jumlahBarang;
    private String keteranganBarang;
    /**
     * 0 = Barang masuk
     * 1 = Barang keluar
     */
    private int jenisPenyimpanan;

    public Penyimpanan(){}

    public Penyimpanan(long timeInMilis, String keyBarang, String kodeBarang, int jumlahBarang, String keteranganBarang, int jenisPenyimpanan) {
        this.timeInMilis = timeInMilis;
        this.keyBarang = keyBarang;
        this.kodeBarang = kodeBarang;
        this.jumlahBarang = jumlahBarang;
        this.keteranganBarang = keteranganBarang;
        this.jenisPenyimpanan = jenisPenyimpanan;
    }

    public long getTimeInMilis() {
        return timeInMilis;
    }

    public void setTimeInMilis(long timeInMilis) {
        this.timeInMilis = timeInMilis;
    }

    public String getKeyBarang() {
        return keyBarang;
    }

    public void setKeyBarang(String keyBarang) {
        this.keyBarang = keyBarang;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public int getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(int jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public String getKeteranganBarang() {
        return keteranganBarang;
    }

    public void setKeteranganBarang(String keteranganBarang) {
        this.keteranganBarang = keteranganBarang;
    }

    public int getJenisPenyimpanan() {
        return jenisPenyimpanan;
    }

    public void setJenisPenyimpanan(int jenisPenyimpanan) {
        this.jenisPenyimpanan = jenisPenyimpanan;
    }
}
