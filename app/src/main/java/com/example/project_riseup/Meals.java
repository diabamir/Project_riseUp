package com.example.project_riseup;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

public class Meals extends AppCompatActivity {

    LinearLayout mealsContainer;
    Button addMealButton;
    int mealCount = 1;  // Keep track of how many meals have been added

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);

        // Find the meals container layout and the add button
        mealsContainer = findViewById(R.id.mealsContainer);  // Make sure this exists in your activity XML
        addMealButton = findViewById(R.id.addMealButton);  // Add this button in your layout XML

        // Set up the click listener for the "Add Meal" button
        addMealButton.setOnClickListener(v -> addNewMealCard());
    }

    private void addNewMealCard() {
        // Inflate a new card view for the meal
        LayoutInflater inflater = LayoutInflater.from(this);
        View newMealCard = inflater.inflate(R.layout.meals_card, mealsContainer, false);

        // Update the meal card with dynamic content
        TextView mealTitle = newMealCard.findViewById(R.id.mealTitle);  // Change the id from 'mealTitle'
        mealTitle.setText("Meal " + mealCount);  // Set the meal number dynamically

        // Set the click listener for the recipe button
        Button recipeButton = newMealCard.findViewById(R.id.recipe);
        LinearLayout hiddenView = newMealCard.findViewById(R.id.hiddenview);

        recipeButton.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition((MaterialCardView) newMealCard);
            hiddenView.setVisibility(hiddenView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        });

        // Add the new meal card to the meals container
        mealsContainer.addView(newMealCard);

        // Increment the meal count
        mealCount++;
    }
}
//package com.example.project_riseup;
//
//import android.os.Bundle;
//import android.transition.TransitionManager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.google.android.material.card.MaterialCardView;
//
//public class Meals extends AppCompatActivity {
//
//    LinearLayout mealsContainer;
//    Button addMealButton;
//    int mealCount = 1;  // Keep track of how many meals have been added
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_meals);
//
//        // Find the meals container layout and the add button
//        mealsContainer = findViewById(R.id.mealsContainer);  // Make sure this exists in your activity XML
//        addMealButton = findViewById(R.id.addMealButton);  // Add this button in your layout XML
//
//        // Set up the click listener for the "Add Meal" button
//        addMealButton.setOnClickListener(v -> addNewMealCard());
//    }
//
//    private void addNewMealCard() {
//        // Inflate a new card view for the meal
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View newMealCard = inflater.inflate(R.layout.meals_card, mealsContainer, false);
//
//        // Update the meal card with dynamic content
//        TextView mealTitle = newMealCard.findViewById(R.id.mealTitle);  // Change the id from 'mealTitle'
//        mealTitle.setText("Meal " + mealCount);  // Set the meal number dynamically
//
//        // Set the click listener for the recipe button
//        Button recipeButton = newMealCard.findViewById(R.id.recipe);
//        LinearLayout hiddenView = newMealCard.findViewById(R.id.hiddenview);
//
//        recipeButton.setOnClickListener(v -> {
//            TransitionManager.beginDelayedTransition((MaterialCardView) newMealCard);
//            hiddenView.setVisibility(hiddenView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
//        });
//
//        // Add the new meal card to the meals container
//        mealsContainer.addView(newMealCard);
//
//        // Increment the meal count
//        mealCount++;
//    }
//}
