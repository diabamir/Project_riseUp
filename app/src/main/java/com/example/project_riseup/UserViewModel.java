package com.example.project_riseup;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    private final LiveData<List<User>> allUsers;

    // ViewModel constructor
    public UserViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
        allUsers = userRepository.getAllUsers();
    }

    // Get all users
    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    // Insert a new user
    public void insertUser(User newUser) {
        userRepository.insertUser(newUser);
    }

    // Get a user by phone
    public LiveData<User> getUserByPhone(String phone) {
        return userRepository.getUserByPhone(phone);
    }

    // Get the first user (used for offline mode)
    public LiveData<User> getFirstUser() {
        return userRepository.getFirstUser(); // This should return a user from Room
    }
}
