package com.example.pelangiaquscape;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.LinkAddress;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.Model.Merek;
import com.example.pelangiaquscape.Model.Pegawai;
import com.example.pelangiaquscape.ViewHolder.PegawaiViewHolder;
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
import com.squareup.picasso.Picasso;

import java.util.List;

public class PegawaiActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView cancel;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    RecyclerView rvPegawai;

    Query q;
    Context c;

    LinearLayout imageLayout;
    private Pegawai pegawai;
    private String key;

    RecyclerView.LayoutManager layoutManager;
    FloatingActionButton fab_pegawai;
    boolean fromTambahPembelianActivity;

    Query query;
    FirebaseRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegawai);

        // INIT VIEW
        cancel = findViewById(R.id.im_cancel);
        fab_pegawai = findViewById(R.id.fab_pegawai);
        imageLayout = findViewById(R.id.linear_imageview_pegawai);
        rvPegawai = findViewById(R.id.rv_pegawai);
        rvPegawai.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        rvPegawai.setLayoutManager(layoutManager);

        //INIT FIREBASE
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Pegawai");

        // REGISTER LISTENER
        cancel.setOnClickListener(this);
        fab_pegawai.setOnClickListener(this);

        // LOAD PEGAWAI FROM FIREBASE
        loadPegawai();
    }

    private void loadPegawai() {
        query = FirebaseDatabase.getInstance().getReference().child("Pegawai").orderByChild("namaPegawai");
        FirebaseRecyclerOptions<Pegawai> options =
                new FirebaseRecyclerOptions.Builder<Pegawai>().setQuery(query, Pegawai.class).build();

        Log.i("SNAPSHOT", options.getSnapshots().toString());

        adapter = new FirebaseRecyclerAdapter<Pegawai, PegawaiViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PegawaiViewHolder holder, int position, @NonNull final Pegawai model) {
                holder.bindData(model);
                imageLayout.setVisibility(View.GONE);

                Log.i("INFORMATION", model.getFotoPegawai() + " " + model.getFotoPegawai());
                Log.i("INFORMATION", model.getNamaPegawai() + " " + model.getNamaPegawai());
                Log.i("INFORMATION", model.getNamapengguna() + " " + model.getNamapengguna());
                Log.i("INFORMATION", model.getPassword() + " " + model.getPassword());
                Log.i("INFORMATION", model.getJabatan() + " " + model.getJabatan());
                Log.i("INFORMATION", model.getHakAkses() + " " + model.getHakAkses());
                Log.i("INFORMATION", model.getNoHp() + " " + model.getNoHp());
                Log.i("INFORMATION", model.getEmailPegawai() + " " + model.getEmailPegawai());


                final Pegawai clickItem = model;

                final int size = this.getItemCount();

                if (!fromTambahPembelianActivity) {
                    holder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Intent pegawai = new Intent(PegawaiActivity.this, TambahPegawaiActivity.class);

                            pegawai.putExtra("idPegawai", adapter.getRef(holder.getAdapterPosition()).getKey());
                            pegawai.putExtra("emailPegawai", model.getEmailPegawai());
                            pegawai.putExtra("fotoPegawai", model.getFotoPegawai());
                            pegawai.putExtra("hakAkses", model.getHakAkses());
                            pegawai.putExtra("id", model.getId());
                            pegawai.putExtra("namaPegawai", model.getNamaPegawai());
                            pegawai.putExtra("namaPengguna", model.getNamapengguna());
                            pegawai.putExtra("noHp", model.getNoHp());
//                        pegawai.putExtra("modelPegawai", model);

                            System.out.println("ID Pegawai " + adapter.getRef(position).getKey());
                            startActivity(pegawai);

//                        merek.putExtra("listSize", size);


//                        Log.i("GET IDMEREK", merek.getStringExtra("idMerek") + adapter.getRef(position).getKey());
//                        setResult(RESULT_OK, merek);


                        }
                    });
                } else {
                    holder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Intent i = new Intent(c, TambahPegawaiActivity.class);
                            i.putExtra("pegawai", model);
                            i.putExtra("pegawai", pegawai);
                            i.putExtra("idForPegawai", key);
                            c.startActivity(i);
                        }
                    });
                }
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
            public PegawaiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item_pegawai, viewGroup, false);
                Log.i("Buat View Holder", view.toString());
                return new PegawaiViewHolder(view);
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();

                if (adapter.getItemCount() > 0){
                    imageLayout.setVisibility(View.GONE);
                }
            }
        };


        rvPegawai.setAdapter(adapter);


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
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.im_cancel:
                finish();
                break;
            case R.id.fab_pegawai:
                Toast.makeText(PegawaiActivity.this, "Tambah Pegawai", Toast.LENGTH_SHORT).show();
                Intent fab_pegawai = new Intent(PegawaiActivity.this, TambahPegawaiActivity.class);
                startActivity(fab_pegawai);
                break;
        }
    }

    void showDeleteDialog(final String key, final Pegawai pegawai){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Hapus Data");
        alertDialog.setMessage("Apakah anda ingin menghapus Pegawai ini ? ");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "YA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference("Pegawai")
                                .child(key)
                                .removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(PegawaiActivity.this, "item "+pegawai.getNamapengguna() +" telah terhapus", Toast.LENGTH_SHORT).show();
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
