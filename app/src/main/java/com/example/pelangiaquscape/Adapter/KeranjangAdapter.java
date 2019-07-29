package com.example.pelangiaquscape.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.Interface.OnItemClickListener;
import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.ViewHolder.KeranjangViewHolder;

import java.text.DecimalFormat;
import java.util.List;

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangViewHolder> {


    Context context;
    List<ItemKeranjang> listKeranjang;



    public KeranjangAdapter(Context context, List<ItemKeranjang> listKeranjang) {
        this.context = context;
        this.listKeranjang = listKeranjang;
    }


    @NonNull
    @Override
    public KeranjangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_keranjang, parent, false);
        KeranjangViewHolder holder = new KeranjangViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull KeranjangViewHolder holder, int i) {
        holder.tvKode.setText(listKeranjang.get(i).getKode());

        DecimalFormat fmt = new DecimalFormat("#,###.00");
        String as = fmt.format(listKeranjang.get(i).getTotalPrice());
        holder.tvHarga.setText("Rp. "+as);
        holder.tvQty.setText(String.valueOf(listKeranjang.get(i).getQty()));
        holder.tvMerek.setText(listKeranjang.get(i).getMerek());
        holder.bind(listKeranjang.get(i));

    }

    @Override
    public int getItemCount() {
        return listKeranjang.size();
    }

}
