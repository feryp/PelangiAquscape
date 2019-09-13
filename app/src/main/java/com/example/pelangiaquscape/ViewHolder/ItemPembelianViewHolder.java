package com.example.pelangiaquscape.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.R;

public class ItemPembelianViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView tvNo, tvNamaBarang, tvQty, tvTotalHarga;
    ImageView delete;

    public ItemPembelianViewHolder(@NonNull View v) {
        super(v);



        tvNo = v.findViewById(R.id.list_no);
        tvNamaBarang = v.findViewById(R.id.list_nama_barang);
        tvQty = v.findViewById(R.id.list_jumlah_barang);
        tvTotalHarga = v.findViewById(R.id.list_total_harga);
        delete = v.findViewById(R.id.iv_delete);

        delete.setOnClickListener(this);


    }

    public void bindData(ItemKeranjang keranjang){
        int no = getAdapterPosition() + 1;
        tvNo.setText(String.valueOf(no));
        tvNamaBarang.setText(keranjang.getKode());
        tvQty.setText(String.valueOf(keranjang.getQty()));
        double totalHargaBeli = keranjang.getHargaBeli() * keranjang.getQty();
        tvTotalHarga.setText(String.valueOf(totalHargaBeli));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_delete:

                break;
        }
    }
}
