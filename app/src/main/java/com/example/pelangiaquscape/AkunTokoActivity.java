package com.example.pelangiaquscape;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pelangiaquscape.Model.AkunToko;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AkunTokoActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView cancel, save;

    TextInputEditText etNamaToko, etNoTelepon, etAlamat, etKodePos;
    FirebaseRecyclerAdapter adapter;
    FirebaseDatabase databaseAkunToko;
    DatabaseReference databaseReference;
    AkunToko akunToko;
    String id;

    int idAkunToko;
    String DEBUG_TAG = "TESTMOTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun_toko);

        //INIT VIEW
        cancel = findViewById(R.id.im_cancel);
        save = findViewById(R.id.im_save);
        etNamaToko = findViewById(R.id.et_nama_toko);
        etNoTelepon = findViewById(R.id.et_no_telepon_toko);
        etAlamat = findViewById(R.id.et_alamat_toko);
        etKodePos = findViewById(R.id.et_kode_pos);

        //INIT FIREBASE
        databaseAkunToko = FirebaseDatabase.getInstance();
        databaseReference = databaseAkunToko.getReference("AkunToko").child("1");
        akunToko = new AkunToko();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AkunToko akunToko = dataSnapshot.getValue(AkunToko.class);
                String namaToko = akunToko.getNamaToko();
                String noTelepon = akunToko.getNoTelepon();
                String alamatToko = akunToko.getAlamat();
                String kodePos = akunToko.getKodePos();

                //set data
                bind(namaToko, noTelepon, alamatToko, kodePos);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        try {
            id = getIntent().getExtras().getString("idForAkunToko");
            System.out.println("ID in akun toko act " + id);
            bind(getIntent().getExtras().getString("namaToko"),
                    getIntent().getExtras().getString("noTelepon"),
                    getIntent().getExtras().getString("alamatToko"),
                    getIntent().getExtras().getString("kodePos"));
        } catch (NullPointerException ex) {
            id = "1";
        }

        cancel.setOnClickListener(this);
        save.setOnClickListener(this);


    }

    private void getValues() {
        akunToko.setNamaToko(etNamaToko.getText().toString());
        akunToko.setNoTelepon(etNoTelepon.getText().toString());
        akunToko.setAlamat(etAlamat.getText().toString());
        akunToko.setKodePos(etKodePos.getText().toString());
    }

    private void bind(String namaToko, String noTelepon, String alamatToko, String kodePos) {
        etNamaToko.setText(namaToko);
        etNoTelepon.setText(noTelepon);
        etAlamat.setText(alamatToko);
        etKodePos.setText(kodePos);
    }


    private void save() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getValues();
                databaseReference.setValue(akunToko);
                Toast.makeText(AkunTokoActivity.this, "Akun Toko telah ditambahkan", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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


