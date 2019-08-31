package com.example.pelangiaquscape;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pelangiaquscape.Model.Pemasok;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TambahPemasokActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView cancel, save;
    Spinner spinnerJenis, spinnerKlasifikasi, spinnerKualifikasi;
    TextInputEditText etNamaPemasok, etTelepon, etEmail, etNoHp, etAlamat;
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Pemasok pemasok;
    String id;

    int idPemasok;
    String DEBUG_TAG = "TESTIMOTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pemasok);


        //INIT VIEW
        cancel = findViewById(R.id.im_cancel);
        save = findViewById(R.id.im_save);
        etNamaPemasok = findViewById(R.id.et_nama_pemasok);
        etNoHp = findViewById(R.id.et_no_hp_pemasok);
        etTelepon = findViewById(R.id.et_telepon_pemasok);
        etEmail = findViewById(R.id.et_email_pemasok);
        etAlamat = findViewById(R.id.et_alamat_pemasok);

        spinnerJenis = findViewById(R.id.spinner_jenis_perusahaan);
        spinnerKlasifikasi = findViewById(R.id.spinner_klasifikasi_perusahaan);
        spinnerKualifikasi = findViewById(R.id.spinner_kualifikasi_perusahaan);



        //INIT FIREBASE
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Pemasok");
        pemasok = new Pemasok();

        try {
            id = getIntent().getExtras().getString("idForPemasok");
            System.out.println("ID in tambah pemasok act " + id);
            bind(getIntent().getExtras().getString("jenisPerusahaan"),
                    getIntent().getExtras().getString("namaPemasok"),
                    getIntent().getExtras().getString("klasifikasiPerusahaan"),
                    getIntent().getExtras().getString("kualifikasiPerusahaan"),
                    getIntent().getExtras().getString("telepon"),
                    getIntent().getExtras().getString("email"),
                    getIntent().getExtras().getString("noHp"),
                    getIntent().getExtras().getString("alamat"));
        } catch (NullPointerException ex) {
            id = "1";
        }

        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(
                this,
                R.array.jenis_perusahaan_arrays,
                android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinnerJenis.setAdapter(adapter1);

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.klasifikasi_perusahaan_arrays,
                android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinnerKlasifikasi.setAdapter(adapter2);

        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(
                this,
                R.array.kualifikasi_perusahaan_arrays,
                android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinnerKualifikasi.setAdapter(adapter3);


//        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.jenis_perusahaan_arrays, android.R.layout.simple_spinner_item);
//        adapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown);
//        spinnerJenis.setAdapter(adapter1);
//
//        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.klasifikasi_perusahaan_arrays, android.R.layout.simple_spinner_item);
//        adapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown);
//        spinnerKlasifikasi.setAdapter(adapter2);
//
//        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.kualifikasi_perusahaan_arrays, android.R.layout.simple_spinner_item);
//        adapter3.setDropDownViewResource(R.layout.simple_spinner_dropdown);
//        spinnerKualifikasi.setAdapter(adapter3);


        cancel.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    private void bind(final String jenisPerusahaan, String namaPemasok, String klasifikasiPerusahaan, String kualifikasiPerusahaan, String telepon, String email, String noHp, String alamat) {

        spinnerJenis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        etNamaPemasok.setText(namaPemasok);
        etTelepon.setText(telepon);
        etEmail.setText(email);
        etNoHp.setText(noHp);
        etAlamat.setText(alamat);

    }

    private void save() {

        spinnerJenis.getSelectedItem().toString();
        spinnerKlasifikasi.getSelectedItem().toString();
        spinnerKualifikasi.getSelectedItem().toString();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                getValues();
                databaseReference.child(String.valueOf(id)).setValue(pemasok);
                Toast.makeText(TambahPemasokActivity.this, "Pemasok telah ditambah", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getValues() {

        pemasok.setJenisPerusahaan(spinnerJenis.getSelectedItem().toString());
        pemasok.setNamaPemasok(etNamaPemasok.getText().toString());
        pemasok.setKlasifikasiPerusahaan(spinnerKlasifikasi.getSelectedItem().toString());
        pemasok.setKualifikasiPerusahaan(spinnerKualifikasi.getSelectedItem().toString());
        pemasok.setTelepon(etTelepon.getText().toString());
        pemasok.setEmailPemasok(etEmail.getText().toString());
        pemasok.setNoHpPemasok(etNoHp.getText().toString());
        pemasok.setAlamatPemasok(etAlamat.getText().toString());

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
