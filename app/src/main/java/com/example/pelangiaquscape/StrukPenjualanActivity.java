package com.example.pelangiaquscape;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelangiaquscape.Adapter.StrukPenjualanAdapter;
import com.example.pelangiaquscape.Model.AkunToko;
import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.Model.Penjualan;
import com.example.pelangiaquscape.Utils.PDFUtils;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.pelangiaquscape.Const.FOLDER_PDF;
import static com.example.pelangiaquscape.Const.tempList;

public class StrukPenjualanActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvNamaToko, tvAlamatToko, tvNoTeleponToko, tvNamaKasir, tvTglTransaksi, tvJamTransaksi, tvWaktuTransaksi, tvNoStruk, tvDiskon, tvTotalHarga, tvUangBayar, tvUangKembalian;
    RecyclerView rvItemBarang;
    FloatingActionButton fabCetak;
    Penjualan penjualan;
    String namaKasir;
    String key;
    PDFUtils utils;

    DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
    List<ItemKeranjang> listKeranjang;

    double totalKembalian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_struk_penjualan);

        totalKembalian = 0;
        final Intent i = getIntent();


        //GET DATA
        Intent p = getIntent();
        penjualan = p.getParcelableExtra("penjualan");
        key = p.getStringExtra("key");
        namaKasir = p.getStringExtra("namaKasir");


        //INIT VIEW
        tvNamaToko = findViewById(R.id.tv_nama_toko);
        tvAlamatToko = findViewById(R.id.tv_alamat_toko);
        tvNoTeleponToko = findViewById(R.id.tv_no_telepon_toko);
        tvNamaKasir = findViewById(R.id.nama_kasir);
        tvTglTransaksi = findViewById(R.id.tanggal_transaksi);
        tvJamTransaksi = findViewById(R.id.waktu_transaksi);
        tvWaktuTransaksi = findViewById(R.id.waktu_transaksi);
        tvNoStruk = findViewById(R.id.no_struk);
        tvDiskon = findViewById(R.id.diskon_struk);
        tvTotalHarga = findViewById(R.id.total_harga_struk);
        tvUangBayar = findViewById(R.id.bayar_struk);
        tvUangKembalian = findViewById(R.id.kembalian_struk);
        fabCetak = findViewById(R.id.fab_action_print);


        rvItemBarang = findViewById(R.id.rv_list_struk_barang);
        rvItemBarang.setHasFixedSize(true);
        rvItemBarang.setLayoutManager(new LinearLayoutManager(this));

        //SET TEXT
        if(namaKasir != null){
            tvNamaKasir.setText(namaKasir);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(penjualan.getTanggalPenjualan());

        SimpleDateFormat formatTgl = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat formatJam = new SimpleDateFormat("HH:mm");
        Date da = calendar.getTime();
        String jamFormat = formatJam.format(da);
        String tglFormat = formatTgl.format(da);
        tvTglTransaksi.setText(tglFormat);
        tvJamTransaksi.setText(jamFormat + " WIB");

        tvNoStruk.setText(penjualan.getNoPenjualan());
        List<ItemKeranjang> listItemTransaksi = penjualan.getListItemKeranjang();
        double total = 0;
        for (ItemKeranjang itemKeranjang:listItemTransaksi){
            total = total + itemKeranjang.getTotalPrice();
        }

        String format = decimalFormat.format(penjualan.getUangBayar());
        tvUangBayar.setText("Rp. " + format);

        String formatDiskon = decimalFormat.format(penjualan.getDiskon()).length()<1?"0":decimalFormat.format(penjualan.getDiskon());
        tvDiskon.setText("Rp. " + formatDiskon);

        String formatKembalian = decimalFormat.format(penjualan.getUangKembalian());
        tvUangKembalian.setText("Rp. " + formatKembalian);
//
//        double kembalian = 0;
//        totalKembalian = total-bayar;
//        tvUangKembalian.setText("Rp. " + kembalian);
//        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


        String totalHargaPesanan = decimalFormat.format(total);
        tvTotalHarga.setText("Rp. " + totalHargaPesanan);

        StrukPenjualanAdapter adapter = new StrukPenjualanAdapter(listItemTransaksi, this);
        rvItemBarang.setAdapter(adapter);

        loadAkun();

        fabCetak.setOnClickListener(this);

    }

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }




    void loadAkun(){

        FirebaseDatabase.getInstance().getReference("AkunToko").child("1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AkunToko toko= dataSnapshot.getValue(AkunToko.class);

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
        switch (v.getId()){
            case R.id.fab_action_print:
                try {
//                    utils.createPdfForReceipt();
                    showToast("Cetak");
                } catch (Exception e){
                    e.printStackTrace();
                    showToast("Tidak Bisa Di Cetak");
                }
                break;
        }
    }



}
