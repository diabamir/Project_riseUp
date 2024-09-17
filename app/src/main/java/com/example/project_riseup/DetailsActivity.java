package com.example.project_riseup;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
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

    // Define custom colors for the bar chart
    private final int[] chartColors = {
            0xFF8B80F6, 0xFFCFCCF4, 0xFF1E88FF, 0xFFB1D3F9,
            0xFF80C1F6, 0xFF62A0EB, 0xFF499AEF, 0xFF216FED
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);  // Make sure your layout file contains only the BarChart

        barChart = findViewById(R.id.barChart);
        database = StepsDatabase.getInstance(this);

        // Use a date format without time for consistent labels
        sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Fetch data and display the bar chart
        fetchStepsDataAndDisplayChart();
    }

    private void fetchStepsDataAndDisplayChart() {
        new Thread(() -> {
            // Get today's date (start of today)
            Date today = getStartOfDay(new Date());

            // Go back 6 days (total 7 days including today)
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -6);
            Date lastWeek = getStartOfDay(calendar.getTime());

            // Fetch steps data from last week to today
            List<Steps> stepsList = database.stepsDao().findStepsByDateRange(lastWeek, today);

            Log.d("DetailsActivity", "Fetching steps between: " + lastWeek + " and " + today);

            // Update UI on the main thread
            runOnUiThread(() -> {
                if (stepsList != null && !stepsList.isEmpty()) {
                    displayBarChart(stepsList);
                } else {
                    barChart.setNoDataText("No steps data available for the past week.");
                    barChart.invalidate();  // Refresh the chart
                }
            });
        }).start();
    }

    private void displayBarChart(List<Steps> stepsList) {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        // Loop over the last 7 days
        for (int i = 6; i >= 0; i--) {
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE, -i);  // Move back in time
            Date day = getStartOfDay(calendar.getTime()); // Ensure start of day
            String dateLabel = sdf.format(day); // Format date as label
            labels.add(dateLabel);  // Use formatted date as a label

            // Default step count to 0 if no data available for this day
            int steps = 0;

            // Check if we have steps for this day
            for (Steps step : stepsList) {
                Date stepsDate = getStartOfDay(step.getDate());  // Ensure start of day
                if (sdf.format(stepsDate).equals(sdf.format(day))) {
                    steps = step.getStepsTaken();
                    Log.d("DetailsActivity", "Steps for " + dateLabel + ": " + steps);  // Log steps data
                    break;
                }
            }

            // Add the steps data to the chart
            barEntries.add(new BarEntry(6 - i, steps));  // Fill the bar entries
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Steps Count");

        // Set custom colors for the bar chart
        ArrayList<Integer> colors = new ArrayList<>();
        for (int i = 0; i < barEntries.size(); i++) {
            colors.add(chartColors[i % chartColors.length]);  // Cycle through the colors
        }
        barDataSet.setColors(colors);

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.8f);  // Set bar width to make bars visible
        barChart.setData(barData);

        // Configure X-axis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f);  // Only show whole numbers on the X-axis
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(labels.size());

        // Ensure Y-axis starts from 0
        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.getAxisRight().setEnabled(false);  // Disable right Y-axis

        // Disable description label
        barChart.getDescription().setEnabled(false);

        // Refresh the chart
        barChart.invalidate();
    }

    private Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
