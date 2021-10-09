package com.xzh.viewdemos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;


/**
 * 颜色画板选择
 */
public class ColorView extends View {


    /**
     * Android Studio Material A400色板
     */
    private String[] colors = {"#FF1744", "#F50057", "#D500F9", "#651FFF", "#3D5AFE", "#2979FF", "#00B0FF", "#00E5FF"
            , "#1DE9B6", "#00E676", "#76FF03", "#C6FF00", "#FFEA00", "#FFC400", "#FF9100", "#FF3D00", "#FF9100", "#FF3D00"};
    private String defaultPaintColor = "#000000";

    private static final int DEFAULT_BORDER_PADDING_LEFT = 100;
    private static final int DEFAULT_BORDER_PADDING_TOP =50 ;

    private Paint mPaint;
    private int screenWidth;
    private int borderWidth;
    private int borderPaddingLeft;
    private int borderPaddingTop;
    private int borderHeight;
    private int rectCountRow;
    private int rectCountColumn;
    private int colorRectSize;
    private int colorRectPadding;
    private List<RectF> colorRects;
    private String selectedColor=defaultPaintColor;
    private String backGroundColor="#ffffffff";

    private Bitmap bitmap;
    private Canvas cacheCanvas;

    private boolean isFirstStart=true;
    private int curPosition;
    private int lastPosition;



    public ColorView(Context context) {
        super(context);
        init(context, null);

    }

    public ColorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        //如果设置为true,在重叠会绘制的时候可能会出现白边
        mPaint.setAntiAlias(false);
        mPaint.setColor(Color.parseColor(defaultPaintColor));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setDither(true);

        //通过在onDraw里面调用drawBitmap，这样就可以通过我们自己声明的cacheCanvas在非onDraw方法中画东西。
        bitmap = Bitmap.createBitmap(1080, 500, Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas(bitmap);

    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        //启动Activity的时候会触发这个方法
        //在按Home和back的时候都会触发这个方法，并且hasWindowFocus值变为false,也就是只有第一次是true
        if (hasWindowFocus){
            Log.d("ColorView", "onWindowFocusChanged: true");
        }else {
            Log.d("ColorView", "onWindowFocusChanged: false");
        }
        //这个变量可以当作判断是否是第一次启动的变量来用，因为只有第一次值为true
        if (hasWindowFocus) {
//            mPaint.setStyle(Paint.Style.STROKE);
//            mPaint.setColor(Color.BLACK);
//            cacheCanvas.drawOval(colorRects.get(0), mPaint);
            //invalidate();
            curPosition=0;
            lastPosition=curPosition;
        }

        isFirstStart=false;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("ColorView", "onDraw invoked");
        canvas.drawBitmap(bitmap,0,0,mPaint);
        drawBorder(canvas);

        // drawColor(canvas);

    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                //起点是当前View的右上角
                float startX = event.getX();
                float startY = event.getY();
                Log.d("ColorView", "colorRects.size():" + colorRects.size());
                for (int i = 0; i < colorRects.size(); i++) {
                    boolean isInRect = pointInRect(startX, startY, colorRects.get(i));
                    if (isInRect) {
                        Log.d("ColorView", "在范围内");
                        mPaint.setStyle(Paint.Style.STROKE);
                        mPaint.setColor(Color.BLACK);
                        cacheCanvas.drawOval(colorRects.get(i), mPaint);

                        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                        //这里只需要把STROKE设为白色就行，因为背景颜色是白色，可以提取变量
                        //这里会出现一个很奇怪的白边问题(圆圈边缘有模糊的白边)，把paint的setAntiAlias()设为false就可以解决。
                        mPaint.setColor(Color.parseColor(backGroundColor));
                        cacheCanvas.drawOval(colorRects.get(lastPosition), mPaint);
                        lastPosition=i;
                        invalidate();
                        break;
                    }
                }

                Log.d("PaintView", "startX:" + startX + " startY:" + startY);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    private boolean pointInRect(float x, float y, RectF rectF) {
        if (x < rectF.left) {
            return false;
        }
        if (x > rectF.right) {
            return false;
        }
        if (y < rectF.top) {
            return false;
        }
        if (y > rectF.bottom) {
            return false;
        }
        return true;
    }

    private void drawColor(Canvas canvas) {
        //上下左右
        colorRectPadding = 10;
        //每行多少个
        rectCountRow = 6;
        //有多少行
        rectCountColumn = 0;
        if (colors.length % rectCountRow == 0) {
            rectCountColumn = colors.length / rectCountRow;
        } else {
            rectCountColumn = colors.length / rectCountRow + 1;
        }
        colorRectSize = (borderWidth - colorRectPadding * (rectCountRow + 1)) / rectCountRow;
        colorRects = new ArrayList<>();
        for (int i = 1; i <= rectCountRow; i++) {
            for (int j = 1; j <= rectCountColumn; j++) {

                RectF colorRect = new RectF(
                        borderPaddingLeft + i * colorRectPadding + (i - 1) * colorRectSize,
                        borderPaddingTop + j * colorRectPadding + (j - 1) * colorRectSize,
                        borderPaddingLeft + i * colorRectPadding + i * colorRectSize,
                        borderPaddingTop + j * colorRectPadding + j * colorRectSize);

                mPaint.setColor(Color.parseColor(colors[(j - 1) * 6 + i - 1]));
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawOval(colorRect, mPaint);
//                mPaint.setStyle(Paint.Style.STROKE);
//                mPaint.setColor(Color.parseColor(colors[(j - 1) * 6 + i - 1]));
//               canvas.drawOval(colorRect, mPaint);
                colorRects.add(colorRect);
            }

        }

        //reset paint
        mPaint.setColor(Color.parseColor("#000000"));
        mPaint.setStyle(Paint.Style.STROKE);
        //  canvas.drawRect(colorRect, mPaint);
    }

    private void drawBorder(Canvas canvas) {
        screenWidth = ScreenUtil.getScreenWidth(getContext());

        borderPaddingLeft = DEFAULT_BORDER_PADDING_LEFT;
        borderPaddingTop = DEFAULT_BORDER_PADDING_TOP;
        borderWidth = screenWidth - borderPaddingLeft * 2;
        drawColor(canvas);//用到的变量在这里已经有值
        borderHeight = rectCountColumn * colorRectSize + colorRectPadding * (rectCountColumn + 1);
        Rect borderRect = new Rect(
                borderPaddingLeft,
                borderPaddingTop,
                borderWidth + borderPaddingLeft,
                borderPaddingTop + borderHeight);

        canvas.drawRect(borderRect, mPaint);

        if (isFirstStart) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.BLACK);
            cacheCanvas.drawOval(colorRects.get(0), mPaint);
        }
    }
}
