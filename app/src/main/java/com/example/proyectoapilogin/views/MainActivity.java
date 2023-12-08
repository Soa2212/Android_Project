package com.example.proyectoapilogin.views;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;
import com.example.proyectoapilogin.R;
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

        if (savedToken != null && !savedToken.isEmpty()) {

        //Aqui quiero verificar que el token sea valido en base a la peticion
            MisHabitaciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Recycler.class);
                    context.startActivity(intent);
                }
            });
        } else {
            Intent intent = new Intent(context, Login.class);
            context.startActivity(intent);
        }
    }

    private String retrieveTokenFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        return sharedPreferences.getString("token", "");
    }
}
