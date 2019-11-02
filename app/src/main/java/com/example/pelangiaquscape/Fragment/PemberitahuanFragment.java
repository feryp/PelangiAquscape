package com.example.pelangiaquscape.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pelangiaquscape.DetailPemberitahuanActivity;
import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.Model.Pemberitahuan;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.ViewHolder.PemberitahuanViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class PemberitahuanFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseRecyclerAdapter adapter;
    RecyclerView rvPemberitahuan;
    RecyclerView.LayoutManager layoutManager;

    LinearLayout imageLayout;

    Query query;

    boolean fromPemberitahuanActivity;

    private Pemberitahuan pemberitahuan;
    private String key;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        fromPemberitahuanActivity = false;
//        try {
//            fromPemberitahuanActivity = getArguments().getBoolean("fromPemberitahuanActivity");
//        } catch (NullPointerException ex) {
//
//        }


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pemberitahuan, container, false);

        //init view
        imageLayout = v.findViewById(R.id.linear_imageviewPemberitahuan);
        rvPemberitahuan = v.findViewById(R.id.rv_pemberitahuan);

        //init firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Pemberitahuan");

        //load data pemberitahuan
        rvPemberitahuan.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rvPemberitahuan.setLayoutManager(layoutManager);

        loadPemberitahuan();

        return v;

    }

    void loadPemberitahuan() {

        query = FirebaseDatabase.getInstance().getReference("Pemberitahuan").orderByChild("waktu");
        Log.v("query", query.getPath().toString());

//        databaseReference = FirebaseDatabase.getInstance().getReference("Pemberitahuan");

        FirebaseRecyclerOptions<Pemberitahuan> options =
                new FirebaseRecyclerOptions.Builder<Pemberitahuan>()
                        .setQuery(query, Pemberitahuan.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Pemberitahuan, PemberitahuanViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull PemberitahuanViewHolder holder, int position, @NonNull Pemberitahuan model) {

//                Log.v("modelPemberitahuan", model.getJudul());
                Log.v("modelPemberitahuan", model.getJudul());
                Log.v("modelPemberitahuan", model.getPesan());
                Log.v("modelPemberitahuan", model.getNamaBarang());
                Log.v("modelPemberitahuan", String.valueOf(model.getWaktu()));


                holder.bindP(model);

                final Pemberitahuan clickItem = model;
                final int size = this.getItemCount();

                if (!fromPemberitahuanActivity){
                    holder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Toast.makeText(getActivity(), "Pemberitahuan", Toast.LENGTH_SHORT).show();
                            Intent p = new Intent(getActivity(), DetailPemberitahuanActivity.class);
//                            p.putExtra("idForPemberitahuan", adapter.getRef(position).getKey());
//                            p.putExtra("judul", model.getJudul());
//                            p.putExtra("pesan", model.getPesan());
//                            p.putExtra("namaBarang", model.getNamaBarang());
//                            p.putExtra("waktu", model.getJudul());
//                            p.putExtra("baca", model.isBaca());
//                            p.putExtra("proses", model.isProses());

                            p.putExtra("pemberitahuan",model);
                         // p.putExtra("pemberitahuan",pemberitahuan );
                            p.putExtra("key", key);
//

                            System.out.println("ID Pemberitahuan " + adapter.getRef(position).getKey());
//                            p.putExtra("fromPemberitahuanFragment" , true);
//                            startActivityForResult(p,1);
                            startActivity(p);
                        }
                    });
                }

            }

            @NonNull
            @Override
            public PemberitahuanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item_pemberitahuan_keluar, viewGroup, false);
                return new PemberitahuanViewHolder(view);
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();

                if (adapter.getItemCount() > 0) {
                    imageLayout.setVisibility(View.GONE);
                }
            }
        };

        rvPemberitahuan.setAdapter(adapter);
        Log.v("itemCount", String.valueOf(rvPemberitahuan.getAdapter().getItemCount()));
        rvPemberitahuan.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();
        }
    }
}
