package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WelcomeTeacher extends AppCompatActivity {
    TextView t;
    Button checkrating;
    Button generateotpactivity;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_teacher);
        t=(TextView)findViewById(R.id.teacher_welcome);
        checkrating=(Button)findViewById(R.id.ratings);
        generateotpactivity=(Button)findViewById((R.id.take_review));
        sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String nameofteacher=sharedPreferences.getString("username","");
        t.setText(" Welcome  "+nameofteacher);
        generateotpactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent get_lecture_review_intent = new Intent(com.example.loginactivity.WelcomeTeacher.this,com.example.loginactivity.GenerateOTP.class);
                startActivity(get_lecture_review_intent);
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
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.mainmenu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.item_aboutapp){
            Intent welcomeintent;
            welcomeintent = new Intent(com.example.loginactivity.WelcomeTeacher.this,com.example.loginactivity.abouttheapp.class);
            startActivity(welcomeintent);
        }
        if(item.getItemId()==R.id.item_logout){
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
        return super.onOptionsItemSelected(item);

    }
}
