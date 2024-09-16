package com.example.project_riseup;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {UserGroupJoin.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class UserGroupJoinDatabase extends RoomDatabase {

    private static UserGroupJoinDatabase instance;

    public abstract UserGroupJoinDao UserGroupJoinDao();

    public static synchronized UserGroupJoinDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            UserGroupJoinDatabase.class, "user_group_join_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}