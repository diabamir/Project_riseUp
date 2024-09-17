package com.example.project_riseup;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Meal.class}, version = 1)
public abstract class mealDatabase extends RoomDatabase {
    public abstract MealDao mealDao();
}
