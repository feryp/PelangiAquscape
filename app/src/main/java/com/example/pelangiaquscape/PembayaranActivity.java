package com.example.pelangiaquscape;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PembayaranActivity extends AppCompatActivity implements View.OnClickListener {


    SwitchCompat btnSwitch;
    TextInputLayout tvDiskon, tvNamaDiskon;
    ExpandableRelativeLayout ex;
    ImageView cancel;
    TextInputEditText etJmlLain, etDiskon, etNamaPelanggan, etNoHp;
    TextView tvTotalPembayaran, tvKembalian;
    Button btnBayar, btnUangPas, btnKelDua, btnKelLima, btnKelSepuluh, btnDiskonRp, btnDiskonPersen;
    RelativeLayout rl;

    LinearLayout ll;
    double totalHarga;
    double totalKembalian;
    double diskon;

    boolean diskonPersen, diskonRp;

    DecimalFormat fmt = new DecimalFormat("#,###.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        totalKembalian = 0;
        final Intent i = getIntent();

        if (i != null) {
            totalHarga = i.getDoubleExtra("totalHargaKeranjang", 0);
        } else {
            totalHarga = 0;
        }

        rl = findViewById(R.id.rl_option_payment);
        ll = findViewById(R.id.ll);
        rl.setOnClickListener(this);
        ll.setOnClickListener(this);

        cancel = findViewById(R.id.im_cancel);

        etJmlLain = findViewById(R.id.et_jumlah_lain);
        etDiskon = findViewById(R.id.et_jumlah_diskon);
        etNamaPelanggan = findViewById(R.id.et_nama_pelanggan_pembayaran);
        etNoHp = findViewById(R.id.et_no_hp_pelanggan);


        btnUangPas = findViewById(R.id.btn_uang_pas);
        btnKelDua = findViewById(R.id.btn_kelipatan_dua);
        btnKelLima = findViewById(R.id.btn_kelipatan_lima);
        btnKelSepuluh = findViewById(R.id.btn_kelipatan_sepuluh);
        btnDiskonRp = findViewById(R.id.btn_diskon_rp);
        btnDiskonPersen = findViewById(R.id.btn_diskon_persen);
        btnBayar = findViewById(R.id.btn_bayar);
        btnDiskonRp.setOnClickListener(this);
        btnDiskonPersen.setOnClickListener(this);
        btnUangPas.setOnClickListener(this);
        btnKelDua.setOnClickListener(this);
        btnKelLima.setOnClickListener(this);
        btnKelSepuluh.setOnClickListener(this);
        btnBayar.setOnClickListener(this);

        tvDiskon = findViewById(R.id.jumlah_diskon);
        tvNamaDiskon = findViewById(R.id.nama_diskon);
        btnSwitch = findViewById(R.id.toogle_switch);

        tvTotalPembayaran = findViewById(R.id.tv_total_pembayaran);
        tvKembalian = findViewById(R.id.tv_kembalian);

        //init
        btnDiskonPersen.setBackground(getResources().getDrawable(R.drawable.button_blue_circle));
        btnDiskonPersen.setTextColor(Color.WHITE);
        etDiskon.setHint("%");
        diskonPersen = true;
        diskonRp = false;


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

                if (s.length() == 0) {
                    diskon = 0;
                    assert i != null;
                    totalHarga = i.getDoubleExtra("totalHargaKeranjang", 0);
                    tvTotalPembayaran.setText("Rp. " + fmt.format(totalHarga));
                } else {

                    diskon = Double.parseDouble(s.toString());
                    double a = i.getDoubleExtra("totalHargaKeranjang", 0);
                    if (diskonPersen) {

                        totalHarga = a - (a * (diskon / 100));
                    } else if (diskonRp) {
                        totalHarga = a - diskon;
                    }
                    tvTotalPembayaran.setText("Rp. " + fmt.format(totalHarga));
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


                if (s.length() == 0) {
                    double kembalian = 0;
                    totalKembalian = kembalian;
                    tvKembalian.setText("Rp. " + kembalian);
                } else {

                    double jmlLain = Double.parseDouble(s.toString());
                    double kembalian = jmlLain - totalHarga;
                    totalKembalian = kembalian;

                    tvKembalian.setText("Rp. " + fmt.format(kembalian));
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
                if (hasFocus) {
                    btnKelDua.setBackground(getResources().getDrawable(R.drawable.button_pembayaran_default));
                    btnUangPas.setBackground(getResources().getDrawable(R.drawable.button_pembayaran_default));
                    btnKelLima.setBackground(getResources().getDrawable(R.drawable.button_pembayaran_default));
                    btnKelSepuluh.setBackground(getResources().getDrawable(R.drawable.button_pembayaran_default));
                    btnKelDua.setTextColor(Color.GRAY);
                    btnKelLima.setTextColor(Color.GRAY);
                    btnKelSepuluh.setTextColor(Color.GRAY);
                    btnUangPas.setTextColor(Color.GRAY);
                }
            }
        });


        String format = fmt.format(totalHarga);
        tvTotalPembayaran.setText("Rp. " + format);


        btnSwitch.setChecked(false);
        ex = findViewById(R.id.l_expand);
        ex.collapse();

        btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!isChecked) {
                    ex.collapse();
                    etDiskon.setText("");
                    etJmlLain.setText("");
                    etDiskon.clearFocus();
                    etJmlLain.clearFocus();

//                    tvDiskon.setVisibility(View.INVISIBLE);
//                    tvNamaDiskon.setVisibility(View.INVISIBLE);
                } else {
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
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        switch (v.getId()) {
            case R.id.btn_kelipatan_dua:

                kembalian = 20000 - totalHarga;
                totalKembalian = kembalian;
                String as = fmt.format(kembalian);
                tvKembalian.setText("Rp. " + as);

                v.setBackground(getResources().getDrawable(R.drawable.button_pembayaran_pressed));
                btnKelDua.setTextColor(Color.WHITE);
                btnKelLima.setTextColor(Color.GRAY);
                btnKelSepuluh.setTextColor(Color.GRAY);
                btnUangPas.setTextColor(Color.GRAY);
                btnUangPas.setBackground(getResources().getDrawable(R.drawable.button_pembayaran_default));
                btnKelLima.setBackground(getResources().getDrawable(R.drawable.button_pembayaran_default));
                btnKelSepuluh.setBackground(getResources().getDrawable(R.drawable.button_pembayaran_default));
                etJmlLain.clearFocus();
                im.hideSoftInputFromWindow(etJmlLain.getWindowToken(), 0);
                break;
            case R.id.btn_kelipatan_lima:
                kembalian = 50000 - totalHarga;
                totalKembalian = kembalian;
                String as1 = fmt.format(kembalian);
                tvKembalian.setText("Rp. " + as1);

                v.setBackground(getResources().getDrawable(R.drawable.button_pembayaran_pressed));
                btnKelDua.setTextColor(Color.GRAY);
                btnKelLima.setTextColor(Color.WHITE);
                btnKelSepuluh.setTextColor(Color.GRAY);
                btnUangPas.setTextColor(Color.GRAY);
                btnKelDua.setBackground(getResources().getDrawable(R.drawable.button_pembayaran_default));
                btnUangPas.setBackground(getResources().getDrawable(R.drawable.button_pembayaran_default));
                btnKelSepuluh.setBackground(getResources().getDrawable(R.drawable.button_pembayaran_default));
                etJmlLain.clearFocus();
                im.hideSoftInputFromWindow(etJmlLain.getWindowToken(), 0);

                break;
            case R.id.btn_kelipatan_sepuluh:
                kembalian = 100000 - totalHarga;
                totalKembalian = kembalian;
                String as2 = fmt.format(kembalian);
                tvKembalian.setText("Rp. " + as2);

                v.setBackground(getResources().getDrawable(R.drawable.button_pembayaran_pressed));
                btnKelDua.setTextColor(Color.GRAY);
                btnKelLima.setTextColor(Color.GRAY);
                btnKelSepuluh.setTextColor(Color.WHITE);
                btnUangPas.setTextColor(Color.GRAY);
                btnKelDua.setBackground(getResources().getDrawable(R.drawable.button_pembayaran_default));
                btnKelLima.setBackground(getResources().getDrawable(R.drawable.button_pembayaran_default));
                btnUangPas.setBackground(getResources().getDrawable(R.drawable.button_pembayaran_default));
                etJmlLain.clearFocus();
                im.hideSoftInputFromWindow(etJmlLain.getWindowToken(), 0);
                break;

            case R.id.btn_uang_pas:
                tvKembalian.setText("Rp. 0.00");
                totalKembalian = 0;
                v.setBackground(getResources().getDrawable(R.drawable.button_pembayaran_pressed));
                btnKelDua.setTextColor(Color.GRAY);
                btnKelLima.setTextColor(Color.GRAY);
                btnKelSepuluh.setTextColor(Color.GRAY);
                btnUangPas.setTextColor(Color.WHITE);
                btnKelDua.setBackground(getResources().getDrawable(R.drawable.button_pembayaran_default));
                btnKelLima.setBackground(getResources().getDrawable(R.drawable.button_pembayaran_default));
                btnKelSepuluh.setBackground(getResources().getDrawable(R.drawable.button_pembayaran_default));
                etJmlLain.clearFocus();
                im.hideSoftInputFromWindow(etJmlLain.getWindowToken(), 0);

                break;

            case R.id.ll:
                etJmlLain.clearFocus();
                im.hideSoftInputFromWindow(etJmlLain.getWindowToken(), 0);
                break;

            case R.id.rl_option_payment:
                etJmlLain.clearFocus();
                im.hideSoftInputFromWindow(etJmlLain.getWindowToken(), 0);
                break;

            case R.id.btn_diskon_persen:
                v.setBackground(getResources().getDrawable(R.drawable.button_blue_circle));
                btnDiskonRp.setBackground(getResources().getDrawable(R.drawable.button_grey_circle));
                btnDiskonRp.setTextColor(Color.GRAY);
                btnDiskonPersen.setTextColor(Color.WHITE);
                etDiskon.setHint("%");
                etDiskon.setMaxLines(2);
                diskonPersen = true;
                diskonRp = false;
                etDiskon.setText("");
                break;

            case R.id.btn_diskon_rp:
                v.setBackground(getResources().getDrawable(R.drawable.button_blue_circle));
                btnDiskonPersen.setBackground(getResources().getDrawable(R.drawable.button_grey_circle));
                btnDiskonPersen.setTextColor(Color.GRAY);
                btnDiskonRp.setTextColor(Color.WHITE);
                etDiskon.setHint("10,000");
                etDiskon.setMaxLines(10);
                diskonPersen = false;
                diskonRp = true;
                etDiskon.setText("");
                break;
            case R.id.btn_bayar:
                showPembayaranBerhasilDialog();
                break;
        }
    }

    void showPembayaranBerhasilDialog() {
        ViewGroup group = findViewById(android.R.id.content);

        View view = LayoutInflater.from(this).inflate(R.layout.popup_transaksi_berhasil, group, false);

        TextView tvTanggal = view.findViewById(R.id.tv_waktu_transaksi);


        TextView tvNamaPelanggan = view.findViewById(R.id.tv_popup_namapelanggan);
        TextView tvNoHp = view.findViewById(R.id.tv_popup_nohp);
        TextView tvUangBayar = view.findViewById(R.id.tv_popup_pilihpembayaran);
        TextView tvTotalBayarTransaksi = view.findViewById(R.id.tv_popup_totalbayar);
        TextView tvKembalianTransaksi = view.findViewById(R.id.tv_popup_kembalian);


        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy, HH:mm");
        String date = format.format(new Date());
        tvTanggal.setText(date.concat(" WIB"));
        tvNamaPelanggan.setText(etNamaPelanggan.getText().toString());
        tvNoHp.setText(etNoHp.getText().toString());
        tvTotalBayarTransaksi.setText("Rp. " + String.valueOf(fmt.format(totalHarga)));
        tvKembalianTransaksi.setText("Rp. " + String.valueOf(fmt.format(totalKembalian)));

        double uangBayar;
        uangBayar = totalKembalian + totalHarga;

        tvUangBayar.setText("Rp. " + String.valueOf(fmt.format(uangBayar)));


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
