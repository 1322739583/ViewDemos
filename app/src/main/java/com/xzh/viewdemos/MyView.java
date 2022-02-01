package com.xzh.viewdemos;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MyView extends View {

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        Log.d("MyView", "getWidth():" + getWidth());
        Log.d("MyView", "getHeight():" + getHeight());
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d("MyView onDraw", "getWidth():" + getWidth());
        Log.d("MyView onDraw", "getHeight():" + getHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //getX()和getRawX()的区别就是是不是相对真实屏幕，如果是做两个View减法的时候，其实是一样的。
        Log.d("MyView", "event.getX():" + event.getX());
        Log.d("MyView", "event.getY():" + event.getY());

        Log.d("MyView", "event.getRawX():" + event.getRawX());
        Log.d("MyView", "event.getRawY():" + event.getRawY());

        Log.d("MyView", "getX():" + getX());
        Log.d("MyView", "getY():" + getY());

        Log.d("MyView", "getWidth():" + getWidth());
        Log.d("MyView", "getHeight():" + getHeight());


        
        return true;
    }
}
