package com.example.project_riseup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class HomePage extends AppCompatActivity {

    // Declare the ImageButton variable here
    ImageButton addWater;
    TextView greetingText;
    UserViewModel userViewModel;
    Button profilebutton;
    long userId;  // Store the passed user ID here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Initialize the ImageButton after setContentView
        addWater = findViewById(R.id.addWater);
        greetingText = findViewById(R.id.greetingText);

        // Get the user ID passed from Signup or other activities
        Intent intent = getIntent();
        userId = intent.getLongExtra("USER_ID", -1);  // Default to -1 if not passed

        if (userId != -1) {
            // Initialize the ViewModel
            userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

            // Run the database fetch on a background thread
            new Thread(() -> {
                // Fetch the user from the Room database
                User user = userViewModel.getUserById(userId);

                // Update the UI on the main thread
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (user != null) {
                        // Get the first name and set it to the TextView
                        String firstName = user.getFirstName();
                        greetingText.setText("Hello, " + firstName + "!");
                    } else {
                        greetingText.setText("User not found");
                    }
                });
            }).start();
        } else {
            greetingText.setText("User ID not passed or found.");
        }

        // Set click listeners for each CardView
        findViewById(R.id.cardMoveDaily).setOnClickListener(v -> startActivity(new Intent(HomePage.this, MainActivity.class)));
        findViewById(R.id.cardStayHydrated).setOnClickListener(v -> startActivity(new Intent(HomePage.this, MainActivity.class)));
        findViewById(R.id.cardStayActive).setOnClickListener(v -> startActivity(new Intent(HomePage.this, MainActivity.class)));
        findViewById(R.id.cardEatBalanced).setOnClickListener(v -> startActivity(new Intent(HomePage.this, MainActivity.class)));

        // Set an OnClickListener for the addWater button
        addWater.setOnClickListener(v -> Toast.makeText(HomePage.this, "Water added!", Toast.LENGTH_SHORT).show());

        // Set an OnClickListener for the profilebutton and pass userId to the Profile activity
        profilebutton = findViewById(R.id.profile1);
        profilebutton.setOnClickListener(v -> {
            Intent intentProfile = new Intent(HomePage.this, Profile.class);
            intentProfile.putExtra("USER_ID", userId);  // Pass the user ID to the Profile activity
            startActivity(intentProfile);
        });
    }
}
