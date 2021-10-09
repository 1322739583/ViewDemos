package com.xzh.viewdemos;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ImageUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    PaintView2 paintView;
    ColorView colorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       paintView = findViewById(R.id.photoView);
       colorView=findViewById(R.id.colorView);
       paintView.getPaint().setColor(Color.GREEN);
    //    view.invalidate();
       // view.setOnTouchListener(this);
    }

    public void showColorPartten(View view) {
        int visibility = colorView.getVisibility();
        if (visibility==View.INVISIBLE||visibility==View.GONE) {
            colorView.setVisibility(View.VISIBLE);
        }else {
            colorView.setVisibility(View.GONE);
        }
    }

    public void saveBitmap(View view) {
       paintView.saveImage();
    }

    public void clearBitmap(View view) {
        paintView.clearImage();
    }
}
