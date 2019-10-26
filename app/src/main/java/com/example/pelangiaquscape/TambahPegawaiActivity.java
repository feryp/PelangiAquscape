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


    String idPegawai;

    private static final int IMAGE_REQUEST = 1;
    private static final int RESULT_LOAD_IMAGE = 1;
    static final int REQUEST_IMAGE_CAPTURE = 80;
    private String currentPhotoPath;
    private Uri mImageUri;
    private StorageTask uploadTask;
    private ProgressDialog pd;


    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pegawai);

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
//        firebaseUser.getIdToken(true).getResult();
        firebaseUser.getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<GetTokenResult> task) {
                if(task.isSuccessful()){
                    token = task.getResult().getToken();
                }
            }
        });

        try {
            idPegawai = getIntent().getExtras().getString("idForPegawai");
            System.out.println("ID in tambah pegawai act " + idPegawai);
            bind(getIntent().getExtras().getString("fotoPegawai"),
                    getIntent().getExtras().getString("namaPegawai"),
                    getIntent().getExtras().getString("namaPengguna"),
                    getIntent().getExtras().getString("password"),
                    getIntent().getExtras().getString("jabatan"),
                    getIntent().getExtras().getString("hakAkses"),
                    getIntent().getExtras().getString("emailPegawai"),
                    getIntent().getExtras().getString("noHp"));
        } catch (NullPointerException ex){
            idPegawai = firebaseAuth.getUid();
        }

//        databaseReference = firebaseDatabase.getReference("User").child(firebaseAuth.getUid());
        storageReference = FirebaseStorage.getInstance().getReference("foto pegawai");

        pegawai = new Pegawai();




        //SET IMAGE
        if (mImageUri != null) {
            Picasso.get().load(mImageUri).into(imgFotoprofile);
        }

//        try {
//            idPegawai = getIntent().getExtras().getString("idPegawai");
//            pegawai = getIntent().getParcelableExtra("modelPegawai");
//            setFormFromModelPegawai();
////            System.out.println("ID in tambah pegawai act " + idPegawai);
//        } catch (NullPointerException ex) {
////            idPegawai = "1";
////            idPegawai =
//        }

        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this, R.array.akses_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinnerHakAkses.setAdapter(adapter);

//       boolean fromPegawai = getIntent().getExtras().getBoolean("fromPegawai", false);
//        if (fromPegawai){
//
//
//            String fotoPegawai = getIntent().getExtras().getString("fotoPegawai");
//            String namaPegawai = getIntent().getExtras().getString("namaPegawai");
//            String namaPengguna = getIntent().getExtras().getString("namaPengguna");
//            String kataSandi = getIntent().getExtras().getString("password");
//            String Jabatan = getIntent().getExtras().getString("jabatan");
//            String hakAkses = getIntent().getExtras().getString("hakAkses");
//            String emailPegawai = getIntent().getExtras().getString("emailPegawai");
//            String noHp = getIntent().getExtras().getString("noHp");
//
//            daftarPegawai(fotoPegawai, namaPegawai, namaPengguna, kataSandi, Jabatan, hakAkses, emailPegawai, noHp);
//        }

        //REGISTER LISTENER
        cancel.setOnClickListener(this);
        save.setOnClickListener(this);
        add_foto.setOnClickListener(this);

    }


    private void bind(final
                      String fotoPegawai,
                      String namaPegawai,
                      String namaPengguna,
                      String kataSandi,
                      String jabatan,
                      String hakAkses,
                      String emailPegawai,
                      String noHp) {

        int idxHakAkses = -1;

        String[] hakAksesArray = getResources().getStringArray(R.array.akses_arrays);

        for (int i = 0; i < hakAksesArray.length; i++){
            if (hakAksesArray[i].equals(hakAkses)){
                idxHakAkses = i;
            }
        }
//        imgFotoprofile.setImageResource(Integer.parseInt(fotoPegawai));
        etNamaPegawai.setText(namaPegawai);
        etNamaPengguna.setText(namaPengguna);
        etKataSandi.setText(kataSandi);
        etJabatan.setText(jabatan);
        spinnerHakAkses.setSelection(idxHakAkses);
        etEmailPegawai.setText(emailPegawai);
        etNoHp.setText(noHp);
    }


    private void daftarPegawai(final String fotoPegawai,
                               String namaPegawai,
                               String namaPengguna,
                               String kataSandi,
                               String jabatan,
                               String hakAkses,
                               String emailPegawai,
                               String noHp) {

//        int idxHakAkses = -1;
//
//        String[] hakAksesArray = getResources().getStringArray(R.array.akses_arrays);
//
//        for (int i = 0; i < hakAksesArray.length; i++){
//            if (hakAksesArray[i].equals(hakAkses)){
//                idxHakAkses = i;
//            }
//        }

//        int finalIdxHakAkses = idxHakAkses;
        firebaseAuth.createUserWithEmailAndPassword(emailPegawai, kataSandi).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                FirebaseUser fUser = task.getResult().getUser();
                String userid = fUser.getUid();
                Pegawai pegawai = new Pegawai();
                pegawai.setId(userid);
                pegawai.setFotoPegawai("");
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
//                            pd.dismiss();

                        }
                    }

                });

