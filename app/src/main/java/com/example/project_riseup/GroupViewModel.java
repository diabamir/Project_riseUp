package com.example.project_riseup;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Date;
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
