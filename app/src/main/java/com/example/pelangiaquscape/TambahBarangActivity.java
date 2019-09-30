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
import com.example.pelangiaquscape.Model.Merek;
import com.example.pelangiaquscape.Model.Penyimpanan;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class TambahBarangActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView cancel, save;
    TextInputEditText
            etNamaBarang, etMerekBarang,
            etHargaJual, etHargaModal,
            etStokAwal, etSatuanUnit,
            etMinimumStok;
    int id;
    String currentIdMerek;
    String DEBUG_TAG = "TESTMOTION";
    Barang barang;

    int currentQty = 0;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String merekBarang = data.getStringExtra("namaMerek");
                currentIdMerek = data.getStringExtra("idMerek");
                etMerekBarang.setText(merekBarang);

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_barang);

        // INIT VIEW
        initView();

        id = 0;

        String merek = null;
        // GET INTENT
        Intent i = getIntent();
        try{
            barang = i.getExtras().getParcelable("barang");
            id = i.getIntExtra("idBarang", -1);
            merek = i.getStringExtra("merek");
            currentIdMerek = barang.getMerek();

//            System.out.println("ID BARANG" + id);
        }catch(NullPointerException ex){

        }






        // FOCUS LISTENER
        etMerekBarang.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Intent i = new Intent(TambahBarangActivity.this, ListMerekActivity.class);
                    startActivityForResult(i, 1);
                }

            }
        });

//        etMerekBarang.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int action = event.getActionMasked();
//
//                switch(action) {
//                    case (MotionEvent.ACTION_DOWN) :
//                        Log.d(DEBUG_TAG,"Action was DOWN");
//                        return true;
//                    case (MotionEvent.ACTION_MOVE) :
//                        Log.d(DEBUG_TAG,"Action was MOVE");
//                        return true;
//                    case (MotionEvent.ACTION_UP) :
//
//                        Log.d(DEBUG_TAG,"Action was UP");
//
//                        return true;
//                    case (MotionEvent.ACTION_CANCEL) :
//                        Log.d(DEBUG_TAG,"Action was CANCEL");
//                        return true;
//                    case (MotionEvent.ACTION_OUTSIDE) :
//                        Log.d(DEBUG_TAG,"Movement occurred outside bounds " +
//                                "of current screen element");
//                        return true;
//                    default :
//                        return TambahBarangActivity.super.onTouchEvent(event);
//                }
//            }
//        });


        if (id > 0) {
            etStokAwal.setHint("Stok saat ini");
            etNamaBarang.setText(barang.getKode());
            etMerekBarang.setText(merek);
            BigDecimal dr = new BigDecimal(barang.getHargaJual());
            etHargaJual.setText(dr.toString());
            dr = new BigDecimal(barang.getHargaBeli());
            etHargaModal.setText(dr.toString());
            etSatuanUnit.setText(barang.getSatuan());
            etStokAwal.setText(!String.valueOf(barang.getStok()).isEmpty()?String.valueOf(barang.getStok()):"0");
            etMinimumStok.setText(!String.valueOf(barang.getMinStok()).isEmpty()?String.valueOf(barang.getMinStok()):"0");
            currentQty = barang.getStok();

        }

        // SET LISTENER
        cancel.setOnClickListener(this);
        save.setOnClickListener(this);

        // END SET LISTENER

    }

    void initView(){
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_cancel:
                finish();
                break;
            case R.id.im_save:

                Barang currentBarang;
//                if(id > 0){
//                    currentBarang = barang;
//                    currentBarang.setKode(etNamaBarang.getText().toString() != null ? etNamaBarang.getText().toString(): "");
//                    currentBarang.setHargaBeli(Double.valueOf(etHargaModal.getText().toString()!= null ? etHargaModal.getText().toString(): "0"));
//                    currentBarang.setHargaJual(Double.valueOf(etHargaJual.getText().toString() != null ? etHargaJual.getText().toString() : "0"));
//                    currentBarang.setSatuan(etSatuanUnit.getText().toString() != null ? etSatuanUnit.getText().toString() : "");
//                    currentBarang.setStok( Integer.parseInt(etStokAwal.getText().toString() != null ? etStokAwal.getText().toString(): "0"));
//                    currentBarang.setMinStok(Integer.parseInt(etMinimumStok.getText().toString() != null ? etMinimumStok.getText().toString() : "10"));
//                    currentBarang.setMerek(String.valueOf(currentIdMerek));
//                }

                Barang barang = new Barang(etNamaBarang.getText().toString(),
                        Double.valueOf(etHargaModal.getText().toString()),
                        Double.valueOf(etHargaJual.getText().toString()),
                        etSatuanUnit.getText().toString(),currentIdMerek ,
                        Integer.parseInt(etStokAwal.getText().toString()),
                        Integer.parseInt(etMinimumStok.getText().toString()));

                int theId;
                if(id > 0){
                    theId = id;
                    Penyimpanan penyimpanan;
                    int inputStok = Integer.parseInt(etStokAwal.getText().toString());
                    if(inputStok > currentQty) {

                        penyimpanan = new Penyimpanan(Calendar.getInstance().getTimeInMillis(), String.valueOf(id), barang.getKode(),inputStok - currentQty , "Stok Opname", 0);
                    }else{
                        penyimpanan = new Penyimpanan(Calendar.getInstance().getTimeInMillis(), String.valueOf(id), barang.getKode(), currentQty - inputStok, "Stok Opname", 1);
                    }

                    FirebaseDatabase.getInstance().getReference("Penyimpanan").push().setValue(penyimpanan);

                }else{
                    theId = getIntent().getIntExtra("sizeOfListBarang", -1);
                    if(theId < 0 ){
                        Penyimpanan penyimpanan;
                        int inputStok = Integer.parseInt(etStokAwal.getText().toString());
                        penyimpanan = new Penyimpanan(Calendar.getInstance().getTimeInMillis(), String.valueOf(1), barang.getKode(),inputStok , "Barang Baru", 0);
                        FirebaseDatabase.getInstance().getReference("Penyimpanan").push().setValue(penyimpanan);

                        FirebaseDatabase.getInstance().getReference("Barang").child(String.valueOf(1)).setValue(barang)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(TambahBarangActivity.this, "Barang berhasil diinput", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });

                    }else {

                        int next = theId + 1;

                        Penyimpanan penyimpanan;
                        int inputStok = Integer.parseInt(etStokAwal.getText().toString());
                        penyimpanan = new Penyimpanan(Calendar.getInstance().getTimeInMillis(), String.valueOf(next), barang.getKode(),inputStok , "Barang Baru", 0);
                        FirebaseDatabase.getInstance().getReference("Penyimpanan").push().setValue(penyimpanan);

                        FirebaseDatabase.getInstance().getReference("Barang").child(String.valueOf(next)).setValue(barang)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(TambahBarangActivity.this, "Barang berhasil diinput", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                    }

                }



                break;
            case R.id.et_merek_barang:
                Intent merekBarang = new Intent(TambahBarangActivity.this, ListMerekActivity.class);
                startActivity(merekBarang);
                break;

        }
    }


}

