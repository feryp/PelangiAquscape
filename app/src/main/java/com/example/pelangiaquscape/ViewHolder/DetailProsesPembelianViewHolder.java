package com.example.pelangiaquscape.ViewHolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.R;

import java.math.BigDecimal;

public class DetailProsesPembelianViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private ItemKeranjang itemKeranjang;

    private TextView tvKode, tvMerek, tvHargaSatuan, tvQty, tvTotalHarga;

    public DetailProsesPembelianViewHolder(@NonNull View v, Context c) {
        super(v);
        this.context = c;
        tvKode = v.findViewById(R.id.list_kode_barang_pembelian);
        tvMerek = v.findViewById(R.id.list_merek_barang_pembelian);
        tvHargaSatuan = v.findViewById(R.id.list_harga_barang);
        tvQty = v.findViewById(R.id.list_kuantitas_barang);
        tvTotalHarga = v.findViewById(R.id.list_total_harga_barang);

    }

    public void bindData(ItemKeranjang itemKeranjang) {
        this.itemKeranjang = itemKeranjang;
        tvKode.setText(itemKeranjang.getKode());
        tvMerek.setText(itemKeranjang.getMerek());
        BigDecimal bg = new BigDecimal(itemKeranjang.getHargaBeli());
        tvHargaSatuan.setText(bg.toString());
        tvQty.setText(String.valueOf(itemKeranjang.getQty()));

        tvTotalHarga.setText(bg.multiply(new BigDecimal(itemKeranjang.getQty())).toString());

    }

}
