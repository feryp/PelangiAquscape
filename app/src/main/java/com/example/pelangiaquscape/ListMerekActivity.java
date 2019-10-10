package com.example.pelangiaquscape;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.Model.Merek;
import com.example.pelangiaquscape.ViewHolder.ListMerekViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ListMerekActivity extends FragmentActivity implements DialogTambahMerek.NoticeDialogListener {

    ImageView cancel;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseMerek;
    FirebaseRecyclerAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Query query;
    FloatingActionButton fabMerek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_merek);

        cancel = findViewById(R.id.im_cancel);
        fabMerek = findViewById(R.id.fab_merek_barang);

        //init Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseMerek = firebaseDatabase.getReference("Merek");

        //load data merek
        recyclerView = findViewById(R.id.rv_list_merek);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        fabMerek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ListMerekActivity.this, "Tambah Merek", Toast.LENGTH_SHORT).show();
                openDialog();
            }
        });

        loadMerk();
    }

    private void loadMerk() {
        query = FirebaseDatabase.getInstance().getReference("Merek").orderByChild("nama");
        FirebaseRecyclerOptions<Merek> options =
                new FirebaseRecyclerOptions.Builder<Merek>().setQuery(query, Merek.class).build();

        Log.i("SNAPSHOT", options.getSnapshots().toString());

        adapter = new FirebaseRecyclerAdapter<Merek, ListMerekViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull ListMerekViewHolder holder, int position, @NonNull final Merek model) {
                holder.bindDataMerek(model);

                Log.i("INFORMATION", model.getNama() + " " + model.getNama());

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent merek = new Intent();

                        merek.putExtra("idMerek", adapter.getRef(holder.getAdapterPosition()).getKey());
                        merek.putExtra("namaMerek", model.getNama());
//                        merek.putExtra("listSize", size);


                        Log.i("GET IDMEREK", merek.getStringExtra("idMerek") + adapter.getRef(position).getKey());
                        setResult(RESULT_OK, merek);
                        finish();
                    }
                });

                holder.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        showDeleteDialog(String.valueOf(adapter.getRef(position).getKey()),model);
                        return false;
                    }
                });
            }

            @NonNull
            @Override
            public ListMerekViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_merek_barang, viewGroup, false);
                Log.i("Buat View Holder", view.toString());
                return new ListMerekViewHolder(view);
            }

        };

        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
    }


    private void openDialog() {
        DialogTambahMerek dialogTambahMerek = new DialogTambahMerek();

        dialogTambahMerek.show(getSupportFragmentManager(), "dialog tambah merek");

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.stopListening();
    }

    @Override
    public void onDialogPositiveClick(DialogTambahMerek dialog, String nameValue) {


    }

    @Override
    public void onDialogNegativeClick(DialogTambahMerek dialog, String nameValue) {
        int a = adapter.getItemCount() + 1;
        String key = String.valueOf(a);
        Merek merek = new Merek(nameValue.toUpperCase());
        FirebaseDatabase.getInstance().getReference("Merek").child(key).setValue(merek).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ListMerekActivity.this, "Data Merek Berhasil ditambah", Toast.LENGTH_SHORT).show();
            }
        });

    }

    void showDeleteDialog(final String key, final Merek merek){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Hapus Data");
        alertDialog.setMessage("Apakah anda ingin menghapus Merek ini ? ");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "YA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference("Merek")
                                .child(key)
                                .removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(ListMerekActivity.this, "item "+merek.getNama() +" telah terhapus", Toast.LENGTH_SHORT).show();
                                    }
                                });

                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "TIDAK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


}
