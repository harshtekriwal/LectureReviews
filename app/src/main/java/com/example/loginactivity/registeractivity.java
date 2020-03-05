package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import okhttp3.OkHttpClient;

public class registeractivity extends AppCompatActivity {
    EditText username;
    EditText password;
    EditText confirmpassword;
    Button register;
    TextView login;
    OkHttpClient client=new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeractivity);
        username=(EditText)findViewById(R.id.edittext_username);
        password=(EditText)findViewById(R.id.edittext_password);
        confirmpassword=(EditText)findViewById(R.id.edittext_confirmpassword);
        register=(Button)findViewById(R.id.button_register);
        login=(TextView)findViewById(R.id.textview_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginIntent=new Intent(com.example.loginactivity.registeractivity.this,com.example.loginactivity.MainActivity.class);
                startActivity(LoginIntent);
            }
        });
    }
}
