package com.example.project_riseup;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StepsHelper {

    private final StepsDatabase database;
    private final Handler mainThreadHandler;

    public StepsHelper(Context context) {
        this.database = StepsDatabase.getInstance(context);
        this.mainThreadHandler = new Handler(Looper.getMainLooper());
    }

    // Function to fetch today's steps and update the TextView
    public void fetchTodaySteps(TextView stepsTextView) {
        Date today = getStartOfDay(new Date());
        Date tomorrow = getStartOfDay(addDays(today, 1)); // Fetch range to avoid time issues

        new Thread(() -> {
            List<Steps> todayStepsList = database.stepsDao().findStepsByDateRange(today, tomorrow);

            // Update UI on the main thread
            mainThreadHandler.post(() -> {
                if (todayStepsList != null && !todayStepsList.isEmpty()) {
                    Steps todaySteps = todayStepsList.get(0); // Get the first entry
                    int stepsTakenToday = todaySteps.getStepsTaken();
                    Log.d("StepsHelper", "Steps taken today: " + stepsTakenToday);
                    stepsTextView.setText(String.format("%d steps", stepsTakenToday));
                } else {
                    Log.d("StepsHelper", "No steps data available for today.");
                    stepsTextView.setText("0 steps"); // Default if no data
                }
            });
        }).start();
    }

    // Helper function to get the start of the day (midnight) for a given date
    private Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    // Helper function to add days to a date
    private Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }
}
