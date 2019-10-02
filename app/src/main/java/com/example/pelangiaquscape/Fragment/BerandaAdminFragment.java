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
import com.example.pelangiaquscape.PenerimaanActivity;
import com.example.pelangiaquscape.PenyimpananActivity;
import com.example.pelangiaquscape.LaporanPenjualanActivity;
import com.example.pelangiaquscape.MitraBisnisActivity;
import com.example.pelangiaquscape.PenjualanActivity;
import com.example.pelangiaquscape.TransaksiActivity;
import com.example.pelangiaquscape.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class BerandaAdminFragment extends Fragment {

    TextView tvNamaToko;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String namaToko;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("AkunToko").child("1");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_beranda_admin, container, false);

        LinearLayout containerLaporan = v.findViewById(R.id.container_laporan);

        tvNamaToko = v.findViewById(R.id.nama_profile_toko);

        CardView cardViewMitra = v.findViewById(R.id.cv_mitra);
        CardView cardViewBarang = v.findViewById(R.id.cv_barang);
        CardView cardViewTransaksi = v.findViewById(R.id.cv_transaksi);
        CardView cardViewPenjualan = v.findViewById(R.id.cv_penjualan);
        CardView cardViewGudang = v.findViewById(R.id.cv_gudang);
        CardView cardViewPenerimaan = v.findViewById(R.id.cv_penerimaan);

        cardViewMitra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cv_mitra = new Intent(getActivity(), MitraBisnisActivity.class);
                startActivity(cv_mitra);
            }
        });


        cardViewBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cv_barang = new Intent(getActivity(), BarangActivity.class);
                startActivity(cv_barang);
            }
        });

        cardViewTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cv_transaksi = new Intent(getActivity(), TransaksiActivity.class);
                startActivity(cv_transaksi);
            }
        });

        cardViewPenjualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cv_penjualan = new Intent(getActivity(), PenjualanActivity.class);
                startActivity(cv_penjualan);
            }
        });

        cardViewPenerimaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cv_penerimaaan = new Intent(getActivity(), PenerimaanActivity.class);
                startActivity(cv_penerimaaan);
            }
        });

        cardViewGudang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cv_gudang = new Intent(getActivity(), PenyimpananActivity.class);
                startActivity(cv_gudang);
            }
        });

        containerLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent container_laporan = new Intent(getActivity(), LaporanPenjualanActivity.class);
                startActivity(container_laporan);
            }
        });

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

        return v;
    }

}
