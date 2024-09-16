package com.example.project_riseup;

//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class ViewGroupsActivity extends AppCompatActivity implements OnJoinClickListener {
//
//    private RecyclerView recyclerView;
//    private GroupAdapter groupAdapter;
//    private GroupViewModel groupViewModel;
//    private ActivityResultLauncher<Intent> mapActivityLauncher;
//    private String locationToFilter = "";
//
//    private long userId;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_groups);
//
//        Button btnAddGroup = findViewById(R.id.btnAddGroup);
//        Button btnForMap = findViewById(R.id.btnForMap);
//
//        btnForMap.setOnClickListener(v -> startActivity(new Intent(ViewGroupsActivity.this, MapActivity.class)));
//
//        btnAddGroup.setOnClickListener(v -> {
//            Intent intent = new Intent(ViewGroupsActivity.this, FavActivites.class);
//            intent.putExtra("LOCATION", locationToFilter);
//            intent.putExtra("USER_ID", userId);
//            startActivity(intent);
//        });
//
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        groupAdapter = new GroupAdapter(this);
//        recyclerView.setAdapter(groupAdapter);
//
//        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
//
//        mapActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
//                String location = result.getData().getStringExtra("LOCATION");
//                filterGroupsByLocation(location);
//            }
//        });
//
//        Intent intent = getIntent();
//        if (intent != null) {
//            if (intent.hasExtra("LOCATION")) {
//                locationToFilter = intent.getStringExtra("LOCATION");
//                filterGroupsByLocation(locationToFilter);
//            } if (intent.hasExtra("GROUP_ID")) {
//                int groupId = intent.getIntExtra("GROUP_ID", -1);
//                if (groupId != -1) {
//                    filterGroupsById(groupId);
//                    btnAddGroup.setVisibility(View.GONE);
//                } else {
//                    Toast.makeText(this, "Invalid Group ID", Toast.LENGTH_SHORT).show();
//                }
//            }
//             if (intent.hasExtra("USER_ID")) {
//                userId = intent.getLongExtra("USER_ID", -1);
//                if (userId == -1)  {
//                    Toast.makeText(this, "Invalid User ID", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            else {
//                observeAllGroups();
//            }
//        } else {
//            observeAllGroups();
//        }
//    }
//
//    private void observeAllGroups() {
//        groupViewModel.getAllGroups().observe(this, new Observer<List<Group>>() {
//            @Override
//            public void onChanged(List<Group> groups) {
//                if (groups == null || groups.isEmpty()) {
//                    Toast.makeText(ViewGroupsActivity.this, "There are no groups yet :(", Toast.LENGTH_SHORT).show();
//                    groupAdapter.setGroups(new ArrayList<>());
//                } else {
//                    groupAdapter.setGroups(groups);
//                }
//            }
//        });
//    }
//
//    private void filterGroupsByLocation(String location) {
//        groupViewModel.getGroupsByLocation(location).observe(this, new Observer<List<Group>>() {
//            @Override
//            public void onChanged(List<Group> groups) {
//                groupAdapter.setGroups(groups);
//            }
//        });
//    }
//
//    private void filterGroupsById(int groupId) {
//        groupViewModel.getGroupById(groupId).observe(this, new Observer<Group>() {
//            @Override
//            public void onChanged(Group group) {
//                if (group != null) {
//                    List<Group> groups = new ArrayList<>();
//                    groups.add(group);
//                    groupAdapter.setGroups(groups);
//                } else {
//                    Toast.makeText(ViewGroupsActivity.this, "Group not found", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onJoinClick(int position) {
//        Group group = groupAdapter.getGroupAtPosition(position);
//        updateGroupStatus(group.getId(), "requested");
//    }
//
//    private void updateGroupStatus(long groupId, String status) {
//        UserGroupApi api = ApiClient.getClient().create(UserGroupApi.class);
//        long currentUserId= userId;
//        Call<Void> call = api.updateUserStatusInGroup(currentUserId, groupId, status);
//
//        call.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful()) {
//                    // Successfully updated status
//                    groupAdapter.notifyDataSetChanged(); // Refresh the adapter data if needed
//                } else {
//                    Toast.makeText(ViewGroupsActivity.this, "Failed to update status", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Toast.makeText(ViewGroupsActivity.this, "Network error", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//
//}
//package com.example.project_riseup;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class ViewGroupsActivity extends AppCompatActivity implements OnJoinClickListener {
//
//    private RecyclerView recyclerView;
//    private GroupAdapter groupAdapter;
//    private GroupViewModel groupViewModel;
//    private ActivityResultLauncher<Intent> mapActivityLauncher;
//    private String locationToFilter = "";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_groups);
//
//        Button btnAddGroup = findViewById(R.id.btnAddGroup);
//        Button btnForMap = findViewById(R.id.btnForMap);
//
//        btnForMap.setOnClickListener(v -> startActivity(new Intent(ViewGroupsActivity.this, MapActivity.class)));
//
//        btnAddGroup.setOnClickListener(v -> {
//            Intent intent = new Intent(ViewGroupsActivity.this, FavActivites.class);
//            intent.putExtra("LOCATION", locationToFilter);
//            startActivity(intent);
//        });
//
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        groupAdapter = new GroupAdapter(this);
//        recyclerView.setAdapter(groupAdapter);
//
//        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
//
//        mapActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
//                String location = result.getData().getStringExtra("LOCATION");
//                filterGroupsByLocation(location);
//            }
//        });
//
//        Intent intent = getIntent();
//        if (intent != null) {
//            if (intent.hasExtra("LOCATION")) {
//                locationToFilter = intent.getStringExtra("LOCATION");
//                filterGroupsByLocation(locationToFilter);
//            } else if (intent.hasExtra("GROUP_ID")) {
//                int groupId = intent.getIntExtra("GROUP_ID", -1);
//                if (groupId != -1) {
//                    filterGroupsById(groupId);
//                    btnAddGroup.setVisibility(View.GONE);
//                } else {
//                    Toast.makeText(this, "Invalid Group ID", Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                observeAllGroups();
//            }
//        } else {
//            observeAllGroups();
//        }
//    }
//
//    private void observeAllGroups() {
//        groupViewModel.getAllGroups().observe(this, new Observer<List<Group>>() {
//            @Override
//            public void onChanged(List<Group> groups) {
//                if (groups == null || groups.isEmpty()) {
//                    Toast.makeText(ViewGroupsActivity.this, "There are no groups yet :(", Toast.LENGTH_SHORT).show();
//                    groupAdapter.setGroups(new ArrayList<>());
//                } else {
//                    groupAdapter.setGroups(groups);
//                }
//            }
//        });
//    }
//
//    private void filterGroupsByLocation(String location) {
//        groupViewModel.getGroupsByLocation(location).observe(this, new Observer<List<Group>>() {
//            @Override
//            public void onChanged(List<Group> groups) {
//                groupAdapter.setGroups(groups);
//            }
//        });
//    }
//
//    private void filterGroupsById(int groupId) {
//        groupViewModel.getGroupById(groupId).observe(this, new Observer<Group>() {
//            @Override
//            public void onChanged(Group group) {
//                if (group != null) {
//                    List<Group> groups = new ArrayList<>();
//                    groups.add(group);
//                    groupAdapter.setGroups(groups);
//                } else {
//                    Toast.makeText(ViewGroupsActivity.this, "Group not found", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onJoinClick(int position) {
//        Group group = groupAdapter.getGroupAtPosition(position);
//        updateGroupStatus(group.getId(), "requested");
//    }
//
//    private void updateGroupStatus(long groupId, String status) {
//        UserGroupApi api = ApiClient.getClient().create(UserGroupApi.class);
//        long currentUserId=1;
//        Call<Void> call = api.updateUserStatusInGroup(currentUserId, groupId, status);
//
//        call.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful()) {
//                    // Successfully updated status
//                    groupAdapter.notifyDataSetChanged(); // Refresh the adapter data if needed
//                } else {
//                    Toast.makeText(ViewGroupsActivity.this, "Failed to update status", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Toast.makeText(ViewGroupsActivity.this, "Network error", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//
//}
//

