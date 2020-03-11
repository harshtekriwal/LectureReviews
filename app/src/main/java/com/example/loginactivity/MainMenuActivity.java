package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Button loginasteacher;
    Button loginasstudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        sharedPreferences= getSharedPreferences("mypref", Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean("logged", false)) {
            Intent welcomeintent;
            welcomeintent = new Intent(com.example.loginactivity.MainMenuActivity.this, com.example.loginactivity.WelcomeTeacher.class);
            startActivity(welcomeintent);
        }
        sharedPreferences= getSharedPreferences("studentdetails", Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean("logged", false)) {
            Intent welcomeintent;
            welcomeintent = new Intent(com.example.loginactivity.MainMenuActivity.this, com.example.loginactivity.WelcomeStudent.class);
            startActivity(welcomeintent);
        }

        loginasteacher=(Button) findViewById(R.id.loginasteacher);
        loginasstudent=(Button)findViewById(R.id.loginasstudent);

        (loginasteacher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent teachermenu ;
                teachermenu=new Intent(com.example.loginactivity.MainMenuActivity.this,com.example.loginactivity.MainActivity.class);
                startActivity(teachermenu);

            }
        });
        loginasstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent studentmenu;
                studentmenu=new Intent(com.example.loginactivity.MainMenuActivity.this,com.example.loginactivity.StudentLoginActivity.class);
                startActivity(studentmenu);
            }
        });
    }
}
