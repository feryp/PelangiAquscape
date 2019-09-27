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

public class DetailSelesaiPembelianViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private ItemKeranjang itemKeranjang;

    private TextView tvKode, tvMerek, tvHargaSatuan, tvQty, tvTotalHarga;

    SharedPreferences sharedPreferences;

    public DetailSelesaiPembelianViewHolder(@NonNull View view, Context context) {
        super(view);
        this.context = context;
        tvKode = view.findViewById(R.id.list_kode_barang_pembelian);
        tvMerek = view.findViewById(R.id.list_merek_barang_pembelian);
        tvHargaSatuan = view.findViewById(R.id.list_harga_barang);
        tvQty = view.findViewById(R.id.list_kuantitas_barang);
        tvTotalHarga = view.findViewById(R.id.list_total_harga_barang);
        sharedPreferences = context.getSharedPreferences("MEREK_KEY", Context.MODE_PRIVATE);
    }

    public void BindData(ItemKeranjang itemKeranjang){
        this.itemKeranjang = itemKeranjang;
        int noMerek = Integer.valueOf(itemKeranjang.getMerek());
        String merek = sharedPreferences.getString(String.valueOf(noMerek), "unknown");
        tvKode.setText(itemKeranjang.getKode());
        tvMerek.setText(merek);
        BigDecimal bigDecimal = new BigDecimal(itemKeranjang.getHargaBeli());
        tvHargaSatuan.setText(bigDecimal.toString());
        tvQty.setText(String.valueOf(itemKeranjang.getQty()));
        tvTotalHarga.setText(bigDecimal.multiply(new BigDecimal(itemKeranjang.getQty())).toString());

    }


}
