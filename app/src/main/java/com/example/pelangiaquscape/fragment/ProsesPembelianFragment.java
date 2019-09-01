package com.example.pelangiaquscape.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pelangiaquscape.FormPembelianActivity;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.TambahPelangganActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class ProsesPembelianFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseRecyclerAdapter adapter;
    RecyclerView rvProsesPembelian;
    RecyclerView.LayoutManager layoutManager;

    Query query;

    LinearLayout imageLayout;

    FloatingActionButton fab_pembelian;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_proses_pembelian, container, false);

        fab_pembelian = v.findViewById(R.id.fab_pembelian);
        imageLayout = v.findViewById(R.id.linear_pembelian_proses);

        //init firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("ProsesPembelian");

        //load data pembelian
        rvProsesPembelian = v.findViewById(R.id.rv_proses_pembelian);
        rvProsesPembelian.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rvProsesPembelian.setLayoutManager(layoutManager);

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
