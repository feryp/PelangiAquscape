package com.example.pelangiaquscape.TestUI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pelangiaquscape.Model.AkunToko;
import com.example.pelangiaquscape.Model.Penjualan;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.Utils.PDFUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TestUIActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnTest;
    PDFUtils utils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testui);

        btnTest = findViewById(R.id.btn_test);

        loadData();


    }

    void loadData(){
        FirebaseDatabase.getInstance().getReference("Penjualan").child("-Lqysqx_9VccSL6CrLLh").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshotPenjualan) {

                FirebaseDatabase.getInstance().getReference("AkunToko").child("1").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        AkunToko toko = dataSnapshot.getValue(AkunToko.class);

                        Penjualan penjualan = dataSnapshotPenjualan.getValue(Penjualan.class);

                        utils =  new PDFUtils(penjualan, toko);
                        btnTest.setOnClickListener(TestUIActivity.this);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_test:

                try {
                    utils.createPdfForReceipt();
                    Toast.makeText(this, "kayanya berhasil wkkkw", Toast.LENGTH_SHORT).show();
                }catch(Exception exc){
                    exc.printStackTrace();
                    Toast.makeText(this, "GAGAL BOS", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
