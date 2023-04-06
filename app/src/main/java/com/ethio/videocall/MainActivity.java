package com.ethio.videocall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.GridView;
import android.widget.ImageView;
import com.ethio.videocall.Model.ModelCall;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView btn,imgopencamera ,imgcall,imageRoter;
    GridView coursesGV;
    boolean isRotate=true;
    int MY_CAMERA_REQUEST_CODE=100;
    static final int CAMERA_PIC_REQUEST = 1337;
    static  final  int CAMERA_PERMISSION_CODE=100;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        coursesGV = findViewById(R.id.idGVcourses);
        ArrayList<ModelCall> courseModelArrayList = new ArrayList<ModelCall>();
        imgopencamera=findViewById(R.id.imgcamera);
        imgcall=findViewById(R.id.imgcall);
        imageRoter=findViewById(R.id.imgrotate);
        imageRoter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ChangeScreenOr();

            }
        });
        imgcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,CallingActivity.class);
                startActivity(intent);
                finish();
            }
        });
        imgopencamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
                Toast.makeText(MainActivity.this, "well come to android studio", Toast.LENGTH_SHORT).show();
            }
        });

        courseModelArrayList.add(new ModelCall("John Doe", R.drawable.img1));
        courseModelArrayList.add(new ModelCall("John Mr ", R.drawable.img2));
        courseModelArrayList.add(new ModelCall("John Do", R.drawable.img3));
        courseModelArrayList.add(new ModelCall("Martin s", R.drawable.img6));
        courseModelArrayList.add(new ModelCall("Donald t ", R.drawable.img4));
        courseModelArrayList.add(new ModelCall("Cris Jho", R.drawable.img5));

        com.example.VideoCall.model.CallAdapter adapter = new com.example.VideoCall.model.CallAdapter(this, courseModelArrayList);
        coursesGV.setAdapter(adapter);
        btn=findViewById(R.id.img);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRotate==false)
                {
                    isRotate=true;
                }else
                {  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    isRotate=false;

                }



            }
        });
    }
//    private void ChangeScreenOr() {
//        if (isRotate) {
//            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            // else change to Portrait
//        } else {
//            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        }
//        isRotate = !isRotate;
//
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { permission }, requestCode);
        }
        else {

               Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
               startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {

            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Showing the toast message
                Toast.makeText(MainActivity.this, "Camera Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void mute() {
        AudioManager amanager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            amanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        }
        amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
    }

    public void unmute() {
        AudioManager amanager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            amanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        }
        amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
    }
}