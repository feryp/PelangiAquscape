package com.example.pelangiaquscape.TestUI;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pelangiaquscape.Model.AkunToko;
import com.example.pelangiaquscape.Model.Pembelian;
import com.example.pelangiaquscape.Model.Penjualan;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.Utils.FakturUtils;
import com.example.pelangiaquscape.Utils.PDFUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;

public class TestUIActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnTest;
    FakturUtils fakturUtils;
    String key;
    Pembelian pembelian;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testui);

        btnTest = findViewById(R.id.btn_test);

        loadData();


    }

    void loadData(){
        FirebaseDatabase.getInstance().getReference("Pembelian").child("-LodnFnIBuUZZqf6D2Em").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshotPembelian) {

                FirebaseDatabase.getInstance().getReference("AkunToko").child("1").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        AkunToko toko = dataSnapshot.getValue(AkunToko.class);

                        Pembelian pembelian = dataSnapshotPembelian.getValue(Pembelian.class);

                        fakturUtils = new FakturUtils(pembelian, toko);
//                        utils =  new PDFUtils(penjualan, toko);
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

                String fpath = "/sdcard/testPdf.pdf";

                File file = new File(fpath);
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.fromFile(file), "application/pdf");

//                startActivity(intent);

                Uri path = FileProvider.getUriForFile(this,  "com.example.pelangiaquscape.fileprovider", file);

//                Uri path = Uri.fromFile(file);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(path, "application/pdf");
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent);
                try {
                    fakturUtils.createPdfForFaktur();
                    Toast.makeText(this, "kayanya berhasil wkkkw", Toast.LENGTH_SHORT).show();
                }catch(Exception exc){
                    exc.printStackTrace();
                    Toast.makeText(this, "GAGAL BOS", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

//    void openFile(){
////         direktori u/ menyimpan pdf
//        String fpath = "/sdcard/"+ pembelian.getNoPesanan() +".pdf";
//        File file = new File(fpath);
//        if(!file.exists()){
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }



    void openPDFFile(){

        // direktori u/ menyimpan pdf
        String fpath = "/sdcard/testPdf.pdf";
        File file = new File(fpath);
        if(!file.exists()){
            fakturUtils.createPdfForFaktur();
        }else{
            try {
                file.createNewFile();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

    }
    void openReceipt(File file){

        Uri path = FileProvider.getUriForFile(this,  "com.example.pelangiaquscape.fileprovider", file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(path, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }
}
