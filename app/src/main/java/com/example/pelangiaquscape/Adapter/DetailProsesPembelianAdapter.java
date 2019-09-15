package com.example.pelangiaquscape.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.ViewHolder.DetailProsesPembelianViewHolder;

import java.util.List;

public class DetailProsesPembelianAdapter extends RecyclerView.Adapter<DetailProsesPembelianViewHolder> {

    List<ItemKeranjang> listItem;
    Context context;

    public DetailProsesPembelianAdapter(List<ItemKeranjang> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public DetailProsesPembelianViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_detail_barang_pembelian, viewGroup, false);
        return new DetailProsesPembelianViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailProsesPembelianViewHolder holder, int i) {
        holder.bindData(listItem.get(i));
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }
}
