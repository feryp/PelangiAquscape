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
import com.example.pelangiaquscape.PegawaiActivity;
import com.example.pelangiaquscape.PembelianActivity;
import com.example.pelangiaquscape.R;


public class BerandaFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v  = inflater.inflate(R.layout.fragment_beranda, container, false);

        LinearLayout containerLaporan = v.findViewById(R.id.container_laporan);

        CardView cardViewPegawai = v.findViewById(R.id.cv_pegawai);
        CardView cardViewMitra = v.findViewById(R.id.cv_mitra);
        CardView cardViewPembelian = v.findViewById(R.id.cv_pembelian);
        CardView cardViewBarang = v.findViewById(R.id.cv_barang);

        cardViewPegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cv_pegawai = new Intent(getActivity(), PegawaiActivity.class);
                startActivity(cv_pegawai);
            }
        });

        cardViewMitra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cv_mitra = new Intent(getActivity(), MitraBisnisActivity.class);
                startActivity(cv_mitra);
            }
        });

        cardViewPembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cv_pembelian = new Intent(getActivity(), PembelianActivity.class);
                startActivity(cv_pembelian);
            }
        });

        cardViewBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cv_barang = new Intent(getActivity(), BarangActivity.class);
                startActivity(cv_barang);
            }
        });

        containerLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent container_laporan = new Intent(getActivity(), LaporanPenjualanActivity.class);
                startActivity(container_laporan);
            }
        });

        return v;
    }


}
