package com.example.pelangiaquscape.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pelangiaquscape.Model.Pemberitahuan;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.ViewHolder.PemberitahuanViewHolder;

import java.util.List;

public class PemberitahuanAdapter extends RecyclerView.Adapter<PemberitahuanViewHolder> {

    Context context;
    List<Pemberitahuan> listPemberitahuan;
    List<String> listKey;

    public PemberitahuanAdapter(Context context, List<Pemberitahuan> pemberitahuan, List<String> key) {
        this.context = context;
        this.listPemberitahuan = pemberitahuan;
        this.listKey = key;
    }

    @NonNull
    @Override
    public PemberitahuanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item_pemberitahuan_masuk, viewGroup, false);

        return new PemberitahuanViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(@NonNull PemberitahuanViewHolder holder, int i) {
        holder.bind(listPemberitahuan.get(i));
    }

    @Override
    public int getItemCount() {
        return listPemberitahuan.size();
    }
}
