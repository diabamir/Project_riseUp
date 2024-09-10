package com.example.project_riseup;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class Signup extends AppCompatActivity {


    private EditText firstNameField, lastNameField, phoneNumberField;
    private TextInputEditText passwordField, confirmPasswordField;
    private DatePicker birthDatePicker;
    private String gender;
    private String fitnessLevel;
    private TextView bmiResult;
    private double weight;
    private double height;
    private TextView txtWeight;
    private TextView txtHeight;
    private MyScaleViewWeight weightScale;
    private MyScaleViewHeight heightScale;
    private UserViewModel userViewModel;
    private User user;
    private Date birthDate;
    private int currentStep = 0;
    private ImageView arrowU;
    private ImageView arrowN;
    private ImageView arrowOver;
    private ImageView arrowO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        user = new User();

        setupStep1();
    }

    // Step 1: Collect basic information
    private void setupStep1() {
        firstNameField = findViewById(R.id.Fname);
        lastNameField = findViewById(R.id.Lname);
        phoneNumberField = findViewById(R.id.phoneNum);
        passwordField = findViewById(R.id.password_input);
        confirmPasswordField = findViewById(R.id.confirmPassword_input);
        birthDatePicker = findViewById(R.id.datePicker1);

        Button signUpButton = findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(v -> {
            if (validateStep1()) {
                collectStep1Data();
                loadNextStep();
            }
        });
    }

    private boolean validateStep1() {
        String password = passwordField.getText().toString();
        String confirmPassword = confirmPasswordField.getText().toString();
        String phoneText = phoneNumberField.getText().toString().trim();

        // Check if passwords match and are at least 8 characters long
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 8) {
            Toast.makeText(this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check if all fields are filled
        if (firstNameField.getText().toString().isEmpty() ||
                lastNameField.getText().toString().isEmpty() ||
                phoneText.isEmpty() || passwordField.getText().toString().isEmpty() || confirmPasswordField.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Phone validation
        if (!phoneValidate(phoneText)) {
            return false;
        }

        return true;
    }

    // Phone validation logic
    private boolean phoneValidate(String phoneText) {
        if (phoneText.isEmpty()) {
            phoneNumberField.setError("Field can't be empty");
            return false;
        } else if (phoneText.length() > 10) {
            phoneNumberField.setError("Phone number can't be more than 10 characters");
            return false;
        } else {
            phoneNumberField.setError(null); // Clear error
            return true;
        }
    }


    private void collectStep1Data() {
        int day = birthDatePicker.getDayOfMonth();
        int month = birthDatePicker.getMonth();
        int year = birthDatePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        birthDate = calendar.getTime();

        user.setFirstName(firstNameField.getText().toString());
        user.setLastName(lastNameField.getText().toString());
        user.setPhoneNumber(phoneNumberField.getText().toString());
        user.setPassword(passwordField.getText().toString());
        user.setBirthDate(birthDate);
    }

    // Step 2: Select Gender with Blur Effect
    private void setupStep2() {
        setContentView(R.layout.activity_gender);

        ImageView femaleImage = findViewById(R.id.imageView2);
        ImageView maleImage = findViewById(R.id.imageView3);
        Button continueButton = findViewById(R.id.continue_button);

        // Click listener for female selection
        femaleImage.setOnClickListener(view -> {
            gender = "Female";
            blurOtherImages(femaleImage, maleImage);  // Blur the other gender image
        });

        // Click listener for male selection
        maleImage.setOnClickListener(view -> {
            gender = "Male";
            blurOtherImages(maleImage, femaleImage);  // Blur the other gender image
        });

        continueButton.setOnClickListener(v -> {
            if (validateStep2()) {
                collectStep2Data();
                loadNextStep();
            }
        });
    }

    private boolean validateStep2() {
        if (gender == null) {
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void collectStep2Data() {
        if (gender != null) {
            user.setGender(gender);
            Toast.makeText(this, "Gender selected: " + gender, Toast.LENGTH_SHORT).show();
        }
    }

    // Method to blur the unselected image
    private void blurOtherImages(ImageView selectedImage, ImageView otherImage) {
        selectedImage.setAlpha(1.0f);  // Keep selected image fully visible
        otherImage.setAlpha(0.3f);     // Blur the other image
    }

    // Step 3: Collect Weight using the custom weight scale
    @SuppressLint("MissingInflatedId")
    private void setupStep3() {
        setContentView(R.layout.activity_my_scale_weight);

        txtWeight = findViewById(R.id.txt_weight);
        weightScale = findViewById(R.id.my_scale);
        weightScale.setUpdateListener(result -> {
            weight = result;
            txtWeight.setText(String.format("%.2f kg", weight));
        });

        Button continueButton = findViewById(R.id.continue_button);
        continueButton.setOnClickListener(v -> {
            if (validateStep3()) {
                collectStep3Data();
                loadNextStep();
            }
        });
    }

    private boolean validateStep3() {
        return weight > 0;
    }

    private void collectStep3Data() {
        user.setWeight(weight);
    }

    // Step 4: Collect Height using the custom height scale
    private void setupStep4() {
        setContentView(R.layout.activity_my_scale_height);

        txtHeight = findViewById(R.id.txt_height);
        heightScale = findViewById(R.id.my_scale);
        heightScale.setUpdateListener(result -> {
            height = result;
            txtHeight.setText(String.format("%.2f cm", height));
        });

        Button continueButton = findViewById(R.id.continue_button);
        continueButton.setOnClickListener(v -> {
            if (validateStep4()) {
                collectStep4Data();
                loadNextStep();
            }
        });
    }

    private boolean validateStep4() {
        return height > 0;
    }

    private void collectStep4Data() {
        user.setHeight(height);
    }

    // Step 5: Display BMI
    private void setupStep5() {
        setContentView(R.layout.activity_bmi);
        bmiResult = findViewById(R.id.bmiresult);
        arrowU = findViewById(R.id.arrowU);
        arrowN = findViewById(R.id.arrowN);
        arrowOver = findViewById(R.id.arrowOver);
        arrowO = findViewById(R.id.arrowO);
        calculateBMI();

        // Set the onClickListener for the continue button
        Button continueButton = findViewById(R.id.continue_button);
        continueButton.setOnClickListener(v -> loadNextStep());
    }

    private void calculateBMI() {
        if (height > 50) {
            double bmi = weight / ((height / 100) * (height / 100));
            bmiResult.setText(String.format("BMI: %.2f", bmi));

            arrowU.setVisibility(View.GONE);
            arrowN.setVisibility(View.GONE);
            arrowOver.setVisibility(View.GONE);
            arrowO.setVisibility(View.GONE);

            if (bmi <= 18.4) {
                arrowU.setVisibility(View.VISIBLE);
            } else if (bmi >= 18.5 && bmi <= 24.9) {
                arrowN.setVisibility(View.VISIBLE);
            } else if (bmi >= 25.0 && bmi <= 39.9) {
                arrowOver.setVisibility(View.VISIBLE);
            } else if (bmi >= 40.0) {
                arrowO.setVisibility(View.VISIBLE);
            }
        } else {
            Toast.makeText(this, "Height is too small to calculate BMI", Toast.LENGTH_SHORT).show();
        }
    }

    // Step 6: Collect Fitness Level and blur other cards
    private void setupStep6() {
        setContentView(R.layout.activity_fitness_level);

        View beginnerCard = findViewById(R.id.Beginner_card);
        View intermediateCard = findViewById(R.id.Intermediate_card);
        View advancedCard = findViewById(R.id.Advanced_card);

        beginnerCard.setOnClickListener(view -> {
            fitnessLevel = "Beginner";
            blurOtherCards(beginnerCard, intermediateCard, advancedCard);
            completeSignUp();
        });

        intermediateCard.setOnClickListener(view -> {
            fitnessLevel = "Intermediate";
            blurOtherCards(intermediateCard, beginnerCard, advancedCard);
            completeSignUp();
        });

        advancedCard.setOnClickListener(view -> {
            fitnessLevel = "Advanced";
            blurOtherCards(advancedCard, beginnerCard, intermediateCard);
            completeSignUp();
        });
    }

    // Method to blur other cards
    private void blurOtherCards(View selectedCard, View card1, View card2) {
        selectedCard.setAlpha(1.0f); // Make the selected card fully visible
        card1.setAlpha(0.3f); // Blur card 1
        card2.setAlpha(0.3f); // Blur card 2
    }

    private void completeSignUp() {
        user.setFitnessLevel(fitnessLevel);
        userViewModel.insertUser(user);
        Toast.makeText(this, "You signed up successfully!", Toast.LENGTH_SHORT).show();
    }

    private void loadNextStep() {
        currentStep++;
        switch (currentStep) {
            case 1:
                setupStep2();
                break;
            case 2:
                setupStep3();
                break;
            case 3:
                setupStep4();
                break;
            case 4:
                setupStep5();
                break;
            case 5:
                setupStep6();
                break;
        }
    }
}
