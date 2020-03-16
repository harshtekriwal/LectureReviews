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
    Button logout;
    Button checkrating;
    Button generateotpactivity;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_teacher);
        checkrating=(Button)findViewById(R.id.ratings);
        generateotpactivity=(Button)findViewById((R.id.take_review));
        logout=(Button)findViewById(R.id.logout_button);
        t=(EditText)findViewById(R.id.teacher_welcome);
        sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String enroll=sharedPreferences.getString("username","");
        System.out.println(enroll);
        t.setText("                       Welcome   "+enroll);
        generateotpactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent get_lecture_review_intent = new Intent(com.example.loginactivity.WelcomeTeacher.this,com.example.loginactivity.GenerateOTP.class);
                startActivity(get_lecture_review_intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
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
        checkrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(com.example.loginactivity.WelcomeTeacher.this,com.example.loginactivity.CheckoutRatingActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
   public void onBackPressed(){

   }
}
