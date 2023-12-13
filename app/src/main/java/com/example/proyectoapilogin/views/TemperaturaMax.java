package com.example.proyectoapilogin.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.example.proyectoapilogin.R;
import com.example.proyectoapilogin.retrofit.ApiService;
import com.example.proyectoapilogin.retrofit.RetrofitRequest;
import com.example.proyectoapilogin.view_model.CrearHabitacionViewModel;
import com.example.proyectoapilogin.view_model.TemperaturaMaxViewModel;

public class TemperaturaMax extends AppCompatActivity {

    TextView temperatura, ajustar, errores;
    private TemperaturaMaxViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperatura_max);
        temperatura = findViewById(R.id.edTemp);
        ajustar = findViewById(R.id.btnAjustar);
        errores = findViewById(R.id.errores);

        ApiService apiService = RetrofitRequest.getRetrofitInstance(this).create(ApiService.class);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        viewModel = new TemperaturaMaxViewModel(apiService, sharedPreferences);

        ajustar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = temperatura.getText().toString();
                viewModel.ajustarTemperatura(temp);
                new CountDownTimer(60000, 1000) {
                    public void onFinish() {
                        ajustar.setEnabled(true);
                    }
                    public void onTick(long millisUntilFinished) {
                        ajustar.setText("Intente en " + millisUntilFinished / 1000 + " segundos");
                        ajustar.setEnabled(false);
                    }
                }.start();
            }
        });
        viewModel.getChangeTemp().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isChanged) {
                if (isChanged) {
                    errores.setBackgroundResource(R.drawable.items_detalle_habitacion);
                    errores.setPadding(100,10,100,10);
                    new CountDownTimer(3000, 1000) {
                        public void onFinish() {
                            Intent intent = new Intent(TemperaturaMax.this, Recycler.class);
                            startActivity(intent);
                            finish();
                        }
                        public void onTick(long millisUntilFinished) {
                            errores.setText("Temperatura ajustada correctamente\n" +  "Volviendo al inicio en " + millisUntilFinished / 1000 + " segundos");
                        }
                    }.start();
                }
            }
        });
    }

}