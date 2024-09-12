package com.example.project_riseup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyScaleViewHeight extends View {

    // Class fields
    static int screenSize = 480;
    static private float pxmm = screenSize / 67.f;
    int width, height, midScreenPoint;
    float startingPoint = 100;  // Default minimum height
    float downpoint = 0, movablePoint = 0, downPointClone = 0;
    private float mainPoint = 0, mainPointClone = 0;
    boolean isDown = false;
    boolean isUpward = false;
    private boolean isMove;
    private onViewUpdateListener mListener;
    private Paint gradientPaint;
    private float rulersize = 0;
    private Paint rulerPaint, textPaint, goldenPaint;
    private int endPoint;
    boolean isSizeChanged = false;
    float userStartingPoint = 100f;  // Start from 100 cm by default
    private int scaleLineSmall;
    private int scaleLineMedium;
    private int scaleLineLarge;
    private int textStartPoint;
    private int yellowLineStrokeWidth;
    boolean isFirstTime = true;

    public MyScaleViewHeight(Context context, AttributeSet foo) {
        super(context, foo);
        if (!isInEditMode()) {
            init(context);
        }
    }

    /**
     * Initializes all necessary Paint objects and sets up the custom font if available.
     */
    private void init(Context context) {
        // Yellow line stroke width
        yellowLineStrokeWidth = (int) getResources().getDimension(R.dimen.yellow_line_stroke_width);

        // Paint for gradient background of the ruler
        gradientPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // Size of the ruler in pixels
        rulersize = pxmm * 10;

        // Ruler line paint
        rulerPaint = new Paint();
        rulerPaint.setStyle(Paint.Style.STROKE);
        rulerPaint.setStrokeWidth(0);
        rulerPaint.setAntiAlias(false); // Ruler lines don't need anti-aliasing
        rulerPaint.setColor(Color.WHITE);

        // Text paint for displaying numbers on the scale
        textPaint = new TextPaint();
        try {
            // Try to load the custom font
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/segoeuil.ttf");
            textPaint.setTypeface(typeface);
        } catch (Exception e) {
            // Fallback to a default font if the custom font is not found
            textPaint.setTypeface(Typeface.SANS_SERIF);
        }
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setStrokeWidth(0);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(getResources().getDimension(R.dimen.txt_size));
        textPaint.setColor(Color.BLACK);

        // Golden line paint for the centerline
        goldenPaint = new Paint();
        goldenPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        goldenPaint.setColor(context.getResources().getColor(R.color.softpurple));
        goldenPaint.setStrokeWidth(yellowLineStrokeWidth);
        goldenPaint.setStrokeJoin(Paint.Join.ROUND);
        goldenPaint.setStrokeCap(Paint.Cap.ROUND);
        goldenPaint.setPathEffect(new CornerPathEffect(10)); // Rounded edges for the line
        goldenPaint.setAntiAlias(true);

        // Scale line sizes
        scaleLineSmall = (int) getResources().getDimension(R.dimen.scale_line_small);
        scaleLineMedium = (int) getResources().getDimension(R.dimen.scale_line_medium);
        scaleLineLarge = (int) getResources().getDimension(R.dimen.scale_line_large);
        textStartPoint = (int) getResources().getDimension(R.dimen.text_start_point);
    }

    /**
     * Sets the listener for the view update events.
     */
    public void setUpdateListener(onViewUpdateListener onViewUpdateListener) {
        mListener = onViewUpdateListener;
    }

    /**
     * Called when the size of the view is changed (e.g., on device rotation).
     */
    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        width = w;
        height = h;
        screenSize = height;
        pxmm = screenSize / 67.f;
        midScreenPoint = height / 2;
        endPoint = width - 40;
        mainPoint = midScreenPoint - (userStartingPoint * 10 * pxmm);  // Start the ruler from the specified height
        // Set up gradient background for the ruler
        gradientPaint.setShader(new LinearGradient(0, 0, width, rulersize, getResources().getColor(R.color.purple),
                getResources().getColor(R.color.transparent_white), android.graphics.Shader.TileMode.MIRROR));
    }

    /**
     * Draws the scale lines, text, and the centerline on the canvas.
     */
    @Override
    public void onDraw(Canvas canvas) {
        // Draw gradient background
        canvas.drawRect(0f, midScreenPoint - (rulersize / 2), width, midScreenPoint + (rulersize / 2), gradientPaint);

        startingPoint = mainPoint;
        for (int i = 1;; ++i) {
            if (startingPoint > screenSize) {
                break;
            }
            startingPoint += pxmm;
            int size = (i % 10 == 0) ? scaleLineLarge : (i % 5 == 0) ? scaleLineMedium : scaleLineSmall;
            canvas.drawLine(endPoint - size, startingPoint, endPoint, startingPoint, rulerPaint);

            // Draw scale text every 10 lines
            if (i % 10 == 0) {
                canvas.drawText((i / 10) + " cm", endPoint - textStartPoint, startingPoint + 8, textPaint);
            }
        }

        // Draw centerline
        canvas.drawLine(0f, midScreenPoint, width - 20, midScreenPoint, goldenPaint);
    }

    /**
     * Handles touch events for scrolling the scale.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mainPointClone = mainPoint;
        if (mainPoint < 0) {
            mainPointClone = -mainPoint;
        }
        float clickPoint = ((midScreenPoint + mainPointClone) / (pxmm * 10));
        if (mListener != null) {
            mListener.onViewUpdate((midScreenPoint + mainPointClone) / (pxmm * 10));
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isMove = true;
                downpoint = event.getY();
                downPointClone = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                movablePoint = event.getY();
                if (downPointClone > movablePoint) {
                    if (isUpward) {
                        downpoint = event.getY();
                        downPointClone = downpoint;
                    }
                    isDown = true;
                    isUpward = false;
                    if (downPointClone - movablePoint > 1) {
                        mainPoint += (-(downPointClone - movablePoint));
                        downPointClone = movablePoint;
                        invalidate();
                    }
                } else {
                    if (movablePoint - downpoint > 1) {
                        mainPoint += (movablePoint - downPointClone);
                        downPointClone = movablePoint;
                        if (mainPoint > 0) {
                            mainPoint = 0;
                            isMove = false;
                        }
                        invalidate();
                    }
                }
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * Sets the starting point of the scale based on user input.
     */
    public void setStartingPoint(float point) {
        if (point < 100) {
            point = 100;  // Ensure the height doesn't go below 100 cm
        }
        userStartingPoint = point;
        isSizeChanged = true;
        if (isFirstTime) {
            isFirstTime = false;
            if (mListener != null) {
                mListener.onViewUpdate(point);
            }
        }
    }

    /**
     * Interface for listening to view updates.
     */
    public interface onViewUpdateListener {
        void onViewUpdate(float result);
    }
}
