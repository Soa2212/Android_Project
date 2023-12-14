package com.example.proyectoapilogin.Repositories;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.proyectoapilogin.retrofit.ApiService;
import com.example.proyectoapilogin.response.CrearHabitacionResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearHabitacionRepository {
    private final ApiService apiService;
    private final SharedPreferences sharedPreferences;

    public CrearHabitacionRepository(ApiService apiService, SharedPreferences sharedPreferences) {
        this.apiService = apiService;
        this.sharedPreferences = sharedPreferences;
    }

    public MutableLiveData<String> crearHabitacion(String name) {
        MutableLiveData<String> roomError = new MutableLiveData<>();

        String token = sharedPreferences.getString("token", "");
        apiService.storeRoom("Bearer " + token, name).enqueue(new Callback<CrearHabitacionResponse>() {
            @Override
            public void onResponse(Call<CrearHabitacionResponse> call, Response<CrearHabitacionResponse> response) {
                Log.d("CrearHabitacionRepository", "Entro a onResponse");
                if (response.body() != null) {
                    if (response.body().getProcess().equals("success")) {
                        roomError.setValue("Habitacion creada correctamente");
                    } else if (response.body().getProcess().equals("failed")) {
                        roomError.setValue("El nombre debe tener al menos 6 caracteres");
                    }
                } else {
                    roomError.setValue("Error de respuesta de la API: " + response.errorBody());
                    Log.e("CrearHabitacionRepository", "Error de respuesta de la API: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<CrearHabitacionResponse> call, Throwable t) {
                roomError.setValue("Error de conexión: " + t.getMessage());
                Log.e("CrearHabitacionRepository", "Error de conexión", t);
            }
        });

        return roomError;
    }
}