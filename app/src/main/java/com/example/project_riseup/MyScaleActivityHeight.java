package com.example.project_riseup;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MyScaleActivityHeight extends AppCompatActivity {
    private TextView txtValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_scale_height);

        // Find the custom view and the TextView in the layout
        final MyScaleViewHeight rulerViewMm = (MyScaleViewHeight) findViewById(R.id.my_scale);
        txtValue = (TextView) findViewById(R.id.txt_height);

        // Set initial starting point for the scale
        rulerViewMm.setStartingPoint(70);  // Example starting point

        // Set the listener for view updates
        rulerViewMm.setUpdateListener(new MyScaleViewHeight.onViewUpdateListener() {
            @Override
            public void onViewUpdate(float result) {
                // Round the result and update the TextView with the selected value
                float value = (float) Math.round(result * 10f) / 10f;
                txtValue.setText(value + " cm");
            }
        });
    }
}