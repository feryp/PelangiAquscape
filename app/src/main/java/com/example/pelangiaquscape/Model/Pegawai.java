package com.example.pelangiaquscape.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pegawai implements Parcelable {


    private String id;
    private String fotoPegawai;
    private String namaPegawai;
    private String namapengguna;
    private String password;
    private String jabatan;
    private String hakAkses;
    private String noHp;
    private String emailPegawai;
    private String bio;



    public Pegawai(String id, String fotoPegawai, String namaPegawai, String namapengguna, String password, String jabatan, String hakAkses, String noHp, String emailPegawai, String bio) {
        this.id = id;
        this.fotoPegawai = fotoPegawai;
        this.namaPegawai = namaPegawai;
        this.namapengguna = namapengguna;
        this.password = password;
        this.jabatan = jabatan;
        this.hakAkses = hakAkses;
        this.noHp = noHp;
        this.emailPegawai = emailPegawai;
        this.bio = bio;
    }


    public Pegawai(){}


    protected Pegawai(Parcel in) {
        id = in.readString();
        fotoPegawai = in.readString();
        namaPegawai = in.readString();
        namapengguna = in.readString();
        password = in.readString();
        jabatan = in.readString();
        hakAkses = in.readString();
        noHp = in.readString();
        emailPegawai = in.readString();
    }

    public static final Creator<Pegawai> CREATOR = new Creator<Pegawai>() {
        @Override
        public Pegawai createFromParcel(Parcel in) {
            return new Pegawai(in);
        }

        @Override
        public Pegawai[] newArray(int size) {
            return new Pegawai[size];
        }
    };

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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(fotoPegawai);
        dest.writeString(namaPegawai);
        dest.writeString(namapengguna);
        dest.writeString(password);
        dest.writeString(jabatan);
        dest.writeString(hakAkses);
        dest.writeString(noHp);
        dest.writeString(emailPegawai);
        dest.writeString(bio);
    }

    @Override
    public String toString() {
        return "Pegawai{" +
                "id='" + id + '\'' +
                ", fotoPegawai='" + fotoPegawai + '\'' +
                ", namaPegawai='" + namaPegawai + '\'' +
                ", namapengguna='" + namapengguna + '\'' +
                ", password='" + password + '\'' +
                ", jabatan='" + jabatan + '\'' +
                ", hakAkses='" + hakAkses + '\'' +
                ", noHp='" + noHp + '\'' +
                ", emailPegawai='" + emailPegawai + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }

}
