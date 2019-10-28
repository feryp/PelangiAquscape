package com.example.pelangiaquscape.Fragment;

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
        fromPemberitahuanActivity = false;
        try {
            fromPemberitahuanActivity = getArguments().getBoolean("fromPemberitahuanActivity");
        } catch (NullPointerException ex) {

        }


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

        databaseReference = FirebaseDatabase.getInstance().getReference("Pemberitahuan");

        FirebaseRecyclerOptions<Pemberitahuan> options =
                new FirebaseRecyclerOptions.Builder<Pemberitahuan>()
                        .setQuery(databaseReference, Pemberitahuan.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Pemberitahuan, PemberitahuanViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull PemberitahuanViewHolder holder, int position, @NonNull Pemberitahuan model) {

                Log.i("INFORMATION", model.getJudul() + " " + model.getJudul());
                Log.i("INFORMATION", model.getPesan() + " " + model.getPesan());
                Log.i("INFORMATION", model.getWaktu() + " " + model.getWaktu());
                holder.bind(model);
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(getActivity(), "Pemberitahuan", Toast.LENGTH_SHORT).show();
                        Intent p = new Intent(getActivity(), DetailPemberitahuanActivity.class);
                        p.putExtra("pemberitahuan", pemberitahuan);
                        p.putExtra("key", key);
                        p.putExtra("pemberitahuan", model);
                        startActivity(p);
                    }
                });

            }

            @NonNull
            @Override
            public PemberitahuanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item_pemberitahuan_masuk, viewGroup, false);

//                Log.i("Buat View Holder", view.toString());

                return new PemberitahuanViewHolder(view, getContext());
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
