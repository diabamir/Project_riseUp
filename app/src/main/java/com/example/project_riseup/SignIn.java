package com.example.project_riseup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignIn extends AppCompatActivity {
    Button signInButton;
    EditText passwordInput;
    EditText phoneInput;
    MaterialButton signUpButton;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);

        // Initialize UserViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Apply system bars insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI elements
        signUpButton = findViewById(R.id.signUpButton);
        signInButton = findViewById(R.id.sign_in_button);
        passwordInput = findViewById(R.id.password_input);
        phoneInput = findViewById(R.id.phone_input);

        // Set up the Sign Up button
        signUpButton.setOnClickListener(view -> {
            Intent intent = new Intent(SignIn.this, Signup.class);
            startActivity(intent);
        });

        // Set up the Sign In button
        signInButton.setOnClickListener(v -> signInUser());
    }

    // Separate method for signing in the user
//    private void signInUser() {
//        String password = passwordInput.getText().toString().trim();
//        String phone = phoneInput.getText().toString().trim();
//
//        if (validateInputs(phone, password)) {
//            // Fetch user from the database using phone number
//            userViewModel.getUserByPhone(phone).observe(this, user -> {
//                if (user != null) {
//                    Log.d("SignIn", "User found: " + user.getUserName());  // Add logging to verify the user
//                    if (user.getPassword().equals(password)) {
//                        Toast.makeText(SignIn.this, "Login Successful!", Toast.LENGTH_SHORT).show();
//                        navigateToHomePage();
//                    } else {
//                        Toast.makeText(SignIn.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(SignIn.this, "User not found! Please sign up.", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        }
//    }
    //Raghad updates
    private void signInUser() {
        String password = passwordInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();

        if (validateInputs(phone, password)) {
            // Fetch user from the database using phone number
            userViewModel.getUserByPhone(phone).observe(this, user -> {
                if (user != null) {
                    Log.d("SignIn", "User found: " + user.getUserName());  // Add logging to verify the user
                    if (user.getPassword().equals(password)) {
                        Toast.makeText(SignIn.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                        // Navigate to HomePage and pass the user ID
                        navigateToHomePage(user.getId());
                    } else {
                        Toast.makeText(SignIn.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignIn.this, "User not found! Please sign up.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    //    // Navigate to HomePage after successful login
//    private void navigateToHomePage() {
//        Intent intent = new Intent(SignIn.this, HomePage.class);
//        startActivity(intent);
//    }
    //Raghad Updates
    private void navigateToHomePage(long userId) {
        // Save the user ID to SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("USER_ID", userId);
        editor.apply();  // Commit changes

        // Navigate to HomePage
        Intent intent = new Intent(SignIn.this, HomePage.class);
        startActivity(intent);
    }



    // Validate phone number and password
    private boolean validateInputs(String phone, String password) {
        boolean isValid = true;

        if (phone.isEmpty()) {
            phoneInput.setError("Phone number cannot be empty");
            isValid = false;
        } else if (phone.length() != 10) {
            phoneInput.setError("Phone number must be 10 digits long");
            isValid = false;
        }

        if (password.isEmpty()) {
            passwordInput.setError("Password cannot be empty");
            isValid = false;
        } else if (password.length() < 8) {
            passwordInput.setError("Password must be at least 8 characters long");
            isValid = false;
        }

        return isValid;
    }
}
