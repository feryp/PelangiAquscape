package com.example.pelangiaquscape.ViewHolder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.KartuPelangganActivity;
import com.example.pelangiaquscape.Model.AkunToko;
import com.example.pelangiaquscape.Model.Pelanggan;
import com.example.pelangiaquscape.R;

import java.util.List;

public class PelangganViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public TextView tv_nama_pelanggan;
    public TextView tv_noHp_pelanggan;
    public TextView tv_alamat_pelanggan;
    public Button btnKartuPelanggan;

    private Pelanggan pelanggan;
    private ItemClickListener itemClickListener;

    private View.OnClickListener onClickListener;

    private View.OnLongClickListener longClickListener;


    public PelangganViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_nama_pelanggan = itemView.findViewById(R.id.tv_nama_pelanggan);
        tv_noHp_pelanggan = itemView.findViewById(R.id.tv_nohp_pelanggan);
        tv_alamat_pelanggan = itemView.findViewById(R.id.tv_alamat_pelanggan);
        btnKartuPelanggan = itemView.findViewById(R.id.btn_kartu_pelanggan);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        btnKartuPelanggan.setOnClickListener(this);
    }

    public void bindData(Pelanggan pelanggan) {
        this.pelanggan = pelanggan;
        tv_nama_pelanggan.setText(pelanggan.getNamaPelanggan());
        tv_noHp_pelanggan.setText(pelanggan.getNoHp());
        tv_alamat_pelanggan.setText(pelanggan.getAlamat());

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_kartu_pelanggan:
                Intent intent = new Intent(itemView.getContext(), KartuPelangganActivity.class);
                intent.putExtra("pelanggan", pelanggan);
                itemView.getContext().startActivity(intent);

                break;
            default:
                itemClickListener.onClick(v, getAdapterPosition(), false);
                break;
        }
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setOnLongClickListener(View.OnLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    @Override
    public boolean onLongClick(View v) {
        longClickListener.onLongClick(v);
        return false;
    }
}
