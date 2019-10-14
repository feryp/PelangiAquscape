package com.example.pelangiaquscape;

import android.os.Environment;

import com.example.pelangiaquscape.Model.ItemKeranjang;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Const {

    public static String FOLDER_PDF = Environment.getExternalStorageDirectory() + File.separator+ "Struk";

    public static ArrayList<ItemKeranjang> tempList(){
        ArrayList<ItemKeranjang> listItem = new ArrayList<ItemKeranjang>();

        return listItem;

    }
}
