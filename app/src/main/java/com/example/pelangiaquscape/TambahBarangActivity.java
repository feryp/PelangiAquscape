package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelangiaquscape.Model.Barang;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TambahBarangActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView cancel, save;

    TextInputEditText etNamaBarang, etMerekBarang, etHargaJual, etHargaModal, etStokAwal, etSatuanUnit, etMinimumStok;
    int id;

    String DEBUG_TAG = "TESTMOTION";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){
            etMerekBarang.setText(data.getStringExtra("idMerek"));

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_barang);


        // INIT VIEW
        cancel = findViewById(R.id.im_cancel);
        save = findViewById(R.id.im_save);
        etNamaBarang = findViewById(R.id.et_nama_barang);
        etMerekBarang = findViewById(R.id.et_merek_barang);
        etHargaJual = findViewById(R.id.et_harga_jual);
        etHargaModal = findViewById(R.id.et_harga_modal);
        etStokAwal = findViewById(R.id.et_stok_awal);
        etSatuanUnit = findViewById(R.id.et_satuan_unit);
        etMinimumStok = findViewById(R.id.et_minimum_stok);
        // END INIT VIEW

        etMerekBarang.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                Log.v("keycodeasd", event.toString() + " " + keyCode);
                return false;
            }


        });

        etMerekBarang.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();

                switch(action) {
                    case (MotionEvent.ACTION_DOWN) :
                        Log.d(DEBUG_TAG,"Action was DOWN");
                        return true;
                    case (MotionEvent.ACTION_MOVE) :
                        Log.d(DEBUG_TAG,"Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP) :

                        Log.d(DEBUG_TAG,"Action was UP");
                        Intent i = new Intent(TambahBarangActivity.this, ListMerekActivity.class);
                        startActivityForResult(i, 1);
                        return true;
                    case (MotionEvent.ACTION_CANCEL) :
                        Log.d(DEBUG_TAG,"Action was CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE) :
                        Log.d(DEBUG_TAG,"Movement occurred outside bounds " +
                                "of current screen element");
                        return true;
                    default :
                        return TambahBarangActivity.super.onTouchEvent(event);
                }
            }
        });

        Intent i = getIntent();
        id = i.getIntExtra("idBarang", -1);
        if (id > 0) {
            etStokAwal.setHint("Stok saat ini");
            FirebaseDatabase.getInstance().getReference().child("Barang").child(String.valueOf(id)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Barang barang = dataSnapshot.getValue(Barang.class);
                    Log.v("linkasd", dataSnapshot.getRef().getPath().toString());

                    if (barang != null) {
                        etNamaBarang.setText(barang.getKode() == null ? "" : barang.getKode());
                    }
                    String merek = dataSnapshot.child("merek").getValue(String.class);
                    etMerekBarang.setText(merek);
                    etHargaJual.setText(String.valueOf(barang.getHargaJual()));
                    etHargaModal.setText(String.valueOf(barang.getHargaBeli()));

                    etSatuanUnit.setText(barang.getSatuan());

                    if (barang.getStok() < 0) {

                    }
                    etStokAwal.setText(!String.valueOf(barang.getStok()).isEmpty()?String.valueOf(barang.getStok()):"0");
                    etMinimumStok.setText(!String.valueOf(barang.getMinStok()).isEmpty()?String.valueOf(barang.getMinStok()):"0");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

//        etMerekBarang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(TambahBarangActivity.this, ListMerekActivity.class);
//                startActivity(i);
//
//            }
//        });

//        etMerekBarang.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//            }
//        });


        // SET LISTENER
        cancel.setOnClickListener(this);
        save.setOnClickListener(this);
//        etMerekBarang.setOnClickListener(this);
        // END SET LISTENER

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_cancel:
                finish();
                break;
            case R.id.im_save:

                Barang barang = new Barang(etNamaBarang.getText().toString(),
                        Double.valueOf(etHargaModal.getText().toString()),
                        Double.valueOf(etHargaJual.getText().toString()),
                        etSatuanUnit.getText().toString(), etMerekBarang.getText().toString(),
                        Integer.parseInt(etStokAwal.getText().toString()),
                        Integer.parseInt(etMinimumStok.getText().toString()));

                int theId;
                if(id > 0){
                    theId = id;
                }else{
                    theId = getIntent().getIntExtra("sizeOfListBarang", -1);
                }

                FirebaseDatabase.getInstance().getReference("Barang").child(String.valueOf(theId)).setValue(barang)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(TambahBarangActivity.this, "Barang berhasil diinput", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });


                break;
            case R.id.et_merek_barang:
                Intent merekBarang = new Intent(TambahBarangActivity.this, ListMerekActivity.class);
                startActivity(merekBarang);
                break;

        }
    }


}

