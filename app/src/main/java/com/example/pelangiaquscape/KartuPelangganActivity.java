package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelangiaquscape.Model.AkunToko;
import com.example.pelangiaquscape.Model.Pelanggan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class KartuPelangganActivity extends AppCompatActivity {

    TextView tvNamaPelanggan, tvNoHpPelanggan, tvAlamatPelanggan, tvNamaToko, tvNoTeleponToko, tvEmailToko, tvAlamatToko;
    Button cetak;
    ImageView cancel;

    Pelanggan pelanggan;
    AkunToko akunToko;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kartu_pelanggan);

        //GET DATA
        Intent p = getIntent();
        pelanggan = p.getParcelableExtra("pelanggan");
        key = p.getStringExtra("key");

        //INIT VIEW
        tvNamaPelanggan = findViewById(R.id.kartu_nama_pelanggan);
        tvNoHpPelanggan = findViewById(R.id.kartu_no_hp_pelanggan);
        tvAlamatPelanggan = findViewById(R.id.kartu_alamat_pelanggan);
        tvNamaToko = findViewById(R.id.kartu_nama_toko);
        tvNoTeleponToko = findViewById(R.id.kartu_no_telepon_toko);
        tvEmailToko = findViewById(R.id.kartu_email_toko);
        tvAlamatToko = findViewById(R.id.kartu_alamat_toko);
        cancel = findViewById(R.id.im_cancel);
        cetak = findViewById(R.id.btn_cetak_kartu_pelanggan);

        //SET TEXT
        tvNamaPelanggan.setText(pelanggan.getNamaPelanggan());
        tvNoHpPelanggan.setText(pelanggan.getNoHp());
        tvAlamatPelanggan.setText(pelanggan.getAlamat());

        loadAkun();

        cetak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Cetak Kartu");
            }
        });


    }

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    void loadAkun(){
        FirebaseDatabase.getInstance().getReference("AkunToko").child("1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AkunToko akunToko = dataSnapshot.getValue(AkunToko.class);

                tvNamaToko.setText(akunToko.getNamaToko());
                tvNoTeleponToko.setText(akunToko.getNoTelepon());
                tvEmailToko.setText(akunToko.getEmailToko());
                tvAlamatToko.setText(akunToko.getAlamat());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
