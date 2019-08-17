package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


public class GudangActivity extends AppCompatActivity {

//    private SlideUp slideUp;
    private View scrim;
    private View slideView;
    private FloatingActionButton fab_gudang;
    ImageView cancel;
    Button btnSimpan, btnHapus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gudang);

//        slideView = findViewById(R.id.slideView);
//        scrim = findViewById(R.id.scrim);
        fab_gudang = findViewById(R.id.fab_gudang);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnHapus = findViewById(R.id.btnHapus);
        cancel = findViewById(R.id.im_cancel);
        FloatingActionButton fab_gudang = findViewById(R.id.fab_gudang);

//        slideUp.hideImmediately();

//        slideView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                slideUp.animateOut();
//                fab_gudang.show();
//
//
//            }
//        });
//
        fab_gudang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GudangActivity.this, "Tambah Barang di Gudang", Toast.LENGTH_SHORT).show();
                Intent fab_gudang = new Intent(GudangActivity.this, TambahGudangActivity.class);
                startActivity(fab_gudang);
////                Toast.makeText(getContext(), "month " + a, Toast.LENGTH_SHORT).show();
//                slideUp.animateIn();
//                fab_rutin.hide();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//
//
//        slideUp.setSlideListener(new SlideUp.SlideListener() {
//            @Override
//            public void onSlideDown(float v) {
//                scrim.setAlpha(1 - (v / 100));
//            }
//
//            @Override
//            public void onVisibilityChanged(int i) {
//                if (i == View.GONE) {
//                    fab_gudang.show();
//                    clear();
//                    isUpdate = false;
//                }
//
//            }
//        });

    }
}
