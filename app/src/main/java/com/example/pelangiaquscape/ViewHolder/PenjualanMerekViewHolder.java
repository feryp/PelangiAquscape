package com.example.pelangiaquscape.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.R;

public class PenjualanMerekViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tv_merek;
    public ImageView im_arrow;

    private ItemClickListener itemClickListener;

    public PenjualanMerekViewHolder(View itemView) {
        super(itemView);

        tv_merek = itemView.findViewById(R.id.tv_merek);
        im_arrow = itemView.findViewById(R.id.im_arrow);
        itemView.setOnClickListener(this);
//        im_arrow.setImageResource(R.drawable.ic_arrow_black);

    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
