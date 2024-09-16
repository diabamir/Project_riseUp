package com.example.project_riseup;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private BarChart barChart;
    private StepsDatabase database;
    private SimpleDateFormat sdf;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        barChart = findViewById(R.id.barChart);
        database = StepsDatabase.getInstance(this);

        sdf = new SimpleDateFormat("yyyy-MM-dd");
        userId = getIntent().getLongExtra("USER_ID", -1);

        fetchStepsDataAndDisplayChart();
    }

    private void fetchStepsDataAndDisplayChart() {
        new Thread(() -> {
            Date today = getTodayDate();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -6);
            Date lastWeek = calendar.getTime();

            List<Steps> stepsList = database.stepsDao().findStepsByDateRange(lastWeek, today, userId);

            Log.d("DetailsActivity", "Fetching steps between: " + lastWeek + " and " + today);

            runOnUiThread(() -> {
                if (stepsList != null && !stepsList.isEmpty()) {
                    displayBarChart(stepsList);
                } else {
                    barChart.setNoDataText("No steps data available for the past week.");
                    barChart.invalidate();
                }
            });
        }).start();
    }

    private void displayBarChart(List<Steps> stepsList) {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < 7; i++) {
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE, -i);
            Date day = calendar.getTime();
            String dateLabel = sdf.format(day);
            labels.add(dateLabel);

            int steps = 0;
            for (Steps step : stepsList) {
                if (sdf.format(step.getDate()).equals(dateLabel)) {
                    steps = step.getStepsTaken();
                    break;
                }
            }
            barEntries.add(new BarEntry(i, steps));
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Steps Count");
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.8f);
        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.getAxisRight().setEnabled(false);
        barChart.invalidate();
    }

    private Date getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
