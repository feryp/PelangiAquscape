package com.example.pelangiaquscape;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelangiaquscape.Adapter.KeranjangAdapter;
import com.example.pelangiaquscape.Database.ItemKeranjangContract.ItemKeranjangEntry;
import com.example.pelangiaquscape.Database.ItemKeranjangDbHelper;
import com.example.pelangiaquscape.Model.ItemKeranjang;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KeranjangPenjualanActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    TextView tv;
    Button btnTambahBarang, btnKonfirmasi;
    ImageView cancel;

    ArrayList<ItemKeranjang> listItemKeranjang = new ArrayList<ItemKeranjang>();
    Bundle bundle = new Bundle();

    double totalAllItemPrice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang_penjualan);

        tv = findViewById(R.id.tv_total_pembayaran);
        cancel = findViewById(R.id.im_cancel);
        btnTambahBarang = findViewById(R.id.btn_tambah_barang);
        btnKonfirmasi = findViewById(R.id.btn_konfirmasi);

        recyclerView = findViewById(R.id.rv_list_transaksi);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(KeranjangPenjualanActivity.this, PembayaranActivity.class);
                i.putExtra("totalHargaKeranjang", totalAllItemPrice);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        btnTambahBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(KeranjangPenjualanActivity.this, TransaksiActivity.class);
                startActivity(i);
                finish();
            }
        });



        ItemKeranjangDbHelper dbHelper = new ItemKeranjangDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                ItemKeranjangEntry.COLUMN_NAME_KODE,
                ItemKeranjangEntry.COLUMN_NAME_MEREK,
                ItemKeranjangEntry.COLUMN_NAME_HARGA_JUAL,
                ItemKeranjangEntry.COLUMN_NAME_HARGA_BELI,
                ItemKeranjangEntry.COLUMN_NAME_SATUAN,
                ItemKeranjangEntry.COLUMN_NAME_QTY,
                ItemKeranjangEntry.COLUMN_NAME_TOTAL_PRICE

        };

        Cursor cursor = db.query(
                ItemKeranjangEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

//                List itemIds = new ArrayList<>();
        ArrayList<ItemKeranjang> list = new ArrayList<>();
        totalAllItemPrice = 0;
        while(cursor.moveToNext()){
            String kode = cursor.getString(cursor.getColumnIndexOrThrow(ItemKeranjangEntry.COLUMN_NAME_KODE));
            String merek = cursor.getString(cursor.getColumnIndexOrThrow(ItemKeranjangEntry.COLUMN_NAME_MEREK));
            double hargaJual = cursor.getDouble(cursor.getColumnIndexOrThrow(ItemKeranjangEntry.COLUMN_NAME_HARGA_JUAL));
            double hargaBeli = cursor.getDouble(cursor.getColumnIndexOrThrow(ItemKeranjangEntry.COLUMN_NAME_HARGA_BELI));
            String satuan= cursor.getString(cursor.getColumnIndexOrThrow(ItemKeranjangEntry.COLUMN_NAME_SATUAN));
            int qty = cursor.getInt(cursor.getColumnIndexOrThrow(ItemKeranjangEntry.COLUMN_NAME_QTY));
            double totalPrice =cursor.getDouble(cursor.getColumnIndexOrThrow(ItemKeranjangEntry.COLUMN_NAME_TOTAL_PRICE));

//            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
//            String subtitle = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE));
////                    itemIds.add(itemId);
//
            totalAllItemPrice = totalAllItemPrice + totalPrice;
            list.add(new ItemKeranjang(kode, merek, hargaJual, hargaBeli, satuan,qty, totalPrice));

//            Toast.makeText(MainActivity.this, subtitle, Toast.LENGTH_SHORT).show();


        }
//        listItemKeranjang.addAll(list);
        bundle.putParcelableArrayList("listItemKeranjang",list);




        cursor.close();

        KeranjangAdapter adapter = new KeranjangAdapter(this, list);
        recyclerView.setAdapter(adapter);

        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        String as = decimalFormat.format(totalAllItemPrice);
        tv.setText("Rp. " + as);




    }
}
