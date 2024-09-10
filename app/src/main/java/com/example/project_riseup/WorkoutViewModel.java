package com.example.project_riseup;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WorkoutViewModel extends AndroidViewModel {

    private WorkoutDao workoutDao;
    private LiveData<List<Workout>> allWorkouts;

    public WorkoutViewModel(Application application) {
        super(application);
        WorkoutDatabase db = WorkoutDatabase.getInstance(application);
        workoutDao = db.workoutDao();
        allWorkouts = workoutDao.getAllWorkouts();
    }

    public LiveData<List<Workout>> getAllWorkouts() {
        return allWorkouts;
    }


}
