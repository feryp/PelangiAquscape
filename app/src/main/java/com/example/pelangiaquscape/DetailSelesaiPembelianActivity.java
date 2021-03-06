package com.example.pelangiaquscape;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelangiaquscape.Adapter.DetailSelesaiPembelianAdapter;
import com.example.pelangiaquscape.Model.AkunToko;
import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.Model.Pemasok;
import com.example.pelangiaquscape.Model.Pembelian;
import com.example.pelangiaquscape.Utils.FakturUtils;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DetailSelesaiPembelianActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView cancel;
    TextView tvNoPesanan, tvStatusPembelian, tvNoFaktur, tvTglPembelian, tvNamaPemasok, tvTotalHargaPembelian;
    Button btnBatalkan, btnLihatFaktur, btnSimpanCicilan;
    SwitchCompat toogle_switch;
//    ExpandableRelativeLayout cicilan_expand;
    TextInputLayout tvKeteranganCicilan, tvTanggalCicilan, tvJumlahCicilan;
    TextInputEditText etKeteranganCicilan, etTanggalCicilan, etJumlahCicilan;
    RecyclerView rvItem, rvCicilan;

    Pembelian pembelian;
    AkunToko toko;
    FakturUtils utils;
    String key;
    int no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_selesai_pembelian);

        // GET DATA
        Intent i = getIntent();
        pembelian = i.getParcelableExtra("value");
        key = i.getStringExtra("key");
        no = i.getIntExtra("no", -1);

        //INIT VIEW
        cancel = findViewById(R.id.im_cancel);
        tvNoPesanan = findViewById(R.id.tv_no_pesanan);
        tvStatusPembelian = findViewById(R.id.tv_status_pembelian);
        tvNoFaktur = findViewById(R.id.tv_no_faktur);
        tvTglPembelian = findViewById(R.id.tv_detail_tgl_pembelian);
        tvNamaPemasok = findViewById(R.id.tv_detail_nama_pemasok);
        tvTotalHargaPembelian = findViewById(R.id.tv_total_harga_pembelian);
//        btnBatalkan = findViewById(R.id.btn_batalkan_pembelian);
        btnLihatFaktur = findViewById(R.id.btn_lihat_faktur);
//        btnSimpanCicilan = findViewById(R.id.btn_simpan_cicilan);
//        toogle_switch = findViewById(R.id.toogle_switch);

        rvItem = findViewById(R.id.rv_list_detail_pembelian_selesai);
//        rvCicilan = findViewById(R.id.rv_cicilan);

        rvItem.setHasFixedSize(true);
        rvItem.setLayoutManager(new LinearLayoutManager(this));

        //SET LISTENER
        btnLihatFaktur.setOnClickListener(this);
        cancel.setOnClickListener(this);
//        btnBatalkan.setOnClickListener(this);

        tvNoPesanan.setText(pembelian.getNoPesanan());
//        switch (pembelian.getStatusPembelian()) {
//            case 1:
//                tvStatusPembelian.setText("Lunas");
//                break;
//            case 2:
//                tvStatusPembelian.setText("Belum Lunas");
//                break;
//        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(pembelian.getTanggalPesanan());

        Date date = calendar.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
        String dateFormat = simpleDateFormat.format(date);
        tvTglPembelian.setText(dateFormat);
        tvNamaPemasok.setText(pembelian.getNamaPemasok());
        tvNoFaktur.setText("00" + no);
        tvStatusPembelian.setText("Lunas");

        List<ItemKeranjang> listBarang = pembelian.getListBarang();
        double total = 0;
        for (ItemKeranjang keranjang : listBarang) {
            total = total + keranjang.getTotalPrice();

        }
//        BigDecimal decimal = new BigDecimal(total);
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        String totalHargaPembelian = decimalFormat.format(total);
        tvTotalHargaPembelian.setText("Rp. " + totalHargaPembelian);

        DetailSelesaiPembelianAdapter adapter = new DetailSelesaiPembelianAdapter(listBarang, this);
        rvItem.setAdapter(adapter);

//        toogle_switch.setChecked(false);
//        cicilan_expand = findViewById(R.id.expand_cicilan);
//        cicilan_expand.collapse();

//        //SWITCH BUTTON
//        toogle_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (!isChecked) {
//                    cicilan_expand.collapse();
//                } else {
//                    cicilan_expand.expand();
//                }
//            }
//        });

        loadToko();

    }

    void loadToko(){

        FirebaseDatabase.getInstance().getReference("AkunToko").child("1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                toko = dataSnapshot.getValue(AkunToko.class);
                Toast.makeText(DetailSelesaiPembelianActivity.this, "membuat faktur", Toast.LENGTH_SHORT).show();
                try {
                    if (toko != null && pembelian != null){
                        utils = new FakturUtils(pembelian, toko, getApplicationContext());
                        utils.createPdfForFaktur();
                        Toast.makeText(DetailSelesaiPembelianActivity.this, "faktur berhasil dibuat", Toast.LENGTH_SHORT).show();
                    } else {
                        utils = new FakturUtils(pembelian, toko);
                        utils.createPdfForFaktur();
                    }


                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(DetailSelesaiPembelianActivity.this,"pembuatan faktur gagal", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_cancel:
                finish();
                break;
            case R.id.btn_lihat_faktur:
                String fpath = "/sdcard/" + pembelian.getNoPesanan().replaceAll("[^a-zA-Z0-9]","") + ".pdf";
                File file = new File(fpath);
                openFaktur(file);
//                Intent lihat_faktur = new Intent(DetailSelesaiPembelianActivity.this, FakturPembelianActivity.class);
//                lihat_faktur.putExtra("pembelian", pembelian);
//                lihat_faktur.putExtra("no", no);
//                startActivity(lihat_faktur);
                break;
//            case R.id.btn_batalkan_pembelian:
//                Intent batalkan_pembelian = new Intent(DetailSelesaiPembelianActivity.this, BatalkanTransaksiPembelianActivity.class);
//                batalkan_pembelian.putExtra("pembelian",pembelian);
//                startActivity(batalkan_pembelian);
//                break;
        }
    }

    private void openFaktur(File file) {

        Uri path = FileProvider.getUriForFile(this,"com.example.pelangiaquscape.fileprovider", file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(path, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }
}
