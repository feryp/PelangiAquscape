package com.example.pelangiaquscape;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.pelangiaquscape.Adapter.ItemPembelianAdapter;
import com.example.pelangiaquscape.Database.ItemKeranjangContract;
import com.example.pelangiaquscape.Database.ItemKeranjangDbHelper;
import com.example.pelangiaquscape.Database.ItemPembelianDbHelper;
import com.example.pelangiaquscape.Model.Barang;
import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.Model.Pembelian;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class TambahPembelianActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView cancel, save;
    Button btnTambahBarang;
    RadioGroup radioMetodePembayaran;
    RadioButton radioButton;
    TextInputEditText etNoPesanan, etNamaPemasok, etTglPesanan;
    List<Barang> barangList;
    FirebaseDatabase databasePembelian;
    DatabaseReference reference;
    Pembelian prosesPembelian;
    String id;
    RecyclerView rvItem;
    int idProsesPembelian;
    SharedPreferences preferences;

    String DEBUG_TAG = "TESTMOTION";


    ItemPembelianAdapter adapter;

    final int REQUEST_PEMBELIAN = 15;
    final String PACKAGE_NAME = "com.example.pelangiaquascape.";
    ItemPembelianDbHelper helper;
    List<ItemKeranjang> listKeranjang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pembelian);


        preferences = getSharedPreferences(PACKAGE_NAME+"PEMBELIAN_KEY", Context.MODE_PRIVATE);
        // GET LIST FROM DATABASE



        try{
            helper = new ItemPembelianDbHelper(this);
            listKeranjang = helper.selectAll();
        }catch(SQLiteException ex){
            helper = new ItemPembelianDbHelper(this);
            helper.onUpgrade(helper.getReadableDatabase(),helper.getReadableDatabase().getVersion(),helper.getReadableDatabase().getVersion()+1);
            listKeranjang = helper.selectAll();
        }

        adapter = new ItemPembelianAdapter(listKeranjang, this);


        // END GET LIST FROM DATABASE

        // GET ID FROM PENJUALAN


        // END GET ID FROM PENJUALAN

        //INIT VIEW
        cancel = findViewById(R.id.im_cancel);
        save = findViewById(R.id.im_save);
        etNoPesanan = findViewById(R.id.et_no_pesanan);
        etNamaPemasok = findViewById(R.id.et_pemasok);
        etTglPesanan = findViewById(R.id.et_tgl_pesanan);
        btnTambahBarang = findViewById(R.id.btn_tambah_barang);
        rvItem = findViewById(R.id.rv_form_barang_pesanan);
        rvItem.setHasFixedSize(true);
        rvItem.setLayoutManager(new LinearLayoutManager(this));
        rvItem.setAdapter(adapter);

        //INIT FIREBASE
        databasePembelian = FirebaseDatabase.getInstance();
        reference = databasePembelian.getReference("Pembelian");
        prosesPembelian = new Pembelian();


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
                getValues();
                reference.child(String.valueOf(id)).setValue(prosesPembelian);
                Toast.makeText(TambahPembelianActivity.this, "List Pembelian telah ditambah", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getValues(){
        prosesPembelian.setNoPesanan(etNoPesanan.getText().toString());
        prosesPembelian.setNamaPemasok(etNamaPemasok.getText().toString());
        prosesPembelian.setTanggalPesanan(Long.parseLong(etTglPesanan.getText().toString()));
        prosesPembelian.setMetodePembayaran(radioButton.getText().toString());
        prosesPembelian.setListBarang(barangList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        System.out.println("REQUEST CODE " + requestCode);
        System.out.println("RESULT CODE " + resultCode);
        if(requestCode == REQUEST_PEMBELIAN){
            if(resultCode == RESULT_OK){
                listKeranjang = helper.selectAll();
                adapter.setListItemBarang(listKeranjang);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void tambahBarang() {
        Intent tambahBarang = new Intent(TambahPembelianActivity.this, TransaksiActivity.class);
        tambahBarang.putExtra("fromTambahPembelian", true);
        startActivityForResult(tambahBarang, REQUEST_PEMBELIAN);
//        Toast.makeText(TambahPembelianActivity.this, "tambah barang", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_cancel:
                ItemPembelianDbHelper hp = new ItemPembelianDbHelper(this);
                hp.deleteAll();
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

    @Override
    public void onBackPressed() {
        if(listKeranjang.size() > 0 ){
            showBackDialog();
        }else{
            super.onBackPressed();
        }

    }

    private void showBackDialog(){

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Hapus Pesanan");
        alertDialog.setMessage("Apakah anda ingin membatalkan pesanan pembelian ini ? ");
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences.Editor edit = preferences.edit();
                        edit.clear();
                        edit.apply();


                        Toast.makeText(getBaseContext(), "Pesanan Dibatalkan", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        ItemPembelianDbHelper helper = new ItemPembelianDbHelper(TambahPembelianActivity.this);
                        helper.deleteAll();

                        finish();
                    }
                });
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
