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
import java.text.DecimalFormat;

public class DetailProsesPembelianViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private ItemKeranjang itemKeranjang;

    private TextView tvKode, tvMerek, tvHargaSatuan, tvQty, tvTotalHarga;

    SharedPreferences preferences;
    public DetailProsesPembelianViewHolder(@NonNull View v, Context c) {
        super(v);
        this.context = c;
        tvKode = v.findViewById(R.id.list_kode_barang_pembelian);
        tvMerek = v.findViewById(R.id.list_merek_barang_pembelian);
        tvHargaSatuan = v.findViewById(R.id.list_harga_barang);
        tvQty = v.findViewById(R.id.list_kuantitas_barang);
        tvTotalHarga = v.findViewById(R.id.list_total_harga_barang);
        preferences = context.getSharedPreferences("MEREK_KEY", Context.MODE_PRIVATE);


    }

    public void bindData(ItemKeranjang itemKeranjang) {
        this.itemKeranjang = itemKeranjang;
        int noMerek = Integer.valueOf(itemKeranjang.getMerek());
        String merek = preferences.getString(String.valueOf(noMerek), "unknown");
        tvKode.setText(itemKeranjang.getKode());
        tvMerek.setText("( " + merek + " ) ");
        BigDecimal bg = new BigDecimal(itemKeranjang.getHargaBeli());
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        String hargaSatuan = decimalFormat.format(itemKeranjang.getHargaBeli());
        String totalHarga = decimalFormat.format(bg.multiply(new BigDecimal(itemKeranjang.getQty())));
        tvHargaSatuan.setText("Rp. " + hargaSatuan);
        tvQty.setText(String.valueOf(itemKeranjang.getQty()));
        tvTotalHarga.setText("Rp. " + totalHarga);


    }

}
