package com.example.pelangiaquscape.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.R;

public class ListMerekViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tv_merek_barang;

    private ItemClickListener itemClickListener;

    public ListMerekViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_merek_barang = itemView.findViewById(R.id.tv_merek_barang);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
