package com.example.proyectoapilogin.views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.proyectoapilogin.R;
import com.example.proyectoapilogin.retrofit.ApiService;
import com.example.proyectoapilogin.retrofit.RetrofitRequest;
import com.example.proyectoapilogin.view_model.LoginViewModel;

public class Login extends AppCompatActivity {
    private Button login, registro;
    private EditText email, password;
    private LoginViewModel loginViewModel;
    private TextView errores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.btnLogin);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPasssword);
        errores = findViewById(R.id.tvError);
        registro = findViewById(R.id.btnRegister);

        ApiService apiService = RetrofitRequest.getRetrofitInstance(this).create(ApiService.class);
        loginViewModel = new LoginViewModel(apiService, this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errores.setText("");
                String correo = email.getText().toString();
                String pass = password.getText().toString();
                loginViewModel.verifyLogin(correo, pass);
            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registro.class);
                startActivity(intent);
            }
        });

        loginViewModel.getLoginError().observe(this, error -> {
            errores.setText(error);
        });

        /*token.setOnClickListener(new View.OnClickListener() {
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
    } */
    }
}
