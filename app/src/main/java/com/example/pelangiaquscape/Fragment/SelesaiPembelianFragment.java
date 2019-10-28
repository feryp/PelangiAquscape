package com.example.pelangiaquscape.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.pelangiaquscape.Adapter.SelesaiPembelianAdapter;
import com.example.pelangiaquscape.Model.Pembelian;
import com.example.pelangiaquscape.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SelesaiPembelianFragment extends Fragment {

    private LinearLayout ll;
    private RecyclerView rvItemSelesai;

    FirebaseDatabase database;
    DatabaseReference reference;

    List<Pembelian> listPembelian;
    List<String> listKey;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // INIT VIEW
        View v = inflater.inflate(R.layout.fragment_selesai_pembelian, container, false);
        ll = v.findViewById(R.id.linear_pembelian_selesai);
        rvItemSelesai = v.findViewById(R.id.rv_selesai_pembelian);
        rvItemSelesai.setHasFixedSize(true);
        rvItemSelesai.setLayoutManager(new LinearLayoutManager(getContext()));

//        // INIT FIREBASE
//        database = FirebaseDatabase.getInstance();
//        reference = database.getReference("Pembelian");
        loadPembelian();

        return v;
    }

    public void loadPembelian(){
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference("Pembelian");
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // RESET LIST
                listPembelian = new ArrayList<>();
                listKey = new ArrayList<>();

                boolean test = false;
                for(DataSnapshot snapShot: dataSnapshot.getChildren()){
                    listKey.add(dataSnapshot.getKey());
                    listPembelian.add(snapShot.getValue(Pembelian.class));

                    if(!snapShot.getValue(Pembelian.class).getProses()){
                        test = true;
                    }

                }

                if(test){
                    SelesaiPembelianAdapter adapter = new SelesaiPembelianAdapter(listPembelian, listKey, getContext());
                    rvItemSelesai.setAdapter(adapter);

                    if(adapter.getItemCount() > 0){

                        ll.setVisibility(View.GONE);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
