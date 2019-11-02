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

    public PemberitahuanAdapter(Context context, List<Pemberitahuan> pemberitahuan) {
        this.context = context;
        this.listPemberitahuan = pemberitahuan;
    }

    @NonNull
    @Override
    public PemberitahuanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_pemberitahuan_keluar, viewGroup, false);

        return new PemberitahuanViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PemberitahuanViewHolder holder, int i) {
        holder.bindP(listPemberitahuan.get(i));
    }

    @Override
    public int getItemCount() {
        return listPemberitahuan.size();
    }
}
