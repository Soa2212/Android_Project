package com.example.proyectoapilogin.views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.proyectoapilogin.R;

public class Login extends AppCompatActivity {
    private TextView token;
    private String Token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vMTkyLjE2OC4xMDAuODQvYXBpL2xvZ2luIiwiaWF0IjoxNzAyMDAwNzI2LCJleHAiOjE3MDIwMDQzMjYsIm5iZiI6MTcwMjAwMDcyNiwianRpIjoiZXd1Y2VFalVUeTJ1blQ2WiIsInN1YiI6IjEiLCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0.GtIQ0-3lxFC7g1Qij34gPNipHuutmQTCLwHy7QzBiDk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        token = findViewById(R.id.Token);

        token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTokenInSharedPreferences(Token);
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void saveTokenInSharedPreferences(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }
}
