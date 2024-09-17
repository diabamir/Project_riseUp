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
    void insertOrUpdateStep(Steps step);

    @Query("SELECT * FROM steps WHERE date >= :startDate AND date <= :endDate")
    List<Steps> findStepsByDateRange(Date startDate, Date endDate);

    @Query("SELECT * FROM steps WHERE date(date) = date(:date)")
    Steps findStepsByDate(Date date);


}
