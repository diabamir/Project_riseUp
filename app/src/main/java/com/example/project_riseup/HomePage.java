package com.example.project_riseup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class HomePage extends AppCompatActivity {

    private ImageButton addWater;
    private TextView greetingText;
    private Button profileButton;
    private long userId;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        addWater = findViewById(R.id.addWater);
        greetingText = findViewById(R.id.greetingText);
        profileButton = findViewById(R.id.profile1);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        userId = sharedPreferences.getLong("USER_ID", -1);

        if (userId == -1) {
            greetingText.setText("User ID not found in SharedPreferences");
            return;
        }

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        loadUserAndSteps();

        profileButton.setOnClickListener(v -> {
            Intent intentProfile = new Intent(HomePage.this, Profile.class);
            intentProfile.putExtra("USER_ID", userId);
            startActivity(intentProfile);
        });

        findViewById(R.id.cardMoveDaily).setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, StepMain.class);
            startActivity(intent);
        });

        addWater.setOnClickListener(v -> Toast.makeText(HomePage.this, "Water added!", Toast.LENGTH_SHORT).show());
    }

    private void loadUserAndSteps() {
        // Load steps from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("StepCounterPrefs", MODE_PRIVATE);
        int stepsTaken = sharedPreferences.getInt("stepsTaken", 0);


        // Load user data asynchronously
        new Thread(() -> {
            User user = userViewModel.getUserById(userId);
            runOnUiThread(() -> {
                if (user != null) {
                    greetingText.setText("Hello, " + user.getFirstName() + "!");
                } else {
                    greetingText.setText("User not found");
                }
            });
        }).start();
    }

}
