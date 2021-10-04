package com.xzh.viewdemos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
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
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor(defaultPaintColor));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBorder(canvas);
       // drawColor(canvas);


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
        List<RectF> colorRects = new ArrayList<>();
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
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setColor(Color.parseColor("#ffffff"));
                canvas.drawOval(colorRect, mPaint);
            }

        }

        //reset paint
        mPaint.setColor(Color.parseColor("#000000"));
        //  canvas.drawRect(colorRect, mPaint);
    }

    private void drawBorder(Canvas canvas) {
        screenWidth = ScreenUtil.getScreenWidth(getContext());
        borderPaddingLeft = 100;
        borderPaddingTop = 50;
        borderWidth = screenWidth - borderPaddingLeft * 2;
        drawColor(canvas);//用到的变量在这里已经有值
        borderHeight =rectCountColumn*colorRectSize+colorRectPadding*(rectCountColumn+1);
        Rect borderRect = new Rect(
                borderPaddingLeft,
                borderPaddingTop,
                borderWidth + borderPaddingLeft,
                borderPaddingTop + borderHeight);

        canvas.drawRect(borderRect, mPaint);
    }
}
