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
import com.example.pelangiaquscape.Model.Barang;
import com.example.pelangiaquscape.Model.Merek;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.TransaksiKodeBarangActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.List;

public class TransaksiBarangViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView tvKode;
    public TextView tvHarga;
    public TextView tvQuantity;
    public TextView tvMerk;
    public AppCompatImageButton min;
    public AppCompatImageButton max;
    SharedPreferences sharedPref;

    String PACKAGE_NAME = "com.example.pelangiaquascape.";

    int qty = 0;

    private ItemClickListener itemClickListener;
    private boolean fromPembelian;

    String barangKey;


    public TransaksiBarangViewHolder(@NonNull View itemView) {

        super(itemView);

        tvKode = itemView.findViewById(R.id.tv_kode);
        tvHarga = itemView.findViewById(R.id.tv_harga_barang);
        tvQuantity = itemView.findViewById(R.id.tv_kuantitas);
        tvMerk = itemView.findViewById(R.id.tv_keterangan_merek);
        min = itemView.findViewById(R.id.btn_minus);
        max = itemView.findViewById(R.id.btn_plus);
        tvQuantity.setText("0");
//        itemView.setOnClickListener(this);
        min.setOnClickListener(this);
        max.setOnClickListener(this);

    }

    public void bindDataTransaksi(Barang barang, String namaMerek, boolean fromPembelian, String barangKey){
        tvKode.setText(barang.getKode());
        tvMerk.setText(namaMerek);
        this.fromPembelian = fromPembelian;
        this.barangKey = barangKey;


        // DIGANTI JADI HARGA JUAL NANTI
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        String as = decimalFormat.format(barang.getHargaJual());
        tvHarga.setText("Rp. " + as);
        // DIGANTI JADI HARGA JUAL NANTI


        if(!this.fromPembelian) {
            sharedPref = itemView.getContext().getSharedPreferences(PACKAGE_NAME+"PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);

        }else{
            sharedPref = itemView.getContext().getSharedPreferences(PACKAGE_NAME + "PEMBELIAN_KEY", Context.MODE_PRIVATE);
        }

        int qty = sharedPref.getInt(tvKode.getText().toString(), 0);
        tvQuantity.setText(String.valueOf(qty));

    }


    @Override
    public void onClick(View v) {

        int value =qty;
        Context context = itemView.getContext();

        SharedPreferences.Editor editor = sharedPref.edit();
//        if(!fromPembelian){
//
//        }
//        SharedPreferences sh = context
//                .getSharedPreferences("com.example.pelangiaquscape.PREFERENCE_FILE_KEY",Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sh.edit();
//
//        SharedPreferences sharedPembelian = context.getSharedPreferences("com.example.pelangiaquascape.PEMBELIAN_KEY", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editorPembelian = sharedPembelian.edit();

        switch(v.getId()){
            case R.id.btn_plus:
                if(qty < 50)
                    qty = qty+1;
                tvQuantity.setText(String.valueOf(qty));

                value = Integer.parseInt(tvQuantity.getText().toString());

                editor.putInt(tvKode.getText().toString(), value);
                editor.putString(tvKode.getText().toString().concat("key"), barangKey);
                editor.apply();

                break;
            case R.id.btn_minus:
                if(qty > 0)
                    qty = qty-1;
                tvQuantity.setText(String.valueOf(qty));

                editor.putInt(tvKode.getText().toString(), value);
                editor.putString(tvKode.getText().toString().concat("key"), barangKey);
                editor.apply();

                break;
        }
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    public void setItemClickListener(ItemClickListener i){
        this.itemClickListener = i;
    }


}
