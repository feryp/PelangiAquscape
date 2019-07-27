package com.example.pelangiaquscape.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.pelangiaquscape.BarangActivity;
import com.example.pelangiaquscape.LaporanPenjualanActivity;
import com.example.pelangiaquscape.MitraBisnisActivity;
import com.example.pelangiaquscape.PembelianActivity;
import com.example.pelangiaquscape.PenjualanActivity;
import com.example.pelangiaquscape.TransaksiActivity;
import com.example.pelangiaquscape.R;


public class BerandaAdminFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_beranda_admin, container, false);

        LinearLayout containerLaporan = v.findViewById(R.id.container_laporan);

        CardView cardViewMitra = v.findViewById(R.id.cv_mitra);
        CardView cardViewBarang = v.findViewById(R.id.cv_barang);
        CardView cardViewTransaksi = v.findViewById(R.id.cv_transaksi);
        CardView cardViewPenjualan = v.findViewById(R.id.cv_penjualan);

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

        containerLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent container_laporan = new Intent(getActivity(), LaporanPenjualanActivity.class);
                startActivity(container_laporan);
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

}
