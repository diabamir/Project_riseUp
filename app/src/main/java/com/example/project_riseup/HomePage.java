//package com.example.project_riseup;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.cardview.widget.CardView;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.lifecycle.ViewModelProvider;
//
//public class HomePage extends AppCompatActivity {
//    ImageButton addWater, homeButton, groupsButton, calendarButton, profileButton;
//    TextView greetingText;
//    UserViewModel userViewModel;
//    ImageButton profilebutton;
//    long userId=1;
//    ImageView profileviewphoto;
//
//    @SuppressLint({"MissingInflatedId", "CutPasteId"})
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home_page);
//
//        // Initialize UI components
//        homeButton = findViewById(R.id.homeImageButton);
//        groupsButton = findViewById(R.id.groupsImageButton);
//        calendarButton = findViewById(R.id.calendarImageButton);
//        profileButton = findViewById(R.id.profileImageButton);
//        addWater = findViewById(R.id.addWater);
//        greetingText = findViewById(R.id.greetingText);
//
//        // Set the home button as selected by default
//        homeButton.setSelected(true);
//
//        // Initialize the ImageView for profile photo (Ensure this is in your XML layout)
//        profileviewphoto = findViewById(R.id.profileImage);
//
//        // Retrieve the user ID from SharedPreferences
//        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
//        userId = sharedPreferences.getLong("USER_ID", -1);
//
//        if (userId != -1) {
//            // Initialize ViewModel and fetch user data
//            userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
//
//            new Thread(() -> {
//                User user = userViewModel.getUserById(userId);
//                runOnUiThread(() -> {
//                    if (user != null) {
//                        greetingText.setText("Hello, " + user.getFirstName() + "!");
//
//                        // Set the appropriate profile photo based on gender
//                        if (user.getGender().equalsIgnoreCase("female")) {
//                            profileviewphoto.setImageResource(R.drawable.womanprofile);
//                        } else if (user.getGender().equalsIgnoreCase("male")) {
//                            profileviewphoto.setImageResource(R.drawable.manprofile);
//                        } else {
//                            profileviewphoto.setImageResource(R.drawable.defaultprofile);
//                        }
//                    } else {
//                        greetingText.setText("User not found");
//                    }
//                });
//            }).start();
//        } else {
//            greetingText.setText("User ID not found in SharedPreferences");
//            // Optionally, log an error or redirect the user to the login page
//        }
//
//        // Set click listeners for CardViews
//        findViewById(R.id.cardMoveDaily).setOnClickListener(v -> startActivity(new Intent(HomePage.this, MainActivity.class)));
//        findViewById(R.id.cardStayHydrated).setOnClickListener(v -> startActivity(new Intent(HomePage.this, MainActivity.class)));
//        findViewById(R.id.cardStayActive).setOnClickListener(v -> startActivity(new Intent(HomePage.this, MainActivity.class)));
//        findViewById(R.id.cardEatBalanced).setOnClickListener(v -> startActivity(new Intent(HomePage.this, MainActivity.class)));
//
//        // Handle addWater button click
//        addWater.setOnClickListener(v -> Toast.makeText(HomePage.this, "Water added!", Toast.LENGTH_SHORT).show());
//
//        // Set click listeners for navigation buttons
//        homeButton.setOnClickListener(this::onHomeClicked);
//        groupsButton.setOnClickListener(this::onGroupsClicked);
//        profileButton.setOnClickListener(this::onProfileClicked);
//        // Navigate to the Profile activity and pass userId
//        profilebutton = findViewById(R.id.profileImageButton);
//        profilebutton.setOnClickListener(v -> {
//            Intent intentProfile = new Intent(HomePage.this, Profile.class);
//            intentProfile.putExtra("USER_ID", userId);  // Pass the user ID to Profile activity
//            startActivity(intentProfile);
//        });
//
//
//        // Initialize the ConstraintLayout (hydration card)
//        CardView hydrationCard = findViewById(R.id.cardStayHydrated);
//        hydrationCard.setOnClickListener(view -> {
////            Toast.makeText(HomePage.this, "Hydration card clicked", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(HomePage.this, HydrationActivity.class);
//            intent.putExtra("USER_ID", userId);
//            startActivity(intent);
//        });
//
//
//        // Set onClickListener for the entire card
////        hydrationCard.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                // Navigate to HydrationActivity
////                Intent intent = new Intent(HomePage.this, HydrationActivity.class);
////                intent.putExtra("USER_ID", userId);  // Pass the user ID to the hydration
////                startActivity(intent);
////            }
////        });
//    }
//
//
//
//    // Methods to handle button clicks
//    public void onHomeClicked(View view) {
//        // No need to start the HomeActivity again, just update button state
//        updateButtonStates(homeButton);
//    }
//
//    public void onGroupsClicked(View view) {
//        updateButtonStates(groupsButton);
//
//        Intent intent = new Intent(this, MapActivity.class);
//        intent.putExtra("USER_ID", userId);  // Pass the user ID to Profile activity
//        startActivity(intent);
//    }
//
//    public void onProfileClicked(View view) {
//        updateButtonStates(profileButton);
//        Intent intent = new Intent(this, Profile.class);
//        intent.putExtra("USER_ID", userId);  // Pass the user ID to Profile activity
//        startActivity(intent);
//    }
//
//    // Method to update the selected state of the buttons
//    private void updateButtonStates(ImageButton selectedButton) {
//        // Deselect all buttons
//        homeButton.setSelected(false);
//        groupsButton.setSelected(false);
//        calendarButton.setSelected(false);
//        profileButton.setSelected(false);
//
//        // Set the selected button to true
//        selectedButton.setSelected(true);
//    }
//}

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
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.Date;

