package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Rating;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OverallRatingActivity extends AppCompatActivity {
    OkHttpClient client;
    RatingBar communication;
    RatingBar clarity;
    RatingBar knowledge;
    TextView numberofstudents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall_rating);
        clarity=(RatingBar)findViewById(R.id.ratingBar_clarity);
        knowledge=(RatingBar)findViewById(R.id.ratingBar_knowledge);
        communication=(RatingBar)findViewById(R.id.ratingBar_communication);
        communication.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        clarity.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        knowledge.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        numberofstudents=(TextView)findViewById(R.id.textView_numberofstudents);
        Intent intent=getIntent();
        AverageOverallRatings ratings=(AverageOverallRatings)intent.getSerializableExtra("ratings");
                communication.setRating(ratings.getCommunication());
                knowledge.setRating(ratings.getKnowledge());
                clarity.setRating(ratings.getClarity());
                int x=ratings.getNumberofstudents();
                System.out.println(x);
                numberofstudents.setText(Integer.toString(x));

    }
}
