package com.example.pelangiaquscape.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.R;

public class BarangViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView tvKode;
    public TextView tvHarga;
    public TextView tvQuantity;
    public AppCompatImageButton min;
    public AppCompatImageButton max;

    int qty = 0;

    private ItemClickListener itemClickListener;


    public BarangViewHolder(@NonNull View itemView) {
        super(itemView);

        tvKode = itemView.findViewById(R.id.tv_kode);
        tvHarga = itemView.findViewById(R.id.tv_harga_barang);
        tvQuantity = itemView.findViewById(R.id.tv_kuantitas);
        min = itemView.findViewById(R.id.btn_minus);
        max = itemView.findViewById(R.id.btn_plus);
        tvQuantity.setText("0");
        itemView.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
        switch(v.getId()){
            case R.id.btn_minus:
                if(qty < 30)
                    qty = qty+1;
                tvQuantity.setText(String.valueOf(qty));
                break;
            case R.id.btn_plus:
                if(qty > -1)
                    qty = qty-1;
                tvQuantity.setText(String.valueOf(qty));
                break;
        }
    }

    public void setItemClickListener(ItemClickListener i){
        this.itemClickListener = i;
    }




}
