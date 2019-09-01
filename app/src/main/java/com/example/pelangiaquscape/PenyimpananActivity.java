package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class PenyimpananActivity extends AppCompatActivity {

    ImageView cancel;
    FloatingActionButton fab_penyimpanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyimpanan);

        cancel = findViewById(R.id.im_cancel);
        fab_penyimpanan = findViewById(R.id.fab_penyimpanan);

        fab_penyimpanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fab_penyimpanan = new Intent(PenyimpananActivity.this, TambahPenyimpananActivity.class);
                Toast.makeText(PenyimpananActivity.this, "Tambah Inventory", Toast.LENGTH_SHORT).show();
                startActivity(fab_penyimpanan);
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
