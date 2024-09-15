package com.example.project_riseup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class NutritionSuggestion extends AppCompatActivity {

    private NutritionViewModel nutritionViewModel;
    private ArrayList<Nutrition> nutritionList = new ArrayList<>();  // ArrayList to store selected items
    Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_suggestion); // Replace with your layout if different

        // Initialize ViewModel
        nutritionViewModel = new ViewModelProvider(this).get(NutritionViewModel.class);

        // Add OnClickListeners for each product button
        addButtonListeners();

        // Handle "Continue" button click
        continueButton = findViewById(R.id.Continue_button);
        continueButton.setOnClickListener(v -> {
            Toast.makeText(this, "Products saved!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);  // Navigate to MainActivity
        });
    }

    // Helper method to convert drawable to byte array
    private byte[] drawableToByteArray(int drawableId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawableId);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private void addButtonListeners() {
        // Tomato button
        Button tomatoButton = findViewById(R.id.Tomatobutton);
        tomatoButton.setOnClickListener(v -> saveNutritionItem("Tomato", R.drawable.image_1599));

        // Cucumber button
        Button cucumberButton = findViewById(R.id.Cucumberobutton);
        cucumberButton.setOnClickListener(v -> saveNutritionItem("Cucumber", R.drawable.image_1620));

        // Potato button
        Button potatoButton = findViewById(R.id.potato_image);
        potatoButton.setOnClickListener(v -> saveNutritionItem("Potato", R.drawable.image_1609));

        // Pepper button
        Button pepperButton = findViewById(R.id.Pepperbutton);
        pepperButton.setOnClickListener(v -> saveNutritionItem("Pepper", R.drawable.image_1619));

        // Carrot button
        Button carrotButton = findViewById(R.id.carrotobutton);
        carrotButton.setOnClickListener(v -> saveNutritionItem("Carrot", R.drawable.image_1613));

        // Lettuce button
        Button lettuceButton = findViewById(R.id.lettuce_image);
        lettuceButton.setOnClickListener(v -> saveNutritionItem("Lettuce", R.drawable.image_1621));

        // Eggplant button
        Button eggplantButton = findViewById(R.id.Eggplantbutton);
        eggplantButton.setOnClickListener(v -> saveNutritionItem("Eggplant", R.drawable.image_1607));

        // Onion button
        Button onionButton = findViewById(R.id.onion_image);
        onionButton.setOnClickListener(v -> saveNutritionItem("Onion", R.drawable.image_1625));

        // Rice button
        Button riceButton = findViewById(R.id.RICE_button);
        riceButton.setOnClickListener(v -> saveNutritionItem("Rice", R.drawable.image_1719));

        // Spaghetti button
        Button spaghettiButton = findViewById(R.id.Spaghetti_button);
        spaghettiButton.setOnClickListener(v -> saveNutritionItem("Spaghetti", R.drawable.image_1723));

        // Oats button
        Button oatsButton = findViewById(R.id.oats_button);
        oatsButton.setOnClickListener(v -> saveNutritionItem("Oats", R.drawable.oats));

        // Bread button
        Button breadButton = findViewById(R.id.bread_button);
        breadButton.setOnClickListener(v -> saveNutritionItem("Bread", R.drawable.image_1633));

        // Egg button
        Button eggsButton = findViewById(R.id.eggs_button);
        eggsButton.setOnClickListener(v -> saveNutritionItem("Egg", R.drawable.image_1685));

        // Chicken button
        Button chickenButton = findViewById(R.id.chicken_button);
        chickenButton.setOnClickListener(v -> saveNutritionItem("Chicken", R.drawable.image_1652));

        // Beef button
        Button beefButton = findViewById(R.id.beef_button);
        beefButton.setOnClickListener(v -> saveNutritionItem("Beef", R.drawable.image_1651));

        // Salmon button
        Button salmonButton = findViewById(R.id.salmon_button);
        salmonButton.setOnClickListener(v -> saveNutritionItem("Salmon", R.drawable.image_1727));

        // Sea bass button
        Button seaBassButton = findViewById(R.id.seabass_button);
        seaBassButton.setOnClickListener(v -> saveNutritionItem("Sea bass", R.drawable.image_1299));

        // Tuna button
        Button tunaButton = findViewById(R.id.tuna_button);
        tunaButton.setOnClickListener(v -> saveNutritionItem("Tuna", R.drawable.open_tuna_can_seen_from));

        // Shrimp button
        Button shrimpButton = findViewById(R.id.Shrimps_button);
        shrimpButton.setOnClickListener(v -> saveNutritionItem("Shrimps", R.drawable.image_1732));

        // Milk button
        Button milkButton = findViewById(R.id.Milk_button);
        milkButton.setOnClickListener(v -> saveNutritionItem("Milk", R.drawable.image_1712));

        // Cheese button
        Button cheeseButton = findViewById(R.id.Cheese_button);
        cheeseButton.setOnClickListener(v -> saveNutritionItem("Cheese", R.drawable.image_1646));

        // Yogurt button
        Button yogurtButton = findViewById(R.id.Yogurt_button);
        yogurtButton.setOnClickListener(v -> saveNutritionItem("Yogurt", R.drawable.greek_yogurt_wooden_bowl_isolated_white_background));

        // Avocado button
        Button avocadoButton = findViewById(R.id.Avocado_button);
        avocadoButton.setOnClickListener(v -> saveNutritionItem("Avocado", R.drawable.image_1605));

        // Apple button
        Button appleButton = findViewById(R.id.Apple_button);
        appleButton.setOnClickListener(v -> saveNutritionItem("Apple", R.drawable.image_1587));

        // Banana button
        Button bananaButton = findViewById(R.id.banana_button);
        bananaButton.setOnClickListener(v -> saveNutritionItem("Banana", R.drawable.image_1578));

        // Berries button
        Button berriesButton = findViewById(R.id.berries_button);
        berriesButton.setOnClickListener(v -> saveNutritionItem("Berries", R.drawable.image_1594));

        // Mango button
        Button mangoButton = findViewById(R.id.Mango_button);
        mangoButton.setOnClickListener(v -> saveNutritionItem("Mango", R.drawable.image_1748));

        // Orange button
        Button orangeButton = findViewById(R.id.Orange_button);
        orangeButton.setOnClickListener(v -> saveNutritionItem("Orange", R.drawable.image_1572__2_));

        // Peach button
        Button peachButton = findViewById(R.id.peach_button);
        peachButton.setOnClickListener(v -> saveNutritionItem("Peach", R.drawable.image_1750__1_));
    }

    // Method to save a Nutrition item to the ArrayList and the database via ViewModel
    private void saveNutritionItem(String name, int drawableId) {
        byte[] image = drawableToByteArray(drawableId);
        Nutrition nutrition = new Nutrition(0, name, image);

        // Add to ArrayList
        nutritionList.add(nutrition);

        // Save the nutrition item using the ViewModel
        nutritionViewModel.insert(nutrition);

        // Show a toast message to confirm the product has been added
        Toast.makeText(this, name + " added to list", Toast.LENGTH_SHORT).show();
    }
}
//package com.example.project_riseup;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import androidx.lifecycle.ViewModelProvider;
//
//import java.io.ByteArrayOutputStream;
//import java.util.ArrayList;
//
//public class NutritionSuggestion extends AppCompatActivity {
//
//    private NutritionViewModel nutritionViewModel;
//    private ArrayList<Nutrition> nutritionList = new ArrayList<>();  // ArrayList to store selected items
//    Button continueButton;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_nutrition_suggestion); // Replace with your layout if different
//
//        // Initialize ViewModel
//        nutritionViewModel = new ViewModelProvider(this).get(NutritionViewModel.class);
//
//        // Add OnClickListeners for each product button
//        addButtonListeners();
//
//        // Handle "Continue" button click
//        continueButton = findViewById(R.id.Continue_button);
//        continueButton.setOnClickListener(v -> {
//            Toast.makeText(this, "Products saved!", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);  // Navigate to MainActivity
//        });
//    }
//
//    // Helper method to convert drawable to byte array
//    private byte[] drawableToByteArray(int drawableId) {
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawableId);
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        return stream.toByteArray();
//    }
//
//    private void addButtonListeners() {
//        // Tomato button
//        Button tomatoButton = findViewById(R.id.Tomatobutton);
//        tomatoButton.setOnClickListener(v -> saveNutritionItem("Tomato", R.drawable.image_1599));
//
//        // Cucumber button
//        Button cucumberButton = findViewById(R.id.Cucumberobutton);
//        cucumberButton.setOnClickListener(v -> saveNutritionItem("Cucumber", R.drawable.image_1620));
//
//        // Potato button
//        Button potatoButton = findViewById(R.id.potato_image);
//        potatoButton.setOnClickListener(v -> saveNutritionItem("Potato", R.drawable.image_1609));
//
//        // Pepper button
//        Button pepperButton = findViewById(R.id.Pepperbutton);
//        pepperButton.setOnClickListener(v -> saveNutritionItem("Pepper", R.drawable.image_1619));
//
//        // Carrot button
//        Button carrotButton = findViewById(R.id.carrotobutton);
//        carrotButton.setOnClickListener(v -> saveNutritionItem("Carrot", R.drawable.image_1613));
//
//        // Lettuce button
//        Button lettuceButton = findViewById(R.id.lettuce_image);
//        lettuceButton.setOnClickListener(v -> saveNutritionItem("Lettuce", R.drawable.image_1621));
//
//        // Eggplant button
//        Button eggplantButton = findViewById(R.id.Eggplantbutton);
//        eggplantButton.setOnClickListener(v -> saveNutritionItem("Eggplant", R.drawable.image_1607));
//
//        // Onion button
//        Button onionButton = findViewById(R.id.onion_image);
//        onionButton.setOnClickListener(v -> saveNutritionItem("Onion", R.drawable.image_1625));
//
//        // Rice button
//        Button riceButton = findViewById(R.id.RICE_button);
//        riceButton.setOnClickListener(v -> saveNutritionItem("Rice", R.drawable.image_1719));
//
//        // Spaghetti button
//        Button spaghettiButton = findViewById(R.id.Spaghetti_button);
//        spaghettiButton.setOnClickListener(v -> saveNutritionItem("Spaghetti", R.drawable.image_1723));
//
//        // Oats button
//        Button oatsButton = findViewById(R.id.oats_button);
//        oatsButton.setOnClickListener(v -> saveNutritionItem("Oats", R.drawable.oats));
//
//        // Bread button
//        Button breadButton = findViewById(R.id.bread_button);
//        breadButton.setOnClickListener(v -> saveNutritionItem("Bread", R.drawable.image_1633));
//
//        // Egg button
//        Button eggsButton = findViewById(R.id.eggs_button);
//        eggsButton.setOnClickListener(v -> saveNutritionItem("Egg", R.drawable.image_1685));
//
//        // Chicken button
//        Button chickenButton = findViewById(R.id.chicken_button);
//        chickenButton.setOnClickListener(v -> saveNutritionItem("Chicken", R.drawable.image_1652));
//
//        // Beef button
//        Button beefButton = findViewById(R.id.beef_button);
//        beefButton.setOnClickListener(v -> saveNutritionItem("Beef", R.drawable.image_1651));
//
//        // Salmon button
//        Button salmonButton = findViewById(R.id.salmon_button);
//        salmonButton.setOnClickListener(v -> saveNutritionItem("Salmon", R.drawable.image_1727));
//
//        // Sea bass button
//        Button seaBassButton = findViewById(R.id.seabass_button);
//        seaBassButton.setOnClickListener(v -> saveNutritionItem("Sea bass", R.drawable.image_1299));
//
//        // Tuna button
//        Button tunaButton = findViewById(R.id.tuna_button);
//        tunaButton.setOnClickListener(v -> saveNutritionItem("Tuna", R.drawable.open_tuna_can_seen_from));
//
//        // Shrimp button
//        Button shrimpButton = findViewById(R.id.Shrimps_button);
//        shrimpButton.setOnClickListener(v -> saveNutritionItem("Shrimps", R.drawable.image_1732));
//
//        // Milk button
//        Button milkButton = findViewById(R.id.Milk_button);
//        milkButton.setOnClickListener(v -> saveNutritionItem("Milk", R.drawable.image_1712));
//
//        // Cheese button
//        Button cheeseButton = findViewById(R.id.Cheese_button);
//        cheeseButton.setOnClickListener(v -> saveNutritionItem("Cheese", R.drawable.image_1646));
//
//        // Yogurt button
//        Button yogurtButton = findViewById(R.id.Yogurt_button);
//        yogurtButton.setOnClickListener(v -> saveNutritionItem("Yogurt", R.drawable.greek_yogurt_wooden_bowl_isolated_white_background));
//
//        // Avocado button
//        Button avocadoButton = findViewById(R.id.Avocado_button);
//        avocadoButton.setOnClickListener(v -> saveNutritionItem("Avocado", R.drawable.image_1605));
//
//        // Apple button
//        Button appleButton = findViewById(R.id.Apple_button);
//        appleButton.setOnClickListener(v -> saveNutritionItem("Apple", R.drawable.image_1587));
//
//        // Banana button
//        Button bananaButton = findViewById(R.id.banana_button);
//        bananaButton.setOnClickListener(v -> saveNutritionItem("Banana", R.drawable.image_1578));
//
//        // Berries button
//        Button berriesButton = findViewById(R.id.berries_button);
//        berriesButton.setOnClickListener(v -> saveNutritionItem("Berries", R.drawable.image_1594));
//
//        // Mango button
//        Button mangoButton = findViewById(R.id.Mango_button);
//        mangoButton.setOnClickListener(v -> saveNutritionItem("Mango", R.drawable.image_1748));
//
//        // Orange button
//        Button orangeButton = findViewById(R.id.Orange_button);
//        orangeButton.setOnClickListener(v -> saveNutritionItem("Orange", R.drawable.image_1572__2_));
//
//        // Peach button
//        Button peachButton = findViewById(R.id.peach_button);
//        peachButton.setOnClickListener(v -> saveNutritionItem("Peach", R.drawable.image_1750__1_));
//    }
//
//    // Method to save a Nutrition item to the ArrayList and the database via ViewModel
//    private void saveNutritionItem(String name, int drawableId) {
//        byte[] image = drawableToByteArray(drawableId);
//        Nutrition nutrition = new Nutrition(0, name, image);
//
//        // Add to ArrayList
//        nutritionList.add(nutrition);
//
//        // Save the nutrition item using the ViewModel
//        nutritionViewModel.insert(nutrition);
//
//        // Show a toast message to confirm the product has been added
//        Toast.makeText(this, name + " added to list", Toast.LENGTH_SHORT).show();
//    }
//}
