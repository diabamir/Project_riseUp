package com.example.project_riseup;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class NotificationService extends Service {

    private static final String CHANNEL_ID = "notification_channel";
    private NotificationManager notificationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null; // No binding, as this is a started service.
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Create a notification channel for Android O and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Notification Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        // Schedule two notifications
        new Handler().postDelayed(this::sendFirstNotification, 65000);
        new Handler().postDelayed(this::sendSecondNotification, 70000);

        return START_STICKY;
    }

    private void sendFirstNotification() {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Time to Stretch    \uD83D\uDEB6")
                .setContentText("Get up and take a short walk. Your body will thank you")
                .setSmallIcon(R.mipmap.ic_launcher) // Use your app icon
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        notificationManager.notify(1, notification); // Notification ID is 1
    }

    private void sendSecondNotification() {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Hydration Break  \uD83D\uDCA7")
                .setContentText("Grab a glass of water and stay refreshed")
                .setSmallIcon(R.mipmap.ic_launcher) // Use your app icon
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        notificationManager.notify(2, notification); // Notification ID is 2
    }
}
