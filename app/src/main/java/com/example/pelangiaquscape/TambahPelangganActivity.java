package com.example.pelangiaquscape;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pelangiaquscape.Model.Pelanggan;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TambahPelangganActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView cancel, save;

    TextInputEditText etNamaPelanggan, etNoHp, etAlamat, etCatatan;
    FirebaseRecyclerAdapter adapter;
    FirebaseDatabase database;
    DatabaseReference reference;
    Pelanggan pelanggan;
    String id;

    int idPelanggan;
    String DEBUG_TAG = "TESTMOTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pelanggan);

        //INIT VIEW
        cancel = findViewById(R.id.im_cancel);
        save = findViewById(R.id.im_save);
        etNamaPelanggan = findViewById(R.id.et_nama_pelanggan);
        etNoHp = findViewById(R.id.et_no_hp);
        etAlamat = findViewById(R.id.et_alamat);
        etCatatan = findViewById(R.id.et_catatan);

        //INIT FIREBASE
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Pelanggan");
        pelanggan = new Pelanggan();


        try{
            id = getIntent().getExtras().getString("idForPelanggan");
            System.out.println("ID in tambah pelanggan act "+ id);
            bind(getIntent().getExtras().getString("namaPelanggan"),
                    getIntent().getExtras().getString("noHp"),
                    getIntent().getExtras().getString("alamat"),
                    getIntent().getExtras().getString("catatan"));
        }catch(NullPointerException ex){
            id = "1";

        }


//        // SET LISTENER
        cancel.setOnClickListener(this);
        save.setOnClickListener(this);

    }

    private void getValues(){
        pelanggan.setNamaPelanggan(etNamaPelanggan.getText().toString());
        pelanggan.setNoHp(etNoHp.getText().toString());
        pelanggan.setAlamat(etAlamat.getText().toString());
        pelanggan.setCatatan(etCatatan.getText().toString());
    }

    private void bind(String nama, String noTelp, String alamat, String catatan){
        etNamaPelanggan.setText(nama);
        etNoHp.setText(noTelp);
        etAlamat.setText(alamat);
        etCatatan.setText(catatan);
    }

    public void save(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getValues();
                reference.child(String.valueOf(id)).setValue(pelanggan);
                Toast.makeText(TambahPelangganActivity.this, "Pelangan telah ditambah",Toast.LENGTH_LONG).show();

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
                break;
            case R.id.im_save:
                save();
                finish();
                break;
        }
    }
}


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.im_cancel:
//                finish();
//                break;
//            case R.id.im_save:
//
//                int a = adapter.getItemCount()+1;
//                String key = String.valueOf(a);
//                Pelanggan pelanggan = new Pelanggan(etNamaPelanggan.getText().toString(),
//                        Integer.parseInt(etNoHp.getText().toString()),
//                        etAlamat.getText().toString(),
//                        etCatatan.getText().toString());
//
//                FirebaseDatabase.getInstance().getReference().child("Pelanggan").child(key).setValue(pelanggan)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(TambahPelangganActivity.this, "Pelanggan berhasil diinput", Toast.LENGTH_SHORT).show();
//                                finish();
//                            }
//                        });
//
//                break;
//
//        }
//    }
