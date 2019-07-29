package com.example.pelangiaquscape;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.text.DecimalFormat;

public class PembayaranActivity extends AppCompatActivity implements View.OnClickListener{


    SwitchCompat btnSwitch;
    TextInputLayout tvDiskon, tvNamaDiskon;
    ExpandableRelativeLayout ex;

    ImageView cancel;

    TextInputEditText etJmlLain, etDiskon;

    TextView tvTotalPembayaran, tvKembalian;

    Button btnUangPas, btnKelDua, btnKelLima, btnKelSepuluh;

    RelativeLayout rl;

    LinearLayout ll;
    double totalHarga;
    double diskon;

    DecimalFormat fmt = new DecimalFormat("#,###.00");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        final Intent i = getIntent();

        if(i!= null){
            totalHarga = i.getDoubleExtra("totalHargaKeranjang", 0);
        }

        else{
            totalHarga = 0;
        }

        rl = findViewById(R.id.rl_option_payment);
        ll = findViewById(R.id.ll);
        rl.setOnClickListener(this);
        ll.setOnClickListener(this);

        cancel = findViewById(R.id.im_cancel);

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

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        etDiskon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0){
                    diskon = 0;
                    assert i != null;
                    totalHarga = i.getDoubleExtra("totalHargaKeranjang", 0);
                    tvTotalPembayaran.setText("Rp. " +fmt.format(totalHarga));
                }else{

                    diskon = Double.parseDouble(s.toString());
                    totalHarga = totalHarga - (totalHarga * (diskon*0.01));
                    tvTotalPembayaran.setText("Rp. " +fmt.format(totalHarga));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etJmlLain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.v("tryTextListen", "before");


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.v("tryTextListen", "onChange");




                if(s.length() == 0){
                    double kembalian = 0;
                    tvKembalian.setText("Rp. " +kembalian);
                }else{

                    double jmlLain = Double.parseDouble(s.toString());
                    double kembalian = jmlLain - totalHarga;

                    tvKembalian.setText("Rp. " +fmt.format(kembalian));
                }


            }

            @Override
            public void afterTextChanged(Editable s) {
//                Log.v("tryTextListen", "after");
            }
        });


        etJmlLain.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    btnKelDua.setBackgroundResource(android.R.drawable.btn_default);
                    btnUangPas.setBackgroundResource(android.R.drawable.btn_default);
                    btnKelLima.setBackgroundResource(android.R.drawable.btn_default);
                    btnKelSepuluh.setBackgroundResource(android.R.drawable.btn_default);
                }
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
                    etDiskon.setText("");
                    etJmlLain.setText("");
                    etDiskon.clearFocus();
                    etJmlLain.clearFocus();
//                    tvDiskon.setVisibility(View.INVISIBLE);
//                    tvNamaDiskon.setVisibility(View.INVISIBLE);
                }else{
                    ex.expand();
                    etDiskon.setFocusable(true);
                    etDiskon.requestFocus();
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

                v.setBackground(getResources().getDrawable(R.color.colorBlue));
//                v.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                btnUangPas.setBackgroundResource(android.R.drawable.btn_default);
                btnKelLima.setBackgroundResource(android.R.drawable.btn_default);
                btnKelSepuluh.setBackgroundResource(android.R.drawable.btn_default);
                etJmlLain.clearFocus();
                break;
            case R.id.btn_kelipatan_lima:
                kembalian = 50000 - totalHarga;

                String as1 = fmt.format(kembalian);
                tvKembalian.setText("Rp. " + as1);

                v.setBackground(getResources().getDrawable(R.color.colorBlue));
//                v.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                btnKelDua.setBackgroundResource(android.R.drawable.btn_default);
                btnUangPas.setBackgroundResource(android.R.drawable.btn_default);
                btnKelSepuluh.setBackgroundResource(android.R.drawable.btn_default);
                etJmlLain.clearFocus();

                break;
            case R.id.btn_kelipatan_sepuluh:
                kembalian = 100000 - totalHarga;

                String as2 = fmt.format(kembalian);
                tvKembalian.setText("Rp. " + as2);

                v.setBackground(getResources().getDrawable(R.color.colorBlue));
//                v.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                btnKelDua.setBackgroundResource(android.R.drawable.btn_default);
                btnKelLima.setBackgroundResource(android.R.drawable.btn_default);
                btnUangPas.setBackgroundResource(android.R.drawable.btn_default);
                etJmlLain.clearFocus();
                break;

            case R.id.btn_uang_pas:
                tvKembalian.setText("Rp. 0.00");

                v.setBackground(getResources().getDrawable(R.color.colorBlue));
//                v.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                btnKelDua.setBackgroundResource(android.R.drawable.btn_default);
                btnKelLima.setBackgroundResource(android.R.drawable.btn_default);
                btnKelSepuluh.setBackgroundResource(android.R.drawable.btn_default);
                etJmlLain.clearFocus();

                break;

            case R.id.ll:
                etJmlLain.clearFocus();
                break;

            case R.id.rl_option_payment:
                etJmlLain.clearFocus();
                break;
        }
    }
}
