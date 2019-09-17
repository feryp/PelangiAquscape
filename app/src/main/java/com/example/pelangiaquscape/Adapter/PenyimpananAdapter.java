package com.example.pelangiaquscape.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pelangiaquscape.Model.Penyimpanan;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.ViewHolder.PenyimpananViewHolder;

import java.util.List;

public class PenyimpananAdapter extends RecyclerView.Adapter<PenyimpananViewHolder> {

    Context context;
    List<Penyimpanan> listPenyimpanan;
    List<String> listKey;

    public PenyimpananAdapter(Context context, List<Penyimpanan> penyimpanan, List<String> key) {
        this.context = context;
        this.listPenyimpanan = penyimpanan;
        this.listKey = key;
    }

    @NonNull
    @Override
    public PenyimpananViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_inventory_barangkeluar, viewGroup, false);

        return new PenyimpananViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PenyimpananViewHolder holder, int i) {
        holder.bindData(listPenyimpanan.get(i), listKey.get(i));

    }

    @Override
    public int getItemCount() {
        return listPenyimpanan.size();
    }
}
