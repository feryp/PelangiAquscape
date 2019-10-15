package com.example.pelangiaquscape;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelangiaquscape.Adapter.DetailProsesPembelianAdapter;
import com.example.pelangiaquscape.Model.Barang;
import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.Model.Pembelian;
import com.example.pelangiaquscape.Model.Penyimpanan;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DetailProsesPembelianActivity extends AppCompatActivity implements View.OnClickListener {


    ImageView cancel;
    TextView tvNoPesanan, tvMetodePembayaran, tvTanggalPesanan, tvPemesan, tvTotalHargaPembelian;
    Button btnLihatFaktur, btnKonfirmasi, btnSimpanCicilan;
    SwitchCompat toogle_switch;
//    ExpandableRelativeLayout cicilan_expand;
    TextInputLayout tvKeteranganCicilan, tvTanggalCicilan, tvJumlahCicilan;
    TextInputEditText etKeteranganCicilan, etTanggalCicilan, etJumlahCicilan;
    RecyclerView rvItem, rvCicilan;

    Pembelian pembelian;
    String key;

    String PACKAGE_NAME = "com.example.pelangiaquascape.";

    SharedPreferences sharedPref;
    List<ItemKeranjang> listItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_proses_pembelian);

        // GET DATA
        Intent i = getIntent();
        pembelian = i.getParcelableExtra("value");
        key = i.getStringExtra("key");

        // SHARED PREFERENCE
        sharedPref = getSharedPreferences(PACKAGE_NAME + "PEMBELIAN_KEY", Context.MODE_PRIVATE);

        // INIT VIEW
        cancel = findViewById(R.id.im_cancel);
        tvNoPesanan = findViewById(R.id.tv_no_pesanan);
        tvMetodePembayaran = findViewById(R.id.tv_status_pembayaran);
        tvTanggalPesanan = findViewById(R.id.tv_detail_tgl_pembelian);
        tvPemesan = findViewById(R.id.tv_detail_nama_pemasok);
        toogle_switch = findViewById(R.id.toogle_switch);
        tvTotalHargaPembelian = findViewById(R.id.tv_total_harga_pembelian);
        btnLihatFaktur = findViewById(R.id.btn_lihat_faktur);
        btnKonfirmasi = findViewById(R.id.btn_konfirmasi_pembelian);
//        btnSimpanCicilan = findViewById(R.id.btn_simpan_cicilan);
//        etKeteranganCicilan = findViewById(R.id.et_keterangan_cicilan);
//        etTanggalCicilan = findViewById(R.id.et_tgl_cicilan);
//        etJumlahCicilan = findViewById(R.id.et_jumlah_cicilan);

        // REGISTER LISTENER
        btnKonfirmasi.setOnClickListener(this);
        btnLihatFaktur.setOnClickListener(this);
        cancel.setOnClickListener(this);

        rvItem = findViewById(R.id.rv_list_detail_pembelian);
//        rvCicilan = findViewById(R.id.rv_cicilan);
        rvItem.setHasFixedSize(true);
        rvItem.setLayoutManager(new LinearLayoutManager(this));


        // SET TEXT
        tvNoPesanan.setText(pembelian.getNoPesanan());
        switch (pembelian.getMetodePembayaran()) {
            case 1:
                tvMetodePembayaran.setText("Bayar di tempat (COD)");
                break;
            case 2:
                tvMetodePembayaran.setText("Cicil");
                break;
        }


        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(pembelian.getTanggalPesanan());

        Date date = c.getTime();

        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        String dateFormat = format.format(date);
        tvTanggalPesanan.setText(dateFormat);
        tvPemesan.setText(pembelian.getNamaPemasok());

        listItem = pembelian.getListBarang();
        double total = 0;
        for (ItemKeranjang keranjang : listItem) {
            total = total + keranjang.getTotalPrice();
        }

//        BigDecimal decimal = new BigDecimal(total);
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        String totalHargaPembelian = decimalFormat.format(total);

        tvTotalHargaPembelian.setText("Rp. " + totalHargaPembelian);

        DetailProsesPembelianAdapter adapter = new DetailProsesPembelianAdapter(listItem, this);
        rvItem.setAdapter(adapter);

//        toogle_switch.setChecked(false);
//        cicilan_expand = findViewById(R.id.expand_cicilan);
//        cicilan_expand.collapse();


//        // SWITCH BUTTON
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.im_cancel:
                finish();
                break;

            case R.id.btn_lihat_faktur:
                Intent lihat_faktur = new Intent(DetailProsesPembelianActivity.this, FakturPembelianActivity.class);
                lihat_faktur.putExtra("pembelian", pembelian);
                startActivity(lihat_faktur);
                break;
            case R.id.btn_konfirmasi_pembelian:
                showConfirmationDialog();
                break;
        }
    }

    void confirm(){
        pembelian.setProses(false);
        Calendar c = Calendar.getInstance();
        final List<Penyimpanan> listPenyimpanan = new ArrayList<>();
        Task<Void> task = null;
        for (ItemKeranjang keranjang : listItem) {
            String k = keranjang.getKode() + "key";
            System.out.println(k);
            Penyimpanan pe = new Penyimpanan(c.getTimeInMillis(),
                    sharedPref.getString(k, ""), keranjang.getKode(), keranjang.getQty(), "Pembelian", 0);

            task = FirebaseDatabase.getInstance().getReference("Penyimpanan").push().setValue(pe);
            listPenyimpanan.add(pe);

        }

        task.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(DetailProsesPembelianActivity.this, listPenyimpanan.size() + " item masuk penyimpanan", Toast.LENGTH_SHORT).show();
            }
        });


        FirebaseDatabase.getInstance().getReference("Barang").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot sn : dataSnapshot.getChildren()) {

                    for (Penyimpanan p : listPenyimpanan) {
                        System.out.println("snapshot "+ sn.getKey() + " key " + p.getKeyBarang());

                        if(p.getKeyBarang().equals(sn.getKey())){
                            Barang barang = sn.getValue(Barang.class);

                            int stok = barang.getStok() + p.getJumlahBarang();
                            System.out.println("NAMA BARANG" + barang.getKode());
                            System.out.println("STOK BARANG" + stok);
                            barang.setStok(stok);
                            sn.getRef().setValue(barang);

                        }
//                            dataSnapshot.getRef().child(p.getKeyBarang()).child("")
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        FirebaseDatabase.getInstance().getReference("Pembelian").child(key).setValue(pembelian)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        setResult(RESULT_OK);
                        finish();

                    }
                });
    }
    private void showConfirmationDialog(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Konfirmasi Pembelian ?");
        builder1.setCancelable(true);

        builder1.setNeutralButton(
                "YES",
                (dialog, id) ->{
                    confirm();
                    dialog.dismiss();
                    Toast.makeText(this, "Pembelian terkonfirmasi", Toast.LENGTH_SHORT).show();
                });

        builder1.setNegativeButton(
                "NO",
                (dialogInterface, i) -> {

                    dialogInterface.dismiss();
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
