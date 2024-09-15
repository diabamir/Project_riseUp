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

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminGroup extends AppCompatActivity {
    private Button groupMembersButton;
    private Button groupRequestsButton;
    private Spinner groupIdSpinner;
    private GroupViewModel groupViewModel;
    private LinearLayout hiddenMembers;
    private LinearLayout hiddenRequests;
    private long selectedGroupId;  // To store the selected group's ID

    private UserGroupApi userGroupApi;  // API instance for server communication

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_group);

        // Initialize the API client
        userGroupApi = ApiClient.getClient().create(UserGroupApi.class);

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

        // Fetch all groups for admin
        groupViewModel.getAllGroups().observe(this, groups -> {
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
        Call<List<User>> call = userGroupApi.getUsersForGroup(groupId, "joined");
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                hiddenMembers.removeAllViews();  // Clear previous views

                List<User> members = response.body();
                if (members == null || members.isEmpty()) {
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
                            // Remove user from the group and set status to declined
                            Call<Void> call2 = userGroupApi.updateUserStatusInGroup(user.getId(), groupId, "declined");
                            call2.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call2, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        hiddenMembers.removeView(cardView);  // Remove the card from the view
                                        Toast.makeText(AdminGroup.this, user.getFirstName() + " has been removed.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(AdminGroup.this, "Failed to remove " + user.getFirstName(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call2, Throwable t) {
                                    Toast.makeText(AdminGroup.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        });

                        hiddenMembers.addView(cardView);  // Add the user view to the hiddenMembers layout
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(AdminGroup.this, "Error loading members: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Load and display group join requests for the selected group
    private void loadGroupRequests(long groupId) {
        Call<List<User>> call = userGroupApi.getUsersForGroup(groupId, "requested");
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                hiddenRequests.removeAllViews();  // Clear previous views

                List<User> requests = response.body();
                if (requests == null || requests.isEmpty()) {
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
                        Button declineButton = cardView.findViewById(R.id.removeButton);

                        // Set the user's data
                        firstNameLastName.setText(user.getFirstName() + " " + user.getLastName());
                        phoneNumber.setText("Phone: " + user.getPhoneNumber());
                        gender.setText("Gender: " + user.getGender());
                        fitnessLevel.setText("Fitness Level: " + user.getFitnessLevel());

                        // Handle the Confirm button click
                        confirmButton.setOnClickListener(v -> {
                            // Confirm the user by changing the status to "joined"
                            Call<Void> call3 = userGroupApi.updateUserStatusInGroup(user.getId(), groupId, "joined");
                            call3.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call3, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        hiddenRequests.removeView(cardView);  // Remove the card from the view
                                        Toast.makeText(AdminGroup.this, user.getFirstName() + " has been confirmed.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(AdminGroup.this, "Failed to confirm " + user.getFirstName(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call3, Throwable t) {
                                    Toast.makeText(AdminGroup.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        });

                        // Handle the Decline button click
                        declineButton.setOnClickListener(v -> {
                            // Decline the user by changing the status to "declined"
                            Call<Void> call4 = userGroupApi.updateUserStatusInGroup(user.getId(), groupId, "declined");
                            call4.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call4, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        hiddenRequests.removeView(cardView);  // Remove the card from the view
                                        Toast.makeText(AdminGroup.this, user.getFirstName() + " has been declined.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(AdminGroup.this, "Failed to decline " + user.getFirstName(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call4, Throwable t) {
                                    Toast.makeText(AdminGroup.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        });

                        hiddenRequests.addView(cardView);  // Add the request view to the hiddenRequests layout
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(AdminGroup.this, "Error loading requests: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
//package com.example.project_riseup;
//
//import android.os.Bundle;
//import android.transition.TransitionManager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.lifecycle.ViewModelProvider;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class AdminGroup extends AppCompatActivity {
//    private Button groupMembersButton;
//    private Button groupRequestsButton;
//    private Spinner groupIdSpinner;
//    private GroupViewModel groupViewModel;
//    private LinearLayout hiddenMembers;
//    private LinearLayout hiddenRequests;
//    private long selectedGroupId;  // To store the selected group's ID
//
//    private UserGroupApi userGroupApi;  // API instance for server communication
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_admin_group);
//
//        // Initialize the API client
//        userGroupApi = ApiClient.getClient().create(UserGroupApi.class);
//
//        // Initialize views
//        groupIdSpinner = findViewById(R.id.groupIdSpinner);
//        groupMembersButton = findViewById(R.id.groupMembersButton);
//        groupRequestsButton = findViewById(R.id.groupRequestsButton);
//        hiddenMembers = findViewById(R.id.hiddenMembers);
//        hiddenRequests = findViewById(R.id.hiddenRequests);
//
//        // Set up the ViewModel
//        setupViewModel();
//
//        // Show/Hide group members when the button is clicked
//        groupMembersButton.setOnClickListener(v -> {
//            TransitionManager.beginDelayedTransition(hiddenMembers);
//            hiddenMembers.setVisibility(hiddenMembers.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
//            loadGroupMembers(selectedGroupId); // Fetch members for the selected group
//        });
//
//        // Show/Hide group requests when the button is clicked
//        groupRequestsButton.setOnClickListener(v -> {
//            TransitionManager.beginDelayedTransition(hiddenRequests);
//            hiddenRequests.setVisibility(hiddenRequests.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
//            loadGroupRequests(selectedGroupId); // Fetch join requests for the selected group
//        });
//    }
//
//    // Set up the ViewModel to fetch groups
//    private void setupViewModel() {
//        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
//
//        // Fetch all groups for admin
//        groupViewModel.getAllGroups().observe(this, groups -> {
//            if (groups != null && !groups.isEmpty()) {
//                List<String> groupNames = new ArrayList<>();
//                for (Group g : groups) {
//                    groupNames.add("ID: " + g.getId() + " - " + g.getWorkOut());
//                }
//
//                // Set up the spinner with the group names
//                ArrayAdapter<String> adapter = new ArrayAdapter<>(AdminGroup.this, android.R.layout.simple_spinner_item, groupNames);
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                groupIdSpinner.setAdapter(adapter);
//
//                // Set the selected group ID to the first group by default
//                selectedGroupId = groups.get(0).getId();
//
//                // Handle spinner selection changes
//                groupIdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                        selectedGroupId = groups.get(position).getId();
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parentView) {
//                        selectedGroupId = groups.get(0).getId();
//                    }
//                });
//            } else {
//                // Handle case where no groups are available
//                Toast.makeText(AdminGroup.this, "No groups available", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    // Load and display group members for the selected group
//    private void loadGroupMembers(long groupId) {
//        Call<List<User>> call = userGroupApi.getUsersForGroup(groupId, "joined");
//        call.enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                hiddenMembers.removeAllViews();  // Clear previous views
//
//                List<User> members = response.body();
//                if (members == null || members.isEmpty()) {
//                    Toast.makeText(AdminGroup.this, "There are no members in this group.", Toast.LENGTH_SHORT).show();
//                } else {
//                    for (User user : members) {
//                        View cardView = LayoutInflater.from(AdminGroup.this)
//                                .inflate(R.layout.user_member_card, hiddenMembers, false);
//
//                        TextView firstNameLastName = cardView.findViewById(R.id.firstNameLastName);
//                        TextView phoneNumber = cardView.findViewById(R.id.phoneNumber);
//                        TextView gender = cardView.findViewById(R.id.gender);
//                        TextView fitnessLevel = cardView.findViewById(R.id.fitnessLevel);
//                        Button removeButton = cardView.findViewById(R.id.removeButton);
//
//                        // Set the user's data
//                        firstNameLastName.setText(user.getFirstName() + " " + user.getLastName());
//                        phoneNumber.setText("Phone: " + user.getPhoneNumber());
//                        gender.setText("Gender: " + user.getGender());
//                        fitnessLevel.setText("Fitness Level: " + user.getFitnessLevel());
//
//                        // Handle the Remove button click
//                        removeButton.setOnClickListener(v -> {
//                            // Remove user from the group and set status to declined
//                            Call<Void> call2 = userGroupApi.updateUserStatusInGroup(user.getId(), groupId, "declined");
//                            call2.enqueue(new Callback<Void>() {
//                                @Override
//                                public void onResponse(Call<Void> call2, Response<Void> response) {
//                                    if (response.isSuccessful()) {
//                                        hiddenMembers.removeView(cardView);  // Remove the card from the view
//                                        Toast.makeText(AdminGroup.this, user.getFirstName() + " has been removed.", Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        Toast.makeText(AdminGroup.this, "Failed to remove " + user.getFirstName(), Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<Void> call2, Throwable t) {
//                                    Toast.makeText(AdminGroup.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        });
//
//                        hiddenMembers.addView(cardView);  // Add the user view to the hiddenMembers layout
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//                Toast.makeText(AdminGroup.this, "Error loading members: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    // Load and display group join requests for the selected group
//    private void loadGroupRequests(long groupId) {
//        Call<List<User>> call = userGroupApi.getUsersForGroup(groupId, "requested");
//        call.enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                hiddenRequests.removeAllViews();  // Clear previous views
//
//                List<User> requests = response.body();
//                if (requests == null || requests.isEmpty()) {
//                    Toast.makeText(AdminGroup.this, "There are no join requests for this group.", Toast.LENGTH_SHORT).show();
//                } else {
//                    for (User user : requests) {
//                        View cardView = LayoutInflater.from(AdminGroup.this)
//                                .inflate(R.layout.user_request_card, hiddenRequests, false);
//
//                        TextView firstNameLastName = cardView.findViewById(R.id.firstNameLastName);
//                        TextView phoneNumber = cardView.findViewById(R.id.phoneNumber);
//                        TextView gender = cardView.findViewById(R.id.gender);
//                        TextView fitnessLevel = cardView.findViewById(R.id.fitnessLevel);
//                        Button confirmButton = cardView.findViewById(R.id.confirmButton);
//                        Button declineButton = cardView.findViewById(R.id.removeButton);
//
//                        // Set the user's data
//                        firstNameLastName.setText(user.getFirstName() + " " + user.getLastName());
//                        phoneNumber.setText("Phone: " + user.getPhoneNumber());
//                        gender.setText("Gender: " + user.getGender());
//                        fitnessLevel.setText("Fitness Level: " + user.getFitnessLevel());
//
//                        // Handle the Confirm button click
//                        confirmButton.setOnClickListener(v -> {
//                            // Confirm the user by changing the status to "joined"
//                            Call<Void> call3 = userGroupApi.updateUserStatusInGroup(user.getId(), groupId, "joined");
//                            call3.enqueue(new Callback<Void>() {
//                                @Override
//                                public void onResponse(Call<Void> call3, Response<Void> response) {
//                                    if (response.isSuccessful()) {
//                                        hiddenRequests.removeView(cardView);  // Remove the card from the view
//                                        Toast.makeText(AdminGroup.this, user.getFirstName() + " has been confirmed.", Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        Toast.makeText(AdminGroup.this, "Failed to confirm " + user.getFirstName(), Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<Void> call3, Throwable t) {
//                                    Toast.makeText(AdminGroup.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        });
//
//                        // Handle the Decline button click
//                        declineButton.setOnClickListener(v -> {
//                            // Decline the user by changing the status to "declined"
//                            Call<Void> call4 = userGroupApi.updateUserStatusInGroup(user.getId(), groupId, "declined");
//                            call4.enqueue(new Callback<Void>() {
//                                @Override
//                                public void onResponse(Call<Void> call4, Response<Void> response) {
//                                    if (response.isSuccessful()) {
//                                        hiddenRequests.removeView(cardView);  // Remove the card from the view
//                                        Toast.makeText(AdminGroup.this, user.getFirstName() + " has been declined.", Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        Toast.makeText(AdminGroup.this, "Failed to decline " + user.getFirstName(), Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<Void> call4, Throwable t) {
//                                    Toast.makeText(AdminGroup.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        });
//
//                        hiddenRequests.addView(cardView);  // Add the request view to the hiddenRequests layout
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//                Toast.makeText(AdminGroup.this, "Error loading requests: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}