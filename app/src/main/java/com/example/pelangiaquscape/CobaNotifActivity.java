package com.example.pelangiaquscape;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class CobaNotifActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coba_notif);

        Common.currentToken = FirebaseInstanceId.getInstance().getToken();

        Log.i("MY TOKEN", Common.currentToken);
    }
}
