package com.example.pelangiaquscape;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.print.PrinterInfo;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelangiaquscape.Model.AkunToko;
import com.example.pelangiaquscape.Model.KartuPelanggan;
import com.example.pelangiaquscape.Model.Pelanggan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class KartuPelangganActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvNamaPelanggan, tvNoHpPelanggan, tvAlamatPelanggan, tvNamaToko, tvNoTeleponToko, tvEmailToko, tvAlamatToko;
    //    Button cetak;
    ImageView cancel, cetak;
    LinearLayout llPdf;

    Pelanggan pelanggan;
    AkunToko akunToko;
    String key;
    Bitmap bitmap;

    public static Bitmap loadBitmapFromView(View view, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        view.draw(c);

        return b;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kartu_pelanggan);

        //GET DATA
        Intent p = getIntent();
        pelanggan = p.getParcelableExtra("pelanggan");
        key = p.getStringExtra("key");

        //INIT VIEW
        tvNamaPelanggan = findViewById(R.id.kartu_nama_pelanggan);
        tvNoHpPelanggan = findViewById(R.id.kartu_no_hp_pelanggan);
        tvAlamatPelanggan = findViewById(R.id.kartu_alamat_pelanggan);
        tvNamaToko = findViewById(R.id.kartu_nama_toko);
        tvNoTeleponToko = findViewById(R.id.kartu_no_telepon_toko);
        tvEmailToko = findViewById(R.id.kartu_email_toko);
        tvAlamatToko = findViewById(R.id.kartu_alamat_toko);
        cancel = findViewById(R.id.im_cancel);
        cetak = findViewById(R.id.im_cetak);
        llPdf = findViewById(R.id.llPdf);

        //SET TEXT
        tvNamaPelanggan.setText(pelanggan.getNamaPelanggan());
        tvNoHpPelanggan.setText(pelanggan.getNoHp());
        tvAlamatPelanggan.setText(pelanggan.getAlamat());

        loadAkun();

        cetak.setOnClickListener(this);

        cancel.setOnClickListener(this);

    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    void loadAkun() {
        FirebaseDatabase.getInstance().getReference("AkunToko").child("1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AkunToko akunToko = dataSnapshot.getValue(AkunToko.class);

                tvNamaToko.setText(akunToko.getNamaToko());
                tvNoTeleponToko.setText(akunToko.getNoTelepon());
                tvEmailToko.setText(akunToko.getEmailToko());
                tvAlamatToko.setText(akunToko.getAlamat());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_cancel:
                finish();
                break;
            case R.id.im_cetak:
                /*showToast("Cetak Kartu");*/
                Log.d("size", " " + llPdf.getWidth() + " " + llPdf.getWidth());
                bitmap = loadBitmapFromView(llPdf, llPdf.getWidth(), llPdf.getHeight());
//                String fpath = "/sdcard/pdfkartupelanggan.pdf";
//                File file = new File(fpath);
                createPdf();
                break;
        }
    }

    private void createPdf() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float hight = displayMetrics.heightPixels;
        float width = displayMetrics.widthPixels;

        int convertHeight = (int) hight, convertWidth = (int) width;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            PdfDocument document = new PdfDocument();
            android.graphics.pdf.PdfDocument.PageInfo pageInfo = new android.graphics.pdf.PdfDocument.PageInfo.Builder(convertWidth, convertHeight, 1).create();
            PdfDocument.Page page = document.startPage(pageInfo);

            Canvas canvas = page.getCanvas();

            Paint paint = new Paint();
            canvas.drawPaint(paint);

            bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHeight, true);

            paint.setColor(Color.BLUE);
            canvas.drawBitmap(bitmap, 0, 0, null);
            document.finishPage(page);

            //write the document content
            String targetPdf = "/sdcard/Kartu_Pelanggan_" + pelanggan.getNamaPelanggan() + ".pdf";
            File filePath = new File(targetPdf);
            try {
                document.writeTo(new FileOutputStream(filePath));
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Ada yang salah : " + e.toString(), Toast.LENGTH_LONG).show();
            }

            //close the document
            document.close();
            Toast.makeText(this, "PDF sudah dibuat", Toast.LENGTH_SHORT).show();
            openGeneratedPDF(filePath);

        }


    }

    private void openGeneratedPDF(File file) {

        Uri uri = FileProvider.getUriForFile(this, "com.example.pelangiaquscape.fileprovider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
