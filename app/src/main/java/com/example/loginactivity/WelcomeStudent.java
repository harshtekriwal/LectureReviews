package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WelcomeStudent extends AppCompatActivity {
    EditText text;
    Button logout;
    Button ratelecture;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_student);
        text=(EditText)findViewById(R.id.student_welcome);
        logout=(Button)findViewById(R.id.logout_button_student);
        ratelecture=(Button)findViewById(R.id.button_post_rating);
        sharedPreferences = getSharedPreferences("studentdetails", Context.MODE_PRIVATE);
        String username=sharedPreferences.getString("username","");
        text.setText("                       Welcome   "+username);
        ratelecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(com.example.loginactivity.WelcomeStudent.this,com.example.loginactivity.LectureFeedback.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = getSharedPreferences("studentdetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.apply();
                Intent welcomeintent;
                welcomeintent = new Intent(com.example.loginactivity.WelcomeStudent.this,com.example.loginactivity.MainMenuActivity.class);
                welcomeintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(welcomeintent);
                finish();
            }
        });



    }

    @Override
    public void onBackPressed(){
    }
}
