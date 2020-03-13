package com.example.bolasepak.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.bolasepak.R;

public class NotificationService extends Service {

    private static final String TAG = "NotificationService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();

        String message = "message";
        String channel = "Channel 1";
        String title = "Title";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        NotificationManager manager = getSystemService(NotificationManager.class);

        NotificationChannel channel1 = new NotificationChannel(channel, "Channel 1", importance);
        channel1.setDescription("This is Channel 1");

        manager.createNotificationChannel(channel1);

        Notification notification = new NotificationCompat.Builder(this, channel)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }
}
