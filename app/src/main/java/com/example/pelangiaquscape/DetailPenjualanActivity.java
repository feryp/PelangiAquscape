package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pelangiaquscape.Adapter.DetailPenjualanAdapter;
import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.Model.Penjualan;
import com.example.pelangiaquscape.ViewHolder.DetailPenjualanViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DetailPenjualanActivity extends AppCompatActivity {

    TextView tvNoStruk, tvKeteranganPembayaran, tvWaktuPenjualan, tvDiskon, tvNamaKasir, tvNamaPelanggan, tvTotalHargaPenjualan;
    Button btnLihatStruk;
    RecyclerView rvDetailItem;

    Penjualan penjualan;
    String key;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penjualan);

        //GET DATA
        Intent p = getIntent();
        penjualan = p.getParcelableExtra("penjualan");
        key = p.getStringExtra("key");

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

        List<ItemKeranjang> listItemPenjualan = penjualan.getListItemKeranjang();
        double total = 0;
        for (ItemKeranjang keranjang:listItemPenjualan){
            total = total + keranjang.getTotalPrice();
        }

        BigDecimal decimal = new BigDecimal(total);
        tvTotalHargaPenjualan.setText(decimal.toString());

        DetailPenjualanAdapter adapter = new DetailPenjualanAdapter(listItemPenjualan, this);
        rvDetailItem.setAdapter(adapter);

        btnLihatStruk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lihatStruk = new Intent(DetailPenjualanActivity.this, StrukPenjualanActivity.class);
                startActivity(lihatStruk);
            }
        });

    }

}
