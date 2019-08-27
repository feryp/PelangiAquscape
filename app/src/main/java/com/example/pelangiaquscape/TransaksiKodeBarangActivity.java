package com.example.pelangiaquscape;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;


import com.example.pelangiaquscape.Database.ItemKeranjangContract.ItemKeranjangEntry;
import com.example.pelangiaquscape.Database.ItemKeranjangDbHelper;
import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.Model.Barang;
import com.example.pelangiaquscape.Model.Merek;
import com.example.pelangiaquscape.ViewHolder.TransaksiBarangViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TransaksiKodeBarangActivity extends AppCompatActivity {

    FirebaseDatabase fd;
    DatabaseReference dr;
    FirebaseRecyclerAdapter adapter;
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    String id, namaMerek;
    Query query;
    ImageView cancel;

    SearchView searchView;
    Button btnJual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_kode_barang);

        cancel =  findViewById(R.id.im_cancel);
        btnJual = findViewById(R.id.btn_jual);
        btnJual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TransaksiKodeBarangActivity.this, KeranjangPenjualanActivity.class);
                startActivity(i);

            }
        });



        Intent i = getIntent();
        id = i.getStringExtra("idMerek");
        namaMerek = i.getStringExtra("namaMerek");

        searchView = findViewById(R.id.search_kode_barang);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchBarang(query, id);
                adapter.startListening();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchBarang(newText, id);
                adapter.startListening();
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                loadBarang(id);
                adapter.startListening();
                return false;
            }
        });

        Log.v("IDMEREK", id);
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

    private void loadBarang(final String ids) {

//        final List<Merek> listMerek = new ArrayList<>();


        query = FirebaseDatabase.getInstance().getReference().child("Barang").orderByChild("merek").equalTo(ids);
        FirebaseRecyclerOptions<Barang> options =
                new FirebaseRecyclerOptions.Builder<Barang>().setQuery(query, Barang.class).build();

        Log.i("SNAPSHOT", options.getSnapshots().toString() + " " + ids);

        adapter = new FirebaseRecyclerAdapter<Barang, TransaksiBarangViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final TransaksiBarangViewHolder holder, int position, @NonNull final Barang model) {

                holder.bindDataTransaksi(model, namaMerek);


                Log.i("INFORMATION", model.getKode() + " " + model.getMerek());


                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(TransaksiKodeBarangActivity.this, position + "", Toast.LENGTH_SHORT).show();
                        ItemKeranjangDbHelper hp = new ItemKeranjangDbHelper(getBaseContext());
                        hp.insertOrDelete(model,ids, holder.tvQuantity.getText().toString(),holder.tvQuantity.getText().toString());

                    }
                });
            }

            @NonNull
            @Override
            public TransaksiBarangViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_kode_barang_transaksi, viewGroup, false);
//                Log.i("Kesini", view.toString());
                return new TransaksiBarangViewHolder(view);
            }

        };

        rv.setAdapter(adapter);
    }


    void searchBarang(final String searchText, final String ids){
        FirebaseDatabase.getInstance().getReference().child("Barang").orderByChild("merek").equalTo(ids).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Query query = dataSnapshot.getRef().orderByChild("kode").startAt(searchText.toUpperCase()).endAt(searchText.toUpperCase() + "\uf8ff");
                FirebaseRecyclerOptions<Barang> options =
                        new FirebaseRecyclerOptions.Builder<Barang>().setQuery(query, Barang.class).build();

                Log.i("SNAPSHOT", options.getSnapshots().toString() + " " + ids);

                adapter = new FirebaseRecyclerAdapter<Barang, TransaksiBarangViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final TransaksiBarangViewHolder holder, int position, @NonNull final Barang model) {

                        holder.bindDataTransaksi(model, namaMerek);


                        Log.i("INFORMATION", model.getKode() + " " + model.getMerek());


                        holder.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onClick(View view, int position, boolean isLongClick) {
                                Toast.makeText(TransaksiKodeBarangActivity.this, position + "", Toast.LENGTH_SHORT).show();
                                ItemKeranjangDbHelper hp = new ItemKeranjangDbHelper(getBaseContext());
                                hp.insertOrDelete(model,ids, holder.tvQuantity.getText().toString(),holder.tvQuantity.getText().toString());

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public TransaksiBarangViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext())
                                .inflate(R.layout.list_kode_barang_transaksi, viewGroup, false);
                        Log.i("Kesini", view.toString());
                        return new TransaksiBarangViewHolder(view);
                    }

                };



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
        rv.setAdapter(adapter);
    }
}
