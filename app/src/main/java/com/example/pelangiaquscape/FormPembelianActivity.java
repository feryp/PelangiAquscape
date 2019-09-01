package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.pelangiaquscape.Model.Barang;
import com.example.pelangiaquscape.Model.ProsesPembelian;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FormPembelianActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView cancel, save;
    Button btnTambahBarang;
    RadioGroup radioMetodePembayaran;
    RadioButton radioButton;
    TextInputEditText etNoPesanan, etNamaPemasok, etTglPesanan;
    List<Barang> barangList;
    FirebaseDatabase databasePembelian;
    DatabaseReference reference;
    ProsesPembelian prosesPembelian;
    String id;
    int idProsesPembelian;

    String DEBUG_TAG = "TESTMOTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pembelian);

        //INIT VIEW
        cancel = findViewById(R.id.im_cancel);
        save = findViewById(R.id.im_save);
        etNoPesanan = findViewById(R.id.et_no_pesanan);
        etNamaPemasok = findViewById(R.id.et_pemasok);
        etTglPesanan = findViewById(R.id.et_tgl_pesanan);
        btnTambahBarang = findViewById(R.id.btn_tambah_barang);

        //INIT FIREBASE
        databasePembelian = FirebaseDatabase.getInstance();
        reference = databasePembelian.getReference("ProsesPembelian");
        prosesPembelian = new ProsesPembelian();


        //SET LISTENER
        btnTambahBarang.setOnClickListener(this);
        cancel.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    public void onRadioButtonClick(View view) {
        radioMetodePembayaran = findViewById(R.id.radio_metode_pembayaran);
        radioButton = findViewById(radioMetodePembayaran.getCheckedRadioButtonId());
        Toast.makeText(this, "Pilih " + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }

    private void save() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reference.child(String.valueOf(id)).setValue(prosesPembelian);
                Toast.makeText(FormPembelianActivity.this, "List Pembelian telah ditambah", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void tambahBarang() {
        Intent tambahBarang = new Intent(FormPembelianActivity.this, BarangActivity.class);
        startActivity(tambahBarang);
//        Toast.makeText(FormPembelianActivity.this, "tambah barang", Toast.LENGTH_SHORT).show();
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
            case  R.id.btn_tambah_barang:
                tambahBarang();
                break;
        }
    }




}
