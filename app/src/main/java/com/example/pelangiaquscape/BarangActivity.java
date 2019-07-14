package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class BarangActivity extends AppCompatActivity {

    ImageView cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang);

        cancel =  (ImageView) findViewById(R.id.im_cancel);

        FloatingActionButton fab_barang = findViewById(R.id.fab_barang);

        fab_barang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BarangActivity.this, "Tambah Barang", Toast.LENGTH_SHORT).show();
                Intent fab_barang = new Intent(BarangActivity.this, TambahBarangActivity.class);
                startActivity(fab_barang);
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
