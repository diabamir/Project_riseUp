package com.example.project_riseup;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;

    public UserViewModel(Application application) {
        super(application);
        UserDatabase db = UserDatabase.getInstance(application);
        userDao = db.userDao();
        allUsers = userDao.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public void insertUser(User newUser) {
        // Use a background thread for database operations
        new Thread(() -> {
            userDao.insertUser(newUser);
        }).start();

    }
}

