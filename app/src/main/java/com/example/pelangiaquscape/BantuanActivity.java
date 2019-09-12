package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

public class BantuanActivity extends AppCompatActivity {

    ImageView cancel;
    CardView caraPembelian, caraPenjualan, caraPenyimpanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bantuan);

        cancel =  findViewById(R.id.im_cancel);
        caraPembelian = findViewById(R.id.cv_cara_pembelian_barang);
        caraPenjualan = findViewById(R.id.cv_cara_transaksi_penjualan);
        caraPenyimpanan = findViewById(R.id.cv_cara_penyimpanan);

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

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
