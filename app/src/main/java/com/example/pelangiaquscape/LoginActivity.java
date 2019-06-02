package com.example.pelangiaquscape;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    ImageView bgapp, clover;
    LinearLayout logosplash, container_user, container_pwd, container_google, container_daftar;
    Animation frombottom;
    Button btn_masuk;
    TextView daftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);

        bgapp = (ImageView) findViewById(R.id.bgapp);
        clover = (ImageView) findViewById(R.id.clover);
        logosplash = (LinearLayout) findViewById(R.id.logosplash);
        container_user = (LinearLayout) findViewById(R.id.container_user);
        container_pwd = (LinearLayout) findViewById(R.id.container_pwd);
        container_google = (LinearLayout) findViewById(R.id.container_google);
        container_daftar = (LinearLayout) findViewById(R.id.container_daftar);
        btn_masuk = (Button) findViewById(R.id.btn_masuk);

        daftar =(TextView) findViewById(R.id.tv_daftar);

        bgapp.animate().translationY(-1500).setDuration(1000).setStartDelay(1500);
        clover.animate().alpha(0).setDuration(1000).setStartDelay(600);
        logosplash.animate().translationY(-260).setDuration(1500).setStartDelay(1000);

        container_user.startAnimation(frombottom);
        container_pwd.startAnimation(frombottom);
        btn_masuk.startAnimation(frombottom);
        container_google.startAnimation(frombottom);
        container_daftar.startAnimation(frombottom);

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent daftar = new Intent(LoginActivity.this,DaftarActivity.class);
                startActivity(daftar);
            }
        });

        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btn_masuk = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(btn_masuk);
            }
        });

    }
}
