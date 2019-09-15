package com.example.pelangiaquscape.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pelangiaquscape.DetailProsesPembelianActivity;
import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.Model.Pembelian;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.TambahPembelianActivity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ProsesPembelianViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private TextView tv_no_pesanan;
    private TextView tv_total_harga;
    private TextView tv_tgl_pesanan;
    private TextView tv_status_pesanan;
    private TextView tv_edit_pesanan;

    Context c;
    private Pembelian pembelian;

    private ItemClickListener itemClickListener;

    private String key;


    public ProsesPembelianViewHolder(@NonNull View itemView, Context c) {
        super(itemView);

        this.c = c;
        tv_no_pesanan = itemView.findViewById(R.id.tv_no_pesanan);
        tv_total_harga = itemView.findViewById(R.id.tv_total_harga);
        tv_tgl_pesanan = itemView.findViewById(R.id.tv_tgl_pesanan);
        tv_status_pesanan = itemView.findViewById(R.id.tv_status_pesanan);
        tv_edit_pesanan = itemView.findViewById(R.id.tv_edit_pesanan);
        tv_edit_pesanan.setOnClickListener(this);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);


    }

    public void bindData(Pembelian pembelian, String key) {
        tv_no_pesanan.setText(pembelian.getNoPesanan());
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(pembelian.getTanggalPesanan());
        Date date = new Date();
        date.setTime(cal.getTimeInMillis());
        DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        String tanggalPesanan = format.format(date);

        tv_tgl_pesanan.setText(tanggalPesanan);
        BigDecimal de = new BigDecimal(pembelian.getTotalHarga());
        tv_total_harga.setText(de.toString());

        this.pembelian = pembelian;
        this.key = key;

    }

    public void setOnClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_edit_pesanan:
                Intent i = new Intent(c, TambahPembelianActivity.class);
                i.putExtra("key", key);
                i.putExtra("value", pembelian);
                c.startActivity(i);


                break;
            default:
                Intent j = new Intent(c, DetailProsesPembelianActivity.class);
                j.putExtra("key", key);
                j.putExtra("value", pembelian);
                c.startActivity(j);
                break;


        }


    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
