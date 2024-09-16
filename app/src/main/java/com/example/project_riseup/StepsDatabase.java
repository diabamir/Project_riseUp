package com.example.project_riseup;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Steps.class}, version = 2, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class StepsDatabase extends RoomDatabase {

    private static StepsDatabase instance;

    public abstract StepsDAO stepsDao();

    public static synchronized StepsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            StepsDatabase.class, "steps_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
