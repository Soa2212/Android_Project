package com.example.proyectoapilogin.views;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;
import com.example.proyectoapilogin.R;
import com.example.proyectoapilogin.retrofit.ApiService;
import com.example.proyectoapilogin.retrofit.RetrofitRequest;
import com.example.proyectoapilogin.view_model.DetalleHabitacionViewModel;
import com.example.proyectoapilogin.view_model.HabitacionViewModel;
import com.example.proyectoapilogin.view_model.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {
    private TextView MisHabitaciones, crearHabitacion, logout;
    private Context context = this;
    private MainActivityViewModel viewModel;
    private String savedToken;
    Intent servicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        servicio = new Intent(this, TemperatureCheckService.class);


        MisHabitaciones = findViewById(R.id.MisHabitaciones);
        crearHabitacion = findViewById(R.id.HabitacionEdit);
        savedToken = retrieveTokenFromSharedPreferences();
        logout = findViewById(R.id.tvLogout);
        ApiService apiService = RetrofitRequest.getRetrofitInstance(this).create(ApiService.class);
        viewModel = new ViewModelProvider(this, new MainActivityViewModel.Factory(apiService)).get(MainActivityViewModel.class);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED){
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1900);
        } else {
            startService(servicio);
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.logout(MainActivity.this);
            }
        });

        viewModel.getLogoutValidity().observe(this, isSuccessful -> {
            if (isSuccessful) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

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
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1900) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startService(servicio);
            }
        }
    }

    private String retrieveTokenFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }
}
