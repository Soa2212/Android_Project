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

public class Recycler extends AppCompatActivity {
    private HabitacionViewModel habitacionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final HabitacionAdapter adapter = new HabitacionAdapter(this);
        recyclerView.setAdapter(adapter);

        ApiService apiService = RetrofitRequest.getRetrofitInstance(this).create(ApiService.class);

        habitacionViewModel = new ViewModelProvider(this, new HabitacionViewModel.Factory(apiService)).get(HabitacionViewModel.class);

        habitacionViewModel.getHabitaciones().observe(this, habitaciones -> {
            adapter.setHabitaciones(habitaciones);
        });
    }
}