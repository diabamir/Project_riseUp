package com.example.project_riseup;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExercise(Exercise exercise);

    @Query("SELECT * FROM exercises WHERE isChecked = 1")
    List<Exercise> getLikedExercises();

    @Query("SELECT * FROM exercises")
    List<Exercise> getAllExercises();

    @Query("DELETE FROM exercises")
    void deleteAllExercises();
}
