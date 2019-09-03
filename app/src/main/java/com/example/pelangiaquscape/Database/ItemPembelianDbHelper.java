package com.example.pelangiaquscape.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.pelangiaquscape.Database.ItemPembelianContract.ItemPembelianEntry;

import com.example.pelangiaquscape.Model.Barang;
import com.example.pelangiaquscape.Model.ItemKeranjang;

import java.util.ArrayList;
import java.util.List;

public class ItemPembelianDbHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME= "Keranjang.db";
    public static final int DATABASE_VERSION = 1;

    SQLiteDatabase database;


    private static final String SQL_CREATE_DATABASE = "CREATE TABLE "
            + ItemPembelianEntry.TABLE_NAME + " ("
            + ItemPembelianEntry.COLUMN_NAME_KODE + " TEXT PRIMARY KEY,"
            + ItemPembelianEntry.COLUMN_NAME_HARGA_JUAL + " REAL,"
            + ItemPembelianEntry.COLUMN_NAME_MEREK + " TEXT,"
            + ItemPembelianEntry.COLUMN_NAME_QTY + " INTEGER,"
            + ItemPembelianEntry.COLUMN_NAME_SATUAN + " TEXT,"
            + ItemPembelianEntry.COLUMN_NAME_HARGA_BELI + " REAL,"
            + ItemPembelianEntry.COLUMN_NAME_TOTAL_PRICE + " REAL)";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS "+ ItemPembelianEntry.TABLE_NAME;



    Context context;
    public ItemPembelianDbHelper(Context c){
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = c;

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_DATABASE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    public int deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ItemPembelianEntry.TABLE_NAME, null, null);
    }

    public void insertOrDelete(Barang model, String ids, String qty, String hargaBeli){
        SQLiteDatabase db =this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ItemPembelianEntry.COLUMN_NAME_KODE, model.getKode());
        values.put(ItemPembelianEntry.COLUMN_NAME_HARGA_BELI, model.getHargaBeli());
        values.put(ItemPembelianEntry.COLUMN_NAME_QTY,qty);
        values.put(ItemPembelianEntry.COLUMN_NAME_MEREK, ids);
        values.put(ItemPembelianEntry.COLUMN_NAME_SATUAN, "-");
        double total = model.getHargaBeli() * Double.parseDouble(hargaBeli);
        values.put(ItemPembelianEntry.COLUMN_NAME_TOTAL_PRICE, total);
        values.put(ItemPembelianEntry.COLUMN_NAME_HARGA_JUAL, model.getHargaJual());

        try{
            long rowID = db.insertOrThrow(ItemPembelianEntry.TABLE_NAME, null, values);
            Toast.makeText(context, "insert db", Toast.LENGTH_SHORT).show();
        }catch(SQLiteConstraintException e){

            long rowID = db.update(ItemPembelianEntry.TABLE_NAME, values, ItemPembelianEntry.COLUMN_NAME_KODE +"=?", new String[]{model.getKode()});

            Toast.makeText(context, "update db", Toast.LENGTH_SHORT).show();
        }
    }

    public List<ItemKeranjang> selectAll(){
        SQLiteDatabase db = this.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                ItemPembelianEntry.COLUMN_NAME_KODE,
                ItemPembelianEntry.COLUMN_NAME_MEREK,
                ItemPembelianEntry.COLUMN_NAME_HARGA_JUAL,
                ItemPembelianEntry.COLUMN_NAME_HARGA_BELI,
                ItemPembelianEntry.COLUMN_NAME_SATUAN,
                ItemPembelianEntry.COLUMN_NAME_QTY,
                ItemPembelianEntry.COLUMN_NAME_TOTAL_PRICE

        };

        Cursor cursor = db.query(
                ItemPembelianEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

//                List itemIds = new ArrayList<>();
        ArrayList<ItemKeranjang> list = new ArrayList<>();

        while(cursor.moveToNext()){
            String kode = cursor.getString(cursor.getColumnIndexOrThrow(ItemKeranjangContract.ItemKeranjangEntry.COLUMN_NAME_KODE));
            String merek = cursor.getString(cursor.getColumnIndexOrThrow(ItemKeranjangContract.ItemKeranjangEntry.COLUMN_NAME_MEREK));
            double hargaJual = cursor.getDouble(cursor.getColumnIndexOrThrow(ItemKeranjangContract.ItemKeranjangEntry.COLUMN_NAME_HARGA_JUAL));
            double hargaBeli = cursor.getDouble(cursor.getColumnIndexOrThrow(ItemKeranjangContract.ItemKeranjangEntry.COLUMN_NAME_HARGA_BELI));
            String satuan= cursor.getString(cursor.getColumnIndexOrThrow(ItemKeranjangContract.ItemKeranjangEntry.COLUMN_NAME_SATUAN));
            int qty = cursor.getInt(cursor.getColumnIndexOrThrow(ItemKeranjangContract.ItemKeranjangEntry.COLUMN_NAME_QTY));
            double totalPrice =cursor.getDouble(cursor.getColumnIndexOrThrow(ItemKeranjangContract.ItemKeranjangEntry.COLUMN_NAME_TOTAL_PRICE));

//            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
//            String subtitle = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE));
////                    itemIds.add(itemId);
//

            list.add(new ItemKeranjang(kode, merek, hargaJual, hargaBeli, satuan,qty, totalPrice));

//            Toast.makeText(MainActivity.this, subtitle, Toast.LENGTH_SHORT).show();


        }

        return list;
    }
}
