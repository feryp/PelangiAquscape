package com.example.pelangiaquscape.Model;

public class Bantuan {
    String judul;
    String desk;
    int image;

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Bantuan(String s, String s1, int i) {
        this.judul = s;
        this.desk = s1;
        this.image = i;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }
}
