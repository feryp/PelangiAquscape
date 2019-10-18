package com.example.pelangiaquscape.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pelangiaquscape.Model.Barang;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.ViewHolder.ProdukTidakLakuViewHolder;

import java.util.List;

public class ProdukTidakLakuAdapter extends RecyclerView.Adapter<ProdukTidakLakuViewHolder> {

    Context context;
    List<Barang> barangList;
    boolean fromTambahPenyimpananActivity;

    public ProdukTidakLakuAdapter(Context context, List<Barang> barangList, boolean fromTambahPenyimpananActivity) {
        this.context = context;
        this.barangList = barangList;
        this.fromTambahPenyimpananActivity = fromTambahPenyimpananActivity;
    }

    @NonNull
    @Override
    public ProdukTidakLakuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_produk_tidak_laku, viewGroup, false);
        return new ProdukTidakLakuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdukTidakLakuViewHolder produkTidakLakuViewHolder, final int i) {
        produkTidakLakuViewHolder.bindProduk(barangList.get(i));
    }

    @Override
    public int getItemCount() {
        return barangList.size();
    }
}
