package com.example.pelangiaquscape.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.ViewHolder.DetailPenjualanViewHolder;

import java.util.List;


public class DetailPenjualanAdapter extends RecyclerView.Adapter<DetailPenjualanViewHolder> {

    List<ItemKeranjang> listItem;
    Context context;

    public DetailPenjualanAdapter(List<ItemKeranjang> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public DetailPenjualanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_detail_penjualan, viewGroup, false);
        return new DetailPenjualanViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailPenjualanViewHolder detailPenjualanViewHolder, int i) {
        detailPenjualanViewHolder.bindData(listItem.get(i));
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }
}
