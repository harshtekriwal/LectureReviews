package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

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

import static okhttp3.RequestBody.create;

public class LectureFeedback extends AppCompatActivity {
    EditText lecturecode;
    RatingBar knowledge;
    RatingBar clarity;
    RatingBar communication;
    Button submit;
    SharedPreferences sharedPreferences;
    OkHttpClient client=new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_feedback);
        knowledge=(RatingBar)findViewById(R.id.ratingBar_knowledge);
        communication=(RatingBar)findViewById(R.id.ratingBar_communication);
        clarity=(RatingBar)findViewById(R.id.ratingBar_clarity);
        knowledge.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override public void onRatingChanged(RatingBar ratingBar, float rating,
                                                  boolean fromUser) {
                if(rating<1.0f)
                    ratingBar.setRating(1.0f);
            }
        });
        communication.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override public void onRatingChanged(RatingBar ratingBar, float rating,
                                                  boolean fromUser) {
                if(rating<1.0f)
                    ratingBar.setRating(1.0f);
            }
        });
        clarity.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override public void onRatingChanged(RatingBar ratingBar, float rating,
                                                  boolean fromUser) {
                if(rating<1.0f)
                    ratingBar.setRating(1.0f);
            }
        });
        lecturecode=(EditText)findViewById(R.id.edit_text_lecturecode);
        submit=(Button)findViewById(R.id.button_lecturefeedback);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lcode=lecturecode.getText().toString();
                if (TextUtils.isEmpty(lcode)) {
                    lecturecode.setError("LectureCode cant be Empty");
                    return;
                }
                FeedbackDetails feedbackDetails=new FeedbackDetails();
                feedbackDetails.setClarity((int)clarity.getRating());
                feedbackDetails.setCommunication((int)communication.getRating());
                feedbackDetails.setKnowledge((int)knowledge.getRating());
                sharedPreferences = getSharedPreferences("studentdetails", Context.MODE_PRIVATE);
                int enrollment = sharedPreferences.getInt("enrollment", 0);
                feedbackDetails.setStudentroll(enrollment);
                feedbackDetails.setLectureid(Integer.parseInt(lcode));
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create("{\n\t\t\"lectureid\":"+feedbackDetails.getLectureid()+",\n\t\t\"studentroll\":"+feedbackDetails.getStudentroll()+",\n\t\t\"knowledge\":"+feedbackDetails.getKnowledge()+",\n\t\t\"communication\":"+feedbackDetails.getCommunication()+",\n\t\t\"clarity\":"+feedbackDetails.getClarity()+"\n}\t",mediaType);
                Request request = new Request.Builder()
                        .url("http://10.0.2.2:8080/DetailsVerifiation/webapi/postfeedback/submit")
                        .post(body)
                        .addHeader("content-type", "application/json")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("postman-token", "7f6666c2-88e5-a8d6-2f1b-fcea5b40c48a")
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder alert = new AlertDialog.Builder(LectureFeedback.this);
                                alert.setTitle("ERROR");
                                alert.setMessage("Server Offline");
                                alert.setCancelable(false);
                                alert.setPositiveButton("OK", null);
                                AlertDialog dialog=alert.create();
                                dialog.show();
                            }
                        });
                        e.printStackTrace();
                    }
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        String json = response.body().string();
                        Gson g = new Gson();

                        final FeedbackProblem problem = g.fromJson(json, FeedbackProblem.class);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                if (problem.toString().equals("NOPROBLEM")) {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(LectureFeedback.this);
                                    alert.setTitle("Result");
                                    alert.setMessage("Congratulations!!! Review Submitted Successfully..");
                                    alert.setCancelable(false);
                                    alert.setPositiveButton("OK", null);
                                    AlertDialog dialog=alert.create();
                                    dialog.show();
                                } else if(problem.toString().equals("WRONGCODE")) {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(LectureFeedback.this);
                                    alert.setTitle("ERROR");
                                    alert.setMessage("Lecture Code is Incorrect...");
                                    alert.setCancelable(false);
                                    alert.setPositiveButton("OK", null);
                                    AlertDialog dialog=alert.create();
                                    dialog.show();
                                }
                                else if(problem.toString().equals("ALREADYRATED")){
                                    AlertDialog.Builder alert = new AlertDialog.Builder(LectureFeedback.this);
                                    alert.setTitle("ERROR");
                                    alert.setMessage("You have already rated for this lecture...");
                                    alert.setCancelable(false);
                                    alert.setPositiveButton("OK", null);
                                    AlertDialog dialog=alert.create();
                                    dialog.show();
                                }
                                else if(problem.toString().equals("WRONGSEMBATCH")){
                                    AlertDialog.Builder alert = new AlertDialog.Builder(LectureFeedback.this);
                                    alert.setTitle("ERROR");
                                    alert.setMessage("The lecture you are trying to review is not taught in your class/semester.");
                                    alert.setCancelable(false);
                                    alert.setPositiveButton("OK", null);
                                    AlertDialog dialog=alert.create();
                                    dialog.show();
                                }
                                else if(problem.toString().equals("TIMEEXCEEDED")){
                                    AlertDialog.Builder alert = new AlertDialog.Builder(LectureFeedback.this);
                                    alert.setTitle("ERROR");
                                    alert.setMessage("Time up... Cant rate this lecture anymore.");
                                    alert.setCancelable(false);
                                    alert.setPositiveButton("OK", null);
                                    AlertDialog dialog=alert.create();
                                    dialog.show();
                                }
                                else if(problem.toString().equals("SQLERROR")){
                                    AlertDialog.Builder alert = new AlertDialog.Builder(LectureFeedback.this);
                                    alert.setTitle("ERROR");
                                    alert.setMessage("ERROR 404.. FATAL!!");
                                    alert.setCancelable(false);
                                    alert.setPositiveButton("OK", null);
                                    AlertDialog dialog=alert.create();
                                    dialog.show();
                                }
                            }
                        });
                    }

                });
            }
        });

    }
}
