package com.ethio.videocall;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.ethio.videocall.Model.ModelCall;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView btn;
    GridView coursesGV;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        coursesGV = findViewById(R.id.idGVcourses);
        ArrayList<ModelCall> courseModelArrayList = new ArrayList<ModelCall>();

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
                Intent newint=new Intent(MainActivity.this,CallingActivity.class);
                startActivity(newint);
            }
        });
    }
}