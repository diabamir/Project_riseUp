package com.example.project_riseup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class mealActivity extends AppCompatActivity {

    private mealDatabase db;
    private MealAdapter adapter;
    private RecyclerView recyclerView;
    private List<Meal> meals = new ArrayList<>();
    private List<Meal> selectedMeals = new ArrayList<>(); // Track selected meals
    private ExecutorService executorService;
    private Handler mainThreadHandler;
    private Button recommendButton; // Button to request recommendations

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_activity);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recommendButton = findViewById(R.id.recommendButton); // The new button

        db = Room.databaseBuilder(getApplicationContext(), mealDatabase.class, "meal_db").build();
        executorService = Executors.newSingleThreadExecutor();
        mainThreadHandler = new Handler(Looper.getMainLooper());

        adapter = new MealAdapter(meals, (meal, isChecked) -> {
            meal.setChecked(isChecked);
            if (isChecked) {
                selectedMeals.add(meal); // Add selected meal to list
            } else {
                selectedMeals.remove(meal); // Remove unselected meal
            }
            Toast.makeText(mealActivity.this, meal.getMealName() + " liked: " + isChecked, Toast.LENGTH_SHORT).show();
        });

        recyclerView.setAdapter(adapter);

        // Set click listener on the recommend button
        recommendButton.setOnClickListener(v -> {
            if (selectedMeals.isEmpty()) {
                Toast.makeText(mealActivity.this, "Please select at least one meal!", Toast.LENGTH_SHORT).show();
            } else {
                recommendMealsBasedOnSelection(selectedMeals);
            }
        });

        // Load meals either from database or CSV
        loadMealsFromDatabaseOrCSV();
    }

    private void loadMealsFromDatabaseOrCSV() {
        executorService.execute(() -> {
            List<Meal> mealList = db.mealDao().getAllMeals();
            if (mealList.isEmpty()) {
                // If database is empty, load from CSV
                loadMealsFromCSV();
            } else {
                // Otherwise, recommend meals from the database
                recommendRandomMeals();
            }
        });
    }

    private void loadMealsFromCSV() {
        try {
            Resources res = getResources();
            InputStream inputStream = res.openRawResource(R.raw.meals); // Ensure meals.csv is in res/raw
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            reader.readLine(); // Skip the header line

            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length == 10) { // Assuming there are 10 columns in the CSV
                    Meal meal = new Meal();
                    meal.setId(Integer.parseInt(columns[0]));
                    meal.setMealName(columns[1]);
                    meal.setIngredient1(columns[2]);
                    meal.setIngredient2(columns[3]);
                    meal.setIngredient3(columns[4]);
                    meal.setIngredient4(columns[5]);
                    meal.setQuantity1(columns[6]);
                    meal.setQuantity2(columns[7]);
                    meal.setQuantity3(columns[8]);
                    meal.setQuantity4(columns[9]);

                    // Insert into database
                    db.mealDao().insertMeal(meal);
                }
            }
            reader.close();

            // After loading from CSV, recommend meals
            recommendRandomMeals();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void recommendRandomMeals() {
        executorService.execute(() -> {
            List<Meal> randomMeals = db.mealDao().getRandomMeals();
            mainThreadHandler.post(() -> {
                meals.clear();
                meals.addAll(randomMeals);
                adapter.notifyDataSetChanged();
            });
        });
    }

    private void recommendMealsBasedOnSelection(List<Meal> selectedMeals) {
        executorService.execute(() -> {
            List<Meal> allMeals = db.mealDao().getAllMeals();
            List<Meal> recommendedMeals = getRecommendationsBasedOnIngredients(selectedMeals, allMeals);

            mainThreadHandler.post(() -> {
                meals.clear();
                meals.addAll(recommendedMeals);
                adapter.notifyDataSetChanged();
            });
        });
    }

    private List<Meal> getRecommendationsBasedOnIngredients(List<Meal> selectedMeals, List<Meal> allMeals) {
        List<Meal> recommendedMeals = new ArrayList<>();
        for (Meal meal : allMeals) {
            if (!meal.isChecked()) { // Only recommend meals that aren't already selected
                for (Meal selectedMeal : selectedMeals) {
                    int similarity = getIngredientSimilarity(selectedMeal, meal);
                    if (similarity > 0) { // Add meals that share at least one ingredient
                        recommendedMeals.add(meal);
                        break; // Avoid duplicates
                    }
                }
            }
        }
        return recommendedMeals.size() > 5 ? recommendedMeals.subList(0, 5) : recommendedMeals;
    }

    private int getIngredientSimilarity(Meal meal1, Meal meal2) {
        int score = 0;
        if (meal1.getIngredient1().equals(meal2.getIngredient1()) || meal1.getIngredient2().equals(meal2.getIngredient2()) ||
                meal1.getIngredient3().equals(meal2.getIngredient3()) || meal1.getIngredient4().equals(meal2.getIngredient4())) {
            score++;
        }
        return score;
    }
}
