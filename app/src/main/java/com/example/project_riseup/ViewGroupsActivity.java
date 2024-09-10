package com.example.project_riseup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class ViewGroupsActivity extends AppCompatActivity implements OnJoinClickListener {

    private RecyclerView recyclerView;
    private GroupAdapter groupAdapter;
    private GroupViewModel groupViewModel;
    private ActivityResultLauncher<Intent> mapActivityLauncher;
    private boolean filterByLocation = false; // Flag to check if we need to filter by location
    private String locationToFilter = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_groups);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        groupAdapter = new GroupAdapter(this);
        recyclerView.setAdapter(groupAdapter);

        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);

        // Setup activity result launcher
        mapActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                String location = result.getData().getStringExtra("LOCATION");
                filterGroupsByLocation(location);
            }
        });

        // Retrieve Intent and check for location parameter
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("LOCATION")) {
            filterByLocation = true;
            locationToFilter = intent.getStringExtra("LOCATION");
            filterGroupsByLocation(locationToFilter);
        } else {
            // Observe all groups if no location filter is set
            groupViewModel.getAllGroups().observe(this, new Observer<List<Group>>() {
                @Override
                public void onChanged(List<Group> groups) {
                    groupAdapter.setGroups(groups);
                }
            });
        }

        Button btnAddGroup = findViewById(R.id.btnAddGroup);
        btnAddGroup.setOnClickListener(v -> startActivity(new Intent(ViewGroupsActivity.this, AddGroupActivity.class)));

        Button mapButton = findViewById(R.id.MapButtton);
        mapButton.setOnClickListener(v -> {
            Intent intentMap = new Intent(ViewGroupsActivity.this, MapActivity.class);
            mapActivityLauncher.launch(intentMap);
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

    @Override
    public void onJoinClick(int position) {
        Group group = groupAdapter.getGroupAtPosition(position);
        // Handle join click event
    }
}