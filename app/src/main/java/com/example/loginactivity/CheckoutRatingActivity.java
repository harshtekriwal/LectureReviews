package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CheckoutRatingActivity extends AppCompatActivity {
    OkHttpClient client;
    Button overallrating;
    Button individualrating;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_rating);
        overallrating = (Button) findViewById(R.id.button_overallrating);
        individualrating=(Button)findViewById(R.id.button_individual_feedback);
        overallrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                int teacherid = sharedPreferences.getInt("id", 0);
                client = new OkHttpClient();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create("{\n\t\t\"teacherid\":" + teacherid + "\n\t\t\n}\t", mediaType);
                Request request = new Request.Builder()
                        .url("http://10.0.2.2:8080/DetailsVerifiation/webapi/postaveragerating/submit")
                        .post(body)
                        .addHeader("content-type", "application/json")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("postman-token", "10d045fe-8c62-544d-93e6-ca4fa75a10a8")
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String json = response.body().string();
                        Gson g = new Gson();
                        AverageOverallRatings ratings = g.fromJson(json, AverageOverallRatings.class);
                        if (ratings.isIssuccess() == true) {
                            Intent intent = new Intent(com.example.loginactivity.CheckoutRatingActivity.this, com.example.loginactivity.OverallRatingActivity.class);
                            intent.putExtra("ratings", (Serializable) ratings);
                            startActivity(intent);
                        } else {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(CheckoutRatingActivity.this);
                                    alert.setTitle("ERROR");
                                    alert.setMessage("Error 404 !! FATAL");
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
                                AlertDialog.Builder alert = new AlertDialog.Builder(CheckoutRatingActivity.this);
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
        individualrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(com.example.loginactivity.CheckoutRatingActivity.this,com.example.loginactivity.IndividualLectureFeedbackActivity.class);
                startActivity(intent);
            }
        });
    }
}
