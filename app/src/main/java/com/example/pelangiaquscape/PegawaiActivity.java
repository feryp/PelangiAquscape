package com.example.pelangiaquscape;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class PegawaiActivity extends AppCompatActivity {

    ImageView cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegawai);

        cancel =(ImageView) findViewById(R.id.im_cancel);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab_pegawai);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PegawaiActivity.this, "Tambah Pegawai", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
