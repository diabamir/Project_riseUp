package com.example.project_riseup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.example.project_riseup.ApiClient;
import com.example.project_riseup.UserApi;
import com.example.project_riseup.User;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Signup extends AppCompatActivity {

    private EditText firstNameField, lastNameField, phoneNumberField;
    private TextInputEditText passwordField, confirmPasswordField;
    private DatePicker birthDatePicker;
    private String gender = ""; // Initialized to avoid NullPointerException
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
    private ImageView arrowU, arrowN, arrowOver, arrowO;
    private String selectedActivity;
    private TextView lastSelectedTextView;
    private int defaultColor;
    private int greenColor;
    private ImageView tealSquare, purpleSquare, cartWheeling, greenSquare;
    private ImageView weightLifting, blueSquare, yoga, darkOrange, swimming, lightYellowSquare, biking, pinkSquare, wrestling, skyBlue, handBall, limeGreen, climbing, orange, surfing, lightRed, golfing, bordeaux, waterPolo, lightPurple
            , oliveSquare, football, lightPink, basketball, lightBlue, rowing, horseRacing, navyBlue, bowling, yellow, pingpong, darkPink, volleyball, turkis, tennis, running;
    private TextView WeightliftingText, yogatext, Cartwheelingtext, RunningText, WaterPoloText, SurfingText, golfingText, ClimbingText, BasketballText, HorseRacingText, FootballText, RowingText, PingPongText,
            BowlingText, TennisText, Volleyball, WrestlingText, HandballText, SwimmingText, BikingText;
    UserApi userApi;
    UserDao userDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        user = new User();

        // Set default and green color resources
        defaultColor = getResources().getColor(android.R.color.black);
        greenColor = getResources().getColor(android.R.color.holo_green_dark);

        UserDatabase db = UserDatabase.getInstance(this);
        userDao = db.userDao();

        setupStep1();
        userApi = ApiClient.getClient().create(UserApi.class);
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

    // Validate Step 1
    private boolean validateStep1() {
        String phoneText = phoneNumberField.getText().toString().trim();
        String password = passwordField.getText().toString();
        String confirmPassword = confirmPasswordField.getText().toString();

        // Check if passwords match and are valid
        if (!passwordvalidate(password, confirmPassword)) {
            return false;
        }

        // Check if all fields are filled
        if (firstNameField.getText().toString().isEmpty() ||
                lastNameField.getText().toString().isEmpty() ||
                phoneText.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Phone validation
        return phoneValidate(phoneText);
    }

    // Password validation logic
    private boolean passwordvalidate(String password, String confirmPassword) {
        if (password.isEmpty()) {
            passwordField.setError("Field can't be empty");  // Show error and make frame red
            return false;
        } else if (password.length() < 8) {  // If password is less than 8 characters
            passwordField.setError("Password must be at least 8 characters long");
            return false;
        } else if (!password.equals(confirmPassword)) {  // Check if passwords match
            confirmPasswordField.setError("Passwords do not match");
            return false;
        } else {
            passwordField.setError(null);  // Clear error if password is valid
            confirmPasswordField.setError(null);
            return true;
        }
    }

    // Phone validation logic
    private boolean phoneValidate(String phoneText) {
        if (phoneText.isEmpty()) {
            phoneNumberField.setError("Field can't be empty");
            return false;
        } else if (!phoneText.matches("\\d+")) {
            phoneNumberField.setError("Phone number must be numeric");
            return false;
        } else if (phoneText.length() != 10) {  // Ensure exactly 10 digits for phone
            phoneNumberField.setError("Phone number must be exactly 10 digits");
            return false;
        } else {
            phoneNumberField.setError(null);
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

        femaleImage.setOnClickListener(view -> {
            gender = "Female";
            blurOtherImages(femaleImage, maleImage);
        });

        maleImage.setOnClickListener(view -> {
            gender = "Male";
            blurOtherImages(maleImage, femaleImage);
        });

        continueButton.setOnClickListener(v -> {
            if (validateStep2()) {
                collectStep2Data();
                loadNextStep();
            }
        });
    }

    private boolean validateStep2() {
        if (gender.isEmpty()) {
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void collectStep2Data() {
        user.setGender(gender);
        Toast.makeText(this, "Gender selected: " + gender, Toast.LENGTH_SHORT).show();
    }

    private void blurOtherImages(ImageView selectedImage, ImageView otherImage) {
        selectedImage.setAlpha(1.0f);
        otherImage.setAlpha(0.3f);
    }

    // Step 3: Collect Weight using custom weight scale
    private void setupStep3() {
        setContentView(R.layout.activity_my_scale_weight);

        txtWeight = findViewById(R.id.txt_weight);
        weightScale = findViewById(R.id.my_scale);
        weightScale.setUpdateListener(result -> {
            weight = Math.max(result, 40); // Ensures minimum weight is 40kg
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

    // Step 4: Collect Height using custom height scale
    private void setupStep4() {
        setContentView(R.layout.activity_my_scale_height);

        txtHeight = findViewById(R.id.txt_height);
        heightScale = findViewById(R.id.my_scale);
        heightScale.setUpdateListener(result -> {
            height = Math.max(result, 100); // Ensures minimum height is 100cm
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
            Toast.makeText(this, "Height too low, resetting to 100 cm", Toast.LENGTH_SHORT).show();
            height = 100; // Resets height to a valid minimum
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
        });

        intermediateCard.setOnClickListener(view -> {
            fitnessLevel = "Intermediate";
            blurOtherCards(intermediateCard, beginnerCard, advancedCard);
        });

        advancedCard.setOnClickListener(view -> {
            fitnessLevel = "Advanced";
            blurOtherCards(advancedCard, beginnerCard, intermediateCard);
        });

        Button continueButton = findViewById(R.id.Continue_button);
        continueButton.setOnClickListener(v -> {
            if (validateStep6()) {
                collectStep6Data();
                loadNextStep();  // Moves to step 7 after step 6
            }
        });
    }

    private void blurOtherCards(View selectedCard, View card1, View card2) {
        selectedCard.setAlpha(1.0f);
        card1.setAlpha(0.3f);
        card2.setAlpha(0.3f);
    }

    private boolean validateStep6() {
        if (fitnessLevel == null) {
            Toast.makeText(this, "Please select your fitness level", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void collectStep6Data() {
        user.setFitnessLevel(fitnessLevel);
    }

    // Step 7: Additional actions if needed
    private void setupStep7() {
        setContentView(R.layout.activity_fav_activites2); // Ensure this layout exists

        // Initialize views
        tealSquare = findViewById(R.id.tealSquare);
        purpleSquare = findViewById(R.id.purpleSquare);
        cartWheeling = findViewById(R.id.cartWheeling);
        greenSquare = findViewById(R.id.greenSquare);
        weightLifting = findViewById(R.id.weightLifting);
        blueSquare = findViewById(R.id.blueSquare);
        yoga = findViewById(R.id.yoga);
        darkOrange = findViewById(R.id.darkOrange);
        swimming = findViewById(R.id.swimming);
        lightYellowSquare = findViewById(R.id.lightYellowSquare);
        biking = findViewById(R.id.biking);
        pinkSquare = findViewById(R.id.pinkSquare);
        wrestling = findViewById(R.id.wrestiling);
        skyBlue = findViewById(R.id.skyBlue);
        handBall = findViewById(R.id.handBall);
        limeGreen = findViewById(R.id.limeGreen);
        climbing = findViewById(R.id.climbing);
        orange = findViewById(R.id.orange);
        surfing = findViewById(R.id.surfing);
        lightRed = findViewById(R.id.lightRed);
        golfing = findViewById(R.id.golfing);
        bordeaux = findViewById(R.id.bordeaux);
        waterPolo = findViewById(R.id.waterPolo);
        lightPurple = findViewById(R.id.lightPurple);
        oliveSquare = findViewById(R.id.oliveSquare);
        football = findViewById(R.id.football);
        lightPink = findViewById(R.id.lightPink);
        basketball = findViewById(R.id.basketball);
        lightBlue = findViewById(R.id.lightBlue);
        rowing = findViewById(R.id.rowing);
        horseRacing = findViewById(R.id.horseRacing);
        navyBlue = findViewById(R.id.navyBlue);
        bowling = findViewById(R.id.bowling);
        yellow = findViewById(R.id.yellow);
        pingpong = findViewById(R.id.pingpong);
        darkPink = findViewById(R.id.darkPink);
        volleyball = findViewById(R.id.vollyball);
        turkis = findViewById(R.id.turkis);
        tennis = findViewById(R.id.tennis);
        running = findViewById(R.id.running);

        WeightliftingText = findViewById(R.id.WeightliftingText);
        yogatext = findViewById(R.id.yogatext);
        Cartwheelingtext = findViewById(R.id.Cartwheelingtext);
        RunningText = findViewById(R.id.RunningText);
        WaterPoloText = findViewById(R.id.WaterPoloText);
        SurfingText = findViewById(R.id.SurfingText);
        golfingText = findViewById(R.id.golfingText);
        ClimbingText = findViewById(R.id.ClimbingText);
        BasketballText = findViewById(R.id.BasketballText);
        HorseRacingText = findViewById(R.id.HorseRacingText);
        FootballText = findViewById(R.id.FootballText);
        RowingText = findViewById(R.id.RowingText);
        PingPongText = findViewById(R.id.PingPongText);
        BowlingText = findViewById(R.id.BowlingText);
        TennisText = findViewById(R.id.TennisText);
        Volleyball = findViewById(R.id.Volleyball);
        WrestlingText = findViewById(R.id.WrestlingText);
        HandballText = findViewById(R.id.HandballText);
        SwimmingText = findViewById(R.id.SwimmingText);
        BikingText = findViewById(R.id.BikingText);

        // Initialize user object
        user.setFavActivities(new ArrayList<>()); // Initialize the list of favorite activities in the User object

        // Call toggleActivity for each activity selection
        tealSquare.setOnClickListener(view -> toggleActivity(tealSquare, WeightliftingText, defaultColor, greenColor, "weightLifting"));
        weightLifting.setOnClickListener(view -> toggleActivity(weightLifting, WeightliftingText, defaultColor, greenColor, "weightLifting"));
        yoga.setOnClickListener(view -> toggleActivity(yoga, yogatext, defaultColor, greenColor, "yoga"));
        blueSquare.setOnClickListener(view -> toggleActivity(blueSquare, yogatext, defaultColor, greenColor, "yoga"));
        purpleSquare.setOnClickListener(view -> toggleActivity(purpleSquare, Cartwheelingtext, defaultColor, greenColor, "cartWheeling"));
        cartWheeling.setOnClickListener(view -> toggleActivity(cartWheeling, Cartwheelingtext, defaultColor, greenColor, "cartWheeling"));
        greenSquare.setOnClickListener(view -> toggleActivity(greenSquare, RunningText, defaultColor, greenColor, "running"));
        running.setOnClickListener(view -> toggleActivity(running, RunningText, defaultColor, greenColor, "running"));
        swimming.setOnClickListener(view -> toggleActivity(swimming, SwimmingText, defaultColor, greenColor, "swimming"));
        darkOrange.setOnClickListener(view -> toggleActivity(darkOrange, SwimmingText, defaultColor, greenColor, "swimming"));
        lightYellowSquare.setOnClickListener(view -> toggleActivity(lightYellowSquare, BikingText, defaultColor, greenColor, "biking"));
        biking.setOnClickListener(view -> toggleActivity(biking, BikingText, defaultColor, greenColor, "biking"));
        pinkSquare.setOnClickListener(view -> toggleActivity(pinkSquare, WrestlingText, defaultColor, greenColor, "wrestling"));
        wrestling.setOnClickListener(view -> toggleActivity(wrestling, WrestlingText, defaultColor, greenColor, "wrestling"));
        skyBlue.setOnClickListener(view -> toggleActivity(skyBlue, HandballText, defaultColor, greenColor, "handBall"));
        handBall.setOnClickListener(view -> toggleActivity(handBall, HandballText, defaultColor, greenColor, "handBall"));
        limeGreen.setOnClickListener(view -> toggleActivity(limeGreen, ClimbingText, defaultColor, greenColor, "climbing"));
        climbing.setOnClickListener(view -> toggleActivity(climbing, ClimbingText, defaultColor, greenColor, "climbing"));
        orange.setOnClickListener(view -> toggleActivity(orange, SurfingText, defaultColor, greenColor, "surfing"));
        surfing.setOnClickListener(view -> toggleActivity(surfing, SurfingText, defaultColor, greenColor, "surfing"));
        lightRed.setOnClickListener(view -> toggleActivity(lightRed, golfingText, defaultColor, greenColor, "golfing"));
        golfing.setOnClickListener(view -> toggleActivity(golfing, golfingText, defaultColor, greenColor, "golfing"));
        bordeaux.setOnClickListener(view -> toggleActivity(bordeaux, WaterPoloText, defaultColor, greenColor, "waterPolo"));
        waterPolo.setOnClickListener(view -> toggleActivity(waterPolo, WaterPoloText, defaultColor, greenColor, "waterPolo"));
        lightPurple.setOnClickListener(view -> toggleActivity(lightPurple, HorseRacingText, defaultColor, greenColor, "horseRacing"));
        horseRacing.setOnClickListener(view -> toggleActivity(horseRacing, HorseRacingText, defaultColor, greenColor, "horseRacing"));
        oliveSquare.setOnClickListener(view -> toggleActivity(oliveSquare, FootballText, defaultColor, greenColor, "football"));
        football.setOnClickListener(view -> toggleActivity(football, FootballText, defaultColor, greenColor, "football"));
        lightPink.setOnClickListener(view -> toggleActivity(lightPink, BasketballText, defaultColor, greenColor, "basketball"));
        basketball.setOnClickListener(view -> toggleActivity(basketball, BasketballText, defaultColor, greenColor, "basketball"));
        navyBlue.setOnClickListener(view -> toggleActivity(navyBlue, BowlingText, defaultColor, greenColor, "bowling"));
        bowling.setOnClickListener(view -> toggleActivity(bowling, BowlingText, defaultColor, greenColor, "bowling"));
        turkis.setOnClickListener(view -> toggleActivity(turkis, TennisText, defaultColor, greenColor, "tennis"));
        tennis.setOnClickListener(view -> toggleActivity(tennis, TennisText, defaultColor, greenColor, "tennis"));
        yellow.setOnClickListener(view -> toggleActivity(yellow, PingPongText, defaultColor, greenColor, "pingpong"));
        pingpong.setOnClickListener(view -> toggleActivity(pingpong, PingPongText, defaultColor, greenColor, "pingpong"));
        darkPink.setOnClickListener(view -> toggleActivity(darkPink, Volleyball, defaultColor, greenColor, "volleyball"));
        volleyball.setOnClickListener(view -> toggleActivity(volleyball, Volleyball, defaultColor, greenColor, "volleyball"));
        lightBlue.setOnClickListener(view -> toggleActivity(lightBlue, RowingText, defaultColor, greenColor, "rowing"));
        rowing.setOnClickListener(view -> toggleActivity(rowing, RowingText, defaultColor, greenColor, "rowing"));

        // Button to proceed after selecting activities
        Button continueButton = findViewById(R.id.continue_button);
        continueButton.setOnClickListener(v -> {
            if (validateStep7()) {
                continueButton.setEnabled(false);
                collectStep7Data();
                completeSignUp();
            }
        });
    }

    private boolean validateStep7() {
        if (user.getFavActivities().isEmpty()) {
            Toast.makeText(this, "Please select at least one activity", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void collectStep7Data() {
        tealSquare.setOnClickListener(view -> toggleActivity(tealSquare, WeightliftingText, defaultColor, greenColor, "weightLifting"));
        weightLifting.setOnClickListener(view -> toggleActivity(weightLifting, WeightliftingText, defaultColor, greenColor, "weightLifting"));
        yoga.setOnClickListener(view -> toggleActivity(yoga, yogatext, defaultColor, greenColor, "yoga"));
        blueSquare.setOnClickListener(view -> toggleActivity(blueSquare, yogatext, defaultColor, greenColor, "yoga"));
        purpleSquare.setOnClickListener(view -> toggleActivity(purpleSquare, Cartwheelingtext, defaultColor, greenColor, "cartWheeling"));
        cartWheeling.setOnClickListener(view -> toggleActivity(cartWheeling, Cartwheelingtext, defaultColor, greenColor, "cartWheeling"));
        greenSquare.setOnClickListener(view -> toggleActivity(greenSquare, RunningText, defaultColor, greenColor, "running"));
        running.setOnClickListener(view -> toggleActivity(running, RunningText, defaultColor, greenColor, "running"));
        swimming.setOnClickListener(view -> toggleActivity(swimming, SwimmingText, defaultColor, greenColor, "swimming"));
        darkOrange.setOnClickListener(view -> toggleActivity(darkOrange, SwimmingText, defaultColor, greenColor, "swimming"));
        lightYellowSquare.setOnClickListener(view -> toggleActivity(lightYellowSquare, BikingText, defaultColor, greenColor, "biking"));
        biking.setOnClickListener(view -> toggleActivity(biking, BikingText, defaultColor, greenColor, "biking"));
        pinkSquare.setOnClickListener(view -> toggleActivity(pinkSquare, WrestlingText, defaultColor, greenColor, "wrestling"));
        wrestling.setOnClickListener(view -> toggleActivity(wrestling, WrestlingText, defaultColor, greenColor, "wrestling"));
        skyBlue.setOnClickListener(view -> toggleActivity(skyBlue, HandballText, defaultColor, greenColor, "handBall"));
        handBall.setOnClickListener(view -> toggleActivity(handBall, HandballText, defaultColor, greenColor, "handBall"));
        limeGreen.setOnClickListener(view -> toggleActivity(limeGreen, ClimbingText, defaultColor, greenColor, "climbing"));
        climbing.setOnClickListener(view -> toggleActivity(climbing, ClimbingText, defaultColor, greenColor, "climbing"));
        orange.setOnClickListener(view -> toggleActivity(orange, SurfingText, defaultColor, greenColor, "surfing"));
        surfing.setOnClickListener(view -> toggleActivity(surfing, SurfingText, defaultColor, greenColor, "surfing"));
        lightRed.setOnClickListener(view -> toggleActivity(lightRed, golfingText, defaultColor, greenColor, "golfing"));
        golfing.setOnClickListener(view -> toggleActivity(golfing, golfingText, defaultColor, greenColor, "golfing"));
        bordeaux.setOnClickListener(view -> toggleActivity(bordeaux, WaterPoloText, defaultColor, greenColor, "waterPolo"));
        waterPolo.setOnClickListener(view -> toggleActivity(waterPolo, WaterPoloText, defaultColor, greenColor, "waterPolo"));
        lightPurple.setOnClickListener(view -> toggleActivity(lightPurple, HorseRacingText, defaultColor, greenColor, "horseRacing"));
        horseRacing.setOnClickListener(view -> toggleActivity(horseRacing, HorseRacingText, defaultColor, greenColor, "horseRacing"));
        oliveSquare.setOnClickListener(view -> toggleActivity(oliveSquare, FootballText, defaultColor, greenColor, "football"));
        football.setOnClickListener(view -> toggleActivity(football, FootballText, defaultColor, greenColor, "football"));
        lightPink.setOnClickListener(view -> toggleActivity(lightPink, BasketballText, defaultColor, greenColor, "basketball"));
        basketball.setOnClickListener(view -> toggleActivity(basketball, BasketballText, defaultColor, greenColor, "basketball"));
        navyBlue.setOnClickListener(view -> toggleActivity(navyBlue, BowlingText, defaultColor, greenColor, "bowling"));
        bowling.setOnClickListener(view -> toggleActivity(bowling, BowlingText, defaultColor, greenColor, "bowling"));
        turkis.setOnClickListener(view -> toggleActivity(turkis, TennisText, defaultColor, greenColor, "tennis"));
        tennis.setOnClickListener(view -> toggleActivity(tennis, TennisText, defaultColor, greenColor, "tennis"));
        yellow.setOnClickListener(view -> toggleActivity(yellow, PingPongText, defaultColor, greenColor, "pingpong"));
        pingpong.setOnClickListener(view -> toggleActivity(pingpong, PingPongText, defaultColor, greenColor, "pingpong"));
        darkPink.setOnClickListener(view -> toggleActivity(darkPink, Volleyball, defaultColor, greenColor, "volleyball"));
        volleyball.setOnClickListener(view -> toggleActivity(volleyball, Volleyball, defaultColor, greenColor, "volleyball"));
        lightBlue.setOnClickListener(view -> toggleActivity(lightBlue, RowingText, defaultColor, greenColor, "rowing"));
        rowing.setOnClickListener(view -> toggleActivity(rowing, RowingText, defaultColor, greenColor, "rowing"));
    }

    private void toggleActivity(ImageView imageView, TextView textView, int defaultColor, int greenColor, String activityName) {
        if (user.getFavActivities().contains(activityName)) {
            textView.setTextColor(defaultColor);
            user.getFavActivities().remove(activityName);
        } else {
            textView.setTextColor(greenColor);
            user.getFavActivities().add(activityName);
        }
    }

    private void completeSignUp() {
        // First, save the user locally using Room
        new Thread(() -> {
            try {
                // Insert user into Room database and get the generated ID
                long userId = userDao.insertUser(user);
                user.setSeeTheInstructions(false);
                user.setId(userId);  // Set the generated ID to the user object

                // Store the user ID in SharedPreferences to continue the session
                SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong("USER_ID", userId);
                editor.apply();  // Commit changes

                // After local saving is done, switch to UI thread to show success message
                runOnUiThread(() -> {
                    Toast.makeText(Signup.this, "User saved locally with ID: " + userId, Toast.LENGTH_SHORT).show();


                    // Pass the user ID to HomePage
                    Intent intent = new Intent(Signup.this, HomePage.class);
                    intent.putExtra("USER_ID", userId);  // Pass the generated user ID
                    startActivity(intent);
                    finish();
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(Signup.this, "Error saving user locally", Toast.LENGTH_SHORT).show();
                });
            }

            // Now, save the user remotely using Retrofit (if needed)
            Call<User> call = userApi.insertUser(user);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        runOnUiThread(() -> {
                            Toast.makeText(Signup.this, "User added to server successfully!", Toast.LENGTH_SHORT).show();
                        });
                    } else {
                        runOnUiThread(() -> {
                            Toast.makeText(Signup.this, "Failed to add user to server: " + response.code(), Toast.LENGTH_SHORT).show();
                        });
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    runOnUiThread(() -> {
                        Toast.makeText(Signup.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            });
        }).start();
    }


    // Move to the next step based on current step
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
            case 6:
                setupStep7();
                break;
            default:
                completeSignUp();
                break;
        }
    }
}
