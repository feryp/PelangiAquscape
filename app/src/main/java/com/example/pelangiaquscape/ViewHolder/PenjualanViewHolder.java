package com.example.pelangiaquscape.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class PenjualanViewHolder extends RecyclerView.ViewHolder {


    TextView tvTotalHargaPenjualan,
            tvHargaPenjualan,
            noStrukPenjualan,
            ketaranganPembayaran,
            tvTanggalPenjualan,
            tvWaktuPenjualan;




    public PenjualanViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
