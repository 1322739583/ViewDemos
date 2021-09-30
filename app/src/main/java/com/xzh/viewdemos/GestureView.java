package com.xzh.viewdemos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.Nullable;

import static android.view.GestureDetector.*;

/**
 * 通过自定义View实现一个画板
 * 练习手势滑动
 */
public class GestureView extends SurfaceView {

    private GestureDetector gestureDetector;
    private PaintGestureListener paintGestureListener;
    private Paint paint;


    public GestureView(Context context) {
        super(context);
        initView(context, null);
    }

    public GestureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }
    float countX=0;
    private void initView(Context context, AttributeSet attrs) {
        paintGestureListener = new PaintGestureListener(countX);
        gestureDetector = new GestureDetector(getContext(), paintGestureListener);
        paint=new Paint();
        paint.setAntiAlias(true);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_UP:
                //Log.d("PaintGestureListener", "onTouchEvent: ");
               // paintGestureListener.setCountX(0);
                break;
        }
        gestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    class PaintGestureListener extends SimpleOnGestureListener {

          float countX;

          public void setCountX(float countX) {
              this.countX = countX;
          }

          public PaintGestureListener(float countX) {
            this.countX=countX;
        }



      //  float countX;
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {


            countX+=distanceX;
            Log.d("PaintGestureListener", "distanceX:" + countX + "distanceY:" + distanceY);
         //   Log.d("PaintGestureListener", "distanceX:" + distanceX + "distanceY:" + distanceY);
            Log.d("PaintGestureListener", "onScroll: "+(e2.getX()-e1.getX()));

            return true;
        }

          @Override
          public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
              return super.onFling(e1, e2, velocityX, velocityY);
          }

          @Override
        public boolean onSingleTapUp(MotionEvent e) {
            //滑动的时候不会调用
            Log.d("PaintGestureListener", "onSingleTapUp: ");
            return true;
        }
    }
}
