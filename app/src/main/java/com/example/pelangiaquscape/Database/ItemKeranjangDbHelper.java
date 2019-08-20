package com.example.pelangiaquscape.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.pelangiaquscape.Model.Barang;
import com.example.pelangiaquscape.Database.ItemKeranjangContract.ItemKeranjangEntry;
import com.example.pelangiaquscape.TransaksiKodeBarangActivity;


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



    Context context;
    public ItemKeranjangDbHelper(Context c){
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
        return db.delete(ItemKeranjangEntry.TABLE_NAME, null, null);
    }

    public void insertOrDelete(Barang model,String ids, String qty, String hargaBeli){
        SQLiteDatabase db =this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ItemKeranjangEntry.COLUMN_NAME_KODE, model.getKode());
        values.put(ItemKeranjangEntry.COLUMN_NAME_HARGA_BELI, model.getHargaBeli());
        values.put(ItemKeranjangEntry.COLUMN_NAME_QTY,qty);
        values.put(ItemKeranjangEntry.COLUMN_NAME_MEREK, ids);
        values.put(ItemKeranjangEntry.COLUMN_NAME_SATUAN, "-");
        double total = model.getHargaBeli() * Double.parseDouble(hargaBeli);
        values.put(ItemKeranjangEntry.COLUMN_NAME_TOTAL_PRICE, total);
        values.put(ItemKeranjangEntry.COLUMN_NAME_HARGA_JUAL, model.getHargaJual());

        try{

            long rowID = db.insertOrThrow(ItemKeranjangEntry.TABLE_NAME, null, values);
            Toast.makeText(context, "insert db", Toast.LENGTH_SHORT).show();
        }catch(SQLiteConstraintException e){

            long rowID = db.update(ItemKeranjangEntry.TABLE_NAME, values, ItemKeranjangEntry.COLUMN_NAME_KODE +"=?", new String[]{model.getKode()});

            Toast.makeText(context, "update db", Toast.LENGTH_SHORT).show();
        }
    }


}
