package com.example.project_riseup;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    private String locationToFilter = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_groups);

        Button btnAddGroup = findViewById(R.id.btnAddGroup);
//        btnAddGroup.setOnClickListener(v -> startActivity(new Intent(ViewGroupsActivity.this, FavActivites.class)));

        Button btnForMap = findViewById(R.id.btnForMap);
        btnForMap.setOnClickListener(v -> startActivity(new Intent(ViewGroupsActivity.this, MapActivity.class)));



        btnAddGroup.setOnClickListener(v -> {
            // Create an Intent to start FavActivites
            Intent intent = new Intent(ViewGroupsActivity.this, FavActivites.class);

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
                filterGroupsByLocation(locationToFilter);
            }
            // Check if there is a GROUP_ID parameter
            else if (intent.hasExtra("GROUP_ID")) {
                int groupId = intent.getIntExtra("GROUP_ID", -1); // Default to -1 if not found
                if (groupId != -1) {
                    filterGroupsById(groupId);
                    btnAddGroup.setVisibility(View.GONE);
                } else {
                    Toast.makeText(this, "Invalid Group ID", Toast.LENGTH_SHORT).show();
                }
            }
            // If no filters are set, observe all groups
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
        // Handle the join click event as needed
    }
}
