package com.example.project_riseup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.Date;

public class HomePage extends AppCompatActivity {
    ImageButton addWater, homeButton, groupsButton, calendarButton, profileButton;
    TextView greetingText, moveDailyValue;
    UserViewModel userViewModel;
    ImageView profileviewphoto;
    long userId;
    StepsHelper stepsHelper; // Declare a StepsHelper instance

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
        moveDailyValue = findViewById(R.id.moveDailyValue); // This will show the steps
        profileviewphoto = findViewById(R.id.profileImage);

        // Initialize StepsHelper
        stepsHelper = new StepsHelper(this); // Initialize StepsHelper

        // Fetch today's steps and update the moveDailyValue TextView
        stepsHelper.fetchTodaySteps(moveDailyValue); // Fetch today's steps

        // Set the home button as selected by default
        homeButton.setSelected(true);

        // Retrieve the user ID from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        userId = sharedPreferences.getLong("USER_ID", -1);

        if (userId != -1) {
            userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
            new Thread(() -> {
                User user = userViewModel.getUserById(userId);
                runOnUiThread(() -> {
                    if (user != null) {
                        greetingText.setText("Hello, " + user.getFirstName() + "!");
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

        // Set click listeners for CardViews and buttons
        findViewById(R.id.cardMoveDaily).setOnClickListener(v -> startActivity(new Intent(HomePage.this, StepMain.class)));
        findViewById(R.id.cardStayHydrated).setOnClickListener(v -> startActivity(new Intent(HomePage.this, MainActivity.class)));
        findViewById(R.id.cardStayActive).setOnClickListener(v -> startActivity(new Intent(HomePage.this, ExerciseActivity.class))); // Navigate to ExerciseActivity
        findViewById(R.id.cardEatBalanced).setOnClickListener(v -> startActivity(new Intent(HomePage.this, mealActivity.class)));

        addWater.setOnClickListener(v -> Toast.makeText(HomePage.this, "Water added!", Toast.LENGTH_SHORT).show());

        homeButton.setOnClickListener(this::onHomeClicked);
        groupsButton.setOnClickListener(this::onGroupsClicked);
        profileButton.setOnClickListener(this::onProfileClicked);
        calendarButton.setOnClickListener(v -> startActivity(new Intent(HomePage.this, CalendarActivity.class)));
    }

    public void onHomeClicked(View view) {
        updateButtonStates(homeButton);
    }

    public void onGroupsClicked(View view) {
        updateButtonStates(groupsButton);
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("USER_ID", userId);
        startActivity(intent);
    }

    public void onProfileClicked(View view) {
        updateButtonStates(profileButton);
        Intent intent = new Intent(this, Profile.class);
        intent.putExtra("USER_ID", userId);
        startActivity(intent);
    }

    private void updateButtonStates(ImageButton selectedButton) {
        homeButton.setSelected(false);
        groupsButton.setSelected(false);
        calendarButton.setSelected(false);
        profileButton.setSelected(false);
        selectedButton.setSelected(true);
    }
}
