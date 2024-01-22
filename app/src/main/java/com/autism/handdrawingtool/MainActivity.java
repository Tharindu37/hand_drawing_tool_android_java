package com.autism.handdrawingtool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;
import android.Manifest;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private float floatStartX=-1, floatStartY=-1,floatEndX=-1, floatEndY=-1;
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint=new Paint();

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView=findViewById(R.id.imageView);
        button=findViewById(R.id.button);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                buttonSaveImage(v);
//            }
//        });

        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, PackageManager.PERMISSION_GRANTED);




    }

    private void drawPaintSketchImage(){
        if (bitmap==null){
            bitmap=Bitmap.createBitmap(imageView.getWidth(),imageView.getHeight(),
                    Bitmap.Config.ARGB_8888);
            canvas=new Canvas(bitmap);
            paint.setColor(Color.RED);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(8);
        }

        canvas.drawLine(floatStartX,
                floatStartY-220,floatEndX,floatEndY-220,paint);

        imageView.setImageBitmap(bitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            floatStartX=event.getX();
            floatStartY=event.getY();
        }
        if (event.getAction()==MotionEvent.ACTION_MOVE){
            floatEndX=event.getX();
            floatEndY=event.getY();
            drawPaintSketchImage();
            floatStartX=event.getX();
            floatStartY=event.getY();
        }
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            floatEndX=event.getX();
            floatEndY=event.getY();
            drawPaintSketchImage();
        }
        return super.onTouchEvent(event);
    }

//    public void  buttonSaveImage(View view){
//        File fileSaveImage=new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
//                Calendar.getInstance().getTime().toString()+".jpg");
//        try {
//            FileOutputStream fileOutputStream=new FileOutputStream(fileSaveImage);
//            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
//            fileOutputStream.flush();
//            fileOutputStream.close();
//            Toast.makeText(this,"File Saved Successfully",Toast.LENGTH_LONG);
//        }catch (Exception e){
//            e.printStackTrace();
//            Toast.makeText(this,"File Not Saved Successfully",Toast.LENGTH_LONG);
//        }
//    }
public void buttonSaveImage(View view) {
    File fileSaveImage = new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            Calendar.getInstance().getTime().toString() + ".jpg");
    try {
        FileOutputStream fileOutputStream = new FileOutputStream(fileSaveImage);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        Toast.makeText(this, "File Saved Successfully", Toast.LENGTH_LONG).show();
    } catch (Exception e) {
        e.printStackTrace();
        Toast.makeText(this, "File Not Saved Successfully", Toast.LENGTH_LONG).show();
    }
}

}