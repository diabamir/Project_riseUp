package com.example.project_riseup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {

    // Declare the ImageButton variable here
    ImageButton addWater;
    TextView greetingText;
    UserViewModel userViewModel;
    Button profilebutton;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Initialize the ImageButton after setContentView
        addWater = findViewById(R.id.addWater);

        // Set click listeners for each CardView
        findViewById(R.id.cardMoveDaily).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, MainActivity.class));
            }
        });

        greetingText = findViewById(R.id.greetingText);
        String firstName = getIntent().getStringExtra("firstName");
        greetingText.setText("Hello, " + firstName + "!");

        findViewById(R.id.cardStayHydrated).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, MainActivity.class));
            }
        });

        findViewById(R.id.cardStayActive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, MainActivity.class));
            }
        });

        findViewById(R.id.cardEatBalanced).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, MainActivity.class));
            }
        });

        // Set an OnClickListener for the addWater button
        addWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the action to add water
                Toast.makeText(HomePage.this, "Water added!", Toast.LENGTH_SHORT).show();
            }
        });
        profilebutton = findViewById(R.id.profile1);
        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, Profile.class));
            }
        });

    }
}
