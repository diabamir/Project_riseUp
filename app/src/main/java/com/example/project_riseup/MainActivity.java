package com.example.project_riseup;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button b;
    private UserRepository userRepository;
    private Handler handlerHydrationNotification;
    private Handler handlerStepsNotification;
    private Runnable runnableHydrationNotification;
    private Runnable runnableStepsNotification;
    private static final String CHANNEL_ID = "default_channel"; // Notification channel ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Set padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Button to navigate to another activity
        b = findViewById(R.id.button2);
        b.setOnClickListener(v -> {
            Intent intent = new Intent(this, firstPageIcon.class);
            startActivity(intent);
        });
        userRepository = new UserRepository(this);

        // Create notification channel (for Android 8.0 and above)
        createNotificationChannel();

        // Set up handler for 5-seconds hydration notifications
        handlerHydrationNotification = new Handler();
        runnableHydrationNotification = new Runnable() {
            @Override
            public void run() {
                showNotification("Drink to thrive \uD83D\uDEB0", "Time to drink water!", 1); // Notification ID 1
                handlerHydrationNotification.postDelayed(this, 60000); // Repeat every 5 seconds
            }
        };

        // Set up handler for 6-seconds steps notifications
        handlerStepsNotification = new Handler();
        runnableStepsNotification = new Runnable() {
            @Override
            public void run() {
                showNotification("Time to stand \uD83D\uDEB6\uD83C\uDFFC\u200D", "Time to move and take steps", 2); // Notification ID 2
                handlerStepsNotification.postDelayed(this, 65000); // Repeat every 6 seconds
            }
        };

        // Start the notification handlers
        handlerHydrationNotification.post(runnableHydrationNotification); // Start hydration notification
        handlerStepsNotification.post(runnableStepsNotification); // Start steps notification
    }

    // Method to show the notification
    private void showNotification(String title, String text, int notificationId) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logot_no_white_bg) // Replace with your notification icon
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH) // Ensure it shows on lock and home screen
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, builder.build()); // Use different IDs for different notifications
    }

    // Create notification channel (required for Android 8.0 and above)
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Default Channel";
            String description = "Channel for default notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop the handlers when the activity is destroyed
        handlerHydrationNotification.removeCallbacks(runnableHydrationNotification);
        handlerStepsNotification.removeCallbacks(runnableStepsNotification);
    }
}
