package com.example.pelangiaquscape.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pelangiaquscape.Model.Pembelian;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.ViewHolder.PenerimaanViewHolder;
import com.example.pelangiaquscape.ViewHolder.SelesaiPembelianViewHolder;

import java.util.ArrayList;
import java.util.List;

public class PenerimaanAdapter extends RecyclerView.Adapter<PenerimaanViewHolder> {
    List<Pembelian> listPembelian;
    List<String> listKey = new ArrayList<>();
    Context context;

    public PenerimaanAdapter(List<Pembelian> listPembelian, List<String> listKey, Context context) {
        this.listPembelian = listPembelian;
        this.listKey = listKey;
        this.context = context;
    }

    @NonNull
    @Override
    public PenerimaanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item_riwayat_pembelian, viewGroup, false);
        return new PenerimaanViewHolder(v, context);

    }

    @Override
    public void onBindViewHolder(@NonNull PenerimaanViewHolder holder, int i) {
        holder.bindData(listPembelian.get(i), listKey.get(i));

    }

    @Override
    public int getItemCount() {
        return listPembelian.size();
    }
}
