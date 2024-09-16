package com.example.project_riseup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class StepsMain extends AppCompatActivity implements SensorEventListener {

    private TextView stepCounterText;
    private ProgressBar progressBar;
    private SensorManager sensorManager;
    private Sensor stepCounterSensor;
    private StepsDatabase database;
    private long userId;
    private int stepsTaken = 0;
    private int cumulativeStepCount = -1;
    private Button details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.steps_main);

        stepCounterText = findViewById(R.id.stepCounterText);
        progressBar = findViewById(R.id.progressBar);
        details = findViewById(R.id.details);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        database = StepsDatabase.getInstance(this);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        userId = sharedPreferences.getLong("USER_ID", -1);

        if (userId == -1) {
            return;
        }

        loadSavedSteps();

        details.setOnClickListener(v -> {
            Intent intentDetails = new Intent(StepsMain.this, DetailsActivity.class);
            intentDetails.putExtra("USER_ID", userId);
            startActivity(intentDetails);
        });

        Intent serviceIntent = new Intent(this, StepCountingService.class);
        serviceIntent.putExtra("USER_ID", userId);
        startService(serviceIntent);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            int sensorStepCount = (int) sensorEvent.values[0];

            if (cumulativeStepCount == -1) {
                cumulativeStepCount = sensorStepCount;
            }

            stepsTaken = sensorStepCount - cumulativeStepCount;
            updateStepUI();
        }
    }

    private void loadSavedSteps() {
        new Thread(() -> {
            Date today = getTodayDate();
            Steps savedSteps = database.stepsDao().findStepsByDate(today, userId);

            if (savedSteps != null) {
                stepsTaken = savedSteps.getStepsTaken();
                cumulativeStepCount = savedSteps.getInitialStepCount();
                runOnUiThread(this::updateStepUI);
            }
        }).start();
    }

    private void updateStepUI() {
        runOnUiThread(() -> {
            stepCounterText.setText("Steps: " + stepsTaken);
            progressBar.setProgress(stepsTaken);
        });
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
        saveSteps();
    }

    private void saveSteps() {
        new Thread(() -> {
            Date today = getTodayDate();
            Steps steps = new Steps(today, cumulativeStepCount, stepsTaken, userId);
            database.stepsDao().insertOrUpdateStep(steps);
        }).start();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}
}
