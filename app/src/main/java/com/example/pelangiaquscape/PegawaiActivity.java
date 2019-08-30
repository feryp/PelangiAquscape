package com.example.pelangiaquscape;

import android.content.Intent;
import android.net.LinkAddress;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

public class PegawaiActivity extends AppCompatActivity {

    ImageView cancel;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    RecyclerView rvPegawai;

    Query q;

    LinearLayout imageLayout;

    RecyclerView.LayoutManager layoutManager;
    FloatingActionButton fab_pegawai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegawai);

        cancel = findViewById(R.id.im_cancel);
        fab_pegawai = findViewById(R.id.fab_pegawai);
        imageLayout = findViewById(R.id.linear_imageview_pegawai);

        //init firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Pegawai");

        //load data pegawai
        rvPegawai = findViewById(R.id.rv_pegawai);
        rvPegawai.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 3);
        rvPegawai.setLayoutManager(layoutManager);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fab_pegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PegawaiActivity.this, "Tambah Pegawai", Toast.LENGTH_SHORT).show();
                Intent fab_pegawai = new Intent(PegawaiActivity.this, TambahPegawaiActivity.class);
                startActivity(fab_pegawai);
            }
        });
    }
}
