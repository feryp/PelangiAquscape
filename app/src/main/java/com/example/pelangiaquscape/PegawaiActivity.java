package com.example.pelangiaquscape;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelangiaquscape.Adapter.PegawaiAdapter;
import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.Interface.NamaPegawai;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PegawaiActivity extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener {

    ImageView cancel;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    RecyclerView rvPegawai;

    Query q;
    Context c;

    LinearLayout imageLayout;
    List<String> namapegawai = new ArrayList<>();
    SearchView searchView;
    RecyclerView.LayoutManager layoutManager;
    FloatingActionButton fab_pegawai;
    boolean fromTambahPembelianActivity;
    Query query;
    FirebaseRecyclerAdapter adapter;
    PegawaiAdapter pegawaiAdapter;
    private Pegawai pegawai;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegawai);

        // INIT VIEW
        cancel = findViewById(R.id.im_cancel);
        fab_pegawai = findViewById(R.id.fab_pegawai);
        imageLayout = findViewById(R.id.linear_imageview_pegawai);
        rvPegawai = findViewById(R.id.rv_pegawai);
        searchView = findViewById(R.id.search_data_pegawai);
        rvPegawai.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        rvPegawai.setLayoutManager(layoutManager);

        //INIT FIREBASE
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Pegawai");


        // REGISTER LISTENER
        cancel.setOnClickListener(this);
        fab_pegawai.setOnClickListener(this);

        readData(new NamaPegawai() {
            @Override
            public void readNamaPegawai(List<String> listNamaPegawai) {
                for (String pegawai : listNamaPegawai) {
                    Log.v("testpegawai", pegawai);
                }
            }
        });


        searchView.setOnQueryTextListener(this);

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                loadPegawai();
                return true;
            }
        });

        // LOAD PEGAWAI FROM FIREBASE
        loadPegawai();


    }

    void readData(final NamaPegawai callback) {
        FirebaseDatabase.getInstance().getReference("Pegawai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String p = ds.child("namaPegawai").getValue(String.class);
                    namapegawai.add(p);

                }
                callback.readNamaPegawai(namapegawai);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadPegawai() {
        query = FirebaseDatabase.getInstance().getReference().child("Pegawai").orderByChild("namaPegawai");
        FirebaseRecyclerOptions<Pegawai> options =
                new FirebaseRecyclerOptions.Builder<Pegawai>().setQuery(query, Pegawai.class).build();
        adapter = new FirebaseRecyclerAdapter<Pegawai, PegawaiViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PegawaiViewHolder holder, int position, @NonNull final Pegawai model) {
                holder.bindData(model);
                imageLayout.setVisibility(View.GONE);

                final Pegawai clickItem = model;

                final int size = this.getItemCount();


                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent pegawai = new Intent(PegawaiActivity.this, TambahPegawaiActivity.class);
                        pegawai.putExtra("pegawai", model);
                        System.out.println("pegawai activity : " + model.toString());

                        pegawai.putExtra("fromPegawaiActivity", true);
                        pegawai.putExtra("idPegawai", adapter.getRef(holder.getAdapterPosition()).getKey());

                        System.out.println("ID Pegawai " + adapter.getRef(position).getKey());
                        startActivity(pegawai);
                    }
                });

                holder.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        showDeleteDialog(String.valueOf(adapter.getRef(position).getKey()), model);
                        return false;
                    }
                });
            }

            @NonNull
            @Override
            public PegawaiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.list_item_pegawai, viewGroup, false);

                return new PegawaiViewHolder(view);
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();

                if (adapter.getItemCount() > 0) {
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
        switch (v.getId()) {
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

    void showDeleteDialog(final String key, final Pegawai pegawai) {
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
                                        Toast.makeText(PegawaiActivity.this, "item " + pegawai.getNamapengguna() + " telah terhapus", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onQueryTextSubmit(String s) {
//        if (s.equals("")) {
//            loadPegawai();
//        }

//        String kata = s.substring(0, 1).toUpperCase() + s.substring(1);
//
//        Query firebaseSearchQuery = FirebaseDatabase.getInstance().getReference().child("Pegawai").orderByChild("namaPegawai").startAt(kata).endAt(kata + "\uf8ff");
//
//        //set Options
//        FirebaseRecyclerOptions<Pegawai> options =
//                new FirebaseRecyclerOptions.Builder<Pegawai>()
//                        .setQuery(firebaseSearchQuery, Pegawai.class)
//                        .setLifecycleOwner(this)
//                        .build();
//
//        FirebaseRecyclerAdapter adaptersearch = new FirebaseRecyclerAdapter<Pegawai, PegawaiSearchviewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull PegawaiSearchviewHolder holder, int position, @NonNull Pegawai model) {
//
//                holder.setNama(model.getNamaPegawai());
//                holder.setJabatan(model.getJabatan());
//
//                FirebaseStorage storage = FirebaseStorage.getInstance();
//                StorageReference storageRef = storage.getReference();
//                StorageReference pegawaiRef = storageRef.child("Profile").child(model.getId() + ".jpg");
//                pegawaiRef.getDownloadUrl().addOnSuccessListener(uri -> {
//
//                    try {
//                        if (uri != null){
//                            Picasso.get().load(uri).into(holder.im_foto_pegawai);
//                        }
//
//                    } catch (IllegalArgumentException e){
//                        holder.im_foto_pegawai.setImageResource(R.drawable.pegawai);
//                    }
//                });
//
//                holder.mView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent pegawai = new Intent(PegawaiActivity.this, TambahPegawaiActivity.class);
//                        pegawai.putExtra("pegawai", model);
//                        System.out.println("pegawai activity : " + model.toString());
//
//                        pegawai.putExtra("fromPegawaiActivity", true);
//                        pegawai.putExtra("idPegawai", adapter.getRef(holder.getAdapterPosition()).getKey());
//
//                        System.out.println("ID Pegawai " + adapter.getRef(position).getKey());
//                        startActivity(pegawai);
//                    }
//                });
//
//                holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View v) {
//                        showDeleteDialog(String.valueOf(adapter.getRef(position).getKey()), model);
//                        return false;
//                    }
//                });
//            }
//
//            @NonNull
//            @Override
//            public PegawaiSearchviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pegawai, parent, false);
//                return new PegawaiSearchviewHolder(mView);
//            }
//        };
//
//        rvPegawai.setAdapter(adaptersearch);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
//        if (s.equals("")) {
//            loadPegawai();
//        }
//
//        return true;
//        if (s.equals("")) {
//            loadPegawai();
//        }

        String kata = null;
        try {
            kata = s.substring(0,1).toUpperCase() + s.substring(1);
        } catch (StringIndexOutOfBoundsException e){
            loadPegawai();
        }

        Query firebaseSearchQuery = FirebaseDatabase.getInstance().getReference().child("Pegawai").orderByChild("namaPegawai").startAt(kata).endAt(kata + "\uf8ff");

        //set Options
        FirebaseRecyclerOptions<Pegawai> options =
                new FirebaseRecyclerOptions.Builder<Pegawai>()
                        .setQuery(firebaseSearchQuery, Pegawai.class)
                        .setLifecycleOwner(this)
                        .build();

        FirebaseRecyclerAdapter adaptersearch = new FirebaseRecyclerAdapter<Pegawai, PegawaiSearchviewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PegawaiSearchviewHolder holder, int position, @NonNull Pegawai model) {

                holder.setNama(model.getNamaPegawai());
                holder.setJabatan(model.getJabatan());

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();
                StorageReference pegawaiRef = storageRef.child("Profile").child(model.getId() + ".jpg");
                pegawaiRef.getDownloadUrl().addOnSuccessListener(uri -> {

                    try {
                        if (uri != null){
                            Picasso.get().load(uri).into(holder.im_foto_pegawai);
                        }

                    } catch (IllegalArgumentException e){
                        holder.im_foto_pegawai.setImageResource(R.drawable.pegawai);
                    }
                });

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent pegawai = new Intent(PegawaiActivity.this, TambahPegawaiActivity.class);
                        pegawai.putExtra("pegawai", model);
                        System.out.println("pegawai activity : " + model.toString());

                        pegawai.putExtra("fromPegawaiActivity", true);
                        pegawai.putExtra("idPegawai", adapter.getRef(holder.getAdapterPosition()).getKey());

                        System.out.println("ID Pegawai " + adapter.getRef(position).getKey());
                        startActivity(pegawai);
                    }
                });

                holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        showDeleteDialog(String.valueOf(adapter.getRef(position).getKey()), model);
                        return false;
                    }
                });
            }

            @NonNull
            @Override
            public PegawaiSearchviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pegawai, parent, false);
                return new PegawaiSearchviewHolder(mView);
            }
        };

        rvPegawai.setAdapter(adaptersearch);


        return true;
    }

    //VIEW HOLDER
    public class PegawaiSearchviewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView mdisplayname, mJabatan;
        ImageView im_foto_pegawai;


        public PegawaiSearchviewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            mdisplayname = mView.findViewById(R.id.tv_nama_pegawai);
            mJabatan = mView.findViewById(R.id.tv_jabatan);
            im_foto_pegawai = itemView.findViewById(R.id.foto_pegawai);
        }

        public void setNama(String display_name) {

            mdisplayname.setText(display_name);
        }

        public void setJabatan(String j) {
            mJabatan.setText(j);
        }
    }

}
