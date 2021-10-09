package com.xzh.viewdemos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.Nullable;

public class PaintView extends View {

    private Paint paint;
    private Path path;
    private float nextX;
    private float nextY;
    private float startX;
    private float startY;
    private Bitmap bitmap;
    private Canvas canvas;

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
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10);
        //不能设为FILL。会出现奇怪的现象
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.YELLOW);
        path = new Path();

        int screenWidth = ScreenUtil.getScreenWidth(getContext());
        int screenHeight = ScreenUtil.getScreenHeight(getContext());
        bitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);


    }

    @Override
    protected void onDraw(Canvas canvas) {
      //  canvas.drawPath(path,paint);
        this.canvas.drawPath(path,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                //起点是当前View的右上角
                 startX = event.getX();
                 startY = event.getY();
                Log.d("PaintView", "startX:" + startX +" startY:" + startY);
               path.moveTo(startX, startY);
                break;
            case MotionEvent.ACTION_MOVE:
                //第一个和startX是不一样的
                nextX = event.getX();
                nextY = event.getY();
                Log.d("PaintView", "nextX:" + nextX +" nextY:" + nextY);
                path.lineTo(nextX,nextY);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
