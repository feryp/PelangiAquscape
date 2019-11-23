package com.example.pelangiaquscape.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.pelangiaquscape.Interface.ItemClickListener;
import com.example.pelangiaquscape.Model.Barang;
import com.example.pelangiaquscape.Model.Merek;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.TambahBarangActivity;
import com.example.pelangiaquscape.ViewHolder.BarangViewHolder;

import java.util.ArrayList;
import java.util.List;

public class BarangAdapter extends RecyclerView.Adapter<BarangViewHolder> implements Filterable {

    Context context;
    BarangFilter theFilter;
    List<Barang> barang;
    List<String> merek;
    List<Barang> filterBarang;
    boolean fromTambahPenyimpananActivity;


    public BarangAdapter(Context context, List<Barang> barang, List<String> merek, boolean fromTambahPenyimpananActivity) {
        this.context = context;
        this.barang = barang;
        this.merek = merek;
        this.filterBarang = barang;
        this.fromTambahPenyimpananActivity = fromTambahPenyimpananActivity;

        getFilter();
    }

    @NonNull
    @Override
    public BarangViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_barang, viewGroup, false);
        return new BarangViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BarangViewHolder holder, final int i) {
        holder.bindData(filterBarang.get(i), merek);


        holder.setOnClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                final Barang b = filterBarang.get(position);
                final int mer = Integer.parseInt(filterBarang.get(position).getMerek())-1;
                String merek1 = merek.get(mer);
                if(fromTambahPenyimpananActivity){
                    Intent i = new Intent();
                    i.putExtra("barang", b);
                    i.putExtra("merek", merek1);
                    ((Activity)context).setResult(Activity.RESULT_OK, i);
                    ((Activity)context).finish();
                }else{
                    Intent intent = new Intent(context, TambahBarangActivity.class);
                    intent.putExtra("barang", filterBarang.get(i));
                    intent.putExtra("idBarang", position+1);
                    intent.putExtra("merek", merek1);
                    context.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return filterBarang.size();
    }

    @Override
    public Filter getFilter() {
        if(theFilter == null){
            theFilter = new BarangFilter();
        }
        return theFilter;
    }

    private class BarangFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if(constraint!= null && constraint.length() > 0){
                ArrayList<Barang> tempList = new ArrayList<>();

                for(Barang barang:barang){
                    if(barang.getKode().toLowerCase().contains(constraint.toString().toLowerCase())){
                        tempList.add(barang);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            }else{
                filterResults.count = barang.size();
                filterResults.values = barang;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filterBarang = (List<Barang>) results.values;
            notifyDataSetChanged();

        }
    }
}
