//package com.example.project_riseup;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.text.Spannable;
//import android.text.SpannableString;
//import android.text.style.ForegroundColorSpan;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//public class HydrationActivity extends AppCompatActivity {
//
//    private MyView myView;
//    private Button increaseButton;
//    private Button decreaseButton;
//    private Button addDrinkButton;
//    private TextView waterAmountText;
//    private TextView cupText;
//    private TextView motivateText;
//    private float progress = 0f;
//    private int underCupAmount = 100;
//
//    private final int maxHydration = 1000;
//
//    ImageButton homeButton;
//    ImageButton groupsButton;
//    ImageButton calendarButton;
//    ImageButton profileButton;
//
//
//    private long userId;//temporary
//
//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Retrieve the userId from the Intent
//        Intent intent = getIntent();
//        if (intent != null && intent.hasExtra("USER_ID")) {
//            userId = intent.getIntExtra("USER_ID", 1); // 1 is the default value if USER_ID is not found
//        }
//
//        initViews();
//
//        // Initialize buttons
//        homeButton = findViewById(R.id.homeImageButton);
//        groupsButton = findViewById(R.id.groupsImageButton);
//        calendarButton = findViewById(R.id.calendarImageButton);
//        profileButton = findViewById(R.id.profileImageButton);
//
//        // Set the home button as selected by default, since this is the HomeActivity
//        homeButton.setSelected(true);
//
//        // Set click listeners for each button
//        homeButton.setOnClickListener(this::onHomeClicked);
//        groupsButton.setOnClickListener(this::onGroupsClicked);
////        calendarButton.setOnClickListener(this::onCalendarClicked);
//        profileButton.setOnClickListener(this::onProfileClicked);
//    }
//
//
//    // Methods to handle button clicks
//    public void onHomeClicked(View view) {
//        // No need to start the HomeActivity again, just update button state
//        updateButtonStates(homeButton);
//    }
//
//    public void onGroupsClicked(View view) {
//        updateButtonStates(groupsButton);
//
//        Intent intent = new Intent(this, MapActivity.class);
//        intent.putExtra("USER_ID", userId);  // Pass the user ID to Profile activity
//        startActivity(intent);
//    }
//
//    public void onProfileClicked(View view) {
//        updateButtonStates(profileButton);
//        Intent intent = new Intent(this, Profile.class);
//        intent.putExtra("USER_ID", userId);  // Pass the user ID to Profile activity
//        startActivity(intent);
//    }
//
////    public void onCalendarClicked(View view) {
////        updateButtonStates(calendarButton);
////        Intent intent = new Intent(this, CalendarActivity.class);
////        intent.putExtra("USER_ID", userId);  // Pass the user ID to Profile activity
////        startActivity(intent);
////    }
//
//
//
//    // Method to update the selected state of the buttons
//    private void updateButtonStates(ImageButton selectedButton) {
//        // Deselect all buttons
//        homeButton.setSelected(false);
//        groupsButton.setSelected(false);
//        calendarButton.setSelected(false);
//        profileButton.setSelected(false);
//
//        // Set the selected button to true
//        selectedButton.setSelected(true);
//    }
//
//
//    private void initViews() {
//        myView = findViewById(R.id.my_view);
//        motivateText = findViewById(R.id.motivateTextView);
//        increaseButton = findViewById(R.id.increase_button);
//        decreaseButton = findViewById(R.id.decrease_button);
//        addDrinkButton = findViewById(R.id.addDrinkButton);
//        waterAmountText = findViewById(R.id.textViewWater);
//        cupText = findViewById(R.id.cupText);
//
//
//        if (progress == maxHydration) {
//            motivateText.setText("Great job!, You done it");
////            // Create a SpannableString with the text you want to style
////            SpannableString spannables = new SpannableString("Great job!, You done it");
////            // Get the app color from resources
////            int appColor = getResources().getColor(R.color.appColor);
////            // Apply the color to the entire text (if you want the whole text to be the app color)
////            spannables.setSpan(new ForegroundColorSpan(appColor), 0, spannables.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////            // Set the SpannableString to the TextView
////            motivateText.setText(spannables);
//        } else {
//            motivateText.setText("You'r almost there!, Keep hydrated");
//        }
//
//
//
//
////        waterAmountText.setText(String.format("%.0f ml of water", progress));
//        setWaterAmountText(waterAmountText, progress);
//
//
//
//
//
//
//
//
//
//        increaseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                progress += 50;
////                if (progress > 1000) {
////                    progress = 1000;
////                }
//
//                underCupAmount += 50;
//                if (underCupAmount > 500) {
//                    underCupAmount = 500;
//                }
////                myView.setProgress(progress)
//
//                // Convert to String
//                String firstPart = String.valueOf(underCupAmount);
//                // Create a SpannableString combining 2 parts
//                SpannableString spannable = new SpannableString(firstPart + " ml");
//                // Set the SpannableString to the TextView
//                cupText.setText(spannable);
//            }
//        });
//
//        decreaseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                progress -= 50;
////                if (progress < 0) {
////                    progress = 0;
////                }
//
//                underCupAmount -= 50;
//                if (underCupAmount < -500) {
//                    underCupAmount = -500;
//                }
////                if (underCupAmount < 0) {
////                    underCupAmount = 0;
////                }
////                myView.setProgress(progress);
//
//                // Convert to String
//                String firstPart = String.valueOf(underCupAmount);
//                // Create a SpannableString combining 2 parts
//                SpannableString spannable = new SpannableString(firstPart + " ml");
//                // Set the SpannableString to the TextView
//                cupText.setText(spannable);
//            }
//        });
//
//        addDrinkButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
////                if (progress == 0){
////                    progress = underCupAmount;
////                }
////                else {
////                    progress += underCupAmount;
////                }
//
//
//
//                if(progress + underCupAmount < 0){
//                    progress = 0;
//                }
//                else{
//                    if(progress + underCupAmount > maxHydration){
//                        progress = maxHydration;
//                    }
//                    else {
//                        progress += underCupAmount;
//                    }
//                }
////                if(progress >= 0) {
////                    progress += underCupAmount;
////                }
////                else{
////                    progress = 0;
////                }
//
//
//
//                if (progress == maxHydration) {
//                    motivateText.setText("Great job!, You done it");
////                    // Create a SpannableString with the text you want to style
////                    SpannableString spannable = new SpannableString("great job!, you done it");
////                    // Get the app color from resources
////                    int appColor = getResources().getColor(R.color.appColor);
////                    // Apply the color to the entire text (if you want the whole text to be the app color)
////                    spannable.setSpan(new ForegroundColorSpan(appColor), 0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////                    // Set the SpannableString to the TextView
////                    motivateText.setText(spannable);
//                } else {
//                    motivateText.setText("your almost there!, Keep hydrated");
//                }
//
//
//
//                setWaterAmountText(waterAmountText, progress);
//
//
////                // Convert progress to String
////                String firstPart = String.valueOf(progress);
////                String secondPart = " ml of water";
////                // Create a SpannableString combining both parts
////                SpannableString spannable = new SpannableString(firstPart + secondPart);
////                // Get the app color from resources
////                int appColor = getResources().getColor(R.color.appColor);
////                // Set the first part (progress) to app color
////                spannable.setSpan(new ForegroundColorSpan(appColor), 0, firstPart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////                // Set the first part (progress) to app color
////                spannable.setSpan(new ForegroundColorSpan(Color.BLACK), firstPart.length(), secondPart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////                // Set the SpannableString to the TextView
////                waterAmountText.setText(spannable);
//
//
//
////                setWaterAmountText(waterAmountText, progress);
//
//
////                restart the under water amount
////                underCupAmount = 50;
//                // Convert to String
//                String firstCupPart = String.valueOf(underCupAmount);
//                // Create a SpannableString combining 2 parts
//                SpannableString spannableCup = new SpannableString(firstCupPart + " ml");
//                // Set the SpannableString to the TextView
//                cupText.setText(spannableCup);
//                /////////////the second parameter should be how much user should drink
//                myView.setProgress(progress, maxHydration);/////////here we define the max hydration amount
//            }
//        });
//
//
//
//
//
//    }
//
//    private void setWaterAmountText(TextView waterAmountText, float progress) {
//        String firstPart = String.valueOf(progress);
//        String secondPart = " ml of water";
//        // Create a SpannableString combining both parts
//        SpannableString spannable = new SpannableString(firstPart + secondPart);
//        // Get the app color from resources
//        int appColor = getResources().getColor(R.color.appColor);
//        // Set the first part (progress) to app color
//        spannable.setSpan(new ForegroundColorSpan(appColor), 0, firstPart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        // Set the second part (water) to black
//        spannable.setSpan(new ForegroundColorSpan(Color.BLACK), firstPart.length(), spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        // Set the SpannableString to the TextView
//        waterAmountText.setText(spannable);
//    }
//}
/////////////////////////////////////////////////////////////////////////////////////////////
//package com.example.project_riseup;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.text.Spannable;
//import android.text.SpannableString;
//import android.text.style.ForegroundColorSpan;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.lifecycle.ViewModelProvider;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//import java.util.List;
//
//public class HydrationActivity extends AppCompatActivity {
//
//    private MyView myView;
//    private Button increaseButton;
//    private Button decreaseButton;
//    private Button addDrinkButton;
//    private TextView waterAmountText;
//    private TextView cupText;
//    private TextView motivateText;
//    private float progress = 0f;
//    private int underCupAmount = 100;
//    private int maxHydration = 1000;
//    private long userId;
//    private HydrationAPI hydrationAPI; // Add this line
//
//    public User user;
//
//    ImageButton homeButton;
//    ImageButton groupsButton;
//    ImageButton calendarButton;
//    ImageButton profileButton;
//
//    UserViewModel userViewModel;
//
//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_hydration); // Update layout to activity_hydration
//
////        // Initialize Retrofit
////        Retrofit retrofit = new Retrofit.Builder()
////                .baseUrl("https://your-api-url.com/") // Update with your base URL
////                .addConverterFactory(GsonConverterFactory.create())
////                .build();
////        hydrationAPI = retrofit.create(HydrationAPI.class);
////
//
//        // Retrieve the userId from the Intent
//        Intent intent = getIntent();
//        if (intent != null && intent.hasExtra("USER_ID")) {
//            userId = intent.getLongExtra("USER_ID", 1); // 1 is the default value if USER_ID is not found
//        }
//
//        // Retrieve the user ID from SharedPreferences
//        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
//        userId = sharedPreferences.getLong("USER_ID", -1);
//
//        if (userId != -1) {
//            // Initialize ViewModel and fetch user data
//            userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
//
//            new Thread(() -> {
//                user = userViewModel.getUserById(userId);
//                runOnUiThread(() -> {
//                    if (user != null) {
//                        progress = user.getHydrationAmount();
//                        maxHydration = (int) (user.getWeight()*35);
//                    } else {
//                        Toast.makeText(HydrationActivity.this, "user is n...", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }).start();
//        } else {
//            Toast.makeText(HydrationActivity.this, "user is -1...", Toast.LENGTH_SHORT).show();
//        }
//
//
//        homeButton = findViewById(R.id.homeImageButton);
//        groupsButton = findViewById(R.id.groupsImageButton);
//        calendarButton = findViewById(R.id.calendarImageButton);
//        profileButton = findViewById(R.id.profileImageButton);
//
//        // Set the home button as selected by default
//        homeButton.setSelected(true);
//
//        homeButton.setOnClickListener(this::onHomeClicked);
//        groupsButton.setOnClickListener(this::onGroupsClicked);
//        profileButton.setOnClickListener(this::onProfileClicked);
//
//
//        initViews();
////        setupButtonListeners();
//        user.setHydrationAmount(progress);
//    }
//
////    private void setupButtonListeners() {
////        homeButton = findViewById(R.id.homeImageButton);
////        groupsButton = findViewById(R.id.groupsImageButton);
////        calendarButton = findViewById(R.id.calendarImageButton);
////        profileButton = findViewById(R.id.profileImageButton);
////
////
////        homeButton.setOnClickListener(this::onHomeClicked);
////        groupsButton.setOnClickListener(this::onGroupsClicked);
////        profileButton.setOnClickListener(this::onProfileClicked);
////    }
//
//    private void initViews() {
//        myView = findViewById(R.id.my_view);
//        motivateText = findViewById(R.id.motivateTextView);
//        increaseButton = findViewById(R.id.increase_button);
//        decreaseButton = findViewById(R.id.decrease_button);
//        addDrinkButton = findViewById(R.id.addDrinkButton);
//        waterAmountText = findViewById(R.id.textViewWater);
//        cupText = findViewById(R.id.cupText);
//
//        updateMotivateText();
//        setWaterAmountText(waterAmountText, progress);
//
//        increaseButton.setOnClickListener(v -> {
//            underCupAmount = Math.min(underCupAmount + 50, 500);
//            updateCupText();
//        });
//
//        decreaseButton.setOnClickListener(v -> {
//            underCupAmount = Math.max(underCupAmount - 50, -500);
//            updateCupText();
//        });
//
//        addDrinkButton.setOnClickListener(v -> {
//            progress = Math.max(0, Math.min(progress + underCupAmount, maxHydration));
//            updateMotivateText();
//            setWaterAmountText(waterAmountText, progress);
//            updateCupText();
//            myView.setProgress(progress, maxHydration);
////            fetchAndUpdateHydrationRecords(); // Add this line
//        });
//    }
//
//    private void updateCupText() {
//        String cupTextValue = underCupAmount + " ml";
//        SpannableString spannable = new SpannableString(cupTextValue);
//        cupText.setText(spannable);
//    }
//
//    private void updateMotivateText() {
//        String motivateMessage = (progress == maxHydration) ? "Great job! You've done it" : "You're almost there! Keep hydrated";
//        motivateText.setText(motivateMessage);
//    }
//
//    private void setWaterAmountText(TextView waterAmountText, float progress) {
//        String waterText = progress + " ml of water";
//        SpannableString spannable = new SpannableString(waterText);
//        int appColor = getResources().getColor(R.color.appColor);
//        spannable.setSpan(new ForegroundColorSpan(appColor), 0, String.valueOf(progress).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannable.setSpan(new ForegroundColorSpan(Color.BLACK), String.valueOf(progress).length(), spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        waterAmountText.setText(spannable);
//    }
//
//    private void fetchAndUpdateHydrationRecords() {
//        // Fetch hydration records
//        Call<List<Hydration>> call = hydrationAPI.getHydrationRecordsByUserId(userId);
//        call.enqueue(new Callback<List<Hydration>>() {
//            @Override
//            public void onResponse(Call<List<Hydration>> call, Response<List<Hydration>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<Hydration> hydrationRecords = response.body();
//                    if (!hydrationRecords.isEmpty()) {
//                        // For demonstration, let's update the first record
//                        Hydration recordToUpdate = hydrationRecords.get(0);
//                        updateHydrationAmount(recordToUpdate.getId(), progress);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Hydration>> call, Throwable t) {
//                Toast.makeText(HydrationActivity.this, "failure in fetchUpdate..", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void updateHydrationAmount(Long hydrationId, float newAmount) {
//        Hydration updatedHydration = new Hydration();
//        updatedHydration.setId(hydrationId);
//        updatedHydration.setUserId(userId);
//        updatedHydration.setWaterAmount(newAmount);
//        // Set other required fields if necessary
//
//        Call<Hydration> call = hydrationAPI.updateHydrationRecord(hydrationId, updatedHydration);
//        call.enqueue(new Callback<Hydration>() {
//            @Override
//            public void onResponse(Call<Hydration> call, Response<Hydration> response) {
//                if (response.isSuccessful()) {
//                    // Handle success
//                } else {
//                    // Handle failure
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Hydration> call, Throwable t) {
//                Toast.makeText(HydrationActivity.this, "Failure in updateHydrationAmount", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    // Methods to handle button clicks
//    public void onHomeClicked(View view) {
//        updateButtonStates(homeButton);
//        Intent intent = new Intent(HydrationActivity.this, HomePage.class);
//        intent.putExtra("USER_ID", userId);
//        startActivity(intent);
//    }
//
//    public void onGroupsClicked(View view) {
//        updateButtonStates(groupsButton);
//        Intent intent = new Intent(this, MapActivity.class);
//        intent.putExtra("USER_ID", userId);
//        startActivity(intent);
//    }
//
//    public void onProfileClicked(View view) {
//        updateButtonStates(profileButton);
//        Intent intent = new Intent(this, Profile.class);
//        intent.putExtra("USER_ID", userId);
//        startActivity(intent);
//    }
//
//    private void updateButtonStates(ImageButton selectedButton) {
//        homeButton.setSelected(false);
//        groupsButton.setSelected(false);
//        calendarButton.setSelected(false);
//        profileButton.setSelected(false);
//        selectedButton.setSelected(true);
//    }
//}
package com.example.project_riseup;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HydrationActivity extends AppCompatActivity {

    private MyView myView;
    private Button increaseButton;
    private Button decreaseButton;
    private Button addDrinkButton;
    private TextView waterAmountText;
    private TextView cupText;
    private TextView motivateText;
    private float progress = 0f;
    private int underCupAmount = 100;
    private int maxHydration = 1000;
    private long userId;
    private HydrationAPI hydrationAPI; // API interface for hydration data

    private User user;

    ImageButton homeButton;
    ImageButton groupsButton;
    ImageButton calendarButton;
    ImageButton profileButton;

    UserViewModel userViewModel;



    //    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_hydration); // Ensure the layout is updated to activity_hydration
//
//        // Initialize Retrofit
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://your-api-url.com/") // Replace with your actual API base URL
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        hydrationAPI = retrofit.create(HydrationAPI.class);
//
//        // Retrieve userId from Intent or SharedPreferences
//        Intent intent = getIntent();
//        if (intent != null && intent.hasExtra("USER_ID")) {
//            userId = intent.getLongExtra("USER_ID", -1);
//        } else {
//            SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
//            userId = sharedPreferences.getLong("USER_ID", -1);
//        }
//
//        if (userId != -1) {
//            // Initialize ViewModel and fetch user data
//            userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
//            new Thread(() -> {
//                user = userViewModel.getUserById(userId);
//                runOnUiThread(() -> {
//                    if (user != null) {
//                        progress = user.getHydrationAmount();
//                        maxHydration = (int) (user.getWeight() * 35);
//                        initViews();
//                        user.setHydrationAmount(progress);
//                    } else {
//                        Toast.makeText(HydrationActivity.this, "User not found", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }).start();
//        } else {
//            Toast.makeText(HydrationActivity.this, "Invalid user ID", Toast.LENGTH_SHORT).show();
//        }
//
//        homeButton = findViewById(R.id.homeImageButton);
//        groupsButton = findViewById(R.id.groupsImageButton);
//        calendarButton = findViewById(R.id.calendarImageButton);
//        profileButton = findViewById(R.id.profileImageButton);
//
//        // Set default selected button
//        homeButton.setSelected(true);
//        homeButton.setOnClickListener(this::onHomeClicked);
//        groupsButton.setOnClickListener(this::onGroupsClicked);
//        profileButton.setOnClickListener(this::onProfileClicked);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (userId != -1) {
//            // Fetch user data when activity resumes
//            new Thread(() -> {
//                user = userViewModel.getUserById(userId);
//                runOnUiThread(() -> {
//                    if (user != null) {
//                        progress = user.getHydrationAmount();
//                        maxHydration = (int) (user.getWeight() * 35);
//                        updateUI();
//                    } else {
//                        Toast.makeText(HydrationActivity.this, "User not found.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }).start();
//        }
//    }
@SuppressLint("MissingInflatedId")
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_hydration); // Ensure the layout is updated to activity_hydration


    // Initialize Retrofit
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://your-api-url.com/") // Replace with your actual API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    hydrationAPI = retrofit.create(HydrationAPI.class);

    // Retrieve userId from Intent or SharedPreferences
    Intent intent = getIntent();
    if (intent != null && intent.hasExtra("USER_ID")) {
        userId = intent.getLongExtra("USER_ID", -1);
    } else {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        userId = sharedPreferences.getLong("USER_ID", -1);
    }

    if (userId != -1) {
        // Initialize ViewModel and fetch user data
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        new Thread(() -> {
            user = userViewModel.getUserById(userId);
            runOnUiThread(() -> {
                if (user != null) {
                    progress = user.getHydrationAmount();
                    maxHydration = (int) (user.getWeight() * 35);
                    initViews();
                    user.setHydrationAmount(progress);
                } else {
                    Toast.makeText(HydrationActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    } else {
        Toast.makeText(HydrationActivity.this, "Invalid user ID", Toast.LENGTH_SHORT).show();
    }

    // Retrieve underCupAmount from SharedPreferences
    SharedPreferences sharedPreferences = getSharedPreferences("HydrationPreferences", MODE_PRIVATE);
    underCupAmount = sharedPreferences.getInt("UNDER_CUP_AMOUNT", 100);

    homeButton = findViewById(R.id.homeImageButton);
    groupsButton = findViewById(R.id.groupsImageButton);
    calendarButton = findViewById(R.id.calendarImageButton);
    profileButton = findViewById(R.id.profileImageButton);

    // Set default selected button
    homeButton.setSelected(true);
    homeButton.setOnClickListener(this::onHomeClicked);
    groupsButton.setOnClickListener(this::onGroupsClicked);
    //calendarButton.setOnClickListener(this::onCalendarClicked);
    profileButton.setOnClickListener(this::onProfileClicked);

}

    @Override
    protected void onResume() {
        super.onResume();
        if (userId != -1) {
            // Fetch user data when activity resumes
            new Thread(() -> {
                user = userViewModel.getUserById(userId);
                runOnUiThread(() -> {
                    if (user != null) {
                        progress = user.getHydrationAmount();
                        maxHydration = (int) (user.getWeight() * 35);
                        updateUI();
                    } else {
                        Toast.makeText(HydrationActivity.this, "User not found.", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        }

        // Retrieve underCupAmount from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("HydrationPreferences", MODE_PRIVATE);
        underCupAmount = sharedPreferences.getInt("UNDER_CUP_AMOUNT", 100);


    }





    private void initViews() {
        myView = findViewById(R.id.my_view);
        motivateText = findViewById(R.id.motivateTextView);
        increaseButton = findViewById(R.id.increase_button);
        decreaseButton = findViewById(R.id.decrease_button);
        addDrinkButton = findViewById(R.id.addDrinkButton);
        waterAmountText = findViewById(R.id.textViewWater);
        cupText = findViewById(R.id.cupText);

        updateMotivateText();
        setWaterAmountText(waterAmountText, progress);

        increaseButton.setOnClickListener(v -> {
            underCupAmount = Math.min(underCupAmount + 50, 500);
            updateCupText();
            // Save underCupAmount to SharedPreferences
            saveUnderCupAmount();
        });

        decreaseButton.setOnClickListener(v -> {
            underCupAmount = Math.max(underCupAmount - 50, -500);
            updateCupText();
            // Save underCupAmount to SharedPreferences
            saveUnderCupAmount();
        });

//        addDrinkButton.setOnClickListener(v -> {
//            progress = Math.max(0, Math.min(progress + underCupAmount, maxHydration));
//            updateMotivateText();
//            setWaterAmountText(waterAmountText, progress);
//            updateCupText();
//            myView.setProgress(progress, maxHydration);
//            fetchAndUpdateHydrationRecords(); // Update hydration records in the backend
//        });
//        addDrinkButton.setOnClickListener(v -> {
//            progress = Math.max(0, Math.min(progress + underCupAmount, maxHydration));
//            updateMotivateText();
//            setWaterAmountText(waterAmountText, progress);
//            updateCupText();
//            myView.setProgress(progress, maxHydration);
//
//            // Update user hydrationAmount
//            if (user != null) {
//                user.setHydrationAmount(progress);
//                new Thread(() -> {
//                    userViewModel.updateUser(user);
//                    runOnUiThread(() -> Toast.makeText(HydrationActivity.this, "Hydration updated!", Toast.LENGTH_SHORT).show());
//                }).start();
//            }
//        });
        addDrinkButton.setOnClickListener(v -> {
            progress = Math.max(0, Math.min(progress + underCupAmount, maxHydration));
            updateMotivateText();
            setWaterAmountText(waterAmountText, progress);
            updateCupText();
            myView.setProgress(progress, maxHydration);

            // Update user hydrationAmount
            if (user != null) {
                user.setHydrationAmount(progress);
                new Thread(() -> {
                    userViewModel.updateUser(user);
                    runOnUiThread(() -> Toast.makeText(HydrationActivity.this, "Hydration updated!", Toast.LENGTH_SHORT).show());
                }).start();
            }

            // Save underCupAmount to SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("HydrationPreferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("UNDER_CUP_AMOUNT", underCupAmount);
            editor.apply();
        });

        // Save underCupAmount to SharedPreferences
        saveUnderCupAmount();
    }

    private void saveUnderCupAmount() {
        SharedPreferences sharedPreferences = getSharedPreferences("HydrationPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("UNDER_CUP_AMOUNT", underCupAmount);
        editor.apply();
    }

    private void updateUI() {
        // Update UI elements with the current progress
        setWaterAmountText(waterAmountText, progress);
        updateCupText();
        updateMotivateText();
        myView.setProgress(progress, maxHydration);
    }

    private void updateCupText() {
        String cupTextValue = underCupAmount + " ml";
        SpannableString spannable = new SpannableString(cupTextValue);
        cupText.setText(spannable);
    }

    private void updateMotivateText() {
        String motivateMessage = (progress == maxHydration) ? "Great job! You've done it" : "You're almost there! Keep hydrated";
        motivateText.setText(motivateMessage);
    }

    private void setWaterAmountText(TextView waterAmountText, float progress) {
        String waterText = progress + " ml of water";
        SpannableString spannable = new SpannableString(waterText);
        int appColor = getResources().getColor(R.color.appColor);
        spannable.setSpan(new ForegroundColorSpan(appColor), 0, String.valueOf(progress).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(Color.BLACK), String.valueOf(progress).length(), spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        waterAmountText.setText(spannable);
    }

    private void fetchAndUpdateHydrationRecords() {
        // Fetch hydration records
        Call<List<Hydration>> call = hydrationAPI.getHydrationRecordsByUserId(userId);
        call.enqueue(new Callback<List<Hydration>>() {
            @Override
            public void onResponse(Call<List<Hydration>> call, Response<List<Hydration>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Hydration> hydrationRecords = response.body();
                    // Handle the hydration records as needed
                } else {
                    Toast.makeText(HydrationActivity.this, "Failed to fetch hydration records", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Hydration>> call, Throwable t) {
                Toast.makeText(HydrationActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Methods to handle button clicks
    public void onHomeClicked(View view) {
        updateButtonStates(homeButton);
        Intent intent = new Intent(this, HomePage.class);
        intent.putExtra("USER_ID", userId);
        startActivity(intent);
    }

    public void onGroupsClicked(View view) {
        updateButtonStates(groupsButton);
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("USER_ID", userId);
        startActivity(intent);
    }

    //public void onCalendarClicked(View view) {
    //    updateButtonStates(calendarButton);
    //     Intent intent = new Intent(this, CalendarActivity.class);
    //   intent.putExtra("USER_ID", userId);  // Pass the user ID
    // startActivity(intent);
    //}

    public void onProfileClicked(View view) {
        updateButtonStates(profileButton);
        Intent intent = new Intent(this, Profile.class);
        intent.putExtra("USER_ID", userId);
        startActivity(intent);
    }

    private void updateButtonStates(ImageButton selectedButton) {
        homeButton.setSelected(false);
        groupsButton.setSelected(false);
        calendarButton.setSelected(false);
        profileButton.setSelected(false);
        selectedButton.setSelected(true);
    }


}
