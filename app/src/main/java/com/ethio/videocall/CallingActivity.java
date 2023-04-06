package com.ethio.videocall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CallingActivity extends AppCompatActivity {
    ImageView btnback,btnAccept;
    TextView textViewName;
    ConstraintLayout constraintLayout;
    private static int[] imgs = { R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4,R.drawable.img5,R.drawable.img6 };



    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);
        getSupportActionBar().hide();
        textViewName=findViewById(R.id.textView);

        constraintLayout=findViewById(R.id.bgcallActiviry);

        Intent intent = getIntent();
        int indexprofile=intent.getIntExtra("index",0);
        String getName = intent.getStringExtra("name");
        String imgepath = intent.getStringExtra("number");
        textViewName.setText(getName);

        constraintLayout.setBackgroundResource(imgs[indexprofile]);

        btnback=findViewById(R.id.imgdecline);
        btnAccept=findViewById(R.id.imgaccept);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CallingActivity.this,MainManu.class);
                intent.putExtra("name", getName);
                intent.putExtra("number", imgepath);
                intent.putExtra("index",indexprofile);
                startActivity(intent);
                finish();

            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CallingActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}