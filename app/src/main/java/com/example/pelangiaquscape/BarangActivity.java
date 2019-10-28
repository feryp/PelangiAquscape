package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.pelangiaquscape.Adapter.BarangAdapter;
import com.example.pelangiaquscape.Interface.MerekCallback;
import com.example.pelangiaquscape.Model.Barang;
import com.example.pelangiaquscape.Model.Merek;
import com.example.pelangiaquscape.ViewHolder.BarangViewHolder;
import com.firebase.ui.database.FirebaseArray;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.ObservableSnapshotArray;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BarangActivity extends AppCompatActivity {

    ImageView cancel;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView rvBarang;
    Query q;
    LinearLayout imageLayout;
    FloatingActionButton fab_barang;
    SearchView searchView;

    List<String> merek = new ArrayList<>();
    private MerekCallback callback;
    private SwipeRefreshLayout swipeRefresh;
    BarangAdapter adapter;

    boolean fromTambahPenyimpananActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang);

        // GET INTENT
        Intent i= getIntent();
        try {
            fromTambahPenyimpananActivity = i.getExtras().getBoolean("fromTambahPenyimpananActivity", false);
        }catch(NullPointerException e){

        }
        // INIT VIEW
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Barang");
        rvBarang = findViewById(R.id.rv_merek_barang);
        rvBarang.setHasFixedSize(true);
        rvBarang.setLayoutManager(new LinearLayoutManager(this));
        cancel = findViewById(R.id.im_cancel);
        fab_barang = findViewById(R.id.fab_barang);
        imageLayout = findViewById(R.id.linear_imageview);
        searchView = findViewById(R.id.search_merek_persediaan_barang);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        // END INIT VIEW

        readData(new MerekCallback() {
            @Override
            public void readMerek(List<String> listMerek) {
                for (String a : listMerek) {
                    Log.v("testmerek", a);
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchFirebase(s);
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                loadBarang();
                return false;
            }
        });


        fab_barang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BarangActivity.this, "Tambah Barang", Toast.LENGTH_SHORT).show();
                Intent fabBarang = new Intent(BarangActivity.this, TambahBarangActivity.class);
                fabBarang.putExtra("sizeOfListBarang",adapter.getItemCount());
                startActivity(fabBarang);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(true);
                loadBarang();

            }
        });

        swipeRefresh.setRefreshing(true);
        loadBarang();


    }

    private void searchFirebase(String searchText) {
        Log.i("MASUK KE SEARCH", "hehe");
        q = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Barang")
                .orderByChild("nama").startAt(searchText.toUpperCase()).endAt(searchText.toUpperCase() + "\uf8ff");

        FirebaseRecyclerOptions<Merek> options =
                new FirebaseRecyclerOptions.Builder<Merek>()
                .setQuery(q, Merek.class)
                .build();

        Log.i("SNAPSHOT", options.getSnapshots().toString());

        FirebaseDatabase.getInstance().getReference().child("Barang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<Barang> listBarang = new ArrayList<>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {

//                    ObservableSnapshotArray<Barang> a = new FirebaseArray<>();
//                    Log.v("KEYKEYKEY", snap.getKey());
                    Barang bbe = snap.getValue(Barang.class);
                    String merek = snap.child("merek").getValue(String.class);
//                    bbe.setMerek(merek);


                    Log.v("MEREK", bbe.getMerek());
                    listBarang.add(bbe);
//                    Log.v("KEYKEYKEYMODEL", bbe.getKode());

                }

                adapter = new BarangAdapter(BarangActivity.this, listBarang, merek, fromTambahPenyimpananActivity);
                rvBarang.setAdapter(adapter);
                if (adapter.getItemCount() > 0) {
                    imageLayout.setVisibility(View.GONE);
                } else {
                    imageLayout.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    void readData(final MerekCallback callback) {
        FirebaseDatabase.getInstance().getReference("Merek").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                List<Merek> merek = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String m = ds.child("nama").getValue(String.class);
                    merek.add(m);
                }

                callback.readMerek(merek);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    void loadBarang() {
        FirebaseDatabase.getInstance().getReference().child("Barang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<Barang> listBarang = new ArrayList<>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {

                    Barang bbe = snap.getValue(Barang.class);
                    String merek = snap.child("merek").getValue(String.class);
                    Log.v("MEREK", bbe.getMerek());
                    listBarang.add(bbe);
                }
                adapter = new BarangAdapter(BarangActivity.this, listBarang, merek, fromTambahPenyimpananActivity);
                rvBarang.setAdapter(adapter);
                if (adapter.getItemCount() > 0) {
                    imageLayout.setVisibility(View.GONE);
                } else {
                    imageLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        swipeRefresh.setRefreshing(false);

    }

//       Log.v("QUERYAN", q.getPath().toString());

//        FirebaseRecyclerOptions<Barang> options =
//                new FirebaseRecyclerOptions.Builder<Barang>().set.setQuery(q, Barang.class).build();

//        Log.v("coba bgst", options.getSnapshots().toString() + " ");
//
//        FirebaseRecyclerAdapter<Barang, BarangViewHolder> adapter = new FirebaseRecyclerAdapter<Barang, BarangViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull BarangViewHolder holder, int position, @NonNull Barang model) {
//                Log.v("test model", model.getKode());
//                holder.bindData(model);
//            }
//
//            @NonNull
//            @Override
//            public BarangViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

//            }
//        };

//        rvBarang.setAdapter(adapter);
}
