package com.example.project_riseup;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Steps.class}, version = 2, exportSchema = false)  // Set the version to 2
@TypeConverters({DateConverter.class})
public abstract class StepsDatabase extends RoomDatabase {

    private static StepsDatabase instance;

    // Abstract method to get the DAO
    public abstract StepsDAO stepsDao();

    // Singleton pattern to get the instance of the database
    public static synchronized StepsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            StepsDatabase.class, "steps_database")
                    .fallbackToDestructiveMigration()  // Clears the database if there is a schema change
                    .build();
        }
        return instance;
    }
}