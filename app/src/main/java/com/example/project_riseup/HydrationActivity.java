package com.example.project_riseup;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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

    private final int maxHydration = 1000;

    ImageButton homeButton;
    ImageButton groupsButton;
    ImageButton calendarButton;
    ImageButton profileButton;


    long userId=1;//temporary

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        // Initialize buttons
        homeButton = findViewById(R.id.homeImageButton);
        groupsButton = findViewById(R.id.groupsImageButton);
        calendarButton = findViewById(R.id.calendarImageButton);
        profileButton = findViewById(R.id.profileImageButton);

        // Set the home button as selected by default, since this is the HomeActivity
        homeButton.setSelected(true);

        // Set click listeners for each button
        homeButton.setOnClickListener(this::onHomeClicked);
        groupsButton.setOnClickListener(this::onGroupsClicked);
//        calendarButton.setOnClickListener(this::onCalendarClicked);
        profileButton.setOnClickListener(this::onProfileClicked);
    }


    // Methods to handle button clicks
    public void onHomeClicked(View view) {
        // No need to start the HomeActivity again, just update button state
        updateButtonStates(homeButton);
    }

    public void onGroupsClicked(View view) {
        updateButtonStates(groupsButton);

        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("USER_ID", userId);  // Pass the user ID to Profile activity
        startActivity(intent);
    }

    public void onProfileClicked(View view) {
        updateButtonStates(profileButton);
        Intent intent = new Intent(this, Profile.class);
        intent.putExtra("USER_ID", userId);  // Pass the user ID to Profile activity
        startActivity(intent);
    }

//    public void onCalendarClicked(View view) {
//        updateButtonStates(calendarButton);
//        Intent intent = new Intent(this, CalendarActivity.class);
//        intent.putExtra("USER_ID", userId);  // Pass the user ID to Profile activity
//        startActivity(intent);
//    }



    // Method to update the selected state of the buttons
    private void updateButtonStates(ImageButton selectedButton) {
        // Deselect all buttons
        homeButton.setSelected(false);
        groupsButton.setSelected(false);
        calendarButton.setSelected(false);
        profileButton.setSelected(false);

        // Set the selected button to true
        selectedButton.setSelected(true);
    }


    private void initViews() {
        myView = findViewById(R.id.my_view);
        motivateText = findViewById(R.id.motivateTextView);
        increaseButton = findViewById(R.id.increase_button);
        decreaseButton = findViewById(R.id.decrease_button);
        addDrinkButton = findViewById(R.id.addDrinkButton);
        waterAmountText = findViewById(R.id.textViewWater);
        cupText = findViewById(R.id.cupText);


        if (progress == maxHydration) {
            motivateText.setText("Great job!, You done it");
//            // Create a SpannableString with the text you want to style
//            SpannableString spannables = new SpannableString("Great job!, You done it");
//            // Get the app color from resources
//            int appColor = getResources().getColor(R.color.appColor);
//            // Apply the color to the entire text (if you want the whole text to be the app color)
//            spannables.setSpan(new ForegroundColorSpan(appColor), 0, spannables.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            // Set the SpannableString to the TextView
//            motivateText.setText(spannables);
        } else {
            motivateText.setText("You'r almost there!, Keep hydrated");
        }




//        waterAmountText.setText(String.format("%.0f ml of water", progress));
        setWaterAmountText(waterAmountText, progress);









        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                progress += 50;
//                if (progress > 1000) {
//                    progress = 1000;
//                }

                underCupAmount += 50;
                if (underCupAmount > 500) {
                    underCupAmount = 500;
                }
//                myView.setProgress(progress)

                // Convert to String
                String firstPart = String.valueOf(underCupAmount);
                // Create a SpannableString combining 2 parts
                SpannableString spannable = new SpannableString(firstPart + " ml");
                // Set the SpannableString to the TextView
                cupText.setText(spannable);
            }
        });

        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                progress -= 50;
//                if (progress < 0) {
//                    progress = 0;
//                }

                underCupAmount -= 50;
                if (underCupAmount < -500) {
                    underCupAmount = -500;
                }
//                if (underCupAmount < 0) {
//                    underCupAmount = 0;
//                }
//                myView.setProgress(progress);

                // Convert to String
                String firstPart = String.valueOf(underCupAmount);
                // Create a SpannableString combining 2 parts
                SpannableString spannable = new SpannableString(firstPart + " ml");
                // Set the SpannableString to the TextView
                cupText.setText(spannable);
            }
        });

        addDrinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                if (progress == 0){
//                    progress = underCupAmount;
//                }
//                else {
//                    progress += underCupAmount;
//                }



                if(progress + underCupAmount < 0){
                    progress = 0;
                }
                else{
                    if(progress + underCupAmount > maxHydration){
                        progress = maxHydration;
                    }
                    else {
                        progress += underCupAmount;
                    }
                }
//                if(progress >= 0) {
//                    progress += underCupAmount;
//                }
//                else{
//                    progress = 0;
//                }



                if (progress == maxHydration) {
                    motivateText.setText("Great job!, You done it");
//                    // Create a SpannableString with the text you want to style
//                    SpannableString spannable = new SpannableString("great job!, you done it");
//                    // Get the app color from resources
//                    int appColor = getResources().getColor(R.color.appColor);
//                    // Apply the color to the entire text (if you want the whole text to be the app color)
//                    spannable.setSpan(new ForegroundColorSpan(appColor), 0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    // Set the SpannableString to the TextView
//                    motivateText.setText(spannable);
                } else {
                    motivateText.setText("your almost there!, Keep hydrated");
                }



                setWaterAmountText(waterAmountText, progress);


//                // Convert progress to String
//                String firstPart = String.valueOf(progress);
//                String secondPart = " ml of water";
//                // Create a SpannableString combining both parts
//                SpannableString spannable = new SpannableString(firstPart + secondPart);
//                // Get the app color from resources
//                int appColor = getResources().getColor(R.color.appColor);
//                // Set the first part (progress) to app color
//                spannable.setSpan(new ForegroundColorSpan(appColor), 0, firstPart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                // Set the first part (progress) to app color
//                spannable.setSpan(new ForegroundColorSpan(Color.BLACK), firstPart.length(), secondPart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                // Set the SpannableString to the TextView
//                waterAmountText.setText(spannable);



//                setWaterAmountText(waterAmountText, progress);


//                restart the under water amount
//                underCupAmount = 50;
                // Convert to String
                String firstCupPart = String.valueOf(underCupAmount);
                // Create a SpannableString combining 2 parts
                SpannableString spannableCup = new SpannableString(firstCupPart + " ml");
                // Set the SpannableString to the TextView
                cupText.setText(spannableCup);
                /////////////the second parameter should be how much user should drink
                myView.setProgress(progress, maxHydration);/////////here we define the max hydration amount
            }
        });





    }

    private void setWaterAmountText(TextView waterAmountText, float progress) {
        String firstPart = String.valueOf(progress);
        String secondPart = " ml of water";
        // Create a SpannableString combining both parts
        SpannableString spannable = new SpannableString(firstPart + secondPart);
        // Get the app color from resources
        int appColor = getResources().getColor(R.color.appColor);
        // Set the first part (progress) to app color
        spannable.setSpan(new ForegroundColorSpan(appColor), 0, firstPart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // Set the second part (water) to black
        spannable.setSpan(new ForegroundColorSpan(Color.BLACK), firstPart.length(), spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // Set the SpannableString to the TextView
        waterAmountText.setText(spannable);
    }
}