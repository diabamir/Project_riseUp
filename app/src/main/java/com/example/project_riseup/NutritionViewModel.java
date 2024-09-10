package com.example.project_riseup;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NutritionViewModel extends AndroidViewModel {

    private NutritionDao nutritionDao;
    private LiveData<List<Nutrition>> allNutritionItems;
    private ExecutorService executorService;

    public NutritionViewModel(Application application) {
        super(application);

        // Initialize database and DAO directly here
        NutritionDatabase db = NutritionDatabase.getDatabase(application);
        nutritionDao = db.nutritionDao();
        allNutritionItems = nutritionDao.getAllNutritionItems();

        // Use ExecutorService to perform database operations asynchronously
        executorService = Executors.newSingleThreadExecutor();
    }

    // Method to insert a Nutrition item directly using the DAO
    public void insert(Nutrition nutrition) {
        executorService.execute(() -> nutritionDao.insert(nutrition));
    }

    // Method to get all Nutrition items as LiveData
    public LiveData<List<Nutrition>> getAllNutritionItems() {
        return allNutritionItems;
    }
}
