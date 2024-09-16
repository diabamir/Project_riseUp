package com.example.project_riseup;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import retrofit2.Call;
import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project_riseup.ApiClient;
import com.example.project_riseup.GroupApi;
import com.example.project_riseup.Group;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddGroupActivity extends AppCompatActivity {

    private EditText Discribtion, editTextLimitMembersNumber;
    private Button buttonAdd;
    private DatePicker datePicker;
    private TimePicker starttimePicker,endtimePicker;
//    private GroupApi groupApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

//        groupApi = ApiClient.getClient().create(GroupApi.class);

        ImageView activityImageView = findViewById(R.id.activityImageView);
        TextView activityTextView = findViewById(R.id.activityTextView);
        TextView locationTextView = findViewById(R.id.locationTextView);
        Intent intent = getIntent();
        String selectedActivity = getIntent().getStringExtra("selectedActivity");
        String location = getIntent().getStringExtra("LOCATION");
        long userId= getIntent().getLongExtra("USER_ID", -1);
        String work=selectedActivity;
        activityTextView.setText(selectedActivity);
        locationTextView.setText(location);

        if (selectedActivity != null) {
            // Dynamically load the drawable resource
            int imageResId = getResources().getIdentifier(selectedActivity.toLowerCase(), "drawable", getPackageName());
            if (imageResId != 0) {
                activityImageView.setImageResource(imageResId);
            } else {
                // Handle case where the drawable is not found
                activityImageView.setImageResource(R.drawable.default_image); // Set a default image
            }
        }

//        buttonTextWorkout = findViewById(R.id.buttonTextWorkout);
//        editTextLocation = findViewById(R.id.editTextLocation);
        Discribtion = findViewById(R.id.Discribtion);
        editTextLimitMembersNumber = findViewById(R.id.editTextLimitMembersNumber);

        datePicker = findViewById(R.id.datePicker); // Assuming the DatePicker's ID is datePicker
        starttimePicker = findViewById(R.id.starttimePicker); // Initialize TimePicker
        endtimePicker = findViewById(R.id.endtimePicker);
        buttonAdd = findViewById(R.id.buttonAdd);

        buttonAdd.setOnClickListener(v -> {

//            String location = editTextLocation.getText().toString();
            String discription = Discribtion.getText().toString();
//            String work = buttonTextWorkout.getText().toString();


            Date ddate = getDateFromDatePicker();
            String startTm = getTimeFromPicker(starttimePicker);
            String endTm = getTimeFromPicker(endtimePicker);


            int LimitMembersNumber = Integer.parseInt(editTextLimitMembersNumber.getText().toString());
            int imageGroupResourceId = getDrawableResourceId(work);


            if (ddate != null) {

                Group group = new Group(0,work, location, discription,startTm,endTm,ddate,(int)userId,null, LimitMembersNumber,0,imageGroupResourceId);
                Executors.newSingleThreadExecutor().execute(() -> {
                    groupDataBase.getInstance(getApplicationContext()).groupDao().insertGroup(group);
//                    runOnUiThread(this::finish);
                    runOnUiThread(() -> {
                        // After adding the group, navigate to ViewGroupsActivity with the location filter
                        Intent viewGroupsIntent = new Intent(AddGroupActivity.this, ViewGroupsActivity.class);
                        viewGroupsIntent.putExtra("LOCATION", location); // Pass the location as an extra
                        viewGroupsIntent.putExtra("USER_ID", userId);
                        startActivity(viewGroupsIntent);
//                        finish(); // Finish this activity to go back to the previous one
                    });
                });
//                Call<Group> call = groupApi.insertGroup(group);
//                call.enqueue(new Callback<Group>() {
//                    @Override
//                    public void onResponse(Call<Group> call, Response<Group> response) {
//                        if (response.isSuccessful()) {
//                            Toast.makeText(AddGroupActivity.this, "Group added successfully", Toast.LENGTH_SHORT).show();
//                            Intent viewGroupsIntent = new Intent(AddGroupActivity.this, ViewGroupsActivity.class);
//                            viewGroupsIntent.putExtra("LOCATION", location);
//                            startActivity(viewGroupsIntent);
//
//                        } else {
//                            Toast.makeText(AddGroupActivity.this, "Failed to add Group", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Group> call, Throwable t) {
//                        Toast.makeText(AddGroupActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                        Log.d(TAG, "onFailure: " + t.getMessage());
//
//                    }
//                });





//                Executors.newSingleThreadExecutor().execute(() -> {
//                    groupDataBase.getInstance(getApplicationContext()).groupDao().insertGroup(group);
////                    runOnUiThread(this::finish);
//                    runOnUiThread(() -> {
//                        // After adding the group, navigate to ViewGroupsActivity with the location filter
//                        Intent viewGroupsIntent = new Intent(AddGroupActivity.this, ViewGroupsActivity.class);
//                        viewGroupsIntent.putExtra("LOCATION", location); // Pass the location as an extra
//                        startActivity(viewGroupsIntent);
////                        finish(); // Finish this activity to go back to the previous one
//                    });
//                });
            }
        });
    }



    public int getDrawableResourceId(String work) {
        switch (work) {
            case "weightLifting":
                return R.drawable.weightlifting;
            case "yoga":
                return R.drawable.yoga;
            case "cartWheeling":
                return R.drawable.cartwheeling;
            case "running":
                return R.drawable.running;
            case "swimming":
                return R.drawable.swimming;
            case "biking":
                return R.drawable.biking;
            case "wrestling":
                return R.drawable.wrestling;
            case "handBall":
                return R.drawable.handball;
            case "climbing":
                return R.drawable.climbing;
            case "surfing":
                return R.drawable.surfing;
            case "golfing":
                return R.drawable.golfing;
            case "waterPolo":
                return R.drawable.waterpolo;
            case "horseRacing":
                return R.drawable.horseracing;
            case "football":
                return R.drawable.football;
            case "basketball":
                return R.drawable.basketball;
            case "bowling":
                return R.drawable.bowling;
            case "tennis":
                return R.drawable.tennis;
            case "pingpong":
                return R.drawable.pingpong;
            case "volleyball":
                return R.drawable.volleyball;
            case "rowing":
                return R.drawable.rowing;
            default:
                return R.drawable.default_image; // Return a default image or handle as needed
        }
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
//package com.example.project_riseup;
//
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.TimePicker;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.concurrent.Executors;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import retrofit2.Call;
//import static android.content.ContentValues.TAG;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//import com.example.project_riseup.ApiClient;
//import com.example.project_riseup.GroupApi;
//import com.example.project_riseup.Group;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class AddGroupActivity extends AppCompatActivity {
//
//    private EditText Discribtion, editTextLimitMembersNumber;
//    private Button buttonAdd;
//    private DatePicker datePicker;
//    private TimePicker starttimePicker,endtimePicker;
//    private GroupApi groupApi;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_group);
//
//        groupApi = ApiClient.getClient().create(GroupApi.class);
//
//        ImageView activityImageView = findViewById(R.id.activityImageView);
//        TextView activityTextView = findViewById(R.id.activityTextView);
//        TextView locationTextView = findViewById(R.id.locationTextView);
//        Intent intent = getIntent();
//        String selectedActivity = getIntent().getStringExtra("selectedActivity");
//        String location = getIntent().getStringExtra("LOCATION");
//        String work=selectedActivity;
//        activityTextView.setText(selectedActivity);
//        locationTextView.setText(location);
//
//        if (selectedActivity != null) {
//            // Dynamically load the drawable resource
//            int imageResId = getResources().getIdentifier(selectedActivity.toLowerCase(), "drawable", getPackageName());
//            if (imageResId != 0) {
//                activityImageView.setImageResource(imageResId);
//            } else {
//                // Handle case where the drawable is not found
//                activityImageView.setImageResource(R.drawable.default_image); // Set a default image
//            }
//        }
//
////        buttonTextWorkout = findViewById(R.id.buttonTextWorkout);
////        editTextLocation = findViewById(R.id.editTextLocation);
//        Discribtion = findViewById(R.id.Discribtion);
//        editTextLimitMembersNumber = findViewById(R.id.editTextLimitMembersNumber);
//
//        datePicker = findViewById(R.id.datePicker); // Assuming the DatePicker's ID is `datePicker`
//        starttimePicker = findViewById(R.id.starttimePicker); // Initialize TimePicker
//        endtimePicker = findViewById(R.id.endtimePicker);
//        buttonAdd = findViewById(R.id.buttonAdd);
//
//        buttonAdd.setOnClickListener(v -> {
//
////            String location = editTextLocation.getText().toString();
//            String discription = Discribtion.getText().toString();
////            String work = buttonTextWorkout.getText().toString();
//
//
//            Date ddate = getDateFromDatePicker();
//            String startTm = getTimeFromPicker(starttimePicker);
//            String endTm = getTimeFromPicker(endtimePicker);
//
//
//            int LimitMembersNumber = Integer.parseInt(editTextLimitMembersNumber.getText().toString());
//            int imageGroupResourceId = getDrawableResourceId(work);
//
//
//            if (ddate != null) {
//                Group group = new Group(0,work, location, discription,startTm,endTm,ddate,1,null, LimitMembersNumber,0,imageGroupResourceId);
//                Call<Group> call = groupApi.insertGroup(group);
//                call.enqueue(new Callback<Group>() {
//                    @Override
//                    public void onResponse(Call<Group> call, Response<Group> response) {
//                        if (response.isSuccessful()) {
//                            Toast.makeText(AddGroupActivity.this, "Group added successfully", Toast.LENGTH_SHORT).show();
//                            Intent viewGroupsIntent = new Intent(AddGroupActivity.this, ViewGroupsActivity.class);
//                            viewGroupsIntent.putExtra("LOCATION", location);
//                            startActivity(viewGroupsIntent);
//
//                        } else {
//                            Toast.makeText(AddGroupActivity.this, "Failed to add Group", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Group> call, Throwable t) {
//                        Toast.makeText(AddGroupActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                        Log.d(TAG, "onFailure: " + t.getMessage());
//
//                    }
//                });
//
//
//
//
//
////                Executors.newSingleThreadExecutor().execute(() -> {
////                    groupDataBase.getInstance(getApplicationContext()).groupDao().insertGroup(group);
//////                    runOnUiThread(this::finish);
////                    runOnUiThread(() -> {
////                        // After adding the group, navigate to ViewGroupsActivity with the location filter
////                        Intent viewGroupsIntent = new Intent(AddGroupActivity.this, ViewGroupsActivity.class);
////                        viewGroupsIntent.putExtra("LOCATION", location); // Pass the location as an extra
////                        startActivity(viewGroupsIntent);
//////                        finish(); // Finish this activity to go back to the previous one
////                    });
////                });
//            }
//        });
//    }
//
//
//
//    public int getDrawableResourceId(String work) {
//        switch (work) {
//            case "weightLifting":
//                return R.drawable.weightlifting;
//            case "yoga":
//                return R.drawable.yoga;
//            case "cartWheeling":
//                return R.drawable.cartwheeling;
//            case "running":
//                return R.drawable.running;
//            case "swimming":
//                return R.drawable.swimming;
//            case "biking":
//                return R.drawable.biking;
//            case "wrestling":
//                return R.drawable.wrestling;
//            case "handBall":
//                return R.drawable.handball;
//            case "climbing":
//                return R.drawable.climbing;
//            case "surfing":
//                return R.drawable.surfing;
//            case "golfing":
//                return R.drawable.golfing;
//            case "waterPolo":
//                return R.drawable.waterpolo;
//            case "horseRacing":
//                return R.drawable.horseracing;
//            case "football":
//                return R.drawable.football;
//            case "basketball":
//                return R.drawable.basketball;
//            case "bowling":
//                return R.drawable.bowling;
//            case "tennis":
//                return R.drawable.tennis;
//            case "pingpong":
//                return R.drawable.pingpong;
//            case "volleyball":
//                return R.drawable.volleyball;
//            case "rowing":
//                return R.drawable.rowing;
//            default:
//                return R.drawable.default_image; // Return a default image or handle as needed
//        }
//    }
//
//
//    private Date getDateFromDatePicker() {
//        Calendar calendar = Calendar.getInstance();
//        int day = datePicker.getDayOfMonth();
//        int month = datePicker.getMonth();
//        int year = datePicker.getYear();
//        calendar.set(year, month, day);
//        return calendar.getTime();
//    }
//
//    private String getTimeFromPicker(TimePicker timePicker) {
//        int hour;
//        int minute;
//        String period = "AM"; // Default to AM
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            // For API level 23 and above
//            hour = timePicker.getHour();
//            minute = timePicker.getMinute();
//        } else {
//            // For API level below 23
//            hour = timePicker.getCurrentHour();
//            minute = timePicker.getCurrentMinute();
//        }
//
//        // Check if the hour is in the PM range
//        if (hour >= 12) {
//            period = "PM";
//            if (hour > 12) {
//                hour -= 12; // Convert 24-hour to 12-hour format
//            }
//        } else if (hour == 0) {
//            hour = 12; // Convert 0 hours to 12 for 12 AM
//        }
//
//        return String.format("%02d:%02d %s", hour, minute, period);
//    }
//
//}
