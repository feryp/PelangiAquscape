package com.example.pelangiaquscape;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelangiaquscape.Model.Penjualan;
import com.example.pelangiaquscape.ViewHolder.PenjualanViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class PenjualanActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView cancel;


    RecyclerView rvPenjualan;

    List<Penjualan> listPenjualan = new ArrayList<>();

    FirebaseDatabase fd;
    DatabaseReference dr;
    FirebaseRecyclerAdapter adapter;


    ImageView iv;
    TextView tvImage;

    Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);


        // INIT VIEW
        cancel =  findViewById(R.id.im_cancel);
        rvPenjualan = findViewById(R.id.rv_penjualan);
        rvPenjualan.setHasFixedSize(true);
//        rvPenjualan.setLayoutManager(new LinearLayoutManager(this));

        iv = findViewById(R.id.iv_ilustrasi_pelanggankosong);
        tvImage = findViewById(R.id.tv_pelanggan_kosong);

        // INIT VIEW END

        fd = FirebaseDatabase.getInstance();
        dr = fd.getReference().child("Penjualan");
        loadDataPenjualan();

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.im_cancel:
                finish();
                break;
        }
    }

    void loadDataPenjualan(){
        query = FirebaseDatabase.getInstance().getReference().child("Penjualan").orderByChild("tanggalPenjualan");
        Log.v("query", query.toString());

        FirebaseRecyclerOptions<Penjualan> options =
                new FirebaseRecyclerOptions.Builder<Penjualan>().setQuery(query, Penjualan.class).build();

        adapter = new FirebaseRecyclerAdapter<Penjualan, PenjualanViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull PenjualanViewHolder holder, int positiona, @NonNull Penjualan model) {
                Log.v("modelPenjualan", model.getNoPenjualan());
                holder.bindData(model);
                holder.setItemClickListener(new PenjualanViewHolder.OnClickListener() {
                    @Override
                    public void onClick(View v, int position) {
                        Toast.makeText(PenjualanActivity.this, position+"", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @NonNull
            @Override
            public PenjualanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_penjualan, viewGroup, false);
                Log.v("hereOnCreateView", "true");
                return new PenjualanViewHolder(v);
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
                iv.setVisibility(View.GONE);
                tvImage.setVisibility(View.GONE);

            }
        };
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        rvPenjualan.setLayoutManager(manager);
        rvPenjualan.setAdapter(adapter);

        Log.v("itemCount", String.valueOf(rvPenjualan.getAdapter().getItemCount()));
        rvPenjualan.setVisibility(View.VISIBLE);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    //        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds:dataSnapshot.getChildren()){
//                    Penjualan penjualan = ds.getValue(Penjualan.class);
//                    Log.v("penjualanModel", penjualan.getNoPenjualan());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
}
