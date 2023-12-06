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

    }
}