package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class GenerateOTP extends AppCompatActivity {
    TextView otp;
    EditText lecture_name;
    EditText batch;
    EditText semester;
    Button CodeGenerator;
    OkHttpClient client = new OkHttpClient();
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_o_t_p);
        lecture_name=(EditText)findViewById(R.id.edit_text_lecturename);
        batch=(EditText)findViewById(R.id.edit_text_batch);
        semester=(EditText)findViewById(R.id.edit_text_Semester);
        CodeGenerator=(Button)findViewById(R.id.button_OTP);
        otp=(TextView) findViewById(R.id.text_view_otp);
        CodeGenerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String lec_name = lecture_name.getText().toString();
                final String batch_b = batch.getText().toString();
                if (TextUtils.isEmpty(lec_name)) {
                    lecture_name.setError("Lecture Name cant be Empty");
                    return;
                }
                if (TextUtils.isEmpty(batch_b)) {
                    batch.setError("Batch cant be empty");
                    return;
                }
                if (TextUtils.isEmpty(semester.getText().toString())) {
                    semester.setError("Semester cant be empty");
                    return;
                }
                CodeGenerator.setEnabled(false);
                sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                String teachername = sharedPreferences.getString("username", "");
                int teacherid = sharedPreferences.getInt("id", 0);
                final int sem = Integer.parseInt(semester.getText().toString());
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, "{\t\t\n\t\t\"lecturename\":\"" + lec_name + "\",\n\t\t\"teachername\":\"" + teachername + "\",\n\t\t\"batch\":\"" + batch_b + "\",\n\t\t\"semester\":" + sem + ",\n        \"teacherid\": " + teacherid + "\n}\t");
                Request request = new Request.Builder()
                        .url("http://10.0.2.2:8080/DetailsVerifiation/webapi/generatecode/seecode")
                        .post(body)
                        .addHeader("content-type", "application/json")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("postman-token", "d94d6ee3-b081-3c13-a896-007d53be0f66")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String json = response.body().string();
                        Gson g = new Gson();
                        final Integer code = g.fromJson(json, Integer.class);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder alert = new AlertDialog.Builder(GenerateOTP.this);
                                alert.setTitle("Lecture Code");
                                alert.setMessage(Integer.toString(code));
                                alert.setCancelable(false);
                                alert.setPositiveButton("OK", null);
                                AlertDialog dialog=alert.create();
                                dialog.show();
                            }


                        });
                    }

                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }
                });

                }
        });

    }
}