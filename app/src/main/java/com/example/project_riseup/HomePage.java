package com.example.project_riseup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.Date;

public class HomePage extends AppCompatActivity {

    private ImageButton addWater;
    private TextView greetingText;
    private Button profileButton;
    private long userId;
    private UserViewModel userViewModel;
    private StepsDatabase stepsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Initialize UI elements
        addWater = findViewById(R.id.addWater);
        greetingText = findViewById(R.id.greetingText);
        profileButton = findViewById(R.id.profile1);

        // Retrieve the user ID from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        userId = sharedPreferences.getLong("USER_ID", -1);
        Log.d("HomePage", "User ID retrieved: " + userId);

        if (userId == -1) {
            Log.e("HomePage", "Invalid User ID! Redirecting to Signup or Login page.");
            greetingText.setText("User ID not found in SharedPreferences");
            return;
        }

        // Initialize the ViewModel and database
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        stepsDatabase = StepsDatabase.getInstance(this);

        loadUserAndSteps();  // Initialize user and steps

        profileButton.setOnClickListener(v -> {
            Intent intentProfile = new Intent(HomePage.this, Profile.class);
            intentProfile.putExtra("USER_ID", userId);  // Pass the user ID to Profile activity
            startActivity(intentProfile);
        });

        findViewById(R.id.cardMoveDaily).setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, StepsMain.class);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });

        addWater.setOnClickListener(v -> Toast.makeText(HomePage.this, "Water added!", Toast.LENGTH_SHORT).show());
    }

    private void loadUserAndSteps() {
        new Thread(() -> {
            try {
                User user = userViewModel.getUserById(userId);
                if (user != null) {
                    runOnUiThread(() -> greetingText.setText("Hello, " + user.getFirstName() + "!"));
                    initializeOrUpdateStepsForToday();  // Initialize or update steps for today
                } else {
                    runOnUiThread(() -> greetingText.setText("User not found"));
                }
            } catch (Exception e) {
                Log.e("HomePage", "Error loading user or initializing steps: " + e.getMessage());
                runOnUiThread(() -> Toast.makeText(HomePage.this, "Error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void initializeOrUpdateStepsForToday() {
        new Thread(() -> {
            try {
                Date today = getTodayDate();
                Steps existingSteps = stepsDatabase.stepsDao().findStepsByDate(today, userId);
                if (existingSteps == null) {
                    // Insert new steps data for today
                    Steps newSteps = new Steps(today, 0, 0, userId);
                    stepsDatabase.stepsDao().insertOrUpdateStep(newSteps);
                    runOnUiThread(() -> Toast.makeText(HomePage.this, "Steps for today initialized!", Toast.LENGTH_SHORT).show());
                } else {
                    runOnUiThread(() -> Toast.makeText(HomePage.this, "Continuing from " + existingSteps.getStepsTaken() + " steps", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                Log.e("HomePage", "Error in steps initialization: " + e.getMessage());
                runOnUiThread(() -> Toast.makeText(HomePage.this, "Error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private Date getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}

