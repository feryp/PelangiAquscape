package com.example.pelangiaquscape;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BantuanActivity extends AppCompatActivity {

    ImageView cancel;
    EditText etSearch;
    ImageButton btnSearch;
    private ListView listView;

    String mTitle[] = {"Cara Pembelian Barang", "Cara Transaksi Penjualan", "Cara Penyimpanan Barang", "Cara Menambah Barang",
            "Cara Penerimaan Barang", "Cara Menambah Mitra Bisnis", "Cara Menambah Pegawai", "Cara Melihat Laporan", "Cara Melihat Rekap Kas",
            "Cara Melihat Pemberitahuan", "Cara Mengubah Akun", "Cara Mengubah Akun Toko", "Cara Mengatur Printer"
    };
    String mDescription[] = {
            "Bagaimana cara membeli barang di Aplikasi Pelangi Aquascape?",
            "Bagaimana cara melakukan transaksi penjualan pada Aplikasi Pelangi Aquascape?",
            "Bagaimana cara untuk menyimpan barang digudang pada Aplikasi Pelangi Aquascape?",
            "Bagaimana cara untuk menambah barang pada Aplikasi Pelangi Aquascape?",
            "Bagaimana cara untuk penerimaan barang dari proses pembelian pada Aplikasi Pelangi Aquascape?",
            "Bagaimana cara untuk menambahkan data mitra bisnis pada Aplikasi Pelangi Aquascape?",
            "Bagaimana cara untuk menambahkan data pegawai pada Aplikasi Pelangi Aquascape?",
            "Bagaimana cara untuk melihat laporan penjualan pada Aplikasi Pelangi Aquascape?",
            "Bagaimana cara untuk melihat rekap kas pada Aplikasi Pelangi Aquascape?",
            "Bagaimana cara untuk melihat pemberitahuan pada Aplikasi Pelangi Aquascape?",
            "Bagaimana cara untuk mengubah akun pada Aplikasi Pelangi Aquascape?",
            "Bagaimana cara untuk mengubah akun toko pada Aplikasi Pelangi Aquascape?",
            "Bagaimana cara untuk mengatur printer pada Aplikasi Pelangi Aquascape?"

    };

    int images[] = {R.drawable.ic_question, R.drawable.ic_question, R.drawable.ic_question, R.drawable.ic_question,
            R.drawable.ic_question, R.drawable.ic_question, R.drawable.ic_question, R.drawable.ic_question,
            R.drawable.ic_question, R.drawable.ic_question, R.drawable.ic_question, R.drawable.ic_question,
            R.drawable.ic_question,
    };
    CardView caraPembelian, caraPenjualan, caraPenyimpanan, caraTambahBarang, caraPenerimaan, caraMitraBisnis, caraLaporan, caraRekap, caraPemberitahuan, caraAkun, caraAkunToko, caraPrinter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bantuan);

        cancel = findViewById(R.id.im_cancel);
        listView = findViewById(R.id.list_view_bantuan);
        etSearch = findViewById(R.id.search_field_bantuan);
        btnSearch = findViewById(R.id.btn_search);

        MyAdapter adapter = new MyAdapter(this, mTitle, mDescription, images);
        listView.setAdapter(adapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              search();
            }

            private void search() {
                etSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Toast.makeText(BantuanActivity.this, "Cara Pembelian", Toast.LENGTH_SHORT).show();
                    Intent caraPembelian = new Intent(BantuanActivity.this, PusatBantuanPembelianActivity.class);
                    startActivity(caraPembelian);
                }
                if (position == 1) {
                    Toast.makeText(BantuanActivity.this, "Cara Penjualan", Toast.LENGTH_SHORT).show();
                    Intent caraPenjualan = new Intent(BantuanActivity.this, PusatBantuanPenjualanActivity.class);
                    startActivity(caraPenjualan);
                }
                if (position == 2) {
                    Toast.makeText(BantuanActivity.this, "Cara Penyimpanan", Toast.LENGTH_SHORT).show();
                    Intent caraPenyimpanan = new Intent(BantuanActivity.this, PusatBantuanPenyimpananActivity.class);
                    startActivity(caraPenyimpanan);
                }
                if (position == 3) {
                    Toast.makeText(BantuanActivity.this, "Cara Tambah Barang", Toast.LENGTH_SHORT).show();
                    Intent caraTambahBarang = new Intent(BantuanActivity.this, PusatBantuanTambahBarangActivity.class);
                    startActivity(caraTambahBarang);
                }
                if (position == 4) {
                    Toast.makeText(BantuanActivity.this, "Cara Penerimaan", Toast.LENGTH_SHORT).show();
                    Intent caraPenerimaan = new Intent(BantuanActivity.this, PusatBantuanPenerimaanActivity.class);
                    startActivity(caraPenerimaan);
                }
                if (position == 5) {
                    Toast.makeText(BantuanActivity.this, "Cara Mitra Bisnis", Toast.LENGTH_SHORT).show();
                    Intent caraMitraBisnis = new Intent(BantuanActivity.this, PusatBantuanMitraBisnisActivity.class);
                    startActivity(caraMitraBisnis);
                }
                if (position == 6) {
                    Toast.makeText(BantuanActivity.this, "Cara Pegawai", Toast.LENGTH_SHORT).show();
                    Intent caraMitraBisnis = new Intent(BantuanActivity.this, PusatBantuanPegawaiActivity.class);
                    startActivity(caraMitraBisnis);
                }
                if (position == 7) {
                    Toast.makeText(BantuanActivity.this, "Cara Laporan", Toast.LENGTH_SHORT).show();
                    Intent caraLaporan = new Intent(BantuanActivity.this, PusatBantuanLaporanActivity.class);
                    startActivity(caraLaporan);
                }
                if (position == 8) {
                    Toast.makeText(BantuanActivity.this, "Cara Rekap Kas", Toast.LENGTH_SHORT).show();
                    Intent caraRekap = new Intent(BantuanActivity.this, PusatBantuanRekapActivity.class);
                    startActivity(caraRekap);
                }
                if (position == 9) {
                    Toast.makeText(BantuanActivity.this, "Cara Pemberitahuan", Toast.LENGTH_SHORT).show();
                    Intent caraPemberitahuan = new Intent(BantuanActivity.this, PusatBantuanPemberitahuanActivity.class);
                    startActivity(caraPemberitahuan);
                }
                if (position == 10) {
                    Toast.makeText(BantuanActivity.this, "Cara Ubah Akun", Toast.LENGTH_SHORT).show();
                    Intent caraAkun = new Intent(BantuanActivity.this, PusatBantuanAkunActivity.class);
                    startActivity(caraAkun);
                }
                if (position == 11) {
                    Toast.makeText(BantuanActivity.this, "Cara Ubah Akun Toko", Toast.LENGTH_SHORT).show();
                    Intent caraAkunToko = new Intent(BantuanActivity.this, PusatBantuanAkunTokoActivity.class);
                    startActivity(caraAkunToko);
                }
                if (position == 12) {
                    Toast.makeText(BantuanActivity.this, "Cara Atur Printer", Toast.LENGTH_SHORT).show();
                    Intent caraPrinter = new Intent(BantuanActivity.this, PusatBantuanPrinterActivity.class);
                    startActivity(caraPrinter);
                }
            }
        });
