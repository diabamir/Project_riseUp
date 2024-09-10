package com.example.project_riseup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MyScaleActivityWeight extends AppCompatActivity {
    private TextView txtValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_scale_weight);

        // Find the custom view and the TextView in the layout
        final MyScaleViewWeight rulerViewMm = (MyScaleViewWeight) findViewById(R.id.my_scale);
        txtValue = (TextView) findViewById(R.id.txt_height);

        // Set initial starting point for the scale
        rulerViewMm.setStartingPoint(70);  // Example starting point

        // Set the listener for view updates
        rulerViewMm.setUpdateListener(new MyScaleViewWeight.onViewUpdateListener() {
            @Override
            public void onViewUpdate(float result) {
                // Round the result and update the TextView with the selected value
                float value = (float) Math.round(result * 10f) / 10f;
                txtValue.setText(value + " kg");
            }
        });
    }
}