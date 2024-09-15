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

    ImageButton addWater;
    TextView greetingText;
    UserViewModel userViewModel;
    Button profilebutton;
    long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Initialize the ImageButton and TextView
        addWater = findViewById(R.id.addWater);
        greetingText = findViewById(R.id.greetingText);

        // Retrieve the user ID from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        long userId = sharedPreferences.getLong("USER_ID", -1);  // Default to -1 if not found

        if (userId != -1) {
            // Initialize the ViewModel
            userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

            // Fetch the user from the Room database on a background thread
            new Thread(() -> {
                User user = userViewModel.getUserById(userId);
                runOnUiThread(() -> {
                    if (user != null) {
                        String firstName = user.getFirstName();
                        greetingText.setText("Hello, " + firstName + "!");
                    } else {
                        greetingText.setText("User not found");
                    }
                });
            }).start();
        } else {
            greetingText.setText("User ID not found in SharedPreferences");
        }

        // Set click listeners for each CardView
        findViewById(R.id.cardMoveDaily).setOnClickListener(v -> startActivity(new Intent(HomePage.this, StepsMain.class))); // This will take you to StepsMain
        findViewById(R.id.cardStayHydrated).setOnClickListener(v -> startActivity(new Intent(HomePage.this, MainActivity.class)));
        findViewById(R.id.cardStayActive).setOnClickListener(v -> startActivity(new Intent(HomePage.this, MainActivity.class)));
        findViewById(R.id.cardEatBalanced).setOnClickListener(v -> startActivity(new Intent(HomePage.this, MainActivity.class)));

        // Handle the addWater button click
        addWater.setOnClickListener(v -> Toast.makeText(HomePage.this, "Water added!", Toast.LENGTH_SHORT).show());

        // Navigate to the Profile activity and pass userId
        profilebutton = findViewById(R.id.profile1);
        profilebutton.setOnClickListener(v -> {
            Intent intentProfile = new Intent(HomePage.this, Profile.class);
            intentProfile.putExtra("USER_ID", userId);  // Pass the user ID to Profile activity
            startActivity(intentProfile);
        });
    }
}
//package com.example.project_riseup;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class HomePage extends AppCompatActivity {
//
//    // Declare the ImageButton variable here
//    ImageButton addWater;
//    TextView greetingText;
//    UserViewModel userViewModel;
//    Button profilebutton;;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home_page);
//
//        // Initialize the ImageButton after setContentView
//        addWater = findViewById(R.id.addWater);
//
//        // Set click listeners for each CardView
//        findViewById(R.id.cardMoveDaily).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomePage.this, MainActivity.class));
//            }
//        });
//
//        greetingText = findViewById(R.id.greetingText);
//        String firstName = getIntent().getStringExtra("firstName");
//        greetingText.setText("Hello, " + firstName + "!");
//
//        findViewById(R.id.cardStayHydrated).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomePage.this, MainActivity.class));
//            }
//        });
//
//        findViewById(R.id.cardStayActive).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomePage.this, MainActivity.class));
//            }
//        });
//
//        findViewById(R.id.cardEatBalanced).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomePage.this, MainActivity.class));
//            }
//        });
//
//        // Set an OnClickListener for the addWater button
//        addWater.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Perform the action to add water
//                Toast.makeText(HomePage.this, "Water added!", Toast.LENGTH_SHORT).show();
//            }
//        });
//        profilebutton = findViewById(R.id.profile1);
//        profilebutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomePage.this, Profile.class));
//            }
//        });
//
//    }
//}
