package com.example.project_riseup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewGroupsActivity extends AppCompatActivity implements OnJoinClickListener {

    private RecyclerView recyclerView;
    private GroupAdapter groupAdapter;
    private GroupViewModel groupViewModel;
    private ActivityResultLauncher<Intent> mapActivityLauncher;
    private String locationToFilter = "";

    private long userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_groups);

        Button btnAddGroup = findViewById(R.id.btnAddGroup);
        Button btnForMap = findViewById(R.id.btnForMap);

        btnForMap.setOnClickListener(v -> startActivity(new Intent(ViewGroupsActivity.this, MapActivity.class)));

        btnAddGroup.setOnClickListener(v -> {
            Intent intent = new Intent(ViewGroupsActivity.this, FavActivites.class);
            intent.putExtra("LOCATION", locationToFilter);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        groupAdapter = new GroupAdapter(this);
        recyclerView.setAdapter(groupAdapter);

        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);

        mapActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                String location = result.getData().getStringExtra("LOCATION");
                filterGroupsByLocation(location);
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("LOCATION")) {
                locationToFilter = intent.getStringExtra("LOCATION");
                filterGroupsByLocation(locationToFilter);
            } if (intent.hasExtra("GROUP_ID")) {
                int groupId = intent.getIntExtra("GROUP_ID", -1);
                if (groupId != -1) {
                    filterGroupsById(groupId);
                    btnAddGroup.setVisibility(View.GONE);
                } else {
                    Toast.makeText(this, "Invalid Group ID", Toast.LENGTH_SHORT).show();
                }
            }
            if (intent.hasExtra("USER_ID")) {
                userId = intent.getLongExtra("USER_ID", -1);
                if (userId == -1)  {
                    Toast.makeText(this, "Invalid User ID", Toast.LENGTH_SHORT).show();
                }
            }

            else {
                observeAllGroups();
            }
        } else {
            observeAllGroups();
        }
    }

    private void observeAllGroups() {
        groupViewModel.getAllGroups().observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(List<Group> groups) {
                if (groups == null || groups.isEmpty()) {
                    Toast.makeText(ViewGroupsActivity.this, "There are no groups yet :(", Toast.LENGTH_SHORT).show();
                    groupAdapter.setGroups(new ArrayList<>());
                } else {
                    List<Group> validGroups = new ArrayList<>();
                    for (Group group : groups) {
                        if (shouldDeleteGroup(group)) {
                            deleteGroup(group);  // Delete the group if conditions are met
                        } else {
                            validGroups.add(group);  // Add to the list if not deleted
                        }
                    }
                    groupAdapter.setGroups(groups);
                }
            }
        });
    }

    private boolean shouldDeleteGroup(Group group) {
        // Get current date and time
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        // Check if group's date matches current date
        if (group.date != null && isSameDay(group.date, currentDate)) {
            // Parse group's endTime
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            try {
                Date endTime = timeFormat.parse(group.endTime);
                // Check if current time is past the group's end time
                if (endTime != null && currentDate.after(endTime)) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date1).equals(dateFormat.format(date2));
    }



    private void deleteGroup(Group group) {
        GroupApi api = ApiClient.getClient().create(GroupApi.class);
        Call<Void> call = api.deleteGroup(group.getId());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ViewGroupsActivity.this, "Group deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ViewGroupsActivity.this, "Failed to delete group", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ViewGroupsActivity.this, "Network error", Toast.LENGTH_SHORT).show();
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
        updateGroupStatus(group.getId(), "requested");
    }

    private void updateGroupStatus(long groupId, String status) {
        UserGroupApi api = ApiClient.getClient().create(UserGroupApi.class);
        long currentUserId= userId;
        Call<Void> call = api.updateUserStatusInGroup(currentUserId, groupId, status);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Successfully updated status
                    groupAdapter.notifyDataSetChanged(); // Refresh the adapter data if needed
                } else {
                    Toast.makeText(ViewGroupsActivity.this, "Failed to update status", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ViewGroupsActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}