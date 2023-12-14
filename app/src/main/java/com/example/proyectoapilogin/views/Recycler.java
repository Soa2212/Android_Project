package com.example.proyectoapilogin.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoapilogin.R;
import com.example.proyectoapilogin.Repositories.HabitacionRepository;
import com.example.proyectoapilogin.adapter.HabitacionAdapter;
import com.example.proyectoapilogin.retrofit.ApiService;
import com.example.proyectoapilogin.retrofit.RetrofitRequest;
import com.example.proyectoapilogin.view_model.HabitacionViewModel;

public class Recycler extends AppCompatActivity {
    private HabitacionViewModel habitacionViewModel;
    private LinearLayout lytReturn;
    private TextView txtReturn;
    private String savedToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        lytReturn = findViewById(R.id.lytReturn);
        txtReturn = findViewById(R.id.txtReturn);
        savedToken = retrieveTokenFromSharedPreferences();
        Log.d("Tonek",savedToken);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final HabitacionAdapter adapter = new HabitacionAdapter(this);
        recyclerView.setAdapter(adapter);

        ApiService apiService = RetrofitRequest.getRetrofitInstance(this).create(ApiService.class);
        HabitacionRepository habitacionRepository = new HabitacionRepository(apiService);

        HabitacionViewModel habitacionViewModel = new ViewModelProvider(this, new HabitacionViewModel.Factory(habitacionRepository,savedToken)).get(HabitacionViewModel.class);

        habitacionViewModel.getHabitaciones().observe(this, habitaciones -> {
            adapter.setHabitaciones(habitaciones);
        });

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
    }

    private void ReturnToMain() {
        Intent intent = new Intent(Recycler.this,MainActivity.class);
        startActivity(intent);
    }

    private String retrieveTokenFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }
}