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
    private TextView MisHabitaciones;
    private Context context = this;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MisHabitaciones = findViewById(R.id.MisHabitaciones);

        String savedToken = retrieveTokenFromSharedPreferences();

        ApiService apiService = RetrofitRequest.getRetrofitInstance(this).create(ApiService.class);

        viewModel = new ViewModelProvider(this, new MainActivityViewModel.Factory(apiService)).get(MainActivityViewModel.class);

        viewModel.getTokenValidity().observe(this, isTokenValid -> {
            if (isTokenValid) {
                Log.d("Token1", String.valueOf(isTokenValid));
                MisHabitaciones.setOnClickListener(v -> {
                    Intent intent = new Intent(context, Recycler.class);
                    context.startActivity(intent);
                });
            } else {
                Log.d("Token2", String.valueOf(isTokenValid));
                Intent intent = new Intent(context, Login.class);
                context.startActivity(intent);
            }
        });

        viewModel.verifyToken(savedToken);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String updatedToken = retrieveTokenFromSharedPreferences();
        viewModel.verifyToken(updatedToken);
    }

    // Método para obtener el token almacenado en SharedPreferences
    private String retrieveTokenFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }
}

