package com.example.pelangiaquscape;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pelangiaquscape.Model.Pegawai;
import com.example.pelangiaquscape.Model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.GetTokenResult;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class TambahPegawaiActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView cancel, save, imgFotoprofile;
    CircleImageView  add_foto;
    Spinner spinnerHakAkses;
    TextInputEditText etNamaPegawai, etNamaPengguna, etKataSandi, etJabatan, etNoHp, etEmailPegawai;

    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    StorageReference storageReference;
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    Pegawai pegawai;


    String keyPegawai;

    private static final int IMAGE_REQUEST = 1;
    private static final int RESULT_LOAD_IMAGE = 1;
    static final int REQUEST_IMAGE_CAPTURE = 80;
    private String currentPhotoPath;
    private Uri mImageUri;
    private StorageTask uploadTask;
    private ProgressDialog pd;


    String token;

    boolean fromPegawaiActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pegawai);

        // get intent from pegawai activity
        Intent i = getIntent();

        if(i!=null){
            keyPegawai = i.getStringExtra("idPegawai");
            pegawai = i.getParcelableExtra("pegawai");
            fromPegawaiActivity =  i.getBooleanExtra("fromPegawaiActivity", false);

            if(pegawai.getFotoPegawai().length() > 0){
                Picasso.get().load(pegawai.getFotoPegawai()).into(imgFotoprofile);
            }


            bind(pegawai);
        }



        //INIT VIEW
        cancel = findViewById(R.id.im_cancel);
        save = findViewById(R.id.im_save);
        etNamaPegawai = findViewById(R.id.et_nama_pegawai);
        imgFotoprofile = findViewById(R.id.profile_pegawai);
        add_foto = findViewById(R.id.add_foto);
        etNamaPengguna = findViewById(R.id.et_nama_pengguna_pegawai);
        etKataSandi = findViewById(R.id.et_password);
        etJabatan = findViewById(R.id.et_jabatan);
        etNoHp = findViewById(R.id.et_no_hp_pegawai);
        etEmailPegawai = findViewById(R.id.et_email_pegawai);
        spinnerHakAkses = findViewById(R.id.spinner_hak_akses);

        //INIT FIREBASE
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Pegawai");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseUser.getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<GetTokenResult> task) {
                if(task.isSuccessful()){
                    token = task.getResult().getToken();
                }
            }
        });

        //SET IMAGE



        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this, R.array.akses_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinnerHakAkses.setAdapter(adapter);

        //REGISTER LISTENER
        cancel.setOnClickListener(this);
        save.setOnClickListener(this);
        add_foto.setOnClickListener(this);

    }


    private void bind(Pegawai pegawai) {

//        imgFotoprofile.setImageResource(Integer.parseInt(fotoPegawai));
        etNamaPegawai.setText(pegawai.getNamaPegawai());
        etNamaPengguna.setText(pegawai.getNamapengguna());
        etKataSandi.setText(pegawai.getPassword());
        etJabatan.setText(pegawai.getJabatan());
        spinnerHakAkses.setSelection(Integer.parseInt(pegawai.getHakAkses()));
        etEmailPegawai.setText(pegawai.getEmailPegawai());
        etNoHp.setText(pegawai.getNoHp());
    }


    private void daftarPegawai(String namaPegawai,
                               String namaPengguna,
                               String kataSandi,
                               String jabatan,
                               String hakAkses,
                               String emailPegawai,
                               String noHp) {

        final Pegawai pegawai = new Pegawai();
        firebaseAuth.createUserWithEmailAndPassword(emailPegawai, kataSandi).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                FirebaseUser fUser = task.getResult().getUser();
                String userid = fUser.getUid();

                String urlImage = uploadToCloudStorage(userid);

                pegawai.setId(userid);
                pegawai.setFotoPegawai(urlImage);
                pegawai.setNamaPegawai(namaPegawai);
                pegawai.setNamapengguna(namaPengguna);
                pegawai.setJabatan(jabatan);
                pegawai.setHakAkses(hakAkses);
                pegawai.setNoHp(noHp);
                pegawai.setEmailPegawai(emailPegawai);

                FirebaseDatabase.getInstance().getReference().child("Pegawai").push().setValue(pegawai).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                        }
                    }

                });

                User user = new User();
                user.setId(userid);
                user.setFotoProfile(urlImage);
                user.setUsername(namaPengguna);
                user.setTelepon(noHp);
                user.setEmail(emailPegawai);
                user.setPassword(kataSandi);
                user.setKodeLogin(hakAkses);


                FirebaseDatabase.getInstance().getReference("User").child(userid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(TambahPegawaiActivity.this, "Berhasil menambahkan user", Toast.LENGTH_SHORT).show();
                    }
                });



                FirebaseAuth.getInstance().signOut();

                Intent keluar = new Intent(TambahPegawaiActivity.this, LoginActivity.class);

                keluar.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(keluar);
                finish();


            }
        });

