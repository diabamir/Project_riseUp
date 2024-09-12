package com.example.project_riseup;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class UserGroups extends AppCompatActivity {

    private UserGroupApi userGroupApi;
    private long currentUserId = 1; // Replace with the actual user ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_groups);

        // Initialize the UserGroupApi instance from ApiClient
        userGroupApi = ApiClient.getClient().create(UserGroupApi.class);

        // Fetch and display both requested and joined groups
        fetchGroups();

        // Handle insets for proper padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void fetchGroups() {
        // Fetch requested groups
        userGroupApi.getGroupsForUser(currentUserId, "requested").enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    displayGroups(response.body(), R.id.linearLayoutJoinRequests);
                }
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                // Handle API call failure
            }
        });

        // Fetch joined groups
        userGroupApi.getGroupsForUser(currentUserId, "joined").enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    displayGroups(response.body(), R.id.linearLayoutJoinedGroups);
                }
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                // Handle API call failure
            }
        });
    }

    private void displayGroups(List<Group> groups, int linearLayoutId) {
        LinearLayout linearLayout = findViewById(linearLayoutId);
        linearLayout.removeAllViews();

        for (Group group : groups) {
            // Create a TextView for each group
            TextView groupTextView = new TextView(this);
            groupTextView.setText(group.getWorkOut());  // Display the workout name
            groupTextView.setPadding(16, 16, 16, 16);   // Add some padding for better visuals
            groupTextView.setTextSize(16);              // Set text size

            // Add the TextView to the LinearLayout
            linearLayout.addView(groupTextView);
        }
    }
}
