package com.example.project_riseup;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

@Dao
public interface StepsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdateStep(Steps steps);

    @Query("SELECT * FROM steps WHERE date = :date AND userId = :userId")
    Steps findStepsByDate(Date date, long userId);

    @Query("SELECT * FROM steps WHERE userId = :userId AND date BETWEEN :startDate AND :endDate")
    List<Steps> findStepsByDateRange(Date startDate, Date endDate, long userId);
}
