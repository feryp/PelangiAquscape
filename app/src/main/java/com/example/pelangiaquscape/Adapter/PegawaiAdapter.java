package com.example.pelangiaquscape.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.pelangiaquscape.Model.Pegawai;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.ViewHolder.PegawaiViewHolder;

import java.util.ArrayList;
import java.util.List;

public class PegawaiAdapter extends RecyclerView.Adapter<PegawaiViewHolder> implements Filterable {

    Context context;
    List<Pegawai> pegawai;
    List<Pegawai> filterPegawai;
    PegawaiFilter theFilter;

    public PegawaiAdapter(Context context, List<Pegawai> pegawai, List<Pegawai> filterPegawai) {
        this.context = context;
        this.pegawai = pegawai;
        this.filterPegawai = filterPegawai;

        getFilter();
    }

    public PegawaiAdapter() {

    }

//    public PegawaiAdapter(Context context, List<Pegawai> pegawai) {
//        this.context = context;
//        this.pegawai = pegawai;
//    }

    @NonNull
    @Override
    public PegawaiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_pegawai, viewGroup, false);
        return new PegawaiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PegawaiViewHolder pegawaiViewHolder, int i) {
        pegawaiViewHolder.bindData(pegawai.get(i));

    }

    @Override
    public int getItemCount() {
        return filterPegawai.size();
    }

    @Override
    public Filter getFilter() {
        if (theFilter == null){
            theFilter = new PegawaiFilter();
        }
        System.out.println("isi filter" + theFilter);
        return theFilter;
    }

   private class PegawaiFilter extends Filter{

       @Override
       protected FilterResults performFiltering(CharSequence constraint) {
           FilterResults filterResults = new FilterResults();
           if (constraint!=null && constraint.length() > 0) {
               ArrayList<Pegawai> tempList = new ArrayList<>();

               for (Pegawai pegawai : pegawai) {
                   if (pegawai.getNamaPegawai().toLowerCase().contains(constraint.toString().toLowerCase())) {
                       tempList.add(pegawai);

                   }
               }

               filterResults.count = tempList.size();
               filterResults.values = tempList;
           } else {
               filterResults.count = pegawai.size();
               filterResults.values = pegawai;
           }
           return filterResults;
       }

       @Override
       protected void publishResults(CharSequence constraint, FilterResults results) {
           filterPegawai = (List<Pegawai>) results.values;
           notifyDataSetChanged();
       }
   }
}
