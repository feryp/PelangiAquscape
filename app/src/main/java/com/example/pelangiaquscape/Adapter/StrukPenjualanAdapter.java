package com.example.pelangiaquscape.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.ViewHolder.StrukPenjualanViewHolder;

import java.util.List;

public class StrukPenjualanAdapter extends RecyclerView.Adapter<StrukPenjualanViewHolder> {

    List<ItemKeranjang> keranjangList;
    Context context;

    public StrukPenjualanAdapter(List<ItemKeranjang> keranjangList, Context context) {
        this.keranjangList = keranjangList;
        this.context = context;
    }

    @NonNull
    @Override
    public StrukPenjualanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_struk_barang, viewGroup, false);
        return new StrukPenjualanViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull StrukPenjualanViewHolder strukPenjualanViewHolder, int i) {
        strukPenjualanViewHolder.bindData(keranjangList.get(i));
    }

    @Override
    public int getItemCount() {
        return keranjangList.size();
    }
}
