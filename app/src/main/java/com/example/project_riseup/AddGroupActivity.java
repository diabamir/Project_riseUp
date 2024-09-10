package com.example.project_riseup;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;

public class AddGroupActivity extends AppCompatActivity {

    private EditText buttonTextWorkout, editTextLocation, Discribtion, editTextLimitMembersNumber;
    private Button buttonAdd;
    private DatePicker datePicker;
    private TimePicker starttimePicker,endtimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        buttonTextWorkout = findViewById(R.id.buttonTextWorkout);
        editTextLocation = findViewById(R.id.editTextLocation);
        Discribtion = findViewById(R.id.Discribtion);
        editTextLimitMembersNumber = findViewById(R.id.editTextLimitMembersNumber);

        datePicker = findViewById(R.id.datePicker); // Assuming the DatePicker's ID is `datePicker`
        starttimePicker = findViewById(R.id.starttimePicker); // Initialize TimePicker
        endtimePicker = findViewById(R.id.endtimePicker);
        buttonAdd = findViewById(R.id.buttonAdd);

        buttonAdd.setOnClickListener(v -> {

            String location = editTextLocation.getText().toString();
            String discription = Discribtion.getText().toString();
            String work = buttonTextWorkout.getText().toString();


            Date ddate = getDateFromDatePicker();
            String startTm = getTimeFromPicker(starttimePicker);
            String endTm = getTimeFromPicker(endtimePicker);


            int LimitMembersNumber = Integer.parseInt(editTextLimitMembersNumber.getText().toString());


            if (ddate != null) {
                Group group = new Group(0, work, location, discription, startTm, endTm, ddate, 1, null, LimitMembersNumber, 0, 0);

                Executors.newSingleThreadExecutor().execute(() -> {
                    groupDataBase.getInstance(getApplicationContext()).groupDao().insertGroup(group);
                    runOnUiThread(this::finish);
                });
            }
        });
    }

    private Date getDateFromDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    private String getTimeFromPicker(TimePicker timePicker) {
        int hour;
        int minute;
        String period = "AM"; // Default to AM

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // For API level 23 and above
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        } else {
            // For API level below 23
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
        }

        // Check if the hour is in the PM range
        if (hour >= 12) {
            period = "PM";
            if (hour > 12) {
                hour -= 12; // Convert 24-hour to 12-hour format
            }
        } else if (hour == 0) {
            hour = 12; // Convert 0 hours to 12 for 12 AM
        }

        return String.format("%02d:%02d %s", hour, minute, period);
    }

}
