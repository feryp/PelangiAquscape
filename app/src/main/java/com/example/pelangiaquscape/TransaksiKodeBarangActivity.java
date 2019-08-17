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


                        SQLiteDatabase db =hp.getWritableDatabase();

                        ContentValues values = new ContentValues();
                        values.put(ItemKeranjangEntry.COLUMN_NAME_KODE, model.getKode());
                        values.put(ItemKeranjangEntry.COLUMN_NAME_HARGA_BELI, model.getHargaBeli());
                        values.put(ItemKeranjangEntry.COLUMN_NAME_QTY, holder.tvQuantity.getText().toString());
                        values.put(ItemKeranjangEntry.COLUMN_NAME_MEREK, ids);
                        values.put(ItemKeranjangEntry.COLUMN_NAME_SATUAN, "-");
                        double total = model.getHargaBeli() * Double.parseDouble(holder.tvQuantity.getText().toString());
                        values.put(ItemKeranjangEntry.COLUMN_NAME_TOTAL_PRICE, total);
                        values.put(ItemKeranjangEntry.COLUMN_NAME_HARGA_JUAL, model.getHargaJual());

                        try{

                            long rowID = db.insertOrThrow(ItemKeranjangEntry.TABLE_NAME, null, values);
                            Toast.makeText(TransaksiKodeBarangActivity.this, "insert db", Toast.LENGTH_SHORT).show();
                        }catch(SQLiteConstraintException e){

                            long rowID = db.update(ItemKeranjangEntry.TABLE_NAME, values, ItemKeranjangEntry.COLUMN_NAME_KODE +"=?", new String[]{model.getKode()});

                            Toast.makeText(TransaksiKodeBarangActivity.this, "update db", Toast.LENGTH_SHORT).show();
                        }
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
