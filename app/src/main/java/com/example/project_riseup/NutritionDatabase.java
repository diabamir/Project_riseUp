package com.example.project_riseup;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Define the database, listing the Nutrition entity and version number
@Database(entities = {Nutrition.class}, version = 1, exportSchema = false)
public abstract class NutritionDatabase extends RoomDatabase {

    // Provide access to the DAO
    public abstract NutritionDao nutritionDao();

    // Singleton instance to prevent multiple instances of the database being created
    private static volatile NutritionDatabase INSTANCE;

    // Method to get the instance of the database
    public static NutritionDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NutritionDatabase.class) {
                if (INSTANCE == null) {
                    // Build the database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    NutritionDatabase.class, "nutrition_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
//package com.example.project_riseup;
//
//import android.content.Context;
//
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//
//// Define the database, listing the Nutrition entity and version number
//@Database(entities = {Nutrition.class}, version = 1, exportSchema = false)
//public abstract class NutritionDatabase extends RoomDatabase {
//
//    // Provide access to the DAO
//    public abstract NutritionDao nutritionDao();
//
//    // Singleton instance to prevent multiple instances of the database being created
//    private static volatile NutritionDatabase INSTANCE;
//
//    // Method to get the instance of the database
//    public static NutritionDatabase getDatabase(final Context context) {
//        if (INSTANCE == null) {
//            synchronized (NutritionDatabase.class) {
//                if (INSTANCE == null) {
//                    // Build the database
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                                    NutritionDatabase.class, "nutrition_database")
//                            .build();
//                }
//            }
//        }
//        return INSTANCE;
//    }
//}
