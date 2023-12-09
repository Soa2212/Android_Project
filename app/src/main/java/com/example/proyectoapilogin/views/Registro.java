package com.example.proyectoapilogin.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.proyectoapilogin.R;
import com.example.proyectoapilogin.retrofit.ApiService;
import com.example.proyectoapilogin.retrofit.RetrofitRequest;
import com.example.proyectoapilogin.view_model.LoginViewModel;
import com.example.proyectoapilogin.view_model.RegistroViewModel;

public class Registro extends AppCompatActivity {
    Button login, registro;
    EditText nombre, email, password, passwordconfirm;
    RegistroViewModel registroViewModel;
    TextView errores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        login = findViewById(R.id.btnLogin);
        registro = findViewById(R.id.btnRegister);
        nombre = findViewById(R.id.etNombre);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPasssword);
        passwordconfirm = findViewById(R.id.etPasswordConfirm);
        errores = findViewById(R.id.tvError);

        ApiService apiService = RetrofitRequest.getRetrofitInstance(this).create(ApiService.class);
        registroViewModel = new RegistroViewModel(apiService, this);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = nombre.getText().toString();
                String correo = email.getText().toString();
                String pass = password.getText().toString();
                String passconfirm = passwordconfirm.getText().toString();
                registroViewModel.verifyRegister(nom, correo, pass, passconfirm);
            }
        });

        registroViewModel.getRegisterError().observe(this, error -> {
            errores.setText(error);
        });

        registroViewModel.getRegisterProcess().observe(this, process -> {
            if (process.equals("successful")) {
                errores.setBackgroundResource(R.drawable.items_detalle_habitacion);
                errores.setGravity(Gravity.CENTER);
            } else if (process.equals("failed")) {
                errores.setBackgroundResource(0);
                errores.setGravity(Gravity.START);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registro.this, Login.class);
                startActivity(intent);
            }
        });
    }
}