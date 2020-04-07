package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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

public class StudentLoginActivity extends AppCompatActivity {
    EditText enrollment;
    EditText password;
    Button login;
    SharedPreferences sharedPreferences;
    OkHttpClient client=new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        enrollment=(EditText)findViewById(R.id.edittext_enrollment_student);
        password=(EditText)findViewById(R.id.edittext_password_student);
        login=(Button)findViewById(R.id.button_login_student);
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("xD");
                final String x = enrollment.getText().toString();
                if (TextUtils.isEmpty(x)) {
                    enrollment.setError("Username cant be empty");
                    return;
                }
                final int enrollment = Integer.parseInt(x);
                final String pass = password.getText().toString();
                if (TextUtils.isEmpty(pass)) {
                    password.setError("Password cant be empty");
                    return;
                }
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create("{\n        \"studentid\": \"" + enrollment + "\",\n        \"studentpassword\":\"" + pass + "\"\n}", mediaType);
                Request request = new Request.Builder()
                        .url("http://192.168.1.28:8080/DetailsVerifiation/webapi/login/student")
                        .post(body)
                        .addHeader("content-type", "application/json")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("postman-token", "f12c53b7-da4a-fe94-caf0-e41c9d658169")
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        System.out.println("what the hell");
                        e.printStackTrace();
                    }
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        String json = response.body().string();
                        Gson g = new Gson();

                        final StudentLoginResponse resp = g.fromJson(json, StudentLoginResponse.class);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                if (resp.getResult().getIsloginsuccessful()) {
                                    Toast.makeText(StudentLoginActivity.this,
                                            "Login Successfull...", Toast.LENGTH_LONG).show();
                                    Intent welcomeintent;
                                    welcomeintent = new Intent(com.example.loginactivity.StudentLoginActivity.this, com.example.loginactivity.WelcomeStudent.class);
                                    startActivity(welcomeintent);
                                    sharedPreferences=getSharedPreferences("studentdetails", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("logged", true);
                                    editor.putString("username",resp.getData().getStudentname());
                                    editor.putInt("enrollment",resp.getData().getStudentid());
                                    editor.apply();

                                } else {
                                    Toast.makeText(StudentLoginActivity.this,
                                            "Login Failure", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }

                });

            }
        });
    }
}
