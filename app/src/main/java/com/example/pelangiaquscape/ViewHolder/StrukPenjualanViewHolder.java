package com.example.pelangiaquscape.ViewHolder;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.R;

import java.math.BigDecimal;

public class StrukPenjualanViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private ItemKeranjang keranjang;
    private TextView tvKode, tvMerek, tvHargaSatuan, tvQty, tvTotalHarga;

    SharedPreferences sharedPreferences;


    public StrukPenjualanViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        tvKode = itemView.findViewById(R.id.tv_kode_barang_struk);
        tvMerek = itemView.findViewById(R.id.tv_merek_barang_struk);
        tvHargaSatuan = itemView.findViewById(R.id.tv_harga_barang_struk);
        tvQty = itemView.findViewById(R.id.tv_kuantitas_barang_struk);
        tvTotalHarga = itemView.findViewById(R.id.total_harga_barang_struk);
        sharedPreferences = context.getSharedPreferences("MEREK_KEY", Context.MODE_PRIVATE);

    }


    public void bindData(ItemKeranjang itemKeranjang) {
        this.keranjang = itemKeranjang;
        int noMerek = Integer.valueOf(itemKeranjang.getMerek());
//        String merek = preferences.getString(String.valueOf(noMerek), "unknown");
        tvKode.setText(itemKeranjang.getKode());
//        tvMerek.setText(merek);
        BigDecimal bg = new BigDecimal(itemKeranjang.getHargaJual());
        tvHargaSatuan.setText(bg.toString());
        tvQty.setText(String.valueOf(itemKeranjang.getQty()));

        tvTotalHarga.setText(bg.multiply(new BigDecimal(itemKeranjang.getQty())).toString());
    }
}
