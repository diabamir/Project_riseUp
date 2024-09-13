package com.example.project_riseup;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private final UserDao userDao;  // DAO to interact with User table
    private final ExecutorService executorService;  // Executor for background tasks

    // Constructor initializes the database, DAO, and ExecutorService
    public UserRepository(Context context) {
        UserDatabase db = UserDatabase.getInstance(context);  // Get the singleton instance of the database
        userDao = db.userDao();  // Initialize the DAO
        executorService = Executors.newSingleThreadExecutor();  // Create a single-threaded executor
    }

    // Insert a user into the database (runs on a background thread)
    //Raghad: updated the return value
    public long insertUser(User user) {
        executorService.execute(() -> userDao.insertUser(user));
        return user.id;  // Use executor instead of a raw thread
    }

    public User getUserByFirstName(String firstName) {
        return userDao.getUserByFirstName(firstName);
    }

    // Retrieve all users from the database
    public LiveData<List<User>> getAllUsers() {
        return userDao.getAllUsers();
    }

    // Retrieve a user by phone number
    public LiveData<User> getUserByPhone(String phone) {
        return userDao.getUserByPhone(phone);
    }

    // Retrieve the first user (for offline mode or single-user apps)
    public LiveData<User> getFirstUser() {
        return userDao.getFirstUser();  // Assumes the DAO has a method to get the first user
    }

    // Optionally, add more database operations like delete, update, etc.
    public void updateUser(User user) {
        executorService.execute(() -> userDao.updateUser(user));
    }

    // Delete a user from the database
    public void deleteUser(User user) {
        executorService.execute(() -> userDao.deleteUser(user));
    }

    //Raghad updates
    // Retrieve the user synchronously (without LiveData)
    public User getUserById(long userId) {
        return userDao.getUserById(userId);
    }
}
