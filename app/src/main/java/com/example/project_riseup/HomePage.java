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

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

public class HomePage extends AppCompatActivity {
    ImageButton addWater, homeButton, groupsButton, calendarButton, profileButton;
    TextView greetingText;
    UserViewModel userViewModel;
    ImageButton profilebutton;
    long userId;
    ImageView profileviewphoto;
    private float underCupAmount = 100; // Amount added per button press

    @SuppressLint({"MissingInflatedId", "CutPasteId"})
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
        profileviewphoto = findViewById(R.id.profileImage);

        // Retrieve the user ID from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        userId = sharedPreferences.getLong("USER_ID", -1);

        if (userId != -1) {
            // Initialize ViewModel and fetch user data
            userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
            fetchUserData();
        } else {
            greetingText.setText("User ID not found in SharedPreferences");
        }

        // Set click listeners for CardViews
        findViewById(R.id.cardMoveDaily).setOnClickListener(v -> startActivity(new Intent(HomePage.this, MainActivity.class)));
//        findViewById(R.id.cardStayHydrated).setOnClickListener(v -> startActivity(new Intent(HomePage.this, MainActivity.class)));
        findViewById(R.id.cardStayActive).setOnClickListener(v -> startActivity(new Intent(HomePage.this, MainActivity.class)));
        findViewById(R.id.cardEatBalanced).setOnClickListener(v -> startActivity(new Intent(HomePage.this, MainActivity.class)));

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


        // Set default selected button
        homeButton.setSelected(true);

        // Initialize the ConstraintLayout (hydration card)
        CardView hydrationCard = findViewById(R.id.cardStayHydrated);
        hydrationCard.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, HydrationActivity.class);
            intent.putExtra("USER_ID", userId);
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

    // Methods to handle button clicks
    public void onHomeClicked(View view) {
        updateButtonStates(homeButton);
        Intent intent = new Intent(this, HomePage.class);
        intent.putExtra("USER_ID", userId);  // Pass the user ID to MapActivity
        startActivity(intent);
    }

    public void onGroupsClicked(View view) {
        updateButtonStates(groupsButton);
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("USER_ID", userId);  // Pass the user ID to MapActivity
        startActivity(intent);
    }

    //public void onCalendarClicked(View view) {
    //    updateButtonStates(calendarButton);
    //     Intent intent = new Intent(this, CalendarActivity.class);
    //   intent.putExtra("USER_ID", userId);  // Pass the user ID
    // startActivity(intent);
    //}

    public void onProfileClicked(View view) {
        updateButtonStates(profileButton);
        Intent intent = new Intent(this, Profile.class);
        intent.putExtra("USER_ID", userId);  // Pass the user ID to Profile activity
        startActivity(intent);
    }


    // Method to update the selected state of the buttons
    private void updateButtonStates(ImageButton selectedButton) {
        homeButton.setSelected(false);
        groupsButton.setSelected(false);
        calendarButton.setSelected(false);
        profileButton.setSelected(false);
        selectedButton.setSelected(true);
    }
}
