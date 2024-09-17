package com.example.project_riseup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class HomePage extends AppCompatActivity {
    ImageButton addWater, homeButton, groupsButton, calendarButton, profileButton;
    TextView greetingText, reminderText;
    UserViewModel userViewModel;
    Button profilebutton;
    long userId = 1;
    ImageView profileviewphoto;
    Handler handler = new Handler();
    int currentQuoteIndex = 0;
    String[] quotes = {
            "Rise Up! Every step fuels your strength. Hydrate, focus, conquer!",
            "Stay hydrated, stay healthy!",
            "Keep moving and stay active.",
            "Consistency builds strength!",
            "Take a break, breathe, and refresh."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Initialize UI components
        homeButton = findViewById(R.id.homeImageButton);
        groupsButton = findViewById(R.id.groupsImageButton);
        calendarButton = findViewById(R.id.calendarImageButton);
        profileButton = findViewById(R.id.profileImageButton);
        addWater = findViewById(R.id.addWater);
        greetingText = findViewById(R.id.greetingText);
        reminderText = findViewById(R.id.reminderText); // Add the reminderText TextView

        // Set the home button as selected by default
        homeButton.setSelected(true);

        // Initialize the ImageView for profile photo
        profileviewphoto = findViewById(R.id.profileImage);

        // Retrieve the user ID from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        userId = sharedPreferences.getLong("USER_ID", -1);

        if (userId != -1) {
            // Initialize ViewModel and fetch user data
            userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

            new Thread(() -> {
                User user = userViewModel.getUserById(userId);
                runOnUiThread(() -> {
                    if (user != null) {
                        greetingText.setText("Hello, " + user.getFirstName() + "!");

                        // Set the appropriate profile photo based on gender
                        if (user.getGender().equalsIgnoreCase("female")) {
                            profileviewphoto.setImageResource(R.drawable.womanprofile);
                        } else if (user.getGender().equalsIgnoreCase("male")) {
                            profileviewphoto.setImageResource(R.drawable.manprofile);
                        } else {
                            profileviewphoto.setImageResource(R.drawable.defaultprofile);
                        }
                    } else {
                        greetingText.setText("User not found");
                    }
                });
            }).start();
        } else {
            greetingText.setText("User ID not found in SharedPreferences");
        }

        // Set click listeners for CardViews
        findViewById(R.id.cardMoveDaily).setOnClickListener(v -> startActivity(new Intent(HomePage.this, MainActivity.class)));
        findViewById(R.id.cardStayHydrated).setOnClickListener(v -> startActivity(new Intent(HomePage.this, MainActivity.class)));
        findViewById(R.id.cardStayActive).setOnClickListener(v -> startActivity(new Intent(HomePage.this, MainActivity.class)));
        findViewById(R.id.cardEatBalanced).setOnClickListener(v -> startActivity(new Intent(HomePage.this, MainActivity.class)));

        // Handle addWater button click
        addWater.setOnClickListener(v -> Toast.makeText(HomePage.this, "Water added!", Toast.LENGTH_SHORT).show());

        // Set click listeners for navigation buttons
        homeButton.setOnClickListener(this::onHomeClicked);
        groupsButton.setOnClickListener(this::onGroupsClicked);
        profileButton.setOnClickListener(this::onProfileClicked);

        // Start updating the quotes every 30 seconds
        startQuoteRotation();
    }

    private void startQuoteRotation() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Update the TextView with the next quote
                reminderText.setText(quotes[currentQuoteIndex]);

                // Move to the next quote in the list
                currentQuoteIndex++;
                if (currentQuoteIndex == quotes.length) {
                    currentQuoteIndex = 0; // Reset to the first quote if we reach the end of the list
                }

                // Repeat this task every 30 seconds (30000 milliseconds)
                handler.postDelayed(this, 30000);
            }
        }, 0); // Start immediately
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null); // Stop the handler when the activity is destroyed
    }

    // Methods to handle button clicks
    public void onHomeClicked(View view) {
        updateButtonStates(homeButton);
    }

    public void onGroupsClicked(View view) {
        updateButtonStates(groupsButton);
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("USER_ID", userId);  // Pass the user ID to Profile activity
        startActivity(intent);
    }

    public void onProfileClicked(View view) {
        updateButtonStates(profileButton);
        Intent intent = new Intent(this, Profile.class);
        intent.putExtra("USER_ID", userId);  // Pass the user ID to Profile activity
        startActivity(intent);
    }

    // Method to update the selected state of the buttons
    private void updateButtonStates(ImageButton selectedButton) {
        // Deselect all buttons
        homeButton.setSelected(false);
        groupsButton.setSelected(false);
        calendarButton.setSelected(false);
        profileButton.setSelected(false);

        // Set the selected button to true
        selectedButton.setSelected(true);
    }
}
