package com.example.proyectoapilogin.views;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;
import com.example.proyectoapilogin.R;
import com.example.proyectoapilogin.retrofit.ApiService;
import com.example.proyectoapilogin.retrofit.RetrofitRequest;
import com.example.proyectoapilogin.view_model.HabitacionViewModel;
import com.example.proyectoapilogin.view_model.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {
    private TextView MisHabitaciones, crearHabitacion;
    private Context context = this;
    private MainActivityViewModel viewModel;
    private String savedToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MisHabitaciones = findViewById(R.id.MisHabitaciones);
        crearHabitacion = findViewById(R.id.HabitacionEdit);
        savedToken = retrieveTokenFromSharedPreferences();

        crearHabitacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CrearHabitacion.class);
                context.startActivity(intent);
            }
        });


            if(savedToken != null){
                MisHabitaciones.setOnClickListener(v -> {
                    Intent intent = new Intent(context, Recycler.class);
                    context.startActivity(intent);
                });
            }else{
                Intent intent = new Intent(context, Login.class);
                context.startActivity(intent);
            }
            /* ApiService apiService = RetrofitRequest.getRetrofitInstance(this).create(ApiService.class);
            viewModel = new ViewModelProvider(this, new MainActivityViewModel.Factory(apiService)).get(MainActivityViewModel.class);
            viewModel.getTokenValidity().observe(this, isTokenValid -> {
                if (isTokenValid) {
                    MisHabitaciones.setOnClickListener(v -> {
                        Intent intent = new Intent(context, Recycler.class);
                       context.startActivity(intent);
                    });
                } else {
                    Intent intent = new Intent(context, Login.class);
                   context.startActivity(intent);
                }
            });
        viewModel.verifyToken(savedToken);*/
    }



    private String retrieveTokenFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }
}
