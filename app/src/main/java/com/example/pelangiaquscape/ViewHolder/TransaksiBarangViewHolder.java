package com.example.pelangiaquscape.ViewHolder;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.TransaksiKodeBarangActivity;

public class TransaksiBarangViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView tvKode;
    public TextView tvHarga;
    public TextView tvQuantity;
    public AppCompatImageButton min;
    public AppCompatImageButton max;

    int qty = 0;

    private ItemClickListener itemClickListener;


    public TransaksiBarangViewHolder(@NonNull View itemView) {
        super(itemView);

        tvKode = itemView.findViewById(R.id.tv_kode);
        tvHarga = itemView.findViewById(R.id.tv_harga_barang);
        tvQuantity = itemView.findViewById(R.id.tv_kuantitas);
        min = itemView.findViewById(R.id.btn_minus);
        max = itemView.findViewById(R.id.btn_plus);
        tvQuantity.setText("0");
        itemView.setOnClickListener(this);
        min.setOnClickListener(this);
        max.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        int value =qty;
        Context context = itemView.getContext();

        SharedPreferences sh = context
                .getSharedPreferences("com.example.pelangiaquscape.PREFERENCE_FILE_KEY",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();

        switch(v.getId()){
            case R.id.btn_plus:
                if(qty < 50)
                    qty = qty+1;
                tvQuantity.setText(String.valueOf(qty));

                value = Integer.parseInt(tvQuantity.getText().toString());

                editor.putInt(tvKode.getText().toString(), value);
                editor.apply();

                break;
            case R.id.btn_minus:
                if(qty > 0)
                    qty = qty-1;
                tvQuantity.setText(String.valueOf(qty));

                editor.putInt(tvKode.getText().toString(), value);
                editor.apply();

                break;
        }
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    public void setItemClickListener(ItemClickListener i){
        this.itemClickListener = i;
    }




}