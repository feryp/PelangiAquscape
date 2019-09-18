package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.pelangiaquscape.Adapter.DetailProsesPembelianAdapter;
import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.Model.Pembelian;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DetailProsesPembelianActivity extends AppCompatActivity {


    TextView tvNoPesanan, tvMetodePembayaran, tvTanggalPesanan, tvPemesan, tvTotalHargaPembelian;
    Button btnLihatFaktur, btnKonfirmasi;
    SwitchCompat toogle_switch;
    ExpandableRelativeLayout cicilan_expand;
    TextInputLayout tvKeteranganCicilan, tvTanggalCicilan, tvJumlahCicilan;
    TextInputEditText etKeteranganCicilan, etTanggalCicilan, etJumlahCicilan;
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
        toogle_switch = findViewById(R.id.toogle_switch);
        tvTotalHargaPembelian = findViewById(R.id.tv_total_harga_pembelian);
        btnLihatFaktur = findViewById(R.id.btn_lihat_faktur);
        btnKonfirmasi = findViewById(R.id.btn_konfirmasi_pembelian);

        rvItem = findViewById(R.id.rv_list_detail_pembelian);
        rvItem.setHasFixedSize(true);
        rvItem.setLayoutManager(new LinearLayoutManager(this));


        // SET TEXT
        tvNoPesanan.setText(pembelian.getNoPesanan());
        switch(pembelian.getMetodePembayaran()){
            case 1:
                tvMetodePembayaran.setText("Bayar di tempat (COD)");
                break;
            case 2:
                tvMetodePembayaran.setText("Cicil");
                break;
        }

        btnLihatFaktur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lihat_faktur = new Intent(DetailProsesPembelianActivity.this, FakturPembelianActivity.class);
                startActivity(lihat_faktur);
            }
        });


        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(pembelian.getTanggalPesanan());

        Date date = c.getTime();

        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        String dateFormat = format.format(date);
        tvTanggalPesanan.setText(dateFormat);
        tvPemesan.setText(pembelian.getNamaPemasok());

        List<ItemKeranjang> listItem = pembelian.getListBarang();
        double total = 0;
        for(ItemKeranjang keranjang:listItem){
            total = total + keranjang.getTotalPrice();
        }
        BigDecimal decimal = new BigDecimal(total);
        tvTotalHargaPembelian.setText(decimal.toString());

        DetailProsesPembelianAdapter adapter = new DetailProsesPembelianAdapter(listItem, this);
        rvItem.setAdapter(adapter);

        toogle_switch.setChecked(false);
        cicilan_expand = findViewById(R.id.expand_cicilan);
        cicilan_expand.collapse();



        // SWITCH BUTTON
        toogle_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked){
                    cicilan_expand.collapse();
                } else {
                    cicilan_expand.expand();
                }
            }
        });
    }
}
