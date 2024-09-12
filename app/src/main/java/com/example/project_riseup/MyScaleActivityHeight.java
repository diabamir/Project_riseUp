package com.example.project_riseup;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MyScaleActivityHeight extends AppCompatActivity {
    private TextView txtValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_scale_height);

        // Find the custom view and the TextView in the layout
        final MyScaleViewHeight rulerViewMm = findViewById(R.id.my_scale);
        txtValue = findViewById(R.id.txt_height);

        // Set minimum starting point for height (100 cm)
        rulerViewMm.setStartingPoint(100);

        // Set the listener for view updates
        rulerViewMm.setUpdateListener(result -> {
            // Round the result and update the TextView with the selected value
            float value = (float) Math.round(result * 10f) / 10f;
            txtValue.setText(value + " cm");
        });
    }
}
