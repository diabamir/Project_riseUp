package com.example.project_riseup;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities = {Workout.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class WorkoutDatabase extends RoomDatabase {
    private static WorkoutDatabase instance;

    public abstract WorkoutDao workoutDao();

    public static synchronized WorkoutDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            WorkoutDatabase.class, "workout_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
