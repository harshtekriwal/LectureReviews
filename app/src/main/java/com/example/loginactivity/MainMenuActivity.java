package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    Button loginasteacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        loginasteacher=(Button) findViewById(R.id.loginasteacher);

        (loginasteacher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent teachermenu ;
                teachermenu=new Intent(com.example.loginactivity.MainMenuActivity.this,com.example.loginactivity.MainActivity.class);
                startActivity(teachermenu);
            }
        });
    }
}
