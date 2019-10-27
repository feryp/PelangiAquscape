package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.pelangiaquscape.Model.Barang;
import com.example.pelangiaquscape.Model.Pemberitahuan;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetailPemberitahuanActivity extends AppCompatActivity implements View.OnClickListener{

    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    TextView tvJudul, tvPesan, tvWaktu;

    FirebaseDatabase database;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pemberitahuan);

        //init view
        toolbar = findViewById(R.id.toolbar);
        collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        tvJudul = findViewById(R.id.tv_judul_keterangan_pesan);
        tvPesan = findViewById(R.id.tv_isi_pesan);
        tvWaktu = findViewById(R.id.tv_waktu_pesan);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Barang barang = new Barang();
        Pemberitahuan pemberitahuan = new Pemberitahuan();

        //set text
        String judul = "Barang " + barang.getKode() + " telah mencapai batas minimum";
        String pesan = "Penambahan stok barang " + barang.getKode() + " harap segera dilakukan, agar barang tersedia lagi.";
        tvJudul.setText(judul);
        tvPesan.setText(pesan);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(pemberitahuan.getWaktu());
        SimpleDateFormat waktuFormat = new SimpleDateFormat("dd MMMM yyyy, HH:mm");
        String timeFormat = waktuFormat.format(new Date());
        tvWaktu.setText(timeFormat);

        toolbar.setNavigationOnClickListener(this);
    }

    void loadBarang(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar:
                finish();
                break;
        }
    }
}
