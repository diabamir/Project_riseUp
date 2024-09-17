package com.example.project_riseup;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsCompat.Type;
import androidx.lifecycle.LiveData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class UserGroups extends AppCompatActivity {

    private UserGroupJoinDao userGroupJoinDao;
    private groupDAO groupDao;
    private ImageButton homeButton,groupsButton,calendarButton,profileButton;
    private long currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_groups);

        Intent intent = getIntent();
        currentUserId = intent.getLongExtra("USER_ID", -1);

        // Initialize DAOs
        userGroupJoinDao = UserGroupJoinDatabase.getInstance(this).UserGroupJoinDao();
        groupDao = groupDataBase.getInstance(this).groupDao();

        fetchGroups();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize buttons
        homeButton = findViewById(R.id.homeImageButton);
        groupsButton = findViewById(R.id.groupsImageButton);
        calendarButton = findViewById(R.id.calendarImageButton);
        profileButton = findViewById(R.id.profileImageButton);

        // Set the home button as selected by default, since this is the HomeActivity
        homeButton.setSelected(true);

        // Set click listeners for each button
        homeButton.setOnClickListener(this::onHomeClicked);
        groupsButton.setOnClickListener(this::onGroupsClicked);
        calendarButton.setOnClickListener(v -> startActivity(new Intent(this, CalendarActivity.class)));
        profileButton.setOnClickListener(this::onProfileClicked);
    }
    // Methods to handle button clicks
    public void onHomeClicked(View view) {
        // No need to start the HomeActivity again, just update button state
        updateButtonStates(homeButton)
        ;
    }

    public void onGroupsClicked(View view) {
        updateButtonStates(groupsButton);
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("USER_ID", currentUserId);
        startActivity(intent);
    }

//    public void onCalendarClicked(View view) {
//        updateButtonStates(calendarButton);
//        Intent intent = new Intent(this, CalendarActivity.class);
//        startActivity(intent);
//    }

    public void onProfileClicked(View view) {
        updateButtonStates(profileButton);
        Intent intent = new Intent(this, Profile.class);
        intent.putExtra("USER_ID", currentUserId);
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

    private void fetchGroups() {
        userGroupJoinDao.getUserGroups(currentUserId).observe(this, userGroupJoins -> {
            List<Group> groups = new ArrayList<>();
            if (userGroupJoins != null) {
                for (UserGroupJoin join : userGroupJoins) {
                    // Only process the groups with "joined" status
                    if ("joined".equals(join.getStatus())) {
                        long groupId = join.getGroupId();

                        // Fetch the group by its ID
                        groupDao.getGroupById(groupId).observe(this, group -> {
                            if (group != null) {
                                groups.add(group);

                                // Once all "joined" groups are fetched, display them
                                if (groups.size() == userGroupJoins.size()) {
                                    displayGroups(groups, R.id.linearLayoutJoinedGroups);
                                }
                            }
                        });
                    }
                }
            }
        });
    }



    private LiveData<Group> fetchGroupById(long groupId) {
        // Fetch group as LiveData
        return groupDao.getGroupById(groupId);
    }


    private void displayGroups(List<Group> groups, int linearLayoutId) {
        LinearLayout linearLayout = findViewById(linearLayoutId);
        linearLayout.removeAllViews();

        for (Group group : groups) {
            LinearLayout groupLayout = new LinearLayout(this);
            groupLayout.setOrientation(LinearLayout.HORIZONTAL);
            groupLayout.setPadding(16, 16, 16, 16);

            ImageView groupImageView = new ImageView(this);
            groupImageView.setImageResource(group.getImageGroup());
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(150, 150);
            groupImageView.setLayoutParams(imageParams);
            groupImageView.setPadding(8, 8, 8, 8);

            TextView groupTextView = new TextView(this);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String date = group.getDate() != null ? dateFormat.format(group.getDate()) : "No date";
            String startTime = group.getStartTime() != null ? group.getStartTime() : "No start time";
            String endTime = group.getEndTime() != null ? group.getEndTime() : "No end time";

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
            appendBoldText(builder, "Admin Number: ");
            builder.append(group.getPhone() + "\n");

            groupTextView.setText(builder);
            groupTextView.setPadding(16, 0, 16, 0);
            groupTextView.setTextSize(16);

            Button actionButton = new Button(this);
            actionButton.setBackgroundColor(Color.parseColor("#AC82E7"));
            actionButton.setText("Remove Group");
            actionButton.setOnClickListener(v -> unjoinGroup(group.getId()));

            groupLayout.addView(groupImageView);
            groupLayout.addView(groupTextView);
            groupLayout.addView(actionButton);

            linearLayout.addView(groupLayout);
        }
    }

    private void appendBoldText(SpannableStringBuilder builder, String text) {
        int start = builder.length();
        builder.append(text);
        builder.setSpan(new StyleSpan(Typeface.BOLD), start, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void unjoinGroup(long groupId) {
        new Thread(() -> {
            // Delete the UserGroupJoin entry from the database
            userGroupJoinDao.deleteUserGroupJoin(currentUserId, groupId);

            runOnUiThread(() -> {
                // Immediately remove the group from the list by refetching the joined groups
                fetchGroups();

                // Show feedback to the user
                Toast.makeText(UserGroups.this, "Successfully unjoined the group", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
}