public class HomePage extends AppCompatActivity {
    ImageButton addWater, homeButton, groupsButton, calendarButton, profileButton;
    TextView greetingText, reminderText;
    TextView moveDailyValue;
    UserViewModel userViewModel;
    Button profilebutton;
    ImageView profileviewphoto;
    long userId;
    StepsHelper stepsHelper; // Declare a StepsHelper instance
    private float underCupAmount = 100; // Amount added per button press

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
        moveDailyValue = findViewById(R.id.moveDailyValue); // This will show the steps
        reminderText = findViewById(R.id.reminderText); // Add the reminderText TextView
        profileviewphoto = findViewById(R.id.profileImage);
        moveDailyValue = findViewById(R.id.moveDailyValue);


        // Initialize StepsHelper
        stepsHelper = new StepsHelper(this); // Initialize StepsHelper

        // Fetch today's steps and update the moveDailyValue TextView
        stepsHelper.fetchTodaySteps(moveDailyValue); // Fetch today's steps

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

        // Fetch steps for today and update the TextView
        fetchStepsForToday();

        // Set click listeners for CardViews and buttons
        findViewById(R.id.cardMoveDaily).setOnClickListener(v -> startActivity(new Intent(HomePage.this, StepMain.class)));
//        findViewById(R.id.cardStayHydrated).setOnClickListener(v -> startActivity(new Intent(HomePage.this, MainActivity.class)));
        findViewById(R.id.cardStayActive).setOnClickListener(v -> startActivity(new Intent(HomePage.this, ExerciseActivity.class))); // Navigate to ExerciseActivity
        findViewById(R.id.cardEatBalanced).setOnClickListener(v -> startActivity(new Intent(HomePage.this, mealActivity.class)));

        addWater.setOnClickListener(v -> Toast.makeText(HomePage.this, "Water added!", Toast.LENGTH_SHORT).show());

        homeButton.setOnClickListener(this::onHomeClicked);
        groupsButton.setOnClickListener(this::onGroupsClicked);
        profileButton.setOnClickListener(this::onProfileClicked);
        calendarButton.setOnClickListener(v -> startActivity(new Intent(HomePage.this, CalendarActivity.class)));
        if (userId != -1) {
            // Initialize ViewModel and fetch user data
            userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
            fetchUserData();
        } else {
            greetingText.setText("User ID not found in SharedPreferences");
        }

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

        // Handle addWater button click
        addWater.setOnClickListener(v -> {
            if (userId != -1) {
                addWater();
            } else {
                Toast.makeText(HomePage.this, "User ID is invalid.", Toast.LENGTH_SHORT).show();
            }
        });

        // Set click listeners for navigation buttons
        homeButton.setOnClickListener(this::onHomeClicked);
        groupsButton.setOnClickListener(this::onGroupsClicked);
        //calendarButton.setOnClickListener(this::onCalendarClicked);
        profileButton.setOnClickListener(this::onProfileClicked);

        // Start updating the quotes every 30 seconds
        startQuoteRotation();


        // Initialize the ConstraintLayout (hydration card)
        CardView hydrationCard = findViewById(R.id.cardStayHydrated);
        hydrationCard.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, HydrationActivity.class);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });

        findViewById(R.id.cardMoveDaily).setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, StepMain.class);
            startActivity(intent);
        });
    }

    private void fetchUserData() {
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
    }

    private void addWater() {
        new Thread(() -> {
            User user = userViewModel.getUserById(userId);
            if (user != null) {
                float newHydrationAmount = user.getHydrationAmount() + underCupAmount;
                user.setHydrationAmount(newHydrationAmount);

                userViewModel.updateUser(user);
                runOnUiThread(() -> {
                    Toast.makeText(HomePage.this, "Water added!", Toast.LENGTH_SHORT).show();
                    updateSharedPreferences(newHydrationAmount);
                });
            } else {
                runOnUiThread(() -> Toast.makeText(HomePage.this, "User not found.", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void updateSharedPreferences(float hydrationAmount) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("HYDRATION_AMOUNT", hydrationAmount);
        editor.apply();
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

    private void fetchStepsForToday() {
        StepsDatabase database = StepsDatabase.getInstance(this);
        Date today = getTodayDate(); // Get today's date (at midnight)

        new Thread(() -> {
            // Fetch today's steps from the database
            Steps todaySteps = database.stepsDao().findStepsByDate(today);

            runOnUiThread(() -> {
                if (todaySteps != null) {
                    moveDailyValue.setText(todaySteps.getStepsTaken() + " steps");
                } else {
                    moveDailyValue.setText("0 steps");
                }
            });
        }).start();
    }

    private Date getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();  // Return today's date at midnight
    }

    public void onHomeClicked(View view) {
        // No need to start the HomeActivity again, just update button state
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

