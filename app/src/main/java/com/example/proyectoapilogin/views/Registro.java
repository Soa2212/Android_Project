package com.example.proyectoapilogin.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.proyectoapilogin.R;
import com.example.proyectoapilogin.retrofit.ApiService;
import com.example.proyectoapilogin.retrofit.RetrofitRequest;
import com.example.proyectoapilogin.view_model.RegistroViewModel;

public class Registro extends AppCompatActivity {
    private Button registro;
    private EditText nombre, email, password, passwordconfirm;
    private RegistroViewModel registroViewModel;
    private TextView errores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        registro = findViewById(R.id.btnRegister);
        nombre = findViewById(R.id.etNombre);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPasssword);
        passwordconfirm = findViewById(R.id.etPasswordConfirm);
        errores = findViewById(R.id.tvError);

        ApiService apiService = RetrofitRequest.getRetrofitInstance(this).create(ApiService.class);
        registroViewModel = new RegistroViewModel(apiService);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registro.setEnabled(false);
                String name = nombre.getText().toString();
                String correo = email.getText().toString();
                String pass = password.getText().toString();
                String passconfirm = passwordconfirm.getText().toString();
                registroViewModel.verifyRegister(name, correo, pass, passconfirm);
            }
        });

        registroViewModel.getRegisterError().observe(this, error -> {
            errores.setText(error);
            if (error.equals("Se ha enviado un correo de confirmación a su email. Verifiquelo e inicie sesión para registrar su cuenta.\n" +
                    "Volviendo al inicio en: 5 segundos")) {
                errores.setBackgroundResource(R.drawable.items_detalle_habitacion);
                errores.setTextColor(ContextCompat.getColor(this, R.color.black));
                errores.setPadding(100,10,100,10);
                errores.setGravity(Gravity.CENTER);
            }
        });

        registroViewModel.getSuccessfulRegister().observe(this, successful -> {
            if (successful) {
                Intent intent = new Intent(Registro.this, Login.class);
                startActivity(intent);
                finish();
            } else {
                registro.setEnabled(true);
                errores.setBackgroundResource(0);
                errores.setGravity(Gravity.START);
                errores.setTextColor(Color.parseColor("#FF0000"));
            }
        });
    }
}