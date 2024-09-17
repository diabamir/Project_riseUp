package com.example.project_riseup;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(Meal meal);

    @Query("SELECT * FROM meals")
    List<Meal> getAllMeals();

    @Query("SELECT * FROM meals WHERE isChecked = 1")
    List<Meal> getLikedMeals(); // Query to get liked meals

    @Query("SELECT * FROM meals ORDER BY RANDOM() LIMIT 5")
    List<Meal> getRandomMeals(); // Query to get 5 random meals
}
