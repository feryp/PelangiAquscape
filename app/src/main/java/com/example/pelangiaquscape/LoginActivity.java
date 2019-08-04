package com.example.pelangiaquscape;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private static int RC_SIGN_IN = 1;
    private static String TAG = "LoginActivity";

    ImageView bgapp, clover;
    LinearLayout logosplash, container_user, container_pwd, container_daftar, container_lupa_password;
    Animation frombottom;
    Button btn_masuk, btn_google;
    TextView daftar, lupa_password;
    TextInputEditText namapengguna, katasandi;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);

        bgapp = (ImageView) findViewById(R.id.bgapp);
        clover = (ImageView) findViewById(R.id.clover);
        logosplash = (LinearLayout) findViewById(R.id.logosplash);
        container_user = (LinearLayout) findViewById(R.id.container_user);
        container_pwd = (LinearLayout) findViewById(R.id.container_pwd);
        container_lupa_password = (LinearLayout) findViewById(R.id.container_lupa_password);
        btn_google = (Button) findViewById(R.id.btn_google);
        container_daftar = (LinearLayout) findViewById(R.id.container_daftar);
        btn_masuk = (Button) findViewById(R.id.btn_masuk);

        namapengguna = findViewById(R.id.et_nama_pengguna);
        katasandi = findViewById(R.id.et_kata_sandi);

        daftar = (TextView) findViewById(R.id.tv_daftar);
        lupa_password = (TextView) findViewById(R.id.tv_lupa_password);


        bgapp.animate().translationY(-1500).setDuration(1000).setStartDelay(1500);
        clover.animate().alpha(0).setDuration(1000).setStartDelay(600);
        logosplash.animate().translationY(-330).setDuration(1500).setStartDelay(1000);

        container_user.startAnimation(frombottom);
        container_pwd.startAnimation(frombottom);
        btn_masuk.startAnimation(frombottom);
        btn_google.startAnimation(frombottom);
        container_daftar.startAnimation(frombottom);
        container_lupa_password.startAnimation(frombottom);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent daftar = new Intent(LoginActivity.this, DaftarActivity.class);
                startActivity(daftar);
            }
        });

        lupa_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lupa_password = new Intent(LoginActivity.this, LupaPasswordActivity.class);
                startActivity(lupa_password);
            }
        });

        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                pd.setMessage("Tunggu Sebentar ...");
                pd.show();

                String str_namapengguna = namapengguna.getText().toString();
                String str_katasandi = katasandi.getText().toString();

                if (TextUtils.isEmpty(str_namapengguna) || TextUtils.isEmpty(str_katasandi)) {
                    Toast.makeText(LoginActivity.this, "Semua harus diisi!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.v("HERE0", "VALUE EVENT LISTENER");
                    firebaseAuth.signInWithEmailAndPassword(str_namapengguna, str_katasandi)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Log.v("HERE1", "VALUE EVENT LISTENER");


                                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("User")
                                                .child(firebaseAuth.getCurrentUser().getUid()).child("kode_login");


                                        reference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                pd.dismiss();
                                                Log.v("HERE2", "VALUE EVENT LISTENER");
                                                int as = Integer.parseInt(dataSnapshot.getValue().toString());
                                                if (as == 1) {
                                                    Intent i = new Intent(LoginActivity.this, Main2Activity.class);
                                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(i);
                                                    finish();
                                                } else if (as == 0) {
                                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                pd.dismiss();

                                            }
                                        });
                                    } else {
                                        pd.dismiss();
                                        Toast.makeText(LoginActivity.this, "Otentikasi gagal!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }

        });

        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {

            final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
            pd.setMessage("Tunggu Sebentar ...");
            pd.show();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("User")
                    .child(currentUser.getUid()).child("kode_login");


            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    pd.dismiss();
                    Log.v("HERE2", "VALUE EVENT LISTENER");
                    int as = Integer.parseInt(dataSnapshot.getValue().toString());
                    if (as == 1) {
                        Intent i = new Intent(LoginActivity.this, Main2Activity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    } else if (as == 0) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    pd.dismiss();

                }
            });
        }
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            handleSignResult(task);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }

    }

//    private void handleSignResult(Task<GoogleSignInAccount> task) {
//        try {
//            GoogleSignInAccount account = task.getResult(ApiException.class);
//
//            // Signed in successfully, show authenticated UI.
//            firebaseAuthWithGoogle(account);
//        } catch (ApiException e) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
////            firebaseAuthWithGoogle(null);
//        }
//
//    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = firebaseAuth.getCurrentUser();
//                            String email = firebaseUser.getEmail();
//                            String userid = firebaseUser.getUid();
//
//                            HashMap<Object, String> hashMap = new HashMap<>();
//                            hashMap.put("email", email);
//                            hashMap.put("id", userid);
//                            hashMap.put("namapengguna", "");
//                            hashMap.put("status_jabatan","");
//                            hashMap.put("fotoProfile", "");
//
//                            FirebaseDatabase database = FirebaseDatabase.getInstance();
//
//                            DatabaseReference databaseReference = database.getReference("User");
//
//                            databaseReference.child(userid).setValue(hashMap);
//
//                            //show user email in toast
//                            Toast.makeText(LoginActivity.this, ""+firebaseUser.getEmail(), Toast.LENGTH_SHORT);
//                            startActivity(new Intent(LoginActivity.this, Main2Activity.class));
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Login Gagal...", Toast.LENGTH_SHORT);
//                            Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
