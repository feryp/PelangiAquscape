package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelangiaquscape.Adapter.BatalkanTransaksiAdapter;
import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.Model.Pembelian;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BatalkanTransaksiPembelianActivity extends AppCompatActivity implements View.OnClickListener{


   private ImageView cancel;
   private Button btnBatalkanTransaksi;
   private RecyclerView rvItemBarang;
   private TextView tvTotalHargaPesanan;
   private RadioGroup radioAlasanPembatalan;
   private RadioButton radioButton;


    Pembelian pembelian;
    String key;

    int ALASAN_PEMBATALAN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batalkan_transaksi_pembelian);

        // GET DATA
        Intent i = getIntent();
        pembelian = i.getParcelableExtra("value");
        key = i.getStringExtra("key");

        //INIT VIEW
        cancel = findViewById(R.id.im_cancel);
        btnBatalkanTransaksi = findViewById(R.id.btn_batalkan_transaksi);
        tvTotalHargaPesanan = findViewById(R.id.tv_total_harga_pesanan);
        radioAlasanPembatalan = findViewById(R.id.radio_alasan_pembatalan);
        radioAlasanPembatalan.check(R.id.pengembalian_barang);
        rvItemBarang = findViewById(R.id.rv_list_detail_barang);
        rvItemBarang.setHasFixedSize(true);
        rvItemBarang.setLayoutManager(new LinearLayoutManager(this));


        //SET LISTENER
        btnBatalkanTransaksi.setOnClickListener(this);
        cancel.setOnClickListener(this);

//        List<ItemKeranjang> listItem = pembelian.getListBarang();
//        double total = 0;
//        for (ItemKeranjang keranjang : listItem){
//            total = total + keranjang.getTotalPrice();
//        }
//
//        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
//        String totalHargaPesanan = decimalFormat.format(total);
//        tvTotalHargaPesanan.setText("Rp. " + totalHargaPesanan);
//
//        BatalkanTransaksiAdapter adapter = new BatalkanTransaksiAdapter(listItem, this);
//        rvItemBarang.setAdapter(adapter);
    }

    public void onRadioButtonClick(View view){

//        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.pengembalian_barang:
                ALASAN_PEMBATALAN = 1;
                break;
            case R.id.barang_rusak:
                ALASAN_PEMBATALAN = 2;
                break;
            case R.id.pembatalan_pesanan:
                ALASAN_PEMBATALAN = 3;
                break;
            case R.id.lainnya:
                ALASAN_PEMBATALAN = 4;
                break;
        }

        Toast.makeText(this, "Pilih " + ((RadioButton) view).getText(), Toast.LENGTH_SHORT).show();

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.im_cancel:
                finish();
                break;
            case R.id.btn_batalkan_transaksi:
                Intent batalkanTransaksi = new Intent(BatalkanTransaksiPembelianActivity.this, DetailTransaksiDibatalkanActivity.class);
                startActivity(batalkanTransaksi);
                break;
        }
    }
}
