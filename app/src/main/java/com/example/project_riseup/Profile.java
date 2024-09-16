package com.example.project_riseup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

public class Profile extends AppCompatActivity {

    private Button editpro, privacy, logout;
    private TextView fullNameTextView, card1Header, card1Content, card2Header, card2Content;
    private UserViewModel userViewModel;
    private long userId;  // Declare userId variable to store the passed userId
    private ImageView profileviewphoto;
    private Button myGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        // Initialize views
        editpro = findViewById(R.id.editpro);
        privacy = findViewById(R.id.privacy);
        fullNameTextView = findViewById(R.id.fullName);
        card1Header = findViewById(R.id.card1Header);
        card1Content = findViewById(R.id.card1Content);
        card2Header = findViewById(R.id.card2Header);
        card2Content = findViewById(R.id.card2Content);
        logout = findViewById(R.id.logout);
        profileviewphoto = findViewById(R.id.profileview);
        logout = findViewById(R.id.logout);
        profileviewphoto = findViewById(R.id.profileview);
        myGroups = findViewById(R.id.myGroups);

        // Handle edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.profile), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UserViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Get the userId passed via Intent
        Intent intent = getIntent();
        userId = intent.getLongExtra("USER_ID", -1);  // Default to -1 if not passed

        if (userId != -1) {
            // Load the user data from Room database using the passed user ID
            loadUserDataFromRoom(userId);
        } else {
            Toast.makeText(this, "User ID not found in Intent", Toast.LENGTH_SHORT).show();
        }

        // Set up the edit profile button to navigate to EditProfileActivity
        editpro.setOnClickListener(view -> {
            Intent editIntent = new Intent(Profile.this, editProfile.class);
            editIntent.putExtra("USER_ID", userId);  // Pass the user ID to the edit activity
            startActivity(editIntent);  // Start the EditProfileActivity
        });

        myGroups.setOnClickListener(view -> {
            Intent groupIntent = new Intent(Profile.this, UserGroups.class);
            groupIntent.putExtra("USER_ID", userId);  // Pass the user ID to the edit activity
            startActivity(groupIntent);  // Start the EditProfileActivity
        });

        // Set up the logout button to navigate to the SignIn activity
        logout.setOnClickListener(view -> {
            Intent signInIntent = new Intent(Profile.this, SignIn.class);
            startActivity(signInIntent);
            Intent editIntent = new Intent(Profile.this, editProfile.class);
            editIntent.putExtra("USER_ID", userId);  // Pass the user ID to the edit activity
            startActivity(editIntent);  // Start the EditProfileActivity
        });

        // Set up the logout button to navigate to the SignIn activity
        logout.setOnClickListener(view -> {
            Intent signInIntent = new Intent(Profile.this, SignIn.class);
            intent.putExtra("USER_ID", userId);  // Pass the user ID to Profile activity
            startActivity(signInIntent);
        });

        // Set up the privacy button to navigate to the privacy activity
        privacy.setOnClickListener(view -> {
            Intent privacyIntent = new Intent(Profile.this, privacy.class);
            intent.putExtra("USER_ID", userId);  // Pass the user ID to Profile activity
            startActivity(privacyIntent);
        });


    }

    // Method to load user data from the Room database
    private void loadUserDataFromRoom(long userId) {
        // Fetch the user data synchronously from Room database using UserViewModel
        new Thread(() -> {
            User user = userViewModel.getUserById(userId);  // Synchronous fetch from Room

            if (user != null) {
                // Update the UI on the main thread
                new Handler(Looper.getMainLooper()).post(() -> {
                    // Set full name
                    String fullName = user.getFirstName() + " " + user.getLastName();
                    fullNameTextView.setText(fullName);

                    // Format weight and height to show two decimal places
                    String formattedWeight = String.format("%.2f", user.getWeight());
                    String formattedHeight = String.format("%.2f", user.getHeight());

                    // Set card1Header and card1Content for weight
                    card1Header.setText("Weight");
                    card1Content.setText(formattedWeight + " kg");

                    // Set card2Header and card2Content for height
                    card2Header.setText("Height");
                    card2Content.setText(formattedHeight + " cm");

                    // Check the gender and set the appropriate profile photo
                    if (user.getGender().equalsIgnoreCase("female")) {
                        profileviewphoto.setImageResource(R.drawable.womanprofile);  // Set female profile photo
                    } else if (user.getGender().equalsIgnoreCase("male")) {
                        profileviewphoto.setImageResource(R.drawable.manprofile);  // Set male profile photo
                    } else {
                        profileviewphoto.setImageResource(R.drawable.defaultprofile);  // Optional: set default profile photo if gender is unspecified
                    }
                });
            } else {
                runOnUiThread(() -> Toast.makeText(Profile.this, "User not found in local database", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

}