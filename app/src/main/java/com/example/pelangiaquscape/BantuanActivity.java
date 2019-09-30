package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

public class BantuanActivity extends AppCompatActivity {

    ImageView cancel;
    CardView caraPembelian, caraPenjualan, caraPenyimpanan, caraTambahBarang, caraPenerimaan, caraMitraBisnis, caraLaporan, caraRekap, caraPemberitahuan, caraAkun, caraAkunToko, caraPrinter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bantuan);

        cancel =  findViewById(R.id.im_cancel);
        caraPembelian = findViewById(R.id.cv_cara_pembelian_barang);
        caraPenjualan = findViewById(R.id.cv_cara_transaksi_penjualan);
        caraPenyimpanan = findViewById(R.id.cv_cara_penyimpanan);
        caraTambahBarang = findViewById(R.id.cv_cara_tambah_barang);
        caraPenerimaan = findViewById(R.id.cv_cara_penerimaan_barang);
        caraMitraBisnis = findViewById(R.id.cv_cara_mitra_bisnis);
        caraLaporan = findViewById(R.id.cv_cara_lihat_laporan);
        caraRekap = findViewById(R.id.cv_cara_lihat_rekap);
        caraPemberitahuan = findViewById(R.id.cv_cara_lihat_pemberitahuan);
        caraAkun = findViewById(R.id.cv_cara_edit_akun);
        caraAkunToko = findViewById(R.id.cv_cara_edit_akun_toko);
        caraPrinter = findViewById(R.id.cv_cara_setting_printer);

        caraPembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent caraPembelian = new Intent(BantuanActivity.this, PusatBantuanPembelianActivity.class);
                startActivity(caraPembelian);
            }
        });

        caraPenjualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent caraPenjualan = new Intent(BantuanActivity.this, PusatBantuanPenjualanActivity.class);
                startActivity(caraPenjualan);
            }
        });

        caraPenyimpanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent caraPenyimpanan = new Intent(BantuanActivity.this, PusatBantuanPenyimpananActivity.class);
                startActivity(caraPenyimpanan);
            }
        });

        caraTambahBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent caraTambahBarang = new Intent(BantuanActivity.this, PusatBantuanTambahBarangActivity.class);
                startActivity(caraTambahBarang);
            }
        });

        caraPenerimaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent caraPenerimaan = new Intent(BantuanActivity.this, PusatBantuanPenerimaanActivity.class);
                startActivity(caraPenerimaan);
            }
        });

        caraMitraBisnis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  caraMitraBisnis = new Intent(BantuanActivity.this, PusatBantuanMitraBisnisActivity.class);
                startActivity(caraMitraBisnis);
            }
        });

        caraLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  caraLaporan = new Intent(BantuanActivity.this, PusatBantuanLaporanActivity.class);
                startActivity(caraLaporan);
            }
        });

        caraRekap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent   caraRekap = new Intent(BantuanActivity.this, PusatBantuanRekapActivity.class);
                startActivity(caraRekap);
            }
        });

        caraPemberitahuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent caraPemberitahuan = new Intent(BantuanActivity.this, PusatBantuanPemberitahuanActivity.class);
                startActivity(caraPemberitahuan);
            }
        });

        caraAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent caraAkun = new Intent(BantuanActivity.this, PusatBantuanAkunActivity.class);
                startActivity(caraAkun);
            }
        });

        caraAkunToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent caraAkunToko = new Intent(BantuanActivity.this, PusatBantuanAkunTokoActivity.class);
                startActivity(caraAkunToko);
            }
        });

        caraPrinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent caraPrinter = new Intent(BantuanActivity.this, PusatBantuanPrinterActivity.class);
                startActivity(caraPrinter);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
