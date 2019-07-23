package com.example.pelangiaquscape;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;


/**
 * modul untuk penjualan
 * alur penjualan:
 * PenjualanActivity -> data merk ->
 */
public class PenjualanActivity extends AppCompatActivity {


    private RecyclerView rvListMerk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);

        rvListMerk = findViewById(R.id.rv_daftar_merek);



    }
}
