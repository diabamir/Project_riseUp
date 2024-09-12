//package com.example.project_riseup;
//
//import android.app.Application;
//import android.widget.Toast;
//
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//public class GroupViewModel extends AndroidViewModel {
//
//    private MutableLiveData<List<Group>> allGroupsForAdmin = new MutableLiveData<>();
//    private MutableLiveData<List<User>> pendingJoinRequests = new MutableLiveData<>();
//    private MutableLiveData<List<User>> groupMembers = new MutableLiveData<>();
//    private LiveData<List<Group>> allGroups;
//
//    // Simulating data (You can replace this with database calls)
//    private List<Group> adminGroups = new ArrayList<>();
//    private List<User> allUsers = new ArrayList<>();
//    private List<User> group1Requests = new ArrayList<>();
//    private List<User> group1Members = new ArrayList<>();
//
//    private groupDAO groupDao;
//
//    public GroupViewModel(Application application) {
//        super(application);
//
//        // Initialize the database and DAO
//        groupDataBase db = groupDataBase.getInstance(application);
//        groupDao = db.groupDao();
//        allGroups = groupDao.getAllGroups();
//    }
//
//    // Fetch all groups from the database
//    public LiveData<List<Group>> getAllGroups() {
//        return allGroups;
//    }
//
//    // Fetch groups where the user is admin
//    public LiveData<List<Group>> getAllGroupsForAdmin() {
//        allGroupsForAdmin.setValue(adminGroups);
//        return allGroupsForAdmin;
//    }
//
//    // Fetch pending join requests for a group
//    public LiveData<List<User>> getPendingJoinRequestsForGroup(long groupId) {
//        if (groupId == 1) {
//            pendingJoinRequests.setValue(group1Requests);
//        } else {
//            pendingJoinRequests.setValue(new ArrayList<>());  // Empty for other groups
//        }
//        return pendingJoinRequests;
//    }
//
//    // Fetch group members for a group
//    public LiveData<List<User>> getUsersForGroup(long groupId) {
//        if (groupId == 1) {
//            groupMembers.setValue(group1Members);
//        } else {
//            groupMembers.setValue(new ArrayList<>());
//        }
//        return groupMembers;
//    }
//
//    // Approve join request
//    public void approveJoinRequest(long userId, long groupId) {
//        if (groupId == 1) {
//            User user = findUserInRequests(userId, group1Requests);
//            if (user != null) {
//                group1Members.add(user);
//                group1Requests.remove(user);
//                pendingJoinRequests.setValue(group1Requests);
//                groupMembers.setValue(group1Members);
//
//                // Show confirmation Toast using getApplication()
//                Toast.makeText(getApplication(), user.getFirstName() + " has been confirmed.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    // Remove a user from the group
//    public void removeUserFromGroup(long userId, long groupId) {
//        if (groupId == 1) {
//            User user = findUserInMembers(userId, group1Members);
//            if (user != null) {
//                group1Members.remove(user);
//                groupMembers.setValue(group1Members);
//
//                // Show removal Toast using getApplication()
//                Toast.makeText(getApplication(), user.getFirstName() + " has been removed.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    // Fetch groups by location (from database)
//    public LiveData<List<Group>> getGroupsByLocation(String location) {
//        return groupDao.getGroupsByLocation(location);  // Make sure to use 'groupDao', not 'groupDAO'
//    }
//
//    // Helper to find a user in requests
//    private User findUserInRequests(long userId, List<User> requestList) {
//        for (User user : requestList) {
//            if (user.getId() == userId) {
//                return user;
//            }
//        }
//        return null;
//    }
//
//    // Helper to find a user in members
//    private User findUserInMembers(long userId, List<User> memberList) {
//        for (User user : memberList) {
//            if (user.getId() == userId) {
//                return user;
//            }
//        }
//        return null;
//    }
//
//    public LiveData<Group> getGroupById(int groupId) {
//        return groupDao.getGroupById(groupId);  // Adjust method name and return type
//    }
//
//}
package com.example.project_riseup;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupViewModel extends AndroidViewModel {

    private MutableLiveData<List<Group>> allGroups = new MutableLiveData<>();
    private MutableLiveData<List<Group>> groupsByLocation = new MutableLiveData<>();
    private MutableLiveData<Group> groupById = new MutableLiveData<>();
    private GroupApi groupApi;

    public GroupViewModel(Application application) {
        super(application);
        // Initialize the API client
        groupApi = ApiClient.getClient().create(GroupApi.class);
    }

    // Fetch all groups from the API
    public LiveData<List<Group>> getAllGroups() {
        groupApi.getAllGroups().enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                if (response.isSuccessful()) {
                    allGroups.setValue(response.body());
                } else {
                    Toast.makeText(getApplication(), "Failed to fetch groups", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                Toast.makeText(getApplication(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return allGroups;
    }

    // Fetch groups filtered by location
    public LiveData<List<Group>> getGroupsByLocation(String location) {
        groupApi.getGroupsByLocation(location).enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                if (response.isSuccessful()) {
                    groupsByLocation.setValue(response.body());
                } else {
                    Toast.makeText(getApplication(), "Failed to fetch groups by location", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                Toast.makeText(getApplication(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return groupsByLocation;
    }

    // Fetch a specific group by ID
    public LiveData<Group> getGroupById(int groupId) {
        groupApi.getGroupById(groupId).enqueue(new Callback<Group>() {
            @Override
            public void onResponse(Call<Group> call, Response<Group> response) {
                if (response.isSuccessful()) {
                    groupById.setValue(response.body());
                } else {
                    Toast.makeText(getApplication(), "Failed to fetch group by ID", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Group> call, Throwable t) {
                Toast.makeText(getApplication(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return groupById;
    }
}