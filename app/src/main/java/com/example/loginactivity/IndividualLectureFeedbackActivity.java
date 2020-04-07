package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.renderscript.Int2;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class IndividualLectureFeedbackActivity extends AppCompatActivity {

    TextView date;
    Button sub;
    SharedPreferences sharedPreferences;
    Calendar currentdate;
    int day,month,year;
    String d,m,y;
    OkHttpClient client;
    Spinner subject;
    Spinner semester;
    Spinner batch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_lecture_feedback);
        sub=(Button)findViewById(R.id.button2_submit);
        currentdate=Calendar.getInstance();
        day=currentdate.get(Calendar.DAY_OF_MONTH);
        month=currentdate.get(Calendar.MONTH);
        year=currentdate.get(Calendar.YEAR);
        d=String.format("%02d",day);
        m=String.format("%02d",month);
        y=Integer.toString(year);
        date=(TextView)findViewById(R.id.textView_date);
        subject=(Spinner)findViewById(R.id.spinner_subject);
        batch =(Spinner)findViewById(R.id.spinner_batch);
        semester=(Spinner)findViewById(R.id.spinner_semester);

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lec_name=subject.getItemAtPosition(subject.getSelectedItemPosition()).toString();
                String batch_b=batch.getItemAtPosition(batch.getSelectedItemPosition()).toString();
                String sem=semester.getItemAtPosition(semester.getSelectedItemPosition()).toString();
                sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                int teacherid = sharedPreferences.getInt("id", 0);
                client = new OkHttpClient();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create( "{\n\t\t\"teacherid\":"+teacherid+",\n\t\t\"semester\":"+Integer.parseInt(sem)+",\n\t\t\"day\":\""+d+"\",\n\t\t\"month\":\""+m+"\",\n\t\t\"year\":\""+y+"\",\n\t\t\"batch\":\""+batch_b+"\",\n\t\t\"lecturename\":\""+lec_name+"\"\n}\t",mediaType);
                Request request = new Request.Builder()
                        .url("http://192.168.1.8:8080/DetailsVerifiation/webapi/postindividualrating/submit")
                        .post(body)
                        .addHeader("content-type", "application/json")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("postman-token", "12893515-796c-65ed-22a3-831cc94b7583")
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String json = response.body().string();
                        Gson g = new Gson();
                        AverageOverallRatings ratings = g.fromJson(json, AverageOverallRatings.class);
                        if (ratings.isIssuccess() == true) {
                            Intent intent = new Intent(com.example.loginactivity.IndividualLectureFeedbackActivity.this, com.example.loginactivity.OverallRatingActivity.class);
                            intent.putExtra("ratings", (Serializable) ratings);
                            startActivity(intent);
                        } else {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(IndividualLectureFeedbackActivity.this);
                                    alert.setTitle("ERROR");
                                    alert.setMessage("The information entered is incorrect. Try entering correct information.");
                                    alert.setCancelable(false);
                                    alert.setPositiveButton("OK", null);
                                    AlertDialog dialog = alert.create();
                                    dialog.show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder alert = new AlertDialog.Builder(IndividualLectureFeedbackActivity.this);
                                alert.setTitle("ERROR");
                                alert.setMessage("Server Offline");
                                alert.setCancelable(false);
                                alert.setPositiveButton("OK", null);
                                AlertDialog dialog = alert.create();
                                dialog.show();
                            }
                        });
                        e.printStackTrace();
                    }


                });


            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(com.example.loginactivity.IndividualLectureFeedbackActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1=i1+1;
                        d=String.format("%02d",i2);
                        m=String.format("%02d",i1);
                        y=Integer.toString(i);
                    date.setText(String.format("%02d",i2)+"/"+String.format("%02d",i1)+"/"+i);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.mainmenu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.item_aboutapp){
            Intent welcomeintent;
            welcomeintent = new Intent(com.example.loginactivity.IndividualLectureFeedbackActivity.this,com.example.loginactivity.abouttheapp.class);
            startActivity(welcomeintent);
        }
        if(item.getItemId()==R.id.item_logout){
            SharedPreferences sharedpreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.apply();
            Intent welcomeintent;
            welcomeintent = new Intent(com.example.loginactivity.IndividualLectureFeedbackActivity.this,com.example.loginactivity.MainMenuActivity.class);
            welcomeintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(welcomeintent);
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
}
