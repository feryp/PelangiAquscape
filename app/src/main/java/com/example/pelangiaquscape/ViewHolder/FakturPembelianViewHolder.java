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

public class FakturPembelianViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private ItemKeranjang itemKeranjang;
    private TextView tvKode, tvMerek, tvHargaSatuan, tvQty, tvJumlah;

    SharedPreferences preferences;

    public FakturPembelianViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        tvKode = itemView.findViewById(R.id.list_faktur_kode);
        tvMerek = itemView.findViewById(R.id.list_faktur_merek);
        tvQty = itemView.findViewById(R.id.list_faktur_kts);
        tvHargaSatuan = itemView.findViewById(R.id.list_faktur_harga_satuan);
        tvJumlah = itemView.findViewById(R.id.list_faktur_jumlah);
        preferences = context.getSharedPreferences("MEREK_KEY", Context.MODE_PRIVATE);

    }

    public void bindData(ItemKeranjang itemKeranjang){
        this.itemKeranjang = itemKeranjang;
        int noMerek = Integer.valueOf(itemKeranjang.getMerek());
        String merek = preferences.getString(String.valueOf(noMerek), "unknown");
        tvKode.setText(itemKeranjang.getKode());
        tvMerek.setText(merek);
        BigDecimal bg = new BigDecimal(itemKeranjang.getHargaBeli());
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        String hargaSatuan = decimalFormat.format(itemKeranjang.getHargaBeli());
        String jumlah = decimalFormat.format(bg.multiply(new BigDecimal(itemKeranjang.getQty())));
        tvHargaSatuan.setText("Rp. " + hargaSatuan);
        tvQty.setText(String.valueOf(itemKeranjang.getQty()));
        tvJumlah.setText("Rp. " + jumlah);
    }

}
