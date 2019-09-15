package com.example.pelangiaquscape.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.Model.Pemasok;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.TambahPemasokActivity;
import com.example.pelangiaquscape.ViewHolder.PemasokViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class PemasokFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databasePemasok;
    FirebaseRecyclerAdapter adapter;
    RecyclerView rvPemasok;
    RecyclerView.LayoutManager layoutManager;

    Query query;

    LinearLayout imageLayout;

    FloatingActionButton fab_pemasok;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pemasok, container, false);

        fab_pemasok = v.findViewById(R.id.fab_pemasok);
        imageLayout = v.findViewById(R.id.linear_imageviewPemasok);

        //init firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databasePemasok = firebaseDatabase.getReference("Pemasok");

        //load data pemasok
        rvPemasok = v.findViewById(R.id.rv_pemasok);
        rvPemasok.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rvPemasok.setLayoutManager(layoutManager);

        fab_pemasok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "Tambah Pemasok", Toast.LENGTH_LONG).show();
                Intent pemasok = new Intent(getActivity(), TambahPemasokActivity.class);
                pemasok.putExtra("idForPemasok", String.valueOf(adapter.getItemCount() + 1));
                startActivity(pemasok);
            }
        });

        loadPemasok();

        // Inflate the layout for this fragment
        return v;
    }

    void loadPemasok() {
        query = FirebaseDatabase.getInstance().getReference("Pemasok").orderByChild("nama");
        FirebaseRecyclerOptions<Pemasok> options =
                new FirebaseRecyclerOptions.Builder<Pemasok>().setQuery(query, Pemasok.class).build();
        Log.i("SNAPSHOT", options.getSnapshots().toString());

        adapter = new FirebaseRecyclerAdapter<Pemasok, PemasokViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PemasokViewHolder holder, final int position, @NonNull final Pemasok model) {
                holder.bindData(model);

                Log.i("INFORMATION", model.getJenisPerusahaan() + " " + model.getJenisPerusahaan());
                Log.i("INFORMATION", model.getNamaPemasok() + " " + model.getNamaPemasok());
                Log.i("INFORMATION", model.getNoHpPemasok() + " " + model.getNoHpPemasok());
                Log.i("INFORMATION", model.getAlamatPemasok() + " " + model.getAlamatPemasok());

                final Pemasok clickItem = model;

                final int size = this.getItemCount();

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent pemasok = new Intent(getActivity(), TambahPemasokActivity.class);
                        pemasok.putExtra("idForPemasok", adapter.getRef(position).getKey());
                        pemasok.putExtra("jenisPerusahaan", model.getJenisPerusahaan());
                        pemasok.putExtra("namaPemasok", model.getNamaPemasok());
                        pemasok.putExtra("klasifikasiPerusahaan", model.getKlasifikasiPerusahaan());
                        pemasok.putExtra("kualifikasiPerusahaan", model.getKualifikasiPerusahaan());
                        pemasok.putExtra("telepon", model.getTelepon());
                        pemasok.putExtra("email", model.getEmailPemasok());
                        pemasok.putExtra("noHp", model.getNoHpPemasok());
                        pemasok.putExtra("alamat", model.getAlamatPemasok());

                        System.out.println("ID Pemasok " + adapter.getRef(position).getKey());
                        pemasok.putExtra("fromPemasokFragment", true);
                        startActivity(pemasok);
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
            public PemasokViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item_pemasok, viewGroup, false);
                Log.i("Buat View Holder", view.toString());
                return new PemasokViewHolder(view);
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();

                if (adapter.getItemCount() > 0) {
                    imageLayout.setVisibility(View.GONE);
                }
            }
        };

        rvPemasok.setAdapter(adapter);
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

    void showDialog(final String key, final Pemasok pemasok){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Hapus Data");
        alertDialog.setMessage("Apakah anda ingin mengahapus Pelaanggan ini ? ");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "YA",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference("Pemasok")
                                .child(key)
                                .removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getActivity().getApplicationContext(), "item " + pemasok.getJenisPerusahaan() + " item " + pemasok.getNamaPemasok() + " item " + pemasok.getNoHpPemasok() + " item " + pemasok.getAlamatPemasok() + " telah terhapus", Toast.LENGTH_SHORT).show();
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

//    void showDialog(final String key, final Pemasok pemasok){
//        AlertDialog.Builder alertDialogBulider = new AlertDialog.Builder(getActivity());
//        alertDialogBulider.setTitle("Hapus Data");
//        alertDialogBulider.setMessage("Apakah anda ingin menghapus Pelanggan ini ? ")
//                .setCancelable(false)
//                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        FirebaseDatabase.getInstance().getReference("Pemasok")
//                                .child(key)
//                                .removeValue()
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        Toast.makeText(getActivity().getApplicationContext(), "item" + pemasok.getJenisPerusahaan() + "item" + pemasok.getNamaPemasok() + "item" + pemasok.getNoHpPemasok() + "item" + pemasok.getAlamatPemasok() + " telah terhapus", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                    }
//                }).setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//
//        AlertDialog alertDialog = alertDialogBulider.create();
//        alertDialog.show();
//
//    }

}
