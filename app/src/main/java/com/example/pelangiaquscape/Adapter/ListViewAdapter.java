package com.example.pelangiaquscape.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.pelangiaquscape.BantuanActivity;
import com.example.pelangiaquscape.Model.Bantuan;
import com.example.pelangiaquscape.PusatBantuanAkunActivity;
import com.example.pelangiaquscape.PusatBantuanAkunTokoActivity;
import com.example.pelangiaquscape.PusatBantuanLaporanActivity;
import com.example.pelangiaquscape.PusatBantuanMitraBisnisActivity;
import com.example.pelangiaquscape.PusatBantuanPegawaiActivity;
import com.example.pelangiaquscape.PusatBantuanPembelianActivity;
import com.example.pelangiaquscape.PusatBantuanPemberitahuanActivity;
import com.example.pelangiaquscape.PusatBantuanPenerimaanActivity;
import com.example.pelangiaquscape.PusatBantuanPenjualanActivity;
import com.example.pelangiaquscape.PusatBantuanPenyimpananActivity;
import com.example.pelangiaquscape.PusatBantuanPrinterActivity;
import com.example.pelangiaquscape.PusatBantuanRekapActivity;
import com.example.pelangiaquscape.PusatBantuanTambahBarangActivity;
import com.example.pelangiaquscape.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private List<Bantuan> bantuanList = null;
    private ArrayList<Bantuan> arraylist;

    public ListViewAdapter(Context ctx, List<Bantuan> bantuanList) {
        mContext = ctx;
        this.bantuanList = bantuanList;
        inflater = LayoutInflater.from(mContext);

        this.arraylist = new ArrayList<Bantuan>();
        this.arraylist.addAll(bantuanList);
    }

    public class ViewHolder {
        TextView judulTxt;
        TextView deskTxt;
        ImageView img;
    }

    @Override
    public int getCount() {
        return bantuanList.size();
    }

    @Override
    public Bantuan getItem(int position) {
        return bantuanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_bantuan, null);
            // Locate the TextViews in listview_item.xml
            holder.judulTxt = view.findViewById(R.id.tv_main);
            holder.deskTxt = view.findViewById(R.id.tv_sub);
            holder.img = view.findViewById(R.id.ic_logo_qustion);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.judulTxt.setText(bantuanList.get(position).getJudul());
        holder.deskTxt.setText(bantuanList.get(position).getDesk());

        // Set the results into ImageView
        holder.img.setImageResource(bantuanList.get(position)
                .getImage());

        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(holder.judulTxt.getText().toString().equals("Cara Pembelian Barang")) {
                    Intent caraPembelian = new Intent(mContext, PusatBantuanPembelianActivity.class);
                    mContext.startActivity(caraPembelian);
                } else if(holder.judulTxt.getText().toString().equals("Cara Transaksi Penjualan")) {
                    Intent caraPenjualan = new Intent(mContext, PusatBantuanPenjualanActivity.class);
                    mContext.startActivity(caraPenjualan);
                } else if(holder.judulTxt.getText().toString().equals("Cara Penyimpanan Barang")) {
                    Intent caraPenyimpanan = new Intent(mContext, PusatBantuanPenyimpananActivity.class);
                    mContext.startActivity(caraPenyimpanan);
                } else if(holder.judulTxt.getText().toString().equals("Cara Menambah Barang")) {
                    Intent caraTambahBarang = new Intent(mContext, PusatBantuanTambahBarangActivity.class);
                    mContext.startActivity(caraTambahBarang);
                } else if(holder.judulTxt.getText().toString().equals("Cara Penerimaan Barang")) {
                    Intent caraPenerimaan = new Intent(mContext, PusatBantuanPenerimaanActivity.class);
                    mContext.startActivity(caraPenerimaan);
                } else if(holder.judulTxt.getText().toString().equals("Cara Menambah Mitra Bisnis")) {
                    Intent caraMitraBisnis = new Intent(mContext, PusatBantuanMitraBisnisActivity.class);
                    mContext.startActivity(caraMitraBisnis);
                } else if(holder.judulTxt.getText().toString().equals("Cara Menambah Pegawai")) {
                    Intent caraMitraBisnis = new Intent(mContext, PusatBantuanPegawaiActivity.class);
                    mContext.startActivity(caraMitraBisnis);
                } else if(holder.judulTxt.getText().toString().equals("Cara Melihat Laporan")) {
                    Intent caraLaporan = new Intent(mContext, PusatBantuanLaporanActivity.class);
                    mContext.startActivity(caraLaporan);
                } else if(holder.judulTxt.getText().toString().equals("Cara Melihat Rekap Kas")) {
                    Intent caraRekap = new Intent(mContext, PusatBantuanRekapActivity.class);
                    mContext.startActivity(caraRekap);
                } else if(holder.judulTxt.getText().toString().equals("Cara Melihat Pemberitahuan")) {
                    Intent caraPemberitahuan = new Intent(mContext, PusatBantuanPemberitahuanActivity.class);
                    mContext.startActivity(caraPemberitahuan);
                } else if(holder.judulTxt.getText().toString().equals("Cara Mengubah Akun")) {
                    Intent caraAkun = new Intent(mContext, PusatBantuanAkunActivity.class);
                    mContext.startActivity(caraAkun);
                } else if(holder.judulTxt.getText().toString().equals("Cara Mengubah Akun Toko")) {
                    Intent caraAkunToko = new Intent(mContext, PusatBantuanAkunTokoActivity.class);
                    mContext.startActivity(caraAkunToko);
                } else if(holder.judulTxt.getText().toString().equals("Cara Mengatur Printer")) {
                    Intent caraPrinter = new Intent(mContext, PusatBantuanPrinterActivity.class);
                    mContext.startActivity(caraPrinter);
                }
            }
        });

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        bantuanList.clear();
        if (charText.length() == 0) {
            bantuanList.addAll(arraylist);
        } else {
            for (Bantuan wp : arraylist) {
                if (wp.getJudul().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    bantuanList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}