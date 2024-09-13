package com.example.project_riseup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.Executors;

public class GroupExpActivity extends AppCompatActivity {
    private Button nextButton;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_group_exp);


        Intent intent = getIntent();
        userId = intent.getIntExtra("USER_ID", -1); // Default to -1 if no ID is found

        // Set up the button and its click listener
        nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(v -> {
            if (userId != -1) {
//                 Update the seen attribute of the user in a background thread
                Executors.newSingleThreadExecutor().execute(() -> {
                    User user = UserDatabase.getInstance(this).userDao().getUserById(userId);
                    if (user != null) {
                        user.setSeeTheInstructions(true);
                        UserDatabase.getInstance(this).userDao().updateUser(user);

                        // Start the new activity and pass the userId
                        Intent nextIntent = new Intent(GroupExpActivity.this, ViewGroupsActivity.class);
//                        nextIntent.putExtra("USER_ID", userId);
                        startActivity(nextIntent);
                         // Optional: finish the current activity
                    }
                });
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}