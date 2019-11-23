package com.example.pelangiaquscape.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.pelangiaquscape.Model.Penyimpanan;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.ViewHolder.PenyimpananViewHolder;

import java.util.ArrayList;
import java.util.List;

public class PenyimpananAdapter extends RecyclerView.Adapter<PenyimpananViewHolder> implements Filterable {

    Context context;
    List<Penyimpanan> listPenyimpanan;
    List<Penyimpanan> filteredListPenyimpanan;
//    List<String> listKey;
    PenyimpananFilter theFilter;

    public PenyimpananAdapter(Context context, List<Penyimpanan> penyimpanan, List<String> key) {
        this.context = context;
        this.listPenyimpanan = penyimpanan;
//        this.listKey = key;

        this.filteredListPenyimpanan = penyimpanan;

        getFilter();
    }

    @NonNull
    @Override
    public PenyimpananViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_inventory_barangkeluar, viewGroup, false);

        return new PenyimpananViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PenyimpananViewHolder holder, int i) {
        holder.bindData(filteredListPenyimpanan.get(i));

    }

    @Override
    public int getItemCount() {
        return filteredListPenyimpanan.size();
    }

    @Override
    public Filter getFilter() {
        if(theFilter == null){
            theFilter = new PenyimpananFilter();
        }
        return theFilter;
    }

    private class PenyimpananFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if(constraint!= null && constraint.length()>0){
                List<Penyimpanan> tempPenyimpanan = new ArrayList<>();

                for(Penyimpanan penyimpanan: listPenyimpanan){
                    if(penyimpanan.getKodeBarang().toLowerCase().contains(constraint.toString().toLowerCase())){
                        tempPenyimpanan.add(penyimpanan);
                    }
                }

                filterResults.count = tempPenyimpanan.size();
                filterResults.values = tempPenyimpanan;
            }else{
                filterResults.count = listPenyimpanan.size();
                filterResults.values = listPenyimpanan;
            }



            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredListPenyimpanan = (List<Penyimpanan>) results.values;
            notifyDataSetChanged();

        }
    }
}
