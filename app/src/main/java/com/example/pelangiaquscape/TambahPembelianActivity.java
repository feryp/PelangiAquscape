package com.example.pelangiaquscape;

import android.app.Activity;
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
import com.example.pelangiaquscape.Model.Pelanggan;
import com.example.pelangiaquscape.Model.Pemasok;
import com.example.pelangiaquscape.Model.Pembelian;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TambahPembelianActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView cancel, save;
    private Button btnTambahBarang;
    private RadioGroup radioMetodePembayaran;
    private RadioButton radioButton;
    private TextInputEditText etNoPesanan, etNamaPemasok, etTglPesanan;
    private List<Barang> barangList;
    private FirebaseDatabase databasePembelian;
    private DatabaseReference reference;
    private Pembelian prosesPembelian;

    private RecyclerView rvItem;

    private SharedPreferences preferences;

    String DEBUG_TAG = "TESTMOTION";


    private ItemPembelianAdapter adapter;

    final int REQUEST_PEMBELIAN = 15;
    final int REQUEST_PELANGGAN = 30;
    final String PACKAGE_NAME = "com.example.pelangiaquascape.";
    ItemPembelianDbHelper helper;
    List<ItemKeranjang> listKeranjang;

    /*
    1 = cod
    2 = cicil
     */
    int KODE_UNTUK_METODE_PEMBAYARAN = 1;

    Pembelian pembelian;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pembelian);

        //INIT VIEW
        cancel = findViewById(R.id.im_cancel);
        save = findViewById(R.id.im_save);
        etNoPesanan = findViewById(R.id.et_no_pesanan);
        etNamaPemasok = findViewById(R.id.et_pemasok);
        etTglPesanan = findViewById(R.id.et_tgl_pesanan);
        btnTambahBarang = findViewById(R.id.btn_tambah_barang);
        rvItem = findViewById(R.id.rv_form_barang_pesanan);
        radioMetodePembayaran = findViewById(R.id.radio_metode_pembayaran);
        radioMetodePembayaran.check(R.id.cod);
        rvItem.setHasFixedSize(true);
        rvItem.setLayoutManager(new LinearLayoutManager(this));
        rvItem.setAdapter(adapter);

        // ON TOUCH
        etNamaPemasok.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Intent i = new Intent(TambahPembelianActivity.this, MitraBisnisActivity.class);
                    i.putExtra("fromTambahPembelian", true);
                    startActivityForResult(i, REQUEST_PELANGGAN);
                }
            }
        });

        //INIT FIREBASE
        databasePembelian = FirebaseDatabase.getInstance();
        reference = databasePembelian.getReference("Pembelian");


        //SET LISTENER
        btnTambahBarang.setOnClickListener(this);
        cancel.setOnClickListener(this);
        save.setOnClickListener(this);

        // GET PARCELABLE
        Intent i = getIntent();
        pembelian = i.getParcelableExtra("value");
        key = i.getStringExtra("key");


        // GET SHARED PREFERENCES
        preferences = getSharedPreferences(PACKAGE_NAME + "PEMBELIAN_KEY", Context.MODE_PRIVATE);


        // GET LIST FROM DATABASE
        try {
            helper = new ItemPembelianDbHelper(this);
            listKeranjang = helper.selectAll();
        } catch (SQLiteException ex) {
            helper = new ItemPembelianDbHelper(this);
            helper.onUpgrade(helper.getReadableDatabase(), helper.getReadableDatabase().getVersion(), helper.getReadableDatabase().getVersion() + 1);
            listKeranjang = helper.selectAll();
        }

        // UPDATE DATA
        if (key != null) {
            setValueFromPembelian();
        }

        // SET ADAPTER
        adapter = new ItemPembelianAdapter(listKeranjang, this);
        rvItem.setAdapter(adapter);

    }

    public void onRadioButtonClick(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.cod:
                KODE_UNTUK_METODE_PEMBAYARAN = 1;
                break;
            case R.id.cicil:
                KODE_UNTUK_METODE_PEMBAYARAN = 2;
                break;
        }

        Toast.makeText(this, "Pilih " + ((RadioButton) view).getText(), Toast.LENGTH_SHORT).show();
    }

    private void save() {
//        String noPesanan, long tanggalPesanan, String namaPemasok,
//                String metodePembayaran, List<Barang> listBarang, boolean proses


        Calendar cal = Calendar.getInstance();
        SimpleDateFormat fmt = new SimpleDateFormat("ddMMyyyy/hhmmss");
        Date date = cal.getTime();
        String formatedDate = fmt.format(date);
        String noPembelian = "PO/" + formatedDate;
//        String noPesanan = etNoPesanan.getText().toString();
        long tanggalPesanan = cal.getTimeInMillis();
        String namaPemasok = etNamaPemasok.getText().toString();
        int metodePembayaran = KODE_UNTUK_METODE_PEMBAYARAN;
        List<ItemKeranjang> daftarPembelian = listKeranjang;
        boolean proses = true;


        Pembelian p = new Pembelian(noPembelian, tanggalPesanan, namaPemasok, metodePembayaran, daftarPembelian, proses);

        if (key != null) {
            reference.child(key).setValue(p).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(TambahPembelianActivity.this, "Update Pesanan berhasil", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            reference.push().setValue(p).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(TambahPembelianActivity.this, "Pesanan Pembelian berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void setValueFromPembelian() {

//        save = findViewById(R.id.im_save);
//        etNoPesanan = findViewById(R.id.et_no_pesanan);
//        etNamaPemasok = findViewById(R.id.et_pemasok);
//        etTglPesanan = findViewById(R.id.et_tgl_pesanan);
//        btnTambahBarang = findViewById(R.id.btn_tambah_barang);
//        rvItem = findViewById(R.id.rv_form_barang_pesanan);
//        radioMetodePembayaran = findViewById(R.id.radio_metode_pembayaran);
//        radioMetodePembayaran.check(R.id.cod);
//        rvItem.setHasFixedSize(true);
//        rvItem.setLayoutManager(new LinearLayoutManager(this));
//        rvItem.setAdapter(adapter);

        etNoPesanan.setText(pembelian.getNoPesanan());
        etNamaPemasok.setText(pembelian.getNamaPemasok());
        int id = 0;
        if (pembelian.getMetodePembayaran() == 1) {
            id = R.id.cod;
        } else if (pembelian.getMetodePembayaran() == 2) {
            id = R.id.cicil;
        }
        radioMetodePembayaran.check(id);
        listKeranjang = pembelian.getListBarang();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        System.out.println("REQUEST CODE " + requestCode);
        System.out.println("RESULT CODE " + resultCode);
        if (requestCode == REQUEST_PEMBELIAN) {
            if (resultCode == RESULT_OK) {
                listKeranjang = helper.selectAll();
                adapter.setListItemBarang(listKeranjang);
                adapter.notifyDataSetChanged();
            }
        }

        if (requestCode == REQUEST_PELANGGAN) {
            if (resultCode == RESULT_OK) {
                Pemasok pelanggan = data.getParcelableExtra("pemasok");
                etNamaPemasok.setText(pelanggan.getNamaPemasok());
            }
        }
    }

    // MENAMBAHKAN LIST BARANG
    private void tambahBarang() {
        Intent tambahBarang = new Intent(TambahPembelianActivity.this, TransaksiActivity.class);
        tambahBarang.putExtra("fromTambahPembelian", true);
        startActivityForResult(tambahBarang, REQUEST_PEMBELIAN);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_cancel:
                if (key != null) {
                    finish();
                } else {
                    clearAllData();
                    finish();
                }

                break;
            case R.id.im_save:
                showConfirmationDialog();
                finish();
                break;
            case R.id.btn_tambah_barang:
                tambahBarang();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (listKeranjang.size() > 0) {
            showBackDialog();
        } else {
            super.onBackPressed();
        }

    }

    private void showBackDialog() {

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage("Apakah anda ingin membatalkan pesanan pembelian ini ? ");
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        clearAllData();

                        Toast.makeText(getBaseContext(), "Pesanan Dibatalkan", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        setResult(Activity.RESULT_OK);
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

    public void showConfirmationDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage("Apakah data Pesanan sudah benar ? ");
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        save();
                        Toast.makeText(TambahPembelianActivity.this, "Pesanan telah tersimpan", Toast.LENGTH_SHORT).show();
                        clearAllData();
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

    private void clearAllData() {
        SharedPreferences.Editor edit = preferences.edit();
        edit.clear();
        edit.apply();

        ItemPembelianDbHelper helper = new ItemPembelianDbHelper(TambahPembelianActivity.this);
        helper.deleteAll();
    }
}
