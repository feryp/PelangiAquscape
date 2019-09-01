package com.example.pelangiaquscape.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.Model.Pelanggan;
import com.example.pelangiaquscape.PegawaiActivity;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.TambahPegawaiActivity;
import com.example.pelangiaquscape.TambahPelangganActivity;
import com.example.pelangiaquscape.ViewHolder.PelangganViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PelangganFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databasePelanggan;
    FirebaseRecyclerAdapter adapter;
    RecyclerView rvPelanggan;
    RecyclerView.LayoutManager layoutManager;

    Query query;

    LinearLayout imageLayout;

    FloatingActionButton fab_pelanggan;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_pelanggan, container, false);

        fab_pelanggan = v.findViewById(R.id.fab_pelanggan);
        imageLayout = v.findViewById(R.id.linear_imageviewPelanggan);

        //ini firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databasePelanggan = firebaseDatabase.getReference("Pelanggan");

        //load data pelanggan
        rvPelanggan = v.findViewById(R.id.rv_pelanggan);
        rvPelanggan.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rvPelanggan.setLayoutManager(layoutManager);

        fab_pelanggan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "Tambah Pelanggan", Toast.LENGTH_LONG).show();
                Intent pelanggan = new Intent(getActivity(), TambahPelangganActivity.class);
                pelanggan.putExtra("idForPelanggan", String.valueOf(adapter.getItemCount() + 1));
                startActivity(pelanggan);
            }
        });


        loadPelanggan();

        // Inflate the layout for this fragment
        return v;
    }

    void loadPelanggan() {
        query = FirebaseDatabase.getInstance().getReference("Pelanggan").orderByChild("nama");
        FirebaseRecyclerOptions<Pelanggan> options =
                new FirebaseRecyclerOptions.Builder<Pelanggan>().setQuery(query, Pelanggan.class).build();
        Log.i("SNAPSHOT", options.getSnapshots().toString());

        adapter = new FirebaseRecyclerAdapter<Pelanggan, PelangganViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PelangganViewHolder holder, final int position, @NonNull final Pelanggan model) {

                holder.bindData(model);


                Log.i("INFORMATION", model.getNamaPelanggan() + " " + model.getNamaPelanggan());
                Log.i("INFORMATION", model.getNoHp() + " " + model.getNoHp());
                Log.i("INFORMATION", model.getAlamat() + " " + model.getAlamat());

                final Pelanggan clickItem = model;

                final int size = this.getItemCount();


                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent pelanggan = new Intent(getActivity(), TambahPelangganActivity.class);
                        pelanggan.putExtra("idForPelanggan", adapter.getRef(position).getKey());
                        pelanggan.putExtra("namaPelanggan", model.getNamaPelanggan());
                        pelanggan.putExtra("noHp", model.getNoHp());
                        pelanggan.putExtra("alamat", model.getAlamat());
                        pelanggan.putExtra("catatan", model.getCatatan());

                        System.out.println("ID Pelanggan " + adapter.getRef(position).getKey());
//                        Log.i("GET IDPELANGGAN", pelanggan.getStringExtra("newIdForPelanggan") + adapter.getRef(position).getKey());
                        startActivity(pelanggan);
                    }
                });

                holder.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        showDialog(String.valueOf(adapter.getRef(position).getKey()), model);
                        return false;
                    }
                });
            }


            @NonNull
            @Override
            public PelangganViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item_pelanggan, viewGroup, false);
                Log.i("Buat View Holder", view.toString());
                return new PelangganViewHolder(view);
            }

            @Override
            public void onDataChanged() {

                super.onDataChanged();

                if (adapter.getItemCount() > 0) {
                    imageLayout.setVisibility(View.GONE);
                }
            }
        };

        rvPelanggan.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.stopListening();
    }

//    void showDialog(final String key, final Pelanggan pelanggan) {
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
//        alertDialogBuilder.setTitle("Hapus Data");
//        alertDialogBuilder.setMessage("Apakah anda ingin menghapus Pelanggan ini ? ")
//                .setCancelable(false)
//                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                FirebaseDatabase.getInstance().getReference("Pelanggan")
//                        .child(key)
//                        .removeValue()
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(getActivity().getApplicationContext(), "item" + pelanggan.getNamaPelanggan() + "item" + pelanggan.getNoHp() + "item" + pelanggan.getAlamat() + " telah terhapus", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//            }
//        }).setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
//
//    }
    void showDialog(final String key, final Pelanggan pelanggan){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Hapus Data");
        alertDialog.setMessage("Apakah anda ingin mengahapus Pelaanggan ini ? ");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "YA",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference("Pelanggan")
                                .child(key)
                                .removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getActivity().getApplicationContext(), "item " + pelanggan.getNamaPelanggan() + " item " + pelanggan.getNoHp() + " item " + pelanggan.getAlamat() + " telah terhapus ", Toast.LENGTH_SHORT).show();
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
