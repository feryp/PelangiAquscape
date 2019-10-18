package com.example.pelangiaquscape.ViewHolder;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.load.engine.Resource;
import com.example.pelangiaquscape.Model.Barang;
import com.example.pelangiaquscape.R;

import java.util.List;

public class ProdukTidakLakuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView tvKode;

    private Barang barang;

    public ProdukTidakLakuViewHolder(@NonNull View view) {
        super(view);
        tvKode = view.findViewById(R.id.tv_kode_barang_tidak_laku);

    }


    public void bindProduk(Barang barang) {
        this.barang = barang;
        tvKode.setText(barang.getKode());

    }

    @Override
    public void onClick(View v) {

    }

}
