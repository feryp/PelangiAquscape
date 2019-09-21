package com.example.pelangiaquscape;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pelangiaquscape.Model.Pembelian;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class PreviewFakturPenerimaanActivity extends AppCompatActivity {


    TextView tvTerunggah, tvNamaFaktur;
    ImageView imTerunggah, imCancel,imView;

    Pembelian pembelian;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_faktur_penerimaan);

        // INTENT
        Intent i = getIntent();
        pembelian = i.getExtras().getParcelable("pembelian");

        // INIT VIEW
        tvTerunggah = findViewById(R.id.telah_terunggah);
        imTerunggah = findViewById(R.id.im_ket_terunggah);
        imView = findViewById(R.id.preview_faktur);
        imCancel = findViewById(R.id.im_cancel);
        tvNamaFaktur = findViewById(R.id.nama_gambar);

        // BELUM UPLOAD
        tvTerunggah.setText("Belum Upload");
        imTerunggah.setVisibility(View.INVISIBLE);

        loadImage();

    }

    void loadImage(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference load = storage.getReference("Faktur").child(pembelian.getNoPesanan()+".jpg");

        load.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                // Pass it to Picasso to download, show in ImageView and caching

                Picasso.get().load(uri.toString()).into(imView);
                tvTerunggah.setText("Sudah Terunggah");
                imTerunggah.setVisibility(View.VISIBLE);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }
}