//package com.example.project_riseup;



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
                filterGroupsByLocation(locationToFilter);
            }
            if (intent.hasExtra("USER_ID")) {
                userId = intent.getLongExtra("USER_ID", -1);
            }
            // Check if there is a GROUP_ID parameter
            if (intent.hasExtra("GROUP_ID")) {
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

    //    @Override
//    public void onJoinClick(int position) {
//        Group group = groupAdapter.getGroupAtPosition(position);
//        // Handle the join click event as needed
//    }
    @Override
    public void onJoinClick(int position) {
        Group group = groupAdapter.getGroupAtPosition(position);

        // Increase the joined member count
        int newCount = group.getHowManyJoin() + 1;
        group.setHowManyJoin(newCount);

        groupDataBase.getInstance(this).groupDao().updateGroup(group);

        // Create a new UserGroupJoin object
        UserGroupJoin userGroupJoin = new UserGroupJoin(userId, group.getId(), "joined");

        // Insert the UserGroupJoin object into the database
        UserGroupJoinDatabase.getInstance(this).UserGroupJoinDao().insert(userGroupJoin);

        // Update the UI to reflect the joined group
        groupAdapter.notifyItemChanged(position);

        // Provide feedback to the user
        Toast.makeText(ViewGroupsActivity.this, "You have joined the group!", Toast.LENGTH_SHORT).show();


    }

}