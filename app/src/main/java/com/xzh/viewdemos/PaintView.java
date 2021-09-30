package com.xzh.viewdemos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class PaintView extends View {

    private Paint paint;
    private Path path;

    public PaintView(Context context) {
        super(context);
        initView(context, null);
    }



    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        paint=new Paint();
        path = new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return true;
    }
}
