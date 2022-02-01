package com.xzh.viewdemos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class PopView extends View {
    Paint mPaint;
    int mWidth;
    int mHeight;

    public PopView(Context context) {
        super(context);
        initView();
    }

    public PopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setColor(Color.YELLOW);
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//         canvas.drawRect(0, 0, 100, 100, mPaint);
//    }

    int getMeasureSize(int defaultSize, int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        Log.d("PopView", "size:" + size);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                Log.d("PopView", "MeasureSpec.EXACTLY:" + MeasureSpec.EXACTLY);
                return size;
            case MeasureSpec.AT_MOST:
                Log.d("PopView", "MeasureSpec.AT_MOST:" + MeasureSpec.AT_MOST);
                return size;
            case MeasureSpec.UNSPECIFIED:
                Log.d("PopView", "MeasureSpec.UNSPECIFIED:" + MeasureSpec.UNSPECIFIED);
                return defaultSize;
        }
        return defaultSize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
////        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
////        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
////        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
////        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        Log.d("PopView", "onMeasure");
//        int measureHeight = getMeasureSize(100, heightMeasureSpec);
//        int measureWidth = getMeasureSize(100, widthMeasureSpec);
//        if (measureHeight>measureWidth){
//            measureHeight=measureWidth;
//        }else {
//            measureWidth=measureHeight;
//        }
//
//        Log.d("PopView", "measureHeight:" + measureHeight);
//        Log.d("PopView", "measureWidth:" + measureWidth);
//
//        setMeasuredDimension(measureWidth,measureHeight);
//    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
