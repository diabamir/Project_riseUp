package com.example.project_riseup;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository userRepository;  // Make sure userRepository is initialized
    private final LiveData<List<User>> allUsers;

    // ViewModel constructor
    public UserViewModel(Application application) {
        super(application);
        // Initialize UserRepository
        userRepository = new UserRepository(application);
        allUsers = userRepository.getAllUsers();  // Get all users from the repository
    }

    // Method to get all users (returns LiveData)
    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    // Method to insert a new user
    public void insertUser(User newUser) {
        userRepository.insertUser(newUser);  // Delegate to the repository
    }

    // Method to fetch a user by phone number
    public LiveData<User> getUserByPhone(String phone) {
        return userRepository.getUserByPhone(phone);  // Delegate to the repository
    }
}
