package com.example.pelangiaquscape;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.Model.Barang;
import com.example.pelangiaquscape.Model.Merek;
import com.example.pelangiaquscape.ViewHolder.BarangViewHolder;
import com.example.pelangiaquscape.ViewHolder.MerekViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class PenjualanKodeBarangActivity extends AppCompatActivity {

    FirebaseDatabase fd;
    DatabaseReference dr;
    FirebaseRecyclerAdapter adapter;
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    String id;
    Query query;
    ImageView cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan_kode_barang);

        cancel =  (ImageView) findViewById(R.id.im_cancel);

        Intent i = getIntent();
        id = i.getStringExtra("idMerek");

        fd = FirebaseDatabase.getInstance();
        dr = fd.getReference().child("Barang");

        //load data merek
        rv = findViewById(R.id.rv_daftar_kode);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loadBarang(id);

    }

    private void loadBarang(String ids) {

        query = FirebaseDatabase.getInstance().getReference().child("Barang").orderByChild("merek").equalTo(ids);
        FirebaseRecyclerOptions<Barang> options =
                new FirebaseRecyclerOptions.Builder<Barang>().setQuery(query, Barang.class).build();

        Log.i("SNAPSHOT", options.getSnapshots().toString() + " " + ids);

        adapter = new FirebaseRecyclerAdapter<Barang, BarangViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull BarangViewHolder holder, int position, @NonNull Barang model) {
                holder.tvKode.setText(model.getKode());
                holder.tvHarga.setText(String.valueOf(model.getHargaJual()));
//                holder.im_arrow.setImageResource(R.drawable.ic_arrow_black);

                Log.i("INFORMATION", model.getKode() + " " + model.getMerek());
                final Barang clickItem = model;

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(PenjualanKodeBarangActivity.this, position + "", Toast.LENGTH_SHORT).show();
                    }
                });


            }

            @NonNull
            @Override
            public BarangViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_kode_barang_penjualan, viewGroup, false);
                Log.i("Kesini", view.toString());
                return new BarangViewHolder(view);
            }
        };

        rv.setAdapter(adapter);
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
}
