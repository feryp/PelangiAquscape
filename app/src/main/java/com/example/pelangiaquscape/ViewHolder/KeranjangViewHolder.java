package com.example.pelangiaquscape.ViewHolder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelangiaquscape.Database.ItemKeranjangContract;
import com.example.pelangiaquscape.Database.ItemKeranjangDbHelper;
import com.example.pelangiaquscape.KeranjangPenjualanActivity;
import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.R;

public class KeranjangViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener, View.OnLongClickListener {


    public TextView tvKode, tvMerek, tvHarga, tvQty, tvTotalHarga;

    private View.OnLongClickListener listener;

    private ItemKeranjang itemKeranjang;

    Context context;

    public KeranjangViewHolder(@NonNull View iv, Context context) {
        super(iv);


        tvKode = iv.findViewById(R.id.tv_kode);
        tvMerek = iv.findViewById(R.id.tv_merek);
        tvQty = iv.findViewById(R.id.tv_jumlah_kuantitas);
        tvHarga = iv.findViewById(R.id.tv_harga_barang);
        this.context = context;
        iv.setOnClickListener(this);
        iv.setOnLongClickListener(this);

    }


    public void bind(ItemKeranjang keranjang){
        this.itemKeranjang = keranjang;

    }


    @Override
    public void onClick(View v) {
//        Toast.makeText(v.getContext(), "onclick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(View v) {
//        Toast.makeText(v.getContext(), "onlong", Toast.LENGTH_SHORT).show();
        showDialog();

        return false;
    }

    void showDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(itemView.getContext()).create();
        alertDialog.setTitle("Hapus Data");
        alertDialog.setMessage("Apakah anda ingin menghapus pesanan ini ? ");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ItemKeranjangDbHelper itemDb = new ItemKeranjangDbHelper(itemView.getContext());

                        SQLiteDatabase db = itemDb.getWritableDatabase();

                        // Define 'where' part of query.
                        String selection = ItemKeranjangContract.ItemKeranjangEntry.COLUMN_NAME_KODE + " LIKE ?";
                        // Specify arguments in placeholder order.
                        String[] selectionArgs = { itemKeranjang.getKode() };

                        SharedPreferences pref = context.getSharedPreferences("com.example.pelangiaquscape.PREFERENCE_FILE_KEY",Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = pref.edit();
                        edit.remove(itemKeranjang.getKode());
                        edit.apply();

                        // Issue SQL statement.
                        int deletedRows = db.delete(ItemKeranjangContract.ItemKeranjangEntry.TABLE_NAME, selection, selectionArgs);


                        Toast.makeText(itemView.getContext(), deletedRows+" item terhapus", Toast.LENGTH_SHORT).show();
                        Context context = itemView.getContext();
                        ((KeranjangPenjualanActivity)context).finish();
                        context.startActivity(new Intent(context, KeranjangPenjualanActivity.class));
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }




}
