package com.example.pelangiaquscape.Model;


//penyimpanan
public class Penyimpanan {
    private String id;
    private long timeInMilis;
    private String kodeBarang;

    private int jumlahBarang;
    private String keteranganBarang;
    /**
     * 1 = Barang masuk
     * 2 = Barang keluar
     */
    private int jenisPenyimpanan;

    public Penyimpanan(String id, long timeInMilis, String kodeBarang,
                       int jumlahBarang, String keteranganBarang, int jenisPenyimpanan) {
        this.id = id;
        this.timeInMilis = timeInMilis;
        this.kodeBarang = kodeBarang;
        this.jumlahBarang = jumlahBarang;
        this.keteranganBarang = keteranganBarang;
        this.jenisPenyimpanan = jenisPenyimpanan;
    }

    public Penyimpanan(long timeInMilis, String kodeBarang,
                       int jumlahBarang, String keteranganBarang, int jenisPenyimpanan) {
        this.timeInMilis = timeInMilis;
        this.kodeBarang = kodeBarang;
        this.jumlahBarang = jumlahBarang;
        this.keteranganBarang = keteranganBarang;
        this.jenisPenyimpanan = jenisPenyimpanan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimeInMilis() {
        return timeInMilis;
    }

    public void setTimeInMilis(long timeInMilis) {
        this.timeInMilis = timeInMilis;
    }

    public String getNamaBarang() {
        return kodeBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.kodeBarang = namaBarang;
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
