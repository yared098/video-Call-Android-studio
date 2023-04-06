package com.ethio.videocall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.common.util.concurrent.ListenableFuture;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainManu extends AppCompatActivity {
    private Executor executor = Executors.newSingleThreadExecutor();
    private int REQUEST_CODE_PERMISSIONS = 1001;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};
    PreviewView mPreviewView;
    boolean isPortrait=false;

    Handler handler;
    //ImageView videoView;
    VideoView backVideo;
    ImageView imageViewMute,imageViewperson;
    boolean isSoundOn=true;
    TextView callerName;
    ImageView Imgcallback;
    ImageView screenRotet;
    private static int[] imgs = { R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4,R.drawable.img5,R.drawable.img6 };
    private static int[] vedios = { R.raw.img1, R.raw.img2, R.raw.img3, R.raw.img4 ,R.raw.vd5,R.raw.vd6};


    @SuppressLint({"MissingInflatedId", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_manu);
        mPreviewView = findViewById(R.id.previewView);
        screenRotet=findViewById(R.id.imgrotate2);
        screenRotet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ChangeScreenOr();
            }
        });
        if(allPermissionsGranted()){
            startCamera(); //start camera if permission has been granted by user
        } else{
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }
        Imgcallback=findViewById(R.id.imgcallback);

        backVideo= findViewById(R.id.videoView);
        imageViewMute=findViewById(R.id.imgmute);
        callerName=findViewById(R.id.txtname);


        getSupportActionBar().hide();
        Intent intent = getIntent();
        int indexprofile=intent.getIntExtra("index",0);
        String getName = intent.getStringExtra("name");
        callerName.setText(getName);
        String imgepath = intent.getStringExtra("number");
        // play back ground
        MediaController _mediacontrole=new MediaController(this);
        _mediacontrole.setAnchorView(backVideo);
        Uri backurl = Uri.parse("android.resource://" + getPackageName() + "/"+vedios[indexprofile]);
        backVideo.setMediaController(_mediacontrole);
        backVideo.setVideoURI(backurl);
        backVideo.requestFocus();
        backVideo.start();
        MediaController mediaController= new MediaController(this);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/"+vedios[indexprofile]);
        imageViewMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSoundOn=false;
                backVideo.stopPlayback();
            }
        });
        MediaPlayer mp = MediaPlayer.create(this, uri);
        int duration = mp.getDuration();
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                Intent intent=new Intent(MainManu.this,MainActivity.class);
                startActivity(intent);

            }
        },duration);
        //  callback function
        Imgcallback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainManu.this,CallingActivity.class);
                intent.putExtra("name", getName);
                intent.putExtra("number", imgepath);
                intent.putExtra("index",indexprofile);
                finish();
                startActivity(intent);

            }
        });
    }
    private void ChangeScreenOr() {
          if (isPortrait) {
              MainManu.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            // else change to Portrait
        } else {
              MainManu.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
        isPortrait = !isPortrait;
        
    }

    private void startCamera() {

        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {

                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    bindPreview(cameraProvider);

                } catch (ExecutionException | InterruptedException e) {
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }

    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {

        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                .build();

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .build();

        ImageCapture.Builder builder = new ImageCapture.Builder();
        final ImageCapture imageCapture = builder
                .setTargetRotation(this.getWindowManager().getDefaultDisplay().getRotation())
                .build();
        preview.setSurfaceProvider(mPreviewView.createSurfaceProvider());
        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, preview, imageAnalysis, imageCapture);
    }

    private boolean allPermissionsGranted(){

        for(String permission : REQUIRED_PERMISSIONS){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        //videoView.stopPlayback();
        finish();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent=new Intent(MainManu.this,CallingActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}