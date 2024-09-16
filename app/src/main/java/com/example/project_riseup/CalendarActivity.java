package com.example.project_riseup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;
import org.threeten.bp.YearMonth;
import org.threeten.bp.format.DateTimeFormatter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    private StepsDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // Initialize ThreeTenABP for backward compatibility with Java 8 Date/Time API
        AndroidThreeTen.init(this);

        database = StepsDatabase.getInstance(this);

        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();
    }

    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(selectedDate));
        fetchStepsForMonth(selectedDate);
    }

    // Fetch steps for the whole month and update the view
    private void fetchStepsForMonth(LocalDate date) {
        new Thread(() -> {
            // Get the first and last date of the month
            YearMonth yearMonth = YearMonth.from(date);
            LocalDate firstDayOfMonth = yearMonth.atDay(1); // First day of the month
            LocalDate lastDayOfMonth = yearMonth.atEndOfMonth(); // Last day of the month

            // Correct way to convert LocalDate to java.sql.Date for backward compatibility
            Date startDate = Date.valueOf(firstDayOfMonth.toString()); // LocalDate to java.sql.Date
            Date endDate = Date.valueOf(lastDayOfMonth.toString());    // LocalDate to java.sql.Date

            // Fetch steps data for the month from the database
            List<Steps> stepsList = database.stepsDao().findStepsByDateRange(startDate, endDate);

            // Update the UI on the main thread
            runOnUiThread(() -> updateCalendarWithSteps(stepsList));
        }).start();
    }

    private void updateCalendarWithSteps(List<Steps> stepsList) {
        ArrayList<DayItem> daysInMonth = daysInMonthArray(selectedDate, stepsList);
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private ArrayList<DayItem> daysInMonthArray(LocalDate date, List<Steps> stepsList) {
        ArrayList<DayItem> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = date.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        // Adjust dayOfWeek for a calendar starting on Sunday (if Sunday = 1st day of the week)
        int firstDayIndex = (dayOfWeek == 7) ? 0 : dayOfWeek;

        Calendar calendar = Calendar.getInstance();
        for (int i = 1; i <= 42; i++) {
            if (i <= firstDayIndex || i > daysInMonth + firstDayIndex) {
                // Blank days for padding
                daysInMonthArray.add(new DayItem("", ""));
            } else {
                int dayOfMonth = i - firstDayIndex;
                LocalDate currentDay = date.withDayOfMonth(dayOfMonth);

                // Default steps to 0
                String steps = " ";

                // Check if we have steps data for this day
                for (Steps step : stepsList) {
                    calendar.setTime(step.getDate());
                    if (calendar.get(Calendar.DAY_OF_MONTH) == dayOfMonth) {
                        steps = String.valueOf(step.getStepsTaken());
                        break;
                    }
                }

                // Log for debugging
                Log.d("CalendarActivity", "Steps for " + currentDay + ": " + steps);

                daysInMonthArray.add(new DayItem(String.valueOf(dayOfMonth), steps));
            }
        }
        return daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction(View view) {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view) {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, String dayText) {
        if (!dayText.equals("")) {
            String message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }
}
