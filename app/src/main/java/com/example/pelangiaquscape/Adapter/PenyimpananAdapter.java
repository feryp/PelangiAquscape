package com.example.pelangiaquscape.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.pelangiaquscape.Model.Penyimpanan;
import com.example.pelangiaquscape.ViewHolder.PenyimpananViewHolder;

import java.util.List;

public class PenyimpananAdapter extends RecyclerView.Adapter<PenyimpananViewHolder> {

    Context context;
    List<Penyimpanan> penyimpanan;

    public PenyimpananAdapter(Context context, List<Penyimpanan> penyimpanan) {
        this.context = context;
        this.penyimpanan = penyimpanan;
    }

    @NonNull
    @Override
    public PenyimpananViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PenyimpananViewHolder penyimpananViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
