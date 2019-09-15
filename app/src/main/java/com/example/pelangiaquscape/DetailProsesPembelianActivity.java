package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.pelangiaquscape.Adapter.DetailProsesPembelianAdapter;
import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.Model.Pembelian;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DetailProsesPembelianActivity extends AppCompatActivity {


    TextView tvNoPesanan, tvMetodePembayaran, tvTanggalPesanan, tvPemesan;
    RecyclerView rvItem;

    Pembelian pembelian;
    String key;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_proses_pembelian);

        // GET DATA
        Intent i = getIntent();
        pembelian = i.getParcelableExtra("value");
        key = i.getStringExtra("key");


        // INIT VIEW
        tvNoPesanan = findViewById(R.id.tv_no_pesanan);
        tvMetodePembayaran = findViewById(R.id.tv_status_pembayaran);
        tvTanggalPesanan = findViewById(R.id.tv_detail_tgl_pembelian);
        tvPemesan = findViewById(R.id.tv_detail_nama_pemasok);

        rvItem = findViewById(R.id.rv_list_detail_pembelian);
        rvItem.setHasFixedSize(true);
        rvItem.setLayoutManager(new LinearLayoutManager(this));


        // SET TEXT
        tvNoPesanan.setText(pembelian.getNoPesanan());
        switch(pembelian.getMetodePembayaran()){
            case 1:
                tvMetodePembayaran.setText("COD");
                break;
            case 2:
                tvMetodePembayaran.setText("Cicil");
                break;
        }

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(pembelian.getTanggalPesanan());

        Date date = c.getTime();

        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        String dateFormat = format.format(date);
        tvTanggalPesanan.setText(dateFormat);

        tvPemesan.setText("Pelangi Aquascape");

        List<ItemKeranjang> listItem = pembelian.getListBarang();


        DetailProsesPembelianAdapter adapter = new DetailProsesPembelianAdapter(listItem, this);
        rvItem.setAdapter(adapter);

    }

}
