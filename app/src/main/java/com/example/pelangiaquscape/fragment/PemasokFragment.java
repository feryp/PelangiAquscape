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

import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.TambahPegawaiActivity;
import com.example.pelangiaquscape.TambahPemasokActivity;


public class PemasokFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_pemasok, container, false);

        FloatingActionButton fab_pemasok = v.findViewById(R.id.fab_pemasok);

        fab_pemasok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "Tambah Pemasok",Toast.LENGTH_LONG).show();
                Intent fab_pemasok = new Intent(getActivity(), TambahPemasokActivity.class);
                startActivity(fab_pemasok);
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

}
