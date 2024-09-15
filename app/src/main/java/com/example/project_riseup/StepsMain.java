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

public class StepsMain extends AppCompatActivity implements SensorEventListener {

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
    private StepsDatabase database;
    private Button details;
    private Button calendarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.steps_main);  // Updated to match the XML file

        stepCounterText = findViewById(R.id.stepCounterText);
        distanceText = findViewById(R.id.distanceText);
        details = findViewById(R.id.details);
        calendarButton = findViewById(R.id.calendarButton);
        stepCountTargetTextview = findViewById(R.id.stepCountTargetTextview);
        progressBar = findViewById(R.id.progressBar);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        database = StepsDatabase.getInstance(this);

        progressBar.setMax(stepCountTarget);
        stepCountTargetTextview.setText("Step Goal: " + stepCountTarget);

        Intent serviceIntent = new Intent(this, StepCountingService.class);
        startService(serviceIntent); // Use startService for API 21

        details.setOnClickListener(v -> {
            Intent intent = new Intent(StepsMain.this, DetailsActivity.class);
            startActivity(intent);
        });

        calendarButton.setOnClickListener(v -> {
            Intent intent = new Intent(StepsMain.this, CalendarActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            int cumulativeStepCount = (int) sensorEvent.values[0];

            if (initialStepCount == -1) {
                // First reading, set as initial step count
                initialStepCount = cumulativeStepCount;
                saveInitialStepCount();
            }

            // Calculate steps taken since initial step count
            stepsTaken = cumulativeStepCount - initialStepCount;

            // Update UI
            stepCounterText.setText("Steps: " + stepsTaken);
            progressBar.setProgress(stepsTaken);

            if (stepsTaken >= stepCountTarget) {
                stepCountTargetTextview.setText("Step target achieved!");
            }

            float distanceInKm = (stepsTaken * stepLengthInMeter) / 1000;
            distanceText.setText(String.format(Locale.getDefault(), "Distance: %.2f km", distanceInKm));

            // Save steps taken to database
            saveStepsTaken();
        }
    }

    private void saveInitialStepCount() {
        Date today = getTodayDate();
        Steps steps = new Steps(today, initialStepCount, 0);
        new Thread(() -> database.stepsDao().insertOrUpdateStep(steps)).start();
    }

    private void saveStepsTaken() {
        Date today = getTodayDate();
        Steps steps = new Steps(today, initialStepCount, stepsTaken);
        new Thread(() -> database.stepsDao().insertOrUpdateStep(steps)).start();
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
    protected void onStop() {
        super.onStop();
        if (stepCounterSensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop the foreground service when the app is destroyed
        Intent serviceIntent = new Intent(this, StepCountingService.class);
        stopService(serviceIntent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}
}
