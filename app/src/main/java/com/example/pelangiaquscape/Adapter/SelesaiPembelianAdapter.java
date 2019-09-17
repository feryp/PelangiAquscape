package com.example.pelangiaquscape.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pelangiaquscape.Model.Pembelian;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.ViewHolder.SelesaiPembelianViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SelesaiPembelianAdapter extends RecyclerView.Adapter<SelesaiPembelianViewHolder> {

    List<Pembelian> listPembelian;
    List<Pembelian> filterPembelianSelesai = new ArrayList<>();
    List<String> listKey = new ArrayList<>();
    List<String> filterKey = new ArrayList<>();
    Context context;

    public SelesaiPembelianAdapter(List<Pembelian> listPembelian, List<String> listKey, Context context) {
        this.listPembelian = listPembelian;
        this.listKey = listKey;
        this.context = context;

        for (Pembelian pembelian : listPembelian) {
            if (!pembelian.getProses()) {
                filterPembelianSelesai.add(pembelian);
                filterKey.add(listKey.get(listPembelian.indexOf(pembelian)));
            }
        }
    }

    @NonNull
    @Override
    public SelesaiPembelianViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item_selesai_pembelian, viewGroup, false);
        return new SelesaiPembelianViewHolder(v, context);

    }

    @Override
    public void onBindViewHolder(@NonNull SelesaiPembelianViewHolder holder, int i) {


        holder.bindData(filterPembelianSelesai.get(i), filterKey.get(i));

    }

    @Override
    public int getItemCount() {
        return filterPembelianSelesai.size();
    }
}
