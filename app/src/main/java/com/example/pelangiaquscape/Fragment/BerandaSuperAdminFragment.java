package com.example.pelangiaquscape.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pelangiaquscape.BarangActivity;
import com.example.pelangiaquscape.Model.Merek;
import com.example.pelangiaquscape.Model.Penjualan;
import com.example.pelangiaquscape.PenerimaanActivity;
import com.example.pelangiaquscape.PenyimpananActivity;
import com.example.pelangiaquscape.LaporanPenjualanActivity;
import com.example.pelangiaquscape.MitraBisnisActivity;
import com.example.pelangiaquscape.PegawaiActivity;
import com.example.pelangiaquscape.PembelianActivity;
import com.example.pelangiaquscape.PenjualanActivity;
import com.example.pelangiaquscape.TransaksiActivity;
import com.example.pelangiaquscape.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class BerandaSuperAdminFragment extends Fragment implements View.OnClickListener{

    TextView tvNamaToko;
    TextView tvLaporan;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String namaToko;
    List<Merek> listMerek = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("AkunToko").child("1");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View v  = inflater.inflate(R.layout.fragment_beranda_superadmin, container, false);

        // INIT VIEW
        tvLaporan = v.findViewById(R.id.total_penjualan);
        LinearLayout containerLaporan = v.findViewById(R.id.container_laporan);
        tvNamaToko = v.findViewById(R.id.nama_profile_toko);
        CardView cardViewPegawai = v.findViewById(R.id.cv_pegawai);
        CardView cardViewMitra = v.findViewById(R.id.cv_mitra);
        CardView cardViewPembelian = v.findViewById(R.id.cv_pembelian);
        CardView cardViewBarang = v.findViewById(R.id.cv_barang);
        CardView cardViewTransaksi = v.findViewById(R.id.cv_transaksi);
        CardView cardViewPenjualan = v.findViewById(R.id.cv_penjualan);
        CardView cardViewGudang = v.findViewById(R.id.cv_gudang);
        CardView cardViewPenerimaan = v.findViewById(R.id.cv_penerimaan);


        // REGISTER LISTENER
        cardViewPegawai.setOnClickListener(this);
        cardViewMitra.setOnClickListener(this);
        cardViewPembelian.setOnClickListener(this);
        cardViewBarang.setOnClickListener(this);
        cardViewTransaksi.setOnClickListener(this);
        cardViewPenjualan.setOnClickListener(this);
        cardViewGudang.setOnClickListener(this);
        cardViewPenerimaan.setOnClickListener(this);
        containerLaporan.setOnClickListener(this);


        // LOAD NAMA TOKO
        loadToko();

        // LOAD DATA PENJUALAN
        loadPenjualan();

        // LOAD DATA MEREK
        loadMerek();

        return v;
    }

    void loadToko(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                namaToko = "" + dataSnapshot.child("namaToko").getValue(String.class);

                //set data
                tvNamaToko.setText(namaToko);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void loadPenjualan(){
        FirebaseDatabase.getInstance().getReference("Penjualan").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Calendar c = Calendar.getInstance();

                int y = c.get(Calendar.YEAR);
                int m = c.get(Calendar.MONTH);

                double total = 0;

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Penjualan penjualan = ds.getValue(Penjualan.class);
                    c.setTimeInMillis(penjualan.getTanggalPenjualan());


                    int month = c.get(Calendar.MONTH);
                    int year1 = c.get(Calendar.YEAR);

                    if (y == year1) {
                        if(m == month){
                            total += penjualan.getTotalPenjualan();
                        }

                    }

                }
                DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
                String totalHarga = decimalFormat.format(total);
                tvLaporan.setText("Rp. " + totalHarga);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void loadMerek(){
        FirebaseDatabase.getInstance().getReference("Merek").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Merek m = ds.getValue(Merek.class);
                    listMerek.add(m);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.container_laporan:
                Intent container_laporan = new Intent(getActivity(), LaporanPenjualanActivity.class);
                startActivity(container_laporan);
                break;
            case R.id.cv_pegawai:
                Intent cv_pegawai = new Intent(getActivity(), PegawaiActivity.class);
                startActivity(cv_pegawai);
                break;
            case R.id.cv_mitra:
                Intent cv_mitra = new Intent(getActivity(), MitraBisnisActivity.class);
                startActivity(cv_mitra);
                break;
            case R.id.cv_pembelian:
                Intent cv_pembelian = new Intent(getActivity(), PembelianActivity.class);
                startActivity(cv_pembelian);
                break;
            case R.id.cv_barang:
                Intent cv_barang = new Intent(getActivity(), BarangActivity.class);
                startActivity(cv_barang);
                break;
            case R.id.cv_transaksi:
                Intent cv_transaksi = new Intent(getActivity(), TransaksiActivity.class);
                startActivity(cv_transaksi);
                break;
            case R.id.cv_penjualan:
                Intent cv_penjualan = new Intent(getActivity(), PenjualanActivity.class);
                startActivity(cv_penjualan);
                break;
            case R.id.cv_gudang:
                Intent cv_gudang = new Intent(getActivity(), PenyimpananActivity.class);
                startActivity(cv_gudang);
                break;
            case R.id.cv_penerimaan:
                Intent cv_penerimaaan = new Intent(getActivity(), PenerimaanActivity.class);
                startActivity(cv_penerimaaan);
                break;
        }
    }
}
