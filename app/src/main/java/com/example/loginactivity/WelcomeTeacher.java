package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WelcomeTeacher extends AppCompatActivity {
    EditText t;
    Button b;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_teacher);
        b=(Button)findViewById(R.id.logout_button);
        t=(EditText)findViewById(R.id.teacher_welcome);
        sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String enroll=sharedPreferences.getString("username","");
        System.out.println(enroll);
        t.setText("                       Welcome   "+enroll);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.apply();
                Intent welcomeintent;
                welcomeintent = new Intent(com.example.loginactivity.WelcomeTeacher.this,com.example.loginactivity.MainMenuActivity.class);
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
