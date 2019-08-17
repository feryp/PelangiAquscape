package com.example.pelangiaquscape.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pelangiaquscape.Model.Barang;
import com.example.pelangiaquscape.Database.ItemKeranjangContract.ItemKeranjangEntry;


public class ItemKeranjangDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME= "Keranjang.db";
    public static final int DATABASE_VERSION = 1;

    SQLiteDatabase database;


    private static final String SQL_CREATE_DATABASE = "CREATE TABLE "
            + ItemKeranjangEntry.TABLE_NAME + " ("
            + ItemKeranjangEntry.COLUMN_NAME_KODE + " TEXT PRIMARY KEY,"
            + ItemKeranjangEntry.COLUMN_NAME_HARGA_JUAL + " REAL,"
            + ItemKeranjangEntry.COLUMN_NAME_MEREK + " TEXT,"
            + ItemKeranjangEntry.COLUMN_NAME_QTY + " INTEGER,"
            + ItemKeranjangEntry.COLUMN_NAME_SATUAN + " TEXT,"
            + ItemKeranjangEntry.COLUMN_NAME_HARGA_BELI + " REAL,"
            + ItemKeranjangEntry.COLUMN_NAME_TOTAL_PRICE + " REAL)";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS "+ ItemKeranjangEntry.TABLE_NAME;



    public ItemKeranjangDbHelper(Context c){
        super(c, DATABASE_NAME, null, DATABASE_VERSION);


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
        return db.delete(ItemKeranjangEntry.TABLE_NAME, null, null);
    }


}
