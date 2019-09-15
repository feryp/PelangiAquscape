package com.example.pelangiaquscape.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.Model.Pembelian;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.ViewHolder.ProsesPembelianViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProsesPembelianAdapter extends RecyclerView.Adapter<ProsesPembelianViewHolder> {

    List<Pembelian> listPembelian;
    List<Pembelian> filterPembelianProses = new ArrayList<>();
    List<String> listKey = new ArrayList<>();
    List<String> filterKey = new ArrayList<>();
    Context context;

    HashMap a = new HashMap<>();

    public ProsesPembelianAdapter(List<Pembelian> listPembelian,List<String> listKey, Context context) {
        this.listPembelian = listPembelian;
        this.listKey = listKey;
        this.context = context;

        for(Pembelian pembelian: listPembelian){
            if(pembelian.getProses()){
                filterPembelianProses.add(pembelian);
                filterKey.add(listKey.get(listPembelian.indexOf(pembelian)));
            }
        }

    }

    public void setListPembelian(List<Pembelian> listPembelian) {
        this.listPembelian = listPembelian;
    }

    @NonNull
    @Override
    public ProsesPembelianViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(context).inflate(R.layout.list_item_proses_pembelian, viewGroup, false);
            return new ProsesPembelianViewHolder(v,context);

    }

    @Override
    public void onBindViewHolder(@NonNull ProsesPembelianViewHolder holder, int i) {
        holder.bindData(filterPembelianProses.get(i), filterKey.get(i));

    }

    @Override
    public int getItemCount() {
        return filterPembelianProses.size();
    }
}
