package com.example.project_riseup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

    private Button editpro, privacy, logout, myGroups;
    private TextView fullNameTextView, card1Header, card1Content, card2Header, card2Content;
    private UserViewModel userViewModel;
    private long userId;
    private ImageView profileviewphoto;
    private ImageButton homeButton, groupsButton, calendarButton, profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        initializeViews();
        setupNavigationButtons();

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userId = getIntent().getLongExtra("USER_ID", -1);

        if (userId != -1) {
            loadUserDataFromRoom(userId);
        } else {
            Toast.makeText(this, "User ID not found in Intent", Toast.LENGTH_SHORT).show();
        }

        editpro.setOnClickListener(view -> navigateToActivity(editProfile.class));
        myGroups.setOnClickListener(view -> navigateToActivity(UserGroups.class));
        logout.setOnClickListener(view -> navigateToActivity(SignIn.class));
        privacy.setOnClickListener(view -> navigateToActivity(privacy.class));
    }

    private void initializeViews() {
        editpro = findViewById(R.id.editpro);
        privacy = findViewById(R.id.privacy);
        fullNameTextView = findViewById(R.id.fullName);
        card1Header = findViewById(R.id.card1Header);
        card1Content = findViewById(R.id.card1Content);
        card2Header = findViewById(R.id.card2Header);
        card2Content = findViewById(R.id.card2Content);
        logout = findViewById(R.id.logout);
        profileviewphoto = findViewById(R.id.profileview);
        myGroups = findViewById(R.id.myGroups);
        homeButton = findViewById(R.id.homeImageButton);
        groupsButton = findViewById(R.id.groupsImageButton);
        calendarButton = findViewById(R.id.calendarImageButton);
        profileButton = findViewById(R.id.profileImageButton);

        profileButton.setSelected(true);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.profile), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupNavigationButtons() {
        homeButton.setOnClickListener(view -> navigateToActivity(HomePage.class));
        groupsButton.setOnClickListener(view -> navigateToActivity(MapActivity.class));
        profileButton.setOnClickListener(view -> updateButtonStates(profileButton));
    }

    private void navigateToActivity(Class<?> cls) {
        Intent intent = new Intent(Profile.this, cls);
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

    private void loadUserDataFromRoom(long userId) {
        new Thread(() -> {
            User user = userViewModel.getUserById(userId);
            if (user != null) {
                runOnUiThread(() -> updateUIWithUserData(user));
            } else {
                runOnUiThread(() -> Toast.makeText(Profile.this, "User not found in local database", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void updateUIWithUserData(User user) {
        String fullName = user.getFirstName() + " " + user.getLastName();
        fullNameTextView.setText(fullName);

        String formattedWeight = String.format("%.2f", user.getWeight());
        String formattedHeight = String.format("%.2f", user.getHeight());

        card1Header.setText("Weight");
        card1Content.setText(formattedWeight + " kg");
        card2Header.setText("Height");
        card2Content.setText(formattedHeight + " cm");

        if ("female".equalsIgnoreCase(user.getGender())) {
            profileviewphoto.setImageResource(R.drawable.womanprofile);
        } else if ("male".equalsIgnoreCase(user.getGender())) {
            profileviewphoto.setImageResource(R.drawable.manprofile);
        } else {
            profileviewphoto.setImageResource(R.drawable.defaultprofile);
        }
    }
}
