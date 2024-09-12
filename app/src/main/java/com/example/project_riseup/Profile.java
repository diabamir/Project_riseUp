package com.example.project_riseup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {

    private Button editpro, privacy;
    private TextView fullNameTextView, username, card1Header, card1Content, card2Header, card2Content;
    private UserApi userApi;
    private User currentUser;

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

        // Handle edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Retrofit API client for UserApi
        userApi = ApiClient.getClient().create(UserApi.class);

        // Retrieve the user ID from SharedPreferences (or use default user ID for testing)
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        long userId = sharedPreferences.getLong("userId", 1); // Default to 1 for testing

        if (userId != -1) {
            // Load the user data from the server using the user ID
            loadUserData(userId);
        }

        // Set up the edit profile button to navigate to EditProfileActivity
        editpro.setOnClickListener(view -> {
            Intent intent = new Intent(Profile.this, editProfile.class);
            intent.putExtra("USER_ID", userId);  // Pass the user ID to the edit activity
            startActivity(intent);  // Start the EditProfileActivity
        });

        // Set up the privacy button to navigate to the privacy activity
        privacy.setOnClickListener(view -> {
            Intent intent = new Intent(Profile.this, privacy.class);

            startActivity(intent);
        });
    }

    private void loadUserData(long userId) {
        // Fetch the user data from the server using the UserApi
        Call<User> call = userApi.getUserById(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    currentUser = response.body();

                    // Update the UI with the fetched user data
                    runOnUiThread(() -> {
                        // Set full name
                        String fullName = currentUser.getFirstName() + " " + currentUser.getLastName();
                        fullNameTextView.setText(fullName);

                        // Set card1Header and card1Content for weight
                        card1Header.setText("Weight");
                        card1Content.setText(currentUser.getWeight() + " kg");

                        // Set card2Header and card2Content for height
                        card2Header.setText("Height");
                        card2Content.setText(currentUser.getHeight() + " cm");
                    });
                } else {
                    Toast.makeText(Profile.this, "Failed to load user data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(Profile.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}