package com.example.pelangiaquscape.ViewHolder;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.Interface.OnItemClickListener;
import com.example.pelangiaquscape.Model.Barang;
import com.example.pelangiaquscape.R;

import java.util.List;

public class BarangViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView tvKode, tvMerek, tvStok, tvSatuan, tvHargaJual, tvHargaBeli;

    private Barang barang;

    ItemClickListener listener;

    public BarangViewHolder(@NonNull View v) {
        super(v);
        tvKode = v.findViewById(R.id.kode_persediaan_barang);
        tvMerek = v.findViewById(R.id.merek_persediaan_barang);
        tvStok = v.findViewById(R.id.stok_barang);
        tvSatuan = v.findViewById(R.id.satuan_unit_barang);
        tvHargaBeli = v.findViewById(R.id.harga_modal_barang);
        tvHargaJual = v.findViewById(R.id.harga_jual_barang);
        v.setOnClickListener(this);
    }

    public void bindData(Barang barang, List<String> listMerek){
        this.barang = barang;
        tvKode.setText(barang.getKode());
        int mer = Integer.parseInt(barang.getMerek())-1;
        String merek = listMerek.get(mer);
        tvMerek.setText(merek);
        tvStok.setText("");
        tvSatuan.setText(barang.getSatuan());
        tvHargaBeli.setText(String.valueOf(barang.getHargaBeli()));
        tvHargaJual.setText(String.valueOf(barang.getHargaJual()));
    }

    public void setOnClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v, getAdapterPosition(), false);
    }
}
