package com.example.pelangiaquscape;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelangiaquscape.Model.admin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DaftarActivity extends AppCompatActivity {

    TextInputEditText namapengguna, telepon, email, katasandi, ulangiKataSandi;
    Button btn_daftar;
    TextView masuk;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        namapengguna = (TextInputEditText) findViewById(R.id.et_nama_pengguna);
        telepon = (TextInputEditText) findViewById(R.id.et_telepon);
        email = (TextInputEditText) findViewById(R.id.et_email);
        katasandi = (TextInputEditText) findViewById(R.id.et_kata_sandi);
        ulangiKataSandi = (TextInputEditText) findViewById(R.id.et_ulangi);
        btn_daftar = (Button) findViewById(R.id.btn_daftar);
        masuk = (TextView) findViewById(R.id.tv_masuk);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("admin");

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent masuk = new Intent(DaftarActivity.this, LoginActivity.class);
                startActivity(masuk);
            }
        });

        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(DaftarActivity.this);
                pd.setMessage("Tunggu Sebentar...");
                pd.show();

                String str_namapengguna = namapengguna.getText().toString();
                String str_telepon = telepon.getText().toString();
                String str_email = email.getText().toString();
                String str_katasandi = katasandi.getText().toString();
                String str_ulangi = ulangiKataSandi.getText().toString();

                if (TextUtils.isEmpty(str_namapengguna) || TextUtils.isEmpty(str_telepon) ||
                        TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_katasandi)
                        || TextUtils.isEmpty(str_ulangi)) {
                    Toast.makeText(DaftarActivity.this, "All fileds are requiered!", Toast.LENGTH_SHORT).show();
                } else if (str_katasandi.length() < 6) {
                    Toast.makeText(DaftarActivity.this, "Password must have 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    btn_daftar(str_namapengguna, str_telepon, str_email, str_katasandi, str_ulangi);
                }

            }
        });
    }

        private void btn_daftar (final String namapengguna, final String telepon, final String email, final String
        katasandi, final String ulangi)
        {

            firebaseAuth.createUserWithEmailAndPassword(email,katasandi)
                    .addOnCompleteListener(DaftarActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                String adminid = firebaseUser.getUid();

                                databaseReference = FirebaseDatabase.getInstance().getReference().child("Admin").child(adminid);

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("id", adminid);
                                hashMap.put("namapengguna",namapengguna.toLowerCase());
                                hashMap.put("telepon", telepon);
                                hashMap.put("email", email);
                                hashMap.put("katasandi", katasandi);
                                hashMap.put("ulangkatasandi", ulangi);

                                databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            pd.dismiss();
                                            Intent intent = new Intent(DaftarActivity.this, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    }
                                });
                            } else {
                                pd.dismiss();
                                Toast.makeText(DaftarActivity.this, "You can't  register with this email or password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }