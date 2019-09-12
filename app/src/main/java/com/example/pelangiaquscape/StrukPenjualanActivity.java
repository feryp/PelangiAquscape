package com.example.pelangiaquscape;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

public class StrukPenjualanActivity extends AppCompatActivity {

    FloatingActionButton fabCetak, fabBagikan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_struk_penjualan);

        fabCetak = findViewById(R.id.fab_action_print);
        fabBagikan = findViewById(R.id.fab_action_bagikan);

        fabCetak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Cetak");
            }
        });

        fabBagikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Bagikan");
            }
        });
    }

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
