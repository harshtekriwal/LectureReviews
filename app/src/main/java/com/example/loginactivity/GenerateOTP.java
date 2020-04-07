package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GenerateOTP extends AppCompatActivity {

    TextView otp;
    EditText batches_selected;
    Button CodeGenerator;
    String sub;
    int sem;
    String bat;
    Spinner subject;
    Spinner semester;
    Button selectbatch;
    String[] listitems;
    boolean[] checkeditems;
    ArrayList<String> batches=new ArrayList<String>();
    ArrayList<Integer> mUseritems=new ArrayList<Integer>();
    OkHttpClient client = new OkHttpClient();
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_o_t_p);
        selectbatch=(Button)findViewById(R.id.button_selectbatch);
        subject=(Spinner)findViewById(R.id.spinner);
        semester=(Spinner)findViewById(R.id.spinner3);
        CodeGenerator=(Button)findViewById(R.id.button_OTP);
        final TextView selectedbatches=(TextView) findViewById(R.id.button_batches);
        listitems=getResources().getStringArray(R.array.batches);
        checkeditems=new boolean[listitems.length];
        selectbatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder=new AlertDialog.Builder(GenerateOTP.this);
                mBuilder.setTitle("Batches");
                mBuilder.setMultiChoiceItems(listitems, checkeditems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked) {
                            if (!mUseritems.contains(position)) {
                                mUseritems.add(position);
                            }
                        } else {
                            if (mUseritems.contains(position)) {
                                mUseritems.remove((Integer)position);
                            }
                        }
                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        batches=new ArrayList<String>();
                        for(int i=0;i<mUseritems.size();i++){
                        batches.add(listitems[mUseritems.get(i)]);
                        }
                        if(batches.size()<=0){
                            selectedbatches.setText("No Batches Selected");
                        }
                        else{
                            selectedbatches.setText(batches.toString());
                        }
                    }
                });
                AlertDialog mDialog=mBuilder.create();
                mDialog.show();
            }
        });
        CodeGenerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(batches.isEmpty()){
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder alert = new AlertDialog.Builder(GenerateOTP.this);
                            alert.setTitle("Error");
                            alert.setMessage("Please select atleast 1 Batch");
                            alert.setCancelable(false);
                            alert.setPositiveButton("OK", null);
                            AlertDialog dialog=alert.create();
                            dialog.show();

                        }
                    });
                    return;
                }
                String lec_name=subject.getItemAtPosition(subject.getSelectedItemPosition()).toString();
                String sem=semester.getItemAtPosition(semester.getSelectedItemPosition()).toString();
                CodeGenerator.setEnabled(false);
                sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                String teachername = sharedPreferences.getString("username", "");
                int teacherid = sharedPreferences.getInt("id", 0);
                MediaType mediaType = MediaType.parse("application/json");
                LectureDetails lectureDetails= new LectureDetails();
                lectureDetails.lecturename=lec_name;
                lectureDetails.teachername=teachername;
                lectureDetails.batch=batches;
                lectureDetails.semester=Integer.parseInt(sem);
                lectureDetails.teacherid=teacherid;
                System.out.println(batches);
                Gson g = new Gson();
                RequestBody body = RequestBody.create(g.toJson(lectureDetails),mediaType);
                Request request = new Request.Builder()
                        .url("http://192.168.1.28:8080/DetailsVerifiation/webapi/generatecode/seecode")
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
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.mainmenu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.item_aboutapp){ Intent welcomeintent;
            welcomeintent = new Intent(com.example.loginactivity.GenerateOTP.this,com.example.loginactivity.abouttheapp.class);
            startActivity(welcomeintent);
        }
        if(item.getItemId()==R.id.item_logout){
            SharedPreferences sharedpreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.apply();
            Intent welcomeintent;
            welcomeintent = new Intent(com.example.loginactivity.GenerateOTP.this,com.example.loginactivity.MainMenuActivity.class);
            welcomeintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(welcomeintent);
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
}