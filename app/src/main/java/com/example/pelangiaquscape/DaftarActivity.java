package com.example.pelangiaquscape;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelangiaquscape.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.HashMap;
import java.util.List;

public class DaftarActivity extends AppCompatActivity implements View.OnClickListener, Validator.ValidationListener {


    @NotEmpty
    TextInputEditText namapengguna;

    @NotEmpty
    TextInputEditText telepon;

    @NotEmpty
    TextInputEditText email;

    @NotEmpty
    @Password
    TextInputEditText katasandi;

    @ConfirmPassword
    TextInputEditText ulangiKataSandi;

    private Button btn_daftar;
    private TextView masuk;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog pd;
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        // VALIDATOR
        validator = new Validator(this);
        validator.setValidationListener(this);

        // INIT VIEW
        namapengguna = findViewById(R.id.et_nama_pengguna);
        telepon = findViewById(R.id.et_telepon);
        email = findViewById(R.id.et_email);
        katasandi = findViewById(R.id.et_kata_sandi);
        ulangiKataSandi = findViewById(R.id.et_ulangi);
        btn_daftar = findViewById(R.id.btn_daftar);
        masuk = findViewById(R.id.tv_masuk);

        // INIT FIREBASE
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");

        // REGISTER LISTENER
        masuk.setOnClickListener(this);
        btn_daftar.setOnClickListener(this);

    }

    private void daftar(final String namapengguna,
                        final String telepon,
                        final String email,
                        final String katasandi) {

        firebaseAuth.createUserWithEmailAndPassword(email, katasandi)
                .addOnCompleteListener(DaftarActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            String userid = firebaseUser.getUid();

                            databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(userid);

                            User user = new User();
                            user.setId(userid);
                            user.setUsername(namapengguna.toLowerCase());
                            user.setTelepon(telepon);
                            user.setEmail(email);
                            user.setPassword(katasandi);
                            user.setKodeLogin("0");
//                            user.setBio("");
                            user.setFotoProfile("");

                            databaseReference.setValue(user).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    pd.dismiss();
                                    Intent intent = new Intent(DaftarActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            });
                        } else {
                            pd.dismiss();
                            Toast.makeText(DaftarActivity.this, "Anda tidak dapat mendaftar dengan email dan kata sandi ini !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_daftar:
                validator.validate();

                pd = new ProgressDialog(DaftarActivity.this);
                pd.setMessage("Tunggu Sebentar...");
                pd.show();

                String str_namapengguna = namapengguna.getText().toString();
                String str_telepon = telepon.getText().toString();
                String str_email = email.getText().toString();
                String str_katasandi = katasandi.getText().toString();
                daftar(str_namapengguna, str_telepon, str_email, str_katasandi);
                break;

            case R.id.tv_masuk:
                Intent masuk = new Intent(DaftarActivity.this, LoginActivity.class);
                startActivity(masuk);
                break;
        }
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            // Display error messages
            if (view instanceof TextInputEditText) {
                ((TextInputEditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }

    }
}