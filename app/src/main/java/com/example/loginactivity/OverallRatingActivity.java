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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.mainmenu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.item_aboutapp){
            Intent welcomeintent;
            welcomeintent = new Intent(com.example.loginactivity.OverallRatingActivity.this,com.example.loginactivity.abouttheapp.class);
            startActivity(welcomeintent);
        }
        if(item.getItemId()==R.id.item_logout){
            SharedPreferences sharedpreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.apply();
            Intent welcomeintent;
            welcomeintent = new Intent(com.example.loginactivity.OverallRatingActivity.this,com.example.loginactivity.MainMenuActivity.class);
            welcomeintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(welcomeintent);
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
}
