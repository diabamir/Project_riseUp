package com.example.project_riseup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

public class ViewGroupsActivity extends AppCompatActivity implements OnJoinClickListener {

    private RecyclerView recyclerView;
    private GroupAdapter groupAdapter;
    private GroupViewModel groupViewModel;
    private ActivityResultLauncher<Intent> mapActivityLauncher;
    private ImageButton homeButton, groupsButton, calendarButton, profileButton;
    long userId;
    private String locationToFilter = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_groups);

        Button btnAddGroup = findViewById(R.id.btnAddGroup);
//        btnAddGroup.setOnClickListener(v -> startActivity(new Intent(ViewGroupsActivity.this, FavActivites.class)));

        Button btnForMap = findViewById(R.id.btnForMap);
//        btnForMap.setOnClickListener(v -> startActivity(new Intent(ViewGroupsActivity.this, MapActivity.class)));



//        Intent intentG = getIntent();
//        userId = intentG.getLongExtra("USER_ID", -1);

        btnForMap.setOnClickListener(v -> {
            // Create an Intent to start FavActivites
            Intent intent = new Intent(ViewGroupsActivity.this, MapActivity.class);

            // Add the location data as an extra
            intent.putExtra("USER_ID", userId);
            // Start the new activity
            startActivity(intent);
        });

        btnAddGroup.setOnClickListener(v -> {
            // Create an Intent to start FavActivites
            Intent intent = new Intent(ViewGroupsActivity.this, FavActivites.class);

            intent.putExtra("USER_ID", userId);
            // Add the location data as an extra
            intent.putExtra("LOCATION", locationToFilter);

            // Start the new activity
            startActivity(intent);
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        groupAdapter = new GroupAdapter(this);
        recyclerView.setAdapter(groupAdapter);

        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);

        // Setup activity result launcher for MapActivity
        mapActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                String location = result.getData().getStringExtra("LOCATION");
                filterGroupsByLocation(location);
            }
        });

        // Retrieve Intent and check for parameters
        Intent intent = getIntent();
        if (intent != null) {
            // Check if there is a LOCATION parameter
            if (intent.hasExtra("LOCATION")) {
                locationToFilter = intent.getStringExtra("LOCATION");
                userId = intent.getLongExtra("USER_ID", -1);
                filterGroupsByLocation(locationToFilter);
            }

            // Check if there is a GROUP_ID parameter
            else if (intent.hasExtra("GROUP_ID")) {
                userId = intent.getLongExtra("USER_ID", -1);
                int groupId = intent.getIntExtra("GROUP_ID", -1); // Default to -1 if not found
                if (groupId != -1) {
                    filterGroupsById(groupId);
                    btnAddGroup.setVisibility(View.GONE); // Hide the button if a group ID is present
                } else {
                    Toast.makeText(this, "Invalid Group ID", Toast.LENGTH_SHORT).show();
                }
            } else {
                // No GROUP_ID provided, observe all groups
                observeAllGroups();
            }
        } else {
            // No intent or extras, observe all groups
            observeAllGroups();
        }
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
//        calendarButton.setOnClickListener(this::onCalendarClicked);
        profileButton.setOnClickListener(this::onProfileClicked);
    }
    // Methods to handle button clicks
    public void onHomeClicked(View view) {
        // No need to start the HomeActivity again, just update button state
        updateButtonStates(homeButton);
    }

    public void onGroupsClicked(View view) {
        updateButtonStates(groupsButton);
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("USER_ID", userId);
        startActivity(intent);
    }

    public void onProfileClicked(View view) {
        updateButtonStates(profileButton);
        Intent intent = new Intent(this, Profile.class);
        intent.putExtra("USER_ID", userId);
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
    private void observeAllGroups() {
        groupViewModel.getAllGroups().observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(List<Group> groups){
                if (groups == null || groups.isEmpty()) {
                    Toast.makeText(ViewGroupsActivity.this, "There is no groups yet :(", Toast.LENGTH_SHORT).show();
                    groupAdapter.setGroups(new ArrayList<>()); // Ensure the RecyclerView is cleared
                } else {
                    groupAdapter.setGroups(groups);
                }
            }
        });
    }

    private void filterGroupsByLocation(String location) {
        groupViewModel.getGroupsByLocation(location).observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(List<Group> groups) {
                groupAdapter.setGroups(groups);
            }
        });
    }

    private void filterGroupsById(int groupId) {
        groupViewModel.getGroupById(groupId).observe(this, new Observer<Group>() {
            @Override
            public void onChanged(Group group) {
                if (group != null) {
                    // Display the group in the adapter by wrapping it in a list
                    List<Group> groups = new ArrayList<>();
                    groups.add(group);
                    groupAdapter.setGroups(groups);
                } else {
                    Toast.makeText(ViewGroupsActivity.this, "Group not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onJoinClick(int position) {
        Group group = groupAdapter.getGroupAtPosition(position);

        // Increase the joined member count
        int newCount = group.getHowManyJoin() + 1;
        group.setHowManyJoin(newCount);

        new Thread(() -> {
            try {
                // Update group in the database
                groupDataBase.getInstance(this).groupDao().updateGroup(group);

                // Create a new UserGroupJoin object
                UserGroupJoin userGroupJoin = new UserGroupJoin(userId, group.getId(), "joined");

                // Insert the UserGroupJoin object into the database
                UserGroupJoinDatabase.getInstance(this).UserGroupJoinDao().insert(userGroupJoin);

                runOnUiThread(() -> {
                    // Update the UI to reflect the joined group
                    groupAdapter.notifyItemChanged(position);

                    // Provide feedback to the user
                    Toast.makeText(ViewGroupsActivity.this, "You have joined the group!", Toast.LENGTH_SHORT).show();

                    // Check join status and update button state
                    checkJoinStatus(group);
                });

            } catch (Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(ViewGroupsActivity.this, "Error joining group", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }


    private void checkJoinStatus(Group group) {
        UserGroupJoinDatabase.getInstance(this).UserGroupJoinDao()
                .getUserGroupJoin(userId, group.getId())
                .observe(this, userGroupJoin -> {
                    Button joinButton = findViewById(R.id.joinButton); // Make sure to get the button by its ID
                    if (userGroupJoin != null && "joined".equals(userGroupJoin.getStatus())) {
                        joinButton.setText("Joined");
                        joinButton.setEnabled(false);
                    } else {
                        joinButton.setText("Join");
                        joinButton.setEnabled(true);
                    }
                });
    }
}