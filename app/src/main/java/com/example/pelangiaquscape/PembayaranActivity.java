package com.example.pelangiaquscape;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class PembayaranActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        final Button buttonUasPas = findViewById(R.id.btn_uang_pas);
        final Button buttonKelDua = findViewById(R.id.btn_kelipatan_dua);
        final Button buttonKelLima = findViewById(R.id.btn_kelipatan_lima);
        final Button buttonKelSepuluh = findViewById(R.id.btn_kelipatan_sepuluh);

        buttonUasPas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonUasPas.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_clickable));
            }
        });

//        buttonUasPas.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                    buttonUasPas.setBackgroundColor(Color.BLUE);
//                } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                    buttonUasPas.setBackgroundColor(Color.GRAY);
//                }
//                return false;
//            }
//        });


    }
}
