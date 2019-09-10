package com.example.pelangiaquscape.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pelangiaquscape.Model.Pegawai;
import com.example.pelangiaquscape.R;

public class PegawaiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public TextView tv_nama_pegawai;
    public TextView tv_jabatan;
    public ImageView im_foto_pegawai;

    private Pegawai pegawai;

    private View.OnLongClickListener onLongClickListener;

    private View.OnClickListener onClickListener;


    public PegawaiViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_nama_pegawai = itemView.findViewById(R.id.tv_nama_pegawai);
        tv_jabatan = itemView.findViewById(R.id.tv_jabatan);
        im_foto_pegawai = itemView.findViewById(R.id.foto_pegawai);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void bindData(Pegawai pegawai){
        this.pegawai = pegawai;
        tv_nama_pegawai.setText(pegawai.getNamaPegawai());
        tv_jabatan.setText(pegawai.getJabatan());
    }

    @Override
    public void onClick(View v) {

    }

    public void setOnLongClickListener(View.OnLongClickListener longClickListener){
        this.onLongClickListener = longClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public boolean onLongClick(View v) {
        onLongClickListener.onLongClick(v);
        return false;
    }
}
