package com.example.project_riseup;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UserRepository {
    private final UserDao userDao;  // DAO to interact with User table

    // Constructor initializes the database and DAO
    public UserRepository(Context context) {
        UserDatabase db = UserDatabase.getInstance(context);  // Get the singleton instance of the database
        userDao = db.userDao();  // Initialize the DAO
    }

    // Insert a user into the database
    public void insertUser(User user) {
        // Run this on a background thread (best done in ViewModel)
        new Thread(() -> userDao.insertUser(user)).start();
    }

    // Retrieve all users from the database
    public LiveData<List<User>> getAllUsers() {
        return userDao.getAllUsers();
    }

    // Retrieve a user by phone number
    public LiveData<User> getUserByPhone(String phone) {
        return userDao.getUserByPhone(phone);
    }

    // Optionally, add more database operations like delete, update, etc.
}
