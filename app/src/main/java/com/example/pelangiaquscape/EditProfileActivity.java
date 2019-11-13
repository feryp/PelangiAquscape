package com.example.pelangiaquscape;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
import com.example.pelangiaquscape.Model.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;
//import com.theartofdev.edmodo.cropper.CropImage;
//import com.theartofdev.edmodo.cropper.CropImageView;


public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_LOAD_IMAGE = 1;
    final String EXTRA = "INTENT_EDIT_TO_MAIN";
    ImageView cancel, save, imgFotoprofile;
    TextView tvUbah;
    TextInputEditText etNamaAkun, etStatusJabatan, etNoHp, etEmail;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    String namaPengguna, statusJabatan, biodata, fotoProfile, kodeLogin;
    User user;
    private Uri mImageUri;
    private StorageTask uploadTask;
    private Uri currentPhotoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        // INIT VIEW
        cancel = findViewById(R.id.im_cancel);
        save = findViewById(R.id.im_save);
        imgFotoprofile = findViewById(R.id.image_profile);
        tvUbah = findViewById(R.id.tv_ubah_foto);
        etNamaAkun = findViewById(R.id.et_nama_akun_pengguna);
        etStatusJabatan = findViewById(R.id.et_status_jabatan);
        etNoHp = findViewById(R.id.et_telepon);
        etEmail = findViewById(R.id.et_email);

        // INIT FIREBASE
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        // REGISTER LISTENER
        cancel.setOnClickListener(this);
        tvUbah.setOnClickListener(this);


        loadProfile();
    }

    void loadProfile() {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference("User");

        dr.child(firebaseAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user1 = dataSnapshot.getValue(User.class);
                System.out.println("EditProfileActivity " + user1.getId());
                if (user1 != null) {
                    etNamaAkun.setText(user1.getUsername());
                    etNoHp.setText(user1.getTelepon());
                    etEmail.setText(user1.getEmail());
                    if (user1.getKodeLogin().equals("1")) {
                        etStatusJabatan.setText("Admin");
                    } else {
                        etStatusJabatan.setText("Super Admin");
                    }

                    user = user1;
                    // etEmail.setEnabled(false);
                    etStatusJabatan.setEnabled(false);

                }

                save.setOnClickListener(EditProfileActivity.this);

//                try {
//                    update(getIntent().getExtras().getString("username"),
//                            getIntent().getExtras().getString("kodeLogin"),
//                            getIntent().getExtras().getString("telepon"),
//                            getIntent().getExtras().getString("bio"));
//                } catch (NullPointerException ex){
//
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        String uid = FirebaseAuth.getInstance().getUid();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference fakturRef = storageRef.child("Profile").child(uid + ".jpg");
        fakturRef.getDownloadUrl().addOnSuccessListener(uri -> {
            if (uri != null) {
                currentPhotoUri = uri;
                Picasso.get().load(uri).into(imgFotoprofile);

            }
//            tvNamaFoto.setText(file.getName());
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            currentPhotoUri = uri;
            imgFotoprofile.setImageURI(uri);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_cancel:
                finish();
                break;
            case R.id.im_save:
                uploadToCloudStorage();
                break;
            case R.id.image_profile:
                break;
            case R.id.tv_ubah_foto:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pilih file"), RESULT_LOAD_IMAGE);
                break;
        }
    }


    void uploadToCloudStorage() {


        Uri file = currentPhotoUri;

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference fakturRef = storageRef.child("Profile").child(user.getId() + ".jpg");
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User").child(firebaseAuth.getUid());

        UploadTask uploadTask = fakturRef.putFile(file);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnProgressListener(taskSnapshot -> {
            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

            ProgressDialog dialog = new ProgressDialog(EditProfileActivity.this);
            dialog.setMessage("Ubah Data...");
            dialog.setIndeterminate(false);
            dialog.setProgress((int) progress);
            dialog.show();


//                    String nama_akun = etNamaAkun.getText().toString();
//                    String status_jabatan = etStatusJabatan.getText().toString();
//                    String no_hp = etNoHp.getText().toString();
//                    String bio = etBio.getText().toString();

//                    update(nama_akun, status_jabatan, no_hp, bio);

            // Kalo user update email
          /*  String emailFirebase = firebaseUser.getEmail();
            String emailBaru = etEmail.getText().toString();

            if (!(emailFirebase.equals(emailBaru))) {
                // Toast.makeText(this, "Email lama : " + emailFirebase + "\n Email baru : " + emailBaru, Toast.LENGTH_SHORT).show();

                FirebaseUser firebaseUser2 = firebaseAuth.getCurrentUser();

                firebaseUser2.updateEmail(emailBaru)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    userRef.child("email").setValue(emailBaru);
                                    Log.d("EMAIL", "email : " + emailBaru);
                                } else {
                                    Log.d("EMAIL", "error");
                                }
                            }
                        });
            } */

            // update data uname & telepon di node User
            userRef.child("username").setValue(etNamaAkun.getText().toString());
            userRef.child("telepon").setValue(etNoHp.getText().toString());

            if (etStatusJabatan.getText().toString().equals("Admin")) {
                DatabaseReference root = FirebaseDatabase.getInstance().getReference("Pegawai");
                Query userQuery = root.orderByChild("id").equalTo(firebaseAuth.getUid());

                userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // update data uname & telepon di node Pegawai (untuk admin/pegawai)
                            for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                String keyPegawai = issue.getKey();

                                root.child(keyPegawai).child("namapengguna").setValue(etNamaAkun.getText().toString());
                                root.child(keyPegawai).child("noHp").setValue(etNoHp.getText().toString());
                            }


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            dialog.dismiss();


        })
                .addOnFailureListener(exception -> {
                    // Handle unsuccessful uploads
                })
                .addOnSuccessListener(taskSnapshot -> {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    Toast.makeText(this, "Upload Foto Profil berhasil", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                });


    }

    private void update(final String nama_akun, final String status_jabatan, final String no_hp, final String bio) {

        etNamaAkun.setText(nama_akun);
        etStatusJabatan.setText(status_jabatan);
        etNoHp.setText(no_hp);
        etEmail.setText(bio);
    }


}
