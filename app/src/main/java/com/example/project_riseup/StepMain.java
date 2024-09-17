package com.example.project_riseup;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StepMain extends AppCompatActivity implements SensorEventListener {

    private TextView stepCounterText;
    private TextView distanceText;
    private SensorManager sensorManager;
    private Sensor stepCounterSensor;
    private int initialStepCount = -1;
    private int stepsTaken = 0;
    private ProgressBar progressBar;
    private float stepLengthInMeter = 0.762f;
    private int stepCountTarget = 200;
    private TextView stepCountTargetTextview;
    private Button details;
    private Button calendarButton;

    // Database instance
    private StepsDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_main);

        stepCounterText = findViewById(R.id.stepCounterText);
        distanceText = findViewById(R.id.distanceText1);
        details = findViewById(R.id.details);
        calendarButton = findViewById(R.id.calendarButton);
        stepCountTargetTextview = findViewById(R.id.stepCountTargetTextview);
        progressBar = findViewById(R.id.progressBar);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        // Initialize Room database
        database = StepsDatabase.getInstance(this);

        progressBar.setMax(stepCountTarget);
        stepCountTargetTextview.setText("Step Goal: " + stepCountTarget);

        // Load the initial step count (if exists) when the app starts
        loadInitialStepCount();

        // Open details activity when "details" button is clicked
        details.setOnClickListener(v -> {
            Intent intent = new Intent(StepMain.this, DetailsActivity.class);
            startActivity(intent);
        });

        // Open calendar activity when "calendarButton" is clicked
        calendarButton.setOnClickListener(v -> {
            Intent intent = new Intent(StepMain.this, CalendarActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            int cumulativeStepCount = (int) sensorEvent.values[0];

            if (initialStepCount == -1) {
                // Set the initial step count from the sensor
                initialStepCount = cumulativeStepCount;
                saveInitialStepCount();
            }

            stepsTaken = cumulativeStepCount - initialStepCount;

            stepCounterText.setText("Steps: " + stepsTaken);
            progressBar.setProgress(stepsTaken);

            if (stepsTaken >= stepCountTarget) {
                stepCountTargetTextview.setText("Step target achieved!");
            }

            float distanceInKm = (stepsTaken * stepLengthInMeter) / 1000;
            distanceText.setText(String.format(Locale.getDefault(), "Distance: %.2f km", distanceInKm));

            // Save steps taken for today
            saveStepsTaken();
        }
    }

    private void saveStepsTaken() {
        // Save today's steps to Room database
        Date today = getTodayDate();
        Steps steps = new Steps(today, initialStepCount, stepsTaken);

        new Thread(() -> {
            database.stepsDao().insertOrUpdateStep(steps);
        }).start();
    }

    private void saveInitialStepCount() {
        // Save the initial step count to SharedPreferences
        getSharedPreferences("StepPrefs", MODE_PRIVATE)
                .edit()
                .putInt("initialStepCount", initialStepCount)
                .apply();
    }

    private void loadInitialStepCount() {
        // Load the initial step count from SharedPreferences
        initialStepCount = getSharedPreferences("StepPrefs", MODE_PRIVATE)
                .getInt("initialStepCount", -1);
    }

    private Date getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (stepCounterSensor != null) {
            sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (stepCounterSensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
