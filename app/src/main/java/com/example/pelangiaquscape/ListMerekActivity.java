package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ListMerekActivity extends AppCompatActivity {

    ImageView cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_merek);

        cancel = findViewById(R.id.im_cancel);
        FloatingActionButton fab_merek = findViewById(R.id.fab_merek_barang);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fab_merek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ListMerekActivity.this, "Tambah Merek", Toast.LENGTH_SHORT).show();
                openDialog();
            }
        });
    }

    private void openDialog() {
        DialogTambahMerek dialogTambahMerek = new DialogTambahMerek();
        dialogTambahMerek.show(getSupportFragmentManager(), "dialog tambah merek");

    }
}
