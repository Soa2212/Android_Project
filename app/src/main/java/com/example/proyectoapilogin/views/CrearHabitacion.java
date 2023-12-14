package com.example.proyectoapilogin.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.proyectoapilogin.R;
import com.example.proyectoapilogin.retrofit.ApiService;
import com.example.proyectoapilogin.retrofit.RetrofitRequest;
import com.example.proyectoapilogin.view_model.CrearHabitacionViewModel;

public class CrearHabitacion extends AppCompatActivity {
    private EditText nombre;
    private CrearHabitacionViewModel room;
    private TextView errores,crearHabitacion;

    private LinearLayout lytReturn;
    private TextView txtReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_habitacion);
        crearHabitacion = findViewById(R.id.btnCrear);
        nombre = findViewById(R.id.edNombre);
        errores = findViewById(R.id.tvErrores);
        lytReturn = findViewById(R.id.lytReturn);
        txtReturn = findViewById(R.id.txtReturn);

        txtReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReturnToMain();
            }
        });

        lytReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReturnToMain();
            }
        });

        ApiService apiService = RetrofitRequest.getRetrofitInstance(this).create(ApiService.class);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        room = new CrearHabitacionViewModel(apiService, sharedPreferences);

        crearHabitacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nombre.getText().toString();
                room.crearHabitacion(name);
                crearHabitacion.setEnabled(false);
            }
        });

        room.getRoomError().observe(this, error -> {
            errores.setText(error);
        });

        room.getChangeMessage().observe(this, change -> {
            if (change) {
                errores.setBackgroundResource(R.drawable.items_detalle_habitacion);
                errores.setTextColor(ContextCompat.getColor(this, R.color.black));
                errores.setPadding(100,10,100,10);
            } else {
                errores.setBackgroundResource(0);
                errores.setTextColor(Color.parseColor("#FF0000"));
                crearHabitacion.setEnabled(true);
            }
        });

        room.getFinishActivity().observe(this, shouldFinish -> {
            if (shouldFinish) {
                Intent intent = new Intent(CrearHabitacion.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void ReturnToMain() {
        Intent intent = new Intent(CrearHabitacion.this,MainActivity.class);
        startActivity(intent);
    }
}