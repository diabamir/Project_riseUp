package com.example.project_riseup;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Hydration.class}, version = 1)
public abstract class HydrationDatabase extends RoomDatabase {

    public abstract HydrationDao hydrationDao();

    private static volatile HydrationDatabase INSTANCE;

    public static HydrationDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (HydrationDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            HydrationDatabase.class,
                            "hydration_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
