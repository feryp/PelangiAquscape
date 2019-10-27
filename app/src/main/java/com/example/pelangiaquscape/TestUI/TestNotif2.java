package com.example.pelangiaquscape.TestUI;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.pelangiaquscape.Fragment.PemberitahuanFragment;
import com.example.pelangiaquscape.R;

public class TestNotif2 extends AppCompatActivity implements View.OnClickListener{

    Button btnTest;

    final String CHANNEL_ID = "theId";

    NotificationManager notificationManager;
    NotificationManager mNotificationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testui);

        btnTest = findViewById(R.id.btn_test);
        btnTest.setOnClickListener(this);

        createNotificationChannel();
//        createNotificationChannel();



    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "The Channel Name";
            String description = "The Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Pelangi Aquascape : test title", importance);
            channel.setDescription("Test Message");
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.setShowBadge(true);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    void showNotification(){

        int icon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? R.drawable.logoblack : R.drawable.logoblack;

        String message = "Test Message";
        String title = "Pelangi Aquascape : test title";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);



        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Notification notification = new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(icon)
                    .setAutoCancel(true)
                    .build();


            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(1221, notification);



        }

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1221, builder.build());
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_test:

                showNotification();

                break;
        }
    }

    void createNotif(){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, "notify_001");
//        Intent ii = new Intent(mContext.getApplicationContext(), RootActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
//        bigText.bigText(verseurl);
        bigText.setBigContentTitle("Today's Bible Verse");
        bigText.setSummaryText("Text in detail");

//        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("Your Title");
        mBuilder.setContentText("Your text");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

// === Removed some obsoletes

            String channelId = "Your_channel_id";
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    android.app.NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }



        mNotificationManager.notify(0, mBuilder.build());
    }
}
