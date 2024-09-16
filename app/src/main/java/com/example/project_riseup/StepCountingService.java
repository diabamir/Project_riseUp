package com.example.project_riseup;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;

public class StepCountingService extends Service implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor stepCounterSensor;
    private StepsDatabase database;
    private long userId;
    private int initialStepCount = -1;
    private int stepsTaken = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        database = StepsDatabase.getInstance(this);

        if (stepCounterSensor != null) {
            sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            userId = intent.getLongExtra("USER_ID", -1);
        }
        return START_STICKY;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            int cumulativeStepCount = (int) event.values[0];

            if (initialStepCount == -1) {
                initialStepCount = cumulativeStepCount;
            }

            stepsTaken = cumulativeStepCount - initialStepCount;

            saveStepsTaken();
        }
    }

    private void saveStepsTaken() {
        Date today = getTodayDate();

        new Thread(() -> {
            User user = database.userDao().getUserById(userId);
            if (user != null) {
                Steps existingSteps = database.stepsDao().findStepsByDate(today, userId);
                if (existingSteps != null) {
                    existingSteps.setStepsTaken(stepsTaken);
                    existingSteps.setInitialStepCount(initialStepCount);
                    database.stepsDao().insertOrUpdateStep(existingSteps);
                } else {
                    Steps newSteps = new Steps(today, initialStepCount, stepsTaken, userId);
                    database.stepsDao().insertOrUpdateStep(newSteps);
                }
                Log.d("StepCountingService", "Steps saved: " + stepsTaken);
            } else {
                Log.e("StepCountingService", "Error: User not found for userId: " + userId);
            }
        }).start();
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
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
