package com.example.pelangiaquscape.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.Model.Pemasok;
import com.example.pelangiaquscape.R;

public class PemasokViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public TextView tv_jenis_perusahaan;
    public TextView tv_nama_pemasok;
    public TextView tv_noHp_pemasok;
    public TextView tv_alamat_pemasok;

    private Pemasok pemasok;
    private ItemClickListener itemClickListener;
    private View.OnLongClickListener longClickListener;


    public PemasokViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_jenis_perusahaan = itemView.findViewById(R.id.tv_jenis_perusahaan);
        tv_nama_pemasok = itemView.findViewById(R.id.tv_nama_pemasok);
        tv_noHp_pemasok = itemView.findViewById(R.id.tv_nohp_pemasok);
        tv_alamat_pemasok = itemView.findViewById(R.id.tv_alamat_pemasok);
        itemView.setOnClickListener(this);

    }

    public void bindData(Pemasok pemasok){
        this.pemasok = pemasok;
        tv_jenis_perusahaan.setText(pemasok.getJenisPerusahaan());
        tv_nama_pemasok.setText(pemasok.getNamaPemasok());
        tv_noHp_pemasok.setText(pemasok.getNoHpPemasok());
        tv_alamat_pemasok.setText(pemasok.getAlamatPemasok());
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public void setOnLongClickListener(View.OnLongClickListener longClickListener){
        this.longClickListener = longClickListener;
    }


    @Override
    public boolean onLongClick(View v) {
        longClickListener.onLongClick(v);
        return false;
    }
}
