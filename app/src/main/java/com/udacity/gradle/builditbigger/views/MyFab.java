package com.udacity.gradle.builditbigger.views;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.udacity.gradle.builditbigger.R;

public class MyFab extends FloatingActionButton implements ValueAnimator.AnimatorUpdateListener {

    private final static String PROP_RADIUS = "PROP_RADIUS";
    private final static String PROP_ALPHA = "PROP_ALPHA";

    private float MIN_RADIUS_VALUE;
    private float MAX_RADIUS_VALUE;
    private float mRadius;
    private boolean initialized = false;

    private ValueAnimator valueAnimator;
    ValueAnimator loadingAnimator;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Rect r = new Rect();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        MAX_RADIUS_VALUE = getMeasuredWidth();
        MIN_RADIUS_VALUE = getMeasuredWidth() / 2;
    }

    private void init() {
        if (initialized)
            return;
        initialized = true;
        setWillNotDraw(true);

        getMeasuredContentRect(r);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4f);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
        paint.setColor(getResources().getColor(R.color.colorAccent));

        mRadius = MIN_RADIUS_VALUE;

        PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofFloat(PROP_RADIUS, MIN_RADIUS_VALUE, MAX_RADIUS_VALUE);
        PropertyValuesHolder valuesHolder1 = PropertyValuesHolder.ofInt(PROP_ALPHA, 255, 0);

        valueAnimator = new ValueAnimator();
        valueAnimator.setValues(valuesHolder, valuesHolder1);
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(this);
        valueAnimator.start();

    }

    public MyFab(Context context) {
        super(context);
    }

    public MyFab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void stopAnimation() {
        valueAnimator.end();
    }

    public void startAnimation() {
        loadingAnimator.end();
        valueAnimator.start();
    }

    public void startLoadingAnim() {
        stopAnimation();
        invalidate();
        loadingAnimator = ValueAnimator.ofFloat(0, 360);
        loadingAnimator.setRepeatCount(ValueAnimator.INFINITE);
        loadingAnimator.setDuration(800);
        loadingAnimator.setStartDelay(300);
        loadingAnimator.setInterpolator(new DecelerateInterpolator());
        loadingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float f = (float) valueAnimator.getAnimatedValue();
                setRotation(f);
            }
        });
        loadingAnimator.start();

    }

    public void stopLoadingAnim() {
        loadingAnimator.end();
        startAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
        canvas.drawCircle(r.centerX(), r.centerY(), mRadius, paint);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        mRadius = (float) valueAnimator.getAnimatedValue(PROP_RADIUS);
        paint.setAlpha((int) valueAnimator.getAnimatedValue(PROP_ALPHA));
        invalidate();
    }


}

