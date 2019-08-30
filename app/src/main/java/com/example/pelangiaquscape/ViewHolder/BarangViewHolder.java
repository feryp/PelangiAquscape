package com.example.pelangiaquscape.ViewHolder;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelangiaquscape.BarangActivity;
import com.example.pelangiaquscape.Database.ItemKeranjangContract;
import com.example.pelangiaquscape.Database.ItemKeranjangDbHelper;
import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.Interface.OnItemClickListener;
import com.example.pelangiaquscape.KeranjangPenjualanActivity;
import com.example.pelangiaquscape.Model.Barang;
import com.example.pelangiaquscape.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class BarangViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    private TextView tvKode, tvMerek, tvStok, tvSatuan, tvHargaJual, tvHargaBeli;

    private Barang barang;

    ItemClickListener listener;

    public BarangViewHolder(@NonNull View v) {
        super(v);
        tvKode = v.findViewById(R.id.kode_persediaan_barang);
        tvMerek = v.findViewById(R.id.merek_persediaan_barang);
        tvStok = v.findViewById(R.id.stok_barang);
        tvSatuan = v.findViewById(R.id.satuan_unit_barang);
        tvHargaBeli = v.findViewById(R.id.harga_modal_barang);
        tvHargaJual = v.findViewById(R.id.harga_jual_barang);
        v.setOnClickListener(this);
        v.setOnLongClickListener(this);
    }

    public void bindData(Barang barang, List<String> listMerek){
        this.barang = barang;
        tvKode.setText(barang.getKode());
        int mer = Integer.parseInt(barang.getMerek())-1;
        String merek = listMerek.get(mer);
        tvMerek.setText(merek);
        tvStok.setText("");
        tvSatuan.setText(barang.getSatuan());
        tvHargaBeli.setText(String.valueOf(barang.getHargaBeli()));
        tvHargaJual.setText(String.valueOf(barang.getHargaJual()));
    }

    public void setOnClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v, getAdapterPosition(), false);
    }

    @Override
    public boolean onLongClick(View v) {

        showDialog();
        return false;
    }

    void showDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(itemView.getContext()).create();
        alertDialog.setTitle("Hapus Data");
        alertDialog.setMessage("Apakah anda ingin menghapus Barang ini ? ");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference("Barang")
                                .child(String.valueOf(getAdapterPosition()+1))
                                .removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(itemView.getContext(), "item "+barang.getKode() +" telah terhapus", Toast.LENGTH_SHORT).show();
                                    }
                                });

                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
