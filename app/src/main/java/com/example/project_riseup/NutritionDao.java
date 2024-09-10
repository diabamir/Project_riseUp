package com.example.project_riseup;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface NutritionDao {

    // Method to insert a Nutrition item
    @Insert
    void insert(Nutrition nutrition);

    // Method to retrieve all Nutrition items
    @Query("SELECT * FROM Nutrition")
    LiveData<List<Nutrition>> getAllNutritionItems();


    @Query("SELECT * FROM nutrition WHERE id = :nutritionId")
    Nutrition getNutritionById(int nutritionId);

    @Update
    void updateNutrition(Nutrition nutrition);

    @Delete
    void deleteNutrition(Nutrition nutrition);

    @Query("SELECT * FROM nutrition WHERE name = :nutritionName")
    Nutrition getNutritionByName(String nutritionName);


}
