package com.example.project_riseup;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

@Dao
public interface StepsDAO {

    // Insert a new step record or update it if the date already exists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdateStep(Steps step);

    // Retrieve steps taken within a date range (used for fetching steps for the last week)
    @Query("SELECT * FROM steps WHERE date >= :startDate AND date <= :endDate")
    List<Steps> findStepsByDateRange(Date startDate, Date endDate);

    @Query("SELECT * FROM steps WHERE date >= :today")
    List<Steps> findStepsForToday(Date today);
    // Add a method to retrieve steps for a specific date
    @Query("SELECT * FROM steps WHERE date(date) = date(:date)")
    Steps findStepsByDate(java.sql.Date date);
}
