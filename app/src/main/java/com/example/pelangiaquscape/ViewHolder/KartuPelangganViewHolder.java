package com.example.pelangiaquscape.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pelangiaquscape.Model.AkunToko;
import com.example.pelangiaquscape.Model.KartuPelanggan;
import com.example.pelangiaquscape.Model.Pelanggan;
import com.example.pelangiaquscape.R;


public class KartuPelangganViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_nama_pelanggan;
    public TextView tv_noHp_pelanggan;
    public TextView tv_alamat_pelanggan;
    public TextView tv_nama_toko;
    public TextView tv_noTelepon_toko;
    public TextView tv_email_toko;
    public TextView tv_alamat_toko;

    private KartuPelanggan kartuPelanggan;
    private Pelanggan pelanggan;
    private AkunToko akunToko;

    public KartuPelangganViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_nama_pelanggan = itemView.findViewById(R.id.kartu_nama_pelanggan);
        tv_noHp_pelanggan = itemView.findViewById(R.id.kartu_no_hp_pelanggan);
        tv_alamat_pelanggan = itemView.findViewById(R.id.kartu_alamat_pelanggan);
        tv_nama_toko = itemView.findViewById(R.id.kartu_nama_toko);
        tv_noTelepon_toko = itemView.findViewById(R.id.kartu_no_telepon_toko);
        tv_email_toko = itemView.findViewById(R.id.kartu_email_toko);
        tv_alamat_toko = itemView.findViewById(R.id.kartu_alamat_toko);


    }





    //    public void bindData(KartuPelanggan kartuPelanggan){
//        this.kartuPelanggan = kartuPelanggan;
//        tv_nama_pelanggan.setText(kartuPelanggan.getNamaPelanggan());
//        tv_noHp_pelanggan.setText(kartuPelanggan.getNoHpPelanggan());
//        tv_alamat_pelanggan.setText(kartuPelanggan.getAlamatPelanggan());
//        tv_nama_toko.setText(kartuPelanggan.getNamaToko());
//        tv_noTelepon_toko.setText(kartuPelanggan.getNoHpToko());
//        tv_alamat_toko.setText(kartuPelanggan.getAlamatToko());

}
