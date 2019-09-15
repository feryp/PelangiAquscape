package com.example.pelangiaquscape.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.pelangiaquscape.KartuPelangganActivity;
import com.example.pelangiaquscape.Model.AkunToko;
import com.example.pelangiaquscape.Model.KartuPelanggan;
import com.example.pelangiaquscape.Model.Pelanggan;
import com.example.pelangiaquscape.ViewHolder.KartuPelangganViewHolder;

import java.util.List;

public class KartuPelangganAdapter extends RecyclerView.Adapter<KartuPelangganViewHolder> {

    Context context;
    List<Pelanggan> pelanggan;

    @NonNull
    @Override
    public KartuPelangganViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull KartuPelangganViewHolder kartuPelangganViewHolder, final int i) {
        KartuPelanggan Pelanggan = pelanggan.get(i);


        kartuPelangganViewHolder.tv_nama_pelanggan.setText(Pelanggan.getNamaPelanggan());
        kartuPelangganViewHolder.tv_noHp_pelanggan.setText(Pelanggan.getNoHpPelanggan());
        kartuPelangganViewHolder.tv_alamat_pelanggan.setText(Pelanggan.getAlamatPelanggan());
//        kartuPelangganViewHolder.tv_nama_toko.setText(kartuPelanggan.getNamaToko());
//        kartuPelangganViewHolder.tv_noTelepon_toko.setText(kartuPelanggan.getNoHpToko());
//        kartuPelangganViewHolder.tv_email_toko.setText(kartuPelanggan.getEmailToko());
//        kartuPelangganViewHolder.tv_alamat_toko.setText(kartuPelanggan.getAlamatToko());

        kartuPelangganViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Context context = v.getContext();

                Intent intent = new Intent(context, KartuPelangganActivity.class);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
