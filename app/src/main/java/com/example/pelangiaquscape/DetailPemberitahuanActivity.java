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

public class DetailPemberitahuanActivity extends AppCompatActivity implements View.OnClickListener {

    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbarPemberitahuan;
    AppBarLayout appBarLayout;
    Button btnKonfirmasi;
    TextView tvJudul, tvPesan, tvWaktu;
    Pemberitahuan pemberitahuan;
    Barang barang;
    String key, barangTemp, waktuTemp;
    long waktu;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pemberitahuan);

        //GET DATA

        Intent i = getIntent();
        pemberitahuan = i.getParcelableExtra("pemberitahuan");
        barang = i.getParcelableExtra("barang");
        key = i.getStringExtra("key");
        barangTemp = i.getStringExtra("title");
        waktuTemp = i.getStringExtra("tanggal");
       /* barangTemp = i.getStringExtra("NAMA_BARANG");*/
//        Log.d("")
        // tanggal = i.getStringExtra("TANGGAL");


        dialog = new ProgressDialog(this);

        //INIT VIEW
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

        try {
        barangTemp = pemberitahuan.getNamaBarang();
        waktu = pemberitahuan.getWaktu();

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(waktu);
            Date date = cal.getTime();
            SimpleDateFormat waktuFormat = new SimpleDateFormat("dd MMMM yyyy, HH:mm");
            String timeFormat = waktuFormat.format(date);
            tvWaktu.setText(timeFormat);
        }
        catch (NullPointerException e) {
            tvWaktu.setText(waktuTemp);
        }

        //SET TEXT
        tvJudul.setText("Barang " + barangTemp + " telah mencapai batas minimum");
        tvPesan.setText("Penambahan stok barang " + barangTemp + " harap segera dilakukan, agar barang tersedia lagi.");


    /*    Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(waktu);
        Date date = cal.getTime();
        SimpleDateFormat waktuFormat = new SimpleDateFormat("dd MMMM yyyy, HH:mm");
        String timeFormat = waktuFormat.format(date);
        tvWaktu.setText(timeFormat);*/


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
