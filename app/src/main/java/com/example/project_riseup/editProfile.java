package com.example.project_riseup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;

public class editProfile extends AppCompatActivity {

    private EditText editFname, editLname, editPhone, newPassword, confirmPassword, editweight, editheight;
    private MaterialButton saveButton, cancelButton;
    private UserViewModel userViewModel;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize fields and buttons
        editFname = findViewById(R.id.editname1);
        editLname = findViewById(R.id.editLname1);
        editPhone = findViewById(R.id.editphone1);
        newPassword = findViewById(R.id.Newpassword1);
        confirmPassword = findViewById(R.id.confirmpass1);
        editweight = findViewById(R.id.editweight1);
        editheight = findViewById(R.id.editheight1);
        saveButton = findViewById(R.id.saveButton1);
        cancelButton = findViewById(R.id.cancelButton1);

        // Initialize ViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Fetch user data by ID (passed via Intent)
        long userId = getIntent().getLongExtra("USER_ID", -1);
        if (userId != -1) {
            new Thread(() -> {
                currentUser = userViewModel.getUserById(userId);
                runOnUiThread(this::populateFields);
            }).start();
        } else {
            Toast.makeText(this, "User ID not found!", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Save changes when the save button is clicked
        saveButton.setOnClickListener(v -> {
            if (validateInputs()) {
                updateUserProfile();  // Update the user profile using ViewModel
            }
        });

        // Cancel the profile editing and return to the previous screen
        cancelButton.setOnClickListener(v -> navigateToProfile());
    }

    // Populate fields with user details when data is fetched
    private void populateFields() {
        if (currentUser != null) {
            editFname.setText(currentUser.getFirstName());
            editLname.setText(currentUser.getLastName());
            editPhone.setText(currentUser.getPhoneNumber());
            editweight.setText(String.format("%.2f", currentUser.getWeight()));
            editheight.setText(String.format("%.2f", currentUser.getHeight()));
        }
    }

    // Validate input fields
    private boolean validateInputs() {
        String fname = editFname.getText().toString().trim();
        String lname = editLname.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();
        String password = newPassword.getText().toString().trim();
        String confirmPass = confirmPassword.getText().toString().trim();
        String weight = editweight.getText().toString().trim();
        String height = editheight.getText().toString().trim();

        if (TextUtils.isEmpty(fname) && TextUtils.isEmpty(lname) && TextUtils.isEmpty(phone) &&
                TextUtils.isEmpty(password) && TextUtils.isEmpty(weight) && TextUtils.isEmpty(height)) {
            Toast.makeText(this, "No changes made.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate phone number (assuming 10 digits are valid)
        if (!TextUtils.isEmpty(phone) && phone.length() != 10) {
            Toast.makeText(this, "Phone number must be 10 digits.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate passwords if provided
        if (!TextUtils.isEmpty(password)) {
            if (!password.equals(confirmPass)) {
                Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    // Update the user profile using Room
    private void updateUserProfile() {
        String fname = editFname.getText().toString().trim();
        String lname = editLname.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();
        String password = newPassword.getText().toString().trim();
        String weight = editweight.getText().toString().trim();
        String height = editheight.getText().toString().trim();

        // Update fields if they are not empty
        if (!TextUtils.isEmpty(fname)) currentUser.setFirstName(fname);
        if (!TextUtils.isEmpty(lname)) currentUser.setLastName(lname);
        if (!TextUtils.isEmpty(phone)) currentUser.setPhoneNumber(phone);
        if (!TextUtils.isEmpty(password)) currentUser.setPassword(password);
        if (!TextUtils.isEmpty(weight)) currentUser.setWeight(Double.parseDouble(weight));
        if (!TextUtils.isEmpty(height)) currentUser.setHeight(Double.parseDouble(height));

        // Update the user in the Room database
        new Thread(() -> {
            userViewModel.updateUser(currentUser);
            runOnUiThread(() -> {
                Toast.makeText(editProfile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                navigateToProfile();
            });
        }).start();
    }

    // Navigate back to the profile screen after updating or canceling
    private void navigateToProfile() {
        Intent intent = new Intent(editProfile.this, Profile.class);
        intent.putExtra("USER_ID", currentUser.getId());  // Pass the user ID to Profile activity
        startActivity(intent);
        finish();  // Optionally finish this activity
    }
}
//package com.example.project_riseup;
//
//import android.content.Context;
//import android.content.Intent;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.ViewModelProvider;
//
//import com.google.android.material.button.MaterialButton;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class editProfile extends AppCompatActivity {
//
//    private EditText editFname, editLname, editPhone, newPassword, confirmPassword, editweight, editheight;
//    private MaterialButton saveButton, cancelButton;
//    private UserApi userApi;
//    private User currentUser;
//    private UserViewModel userViewModel;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_profile);
//
//        // Initialize fields and buttons
//        editFname = findViewById(R.id.editname);
//        editLname = findViewById(R.id.editLname1);
//        editPhone = findViewById(R.id.editphone1);
//        newPassword = findViewById(R.id.Newpassword1);
//        confirmPassword = findViewById(R.id.confirmpass1);
//        editweight = findViewById(R.id.editweight1);
//        editheight = findViewById(R.id.editheight1);
//        saveButton = findViewById(R.id.saveButton1);
//        cancelButton = findViewById(R.id.cancelButton1);
//
//        // Initialize Retrofit and ViewModel
//        userApi = ApiClient.getClient().create(UserApi.class);
//        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
//
//        // Check network connectivity and fetch user data accordingly
//        if (isNetworkAvailable()) {
//            long userId = getIntent().getLongExtra("USER_ID", -1);
//            fetchUserDataFromServer(userId); // Fetch from server
//        } else {
//            fetchUserDataFromRoom(); // Fetch from Room (offline)
//        }
//
//        // Save changes when the save button is clicked
//        saveButton.setOnClickListener(v -> {
//            if (validateInputs()) {
//                long userId = currentUser != null ? currentUser.getId() : -1;
//                if (userId != -1) {
//                    updateUserProfile(userId); // Run the update
//                }
//            }
//        });
//
//        // Cancel the profile editing and return to the previous screen
//        cancelButton.setOnClickListener(v -> {
//            navigateToProfile();  // Simply return to profile screen
//        });
//    }
//
//    // Check if the device has an active internet connection
//    private boolean isNetworkAvailable() {
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }
//
//    // Fetch user data from the server
//    private void fetchUserDataFromServer(long userId) {
//        Call<User> call = userApi.getUserById(userId);
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                if (response.isSuccessful()) {
//                    currentUser = response.body();
//                    populateFields();
//                } else {
//                    Toast.makeText(editProfile.this, "Failed to load user data from server", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Toast.makeText(editProfile.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    // Fetch user data from the local Room database (offline mode)
//    private void fetchUserDataFromRoom() {
//        LiveData<User> userLiveData = userViewModel.getFirstUser();
//        userLiveData.observe(this, user -> {
//            if (user != null) {
//                currentUser = user;
//                populateFields();
//            } else {
//                Toast.makeText(editProfile.this, "No user data available offline", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    // Populate fields with user details when data is fetched
//    private void populateFields() {
//        if (currentUser != null) {
//            editFname.setText(currentUser.getFirstName());
//            editLname.setText(currentUser.getLastName());
//            editPhone.setText(String.valueOf(currentUser.getPhoneNumber()));
//            editweight.setText(String.valueOf(currentUser.getWeight()));
//            editheight.setText(String.valueOf(currentUser.getHeight()));
//        }
//    }
//
//    // Validate input fields
//    private boolean validateInputs() {
//        String fname = editFname.getText().toString().trim();
//        String lname = editLname.getText().toString().trim();
//        String phone = editPhone.getText().toString().trim();
//        String password = newPassword.getText().toString().trim();
//        String confirmPass = confirmPassword.getText().toString().trim();
//        String weight = editweight.getText().toString().trim();
//        String height = editheight.getText().toString().trim();
//
//        // No need to fill out all fields, let the user cancel if they want
//        if (TextUtils.isEmpty(fname) && TextUtils.isEmpty(lname) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(password) && TextUtils.isEmpty(weight) && TextUtils.isEmpty(height)) {
//            Toast.makeText(this, "No changes made.", Toast.LENGTH_SHORT).show();
//            return false;  // Prevent saving if nothing was changed
//        }
//
//        // Validate phone number (assuming 10 digits are valid)
//        if (!TextUtils.isEmpty(phone) && phone.length() != 10) {
//            Toast.makeText(this, "Phone number must be 10 digits.", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        // Validate passwords if provided
//        if (!TextUtils.isEmpty(password)) {
//            if (!password.equals(confirmPass)) {
//                Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    // Update the user profile on the server
//    private void updateUserProfile(long userId) {
//        String fname = editFname.getText().toString().trim();
//        String lname = editLname.getText().toString().trim();
//        String phone = editPhone.getText().toString().trim();
//        String password = newPassword.getText().toString().trim();
//        String weight = editweight.getText().toString().trim();
//        String height = editheight.getText().toString().trim();
//
//        // Update fields if they are not empty
//        if (!TextUtils.isEmpty(fname)) currentUser.setFirstName(fname);
//        if (!TextUtils.isEmpty(lname)) currentUser.setLastName(lname);
//        if (!TextUtils.isEmpty(phone)) currentUser.setPhoneNumber(phone);
//        if (!TextUtils.isEmpty(password)) currentUser.setPassword(password);
//        if (!TextUtils.isEmpty(weight)) currentUser.setWeight(Double.parseDouble(weight));
//        if (!TextUtils.isEmpty(height)) currentUser.setHeight(Double.parseDouble(height));
//
//        // Send updated user data to the server
//        Call<User> call = userApi.updateUser(userId, currentUser);
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(editProfile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
//                    navigateToProfile();
//                } else {
//                    Toast.makeText(editProfile.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Toast.makeText(editProfile.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    // Navigate back to the profile screen after updating or canceling
//    private void navigateToProfile() {
//        Intent intent = new Intent(editProfile.this, Profile.class);
//        startActivity(intent);
//    }
//}
