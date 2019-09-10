package com.example.pelangiaquscape.Model;

public class Pegawai {


    private String id;
    private String fotoPegawai;
    private String namaPegawai;
    private String namapengguna;
    private String password;
    private String jabatan;
    private String hakAkses;
    private String noHp;
    private String emailPegawai;

    public Pegawai(String id, String fotoPegawai, String namaPegawai, String namapengguna, String password, String jabatan, String hakAkses, String noHp, String emailPegawai) {
        this.id = id;
        this.fotoPegawai = fotoPegawai;
        this.namaPegawai = namaPegawai;
        this.namapengguna = namapengguna;
        this.password = password;
        this.jabatan = jabatan;
        this.hakAkses = hakAkses;
        this.noHp = noHp;
        this.emailPegawai = emailPegawai;
    }

    public Pegawai(){}



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFotoPegawai() {
        return fotoPegawai;
    }

    public void setFotoPegawai(String fotoPegawai) {
        this.fotoPegawai = fotoPegawai;
    }

    public String getNamaPegawai() {
        return namaPegawai;
    }

    public void setNamaPegawai(String namaPegawai) {
        this.namaPegawai = namaPegawai;
    }

    public String getNamapengguna() {
        return namapengguna;
    }

    public void setNamapengguna(String namapengguna) {
        this.namapengguna = namapengguna;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getHakAkses() {
        return hakAkses;
    }

    public void setHakAkses(String hakAkses) {
        this.hakAkses = hakAkses;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getEmailPegawai() {
        return emailPegawai;
    }

    public void setEmailPegawai(String emailPegawai) {
        this.emailPegawai = emailPegawai;
    }


}
