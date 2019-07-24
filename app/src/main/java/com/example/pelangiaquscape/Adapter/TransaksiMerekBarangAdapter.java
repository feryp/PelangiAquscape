package com.example.pelangiaquscape.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.pelangiaquscape.Model.Merek;
import com.example.pelangiaquscape.R;

import java.util.ArrayList;

public class TransaksiMerekBarangAdapter extends ArrayAdapter<Merek> {


    public TransaksiMerekBarangAdapter(@NonNull Context context, ArrayList<Merek> Merek, int resource) {
        super(context, 0, Merek);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view==null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_merk_barang_transaksi, parent, false);
        }
        return view;
    }
}
