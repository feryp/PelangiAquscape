package com.example.pelangiaquscape;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pelangiaquscape.Model.Notifikasi;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.pelangiaquscape.Notification.NotificationHelper.CHANNEL_ID;

public class NotifikasiActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    public static final String mypreferences = "mypref";
    final String EXTRA = "INTENT_EDIT_TO_MAIN";
    ImageView cancel;
    SwitchCompat notifikasi;
    NotificationManager notificationManager;
    FirebaseDatabase db;
    DatabaseReference dr;
    SharedPreferences sharedPreferences;
    String PACKAGE_NAME = "com.example.pelangiaquascape.";
    private boolean fromNotifikasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);

        cancel = findViewById(R.id.im_cancel);
        notifikasi = findViewById(R.id.switch_notifikasi);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (notifikasi != null){
            notifikasi.setOnCheckedChangeListener(this);
        }



    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(NotifikasiActivity.this, "Notifikasi switch " + (isChecked ? "on" : "off"),
                Toast.LENGTH_SHORT).show();
        if (isChecked) {
            Toast.makeText(NotifikasiActivity.this, "Notifikasi Nyala", Toast.LENGTH_SHORT).show();
            notifikasi.setChecked(true);
            saveNotifSetting(true);
        } else {
            Toast.makeText(NotifikasiActivity.this, "Notifikasi Mati", Toast.LENGTH_SHORT).show();
            notifikasi.setChecked(false);
            saveNotifSetting(false);
        }

    }

    public void saveNotifSetting(Boolean notifikasi) {

        sharedPreferences = getApplicationContext().getSharedPreferences(PACKAGE_NAME + "SETTING_NOTIF", Context.MODE_PRIVATE);

        sharedPreferences.getBoolean("notif", false);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("notif", notifikasi);
            editor.apply();
//            Intent intent = new Intent(NotifikasiActivity.this, PembayaranActivity.class);
//            startActivity(intent);
        }


    }


