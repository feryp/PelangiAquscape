package com.example.pelangiaquscape;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelangiaquscape.Adapter.UploadPenerimaanAdapter;
import com.example.pelangiaquscape.Model.Pembelian;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UploadPenerimaanActivity extends AppCompatActivity implements View.OnClickListener {


    private LinearLayout containerKamera;
    private LinearLayout containerGaleri;
    private ImageView imPhoto, imSave, cancel;
    private TextView tvNamaFoto;
    private ProgressBar progressBar;

    private UploadPenerimaanAdapter uploadPenerimaanAdapter;
    private StorageReference mStorage;


    private String currentPhotoPath;

    private Uri currentPhotoUri;

    private static final int RESULT_LOAD_IMAGE = 1;
    static final int REQUEST_IMAGE_CAPTURE = 80;
    Pembelian pembelian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_penerimaan);

        // INTENT
        Intent i = getIntent();
        pembelian = i.getExtras().getParcelable("pembelian");
        try {
            currentPhotoUri = Uri.parse(i.getExtras().getString("uri"));
        }catch(NullPointerException e){

        }


        // INIT FIREBASE STORAGE
        mStorage = FirebaseStorage.getInstance().getReference();

        // INIT VIEW
        cancel = findViewById(R.id.im_cancel);
        containerKamera = findViewById(R.id.gunakan_kamera);
        containerGaleri = findViewById(R.id.pilih_dari_galeri);
        imPhoto = findViewById(R.id.preview_faktur);
        imSave = findViewById(R.id.im_save);
        tvNamaFoto = findViewById(R.id.nama_gambar);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        // REGISTER LISTENER
        cancel.setOnClickListener(this);
        containerKamera.setOnClickListener(this);
        containerGaleri.setOnClickListener(this);
        imSave.setOnClickListener(this);

        // SET IMAGE
        if(currentPhotoUri!= null){
            Picasso.get().load(currentPhotoUri).into(imPhoto);

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            galleryAddPic();

            File file = new File(currentPhotoPath);
            Uri uri = Uri.fromFile(file);
            currentPhotoUri = uri;
            imPhoto.setImageURI(uri);
            tvNamaFoto.setText(file.getName());
        }

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {

            Uri uri = data.getData();
            currentPhotoUri = uri;
            imPhoto.setImageURI(uri);
            tvNamaFoto.setText(getFileName(uri));

        }
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gunakan_kamera:
                if (ContextCompat.checkSelfPermission(UploadPenerimaanActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted
                    ActivityCompat.requestPermissions(UploadPenerimaanActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            REQUEST_IMAGE_CAPTURE);
                } else {
                    dispatchTakePictureIntent();
                }

                break;

            case R.id.pilih_dari_galeri:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pilih file"), RESULT_LOAD_IMAGE);
                break;

            case R.id.im_cancel:
                finish();
                break;

            case R.id.im_save:
                if (currentPhotoUri != null) {
                    uploadToCloudStorage();
                } else {
                    showLimitDialog();
                }

                break;

        }
    }

    private void dispatchTakePictureIntent() {


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }


            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.santuy.optimal.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

            }

        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        System.out.println(storageDir.toString());
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void showLimitDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Foto belum dipilih");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Cancel",
                (dialog, id) -> dialog.dismiss());


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    void uploadToCloudStorage() {

        progressBar.setVisibility(View.VISIBLE);
        Uri file = currentPhotoUri;

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference fakturRef = storageRef.child("Faktur").child(pembelian.getNoPesanan()+".jpg");



        UploadTask uploadTask = fakturRef.putFile(file);

        // Register observers to listen for when the download is done or if it fails
        uploadTask
                .addOnProgressListener(taskSnapshot -> {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                    ProgressDialog dialog = new ProgressDialog(UploadPenerimaanActivity.this);
                    dialog.setMessage("Sedang mengupload faktur...");
                    dialog.setIndeterminate(false);
                    dialog.setProgress((int)progress);
                    dialog.show();

//                    progressBar.setVisibility(View.VISIBLE);
//                    progressBar.setIndeterminate(false);
//                    progressBar.setProgress((int)progress);


//                    System.out.println("Upload is " + progress + "% done");
                })
                .addOnFailureListener(exception -> {
                    // Handle unsuccessful uploads
                })
                .addOnSuccessListener(taskSnapshot -> {
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    Toast.makeText(this, "Upload Faktur berhasil", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();

                });
    }
}
