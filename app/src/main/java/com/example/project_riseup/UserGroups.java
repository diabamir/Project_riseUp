package com.example.project_riseup;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsCompat.Type;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

//        // Manually add test groups for display
//        addTestGroups();

        // Handle insets for proper padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(Type.systemBars());
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

//    private void addTestGroups() {
//        List<Group> joinedGroups = new ArrayList<>();
//        List<Group> requestedGroups = new ArrayList<>();
//
//        // Create 2 test groups for "Joined"
//        for (int i = 0; i < 2; i++) {
//            joinedGroups.add(createTestGroup(i + 1, "Joined Group " + (i + 1)));
//        }
//
//        // Create 3 test groups for "Requested"
//        for (int i = 0; i < 3; i++) {
//            requestedGroups.add(createTestGroup(i + 3, "Requested Group " + (i + 1)));
//        }
//
//        // Display the test groups in their respective sections
//        displayGroups(joinedGroups, R.id.linearLayoutJoinedGroups);
//        displayGroups(requestedGroups, R.id.linearLayoutJoinRequests);
//    }

//    private Group createTestGroup(int id, String groupName) {
//        // Create a sample group using the full constructor
//        return new Group(
//                id,                        // id
//                groupName,                // workOut
//                "Community Center",       // location
//                "Session " + id,          // description
//                "06:00",                  // startTime
//                "07:00",                  // endTime
//                new Date(),               // date
//                123,                      // userAdminId
//                null,                     // users
//                10,                       // membersNumber
//                5,                        // howManyJoin
//                R.drawable.climbing      // imageGroup (replace with actual drawable resource)
//        );
//    }

    private void displayGroups(List<Group> groups, int linearLayoutId) {
        LinearLayout linearLayout = findViewById(linearLayoutId);
        linearLayout.removeAllViews();

        for (Group group : groups) {
            // Create a container layout for each group
            LinearLayout groupLayout = new LinearLayout(this);
            groupLayout.setOrientation(LinearLayout.HORIZONTAL);
            groupLayout.setPadding(16, 16, 16, 16);

            // Create an ImageView for the group's image
            ImageView groupImageView = new ImageView(this);
            groupImageView.setImageResource(group.getImageGroup()); // Assuming the drawable resource is set correctly
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(150, 150); // Set size as needed
            groupImageView.setLayoutParams(imageParams);
            groupImageView.setPadding(8, 8, 8, 8);

            // Create a TextView for each group
            TextView groupTextView = new TextView(this);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String date = group.getDate() != null ? dateFormat.format(group.getDate()) : "No birth date";
            String startTime = group.getStartTime() != null ? group.getStartTime() : "No contact time";
            String endTime = group.getEndTime() != null ? group.getEndTime() : "No contact time";

            // Use SpannableString to format the text
            SpannableStringBuilder builder = new SpannableStringBuilder();

            appendBoldText(builder, "Id: ");
            builder.append(group.getId() + "\n");

            appendBoldText(builder, "Workout: ");
            builder.append(group.getWorkOut() + "\n");

            appendBoldText(builder, "Location: ");
            builder.append(group.getLocation() + "\n");

            appendBoldText(builder, "Description: ");
            builder.append(group.getDiscribtion() + "\n");

            appendBoldText(builder, "Date: ");
            builder.append(date + "\n");

            appendBoldText(builder, "Start time: ");
            builder.append(startTime + "\n");

            appendBoldText(builder, "End time: ");
            builder.append(endTime + "\n");

            appendBoldText(builder, "Members Number: ");
            builder.append(group.getMembersNumber() + "\n");


            groupTextView.setText(builder);
            groupTextView.setPadding(16, 0, 16, 0);   // Add some padding for better visuals
            groupTextView.setTextSize(16);            // Set text size

            Button actionButton = new Button(this);
            actionButton.setBackgroundColor(Color.parseColor("#AC82E7")); // Example color code

            if (linearLayoutId == R.id.linearLayoutJoinedGroups) {
                actionButton.setText("Unjoin");
                actionButton.setOnClickListener(v -> unjoinGroup(group.getId()));
            } else if (linearLayoutId == R.id.linearLayoutJoinRequests) {
                actionButton.setText("Unrequest");
                actionButton.setOnClickListener(v -> unrequestGroup(group.getId()));
            }
            // Add the ImageView and TextView to the group layout
            groupLayout.addView(groupImageView);
            groupLayout.addView(groupTextView);
            groupLayout.addView(actionButton);

            // Add the group layout to the main linear layout
            linearLayout.addView(groupLayout);
        }
    }

    // Helper method to append bold text to SpannableStringBuilder
    private void appendBoldText(SpannableStringBuilder builder, String text) {
        int start = builder.length();
        builder.append(text);
        builder.setSpan(new StyleSpan(Typeface.BOLD), start, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void unjoinGroup(long groupId) {
        userGroupApi.updateUserStatusInGroup(currentUserId, groupId, null).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Handle successful unjoin, e.g., refresh the joined groups list
                    fetchGroups(); // To refresh the list of groups
                } else {
                    // Handle failure
                    Toast.makeText(UserGroups.this, "Failed to unjoin the group", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure
                Toast.makeText(UserGroups.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void unrequestGroup(long groupId) {
        userGroupApi.updateUserStatusInGroup(currentUserId, groupId, null).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Handle successful unrequest, e.g., refresh the requested groups list
                    fetchGroups(); // To refresh the list of groups
                } else {
                    // Handle failure
                    Toast.makeText(UserGroups.this, "Failed to unrequest the group", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure
                Toast.makeText(UserGroups.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