//        caraPembelian = findViewById(R.id.cv_cara_pembelian_barang);
//        caraPenjualan = findViewById(R.id.cv_cara_transaksi_penjualan);
//        caraPenyimpanan = findViewById(R.id.cv_cara_penyimpanan);
//        caraTambahBarang = findViewById(R.id.cv_cara_tambah_barang);
//        caraPenerimaan = findViewById(R.id.cv_cara_penerimaan_barang);
//        caraMitraBisnis = findViewById(R.id.cv_cara_mitra_bisnis);
//        caraLaporan = findViewById(R.id.cv_cara_lihat_laporan);
//        caraRekap = findViewById(R.id.cv_cara_lihat_rekap);
//        caraPemberitahuan = findViewById(R.id.cv_cara_lihat_pemberitahuan);
//        caraAkun = findViewById(R.id.cv_cara_edit_akun);
//        caraAkunToko = findViewById(R.id.cv_cara_edit_akun_toko);
//        caraPrinter = findViewById(R.id.cv_cara_setting_printer);
//
//        caraPembelian.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent caraPembelian = new Intent(BantuanActivity.this, PusatBantuanPembelianActivity.class);
//                startActivity(caraPembelian);
//            }
//        });
//
//        caraPenjualan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent caraPenjualan = new Intent(BantuanActivity.this, PusatBantuanPenjualanActivity.class);
//                startActivity(caraPenjualan);
//            }
//        });
//
//        caraPenyimpanan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent caraPenyimpanan = new Intent(BantuanActivity.this, PusatBantuanPenyimpananActivity.class);
//                startActivity(caraPenyimpanan);
//            }
//        });
//
//        caraTambahBarang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent caraTambahBarang = new Intent(BantuanActivity.this, PusatBantuanTambahBarangActivity.class);
//                startActivity(caraTambahBarang);
//            }
//        });
//
//        caraPenerimaan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent caraPenerimaan = new Intent(BantuanActivity.this, PusatBantuanPenerimaanActivity.class);
//                startActivity(caraPenerimaan);
//            }
//        });
//
//        caraMitraBisnis.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent  caraMitraBisnis = new Intent(BantuanActivity.this, PusatBantuanMitraBisnisActivity.class);
//                startActivity(caraMitraBisnis);
//            }
//        });
//
//        caraLaporan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent  caraLaporan = new Intent(BantuanActivity.this, PusatBantuanLaporanActivity.class);
//                startActivity(caraLaporan);
//            }
//        });
//
//        caraRekap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent   caraRekap = new Intent(BantuanActivity.this, PusatBantuanRekapActivity.class);
//                startActivity(caraRekap);
//            }
//        });
//
//        caraPemberitahuan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent caraPemberitahuan = new Intent(BantuanActivity.this, PusatBantuanPemberitahuanActivity.class);
//                startActivity(caraPemberitahuan);
//            }
//        });
//
//        caraAkun.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent caraAkun = new Intent(BantuanActivity.this, PusatBantuanAkunActivity.class);
//                startActivity(caraAkun);
//            }
//        });
//
//        caraAkunToko.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent caraAkunToko = new Intent(BantuanActivity.this, PusatBantuanAkunTokoActivity.class);
//                startActivity(caraAkunToko);
//            }
//        });
//
//        caraPrinter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent caraPrinter = new Intent(BantuanActivity.this, PusatBantuanPrinterActivity.class);
//                startActivity(caraPrinter);
//            }
//        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String rTitle[];
        String rDescription[];
        int rImgs[];

        public MyAdapter(Context context, String title[], String description[], int imgs[]) {
            super(context, R.layout.list_bantuan, title);
            this.context = context;
            this.rTitle = title;
            this.rDescription = description;
            this.rImgs = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View list = layoutInflater.inflate(R.layout.list_bantuan, parent, false);
            ImageView image = list.findViewById(R.id.ic_logo_qustion);
            TextView myTitle = list.findViewById(R.id.tv_main);
            TextView myDesc = list.findViewById(R.id.tv_sub);

            image.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            myDesc.setText(rDescription[position]);
            return list;
        }
    }
}
