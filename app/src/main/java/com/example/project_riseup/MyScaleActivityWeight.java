package com.example.project_riseup;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MyScaleActivityWeight extends AppCompatActivity {
    private TextView txtValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_scale_weight);

        // Find the custom view and the TextView in the layout
        final MyScaleViewWeight rulerViewMm = findViewById(R.id.my_scale);
        txtValue = findViewById(R.id.txt_weight);

        // Set minimum starting point for weight (40 kg)
        rulerViewMm.setStartingPoint(40);

        // Set the listener for view updates
        rulerViewMm.setUpdateListener(result -> {
            // Round the result and update the TextView with the selected value
            float value = (float) Math.round(result * 10f) / 10f;
            txtValue.setText(value + " kg");
        });
    }
}
//package com.example.project_riseup;
//
//import android.os.Bundle;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class MyScaleActivityWeight extends AppCompatActivity {
//    private TextView txtValue;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_scale_weight);
//
//        // Find the custom view and the TextView in the layout
//        final MyScaleViewWeight rulerViewMm = findViewById(R.id.my_scale);
//        txtValue = findViewById(R.id.txt_weight);
//
//        // Set minimum starting point for weight (40 kg)
//        rulerViewMm.setStartingPoint(40);
//
//        // Set the listener for view updates
//        rulerViewMm.setUpdateListener(result -> {
//            // Round the result and update the TextView with the selected value
//            float value = (float) Math.round(result * 10f) / 10f;
//            txtValue.setText(value + " kg");
//        });
//    }
//}
