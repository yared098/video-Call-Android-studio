package com.example.VideoCall.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ethio.videocall.CallingActivity;
import com.ethio.videocall.MainManu;
import com.ethio.videocall.Model.ModelCall;
import com.ethio.videocall.R;

import java.util.ArrayList;

public class CallAdapter  extends ArrayAdapter<ModelCall> {
    public CallAdapter(@NonNull Context context, ArrayList<ModelCall> courseModelArrayList) {
        super(context, 0, courseModelArrayList);
    }
    @NonNull
    @Override
    public View getView( int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.cardimage, parent, false);
        }

        ModelCall modelCaller = getItem(position);
        TextView callname = listitemView.findViewById(R.id.txt);
        ImageView callerProfile = listitemView.findViewById(R.id.img);
        callerProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), CallingActivity.class);
                intent.putExtra("name", modelCaller.getCourse_name());
                intent.putExtra("number", modelCaller.getImgid());
                intent.putExtra("index",position);
                getContext().startActivity(intent);

            }
        });

        callname.setText(modelCaller.getCourse_name());
        callerProfile.setImageResource(modelCaller.getImgid());
        return listitemView;
    }
}