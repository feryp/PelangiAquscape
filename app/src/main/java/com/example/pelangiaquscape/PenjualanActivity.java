package com.example.pelangiaquscape;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.Model.Penjualan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PenjualanActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView cancel;


    RecyclerView rvPenjualan;

    List<Penjualan> listPenjualan = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);


        // INIT VIEW
        cancel =  findViewById(R.id.im_cancel);
        rvPenjualan = findViewById(R.id.rv_penjualan);
        rvPenjualan.setHasFixedSize(true);
        rvPenjualan.setLayoutManager(new LinearLayoutManager(this));

        // INIT VIEW END

        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference().child("Penjualan");

        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnap: dataSnapshot.getChildren()){
                    listPenjualan.add(dataSnap.getValue(Penjualan.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.im_cancel:
                finish();
                break;
        }

    }
}
