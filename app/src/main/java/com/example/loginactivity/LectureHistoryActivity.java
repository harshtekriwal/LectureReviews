package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class LectureHistoryActivity extends AppCompatActivity {
ListView lecture_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_history);
        lecture_history=(ListView)findViewById(R.id.ListView_lecturehistory);
        Intent intent=getIntent();
        LectureHistory ratings=(LectureHistory) intent.getSerializableExtra("ratings");
        ArrayList<LectureReviewDetails> alist= new ArrayList<LectureReviewDetails>();
        for(LectureReviewDetails details:ratings.getLecture_history()){
            alist.add(details);
        }

        ArrayAdapter arrayAdapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,alist);
        lecture_history.setAdapter(arrayAdapter);
    }
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.mainmenu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.item_aboutapp){
            Intent welcomeintent;
            welcomeintent = new Intent(com.example.loginactivity.LectureHistoryActivity.this,com.example.loginactivity.abouttheapp.class);
            startActivity(welcomeintent);
        }
        if(item.getItemId()==R.id.item_logout){
            SharedPreferences sharedpreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.apply();
            Intent welcomeintent;
            welcomeintent = new Intent(com.example.loginactivity.LectureHistoryActivity.this,com.example.loginactivity.MainMenuActivity.class);
            welcomeintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(welcomeintent);
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
}
