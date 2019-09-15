package com.example.pelangiaquscape.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.pelangiaquscape.Adapter.ProsesPembelianAdapter;
import com.example.pelangiaquscape.Model.Pembelian;
import com.example.pelangiaquscape.PembelianActivity;
import com.example.pelangiaquscape.TambahPembelianActivity;
import com.example.pelangiaquscape.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ProsesPembelianFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseRecyclerAdapter adapter;
    RecyclerView rvProsesPembelian;
    RecyclerView.LayoutManager layoutManager;
    RelativeLayout rl;

    Query query;

    LinearLayout imageLayout;

    FloatingActionButton fab_pembelian;

    HashMap<String, Pembelian> mapping = new HashMap<>();
    List<Pembelian> listPembelian;
    List<String> listKey;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // INIT VIEW
        View v = inflater.inflate(R.layout.fragment_proses_pembelian, container, false);
        fab_pembelian = v.findViewById(R.id.fab_pembelian);
        imageLayout = v.findViewById(R.id.linear_pembelian_proses);
        rl = v.findViewById(R.id.pembelian_ket);



        //INIT FIREBASE
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Pembelian");

        //LOAD DATA PEMBELIAN
        rvProsesPembelian = v.findViewById(R.id.rv_proses_pembelian);
        rvProsesPembelian.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rvProsesPembelian.setLayoutManager(layoutManager);

        fab_pembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent fab_pembelian = new Intent(getActivity(), TambahPembelianActivity.class);
                startActivity(fab_pembelian);
            }
        });

        rvProsesPembelian.removeAllViews();
        loadPembelian();
        // Inflate the layout for this fragment
        return v;
    }



    public void loadPembelian(){
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference("Pembelian");


        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // RESET LIST
                listPembelian = new ArrayList<>();
                listKey = new ArrayList<>();

                for(DataSnapshot snapShot: dataSnapshot.getChildren()){
//                    mapping.put(dataSnapshot.getKey(), snapShot.getValue(Pembelian.class));
                    listKey.add(dataSnapshot.getKey());
                    listPembelian.add(snapShot.getValue(Pembelian.class));


                }


                ProsesPembelianAdapter adapter = new ProsesPembelianAdapter(listPembelian, listKey, getContext());
                rvProsesPembelian.setAdapter(adapter);


                if(adapter.getItemCount() > 0){

                    imageLayout.setVisibility(View.GONE);
                    rl.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
