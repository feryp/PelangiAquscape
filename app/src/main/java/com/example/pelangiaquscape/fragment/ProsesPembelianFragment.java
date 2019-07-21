package com.example.pelangiaquscape.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pelangiaquscape.FormPembelianActivity;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.TambahPelangganActivity;


public class ProsesPembelianFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_proses_pembelian, container, false);

        FloatingActionButton fab_pembelian = v.findViewById(R.id.fab_pembelian);

        fab_pembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "Tambah List Pembelian",Toast.LENGTH_LONG).show();
                Intent fab_pembelian = new Intent(getActivity(), FormPembelianActivity.class);
                startActivity(fab_pembelian);
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

}
