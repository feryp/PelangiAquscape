package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

public class BantuanActivity extends AppCompatActivity {

    ImageView cancel;
    CardView caraPembelian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bantuan);

        cancel =  findViewById(R.id.im_cancel);
        caraPembelian = findViewById(R.id.cv_cara_pembelian_barang);

        caraPembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent caraPembelian = new Intent(BantuanActivity.this, PusatBantuanPembelianActivity.class);
                startActivity(caraPembelian);
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
