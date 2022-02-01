package com.xzh.viewdemos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MotionEventActivity extends AppCompatActivity {


    private MyView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_event);

        view = findViewById(R.id.myView);
        Log.d("MotionEventActivity", "view.getWidth():" + view.getWidth());
        Log.d("MotionEventActivity", "view.getRight():" + view.getRight());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d("MotionEvent FocusChang", "view.getWidth():" + view.getWidth());
        Log.d("MotionEvent FocusChang", "view.getRight():" + view.getRight());
    }


}
