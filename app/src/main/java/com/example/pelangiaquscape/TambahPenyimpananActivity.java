package com.example.pelangiaquscape;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pelangiaquscape.Model.Barang;
import com.example.pelangiaquscape.Model.Merek;
import com.example.pelangiaquscape.Model.Penyimpanan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TambahPenyimpananActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView cancel, save;
    Spinner spinnerKeteranganBarang;

    TextInputEditText etNamaBarang, etMerekBarang, etStokBarang, etTanggalMasuk, etKeterangan;

    Barang barang;
    String merek;

    long tanggalMasuk;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 45){
            if(resultCode == RESULT_OK){
                barang = data.getParcelableExtra("barang");
                merek = data.getStringExtra("merek");
                etNamaBarang.setText(barang.getKode());
                etMerekBarang.setText(merek);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_penyimpanan);

        //INIT VIEW
        cancel = findViewById(R.id.im_cancel);
        save = findViewById(R.id.im_save);
        spinnerKeteranganBarang = findViewById(R.id.spinner_status_barang);
        etNamaBarang = findViewById(R.id.et_nama_barang_inventory);
        etMerekBarang = findViewById(R.id.et_merek_barang_inventory);
        etStokBarang = findViewById(R.id.et_jumlah_stok_inventory);
        etTanggalMasuk = findViewById(R.id.et_tanggal_input_inventory);
        etKeterangan = findViewById(R.id.et_keterangan_inventory);

        final Calendar myCalendar = Calendar.getInstance();

        // DATE LISTENER
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                Date date = myCalendar.getTime();
                tanggalMasuk = myCalendar.getTimeInMillis();

                SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyy");
                String tanggalMasuk = format.format(date);
                etTanggalMasuk.setText(tanggalMasuk);

            }

        };

        // FOCUS CHANGE
        etNamaBarang.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Intent i = new Intent(TambahPenyimpananActivity.this, BarangActivity.class);
                    i.putExtra("fromTambahPenyimpananActivity", true);
                    startActivityForResult(i, 45);
                }else{

                }
            }
        });

        etTanggalMasuk.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    new DatePickerDialog(TambahPenyimpananActivity.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });

        // SET SPINNER ADAPTER
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,
                R.array.status_barang_arrays,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinnerKeteranganBarang.setAdapter(adapter);

        cancel.setOnClickListener(this);
        save.setOnClickListener(this);

    }

    private void save() {

        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference("Penyimpanan");




//        Penyimpanan penyimpanan = new Penyimpanan(tanggalMasuk, barang.getKode(),
//                Integer.valueOf(etStokBarang.getText().toString()), etKeterangan.getText().toString(), spinnerKeteranganBarang.getSelectedItemPosition());
//
//        dr.push().setValue(penyimpanan).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//                Toast.makeText(TambahPenyimpananActivity.this, "Penyimpanan barang berhasil", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_cancel:
                finish();
                break;
            case R.id.im_save:
                save();
                finish();
                break;
        }
    }
}




