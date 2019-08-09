package com.example.pelangiaquscape;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
import com.bumptech.glide.Glide;
import com.example.pelangiaquscape.Model.User;
import com.example.pelangiaquscape.fragment.ProfileFragment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
//import com.theartofdev.edmodo.cropper.CropImage;
//import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {

    final String EXTRA = "INTENT_EDIT_TO_MAIN";
    ImageView cancel, save, imgFotoprofile;
    TextView ubah_foto;
    TextInputEditText nama_akun_pengguna, status_jabatan, bio;

    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    private static final int IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private StorageTask uploadTask;

    String namaPengguna, statusJabatan, biodata, fotoProfile, kodeLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        cancel = findViewById(R.id.im_cancel);
        save = findViewById(R.id.im_save);
        imgFotoprofile = findViewById(R.id.image_profile);
        ubah_foto = findViewById(R.id.tv_ubah_foto);
        nama_akun_pengguna = findViewById(R.id.et_nama_akun_pengguna);
        status_jabatan = findViewById(R.id.et_status_jabatan);
        bio = findViewById(R.id.et_bio);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User").child(firebaseAuth.getUid());
        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                User user = dataSnapshot.getValue(User.class);
//                nama_akun_pengguna.setText(user.getUsername());
//                status_jabatan.setText(user.getStatusJabatan());
//                bio.setText(user.getBio());
//                if (user.getFotoProfile().equals("default")){
//                    imgFotoprofile.setImageResource(R.drawable.ic_foto_profile);
//                } else {
//                    Glide.with(getApplicationContext()).load(user.getFotoProfile()).into(imgFotoprofile);
//                }
                namaPengguna = "" + dataSnapshot.child("namapengguna").getValue();
                kodeLogin = "" + dataSnapshot.child("kode_login").getValue();
                switch (kodeLogin) {
                    case "0":
                        statusJabatan = "Super Admin";
                        break;
                    case "1":
                        statusJabatan = "Pegawai";
                        break;
                }
                biodata = "" + dataSnapshot.child("bio").getValue();
                fotoProfile = "" + dataSnapshot.child("fotoProfile").getValue();


                try {
                    Picasso.get().load(fotoProfile).into(imgFotoprofile);
                } catch (IllegalArgumentException e) {
                    imgFotoprofile.setImageResource(R.drawable.ic_foto_profile);
                }

                //set data
                nama_akun_pengguna.setText(namaPengguna);
                status_jabatan.setText(statusJabatan);
                bio.setText(biodata);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//
        ubah_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
            }
        });

//        imgFotoprofile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile(
                        nama_akun_pengguna.getText().toString(),
                        status_jabatan.getText().toString(),
                        bio.getText().toString());
            }
        });
    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private void updateProfile(String nama_akun_pengguna, String status_jabatan, String bio) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());

//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("nama_akun_pengguna", nama_akun_pengguna);
//        hashMap.put("status_jabatan", status_jabatan);
//        hashMap.put("bio", bio);

//        reference.updateChildren(hashMap);

    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Diunggah");
        pd.show();

        if (mImageUri != null){
            final StorageReference filereference = storageReference.child(System.currentTimeMillis()
                    +"."+ getFileExtension(mImageUri));

            uploadTask = filereference.putFile(mImageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }

                    return filereference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String myUrl = downloadUri.toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("imageURL", myUrl);

                        reference.updateChildren(hashMap);
                        pd.dismiss();

                    } else {

                        Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });

        } else {

            Toast.makeText(this,"Tidak ada gambar yang dipilih", Toast.LENGTH_SHORT).show();

        }
    }

    //Ctrl + O

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
            && data != null && data.getData() != null){
            mImageUri = data.getData();

            if (uploadTask != null && uploadTask.isInProgress()){
                Toast.makeText(getApplicationContext(), "Unggah sedang dalam proses", Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();
            }
        }
    }
}
