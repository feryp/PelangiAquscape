package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TambahBarangActivity extends AppCompatActivity {

    ImageView cancel;
    TextInputLayout merekBarang;
    TextInputEditText etMerekBarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_barang);

        cancel = findViewById(R.id.im_cancel);
        merekBarang = findViewById(R.id.merek_barang);
        etMerekBarang = findViewById(R.id.et_merek_barang);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        etMerekBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent merekBarang = new Intent(TambahBarangActivity.this, ListMerekActivity.class);
                startActivity(merekBarang);
            }
        });


//        final ExpandableRelativeLayout ex = findViewById(R.id.expand_barang);
//
//        ex.collapse();


//        final SwitchCompat toogle = findViewById(R.id.toogle_switch);
//        toogle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//
//                if(toogle.isChecked()){
//                    ex.expand();
//                }else{
//                    ex.collapse();
//                }
//            }
//        });

    }

//    public void list(View view) {
//        TextInputEditText textInputEditText = (TextInputEditText) view;
//        if (textInputEditText.length() == 0)
//            return;
//        Intent intent = new Intent(TambahBarangActivity.this, ListMerekActivity.class);
//        startActivity(intent);
//    }
}

