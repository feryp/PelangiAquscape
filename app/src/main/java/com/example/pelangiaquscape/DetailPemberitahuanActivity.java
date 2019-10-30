package com.example.pelangiaquscape;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pelangiaquscape.Model.Barang;
import com.example.pelangiaquscape.Model.Pemberitahuan;
import com.example.pelangiaquscape.Model.Penyimpanan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DetailPemberitahuanActivity extends AppCompatActivity implements View.OnClickListener{

    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbarPemberitahuan;
    AppBarLayout appBarLayout;
    Button btnKonfirmasi;
    TextView tvJudul, tvPesan, tvWaktu;
    Pemberitahuan pemberitahuan;
    String key;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pemberitahuan);

        //get data
        Intent i = getIntent();
        pemberitahuan = i.getParcelableExtra("pemberitahuan");
        key = i.getStringExtra("key");


        dialog = new ProgressDialog(this);
        //init view
        toolbarPemberitahuan = findViewById(R.id.toolbar_pemberitahuan);
        collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        appBarLayout = findViewById(R.id.app_bar_pemberitahuan);
        tvJudul = findViewById(R.id.tv_judul_keterangan_pesan);
        tvPesan = findViewById(R.id.tv_isi_pesan);
        tvWaktu = findViewById(R.id.tv_waktu_pesan);
        btnKonfirmasi = findViewById(R.id.btn_konfirmasi_pemberitahuan);

        setSupportActionBar(toolbarPemberitahuan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarPemberitahuan.setNavigationIcon(R.drawable.ic_back);
        toolbarPemberitahuan.setNavigationOnClickListener(this);

        //set text
        String judul = "Barang " + pemberitahuan.getNamaBarang() + " telah mencapai batas minimum";
        String pesan = "Penambahan stok barang " + pemberitahuan.getNamaBarang() + " harap segera dilakukan, agar barang tersedia lagi.";
        tvJudul.setText(judul);
        tvPesan.setText(pesan);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(pemberitahuan.getWaktu());
        Date date = cal.getTime();
        SimpleDateFormat waktuFormat = new SimpleDateFormat("dd MMMM yyyy, HH:mm");
        String timeFormat = waktuFormat.format(date);
        tvWaktu.setText(timeFormat);

        btnKonfirmasi.setOnClickListener(this);


    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_pemberitahuan:
                finish();
                break;
            case R.id.btn_konfirmasi_pemberitahuan:
                Intent intent = new Intent(DetailPemberitahuanActivity.this, TambahPembelianActivity.class);
                startActivity(intent);
                break;
        }
    }
}
