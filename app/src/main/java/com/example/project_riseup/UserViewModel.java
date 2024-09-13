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
    //Raghad:
    // **Update user method**
    public void updateUser(User user) {
        userRepository.updateUser(user);
    }
    // Get all users
    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    // Insert a new user
    //Raghad: update return val
    public long insertUser(User newUser) {
        userRepository.insertUser(newUser);
        return newUser.getId();
    }

    // Get a user by phone
    public LiveData<User> getUserByPhone(String phone) {
        return userRepository.getUserByPhone(phone);
    }

    // Get the first user (used for offline mode)
    public LiveData<User> getFirstUser() {
        return userRepository.getFirstUser(); // This should return a user from Room
    }
    public User getUserByFirstname(String firstName){
        return userRepository.getUserByFirstName(firstName);
    }
    // Get a user by ID synchronously,added by Raghad
    public User getUserById(long userId) {
        return userRepository.getUserById(userId);
    }
}
