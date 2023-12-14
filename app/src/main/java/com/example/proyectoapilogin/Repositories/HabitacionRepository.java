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

    public LiveData<List<Habitacion>> getHabitaciones() {
        MutableLiveData<List<Habitacion>> habitaciones = new MutableLiveData<>();

        apiService.getHabitaciones().enqueue(new Callback<HabitacionResponse>() {
            @Override
            public void onResponse(Call<HabitacionResponse> call, Response<HabitacionResponse> response) {
                if (response.isSuccessful()) {
                    habitaciones.setValue(response.body().getHabitaciones());
                    Log.d("HabitacionRepository", "Petición exitosa. Habitaciones obtenidas: " + response.body().getHabitaciones());
                } else {
                    Log.e("HabitacionRepository", "Error en la petición: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<HabitacionResponse> call, Throwable t) {
                Log.e("HabitacionRepository", "Error en la petición: " + t.getMessage());
                t.printStackTrace();
            }
        });

        return habitaciones;
    }
}