//        String uid = FirebaseAuth.getInstance().getUid();
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReference();
//        StorageReference pegawaiRef = storageRef.child("Profile").child(uid + ".jpg");

//        pegawaiRef.getDownloadUrl().addOnSuccessListener(uri -> {
//            if (uri != null){
//                mImageUri = uri;
//
//                pegawai.setFotoPegawai(uri.toString());
//                Picasso.get().load(uri).into(imgFotoprofile);
////                FirebaseDatabase.getInstance().getRe
//            }
//        });


    }

    private void getValues(){
//        pegawai.setFotoPegawai(imgFotoprofile.getDrawable().toString());
        uploadToCloudStorage(pegawai.getId());
        pegawai.setNamaPegawai(etNamaPegawai.getText().toString());
        pegawai.setNamapengguna(etNamaPengguna.getText().toString());
        pegawai.setPassword(etKataSandi.getText().toString());
        pegawai.setJabatan(etJabatan.getText().toString());
//        pegawai.setHakAkses(spinnerHakAkses.getSelectedItem().toString());
        pegawai.setEmailPegawai(etEmailPegawai.getText().toString());
        pegawai.setNoHp(etNoHp.getText().toString());

    }


//
    private void save() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getValues();
                databaseReference.child(String.valueOf(keyPegawai)).setValue(pegawai);
                Toast.makeText(TambahPegawaiActivity.this, "Pegawai telah ditambah", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private void setFormFromModelPegawai(){
        pegawai.setFotoPegawai(pegawai.getFotoPegawai());
        pegawai.setNamaPegawai(pegawai.getNamaPegawai());
        pegawai.setNamapengguna(pegawai.getNamapengguna());
        pegawai.setPassword(pegawai.getPassword());
        pegawai.setJabatan(pegawai.getJabatan());
//        pegawai.setHakAkses(pegawai);
        pegawai.setNoHp(pegawai.getNoHp());
        pegawai.setEmailPegawai(pegawai.getEmailPegawai());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            mImageUri = uri;
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

                if(!fromPegawaiActivity){
//                    pegawai.setNamaPegawai(etNamaPegawai.getText().toString());
//                    pegawai.setNamapengguna(etNamaPengguna.getText().toString());
//                    pegawai.setPassword(etKataSandi.getText().toString());
//                    pegawai.setJabatan(etJabatan.getText().toString());
////        pegawai.setHakAkses(spinnerHakAkses.getSelectedItem().toString());
//                    pegawai.setEmailPegawai(etEmailPegawai.getText().toString());
//                    pegawai.setNoHp(etNoHp.getText().toString());
                    daftarPegawai(etNamaPegawai.getText().toString(),
                            etNamaPengguna.getText().toString(),
                            etKataSandi.getText().toString(),
                            etJabatan.getText().toString(),
                            String.valueOf(spinnerHakAkses.getSelectedItemPosition()),etEmailPegawai.getText().toString(),
                            etNoHp.getText().toString());
                }else{
                    save();
                }
                finish();
                break;
            case R.id.add_foto:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pilih file"), RESULT_LOAD_IMAGE);
                break;
        }

    }

    private String uploadToCloudStorage(String uid) {
        Uri file = mImageUri;

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference pegawaiRef = storageRef.child("Profile").child(uid + ".jpg");


        UploadTask uploadTask = pegawaiRef.putFile(file);

        // Register observers to listen for when the download is done or if it fails
        uploadTask
                .addOnProgressListener(taskSnapshot -> {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                    ProgressDialog dialog = new ProgressDialog(TambahPegawaiActivity.this);
                    dialog.setMessage("Sedang mengupload foto ...");
                    dialog.setIndeterminate(false);
                    dialog.setProgress((int) progress);

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

        return pegawaiRef.getDownloadUrl().toString();
    }


}
