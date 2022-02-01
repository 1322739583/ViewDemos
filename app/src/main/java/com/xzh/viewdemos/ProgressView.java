package com.xzh.viewdemos;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 默认是圆形的，提供圆形直线，油盘等效果。
 * 所以一定会用到属性
 */
public class ProgressView extends View {


    private Paint mPrimaryPaint;
    private Paint mAccentPaint;
    /**
     * 底色
     */
    private int primaryColor;
    /**
     * 进度条颜色
     */
    private int accentColor;
    /**
     * 进度条类型
     */
    private int style;

    private float mProcess = 0;
    private float mStrokeWidth = 20;

    float mRadius =0;
    float mRadiusWithStrike=mRadius+mStrokeWidth/2;

    public ProgressView(Context context) {
        super(context);
        initView();
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        primaryColor = typedArray.getColor(R.styleable.ProgressView_primaryColor, 0);
        accentColor = typedArray.getColor(R.styleable.ProgressView_accentColor, 0);
        style = typedArray.getInt(R.styleable.ProgressView_style, 0);
        mRadius=typedArray.getFloat(R.styleable.ProgressView_radius,0);
        typedArray.recycle();
    }

    enum Style {
        LINE, CIRCLE;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (style == 0) {
            drawCircleStyle(canvas);
        } else if (style == 1) {
            drawLineStyle(canvas);
        }



    }

    /**
     * 画圆形进度条
     *
     * @param canvas
     */
    private void drawCircleStyle(Canvas canvas) {
        mPrimaryPaint.setStyle(Paint.Style.STROKE);
        //@TODO 这个值会让半径增加10/2,可以在布局的时候调整
        mPrimaryPaint.setStrokeWidth(mStrokeWidth);
        if (primaryColor == 0) {
            mPrimaryPaint.setColor(Color.YELLOW);
        } else {
            mPrimaryPaint.setColor(primaryColor);
        }

        //这样计算是正确显示的，好好研究一下机制
        mRadiusWithStrike =getWidth()/2;
        //可以通过用户设置传递
        mRadius=mRadiusWithStrike-mStrokeWidth/2;
        Log.d("ProgressView", "radius:" + mRadius);
        Log.d("ProgressView", "getWidth():" + getWidth());
        //这里的关键是drawCircle第三个参数默认是不算mStrokeWidth
        //而第一个和第二个参数又是算mStrokeWidth的，这本身就有点奇怪
        canvas.drawCircle(mRadiusWithStrike, mRadiusWithStrike, mRadius, mPrimaryPaint);

        mAccentPaint.setStyle(Paint.Style.STROKE);
        mAccentPaint.setStrokeWidth(mStrokeWidth);
        if (accentColor == 0) {
            mAccentPaint.setColor(Color.GREEN);
        } else {
            mAccentPaint.setColor(accentColor);
        }
        RectF rectF = new RectF();
        rectF.top = 0+mStrokeWidth/2;
        rectF.left = 0+mStrokeWidth/2;
        rectF.bottom = mRadius*2+mStrokeWidth/2;
        rectF.right = mRadius*2+mStrokeWidth/2;
       canvas.drawArc(rectF, -90, (float) 3.6 * mProcess, false, mAccentPaint);
    }

    /**
     * 画线形进度条
     *
     * @param canvas
     */
    private void drawLineStyle(Canvas canvas) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int measureWidth=width;
        int measureHeight=height;
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                measureWidth=width+(int)mStrokeWidth;
                break;
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }

        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                measureHeight=height+(int)mStrokeWidth;
                break;
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }

        setMeasuredDimension(measureWidth,measureHeight);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("ProgressView", "getWidth():" + getWidth());
        Log.d("ProgressView", "getHeight():" + getHeight());
    }

    void startAnin(float progress) {
        ValueAnimator animator = ObjectAnimator.ofFloat(0, 100);
        // Log.d("ProgressView", "animator.getAnimatedValue():" + animator.getAnimatedValue());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //@TODO 这个值是不对的
                mProcess = (float) animation.getAnimatedValue();
                //Log.d("ProgressView", "mProcess:" + mProcess);
                //   Log.d("ProgressView", "animator.getAnimatedValue():" + animator.getAnimatedValue());
                postInvalidate();
            }
        });

        animator.setStartDelay(500);
        animator.setDuration(5000);
        // animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnin(mProcess);
    }

    private void initView() {
        mPrimaryPaint = new Paint();
        mAccentPaint = new Paint();
        mPrimaryPaint.setDither(true);
        mPrimaryPaint.setAntiAlias(true);
        mAccentPaint.setDither(true);
        mAccentPaint.setAntiAlias(true);
        mAccentPaint.setStrokeCap(Paint.Cap.SQUARE);
    }
}
