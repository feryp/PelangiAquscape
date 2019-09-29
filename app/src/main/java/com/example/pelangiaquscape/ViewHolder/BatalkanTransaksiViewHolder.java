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

public class BatalkanTransaksiViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private ItemKeranjang itemKeranjang;


    private TextView tvKode, tvMerek, tvHargaSatuan, tvQty, tvTotalHarga;

    SharedPreferences preferences;

    public BatalkanTransaksiViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        tvKode = itemView.findViewById(R.id.kode_barang_dibatalkan);
        tvMerek = itemView.findViewById(R.id.merek_barang_dibatalkan);
        tvHargaSatuan = itemView.findViewById(R.id.harga_barang_dibatalkan);
        tvQty = itemView.findViewById(R.id.kuantitas_barang);
        preferences = context.getSharedPreferences("MEREK_KEY", Context.MODE_PRIVATE);

    }


    public void bindData(ItemKeranjang itemKeranjang) {
        this.itemKeranjang = itemKeranjang;
        int noMerek = Integer.valueOf(itemKeranjang.getMerek());
        String merek = preferences.getString(String.valueOf(noMerek), "unknown");
        tvKode.setText(itemKeranjang.getKode());
        tvMerek.setText("( " + merek + " ) ");
        BigDecimal bigDecimal = new BigDecimal(itemKeranjang.getHargaBeli());
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        String hargaSatuan =  decimalFormat.format(itemKeranjang.getHargaBeli());
        String totalHarga = decimalFormat.format(bigDecimal.multiply(new BigDecimal(itemKeranjang.getQty())));
        tvHargaSatuan.setText("Rp. " + hargaSatuan);
        tvQty.setText(String.valueOf(itemKeranjang.getQty()));

    }
}
