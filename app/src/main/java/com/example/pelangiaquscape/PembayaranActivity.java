package com.example.pelangiaquscape;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.text.DecimalFormat;

public class PembayaranActivity extends AppCompatActivity implements View.OnClickListener{


    SwitchCompat btnSwitch;
    TextInputLayout tvDiskon, tvNamaDiskon;
    ExpandableRelativeLayout ex;

    TextInputEditText etJmlLain, etDiskon;

    TextView tvTotalPembayaran, tvKembalian;

    Button btnUangPas, btnKelDua, btnKelLima, btnKelSepuluh;

    double totalHarga;

    DecimalFormat fmt = new DecimalFormat("#,###.00");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        Intent i = getIntent();
        if(i!= null)
            totalHarga = i.getDoubleExtra("totalHargaKeranjang", 0);
        else
            totalHarga = 0;

        etJmlLain = findViewById(R.id.et_jumlah_lain);
        etDiskon = findViewById(R.id.et_jumlah_diskon);

        btnUangPas = findViewById(R.id.btn_uang_pas);
        btnKelDua = findViewById(R.id.btn_kelipatan_dua);
        btnKelLima = findViewById(R.id.btn_kelipatan_lima);
        btnKelSepuluh = findViewById(R.id.btn_kelipatan_sepuluh);
        btnUangPas.setOnClickListener(this);
        btnKelDua.setOnClickListener(this);
        btnKelLima.setOnClickListener(this);
        btnKelSepuluh.setOnClickListener(this);

        tvDiskon = findViewById(R.id.jumlah_diskon);
        tvNamaDiskon = findViewById(R.id.nama_diskon);
        btnSwitch = findViewById(R.id.toogle_switch);

        tvTotalPembayaran = findViewById(R.id.tv_total_pembayaran);
        tvKembalian = findViewById(R.id.tv_kembalian);


        etJmlLain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        String format = fmt.format(totalHarga);
        tvTotalPembayaran.setText("Rp. "+ format);




        btnSwitch.setChecked(false);
        ex = findViewById(R.id.l_expand);
        ex.collapse();

        btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    ex.collapse();
//                    tvDiskon.setVisibility(View.INVISIBLE);
//                    tvNamaDiskon.setVisibility(View.INVISIBLE);
                }else{
                    ex.expand();
//                    tvDiskon.setVisibility(View.VISIBLE);
//                    tvNamaDiskon.setVisibility(View.VISIBLE);
                }
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

    @Override
    public void onClick(View v) {

        double kembalian;
        switch (v.getId()){
            case R.id.btn_kelipatan_dua:

                kembalian = 20000 - totalHarga;
                String as = fmt.format(kembalian);
                tvKembalian.setText("Rp. " + as);
                break;
            case R.id.btn_kelipatan_lima:
                kembalian = 50000 - totalHarga;

                String as1 = fmt.format(kembalian);
                tvKembalian.setText("Rp. " + as1);
                break;
            case R.id.btn_kelipatan_sepuluh:
                kembalian = 100000 - totalHarga;

                String as2 = fmt.format(kembalian);
                tvKembalian.setText("Rp. " + as2);
                break;
            case R.id.btn_uang_pas:
                tvKembalian.setText("Rp. 0.00");
                break;
        }
    }
}
