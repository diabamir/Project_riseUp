package com.example.project_riseup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class SignIn extends AppCompatActivity {
    Button signInButton;
    EditText passwordInput;
    EditText phoneInput;
    MaterialButton signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        signUpButton = findViewById(R.id.signUpButton);
        signInButton = findViewById(R.id.sign_in_button);
        passwordInput = findViewById(R.id.password_input);
        phoneInput = findViewById(R.id.phone_input);

        signUpButton.setOnClickListener(view -> {
            Intent intent = new Intent(SignIn.this, Signup.class);
            startActivity(intent);
        });


        signInButton.setOnClickListener(v -> {
            String password = passwordInput.getText().toString().trim();
            String phone = phoneInput.getText().toString().trim();

            if (password.isEmpty() || phone.isEmpty()) {
                if (password.isEmpty()) {
                    passwordInput.setError("Password cannot be empty");
                }
                if (phone.isEmpty()) {
                    phoneInput.setError("Phone number cannot be empty");
                }
            } else {
                if (password.length() < 8) {
                    passwordInput.setError("Password must be at least 8 characters long");
                } else if (phone.length() != 10) {
                    phoneInput.setError("Phone number must be 10 digits long");
                } else {
                    // Hardcoded user credentials, replace with actual validation later
                    if (phone.equals("user") && password.equals("user")) {
                        Toast.makeText(SignIn.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignIn.this, HomePage.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignIn.this, "Login Failed! Please sign up.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
