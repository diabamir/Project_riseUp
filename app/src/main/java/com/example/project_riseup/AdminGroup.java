package com.example.project_riseup;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

public class AdminGroup extends AppCompatActivity {
    private Button groupMembersButton;
    private Button groupRequestsButton;
    private Spinner groupIdSpinner;
    private GroupViewModel groupViewModel;
    private LinearLayout hiddenMembers;
    private LinearLayout hiddenRequests;
    private long selectedGroupId;  // To store the selected group's ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_group);

        // Initialize views
        groupIdSpinner = findViewById(R.id.groupIdSpinner);
        groupMembersButton = findViewById(R.id.groupMembersButton);
        groupRequestsButton = findViewById(R.id.groupRequestsButton);
        hiddenMembers = findViewById(R.id.hiddenMembers);
        hiddenRequests = findViewById(R.id.hiddenRequests);

        // Set up the ViewModel
        setupViewModel();

        // Show/Hide group members when the button is clicked
        groupMembersButton.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(hiddenMembers);
            hiddenMembers.setVisibility(hiddenMembers.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            loadGroupMembers(selectedGroupId); // Fetch members for the selected group
        });

        // Show/Hide group requests when the button is clicked
        groupRequestsButton.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(hiddenRequests);
            hiddenRequests.setVisibility(hiddenRequests.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            loadGroupRequests(selectedGroupId); // Fetch join requests for the selected group
        });
    }

    // Set up the ViewModel to fetch groups
    private void setupViewModel() {
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);

        // Observing the list of groups for this admin
        groupViewModel.getAllGroupsForAdmin().observe(this, groups -> {
            if (groups != null && !groups.isEmpty()) {
                List<String> groupNames = new ArrayList<>();
                for (Group g : groups) {
                    groupNames.add("ID: " + g.getId() + " - " + g.getWorkOut());
                }

                // Set up the spinner with the group names
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AdminGroup.this, android.R.layout.simple_spinner_item, groupNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                groupIdSpinner.setAdapter(adapter);

                // Set the selected group ID to the first group by default
                selectedGroupId = groups.get(0).getId();

                // Handle spinner selection changes
                groupIdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        selectedGroupId = groups.get(position).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        selectedGroupId = groups.get(0).getId();
                    }
                });
            } else {
                // Handle case where no groups are available
                Toast.makeText(AdminGroup.this, "No groups available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Load and display group members for the selected group
    private void loadGroupMembers(long groupId) {
        groupViewModel.getUsersForGroup(groupId).observe(this, members -> {
            hiddenMembers.removeAllViews();  // Clear previous views

            if (members.isEmpty()) {
                Toast.makeText(AdminGroup.this, "There are no members in this group.", Toast.LENGTH_SHORT).show();
            } else {
                for (User user : members) {
                    View cardView = LayoutInflater.from(AdminGroup.this)
                            .inflate(R.layout.user_member_card, hiddenMembers, false);

                    TextView firstNameLastName = cardView.findViewById(R.id.firstNameLastName);
                    TextView phoneNumber = cardView.findViewById(R.id.phoneNumber);
                    TextView gender = cardView.findViewById(R.id.gender);
                    TextView fitnessLevel = cardView.findViewById(R.id.fitnessLevel);
                    Button removeButton = cardView.findViewById(R.id.removeButton);

                    // Set the user's data
                    firstNameLastName.setText(user.getFirstName() + " " + user.getLastName());
                    phoneNumber.setText("Phone: " + user.getPhoneNumber());
                    gender.setText("Gender: " + user.getGender());
                    fitnessLevel.setText("Fitness Level: " + user.getFitnessLevel());

                    // Handle the Remove button click
                    removeButton.setOnClickListener(v -> {
                        groupViewModel.removeUserFromGroup(user.getId(), groupId);
                        hiddenMembers.removeView(cardView);  // Remove the card from the view
                        Toast.makeText(AdminGroup.this, user.getFirstName() + " has been removed.", Toast.LENGTH_SHORT).show();
                    });

                    hiddenMembers.addView(cardView);  // Add the user view to the hiddenMembers layout
                }
            }
        });
    }

    // Load and display group join requests for the selected group
    private void loadGroupRequests(long groupId) {
        groupViewModel.getPendingJoinRequestsForGroup(groupId).observe(this, requests -> {
            hiddenRequests.removeAllViews();  // Clear previous views

            if (requests.isEmpty()) {
                Toast.makeText(AdminGroup.this, "There are no join requests for this group.", Toast.LENGTH_SHORT).show();
            } else {
                for (User user : requests) {
                    View cardView = LayoutInflater.from(AdminGroup.this)
                            .inflate(R.layout.user_request_card, hiddenRequests, false);

                    TextView firstNameLastName = cardView.findViewById(R.id.firstNameLastName);
                    TextView phoneNumber = cardView.findViewById(R.id.phoneNumber);
                    TextView gender = cardView.findViewById(R.id.gender);
                    TextView fitnessLevel = cardView.findViewById(R.id.fitnessLevel);
                    Button confirmButton = cardView.findViewById(R.id.confirmButton);
                    Button removeButton = cardView.findViewById(R.id.removeButton);

                    // Set the user's data
                    firstNameLastName.setText(user.getFirstName() + " " + user.getLastName());
                    phoneNumber.setText("Phone: " + user.getPhoneNumber());
                    gender.setText("Gender: " + user.getGender());
                    fitnessLevel.setText("Fitness Level: " + user.getFitnessLevel());

                    // Handle the Confirm button click
                    confirmButton.setOnClickListener(v -> {
                        groupViewModel.approveJoinRequest(user.getId(), groupId);
                        hiddenRequests.removeView(cardView);  // Remove the card from the view
                        Toast.makeText(AdminGroup.this, user.getFirstName() + " has been confirmed.", Toast.LENGTH_SHORT).show();
                    });

                    // Handle the Remove button click
                    removeButton.setOnClickListener(v -> {
                        groupViewModel.removeUserFromGroup(user.getId(), groupId);
                        hiddenRequests.removeView(cardView);  // Remove the card from the view
                        Toast.makeText(AdminGroup.this, user.getFirstName() + " has been removed.", Toast.LENGTH_SHORT).show();
                    });

                    hiddenRequests.addView(cardView);  // Add the request view to the hiddenRequests layout
                }
            }
        });
    }
}
