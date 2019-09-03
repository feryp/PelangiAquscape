package com.example.pelangiaquscape.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pelangiaquscape.Model.Barang;
import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.ViewHolder.ItemPembelianViewHolder;

import java.util.List;

public class ItemPembelianAdapter extends RecyclerView.Adapter<ItemPembelianViewHolder> {

    List<ItemKeranjang> listItemBarang;
    Context context;

    public ItemPembelianAdapter(List<ItemKeranjang> listItemBarang, Context context) {
        this.listItemBarang = listItemBarang;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemPembelianViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_form_barang_pesanan, viewGroup, false);
        return new ItemPembelianViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemPembelianViewHolder holder, int i) {
        holder.bindData(listItemBarang.get(i));
    }

    @Override
    public int getItemCount() {
        return listItemBarang.size();
    }
}
