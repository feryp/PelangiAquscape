package com.example.pelangiaquscape.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelangiaquscape.Database.ItemKeranjangContract;
import com.example.pelangiaquscape.Database.ItemKeranjangDbHelper;
import com.example.pelangiaquscape.Database.ItemPembelianContract;
import com.example.pelangiaquscape.Database.ItemPembelianDbHelper;
import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.TambahPembelianActivity;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;

public class ItemPembelianViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView tvNo, tvNamaBarang, tvQty, tvTotalHarga;
    private ImageView delete;

    private ItemKeranjang keranjang;

    Context context;

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

        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        double totalHargaBeli =  keranjang.getHargaBeli() * keranjang.getQty();
        String totalHargaBeli2 = decimalFormat.format(totalHargaBeli);
        tvTotalHarga.setText("Rp. "  + totalHargaBeli2);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_delete:
//                ItemKeranjangDbHelper itemDb = new ItemKeranjangDbHelper(itemView.getContext());
//
//                SQLiteDatabase db = itemDb.getWritableDatabase();
//
//                // Define 'where' part of query.
//                String selection = ItemKeranjangContract.ItemKeranjangEntry.COLUMN_NAME_KODE + " LIKE ?";
//                // Specify arguments in placeholder order.
//                String[] selectionArgs = { keranjang.getKode() };
//
//                SharedPreferences preferences = context.getSharedPreferences("com.example.pelangiaquscape.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.remove(keranjang.getKode());
//                editor.apply();
//
//                //Issue SQL statement
//                int deletedRows = db.delete(ItemKeranjangContract.ItemKeranjangEntry.TABLE_NAME, selection, selectionArgs);
//
//                Toast.makeText(itemView.getContext(), deletedRows+ "barang terhapus", Toast.LENGTH_SHORT).show();
//                Context context = itemView.getContext();
//                ((TambahPembelianActivity)context).finish();
//                context.startActivity(new Intent(context, TambahPembelianActivity.class));

                break;
        }
    }
}