//                        String id, String username, String telepon, String email, String password,  String kodeLogin, String fotoProfile, String bio
                User user = new User();
                user.setId(userid);
                user.setFotoProfile(fotoPegawai);
                user.setUsername(namaPengguna);
                user.setTelepon(noHp);
                user.setEmail(emailPegawai);
                user.setPassword(kataSandi);
                user.setKodeLogin(hakAkses);
                user.setBio("");

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

        String uid = FirebaseAuth.getInstance().getUid();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference pegawaiRef = storageRef.child("Foto Pegawai").child(uid + ".jpg");
        pegawaiRef.getDownloadUrl().addOnSuccessListener(uri -> {
            if (uri != null){
                mImageUri = uri;
                Picasso.get().load(uri).into(imgFotoprofile);
            }
        });


    }

    private void getValues(){
//        pegawai.setFotoPegawai(imgFotoprofile.getDrawable().toString());
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
                databaseReference.child(String.valueOf(idPegawai)).setValue(pegawai);
                Toast.makeText(TambahPegawaiActivity.this, "Pegawai telah ditambah", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        spinnerHakAkses.getSelectedItem().toString();
//        if(idPegawai != null){
//
//        }else{
//            daftarPegawai("",
//                    etNamaPegawai.getText().toString(),
//                    etNamaPengguna.getText().toString(),
//                    etKataSandi.getText().toString(),
//                    etJabatan.getText().toString(),
//                    String.valueOf(spinnerHakAkses.getSelectedItemPosition()),
//                    etNoHp.getText().toString(),
//                    etEmailPegawai.getText().toString());
//        }


    }

    //
//    private void setFormFromModelPegawai() {
////        pegawai.setFotoPegawai(fotoPegawai.getDrawable().toString());
//        pegawai.setNamaPegawai(etNamaPegawai.getText().toString());
//        pegawai.setNamapengguna(etNamaPengguna.getText().toString());
//        pegawai.setPassword(etKataSandi.getText().toString());
//        pegawai.setJabatan(etJabatan.getText().toString());
//        pegawai.setHakAkses(spinnerHakAkses.getSelectedItem().toString());
//        pegawai.setNoHp(etNoHp.getText().toString());
//        pegawai.setEmailPegawai(etEmailPegawai.getText().toString());
//
//    }

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
                save();
                uploadToCloudStorage();
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

    private void uploadToCloudStorage() {
        Uri file = mImageUri;

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference pegawaiRef = storageRef.child("Foto Pegawai").child(pegawai.getId() + ".jpg");


        UploadTask uploadTask = pegawaiRef.putFile(file);

        // Register observers to listen for when the download is done or if it fails
        uploadTask
                .addOnProgressListener(taskSnapshot -> {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                    ProgressDialog dialog = new ProgressDialog(TambahPegawaiActivity.this);
                    dialog.setMessage("Sedang mengupload foto ...");
                    dialog.setIndeterminate(false);
                    dialog.setProgress((int) progress);
//                    dialog.show();
//
//                    dialog.dismiss();
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


}
