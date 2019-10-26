package com.example.pelangiaquscape.TestUI;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.pelangiaquscape.R;
import com.example.pelangiaquscape.Utils.NotificationUtils;

import java.util.Date;

public class TestNotif extends AppCompatActivity implements View.OnClickListener {

    Button btn_test;
    Context context;
    String message = "Pemberitahuan stok barang sudah mencapai batas minimum";
    String title = "Stok Barang";

//    public TestNotif(Context context, String message, String title) {
//        this.context = context;
//        this.message = message;
//        this.title = title;
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testui);

        btn_test = findViewById(R.id.btn_test);

        btn_test.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_test:
                notification();
                break;
        }

    }

    public void notification() {
        Intent intent = new Intent(TestNotif.this, NotificationUtils.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("message", message);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        int icon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? R.drawable.logoblack : R.drawable.logoblack;

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "default";
            NotificationChannel channel = new NotificationChannel(channelId, title, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(message);
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.setShowBadge(true);
            notificationManager.createNotificationChannel(channel);
            Notification notification = new Notification.Builder(this, channelId)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(icon)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .build();
            notificationManager.notify(m, notification);
        } else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(icon)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setLights(Color.BLUE, 3000, 3000);
            notificationManager.notify(m, notificationBuilder.build());
        }




//        NotificationCompat.Builder notification = new  NotificationCompat.Builder(this, channelId)
//                .setSmallIcon(R.drawable.logoblack)
//                .setContentTitle("New Notification")
//                .setContentText(message);
//
//
//        Intent intent = new Intent(TestNotif.this, NotificationUtils.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("message", message);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(TestNotif.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        notification.setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager = (NotificationManager)getSystemService(context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0, notification.build());

    }
}
