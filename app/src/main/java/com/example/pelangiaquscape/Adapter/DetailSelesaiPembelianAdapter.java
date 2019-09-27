package com.example.pelangiaquscape.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.ViewHolder.DetailSelesaiPembelianViewHolder;

import java.util.List;

public class DetailSelesaiPembelianAdapter extends RecyclerView.Adapter<DetailSelesaiPembelianViewHolder> {

   List<ItemKeranjang> listItemBarang;
   Context context;

    public DetailSelesaiPembelianAdapter(List<ItemKeranjang> listItemBarang, Context context) {
        this.listItemBarang = listItemBarang;
        this.context = context;
    }

    @NonNull
    @Override
    public DetailSelesaiPembelianViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_detail_barang_pembelian, viewGroup, false);
        return new DetailSelesaiPembelianViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailSelesaiPembelianViewHolder holder, int i) {
        holder.BindData(listItemBarang.get(i));
    }

    @Override
    public int getItemCount() {
        return listItemBarang.size();
    }
}
