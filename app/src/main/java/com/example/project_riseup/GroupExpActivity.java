//package com.example.project_riseup;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import java.util.concurrent.Executors;
//import android.os.Bundle;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.ImageView;
//public class GroupExpActivity extends AppCompatActivity {
//    private Button nextButton;
//    private int userId;
//    private UserApi userApi;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_group_exp);
//
//
//        Intent intent = getIntent();
//        userId = intent.getIntExtra("USER_ID", -1); // Default to -1 if no ID is found
//
//        userApi = ApiClient.getClient().create(UserApi.class);
//        // Set up the button and its click listener
//        nextButton = findViewById(R.id.next_button);
//        nextButton.setOnClickListener(v -> {
//            if (userId != -1) {
////                 Update the seen attribute of the user in a background thread
//                Executors.newSingleThreadExecutor().execute(() -> {
//                    updateUserSeeInstructions(userId);
////                    User user = UserDatabase.getInstance(this).userDao().getUserById(userId);
////                    if (user != null) {
////                        user.setSeeTheInstructions(true);
////                        UserDatabase.getInstance(this).userDao().updateUser(user);
////
////                        // Start the new activity and pass the userId
////                        Intent nextIntent = new Intent(GroupExpActivity.this, ViewGroupsActivity.class);
//////                        nextIntent.putExtra("USER_ID", userId);
////                        startActivity(nextIntent);
////                        // Optional: finish the current activity
////                    }
//                });
//            }
//        });
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
//
//
//    private void updateUserSeeInstructions(int userId) {
//        Call<User> call = userApi.getUserById(userId);
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                if (response.isSuccessful()) {
//                    User user = response.body();
//                    if (user != null) {
//                        // Update the seeTheInstructions field to true
//                        user.setSeeTheInstructions(true);
//
//                        // Now, make an API call to update the user
//                        Call<User> updateCall = userApi.updateUser(userId, user); // Change here
//                        updateCall.enqueue(new Callback<User>() {
//                            @Override
//                            public void onResponse(Call<User> call, Response<User> response) {
//                                if (response.isSuccessful()) {
//                                    // User updated successfully, navigate to ViewGroupsActivity
//                                    runOnUiThread(() -> {
//                                        Intent nextIntent = new Intent(GroupExpActivity.this, MapActivity.class);
//                                        nextIntent.putExtra("USER_ID", userId);
//                                        startActivity(nextIntent);
//                                        finish();  // Finish current activity
//                                    });
//                                } else {
//                                    // Handle failure in updating user
//                                    runOnUiThread(() -> Toast.makeText(GroupExpActivity.this, "Failed to update user", Toast.LENGTH_SHORT).show());
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<User> call, Throwable t) {
//                                // Handle API failure
//                                runOnUiThread(() -> Toast.makeText(GroupExpActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show());
//                            }
//                        });
//                    }
//                } else {
//                    // Handle failure in retrieving user
//                    runOnUiThread(() -> Toast.makeText(GroupExpActivity.this, "Failed to retrieve user", Toast.LENGTH_SHORT).show());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                // Handle API failure
//                runOnUiThread(() -> Toast.makeText(GroupExpActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show());
//            }
//        });
//    }
//
//}

package com.example.project_riseup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.Executors;

public class GroupExpActivity extends AppCompatActivity {
    private Button nextButton;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_group_exp);


        Intent intent = getIntent();
        userId = intent.getLongExtra("USER_ID", -1); // Default to -1 if no ID is found

        // Set up the button and its click listener
        nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(v -> {
            if (userId != -1) {
//                 Update the seen attribute of the user in a background thread
                Executors.newSingleThreadExecutor().execute(() -> {
                    User user = UserDatabase.getInstance(this).userDao().getUserById(userId);
                    if (user != null) {
                        user.setSeeTheInstructions(true);
                        UserDatabase.getInstance(this).userDao().updateUser(user);

                        // Start the new activity and pass the userId
                        Intent nextIntent = new Intent(GroupExpActivity.this, MapActivity.class);
                        nextIntent.putExtra("USER_ID", userId);
                        startActivity(nextIntent);
                        // Optional: finish the current activity
                    }
                });
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}