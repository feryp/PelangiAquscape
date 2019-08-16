package com.example.pelangiaquscape.Model;

public class Gudang {
    private String id;
    private long timeInMilis;
    private String namaBarang;
    private String jumlahBarang;
    private String KeteranganBarang;

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
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(String jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public String getKeteranganBarang() {
        return KeteranganBarang;
    }

    public void setKeteranganBarang(String keteranganBarang) {
        KeteranganBarang = keteranganBarang;
    }

    public Gudang(String id, long timeInMilis, String namaBarang, String jumlahBarang, String keteranganBarang) {
        this.id = id;
        this.timeInMilis = timeInMilis;
        this.namaBarang = namaBarang;
        this.jumlahBarang = jumlahBarang;
        KeteranganBarang = keteranganBarang;
    }
}
