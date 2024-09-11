package com.example.project_riseup;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Group.class}, version = 2, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class groupDataBase extends RoomDatabase {

    private static groupDataBase instance;

    public abstract groupDAO groupDao();

    public static synchronized groupDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            groupDataBase.class, "group_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

