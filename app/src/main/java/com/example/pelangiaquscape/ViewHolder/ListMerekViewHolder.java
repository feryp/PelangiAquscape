package com.example.pelangiaquscape.ViewHolder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.Model.Merek;
import com.example.pelangiaquscape.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class ListMerekViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public CardView cv_merek_barang;
    public TextView tv_merek_barang;

    Merek merek;

    private ItemClickListener itemClickListener;
    private View.OnLongClickListener longClickListener;

    public ListMerekViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_merek_barang = itemView.findViewById(R.id.tv_merek_barang);
        cv_merek_barang = itemView.findViewById(R.id.cv_merek_barang);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void bindDataMerek(Merek merek){
        this.merek = merek;
        tv_merek_barang.setText(merek.getNama());
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setOnLongClickListener(View.OnLongClickListener longClickListener){
        this.longClickListener = longClickListener;
    }

    @Override
    public boolean onLongClick(View v) {
        longClickListener.onLongClick(v);
//        showDialog();
        return false;
    }


}
