package com.example.pelangiaquscape.Database;

import android.provider.BaseColumns;

public class ItemKeranjangContract {

    public ItemKeranjangContract() {
    }

    public static class ItemKeranjangEntry implements BaseColumns{

        public static final String TABLE_NAME = "keranjang";
        public static final String COLUMN_NAME_KODE = "kode";
        public static final String COLUMN_NAME_MEREK = "merek";
        public static final String COLUMN_NAME_HARGA_JUAL= "harga_jual";
        public static final String COLUMN_NAME_SATUAN = "satuan";
        public static final String COLUMN_NAME_HARGA_BELI = "harga_beli";
        public static final String COLUMN_NAME_QTY = "qty";
        public static final String COLUMN_NAME_TOTAL_PRICE = "total_price";
    }
}
