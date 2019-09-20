package com.example.pelangiaquscape;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pelangiaquscape.Adapter.UploadPenerimaanAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class UploadPenerimaanActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView cancel;

    private static final int RESULT_LOAD_IMAGE = 1;
    private LinearLayout containerKamera;
    private LinearLayout containerGaleri;
    private RecyclerView rv_upload_penerimaan;

    private List<String> fileNameList;
    private List<String> fileDoneList;
    private UploadPenerimaanAdapter uploadPenerimaanAdapter;
    private StorageReference mStorage;

    static final int REQUEST_IMAGE_CAPTURE = 80;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_penerimaan);

        // INIT FIREBASE STORAGE
        mStorage = FirebaseStorage.getInstance().getReference();


        cancel = findViewById(R.id.im_cancel);
        containerKamera = findViewById(R.id.gunakan_kamera);
        containerGaleri = findViewById(R.id.pilih_dari_galeri);
        rv_upload_penerimaan = findViewById(R.id.rv_upload_penerimaan);


        // REGISTER LISTENER
        cancel.setOnClickListener(this);
        containerKamera.setOnClickListener(this);
        containerGaleri.setOnClickListener(this);



        // INIT LIST
        fileNameList = new ArrayList<>();
        fileDoneList = new ArrayList<>();

        // INIT ADAPTER
        uploadPenerimaanAdapter = new UploadPenerimaanAdapter(fileNameList, fileDoneList);

        //RecyclerView
        rv_upload_penerimaan.setLayoutManager(new LinearLayoutManager(this));
        rv_upload_penerimaan.setHasFixedSize(true);
        rv_upload_penerimaan.setAdapter(uploadPenerimaanAdapter);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {

            if (data.getClipData() != null) {

                int totalItemSelected = data.getClipData().getItemCount();

                for (int i = 0; i < totalItemSelected; i++) {

                    Uri fileUri = data.getClipData().getItemAt(i).getUri();

                    String fileName = getFileName(fileUri);

                    fileNameList.add(fileName);
                    fileDoneList.add("uploading");
                    uploadPenerimaanAdapter.notifyDataSetChanged();

                    StorageReference fileToUpload = mStorage.child("Faktur").child(fileName);

                    final int finalI = i;
                    fileToUpload.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            fileDoneList.remove(finalI);
                            fileDoneList.add(finalI, "Selesai");

                            uploadPenerimaanAdapter.notifyDataSetChanged();
                        }
                    });
                }
//                Toast.makeText(UploadPenerimaanActivity.this, "Selected Multiple Files", Toast.LENGTH_SHORT).show();

            } else if (data.getData() != null) {

                Toast.makeText(UploadPenerimaanActivity.this, "Selected Single Files", Toast.LENGTH_SHORT).show();
            }
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
        switch(v.getId()){
            case R.id.gunakan_kamera:
                dispatchTakePictureIntent();
                break;

            case R.id.pilih_dari_galeri:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pilih file"), RESULT_LOAD_IMAGE);
                break;

            case R.id.im_cancel:
                break;

            case R.id.im_save:
                finish();
                break;

        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
    }
}
