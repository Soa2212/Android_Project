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
    private String Token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vMTkyLjE2OC4xMDAuODQvYXBpL3JlZnJlc2giLCJpYXQiOjE3MDIwMDc4MzYsImV4cCI6MTcwMjAxMTcwNiwibmJmIjoxNzAyMDA4MTA2LCJqdGkiOiJRZ1BwaE1NdUxvSlZrWHBCIiwic3ViIjoiMSIsInBydiI6IjIzYmQ1Yzg5NDlmNjAwYWRiMzllNzAxYzQwMDg3MmRiN2E1OTc2ZjcifQ.UVzBk3MN6NSGDetoTjmdDCuOpNUcIXkJuhEi3SWi51E";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        token = findViewById(R.id.Token);

        token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTokenInSharedPreferences(Token);
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateTokenInSharedPreferences(String newToken) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", newToken);
        editor.apply();
    }
}
