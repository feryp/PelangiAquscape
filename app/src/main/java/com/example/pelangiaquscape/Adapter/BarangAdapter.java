package com.example.pelangiaquscape.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.Model.Barang;
import com.example.pelangiaquscape.Model.Merek;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.TambahBarangActivity;
import com.example.pelangiaquscape.ViewHolder.BarangViewHolder;

import java.util.List;

public class BarangAdapter extends RecyclerView.Adapter<BarangViewHolder> {

    Context context;
    List<Barang> barang;
    List<String> merek;


    public BarangAdapter(Context context, List<Barang> barang, List<String> merek) {
        this.context = context;
        this.barang = barang;
        this.merek = merek;
    }

    @NonNull
    @Override
    public BarangViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_barang, viewGroup, false);
        return new BarangViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BarangViewHolder holder, int i) {
        holder.bindData(barang.get(i), merek);
        holder.setOnClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent i = new Intent(context, TambahBarangActivity.class);
                i.putExtra("idBarang", position+1);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return barang.size();
    }
}
