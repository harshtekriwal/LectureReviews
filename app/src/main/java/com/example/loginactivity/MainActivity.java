package com.example.loginactivity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button login;
    TextView register;
    SharedPreferences sharedpreferences;
    OkHttpClient client = new OkHttpClient();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("lol");
        username = (EditText) findViewById(R.id.edittext_username);
        password = (EditText) findViewById(R.id.edittext_password);
        login = (Button) findViewById(R.id.button_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String x = username.getText().toString();
                if (TextUtils.isEmpty(x)) {
                    username.setError("Username cant be empty");
                    return;
                }
                final int uname = Integer.parseInt(x);
                final String pass = password.getText().toString();
                if (TextUtils.isEmpty(pass)) {
                    password.setError("Password cant be empty");
                    return;
                }
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create("{\n        \"teacherid\": \"" + uname + "\",\n        \"teacherpassword\":\"" + pass + "\"\n}", mediaType);
                Request request = new Request.Builder()
                        .url("http://192.168.1.28:8080/DetailsVerifiation/webapi/login/teacher")
                        .post(body)
                        .addHeader("content-type", "application/json")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("postman-token", "1c8dd841-a5b0-b45f-c14e-2ee6e0d78235")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        String json = response.body().string();
                        Gson g = new Gson();
                            /*
                            Type type = new TypeToken<List<LoginDetails>>() {
                            }.getType();
                            List<LoginDetails> loginDetailsList = g.fromJson(json, type);
                            Class<List> listClass = List.class;
                            System.out.println(loginDetailsList);*/
                        final TeacherLoginResponse resp = g.fromJson(json, TeacherLoginResponse.class);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                if (resp.getResult().getIsloginsuccessful()) {
                                    Toast.makeText(MainActivity.this,
                                            "Login Successfull...", Toast.LENGTH_LONG).show();
                                    Intent welcomeintent;
                                    welcomeintent = new Intent(com.example.loginactivity.MainActivity.this, com.example.loginactivity.WelcomeTeacher.class);
                                    startActivity(welcomeintent);
                                    sharedpreferences=getSharedPreferences("mypref", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putBoolean("logged", true);
                                    editor.putString("username",resp.getData().getTeachername());
                                    editor.putInt("id",resp.getData().getTeacherid());
                                    editor.apply();

                                } else {
                                    Toast.makeText(MainActivity.this,
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


