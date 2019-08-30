package com.example.pelangiaquscape.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.Model.Pelanggan;
import com.example.pelangiaquscape.R;

import java.util.List;

public class PelangganViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tv_nama_pelanggan;
    public TextView tv_noHp_pelanggan;
    public TextView tv_alamat_pelanggan;

    private Pelanggan pelanggan;
    private ItemClickListener itemClickListener;


    public PelangganViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_nama_pelanggan = itemView.findViewById(R.id.tv_nama_pelanggan);
        tv_noHp_pelanggan = itemView.findViewById(R.id.tv_nohp_pelanggan);
        tv_alamat_pelanggan = itemView.findViewById(R.id.tv_alamat_pelanggan);
        itemView.setOnClickListener(this);
    }

    public void bindData(Pelanggan pelanggan) {
        this.pelanggan = pelanggan;
        tv_nama_pelanggan.setText(pelanggan.getNamaPelanggan());
        tv_noHp_pelanggan.setText(pelanggan.getNoHp());
        tv_alamat_pelanggan.setText(pelanggan.getAlamat());

    }


    @Override
    public void onClick(View v) {

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
