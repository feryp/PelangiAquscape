package com.example.pelangiaquscape;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NotificationCompat;
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
import android.widget.Toast;

import com.example.pelangiaquscape.Database.ItemKeranjangContract;
import com.example.pelangiaquscape.Database.ItemKeranjangDbHelper;
import com.example.pelangiaquscape.Fragment.PemberitahuanFragment;
import com.example.pelangiaquscape.Model.AkunToko;
import com.example.pelangiaquscape.Model.Barang;
import com.example.pelangiaquscape.Model.ItemKeranjang;
import com.example.pelangiaquscape.Model.Pemberitahuan;
import com.example.pelangiaquscape.Model.Penjualan;
import com.example.pelangiaquscape.Model.Penyimpanan;
import com.example.pelangiaquscape.Model.User;
import com.example.pelangiaquscape.Utils.PDFUtils;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PembayaranActivity extends AppCompatActivity implements View.OnClickListener {


    SwitchCompat btnSwitch;
    TextInputLayout tvDiskon, tvNamaDiskon;
    ExpandableRelativeLayout ex;
    ImageView cancel, ivHapus;
    TextInputEditText etJmlLain, etDiskon, etNamaPelanggan, etNoHp;
    TextView tvTotalPembayaran, tvKembalian;
    Button btnBayar, btnUangPas, btnKelDua, btnKelLima, btnKelSepuluh, btnDiskonRp, btnDiskonPersen;
    RelativeLayout rl;

//    Pemberitahuan pemberitahuan;

    Pemberitahuan model;
    String key;

    LinearLayout ll;
    double totalHarga;
    double totalKembalian;
    double diskon;
    double uangBayar;

    boolean diskonPersen, diskonRp;

    Penjualan penjualan;


    DecimalFormat fmt = new DecimalFormat("#,###.00");
    List<ItemKeranjang> listKeranjang;

    SharedPreferences sharedPref;
    String PACKAGE_NAME = "com.example.pelangiaquascape.";


    final String CHANNEL_ID = "theId";

    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        // AMBIL DARI INTERNAL
        sharedPref = getSharedPreferences(PACKAGE_NAME + "PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);

        // INISIALISASI NILAI AWAL
        totalKembalian = 0;
        uangBayar = 0;
        diskon = 0;

        // GET INTENT DARI ACTIVITY SEBELUMNYA
        final Intent i = getIntent();
        if (i != null) {
            totalHarga = i.getDoubleExtra("totalHargaKeranjang", 0);
            Bundle bundle = getIntent().getExtras();
            ArrayList<ItemKeranjang> k = bundle.getParcelableArrayList("listItemKeranjang");
            listKeranjang = new ArrayList<>();
            listKeranjang.addAll(k);
        } else {
            totalHarga = 0;
        }

        // INIT VIEW
        rl = findViewById(R.id.rl_option_payment);
        ll = findViewById(R.id.ll);
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
        tvDiskon = findViewById(R.id.jumlah_diskon);
        tvNamaDiskon = findViewById(R.id.nama_diskon);
        btnSwitch = findViewById(R.id.toogle_switch);
        ivHapus = findViewById(R.id.im_delete);
        tvTotalPembayaran = findViewById(R.id.tv_total_pembayaran);
        tvKembalian = findViewById(R.id.tv_kembalian);
        ex = findViewById(R.id.l_expand);

        // INIT BTN DISKON
        btnDiskonPersen.setBackground(getResources().getDrawable(R.drawable.button_blue_circle));
        btnDiskonPersen.setTextColor(Color.WHITE);
        etDiskon.setHint("%");
        diskonPersen = true;
        diskonRp = false;


        // REGISTER LISTENER
        rl.setOnClickListener(this);
        ll.setOnClickListener(this);
        ivHapus.setOnClickListener(this);
        btnDiskonRp.setOnClickListener(this);
        btnDiskonPersen.setOnClickListener(this);
        btnUangPas.setOnClickListener(this);
        btnKelDua.setOnClickListener(this);
        btnKelLima.setOnClickListener(this);
        btnKelSepuluh.setOnClickListener(this);
        btnBayar.setOnClickListener(this);
        cancel.setOnClickListener(this);


        // PERTAMA SET PEMBAYARAN KETIKA MEMBUKA PEMBAYARAN ACTIVITY
        String format = fmt.format(totalHarga);
        tvTotalPembayaran.setText("Rp. " + format);


        // KETIKA MEMBUKA PEMBAYARAN ACTIVITY BTN SWITCH TIDAK TERBUKA
        // DAN EXPANDABLE VIEW TERTUTUP
        btnSwitch.setChecked(false);
        ex.collapse();


        // EVENT LAIN
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
                    uangBayar = jmlLain;
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

        // END EVENT LAIN


        // MEMBUAT CHANNEL UNTUK NOTIFIKASI;
        createNotificationChannel();


    }

    @Override
    public void onClick(View v) {

        double kembalian;
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        switch (v.getId()) {
            case R.id.btn_kelipatan_dua:

                kembalian = 20000 - totalHarga;
                uangBayar = 20000;
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
                uangBayar = 50000;
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
                uangBayar = 100000;
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
                uangBayar = totalHarga;
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
                SimpleDateFormat fmt = new SimpleDateFormat("ddMMyyyy-hhmmss");
                Calendar c = Calendar.getInstance();
                Date date = c.getTime();
                String formatedDate = fmt.format(date);
                String noPenjualan = "INV-" + formatedDate;

                Penjualan p = new Penjualan(noPenjualan,
                        "Tunai",
                        c.getTimeInMillis(),
                        FirebaseAuth.getInstance().getUid(),
                        listKeranjang,
                        etNamaPelanggan.getText().toString() != null ? etNamaPelanggan.getText().toString() : "",
                        etNoHp.getText().toString() != null ? etNoHp.getText().toString() : "",
                        totalHarga,
                        uangBayar,
                        totalKembalian,
                        diskon
                );

                FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
//                            tvNamaKasir.setText(user.getUsername());
                            FirebaseDatabase.getInstance().getReference("AkunToko").child("1").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    AkunToko toko = dataSnapshot.getValue(AkunToko.class);
                                    Toast.makeText(PembayaranActivity.this, "membuat struk otomatis", Toast.LENGTH_SHORT).show();
                                    try {
                                        if(toko!= null && penjualan != null){
                                            PDFUtils utils = new PDFUtils(penjualan, toko, user.getUsername());
                                            utils.createPdfForReceipt();
                                        }


                                        Toast.makeText(PembayaranActivity.this, "struk berhasil dibuat", Toast.LENGTH_SHORT).show();
                                    }catch(Exception e){
                                        e.printStackTrace();
                                        Toast.makeText(PembayaranActivity.this, "pembuatan struk gagal", Toast.LENGTH_SHORT).show();
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                penjualan = p;


                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference dr = db.getReference().child("Penjualan");
                dr.push().setValue(p).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(PembayaranActivity.this, "Pembayaran berhasil diinput", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PembayaranActivity.this, "Pembayaran gagal diinput", Toast.LENGTH_SHORT).show();
                    }
                });

                final List<Penyimpanan> listPenyimpanan = new ArrayList<>();
                Task<Void> task = null;
                for (ItemKeranjang keranjang : listKeranjang) {
                    Penyimpanan pe = new Penyimpanan(c.getTimeInMillis(),
                            sharedPref.getString(keranjang.getKode().concat("key"), ""), keranjang.getKode(), keranjang.getQty(), "Penjualan", 1);

                    task = FirebaseDatabase.getInstance().getReference("Penyimpanan").push().setValue(pe);
                    listPenyimpanan.add(pe);

                }

                task.addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(PembayaranActivity.this, listPenyimpanan.size() + " item masuk penyimpanan", Toast.LENGTH_SHORT).show();
                    }
                });


                db.getReference("Barang").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot sn : dataSnapshot.getChildren()) {

                            for (Penyimpanan p : listPenyimpanan) {
                                System.out.println("snapshot "+ sn.getKey() + " key " + p.getKeyBarang());

                                if(p.getKeyBarang().equals(sn.getKey())){
                                    Barang barang = sn.getValue(Barang.class);

                                    int stok = barang.getStok() - p.getJumlahBarang();
                                    System.out.println("NAMA BARANG" + barang.getKode());
                                    System.out.println("STOK BARANG" + stok);
                                    barang.setStok(stok);
                                    sn.getRef().setValue(barang);

                                    if(barang.getStok() < barang.getMinStok()){
                                        showNotification(barang);
                                        insertNotif(barang);
                                    }

                                }
//                            dataSnapshot.getRef().child(p.getKeyBarang()).child("")
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




                SharedPreferences pref = getSharedPreferences(PACKAGE_NAME+"PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.clear();
                edit.apply();

                ItemKeranjangDbHelper helper = new ItemKeranjangDbHelper(this);
                helper.deleteAll();
                helper.close();

                break;

            case R.id.im_delete:
                showDeleteDialog();
                break;

            case R.id.im_cancel:
                finish();
                break;

        }
    }




    void showPembayaranBerhasilDialog() {
        ViewGroup group = findViewById(android.R.id.content);

        View view = LayoutInflater.from(this).inflate(R.layout.popup_transaksi_berhasil, group, false);

        TextView tvTanggal = view.findViewById(R.id.tv_waktu_transaksi);
        final ImageView imClosePopUp = view.findViewById(R.id.close_popup);
        final Button btnLihatStruk = view.findViewById(R.id.btn_lihat_struk);


        TextView tvNamaPelanggan = view.findViewById(R.id.tv_popup_namapelanggan);
        TextView tvNoHp = view.findViewById(R.id.tv_popup_nohp);
        TextView tvUangBayar = view.findViewById(R.id.tv_popup_pilihpembayaran);
        TextView tvDiskon = view.findViewById(R.id.tv_popup_diskon);
        TextView tvTotalBayarTransaksi = view.findViewById(R.id.tv_popup_totalbayar);
        TextView tvKembalianTransaksi = view.findViewById(R.id.tv_popup_kembalian);


        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy, HH:mm");
        String date = format.format(new Date());
        tvTanggal.setText(date.concat(" WIB"));
        tvNamaPelanggan.setText(etNamaPelanggan.getText().toString());
        tvNoHp.setText(etNoHp.getText().toString());
        tvDiskon.setText(etDiskon.getText().toString());
        tvTotalBayarTransaksi.setText("Rp. " + (fmt.format(totalHarga)));
        tvKembalianTransaksi.setText("Rp. " + (fmt.format(totalKembalian)));

        double uangBayar;
        uangBayar = totalKembalian + totalHarga;

        tvUangBayar.setText("Rp. " + (fmt.format(uangBayar)));


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        imClosePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent close = new Intent(PembayaranActivity.this, TransaksiActivity.class);
                close.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(close);
            }
        });

        btnLihatStruk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lihatStruk = new Intent(PembayaranActivity.this, StrukPenjualanActivity.class);
                lihatStruk.putExtra("penjualan", penjualan);
                lihatStruk.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(lihatStruk);
            }
        });
    }

    void showDeleteDialog() {

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Hapus Pesanan");
        alertDialog.setMessage("Apakah anda ingin menghapus pesanan ini ? ");
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ItemKeranjangDbHelper itemDb = new ItemKeranjangDbHelper(PembayaranActivity.this);

                        SQLiteDatabase db = itemDb.getWritableDatabase();

                        SharedPreferences pref = PembayaranActivity.this.getSharedPreferences("com.example.pelangiaquscape.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = pref.edit();
                        edit.clear();
                        edit.apply();

                        // Issue SQL statement.
                        int deletedRows = db.delete(ItemKeranjangContract.ItemKeranjangEntry.TABLE_NAME, null, null);


                        Toast.makeText(PembayaranActivity.this, deletedRows + " item terhapus", Toast.LENGTH_SHORT).show();
                        Intent close = new Intent(PembayaranActivity.this, TransaksiActivity.class);
                        close.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                        startActivity(close);
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "The Channel Name";
            String description = "The Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Pelangi Aquascape : test title", importance);
            channel.setDescription("Test Message");
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.setShowBadge(true);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    void showNotification(Barang barang){

        int icon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? R.drawable.logoblack : R.drawable.logoblack;

        String message = "Stok minimal "+barang.getMinStok() + " ,Stok saat ini "+barang.getStok();
        String title = "Barang " + barang.getKode() + " telah mencapai stok minimal";

        Intent intent = new Intent(this, DetailPemberitahuanActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("message", message);
        intent.putExtra("title", title);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Notification notification = new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(icon)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .build();


            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(1221, notification);

        }

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1221, builder.build());
    }

    private void insertNotif(Barang barang) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
        long waktu = calendar.getTimeInMillis();
        String namaBarang = barang.getKode();
        String judul = "Stok Barang";
        String pesan = "Persediaan barang " + namaBarang + " sudah mencapai batas minimum";

        Pemberitahuan pemberitahuan = new Pemberitahuan(judul, pesan ,namaBarang, waktu,  false, false );

        FirebaseDatabase.getInstance().getReference("Pemberitahuan").push().setValue(pemberitahuan).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });



    }


}
