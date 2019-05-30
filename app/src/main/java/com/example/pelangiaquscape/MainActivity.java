package com.example.pelangiaquscape;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    ImageView bgapp, clover;
    LinearLayout logosplash, containeruser, containerpwd;
    Animation frombottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);

        bgapp = (ImageView) findViewById(R.id.bgapp);
        clover = (ImageView) findViewById(R.id.clover);
        logosplash = (LinearLayout) findViewById(R.id.logosplash);
        containeruser = (LinearLayout) findViewById(R.id.containeruser);
        containerpwd = (LinearLayout) findViewById(R.id.containerpwd);

        bgapp.animate().translationY(-1500).setDuration(1000).setStartDelay(1500);
        clover.animate().alpha(0).setDuration(1000).setStartDelay(600);
        logosplash.animate().translationY(-260).setDuration(1500).setStartDelay(1000);

        containeruser.startAnimation(frombottom);
        containerpwd.startAnimation(frombottom);
    }
}
