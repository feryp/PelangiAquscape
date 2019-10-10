package com.example.pelangiaquscape.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.ViewHolder.BatalkanTransaksiViewHolder;

import java.util.List;

public class BatalkanTransaksiAdapter extends RecyclerView.Adapter<BatalkanTransaksiViewHolder> {

    List<ItemKeranjang> listItemBarang;
    Context context;

    public BatalkanTransaksiAdapter(List<ItemKeranjang> listItemBarang, Context context) {
        this.listItemBarang = listItemBarang;
        this.context = context;
    }


    @NonNull
    @Override
    public BatalkanTransaksiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_barang_dibatalkan, viewGroup, false);
        return new BatalkanTransaksiViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull BatalkanTransaksiViewHolder batalkanTransaksiViewHolder, int i) {
        batalkanTransaksiViewHolder.bindData(listItemBarang.get(i));
    }

    @Override
    public int getItemCount() {
        return listItemBarang.size();
    }
}
