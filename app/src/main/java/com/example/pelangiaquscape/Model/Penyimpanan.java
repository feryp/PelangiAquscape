package com.example.pelangiaquscape.Model;


//penyimpanan
public class Penyimpanan {
    private String id;
    private long timeInMilis;
    private String kodeBarang;
    private String jumlahBarang;
    private String keteranganBarang;
    private String jenisPenyimpanan;

    public Penyimpanan(String id, long timeInMilis, String kodeBarang, String jumlahBarang, String keteranganBarang, String jenisPenyimpanan) {
        this.id = id;
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

    public String getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(String jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public String getKeteranganBarang() {
        return keteranganBarang;
    }

    public void setKeteranganBarang(String keteranganBarang) {
        this.keteranganBarang = keteranganBarang;
    }

    public String getJenisPenyimpanan() {
        return jenisPenyimpanan;
    }

    public void setJenisPenyimpanan(String jenisPenyimpanan) {
        this.jenisPenyimpanan = jenisPenyimpanan;
    }
}
