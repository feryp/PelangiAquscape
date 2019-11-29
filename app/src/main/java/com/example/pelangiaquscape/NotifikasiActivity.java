package com.example.pelangiaquscape;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.pelangiaquscape.Notification.NotificationHelper.CHANNEL_ID;

public class NotifikasiActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    final String EXTRA = "INTENT_EDIT_TO_MAIN";
    ImageView cancel;
    SwitchCompat notifikasi;
    NotificationManager notificationManager;
    FirebaseDatabase db;
    DatabaseReference dr;


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

        notifikasi.setOnCheckedChangeListener(this);


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            Toast.makeText(NotifikasiActivity.this,"Notifikasi Nyala", Toast.LENGTH_SHORT).show();
            notifikasi.setChecked(true);
        } else {
            Toast.makeText(NotifikasiActivity.this,"Notifikasi Mati", Toast.LENGTH_SHORT).show();
            notifikasi.setChecked(false);
        }

    }


//    void showNotification(){
//        int icon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? R.drawable.logoblack : R.drawable.logoblack;
//
//        String message = "Stok minimal "+ barang.getMinStok() + " ,Stok saat ini "+barang.getStok();
//        String title = "Barang " + barang.getKode() + " telah mencapai stok minimal";
//
//        Intent intent = new Intent(this, DetailPemberitahuanActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        intent.putExtra("message", message);
//        intent.putExtra("title", title);
//        intent.putExtra("tanggal",date);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setSmallIcon(icon)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            Notification notification = new Notification.Builder(this, CHANNEL_ID)
//                    .setContentTitle(title)
//                    .setContentText(message)
//                    .setSmallIcon(icon)
//                    .setAutoCancel(true)
//                    .setContentIntent(pendingIntent)
//                    .build();
//
//            notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//
//            // notificationId is a unique int for each notification that you must define
//            notificationManager.notify(1221, notification);
//
//        }
//
//        // notificationId is a unique int for each notification that you must define
//        notificationManager.notify(1221, builder.build());
//    }

}
