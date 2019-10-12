package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.pelangiaquscape.Adapter.FakturPembelianAdapter;
import com.example.pelangiaquscape.Model.AkunToko;
import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.Model.Pemasok;
import com.example.pelangiaquscape.Model.Pembelian;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ibm.icu.text.IDNA;
import com.ibm.icu.text.RuleBasedNumberFormat;
import com.ibm.icu.util.Currency;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FakturPembelianActivity extends AppCompatActivity {

    TextView tvNamaToko, tvAlamatToko, tvNoTeleponToko, tvNoFaktur, tvNoPesanan, tvTglFaktur, tvNamaPemasok, tvTotal,tvTerbilang, tvKeterangan;
    RecyclerView rvItemPesanan;
    FloatingActionButton fabCetak;

    Pembelian pembelian;
    Pemasok pemasok;
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

        rvItemPesanan = findViewById(R.id.rv_item_faktur);
        rvItemPesanan.setHasFixedSize(true);
        rvItemPesanan.setLayoutManager(new LinearLayoutManager(this));

        //SET TEXT
        tvNoFaktur.setText("00"+no);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(pembelian.getTanggalPesanan());

        List<ItemKeranjang> listItemPembelian = pembelian.getListBarang();
        double total = 0;
        for (ItemKeranjang itemKeranjang:listItemPembelian){
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
        tvTerbilang.setText(terbilang);


        SimpleDateFormat formatTgl = new SimpleDateFormat("dd MMMM yyyy");
        Date da = calendar.getTime();
        String tglFormat = formatTgl.format(da);
        tvTglFaktur.setText(tglFormat);
        tvNoPesanan.setText(pembelian.getNoPesanan());
        tvNamaPemasok.setText(pembelian.getNamaPemasok());

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
                AkunToko akunToko = dataSnapshot.getValue(AkunToko.class);

                tvNamaToko.setText(akunToko.getNamaToko());
                tvAlamatToko.setText(akunToko.getAlamat());
                tvNoTeleponToko.setText(akunToko.getNoTelepon());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
