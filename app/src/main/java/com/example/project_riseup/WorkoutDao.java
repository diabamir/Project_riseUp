package com.example.project_riseup;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface WorkoutDao {
    @Insert
    void insertWorkout(Workout workout);

    @Query("SELECT * FROM workouts")
    LiveData<List<Workout>> getAllWorkouts();

    @Query("SELECT * FROM workouts WHERE id = :workoutId")
    Workout getWorkoutById(int workoutId);

    @Update
    void updateWorkout(Workout workout);

    @Delete
    void deleteWorkout(Workout workout);

    @Query("SELECT * FROM workouts WHERE activityName = :workoutName")
    Workout getWorkoutByName(String workoutName);

    @Query("SELECT * FROM workouts WHERE description = :workoutDescription")
    Workout getWorkoutByDescription(String workoutDescription);

    @Query("SELECT * FROM workouts WHERE times = :workoutTimes")
    Workout getWorkoutByTimes(int workoutTimes);

    @Query("SELECT * FROM workouts WHERE cals = :workoutCals")
    Workout getWorkoutByCals(int workoutCals);

    @Query("SELECT * FROM workouts WHERE level = :workoutLevel")
    Workout getWorkoutByLevel(String workoutLevel);




}
//package com.example.project_riseup;
//
//import androidx.lifecycle.LiveData;
//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.Query;
//import androidx.room.Update;
//
//import java.util.List;
//@Dao
//public interface WorkoutDao {
//    @Insert
//    void insertWorkout(Workout workout);
//
//    @Query("SELECT * FROM workouts")
//    LiveData<List<Workout>> getAllWorkouts();
//
//    @Query("SELECT * FROM workouts WHERE id = :workoutId")
//    Workout getWorkoutById(int workoutId);
//
//    @Update
//    void updateWorkout(Workout workout);
//
//    @Delete
//    void deleteWorkout(Workout workout);
//
//    @Query("SELECT * FROM workouts WHERE activityName = :workoutName")
//    Workout getWorkoutByName(String workoutName);
//
//    @Query("SELECT * FROM workouts WHERE description = :workoutDescription")
//    Workout getWorkoutByDescription(String workoutDescription);
//
//    @Query("SELECT * FROM workouts WHERE times = :workoutTimes")
//    Workout getWorkoutByTimes(int workoutTimes);
//
//    @Query("SELECT * FROM workouts WHERE cals = :workoutCals")
//    Workout getWorkoutByCals(int workoutCals);
//
//    @Query("SELECT * FROM workouts WHERE level = :workoutLevel")
//    Workout getWorkoutByLevel(String workoutLevel);
//
//
//
//
//}
