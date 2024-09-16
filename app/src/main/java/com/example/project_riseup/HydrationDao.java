package com.example.project_riseup;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HydrationDao {

    @Query("SELECT * FROM hydration")
    List<Hydration> getAllHydrationRecords();

    @Query("SELECT * FROM hydration WHERE userId = :userId")
    List<Hydration> getHydrationRecordsByUserId(int userId);

    @Insert
    void insertHydration(Hydration hydration);

    @Update
    void updateHydration(Hydration hydration);

    @Delete
    void deleteHydration(Hydration hydration);
}
