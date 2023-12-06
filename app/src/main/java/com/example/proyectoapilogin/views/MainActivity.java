package com.example.proyectoapilogin.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
    private TextView MisHabitaciones;
    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MisHabitaciones = findViewById(R.id.MisHabitaciones);
        MisHabitaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Recycler.class);
                context.startActivity(intent);
            }
        });
    }
}