package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.pelangiaquscape.Adapter.PenerimaanAdapter;
import com.example.pelangiaquscape.Model.Pembelian;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PenerimaanActivity extends AppCompatActivity {

    RecyclerView rvPenerimaan;
    LinearLayout llPenerimaan;
    List<Pembelian> listPembelian;
    List<String> listKey;
    ImageView cancel;

    Query query;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK){
            loadPembelian();
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penerimaan);

        // INIT VIEW
        rvPenerimaan = findViewById(R.id.rv_penerimaan);
        rvPenerimaan.setHasFixedSize(true);
        rvPenerimaan.setLayoutManager(new LinearLayoutManager(this));
        llPenerimaan = findViewById(R.id.ll_penerimaan);

        cancel = findViewById(R.id.im_cancel);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadPembelian();

    }

    public void loadPembelian(){
//        FirebaseDatabase fd = FirebaseDatabase.getInstance();
//        DatabaseReference dr = fd.getReference("Pembelian");
        query = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Pembelian")
                .orderByChild("tanggalPesanan");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // RESET LIST
                listPembelian = new ArrayList<>();
                listKey = new ArrayList<>();

                boolean test = false;
                for(DataSnapshot snapShot: dataSnapshot.getChildren()){
//                    mapping.put(dataSnapshot.getKey(), snapShot.getValue(Pembelian.class));
                    listKey.add(dataSnapshot.getKey());
                    listPembelian.add(snapShot.getValue(Pembelian.class));

                    if(!snapShot.getValue(Pembelian.class).getProses()){
                        test = true;
                    }

                }

                if(test){
                    PenerimaanAdapter adapter = new PenerimaanAdapter(listPembelian, listKey, PenerimaanActivity.this);
                    rvPenerimaan.setAdapter(adapter);

                    if(adapter.getItemCount() > 0){

                        llPenerimaan.setVisibility(View.GONE);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
