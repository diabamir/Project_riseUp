package com.example.project_riseup;


import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MyView extends View {

    private Paint wavePaint;
    private Paint circlePaint;
    private Paint textPaint;
    private int screenWidth;
    private int screenHeight;
    private final int amplitude = 100;
    private Path path;
    private float progress = 0f;
    private float textProgress = 0f;
    private Point startPoint = new Point();

    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        wavePaint = new Paint();
        wavePaint.setAntiAlias(true);
        wavePaint.setStrokeWidth(1f);

        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.parseColor("#000000"));
        textPaint.setTextSize(80f);

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.parseColor("#00BFFF"));
        circlePaint.setStrokeWidth(10f);
        circlePaint.setStyle(Paint.Style.STROKE);
    }

    public void setProgress(float addedWater, float maxWater) {
        // Calculate the percentage of water added
        float percentage = (addedWater / maxWater) * 100;

        // Update the text progress and progress for the wave animation
        textProgress = percentage;
        if (percentage == 100f) {
            this.progress = percentage + amplitude;
        } else {
            this.progress = percentage;
        }

        // Redraw the view
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.min(measureSize(400, widthMeasureSpec), measureSize(400, heightMeasureSpec));
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = w;
        screenHeight = h;
        startPoint.x = -screenWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        clipCircle(canvas);
        drawCircle(canvas);
        drawWave(canvas);
        drawText(canvas);
        postInvalidateDelayed(10); // Repaint after 10 ms for smooth animation
    }

    private void drawText(Canvas canvas) {
        Rect targetRect = new Rect(0, -screenHeight, screenWidth, 0);
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        textPaint.setTextAlign(Paint.Align.CENTER);
        // Display the percentage text in the center of the circle
        canvas.drawText(String.format("%.0f%%", textProgress), targetRect.centerX(), baseline, textPaint);
    }

    private void drawWave(Canvas canvas) {
        final int WATER_BLUE_COLOR = Color.parseColor("#00BFFF"); // Water Blue color code
        final int wave = screenWidth / 4;
        final int height = (int) (progress / 100 * screenHeight);

        startPoint.y = -height;
        canvas.translate(0f, screenHeight);

        path = new Path();
        wavePaint.setStyle(Paint.Style.FILL);
        wavePaint.setColor(WATER_BLUE_COLOR);

        path.moveTo(startPoint.x, startPoint.y);

        for (int i = 0; i < 4; i++) {
            float startX = startPoint.x + i * wave * 2;
            float endX = startX + 2 * wave;

            if (i % 2 == 0) {
                path.quadTo(
                        (startX + endX) / 2,
                        startPoint.y + amplitude,
                        endX,
                        startPoint.y
                );
            } else {
                path.quadTo(
                        (startX + endX) / 2,
                        startPoint.y - amplitude,
                        endX,
                        startPoint.y
                );
            }
        }

        path.lineTo(screenWidth, screenHeight / 2);
        path.lineTo(-screenWidth, screenHeight / 2);
        path.lineTo(-screenWidth, 0);
        path.close();

        canvas.drawPath(path, wavePaint);
        startPoint.x += 10;

        if (startPoint.x > 0) {
            startPoint.x = -screenWidth;
        }

        path.reset();
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(screenHeight / 2f, screenHeight / 2f, screenHeight / 2f, circlePaint);
    }

    private void clipCircle(Canvas canvas) {
        Path circlePath = new Path();
        circlePath.addCircle(screenWidth / 2f, screenHeight / 2f, screenHeight / 2f, Path.Direction.CW);
        canvas.clipPath(circlePath);
    }

    private int measureSize(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.UNSPECIFIED) {
            result = defaultSize;
        } else if (mode == MeasureSpec.AT_MOST || mode == MeasureSpec.EXACTLY) {
            result = size;
        }
        return result;
    }

    public float getProgress() {
        return textProgress;
    }
}