package com.example.proyectoapilogin.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoapilogin.R;
import com.example.proyectoapilogin.adapter.HabitacionAdapter;
import com.example.proyectoapilogin.retrofit.ApiService;
import com.example.proyectoapilogin.retrofit.RetrofitRequest;
import com.example.proyectoapilogin.view_model.HabitacionViewModel;

public class MainActivity extends AppCompatActivity {
    private HabitacionViewModel habitacionViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final HabitacionAdapter adapter = new HabitacionAdapter(this);
        recyclerView.setAdapter(adapter);

        // Crear una instancia de ApiService usando Retrofit
        ApiService apiService = RetrofitRequest.getRetrofitInstance().create(ApiService.class);

        // Utilizar la fábrica del ViewModel para pasarle el ApiService
        habitacionViewModel = new ViewModelProvider(this, new HabitacionViewModel.Factory(apiService)).get(HabitacionViewModel.class);

        habitacionViewModel.getHabitaciones().observe(this, habitaciones -> {
            adapter.setHabitaciones(habitaciones);
        });
    }
}