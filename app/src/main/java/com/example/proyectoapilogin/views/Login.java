package com.example.proyectoapilogin.views;

import androidx.appcompat.app.AppCompatActivity;

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
    private EditText email, password;
    private LoginViewModel loginViewModel;
    private TextView errores,login,registro;

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
        loginViewModel = new LoginViewModel(apiService,this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setEnabled(false);
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

        loginViewModel.getBlock().observe(this, block -> {
            if (block) {
                login.setEnabled(false);
            } else {
                login.setEnabled(true);
            }
        });

        loginViewModel.getSuccessfulLogin().observe(this, successful -> {
            if (successful) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}