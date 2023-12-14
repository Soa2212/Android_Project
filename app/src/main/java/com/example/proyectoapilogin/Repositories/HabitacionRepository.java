package com.example.proyectoapilogin.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.proyectoapilogin.model.Habitacion;
import com.example.proyectoapilogin.response.HabitacionResponse;
import com.example.proyectoapilogin.retrofit.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HabitacionRepository {
    private final ApiService apiService;

    public HabitacionRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public LiveData<List<Habitacion>> getHabitaciones(String token) {
        MutableLiveData<List<Habitacion>> habitaciones = new MutableLiveData<>();
        Log.d("Habitaciones",token);

        apiService.getHabitaciones("Bearer " + token).enqueue(new Callback<HabitacionResponse>() {

            @Override
            public void onResponse(Call<HabitacionResponse> call, Response<HabitacionResponse> response) {
                if (response.isSuccessful()) {
                    habitaciones.setValue(response.body().getHabitaciones());
                    Log.d("Habitaciones",String.valueOf(response.body().getHabitaciones()));
                }
            }

            @Override
            public void onFailure(Call<HabitacionResponse> call, Throwable t) {
                // Manejo de errores
            }
        });

        return habitaciones;
    }
}
