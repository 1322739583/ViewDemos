package com.xzh.viewdemos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ImageUtils;

import java.io.File;

public class PaintView2 extends View {

    private Paint paint;
    private Path path;
    private float nextX;
    private float nextY;
    private float startX;
    private float startY;
    private Bitmap bitmap;
    private Canvas cacheCanvas;


    public PaintView2(Context context) {
        super(context);
        initView(context, null);
    }

    public PaintView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean performContextClick() {
        return super.performContextClick();
    }

    private void initView(Context context, AttributeSet attrs) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10);
        //不能设为FILL。会出现奇怪的现象
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.YELLOW);
        path = new Path();

        int screenWidth = ScreenUtil.getScreenWidth(getContext());
        int screenHeight = ScreenUtil.getScreenHeight(getContext());

        //通过在onDraw里面调用drawBitmap，这样就可以通过我们自己声明的cacheCanvas在非onDraw方法中画东西。
        bitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas(bitmap);
        //必须要设置，不然保存的图片背景会是黑色的
        cacheCanvas.drawColor(Color.WHITE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, paint);
        // this.canvas.drawPath(path,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                //起点是当前View的右上角
                startX = event.getX();
                startY = event.getY();
                Log.d("PaintView", "startX:" + startX + " startY:" + startY);
                path.moveTo(startX, startY);
                break;
            case MotionEvent.ACTION_MOVE:
                //第一个和startX是不一样的
                nextX = event.getX();
                nextY = event.getY();
                Log.d("PaintView", "nextX:" + nextX + " nextY:" + nextY);
                path.lineTo(nextX, nextY);
                cacheCanvas.drawPath(path, paint);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    public void saveImage() {
        ImageUtils.save(bitmap, getContext().getFilesDir() + "/" + System.currentTimeMillis() + ".jpeg", Bitmap.CompressFormat.JPEG);

    }

    public void clearImage() {
        //将bitmap内容清空
        bitmap.eraseColor(Color.WHITE);
        //path需要重置，不然在画的时候之前的图案又显示出来了
        path.reset();
        invalidate();
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }
}
