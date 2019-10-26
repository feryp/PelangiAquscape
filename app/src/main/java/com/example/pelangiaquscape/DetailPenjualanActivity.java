package com.example.pelangiaquscape;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelangiaquscape.Adapter.DetailPenjualanAdapter;
import com.example.pelangiaquscape.Model.AkunToko;
import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.Model.Penjualan;
import com.example.pelangiaquscape.Model.User;
import com.example.pelangiaquscape.Utils.PDFUtils;
import com.example.pelangiaquscape.ViewHolder.DetailPenjualanViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DetailPenjualanActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvNoStruk, tvKeteranganPembayaran, tvWaktuPenjualan, tvDiskon, tvNamaKasir, tvNamaPelanggan, tvTotalHargaPenjualan;
    Button btnLihatStruk;
    RecyclerView rvDetailItem;

    Penjualan penjualan;
    String key;


    User user;
    ProgressDialog dialog;

    DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
    PDFUtils utils;
    AkunToko toko;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penjualan);

        //GET DATA
        Intent p = getIntent();
        penjualan = p.getParcelableExtra("penjualan");
        key = p.getStringExtra("key");

        dialog = new ProgressDialog(this);
        //INIT VIEW
        tvNoStruk = findViewById(R.id.tv_no_struk_detail_penjualan);
        tvKeteranganPembayaran = findViewById(R.id.tv_keterangan_pembayaran_detail);
        tvWaktuPenjualan = findViewById(R.id.tv_waktu_detail_penjualan);
        tvDiskon = findViewById(R.id.tv_detail_diskon);
        tvNamaKasir = findViewById(R.id.tv_detail_nama_kasir);
        tvNamaPelanggan = findViewById(R.id.tv_detail_nama_customer);
        tvTotalHargaPenjualan = findViewById(R.id.tv_detail_total_penjualan);
        btnLihatStruk = findViewById(R.id.btn_detail_lihat_struk);

        rvDetailItem = findViewById(R.id.rv_list_detail_penjualan);
        rvDetailItem.setHasFixedSize(true);
        rvDetailItem.setLayoutManager(new LinearLayoutManager(this));


        //SET TEXT
        tvNoStruk.setText(penjualan.getNoPenjualan());
        tvKeteranganPembayaran.setText("Tunai");

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(penjualan.getTanggalPenjualan());

        Date date = c.getTime();

        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        String dateFormat = format.format(date);
        tvWaktuPenjualan.setText(dateFormat);

        tvNamaKasir.setText(penjualan.getNamaPenjual());
        tvNamaPelanggan.setText(penjualan.getNamaKustomer());

        String formatDiskon = decimalFormat.format(penjualan.getDiskon()).length() < 1 ? "0" : decimalFormat.format(penjualan.getDiskon());
        tvDiskon.setText("Rp. " + formatDiskon);


        List<ItemKeranjang> listItemPenjualan = penjualan.getListItemKeranjang();
        double total = 0;
        for (ItemKeranjang keranjang : listItemPenjualan) {
            total = total + keranjang.getTotalPrice();
        }

//        BigDecimal decimal = new BigDecimal(total);

        String totalHargaPenjualan = decimalFormat.format(total);
        tvTotalHargaPenjualan.setText("Rp. " + totalHargaPenjualan);


        DetailPenjualanAdapter adapter = new DetailPenjualanAdapter(listItemPenjualan, this);
        rvDetailItem.setAdapter(adapter);

        btnLihatStruk.setOnClickListener(this);


        dialog.show();

        loadUser(penjualan.getNamaPenjual());


    }

    void loadUser(String key) {
        FirebaseDatabase.getInstance().getReference("User").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    tvNamaKasir.setText(user.getUsername());

                }

                FirebaseDatabase.getInstance().getReference("AkunToko").child("1").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        toko = dataSnapshot.getValue(AkunToko.class);
                        Toast.makeText(DetailPenjualanActivity.this, "membuat struk otomatis", Toast.LENGTH_SHORT).show();
                        try {
                            if(toko!= null && penjualan != null && user != null){
                                utils = new PDFUtils(penjualan, toko, user.getUsername());
                                utils.createPdfForReceipt();
                            }else{
                                utils = new PDFUtils(penjualan, toko);
                                utils.createPdfForReceipt();

                            }


                            Toast.makeText(DetailPenjualanActivity.this, "struk berhasil dibuat", Toast.LENGTH_SHORT).show();
                        }catch(Exception e){
                            e.printStackTrace();
                            Toast.makeText(DetailPenjualanActivity.this, "pembuatan struk gagal", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_detail_lihat_struk:
                String fpath = "/sdcard/" + penjualan.getNoPenjualan() + ".pdf";
                File file = new File(fpath);
                openReceipt(file);
                break;



//                Intent lihatStruk = new Intent(DetailPenjualanActivity.this, StrukPenjualanActivity.class);
//                lihatStruk.putExtra("penjualan", penjualan);
//                if(user != null) {
//                    lihatStruk.putExtra("namaKasir", user.getUsername());
//                }
//                startActivity(lihatStruk);

        }
    }


    void openReceipt(File file) {

        Uri path = FileProvider.getUriForFile(this, "com.example.pelangiaquscape.fileprovider", file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(path, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }
}
