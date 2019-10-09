package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class LupaPasswordActivity extends AppCompatActivity {

    TextView masuk;
    private TextInputEditText etEmailReset;
    private Button btnAturUlang;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        //INIT VIEW
        masuk = findViewById(R.id.tv_masuk);
        etEmailReset = findViewById(R.id.et_email_forgot);
        btnAturUlang = findViewById(R.id.btn_atur_ulang);

        //INIT FIREBASE
        firebaseAuth = FirebaseAuth.getInstance();

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent masuk = new Intent(LupaPasswordActivity.this, LoginActivity.class);
                startActivity(masuk);
            }
        });

        btnAturUlang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = etEmailReset.getText().toString().trim();

                if (userEmail.equals("")){
                    Toast.makeText(LupaPasswordActivity.this,"Silahkan masukkan ID email anda", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(LupaPasswordActivity.this, "Kata sandi atur ulang email terkirim", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(LupaPasswordActivity.this, LoginActivity.class));
                            } else {
                                Toast.makeText(LupaPasswordActivity.this, "Kesalahan dalam mengirim email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
