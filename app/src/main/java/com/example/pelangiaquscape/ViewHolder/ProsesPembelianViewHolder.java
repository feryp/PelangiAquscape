package com.example.pelangiaquscape.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.Model.Pembelian;
import com.example.pelangiaquscape.R;

public class ProsesPembelianViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public TextView tv_no_pesanan;
    public TextView tv_total_harga;
    public TextView tv_tgl_pesanan;
    public TextView tv_status_pesanan;

    private Pembelian pembelian;

    private ItemClickListener itemClickListener;

    private View.OnClickListener onClickListener;



    public ProsesPembelianViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_no_pesanan = itemView.findViewById(R.id.tv_no_pesanan);
        tv_total_harga = itemView.findViewById(R.id.tv_total_harga);
        tv_tgl_pesanan = itemView.findViewById(R.id.tv_tgl_pesanan);
        tv_status_pesanan = itemView.findViewById(R.id.tv_status_pesanan);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
