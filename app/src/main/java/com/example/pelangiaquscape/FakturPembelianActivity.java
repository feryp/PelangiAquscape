package com.example.pelangiaquscape;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelangiaquscape.Adapter.FakturPembelianAdapter;
import com.example.pelangiaquscape.Model.AkunToko;
import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.Model.Pemasok;
import com.example.pelangiaquscape.Model.Pembelian;
import com.example.pelangiaquscape.Utils.FakturUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ibm.icu.text.IDNA;
import com.ibm.icu.text.RuleBasedNumberFormat;
import com.ibm.icu.util.Currency;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FakturPembelianActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvNamaToko, tvAlamatToko, tvNoTeleponToko, tvNoFaktur, tvNoPesanan, tvTglFaktur, tvNamaPemasok, tvTotal, tvTerbilang, tvKeterangan;
    RecyclerView rvItemPesanan;
    FloatingActionButton fabCetak;

    Pembelian pembelian;
    AkunToko toko;
    Pemasok pemasok;
    FakturUtils utils;
    String key;
    int no;

    DecimalFormat decimalFormat = new DecimalFormat("#,###.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faktur_pembelian);

        //GET DATA
        Intent p = getIntent();
        pembelian = p.getParcelableExtra("pembelian");
        key = p.getStringExtra("key");
        no = p.getIntExtra("no", no);

        //INIT VIEW
        tvNamaToko = findViewById(R.id.faktur_nama_toko);
        tvAlamatToko = findViewById(R.id.faktur_alamat_toko);
        tvNoTeleponToko = findViewById(R.id.faktur_no_telepon_toko);
        tvNoFaktur = findViewById(R.id.no_faktur);
        tvNoPesanan = findViewById(R.id.no_pesanan_faktur);
        tvTglFaktur = findViewById(R.id.tgl_faktur);
        tvNamaPemasok = findViewById(R.id.faktur_nama_pemasok);
        tvTotal = findViewById(R.id.total_faktur);
        tvTerbilang = findViewById(R.id.terbilang_nominal);
        tvKeterangan = findViewById(R.id.keterangan_faktur);
        fabCetak = findViewById(R.id.fab_print_faktur);

        rvItemPesanan = findViewById(R.id.rv_item_faktur);
        rvItemPesanan.setHasFixedSize(true);
        rvItemPesanan.setLayoutManager(new LinearLayoutManager(this));

        //SET TEXT
        tvNoFaktur.setText("00" + no);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(pembelian.getTanggalPesanan());

        List<ItemKeranjang> listItemPembelian = pembelian.getListBarang();
        double total = 0;
        for (ItemKeranjang itemKeranjang : listItemPembelian) {
            total = total + itemKeranjang.getTotalPrice();
        }

        FakturPembelianAdapter adapter = new FakturPembelianAdapter(listItemPembelian, this);
        rvItemPesanan.setAdapter(adapter);

        String totalPembelian = decimalFormat.format(total);
        tvTotal.setText("Rp. " + totalPembelian);

        Locale local = new Locale("id_ID");
        RuleBasedNumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat(local,
                RuleBasedNumberFormat.SPELLOUT);

        String terbilang = ruleBasedNumberFormat.format(total);
        tvTerbilang.setText(terbilang + " rupiah");


        SimpleDateFormat formatTgl = new SimpleDateFormat("dd MMMM yyyy");
        Date da = calendar.getTime();
        String tglFormat = formatTgl.format(da);
        tvTglFaktur.setText(tglFormat);
        tvNoPesanan.setText(pembelian.getNoPesanan());
        tvNamaPemasok.setText(pembelian.getNamaPemasok());

        fabCetak.setOnClickListener(this);

        loadAkunToko();
    }

//    private String convertIntoWords(String totalPembelian) {
//        Locale locale = new Locale(totalPembelian);
//        RuleBasedNumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat(locale,
//                RuleBasedNumberFormat.SPELLOUT);
//        return ruleBasedNumberFormat.format(totalPembelian);
//
//
//    }

    private void loadAkunToko() {
        FirebaseDatabase.getInstance().getReference("AkunToko").child("1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                toko = dataSnapshot.getValue(AkunToko.class);
                Toast.makeText(FakturPembelianActivity.this, "membuat faktur", Toast.LENGTH_SHORT).show();
                try {
                    if (toko != null && pembelian != null){
                        utils = new FakturUtils(pembelian, toko, getApplicationContext());
                        utils.createPdfForFaktur();
                        Toast.makeText(FakturPembelianActivity.this, "faktur berhasil dibuat", Toast.LENGTH_SHORT).show();
                    } else {
                        utils = new FakturUtils(pembelian, toko);
                        utils.createPdfForFaktur();
                    }

                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(FakturPembelianActivity.this,"pembuatan faktur gagal", Toast.LENGTH_SHORT).show();
                }


                tvNamaToko.setText(toko.getNamaToko());
                tvAlamatToko.setText(toko.getAlamat());
                tvNoTeleponToko.setText(toko.getNoTelepon());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_print_faktur:
                String fpath = "/sdcard/" + pembelian.getNoPesanan().replaceAll("[^a-zA-Z0-9]", "") + ".pdf";
                File file = new File(fpath);
                openReceipt(file);

                break;
        }

    }

    private void openReceipt(File file) {
        Uri path = FileProvider.getUriForFile(this, "com.example.pelangiaquscape.fileprovider", file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(path, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }
}
