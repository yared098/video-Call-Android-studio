package com.ethio.videocall;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class MainManu extends AppCompatActivity {

    Handler handler;
    VideoView videoView;
    View csn;
    ImageView imageViewMute;
    boolean isSoundOn=true;
    TextView callerName;
    private static int[] imgs = { R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4,R.drawable.img5,R.drawable.img6 };
    private static int[] vedios = { R.raw.img1, R.raw.img2, R.raw.img3, R.raw.img4 ,R.raw.vd5,R.raw.vd6};


    @SuppressLint({"MissingInflatedId", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_manu);

        csn= findViewById(R.id.constrnbg);
        imageViewMute=findViewById(R.id.imgmute);
        callerName=findViewById(R.id.txtname);


        getSupportActionBar().hide();
        videoView =(VideoView)findViewById(R.id.vdiew2);
        Intent intent = getIntent();
        int indexprofile=intent.getIntExtra("index",0);
        String getName = intent.getStringExtra("name");
        callerName.setText(getName);
        String imgepath = intent.getStringExtra("number");

        csn.setBackgroundResource(imgs[indexprofile]);

        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/"+vedios[indexprofile]);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();

        if (isSoundOn==true)
        {
            videoView.start();
        }else
        {
            videoView.stopPlayback();
        }
        imageViewMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSoundOn=false;
                videoView.stopPlayback();

            }
        });
        MediaPlayer mp = MediaPlayer.create(this, uri);
        int duration = mp.getDuration();
//        Toast.makeText(this, "Duration"+duration, Toast.LENGTH_SHORT).show();
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainManu.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },duration);

    }

    @Override
    protected void onDestroy() {
        videoView.stopPlayback();
        finish();
        super.onDestroy();
    }
}