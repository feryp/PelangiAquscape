package com.example.pelangiaquscape.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pelangiaquscape.R;

public class KeranjangViewHolder extends RecyclerView.ViewHolder {


    public TextView tvKode, tvMerek, tvHarga, tvQty, tvTotalHarga;

    public KeranjangViewHolder(@NonNull View iv) {
        super(iv);

        tvKode = iv.findViewById(R.id.tv_kode);
        tvMerek = iv.findViewById(R.id.tv_merek);
        tvQty = iv.findViewById(R.id.tv_jumlah_kuantitas);
        tvHarga = iv.findViewById(R.id.tv_harga_barang);

    }
}
