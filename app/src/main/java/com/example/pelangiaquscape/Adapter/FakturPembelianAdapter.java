package com.example.pelangiaquscape.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.ViewHolder.FakturPembelianViewHolder;

import java.util.List;

public class FakturPembelianAdapter extends RecyclerView.Adapter<FakturPembelianViewHolder> {

    List<ItemKeranjang> listKeranjang;
    Context context;

    public FakturPembelianAdapter(List<ItemKeranjang> listKeranjang, Context context) {
        this.listKeranjang = listKeranjang;
        this.context = context;
    }

    @NonNull
    @Override
    public FakturPembelianViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_faktur, viewGroup, false);
        return new FakturPembelianViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull FakturPembelianViewHolder fakturPembelianViewHolder, int i) {
        fakturPembelianViewHolder.bindData(listKeranjang.get(i));
    }

    @Override
    public int getItemCount() {
        return listKeranjang.size();
    }
}
