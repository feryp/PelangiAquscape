package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelangiaquscape.Adapter.StrukPenjualanAdapter;
import com.example.pelangiaquscape.Model.AkunToko;
import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.Model.Penjualan;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StrukPenjualanActivity extends AppCompatActivity {

    TextView tvNamaToko, tvAlamatToko, tvNoTeleponToko, tvNamaKasir, tvTglTransaksi, tvWaktuTransaksi, tvNoStruk, tvDiskon, tvTotalHarga, tvUangBayar, tvUangKembalian;
    RecyclerView rvItemBarang;
    FloatingActionButton fabCetak, fabBagikan;
    FloatingActionsMenu fabMenu;


    Penjualan penjualan;
    AkunToko akunToko;
    String namaKasir;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_struk_penjualan);

        //GET DATA
        Intent p = getIntent();
        penjualan = p.getParcelableExtra("penjualan");
        namaKasir = p.getStringExtra("namaKasir");
//        akunToko = p.getParcelableExtra("akuntoko");
//        key = p.getStringExtra("key");

        //INIT VIEW
        tvNamaToko = findViewById(R.id.tv_nama_toko);
        tvAlamatToko = findViewById(R.id.tv_alamat_toko);
        tvNoTeleponToko = findViewById(R.id.tv_no_telepon_toko);
        tvNamaKasir = findViewById(R.id.nama_kasir);
        tvTglTransaksi = findViewById(R.id.tanggal_transaksi);
        tvWaktuTransaksi = findViewById(R.id.waktu_transaksi);
        tvNoStruk = findViewById(R.id.no_struk);
        tvDiskon = findViewById(R.id.diskon_struk);
        tvTotalHarga = findViewById(R.id.total_harga_struk);
        tvUangBayar = findViewById(R.id.bayar_struk);
        tvUangKembalian = findViewById(R.id.kembalian_struk);

        fabCetak = findViewById(R.id.fab_action_print);
        fabBagikan = findViewById(R.id.fab_action_bagikan);
        fabMenu = findViewById(R.id.fab_menu);

        rvItemBarang = findViewById(R.id.rv_list_struk_barang);
        rvItemBarang.setHasFixedSize(true);
        rvItemBarang.setLayoutManager(new LinearLayoutManager(this));

        //SET TEXT


        if(namaKasir != null){
            tvNamaKasir.setText(namaKasir);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(penjualan.getTanggalPenjualan());

        Date date = calendar.getTime();

        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        String dateFormat = format.format(date);
        tvTglTransaksi.setText(dateFormat);

        tvNoStruk.setText(penjualan.getNoPenjualan());
        List<ItemKeranjang> listItemTransaksi = penjualan.getListItemKeranjang();
        double total = 0;
        for (ItemKeranjang itemKeranjang:listItemTransaksi){
            total = total + itemKeranjang.getTotalPrice();
        }

        BigDecimal bigDecimal = new BigDecimal(total);
        tvTotalHarga.setText(bigDecimal.toString());

        StrukPenjualanAdapter adapter = new StrukPenjualanAdapter(listItemTransaksi, this);
        rvItemBarang.setAdapter(adapter);

//        loadAkun();

        fabCetak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Cetak");
            }
        });

        fabBagikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Bagikan");
            }
        });
    }

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


//    void loadAkun(){
//        FirebaseDatabase.getInstance().getReference("AkunToko").child("1").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                AkunToko akunToko = dataSnapshot.getValue(AkunToko.class);
//
//                tvNamaToko.setText(akunToko.getNamaToko());
//                tvAlamatToko.setText(akunToko.getAlamat());
//                tvNoTeleponToko.setText(akunToko.getNoTelepon());
//
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//                tvNamaKasir.setText(user.getDisplayName());
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
}
